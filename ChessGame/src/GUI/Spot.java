package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;



public class Spot extends JButton {

    private Piece piece;
    private String pieceName = null;
    private String color = null;
    private final int row;
    private final int col;
    private final Color spotColor;

    public Spot(String spotPiece, int row, int col){

        this.row = row;
        this.col = col;

        if(!Objects.equals(spotPiece, "N")){
            this.pieceName = spotPiece.substring(5);
            this.color = spotPiece.substring(0,5);
            Icon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + color + pieceName + ".png")));
            this.setIcon(icon);
        }

        setFirstPiece();

        if((row+col) % 2 == 0){
            spotColor = new Color (233,220,211);
            setBackground(spotColor);
        }
        else{
            spotColor = new Color (141,121,106);;
            setBackground(spotColor);
        }
        setOpaque(true);
        setBorderPainted(false);
    }

    public void setAcceptedMoveColor() {
        setBackground(new Color (63, 154, 33, 107));
    }

    public void removeAcceptedMoveColor(){
        setBackground(spotColor);
    }


    public void setFirstPiece(){
        if (Objects.equals(color, "White")) {
            if (Objects.equals(pieceName, "Pawn")) {
                piece = new Pawn(true);
            } else if (Objects.equals(pieceName, "King")) {
                piece = new King(true);
            } else if (Objects.equals(pieceName, "Queen")) {
                piece = new Queen(true);
            } else if (Objects.equals(pieceName, "Knight")) {
                piece = new Knight(true);
            } else if (Objects.equals(pieceName, "Bishop")) {
                piece = new Bishop(true);
            } else if (Objects.equals(pieceName, "Rook")) {
                piece = new Rook(true);
            }
        }
        else if (Objects.equals(color, "Black")) {
            if (Objects.equals(pieceName, "Pawn")) {
                piece = new Pawn(false);
            } else if (Objects.equals(pieceName, "King")) {
                piece = new King(false);
            } else if (Objects.equals(pieceName, "Queen")) {
                piece = new Queen(false);
            } else if (Objects.equals(pieceName, "Knight")) {
                piece = new Knight(false);
            } else if (Objects.equals(pieceName, "Bishop")) {
                piece = new Bishop(false);
            } else if (Objects.equals(pieceName, "Rook")) {
                piece = new Rook(false);
            }
        }
    }

    public Spot getSpot(){
        return this;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece p) {
        this.piece = p;
    }

    public int getRow() {
        return this.row;
    }


    public int getCol() {
        return this.col;
    }

}
