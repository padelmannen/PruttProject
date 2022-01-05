package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.*;

public class Board extends JFrame implements ActionListener {

    private JPanel gamePanel = new JPanel();
    private JPanel messagePanel = new JPanel();
    private JLabel messageLabel = new JLabel();
    private JLabel whiteCheckLabel = new JLabel();
    private JLabel blackCheckLabel = new JLabel();
    private Spot[][] gameboard;
    private Spot movBut;
    private Stack <Spot> possiblemoves = new Stack<>();
    private boolean whiteKingCheck = false;  //kan behövas
    private boolean blackKingCheck = false; //kan behövas
    private boolean whiteTurn = true;

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
    }

    public Spot getBox(int row, int col){
        return gameboard[row][col];
    }

    public void move(Spot newSpot){
        Spot oldSpot = gameboard[movBut.getRow()][movBut.getCol()];
        Piece movedPiece = movBut.getPiece();

        if (newSpot.getPiece() instanceof King){
            handleVictory();
        }

        newSpot.setIcon(movBut.getIcon());

        newSpot.setPiece(movedPiece);

        oldSpot.setIcon(null);
        oldSpot.setPiece(null);

        if (movedPiece instanceof Pawn) {
            handleMovedPawn(newSpot, movedPiece);  //för att hålla koll på när bonden ska förvandlas
        }
        movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas
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

    public boolean showMoves(Spot curSpot){
        boolean movePossible = false;
        for(Spot[] spots : gameboard){
            for(Spot spot : spots){
                  if (curSpot.getPiece().acceptedMove(this, curSpot, spot)) {
                      movePossible = true;
                      spot.setAcceptedMoveColor();
                      possiblemoves.push(spot);
                  }
            }
        }
        return movePossible;
    }

    private void removeShowedMoves() {
        while (!possiblemoves.isEmpty()){
            possiblemoves.pop().removeAcceptedMoveColor();
        }
    }

    public void actionPerformed(ActionEvent e) {
        Spot presBut = (Spot)e.getSource();

        if(movBut == null){
            checkChosenSpot(presBut);
        }
        else {
            if(movBut.getPiece().acceptedMove(this, movBut.getSpot(), presBut)){
                move(presBut);
                checkForCheck(); //kolla ifall motsåndaren står i schack
                switchTurn();
                if(whiteTurn){
                    messageLabel.setText("Vit spelares tur");
                }
                else{
                    messageLabel.setText("Svart spelares tur");
                }
                removeShowedMoves();
            }
            else if(movBut.getPiece().isWhite() == presBut.getPiece().isWhite()){
                movBut = presBut;
                removeShowedMoves();
                checkChosenSpot(presBut);
            }
            else{
                messageLabel.setText("Välj en giltig plats");
            }
        }
    }

    private void checkForCheck() { //metod som kollar om någon pjäs hotar motståndarens kung
        Spot blackKingSpot = null;
        Spot whiteKingSpot = null;

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

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if(spot.getPiece() != null){
                    if (spot.getPiece().isWhite()){
                        if (spot.getPiece().acceptedMove(this, spot, blackKingSpot)) {
                            blackCheckLabel.setText("Svart kung i schack");
                        }
                    }
                    else{
                        if (spot.getPiece().acceptedMove(this, spot, whiteKingSpot)) {
                            whiteCheckLabel.setText("Vit kung i schack");
                        }
                    }
                }
            }
        }
    }

    private void checkChosenSpot(Spot presBut) {

        if(presBut.getPiece() != null){
            if (presBut.getPiece().isWhite() == whiteTurn) {
                if (showMoves(presBut)) {
                    messageLabel.setText("Välj ny plats");
                    movBut = presBut;
                }
            }
            else {
                messageLabel.setText("Pjäsen går inte att flytta");
            }
        }
        else {
            if (whiteTurn){
                messageLabel.setText("Välj din egen färg, Vit spelar!");
            }
            else{
                messageLabel.setText("Välj din egen färg, Svart spelar!");

            }
        }
    }

    private void switchTurn() {
        whiteTurn = !whiteTurn;
    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}

