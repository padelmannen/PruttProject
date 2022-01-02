package GUI;

public class Pawn extends Piece {

    int moves;

    public Pawn(boolean white) {
        super(white);
        moves = 0;
    }

    @Override
    public boolean acceptedMove(Board board, Spot start,
                                Spot end) {
        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        if (moves == 0) {  //other rules when first move
            return acceptedFirstMove();
        }
        moves++;
        return true;
    }

    private boolean acceptedFirstMove() {
        int forwardSteps = Math.abs(start.getX() - end.getX());
        int sideSteps = Math.abs(start.getY() - end.getY());

        if (!(sideSteps == 0 && (forwardSteps == 1 || forwardSteps == 2))) {  //one OR two steps forward, not allowed to take!
            return false;
        }
        if (forwardSteps == 2) { //not allowed to jump over piece from start
            if (!(this.isWhite() && (board.getBox(start.getY() + 1, start.getY()) == null))) {  //for white pieces
                return false;
            }
            if (!(!(this.isWhite()) && (board.getBox(start.getY() - 1, start.getY()) == null))) {  //for blackPieces
                return false;
            }
        }
        moves++;
        return true;
    }
}