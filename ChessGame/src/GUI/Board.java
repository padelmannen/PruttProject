package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;

public class Board extends JFrame implements ActionListener {
//    private final String[] colors = {"Black","White"};
//    private final Integer[] rows = {1,2,3,4,5,6,7,8};
//    private final String[] columns = {"a","b","c","d","e","f","g","h"};

    private final Spot[][] gameboard;
    private Spot movBut = null;
    private int curRow;
    private int curCol;
    private Spot[] possibleMoves;
    private Color[] prevColor;

    public Board() throws IOException {
        gameboard = new Spot[8][8];
        setupBoard();
        setVisible(true);
        pack();
    }

    private void setupBoard() throws IOException {
        setLayout((new GridLayout(8, 8)));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BufferedReader startPos = new BufferedReader(new FileReader("ChessGame/src/GUI/startPositions.txt"));
        String line = startPos.readLine();
        int row = 0;
        int col = 0;
        while (line != null) {
            String[] positions = line.split(" ");
            for(String pos : positions){
                Spot curBut = new Spot(pos, row, col);
                curBut.addActionListener(this);
                add(curBut);
                gameboard[row][col] = curBut;
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
    }

    public Spot getBox(int row, int col){
        return gameboard[row][col];
    }

    public void move(Spot newSpot){
        movBut.setRow(newSpot.getRow());    // byt ut rad och kolumn till nya värden i spotobjektet
        movBut.setCol(newSpot.getCol());
        gameboard[movBut.getRow()][movBut.getCol()] = movBut;   // lägg in knappen som ska flyttas på ny plats
        movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas

    }


    public void showMoves(Spot curSpot){
//        for(Spot[] spots : gameboard){
//            for(Spot spot : spots){
//                  //kolla om man får gå hit
                    // om ja, ändra bakgrundsfärg till grön
//            }
//        }
    }

    public void actionPerformed(ActionEvent e) {
        Spot presBut = (Spot)e.getSource();

        if(movBut == null){
            if(presBut.getPieceName() != null){
                showMoves(presBut);
                movBut = presBut;
            }
        }
        else {
            move(presBut);
            movBut = null;
        }
    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}

