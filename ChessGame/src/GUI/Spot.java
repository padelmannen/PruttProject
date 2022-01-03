package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Spot extends JButton {


    private Piece piece;
    private int x;
    private int y;

    public Spot(String pos, int row, int col){

        if(!Objects.equals(pos, "N")){
            setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + pos + ".png"))));
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

    public Piece getPiece() {
        return this.piece;
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
