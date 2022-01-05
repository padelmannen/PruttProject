package GUI.ViewControlPackage;



public class Knight extends Piece {
    public Knight(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Board board, Spot start,
                                Spot end)
    {
        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece() != null ) {
            if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
                return false;
            }
        }
        int colDiff = Math.abs(start.getCol() - end.getCol());
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        return colDiff * rowDiff == 2;
    }
}
