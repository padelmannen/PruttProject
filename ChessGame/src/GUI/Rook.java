package GUI;

public class Rook extends Piece {
    public Rook(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Board board, Spot start,
                                Spot end) {

        if (end.getPiece() != null ) {
            if (end.getPiece().isWhite() == this.isWhite()) {    //cannot go to square with same piece colour
                return false;
            }
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
            for (int rowPos = rowMin+1; rowPos < rowMax; rowPos++) {
                if (!(spotIsNull(board, rowPos, colStart))) {
                    return false;
                }
            }
        }
        if (rowMin == rowMax){  //vertical
            for (int colPos = colMin+1; colPos < colMax; colPos++) {
                if (!(spotIsNull(board, rowStart, colPos))) {
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

    private boolean spotIsNull(Board board, int row, int col) {
        return board.getBox(row, col).getPiece() == null;

    }
}