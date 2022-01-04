package GUI;

public class Bishop extends Piece {
    public Bishop(boolean white)
    {
        super(white);
    }

    @Override
    public boolean acceptedMove(Board board, Spot start,
                                Spot end) {
        // we can't move the piece to a spot that has
        // a piece of the same colour
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
        int colStart = start.getCol();
        int colEnd = end.getY();
        int rowStart = start.getX();
        int rowEnd = end.getX();

        int stepLength = Math.abs(colStart - colEnd);

        if (rowStart > rowEnd && colStart > colEnd) {   //south west
            for (int i = 1; i < stepLength; i++){
                int colPos = rowStart - i;
                int rowPos = colStart - i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else if (rowStart > rowEnd && colStart < colEnd) { //north west
            for (int i = 1; i < stepLength; i++){
                int colPos = rowStart - i;
                int rowPos = colStart + i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else if (rowStart < rowEnd && colStart > colEnd) {  // south east
            for (int i = 1; i < stepLength; i++){
                int colPos = rowStart + i;
                int rowPos = colStart - i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else {                                      //north east
            for (int i = 1; i < stepLength; i++) {
                int colPos = rowStart + i;
                int rowPos = colStart + i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean acceptedDirection(Spot start, Spot end) {
        int colDiff = Math.abs(start.getCol() - end.getCol());
        int rowDiff = Math.abs(start.getRow() - end.getRow());

        return (colDiff == rowDiff);
    }

    private boolean spotIsNull(Board board, int col, int row) {
        return board.getBox(col, row).getPiece() == null;

    }
}
