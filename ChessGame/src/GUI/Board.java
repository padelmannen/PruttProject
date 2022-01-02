package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class Board extends JFrame implements ActionListener {
//    private final String[] colors = {"Black","White"};
//    private final Integer[] rows = {1,2,3,4,5,6,7,8};
//    private final String[] columns = {"a","b","c","d","e","f","g","h"};

    private JButton prevBut;
    private Color prevColor;

    public Board() throws IOException {
        setupBoard();
        setVisible(true);
        pack();
    }

    private void setupBoard() throws IOException {
        setLayout((new GridLayout(8, 8)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/startPositions.txt"));
        String line = startPos.readLine();
        int curRow = 0;
        int curCol = 0;
        while (line != null) {
            curRow++;
            String[] positions = line.split(" ");
            for(String pos : positions){
                curCol++;
                JButton curBut = new Spot(pos, curRow, curCol);
                curBut.addActionListener(this);
                add(curBut);
            }
            curCol = 0;
            line = startPos.readLine();
        }
    }

    public void move(JButton button){
        if(prevBut != null){
            prevBut.setBackground(prevColor);
        }
        prevBut = button; prevColor = button.getBackground();
        button.setBackground(Color.green);
    }

    public void actionPerformed(ActionEvent e) {
        Spot presBut = (Spot)e.getSource();
        move(presBut);

    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}
