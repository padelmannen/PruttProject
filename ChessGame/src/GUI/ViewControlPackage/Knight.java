package GUI.ViewControlPackage;



public class Knight extends Piece {
    public Knight(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Spot[][] board, Spot start,
                                Spot end)
    {

        if (end.getPiece() != null ) {
            if (end.getPiece().isWhite() == this.isWhite()) {
                return false;
            }
        }
        int colDiff = Math.abs(start.getCol() - end.getCol());
        int rowDiff = Math.abs(start.getRow() - end.getRow());
        return colDiff * rowDiff == 2;   //L-shaped move
    }
}
