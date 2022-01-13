package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


class ChessGUI extends JFrame implements ActionListener {
    private final JPanel messagePanel = new JPanel();
    private final  JPanel chessGamePanel = new JPanel();
    private final JLabel gameStatus = new JLabel();
    private final JLabel turnLabel = new JLabel("Vit spelar");
    private final JLabel blackCheckStatus = new JLabel();
    private final JLabel whiteCheckStatus = new JLabel();
    private final ChessGame chessGame;
    private final int size;
    private final GUISpot[][] GUIBoard;
    private final ArrayList<GUISpot> coloredSpots = new ArrayList<>();

    public ChessGUI(ChessGame gb) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Amazing Chess Game");
        setSize(800, 1000);
        chessGame = gb;
        size = chessGame.getSize();
        GUIBoard = new GUISpot[size][size];
        setupView();
    }

    private void setupView() {
        setLayout(new GridBagLayout());
        addComponents();
        setVisible(true);
    }

    private void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        add(messagePanel, c);
        c.gridx = 0;
        c.gridy = 1;
        add(chessGamePanel, c);
        setupMessagePanel();
        setupChessGamePanel();

    }

    private void setupMessagePanel(){
        messagePanel.setSize(600,200);
        messagePanel.setLayout(new GridLayout(4,1));
        messagePanel.add(turnLabel);
        messagePanel.add(gameStatus);
        messagePanel.add(blackCheckStatus);
        messagePanel.add(whiteCheckStatus);

        turnLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gameStatus.setText(chessGame.getGameStatus());

    }

    private void setupChessGamePanel(){
        chessGamePanel.setLayout(new GridLayout(size, size));

        for (Spot[] spots : chessGame.getChessBoard()){
            for (Spot spot : spots){
                GUISpot newSpot = new GUISpot(spot);
                newSpot.addActionListener(this);
                GUIBoard[spot.getRow()][spot.getCol()] = newSpot;
                chessGamePanel.add(GUIBoard[spot.getRow()][spot.getCol()]);
            }
        }
    }

    private void updateMessagePanel(){
        gameStatus.setText(chessGame.getGameStatus());
        updateCheckLabels();
        updateTurnLabel();
    }

    private void updateTurnLabel() {
        if(chessGame.isWhiteTurn()){
            turnLabel.setText("Vit spelares tur");
        }
        else{
            turnLabel.setText("Svart spelares tur");
        }
    }

    private void updateCheckLabels() {
        blackCheckStatus.setText("");
        whiteCheckStatus.setText("");
        if (chessGame.isBlackKingChecked()) {
            blackCheckStatus.setText("Svart kung i schack");
        }
        if (chessGame.isWhiteKingChecked()) {
            whiteCheckStatus.setText("Vit kung i schack");
        }
    }

    private void updateGUIBoard(){
        for (Spot[] spots : chessGame.getChessBoard()) {
            for (Spot spot : spots) {
                GUIBoard[spot.getRow()][spot.getCol()].updateSpot();
            }
        }
    }

    private void endGame() {
        messagePanel.removeAll();
        messagePanel.add(turnLabel);
        turnLabel.setText(chessGame.getGameStatus());

        for (GUISpot[] spots : GUIBoard) {
            for (GUISpot spot : spots) {
                spot.removeActionListener(this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        GUISpot pressedSpot = (GUISpot) e.getSource();
        Spot clickedSpot = pressedSpot.getSpot();                  //kollar vilken chessboard-spot som klickad GUISpot representerar
        boolean clickOne = chessGame.isClickOne();
        chessGame.handleMoves(clickedSpot);                    //skicka klickad spot till chessboard
        updateMessagePanel();

        if (clickOne){
            for (Spot possibleSpot : chessGame.getPossibleMoves()){
                GUISpot colorSpot = GUIBoard[possibleSpot.getRow()][possibleSpot.getCol()];
                colorSpot.setAcceptedMoveColor();
                coloredSpots.add(colorSpot);
            }
        }
        else{
            updateGUIBoard();
            for (GUISpot coloredSpot : coloredSpots){
                coloredSpot.removeAcceptedMoveColor();
            }
            coloredSpots.clear();

            if(chessGame.isWon()){
                endGame();
            }
        }
    }

}
