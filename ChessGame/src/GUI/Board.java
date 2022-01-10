package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Board extends JFrame implements ActionListener {

    private final JPanel gamePanel = new JPanel();
    private final JPanel messagePanel = new JPanel();
    private JLabel messageLabel = new JLabel();
    private final JLabel whiteCheckLabel = new JLabel();
    private final JLabel blackCheckLabel = new JLabel();
    private final Spot[][] gameboard;
    private Spot movBut;
    private ArrayList <Spot> possibleMoves = new ArrayList<>();
    private final Stack <Spot> coloredSpots = new Stack<>();

   // private boolean whiteKingCheck = false;  //kan behövas
    //private boolean blackKingCheck = false; //kan behövas
    private Spot whiteKingSpot;
    private Spot blackKingSpot;
    private boolean whiteTurn = true;
    private boolean clickOne = true;

    public Board() throws IOException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        //setResizable(false);
        gameboard = new Spot[8][8];
        setLayout(new FlowLayout());
        messagePanel.setLayout(new GridLayout(3,1));
        messageLabel.setText("Vit spelare startar");
        messagePanel.add(messageLabel);
        messagePanel.add(whiteCheckLabel);
        messagePanel.add(blackCheckLabel);
        messagePanel.setMaximumSize(new Dimension(200, 200));


        add(messagePanel);
        add(gamePanel);
        setupBoard();

        setVisible(true);
        //pack();
    }

    private void setupBoard() throws IOException {
        gamePanel.setLayout((new GridLayout(8, 8)));

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/startPositions.txt"));
        String line = startPos.readLine();
        int row = 0;
        int col = 0;
        while (line != null) {
            String[] positions = line.split(" ");
            for(String pos : positions){
                Spot curBut = new Spot(pos, row, col);
                curBut.addActionListener(this);
                gameboard[row][col] = curBut;
                gamePanel.add(gameboard[row][col]);
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
        updateKingsSpot();
    }

    public void makeMove(Spot newSpot){
        Spot oldSpot = gameboard[movBut.getRow()][movBut.getCol()];
        Piece movedPiece = movBut.getPiece();
        switchPos(oldSpot, newSpot, movedPiece);

//        Spot oldSpot = gameboard[movBut.getRow()][movBut.getCol()];
//        Piece movedPiece = movBut.getPiece();
//        newSpot.setIcon(movBut.getIcon());
//        newSpot.setPiece(movedPiece);
//        oldSpot.setIcon(null);
//        oldSpot.setPiece(null);

        handleSpecialMoves(newSpot, movedPiece);

//        if (newSpot.getPiece() instanceof King){
//            handleVictory();
//        }
//        if (movedPiece instanceof King) {
//            updateKingsSpot();  //för att hålla koll på när bonden ska förvandlas
//        }
//        else if (movedPiece instanceof Pawn) {
//            handleMovedPawn(newSpot, movedPiece);  //för att hålla koll på när bonden ska förvandlas
//        }
        checkForCheck(); //kolla ifall motsåndaren står i schack
        switchTurn();
        movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas
    }

    private void handleSpecialMoves(Spot newSpot, Piece movedPiece) {
        if (newSpot.getPiece() instanceof King){
            handleVictory();
        }
        if (movedPiece instanceof King) {
            updateKingsSpot();  //för att hålla koll på när bonden ska förvandlas
        }
        else if (movedPiece instanceof Pawn) {
            handleMovedPawn(newSpot, movedPiece);  //för att hålla koll på när bonden ska förvandlas
        }
    }

    private void switchPos(Spot oldSpot, Spot newSpot, Piece movedPiece) {


        newSpot.setIcon(movBut.getIcon());
        newSpot.setPiece(movedPiece);
        oldSpot.setIcon(null);
        oldSpot.setPiece(null);

    }

    private void handleVictory() {
        if (whiteTurn){
            messageLabel.setText("Vit vinner!");
        }
        else{
            messageLabel.setText("Svart vinner!");
        }
        remove(gamePanel);
        messagePanel.remove(blackCheckLabel);
        messagePanel.remove(whiteCheckLabel);
        repaint();
    }

    private void handleMovedPawn(Spot newSpot, Piece pawn) {
        int stepLength = Math.abs(newSpot.getRow() - movBut.getRow());
        ((Pawn) pawn).increasePawnMoves(stepLength);  //öka antalet steg för bonden
        if (((Pawn) pawn).getNumOfMoves() == 6) {       //om bonden nått över till andra sidan
            transformPawn(newSpot, pawn);               //förvandla bonden
        }
    }

    private void transformPawn(Spot spot, Piece pawn) {
        if (pawn.isWhite()){
            spot.setPiece(new Queen(true));
            Icon queenIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/WhiteQueen.png")));
            spot.setIcon(queenIcon);
        }
        else {
            spot.setPiece(new Queen(false));
            Icon queenIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/BlackQueen.png")));
            spot.setIcon(queenIcon);
        }
    }

    public void showMoves(){
        for (Spot possibleSpot : possibleMoves) {
            possibleSpot.setAcceptedMoveColor();
            coloredSpots.push(possibleSpot);
        }
    }

    private void removeShowedMoves() {

        possibleMoves.clear();
        while (!coloredSpots.isEmpty()){
            coloredSpots.pop().removeAcceptedMoveColor();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Spot pressedSpot = (Spot)e.getSource();

        if(clickOne){
            FirstClick click = new FirstClick(pressedSpot, gameboard, whiteTurn);
            if(click.isOkClick()) {
                movBut = pressedSpot;
                possibleMoves = click.getPossibleMoves();
                messageLabel = click.getMessageLabel();
                showMoves();
                clickOne = false;
            }
            else{
                clickOne = true;
                messageLabel = click.getMessageLabel();
            }
        }
        else {
            if (possibleMoves.contains(pressedSpot)) {
                removeShowedMoves();
                makeMove(pressedSpot);
            }
            else{
                messageLabel.setText("Välj en giltig plats");
            }
            clickOne = true;
        }
    }



    private void checkForCheck() { //metod som kollar om någon pjäs hotar motståndarens kung

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if(spot.getPiece() != null){
                    Piece curPiece = spot.getPiece();
                    if (curPiece.isWhite()){
                        if (curPiece.acceptedMove(gameboard, spot, blackKingSpot)) {
                            blackCheckLabel.setText("Svart kung i schack");
                        }
                    }
                    else{
                        if (curPiece.acceptedMove(gameboard, spot, whiteKingSpot)) {
                            whiteCheckLabel.setText("Vit kung i schack");
                        }
                    }
                }
            }
        }
    }


    private void switchTurn() {
        clickOne = true;
        if(whiteTurn){
            messageLabel.setText("Vit spelares tur");
        }
        else{
            messageLabel.setText("Svart spelares tur");
        }
        whiteTurn = !whiteTurn;
    }

    private void updateKingsSpot() {
        //blackKingSpot = null;
        //whiteKingSpot = null;

        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null && spot.getPiece() instanceof King) {
                    if (spot.getPiece().isWhite()) {
                        whiteKingSpot = spot;
                    } else {
                        blackKingSpot = spot;
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}

