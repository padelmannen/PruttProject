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

    private final Spot[][] board;
    private Spot movBut;
    private int curRow;
    private int curCol;
    private Spot[] possibleMoves;
    private Color[] prevColor;

    public Board() throws IOException {
        board = new Spot[8][8];
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
                board[row][col] = curBut;
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
    }

    public void move(Spot newSpot){

        int newRow = newSpot.getX()/72;
        int newCol = newSpot.getY()/76;
        System.out.println("old: " + String.valueOf(curRow) + " " + String.valueOf(curCol));
        System.out.println("new: " + String.valueOf(newRow) + " " + String.valueOf(newCol));
        board[newRow][newCol] = new Spot(movBut.getPiece(), newRow, newCol);
        board[curRow][curCol] = new Spot("N", curRow, curCol);

        possibleMoves[0].setBackground(prevColor[0]);
        possibleMoves[1].setBackground(prevColor[1]);

    }


    public void showMoves(Spot button){
        curRow = button.getX()/76;
        curCol = button.getY()/72;
        System.out.println("pressed: " + String.valueOf(curRow) + " " + String.valueOf(curCol));
/*
        possibleMoves = new Spot[2];
        prevColor = new Color[2];

        curRow = button.getX()/76;
        curCol = button.getY()/72;
        possibleMoves[0] = board[curRow-1][curCol];
        possibleMoves[1] = board[curRow-2][curCol];
        prevColor[0] = board[curRow-1][curCol].getBackground();
        prevColor[1] = board[curRow-2][curCol].getBackground();

        for(Spot move : possibleMoves){
            move.setBackground(Color.green);
        }
*/
    }

    public void actionPerformed(ActionEvent e) {
        Spot presBut = (Spot)e.getSource();
        System.out.println(movBut);
        if(movBut == null){
            System.out.println("if");
            movBut = presBut;
            showMoves(movBut);
        }
        else {
            System.out.println("else");
            move(presBut);
        }

    }

    public static void main(String[] args) throws IOException {
        new Board();
    }
}
