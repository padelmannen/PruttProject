package GUI;

public class King extends Piece {
    public King(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Board board, Spot start, Spot end) {
            // we can't move the piece to a Spot that
            // has a piece of the same color
            if (end.getPiece() != null ) {
                if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
                    return false;
                }
            }

            int col = Math.abs(start.getCol() - end.getCol());
            int row = Math.abs(start.getRow() - end.getRow());
            if(col + row == 1 || col == 1 & row == 1) {
                return true;
            }
            return false;
    }
}
