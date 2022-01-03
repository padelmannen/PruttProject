package GUI;

public class Rook extends Piece {
    public Rook(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Board board, Spot start,
                                Spot end) {
        // we can't move the piece to a spot that has
        // a piece of the same colour
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        if (!(acceptedDirection(start, end))) {
            return false;
        }
        return clearPath(board, start, end);
    }

    private boolean clearPath(Board board, Spot start, Spot end) {
        int rowStart = start.getRow();
        int rowEnd = end.getRow();
        int colStart = start.getCol();
        int colEnd = end.getCol();

        int colMin = Math.min(colStart, colEnd);
        int colMax = Math.max(colStart, colEnd);
        int rowMin = Math.min(rowStart, rowEnd);
        int rowMax = Math.max(rowStart, rowEnd);

        if (colMin == colMax) {   //horizontal
            for (int rowPos = rowMin; rowPos < rowMax; rowPos++) {
                if (!(spotIsNull(board, colStart, rowPos))) {
                    return false;
                }
            }
        }
        else if (rowMin == rowMax){  //vertical
            for (int colPos = colMin; colPos < colMax; colPos++) {
                if (!(spotIsNull(board, colPos, rowStart))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean acceptedDirection(Spot start, Spot end) {
        int xDiff = Math.abs(start.getX() - end.getX());
        int yDiff = Math.abs(start.getY() - end.getY());

        return xDiff == 0 || yDiff == 0;
    }

    private boolean spotIsNull(Board board, int x, int y) {
        return board.getBox(x, y).getPiece() == null;

    }
}