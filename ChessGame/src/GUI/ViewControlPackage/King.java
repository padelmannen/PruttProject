package GUI.ViewControlPackage;


public class King extends Piece {
    public King(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Spot[][] board, Spot start, Spot end) {

        if (end.getPiece() != null ) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }
        int colDiff = Math.abs(start.getCol() - end.getCol());
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        return colDiff + rowDiff == 1 || colDiff == 1 & rowDiff == 1;
    }
}
