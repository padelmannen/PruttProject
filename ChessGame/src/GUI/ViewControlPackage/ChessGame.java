package GUI.ViewControlPackage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ChessGame {

    private final Spot[][] chessBoard;
    private final int size = 8;
    private ArrayList<Spot> possibleMoves = new ArrayList<>();
    private boolean whiteKingCheck = false;
    private boolean blackKingCheck = false;
    private Spot blackKingSpot = null;
    private Spot whiteKingSpot = null;
    private boolean whiteTurn = true;
    private boolean whiteWin = false;
    private boolean blackWin = false;
    private String gameStatus;
    private boolean clickOne = true;
    private FirstClick firstClick;
    private Spot newSpot;

    public ChessGame() throws IOException {
        chessBoard = new Spot[size][size];
        setupBoard();
    }

    private void setupBoard() throws IOException {

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/ViewControlPackage/startPositions.txt"));
        String line = startPos.readLine();
        int row = 0;
        int col = 0;
        while (line != null) {
            String[] pieces = line.split(" ");
            for(String piece : pieces){
                Spot spot = new Spot(piece, row, col);
                chessBoard[row][col] = spot;
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
        updateKingsPos();
    }

    public void handleMoves(Spot presBut) {

        if(clickOne){
            firstClick = new FirstClick(presBut, chessBoard, whiteTurn);
            handleFirstClick();
        }
        else {
            if (possibleMoves.contains(presBut)) {
                newSpot = presBut;
                makeMove();
                //gameStatus = "Välj pjäs";
            }
            else{
                gameStatus = "Välj en giltig plats";

            }
            possibleMoves.clear();
            clickOne = true;
        }
    }  //kallas på vid knapptryckning

    private void makeMove() {

        Spot oldSpot = chessBoard[firstClick.getClickedSpot().getRow()][firstClick.getClickedSpot().getCol()];
        Piece movedPiece = firstClick.getClickedSpot().getPiece();
        Piece takenSpot = newSpot.getPiece();

        newSpot.setPiece(movedPiece);
        oldSpot.setPiece(null);

        if (takenSpot instanceof King) {
            handleVictory();
        }
        else {
            if (movedPiece instanceof Pawn) {
                handleMovedPawn((Pawn) movedPiece);  //för att hålla koll på när bonden ska förvandlas
            }
            else if (movedPiece instanceof King) {
                updateKingsPos();  //för att hålla koll på när bonden ska förvandlas
            }

            switchTurn();
            checkForCheck();
        }
    }

    private void checkForCheck() { //metod som kollar om någon pjäs hotar motståndarens kung

        blackKingCheck = false;
        whiteKingCheck = false;

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        for (Spot[] spots : chessBoard) {
            for (Spot spot : spots) {

                if (spot.getPiece() != null) {

                    if (spot.getPiece().isWhite()) {
                        if (spot.getPiece().acceptedMove(chessBoard, spot, blackKingSpot)) {
                            blackKingCheck = true;
                        }
                    }
                    else {
                        if (spot.getPiece().acceptedMove(chessBoard, spot, whiteKingSpot)) {
                            whiteKingCheck = true;
                        }
                    }
                }
            }
        }
    }

    private void handleFirstClick() {
        if(firstClick.isOkClick()) {
            possibleMoves = firstClick.getPossibleMoves();   ///här sparas dom möjliga dragen för ett klick
            clickOne = false;
            gameStatus = "Välj ny plats";
        }
        else{
            clickOne = true;
            gameStatus = firstClick.getMessageLabel();
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

    private void handleMovedPawn(Pawn pawn) {
        int stepLength = Math.abs(newSpot.getRow() - firstClick.getClickedSpot().getRow());
        pawn.increasePawnMoves(stepLength);  //öka antalet steg för bonden
        if (pawn.getNumOfMoves() == 6) {       //om bonden nått över till andra sidan
            transformPawn(pawn);               //förvandla bonden
        }
    }

    private void transformPawn(Pawn pawn) {
        if (pawn.isWhite()){
            newSpot.setPiece(new Queen(true));
        }
        else {
            newSpot.setPiece(new Queen(false));
        }
    }

    private void updateKingsPos() {

        for (Spot[] spots : chessBoard) {
            for (Spot spot : spots) {
                if (spot.getPiece() instanceof King) {
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

    public String getGameStatus(){
        return this.gameStatus;
    }

    public int getSize() {
        return size;
    }

    public Spot[][] getChessBoard() {
        return chessBoard;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean isBlackKingChecked() {
        return blackKingCheck;
    }

    public boolean isWhiteKingChecked() {
        return whiteKingCheck;
    }

    public boolean isClickOne() {
        return clickOne;
    }

    public ArrayList<Spot> getPossibleMoves() {
        return possibleMoves;
    }

    public boolean isWon() {
        return whiteWin || blackWin;
    }
}

