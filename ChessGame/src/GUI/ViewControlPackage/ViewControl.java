package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;


class ViewControl extends JFrame implements ActionListener {
    JPanel messagePanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JLabel gameStatus = new JLabel();
    JLabel turnStatus = new JLabel("Vit spelar");
    JLabel blackCheckStatus = new JLabel();
    JLabel whiteCheckStatus = new JLabel();
    ChessBoard gameboard;
    GraphicSpot[][] visibleGameboard;
    ArrayList<GraphicSpot> changedSpots = new ArrayList<>();

    public ViewControl() throws IOException, NullPointerException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Amazing Chess Game");
        setSize(800, 1000);
        gameboard = new ChessBoard();
        visibleGameboard = new GraphicSpot[gameboard.size][gameboard.size];
        setupView();
    }

    public void setupView() {
        setLayout(new GridBagLayout());
        addComponents();
        setVisible(true);
    }

    public void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        add(messagePanel, c);
        c.gridx = 0;
        c.gridy = 1;
        add(gamePanel, c);
        setupMessagePanel();
        setupGamePanel();

    }

    public void setupMessagePanel(){
        messagePanel.setSize(600,200);
        messagePanel.setLayout(new GridLayout(4,1));
        messagePanel.add(turnStatus);
        messagePanel.add(gameStatus);
        messagePanel.add(blackCheckStatus);
        messagePanel.add(whiteCheckStatus);

        turnStatus.setFont(new Font("Serif", Font.BOLD, 20));
        gameStatus.setText(gameboard.getGameStatus());

    }

    public void setupGamePanel(){
        gamePanel.setLayout(new GridLayout(8,8));
        for (Spot[] spots : gameboard.gameboard){
            for (Spot spot : spots){
                GraphicSpot newSpot = new GraphicSpot(spot);
                newSpot.addActionListener(this);
                visibleGameboard[spot.getRow()][spot.getCol()] = newSpot;
                gamePanel.add(visibleGameboard[spot.getRow()][spot.getCol()]);

            }
        }
        gamePanel.setVisible(true);
    }

    public void updateStatus(){
        gameStatus.setText(gameboard.getGameStatus());
        updateCheckLabels();
        if(gameboard.whiteTurn){
            turnStatus.setText("Vit spelares tur");
        }
        else{
            turnStatus.setText("Svart spelares tur");
        }
    }

    private void updateCheckLabels() {
        blackCheckStatus.setText("");
        whiteCheckStatus.setText("");
        if (gameboard.blackKingCheck) {
            blackCheckStatus.setText("Svart kung i schack");
        }
        if (gameboard.whiteKingCheck) {
            whiteCheckStatus.setText("Vit kung i schack");
        }
    }

    public void updateBoard(){
        for (Spot[] spots : gameboard.gameboard) {
            for (Spot spot : spots) {
                visibleGameboard[spot.getRow()][spot.getCol()].updateSpot();
            }
        }
    }

    private void endGame() {
        messagePanel.removeAll();
        messagePanel.add(turnStatus);
        turnStatus.setText(gameboard.getGameStatus());

        for (GraphicSpot[] spots : visibleGameboard) {
            for (GraphicSpot spot : spots) {
                spot.removeActionListener(this);
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        GraphicSpot presBut = (GraphicSpot) e.getSource();
        Spot spot = presBut.spot;
        gameboard.handleMoves(spot);
        updateStatus();
        if (!(gameboard.clickOne)){
            for (Spot posSpot : gameboard.possibleMoves){
                GraphicSpot changeSpot = visibleGameboard[posSpot.getRow()][posSpot.getCol()];
                changeSpot.setAcceptedMoveColor();
                changedSpots.add(changeSpot);
            }
        }
        else{
            updateBoard();
            for (GraphicSpot changedSpot : changedSpots){
                changedSpot.removeAcceptedMoveColor();
            }
            if(gameboard.whiteWin || gameboard.blackWin){
                endGame();
            }
        }
    }


    public static void main(String[] args) throws IOException, NullPointerException {
        new ViewControl();
    }

}
