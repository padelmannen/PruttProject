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
        if (!(clearPath(board, start, end))) {
            return false;
        }
        return true;
    }

    private boolean clearPath(Board board, Spot start, Spot end) {
        int yStart = start.getY();
        int yEnd = end.getY();
        int xStart = start.getX();
        int xEnd = end.getX();


        int xMin = Math.min(xStart, xEnd);
        int xMax = Math.max(xStart, xEnd);
        int yMin = Math.min(yStart, yEnd);
        int yMax = Math.max(yStart, yEnd);


        if (xMin == xMax) {
            for (int yPos = yMin; yPos < yMax; yPos++) {
                if (!(spotIsNull(board, xStart, yPos))) {
                    return false;
                }
            }
        }
        else {
            for (int xPos = xMin; xPos < xMax; xPos++) {
                if (!(spotIsNull(board, xPos, yStart))) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean acceptedDirection(Spot start, Spot end) {
        int xDiff = Math.abs(start.getX() - end.getX());
        int yDiff = Math.abs(start.getY() - end.getY());

        return (xDiff == 0 && yDiff != 0) || (xDiff != 0 && yDiff == 0);
    }

    private boolean spotIsNull(Board board, int x, int y) {
        return board.getBox(x, y) == null;

    }
}