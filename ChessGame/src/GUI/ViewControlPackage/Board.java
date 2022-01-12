package GUI.ViewControlPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Board {

    public Spot[][] gameboard;
    public int size = 8;
    public Spot movBut;
    public ArrayList<Spot> possiblemoves = new ArrayList<>();
    public boolean whiteKingCheck = false;
    public boolean blackKingCheck = false;
    private Spot blackKingSpot = null;
    private Spot whiteKingSpot = null;
    public boolean whiteTurn = true;
    public boolean whiteWin = false;
    public boolean blackWin = false;
    public String gameStatus;
    public boolean clickOne = true;

    public Board() throws IOException, NullPointerException {
        gameboard = new Spot[size][size];
        setupBoard();
    }

    private void setupBoard() throws IOException, NullPointerException {

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/ViewControlPackage/startPositions.txt"));
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
        updateKingsPos();
    }

    public String getGameStatus(){
        return this.gameStatus;
    }

    public void move(Spot newSpot) {
        Spot oldSpot = gameboard[movBut.getRow()][movBut.getCol()];
        Piece movedPiece = movBut.getPiece();

        if (newSpot.getPiece() instanceof King) {
            handleVictory();
            newSpot.setPiece(movedPiece);
            oldSpot.setPiece(null);
        }
        else {

            newSpot.setPiece(movedPiece);
            oldSpot.setPiece(null);

            if (movedPiece instanceof Pawn) {
                handleMovedPawn(newSpot, movedPiece);  //för att hålla koll på när bonden ska förvandlas
            }
            if (movedPiece instanceof King) {
                updateKingsPos();  //för att hålla koll på när bonden ska förvandlas
            }

            switchTurn();
            checkForCheck();
            movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas
        }
    }

    private void handleVictory() {
        if (whiteTurn){
            whiteWin = true;
            gameStatus = "Vit vinner!";
        }
        else{
            blackWin = true;
            gameStatus = "Svart vinner!";
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


    private void removeShowedMoves() {
            possiblemoves.clear();
    }

    public void handleMoves(Spot presBut) {

        if(clickOne){
            FirstClick click = new FirstClick(presBut, gameboard, whiteTurn);
            handleFirstClick(click, presBut);
        }
        else {
            if (possiblemoves.contains(presBut)) {
                move(presBut);
                //gameStatus = null;
            }
            else{
                gameStatus = "Välj en giltig plats";

            }
            removeShowedMoves();
            clickOne = true;
        }
    }

    private void handleFirstClick(FirstClick click, Spot pressedSpot) {
        if(click.isOkClick()) {
            movBut = pressedSpot;
            possiblemoves = click.getPossibleMoves();   ///här sparas dom möjliga dragen för ett klick
            clickOne = false;
            gameStatus = null;
        }
        else{
            clickOne = true;
            gameStatus = click.getMessageLabel();
        }
    }

    private void checkForCheck() { //metod som kollar om någon pjäs hotar motståndarens kung

        blackKingCheck = false;
        whiteKingCheck = false;

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        for (Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null) {
                    if (spot.getPiece().isWhite()) {
                        if (spot.getPiece().acceptedMove(gameboard, spot, blackKingSpot)) {
                            blackKingCheck = true;
                        }
                    }
                    else {
                        if (spot.getPiece().acceptedMove(gameboard, spot, whiteKingSpot)) {
                            whiteKingCheck = true;
                        }
                    }
                }
            }
        }
    }
    private void updateKingsPos() {

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
    }

    private void switchTurn() {
        clickOne = true;
        whiteTurn = !whiteTurn;
    }

}

