package GUI.ViewControlPackage;


public abstract class Piece {

    private boolean white = false;

    public Piece(boolean white)
    {
        this.setWhite(white);
    }

    public boolean isWhite()
    {
        return this.white;  //true or false
    }

    private void setWhite(boolean white)
    {
        this.white = white;
    }

    public abstract boolean acceptedMove(Spot[][] board,
                                         Spot start, Spot end);  //method depends on Piece
    public Piece getPiece(){
        return this;
    };
}
