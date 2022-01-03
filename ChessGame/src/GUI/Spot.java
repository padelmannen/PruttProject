package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Spot extends JButton {

    private final String type;

    public Spot(String type, int row, int col){

        this.type = type;

        if(!Objects.equals(type, "N")){
            setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource("Icons/" + type + ".png"))));
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

    public String getType(){
        return type;
    }



}
