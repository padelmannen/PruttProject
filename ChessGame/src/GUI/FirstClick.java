package GUI;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Stack;

public class FirstClick {
    private Spot chosenSpot;
    private Spot whiteKingSpot;
    private Spot blackKingSpot;
    private boolean whiteTurn;
    private Spot[][] curGameBoard;
    private ArrayList<Spot> possibleMoves = new ArrayList<>();
    private JLabel messageLabel = new JLabel();
    private boolean whiteKingCheck;
    private boolean blackKingCheck;


    public FirstClick(Spot clickedSpot, boolean white, Spot WK, Spot BK, Spot[][] GB, boolean WKcheck, boolean BKcheck) {
        chosenSpot = clickedSpot;
        whiteTurn = white;
        whiteKingSpot = WK;
        blackKingSpot = BK;
        curGameBoard = GB;
        whiteKingCheck = WKcheck;
        blackKingCheck = BKcheck;
        checkChosenSpot();

    }


    private void checkChosenSpot (){

        if (chosenSpot.getPiece() != null) {  //om vi klickat på en pjäs
            if (chosenSpot.getPiece().isWhite() == whiteTurn) {  //om vi klickar på rätt färg
                if (findPossibleMoves(chosenSpot)) {  //om det finns minst ett möjligt drag för vald pjäs
                    messageLabel.setText("Välj ny position");
                }
                else {
                    messageLabel.setText("Pjäsen går inte att flytta");
                }
            } else {
                setWrongPieceMessage();
            }
        } else {
            setWrongPieceMessage();
        }
    }

    public ArrayList<Spot> getPossibleMoves(){
        return possibleMoves;
    }

    public JLabel getMessageLabel(){
        return messageLabel;
    }

    public Spot getChosenSpot() {
        return chosenSpot;
    }

    private boolean findPossibleMoves(Spot curSpot){
        Piece chosenPiece = curSpot.getPiece();
        boolean movePossible = false;

        for (Spot[] spots : curGameBoard) {
            for (Spot newSpot : spots) {
                if (chosenPiece.acceptedMove(curGameBoard, curSpot, newSpot)) {
                    if (notOwnCheckMove(curSpot, newSpot)) {
                        movePossible = true;
                        newSpot.setAcceptedMoveColor();
                        possibleMoves.add(newSpot);
                    }
                }
            }
        }
        return movePossible;
    }

    private boolean notOwnCheckMove (Spot curSpot, Spot newSpot){
        Piece movedPiece = curSpot.getPiece();
        Piece newSpotPiece = newSpot.getPiece();

        curSpot.setPiece(null);
        newSpot.setPiece(movedPiece);


        if (whiteTurn) {
            checkForCheck();
            if (whiteKingCheck) {
                whiteKingCheck = false;
                curSpot.setPiece(movedPiece);
                newSpot.setPiece(newSpotPiece);
                return false;
            }
        } else {
            checkForCheck();
            if (blackKingCheck) {
                blackKingCheck = false;
                curSpot.setPiece(movedPiece);
                newSpot.setPiece(newSpotPiece);
                return false;
            }
        }
        curSpot.setPiece(movedPiece);
        newSpot.setPiece(newSpotPiece);
        return true;
    }

    private void checkForCheck () { //metod som kollar om någon pjäs hotar motståndarens kung
        //updateKingsPos();

        //kollar just nu om båda är i schack eftersom egna drag kan medföra att ens kung hamnar i schack
        blackKingCheck = false;
        whiteKingCheck = false;
        for (Spot[] spots : curGameBoard) {
            for (Spot spot : spots) {
                if (spot.getPiece() != null) {
                    if (spot.getPiece().isWhite()) {
                        if (spot.getPiece().acceptedMove(curGameBoard, spot, blackKingSpot)) {
                            //blackCheckLabel.setText("Svart kung i schack");
                            blackKingCheck = true;
                        }
                    } else {
                        if (spot.getPiece().acceptedMove(curGameBoard, spot, whiteKingSpot)) {
                            //whiteCheckLabel.setText("Vit kung i schack");
                            whiteKingCheck = true;
                        }
                    }
                }
            }
        }
    }

    private void setWrongPieceMessage () {
        if (whiteTurn) {
            messageLabel.setText("Välj din egen färg, Vit spelar!");
        } else {
            messageLabel.setText("Välj din egen färg, Svart spelar!");
        }
    }
}
