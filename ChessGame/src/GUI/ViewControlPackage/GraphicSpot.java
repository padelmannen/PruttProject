package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GraphicSpot extends JButton {
    int row;
    int col;
    Spot spot;
    Color spotColor;
    Piece piece;

    GraphicSpot(Spot spot) {
        this.spot = spot;
        this.piece = spot.getPiece();
        this.row = spot.getRow();
        this.col = spot.getCol();

        setOpaque(true);
        setBorderPainted(false);
        setSpotColor();
        setSpotIcon();
    }

    private void setSpotColor() {
        if ((row + col) % 2 == 0) {
            spotColor = new Color(233, 220, 211);
            setBackground(spotColor);
        } else {
            spotColor = new Color(141, 121, 106);
            setBackground(spotColor);
        }
    }

    private void setSpotIcon() {
        //vi skulle även kunna lagra namn och färg som instansvariabler/ha get-funktioner i pieceklasserna
        if (!Objects.equals(this.piece, null)) {
            String pieceName = piece.getClass().getName().split("\\.")[2];
            String pieceColor = "Black";
            if (piece.getPiece().isWhite()){
                pieceColor = "White";
            }

            java.net.URL imgURL = getClass().getResource("Icons/" + pieceColor + pieceName + ".png");
            if (imgURL != null) {
                Icon icon = new ImageIcon(imgURL);
                this.setIcon(icon);
            }

        }
        else{
            this.setIcon(null);
        }
    }

    public void updateSpot(){
        this.piece = spot.getPiece();
        setSpotIcon();
    }

    public void setAcceptedMoveColor() {
        setBackground(new Color (63, 154, 33, 107));
    }

    public void removeAcceptedMoveColor(){
        setBackground(spotColor);
    }

}
