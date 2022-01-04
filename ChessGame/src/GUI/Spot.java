package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;



public class Spot extends JButton {

    private Piece piece;
    private String pieceName = null;
    private String color = null;
    private int row;
    private int col;
    private final Color spotColor;
    //private Icon icon;

    public Spot(String spotPiece, int row, int col){

        this.row = row;
        this.col = col;

        if(!Objects.equals(spotPiece, "N")){
            this.pieceName = spotPiece.substring(5);
            this.color = spotPiece.substring(0,5);
            Icon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + color + pieceName + ".png")));
            //this.icon = createImageIcon()
            this.setIcon(icon);
        }

        setFirstPiece();


        if((row+col) % 2 == 0){
            spotColor = Color.darkGray;
            setBackground(spotColor);
        }
        else{
            spotColor = Color.lightGray;
            setBackground(spotColor);
        }
        setOpaque(true);
        setBorderPainted(false);
    }

    //public void setIcon(Icon i){
    //    icon = i;
    //}

    public void setAcceptedMoveColor() {
        setBackground(Color.green);
        setOpaque(true);
        setBorderPainted(false);
    }

    public void removeAcceptedMoveColor(){
        setBackground(spotColor);
        setOpaque(true);
        setBorderPainted(false);
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

    public String getPieceName(){
        return pieceName;
    }

    public String getPieceColor(){
        return color;
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

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return this.col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    //public Icon getIcon(){
    //    return this.icon;
   // }

   // public void removeIcon() {
        //icon = null;
    //}
}
