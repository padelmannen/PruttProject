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
        if (end.getPiece().isWhite() == this.isWhite()) {
            return false;
        }
        if (!(acceptedDirection(start, end))) {
            return false;
        }
        return clearPath(board, start, end);
    }

    private boolean clearPath(Board board, Spot start, Spot end) {
        int yStart = start.getY();
        int yEnd = end.getY();
        int xStart = start.getX();
        int xEnd = end.getX();

        int stepLength = Math.abs(yStart-yEnd);

        if (xStart>xEnd && yStart>yEnd) {   //south west
            for (int i = 1; i < stepLength; i++){
                int xPos = xStart - i;
                int yPos = yStart - i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        else if (xStart>xEnd && yStart<yEnd) { //north west
            for (int i = 1; i < stepLength; i++){
                int xPos = xStart - i;
                int yPos = yStart + i;
                if (!(spotIsNull(board, xPos, yPos))) {
                    return false;
                }
            }
        }
        else if (xStart<xEnd && yStart>yEnd) {  // south east
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
        int yDiff = Math.abs(start.getY() - end.getY());

        return (xDiff == yDiff);
    }

    private boolean spotIsNull(Board board, int x, int y) {
        return board.getBox(x, y) == null;

    }
}
