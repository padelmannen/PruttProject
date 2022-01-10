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

        setSpotColor();

        setOpaque(true);
        setBorderPainted(false);

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
        if (!Objects.equals(this.piece, null)) {
            java.net.URL imgURL = getClass().getResource("Icons/" + spot.color + spot.pieceName + ".png");
            if (imgURL != null) {
                Icon icon = new ImageIcon(imgURL);
                this.setIcon(icon);
            }
        }
    }

    public void updateSpot(Spot spot){
        this.spot = spot;
        this.piece = this.spot.getPiece();
        setSpotIcon();
    }

    public void setAcceptedMoveColor() {
        setBackground(new Color (63, 154, 33, 107));
    }

    public void removeAcceptedMoveColor(){
        setBackground(spotColor);
    }


}
