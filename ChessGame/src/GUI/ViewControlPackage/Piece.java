package GUI.ViewControlPackage;


public abstract class Piece {

    private boolean white = false;

    Piece(boolean white)
    {
        this.setWhite(white);
    }

    boolean isWhite()
    {
        return this.white;  //true or false
    }

    void setWhite(boolean white)
    {
        this.white = white;
    }

    abstract boolean acceptedMove(Spot[][] board,
                                         Spot start, Spot end);  //method depends on Piece
    Piece getPiece(){
        return this;
    };
}
