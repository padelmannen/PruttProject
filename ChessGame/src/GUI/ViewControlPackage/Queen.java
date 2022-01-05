package GUI.ViewControlPackage;


public class Queen extends Piece {
    public Queen(boolean white)
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

        int stepLength = Math.abs(rowStart - rowEnd);

        if (colMin == colMax) {   //horizontal
            for (int rowPos = rowMin+1; rowPos < rowMax; rowPos++) {
                if (!(spotIsNull(board, rowPos, colStart))) {
                    return false;
                }
            }
        }
        else if (rowMin == rowMax){  //vertical
            for (int colPos = colMin+1; colPos < colMax; colPos++) {
                if (!(spotIsNull(board, rowStart, colPos))) {
                    return false;
                }
            }
        }
        else if (rowStart > rowEnd && colStart > colEnd) {   //south west
            for (int i = 1; i < stepLength; i++){
                int rowPos = rowStart - i;
                int colPos = colStart - i;
                if (!(spotIsNull(board, rowPos, colPos))) {
                    return false;
                }
            }
        }
        else if (rowStart > rowEnd) { //north west
            for (int i = 1; i < stepLength; i++){
                int rowPos = rowStart - i;
                int colPos = colStart + i;
                if (!(spotIsNull(board, rowPos, colPos))) {
                    return false;
                }
            }
        }
        else if (colStart > colEnd) {  // south east
            for (int i = 1; i < stepLength; i++){
                int rowPos = rowStart + i;
                int colPos = colStart - i;
                if (!(spotIsNull(board, rowPos, colPos))) {
                    return false;
                }
            }
        }
        else {                                      //north east
            for (int i = 1; i < stepLength; i++) {
                int rowPos = rowStart + i;
                int colPos = colStart + i;
                if (!(spotIsNull(board, rowPos, colPos))) {
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

    private boolean spotIsNull(Board board, int row, int col) {
        return board.getBox(row, col).getPiece() == null;

    }
}
