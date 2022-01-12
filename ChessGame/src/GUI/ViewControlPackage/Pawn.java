package GUI.ViewControlPackage;


public class Pawn extends Piece {

    private int moves;

    public Pawn(boolean white) {
        super(white);
        moves = 0;
    }

    @Override
    public boolean acceptedMove(Spot[][] board, Spot start, Spot end) {

        if (end.getPiece() != null) {
            if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
                return false;
            }
        }
        if (backwardMove(start, end)) {
            return false;
        }

        int forwardSteps = Math.abs(start.getRow() - end.getRow());
        int sideSteps = Math.abs(start.getCol() - end.getCol());

        //Gör så att pawn kan ta om det står en pjäs snett emot vid start
        if (end.getPiece() != null) {
            return sideSteps == 1 && forwardSteps == 1;  //return true om motståndare diagonalt
        }
        if (!(sideSteps == 0)) {
            return false;
        }

        if (!(forwardSteps == 1)) {  //one OR two steps forward, not allowed to take!
            if (moves == 0) {  //other rules apply when first move of pawn
                return acceptedFirstMove(board, start, end);
            }
            return false;
        }
        return true;
    }

    private boolean backwardMove(Spot start, Spot end) {
        if (this.isWhite()){
            return start.getRow()<end.getRow();
        }
        else{
            return start.getRow()>end.getRow();
        }
    }

    public void increasePawnMoves(int stepLength){
        moves++;
        if (stepLength == 2){
            moves++;
        }
    }


    private boolean acceptedFirstMove(Spot[][] board, Spot start, Spot end) {
        int forwardSteps = Math.abs(start.getRow() - end.getRow());
        if (forwardSteps == 2) { //not allowed to jump over piece from start
            if (this.isWhite()) {
                return spotIsNull(board, start.getRow() - 1, start.getCol());
            }
            else{
                return spotIsNull(board, start.getRow() + 1, start.getCol());
            }
        }
        return false;
    }

    private boolean spotIsNull(Spot[][] board, int row, int col) {

        return board[row][col].getPiece() == null;
    }

    public int getNumOfMoves(){
        return moves;
    }
}