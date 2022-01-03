package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Spot extends JButton {

    private Piece piece;
    private String pieceName;
    private String color;
    private int row;
    private int col;

    public Spot(String spotPiece, int row, int col){

        this.row = row;
        this.col = col;

        if(!Objects.equals(spotPiece, "N")){
            this.pieceName = spotPiece.substring(5);
            this.piece = null;
            this.color = spotPiece.substring(0,5);
            setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + color + pieceName + ".png"))));
        }

        else{
            this.pieceName = null;
            this.piece = null;
            this.color = null;
        }
        if((row+col) % 2 == 0){
            setBackground(Color.darkGray);
        }
        else{
            setBackground(Color.lightGray);
        }
        setOpaque(true);
        setBorderPainted(false);
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
}
