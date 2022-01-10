package GUI;

import java.util.ArrayList;

public class FirstClick {
    private final ArrayList<Spot> possibleMoves = new ArrayList();
    private String message;
    private final boolean whiteTurn;
    private final Spot[][] curBoard;
    private final Spot curSpot;
    private boolean okClick = false;


    public FirstClick(Spot clickedSpot, Spot[][] GB, boolean WT) {
        curBoard = GB;
        whiteTurn = WT;
        curSpot = clickedSpot;
        checkChosenSpot();
    }

    private void checkChosenSpot() {
        Piece chosenPiece = curSpot.getPiece();
        if (chosenPiece != null) {
            if (chosenPiece.isWhite() == whiteTurn) {
                if (findPossibleMoves()) {
                    message = ("Välj ny plats");
                    okClick = true;
                }
                else{
                    message = ("Pjäsen har inga giltiga drag");
                }
            }
            else {
                if (whiteTurn) {
                    message = ("Välj din egen färg, Vit spelar!");
                } else {
                    message = ("Välj din egen färg, Svart spelar!");
                }
            }
        }
    }
    public boolean isOkClick(){
        return okClick;
    }

    private boolean findPossibleMoves() {
        boolean movePossible = false;
        for (Spot[] spots : curBoard) {
            for (Spot spot : spots) {
                if (curSpot.getPiece().acceptedMove(curBoard, curSpot, spot)) {
                    movePossible = true;
                    //spot.setAcceptedMoveColor();
                    possibleMoves.add(spot);
                }
            }
        }
        return movePossible;
    }
    public ArrayList<Spot> getPossibleMoves() {
        return possibleMoves;
    }

    public String getMessageLabel() {
        return message;
    }

}
