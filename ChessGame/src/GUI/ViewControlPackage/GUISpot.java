package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GUISpot extends JButton {
    private final int row;
    private final int col;
    private final Spot spot;
    private Color spotColor;
    private Piece piece;

    GUISpot(Spot boardSpot) {
        spot = boardSpot;
        piece = spot.getPiece();
        row = spot.getRow();
        col = spot.getCol();

        setOpaque(true);
        setBorderPainted(false);
        setSpotColor();
        setSpotIcon();
    }

    private void setSpotColor() {
        if ((row + col) % 2 == 0) {
            spotColor = new Color(233, 220, 211);
        } else {
            spotColor = new Color(141, 121, 106);
        }
        setBackground(spotColor);
    }

    private void setSpotIcon() {
        if (!Objects.equals(piece, null)) {
            String pieceName = piece.getClass().getName().split("\\.")[2];
            String pieceColor;
            if (piece.getPiece().isWhite()){
                pieceColor = "White";
            }
            else{
                pieceColor = "Black";
            }

            java.net.URL imgURL = getClass().getResource("Icons/" + pieceColor + pieceName + ".png");
            if (imgURL != null) {
                Icon icon = new ImageIcon(imgURL);
                setIcon(icon);
            }
        }
        else{
            setIcon(null);
        }
    }

    public void updateSpot(){
        piece = spot.getPiece();
        setSpotIcon();
    }

    public void setAcceptedMoveColor() {
        setBackground(new Color (63, 154, 33, 107));
    }

    public void removeAcceptedMoveColor(){
        setBackground(spotColor);
    }

    public Spot getSpot() {
        return spot;
    }
}
