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
        if (end.getPiece() != null ) {
            if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
                return false;
            }
        }

        if (moves == 0) {  //other rules apply when first move of pawn
            return acceptedFirstMove(board, start, end);
        }

        int forwardSteps = start.getRow() - end.getRow();
        int sideSteps = Math.abs(start.getCol() - end.getCol());

        if (this.isWhite()) {
            if (!(forwardSteps == 1)) {  //must go one step forward
                return false;
            }
            if (sideSteps == 0) {
                //not allowed to take straight forward
                return (spotIsNull(board, end.getRow(), end.getCol()));
            }
            if (sideSteps == 1) {
                return !(spotIsNull(board, end.getRow(), end.getCol()));
            }
            return false;
        }
        else{  //for black pawns
            if (!(forwardSteps == -1)) {  //must go one step forward
                return false;
            }
            if (sideSteps == 0) {
                //not allowed to take straight forward
                return (spotIsNull(board, end.getRow(), end.getCol()));
            }
            if (sideSteps == 1) {
                return !(spotIsNull(board, end.getRow(), end.getCol()));
            }
            return false;
        }
        //return true;
    }

    public void increasePawnMoves(){
        moves++;
    }


    private boolean acceptedFirstMove(Board board, Spot start, Spot end) {
        int sideSteps = Math.abs(start.getCol() - end.getCol());
        int forwardSteps = Math.abs(start.getRow() - end.getRow());


        if (!(sideSteps == 0 && (forwardSteps == 1 || forwardSteps == 2))) {  //one OR two steps forward, not allowed to take!
            return false;
        }
        if (forwardSteps == 2) { //not allowed to jump over piece from start
            if (this.isWhite()) {
                if (!(spotIsNull(board, start.getRow() - 1, start.getCol()))) {
                    return false;
                }
            }
            else{
                if (!(spotIsNull(board, start.getRow() + 1, start.getCol()))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean spotIsNull(Board board, int row, int col) {
        return board.getBox(row, col).getPiece() == null;
    }

    public int getNumOfMoves(){
        return moves;
    }


}