package GUI;

public class Queen extends Piece {
    public Queen(boolean white)
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

        int stepLength = Math.abs(rowStart - rowEnd);


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
        else if (colStart > colEnd && rowStart > rowEnd) {   //south west
            for (int i = 1; i < stepLength; i++){
                int colPos = colStart - i;
                int rowPos = rowStart - i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else if (colStart > colEnd) { //north west
            for (int i = 1; i < stepLength; i++){
                int colPos = colStart - i;
                int rowPos = rowStart + i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else if (rowStart > rowEnd) {  // south east
            for (int i = 1; i < stepLength; i++){
                int colPos = colStart + i;
                int rowPos = rowStart - i;
                if (!(spotIsNull(board, colPos, rowPos))) {
                    return false;
                }
            }
        }
        else {                                      //north east
            for (int i = 1; i < stepLength; i++) {
                int xPos = colStart + i;
                int yPos = rowStart + i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean acceptedDirection(Spot start, Spot end) {
        int colDiff = Math.abs(start.getCol() - end.getCol());
        int rowDiff = Math.abs(start.getRow() - end.getRow());

        return colDiff == rowDiff || colDiff == 0 || rowDiff == 0;
    }

    private boolean spotIsNull(Board board, int col, int row) {
        return board.getBox(col, row).getPiece() == null;

    }
}
