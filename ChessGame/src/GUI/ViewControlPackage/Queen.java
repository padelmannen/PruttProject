package GUI.ViewControlPackage;


public class Queen extends Piece {
    private final Piece provBishop;
    private final Piece provRook;

    public Queen(boolean white) {
        super(white);
        provBishop = new Bishop(white);
        provRook = new Rook(white);
    }

    @Override
    public boolean acceptedMove(Spot[][] board, Spot start,
                                Spot end) {

        return (provBishop.acceptedMove(board, start, end) || provRook.acceptedMove(board, start, end));
    }
}
