package GUI.ViewControlPackage;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Board {

    public Spot[][] gameboard;
    public int size = 8;
    public Spot movBut;
    public Stack <Spot> possiblemoves = new Stack<>();
    public boolean whiteKingCheck = false;
    public boolean blackKingCheck = false;
    public boolean whiteTurn = true;
    public boolean whiteWin = false;
    public boolean blackWin = false;
    public String gameStatus = "STATUS";

    public Board() throws IOException, NullPointerException {
        gameboard = new Spot[size][size];
        setupBoard();
    }

    private void setupBoard() throws IOException, NullPointerException {

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/startPositions.txt"));
        String line = startPos.readLine();
        int row = 0;
        int col = 0;
        while (line != null) {
            String[] positions = line.split(" ");
            for(String pos : positions){
                Spot spot = new Spot(pos, row, col);
                gameboard[row][col] = spot;
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
    }


    public String getGameStatus(){
        return this.gameStatus;
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

        newSpot.setPiece(movedPiece);

        if (movedPiece instanceof Pawn) {
            handleMovedPawn(newSpot, movedPiece);  //för att hålla koll på när bonden ska förvandlas
        }
        movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas
    }

    private void handleVictory() {
        if (whiteTurn){
            whiteWin = true;
        }
        else{
            blackWin = true;
        }
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
        }
        else {
            spot.setPiece(new Queen(false));
        }
    }

    public boolean showMoves(Spot curSpot){
        boolean movePossible = false;
        for(Spot[] spots : gameboard){
            for(Spot spot : spots){
                if (curSpot.getPiece().acceptedMove(this, curSpot, spot)) {
                    movePossible = true;
                    possiblemoves.push(spot);
                }
            }
        }
        return movePossible;
    }

    private void removeShowedMoves() {
            possiblemoves.clear();
    }

    public void actionPerformed(Spot presBut) {

        if (movBut == null) {
            checkChosenSpot(presBut);
        }
        else {
            if (movBut.getPiece().acceptedMove(this, movBut.getSpot(), presBut)) {
                move(presBut);
                checkForCheck(); //kolla ifall motsåndaren står i schack
                switchTurn();
                gameStatus = "bytt tur";
                removeShowedMoves();
            }
            else if (movBut.getPiece().isWhite() == presBut.getPiece().isWhite()) {
                movBut = presBut;
                removeShowedMoves();
                checkChosenSpot(presBut);
            }
        }
    }

    private void checkForCheck() { //metod som kollar om någon pjäs hotar motståndarens kung
        Spot blackKingSpot = null;
        Spot whiteKingSpot = null;
        blackKingCheck = false;
        whiteKingCheck = false;

        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null && spot.getPiece() instanceof King) {
                    if (spot.getPiece().isWhite()) {
                        whiteKingSpot = spot;
                    }
                    else {
                        blackKingSpot = spot;
                    }
                }
            }
        }

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null) {
                    if (spot.getPiece().isWhite()) {
                        if (spot.getPiece().acceptedMove(this, spot, blackKingSpot)) {
                            blackKingCheck = true;
                        }
                    }
                    else {
                        if (spot.getPiece().acceptedMove(this, spot, whiteKingSpot)) {
                            whiteKingCheck = true;
                        }
                    }
                }
            }
        }
    }

    private void checkChosenSpot(Spot presBut) {
        if (presBut.getPiece() != null) {
            if (presBut.getPiece().isWhite() == whiteTurn) {
                if (showMoves(presBut)) {
                    movBut = presBut;
                }
            }
        }
    }

    private void switchTurn() {
        whiteTurn = !whiteTurn;
    }

}
