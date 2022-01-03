package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Spot extends JButton {

    private final String piece;
    private final String color;

    public Spot(String spotPiece, int row, int col){

        if(!Objects.equals(spotPiece, "N")){
            this.piece = spotPiece.substring(5);
            this.color = spotPiece.substring(0,5);
            System.out.println(piece);
            System.out.println(color);
            setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + color + piece + ".png"))));
        }
        else{
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

    public String getPiece(){
        return piece;
    }


    public void setPiece(Piece p)
    {
        this.piece = p;
    }

    public int getX()
    {
        return this.x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return this.y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}
