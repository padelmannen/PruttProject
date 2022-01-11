package GUI.ViewControlPackage;

import java.util.Objects;


public class Spot {
    public Piece piece;
    //public String pieceName = null;
    //public String color = null;
    public final int row;
    public final int col;

    public Spot(String spotPiece, int row, int col) throws NullPointerException{

        this.row = row;
        this.col = col;

        if(!Objects.equals(spotPiece, "N")){
            String pieceName = spotPiece.substring(5);
            String color = spotPiece.substring(0,5);
            setFirstPiece(pieceName, color);
        }
    }

    public void setFirstPiece(String pieceName, String color){
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
