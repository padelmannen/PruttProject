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

    private Spot[][] gameboard;
    private Spot movBut;

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
                gameboard[row][col] = curBut;
                add(gameboard[row][col]);
                col++;
            }
            row++;
            col = 0;
            line = startPos.readLine();
        }
    }

    public void updateBoard(){
        for(Spot[] spots : gameboard) {
            for (Spot spot : spots) {
                Spot curBut = spot.getSpot();
//                System.out.println(String.valueOf(curBut.getPieceColor()) + " " +
//                        String.valueOf(curBut.getPieceName()) + " " +
//                        String.valueOf(curBut.getRow()) + " " +
//                        String.valueOf(curBut.getCol()));
                gameboard[curBut.getRow()][curBut.getCol()] = curBut;
                //add(gameboard[curBut.getRow()][curBut.getCol()]);
            }
        }
        /*for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                add(gameboard[row][col]);
            }
        }*/
        System.out.println("gameboard updated");
    }

    public Spot getBox(int row, int col){
        return gameboard[row][col];
    }

    public void move(Spot newSpot){

/*        int row = movBut.getRow();
        int col = movBut.getRow();*/
        //System.out.println("moved " + String.valueOf(movBut.getPieceColor()) + String.valueOf(movBut.getPieceName()) +
          //      " to " + String.valueOf(newSpot.getRow()) + " " + String.valueOf(newSpot.getCol()));

/*        movBut.setRow(newSpot.getRow());    // byt ut rad och kolumn till nya värden i spotobjektet
        movBut.setCol(newSpot.getCol());
        newSpot.setRow(row);
        newSpot.setCol(col);*/
        //newSpot.setIcon(movBut.getIcon());
        //newSpot.setPiece(movBut.getPiece());
        //movBut.setIcon(null);
        //movBut.setPiece(null);

        //gameboard[newSpot.getRow()][newSpot.getCol()] = movBut;   // lägg in knappen som ska flyttas på ny plats

        int oldRow = movBut.getRow();
        int oldCol = movBut.getCol();
        int newRow = newSpot.getRow();
        int newCol = newSpot.getCol();
        Icon moveIcon = movBut.getIcon();

//        gameboard[newSpot.getRow()][newSpot.getCol()].setIcon(movBut.getIcon());
//        gameboard[movBut.getRow()][movBut.getCol()].setIcon(null);
        gameboard[newRow][newCol].setIcon(moveIcon);
        gameboard[oldRow][oldCol].removeIcon();


        movBut = null;      // knappen är flyttad, ingen knapp väntar nu på att flyttas

        //updateBoard();
        //pack();
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
                //showMoves(presBut);
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

