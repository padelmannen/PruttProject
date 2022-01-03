package GUI;

public class Pawn extends Piece {

    int moves;

    public Pawn(boolean white) {
        super(white);
        moves = 0;
    }

    @Override
    public boolean acceptedMove(Board board, Spot start, Spot end) {

        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
            return false;
        }

        if (moves == 0) {  //other rules apply when first move of pawn
            return acceptedFirstMove(board, start, end);
        }

        int forwardSteps = start.getCol() - end.getCol();
        int sideSteps = Math.abs(start.getRow() - end.getRow());

        if (this.isWhite()) {
            if (!(forwardSteps == -1)) {  //must go one step forward
                return false;
            }
            if (sideSteps == 0) {
                if (!(spotIsNull(board, end.getCol(), end.getRow()))) {  //not allowed to take straight forward
                    return false;
                }
            }
            else if (sideSteps > 1){
                return false;
            }
        }
        else{  //for black pawns
            if (!(forwardSteps == 1)) {   //must go one step forward
                return false;
            }
            if (sideSteps == 0){
                if (!(spotIsNull(board, end.getCol(), end.getCol()))) {   //not allowed to take straight forward
                    return false;
                }
            }
            else if (sideSteps > 1){
                return false;
            }
        }
        moves++;
        return true;
    }


    private boolean acceptedFirstMove(Board board, Spot start, Spot end) {
        int sideSteps = Math.abs(start.getCol() - end.getCol());
        int forwardSteps = Math.abs(start.getRow() - end.getRow());


        if (!(sideSteps == 0 && (forwardSteps == 1 || forwardSteps == 2))) {  //one OR two steps forward, not allowed to take!
            return false;
        }
        if (forwardSteps == 2) { //not allowed to jump over piece from start
            if (this.isWhite()) {
                if (!(spotIsNull(board, start.getCol(), start.getRow() - 1))) {
                    return false;
                }
            }
            else{
                if (!(spotIsNull(board, start.getCol(), start.getRow() + 1))) {
                    return false;
                }
            }
        }
        moves++;
        if (forwardSteps == 2){
            moves ++;
        }
        return true;
    }

    private boolean spotIsNull(Board board, int col, int row) {
        return board.getBox(col, row).getPiece() == null;
    }

    public int getNumOfMoves(){
        return moves;
    }


}