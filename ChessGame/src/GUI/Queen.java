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
        int yStart = start.getRow();
        int yEnd = end.getRow();
        int xStart = start.getX();
        int xEnd = end.getX();

        int xMin = Math.min(xStart, xEnd);
        int xMax = Math.max(xStart, xEnd);
        int yMin = Math.min(yStart, yEnd);
        int yMax = Math.max(yStart, yEnd);

        int stepLength = Math.abs(yStart-yEnd);


        if (xMin == xMax) {   //horizontal
            for (int yPos = yMin; yPos < yMax; yPos++) {
                if (!(spotIsNull(board, xStart, yPos))) {
                    return false;
                }
            }
        }
        else if (yMin == yMax){  //vertical
            for (int xPos = xMin; xPos < xMax; xPos++) {
                if (!(spotIsNull(board, xPos, yStart))) {
                    return false;
                }
            }
        }
        else if (xStart>xEnd && yStart>yEnd) {   //south west
            for (int i = 1; i < stepLength; i++){
                int xPos = xStart - i;
                int yPos = yStart - i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        else if (xStart>xEnd) { //north west
            for (int i = 1; i < stepLength; i++){
                int xPos = xStart - i;
                int yPos = yStart + i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        else if (yStart>yEnd) {  // south east
            for (int i = 1; i < stepLength; i++){
                int xPos = xStart + i;
                int yPos = yStart - i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        else {                                      //north east
            for (int i = 1; i < stepLength; i++) {
                int xPos = xStart + i;
                int yPos = yStart + i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean acceptedDirection(Spot start, Spot end) {
        int xDiff = Math.abs(start.getX() - end.getX());
        int yDiff = Math.abs(start.getRow() - end.getRow());

        return xDiff == yDiff || xDiff == 0 || yDiff == 0;
    }

    private boolean spotIsNull(Board board, int col, int row) {
        return board.getBox(col, row).getPiece() == null;

    }
}
