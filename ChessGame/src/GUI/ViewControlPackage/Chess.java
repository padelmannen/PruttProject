package GUI.ViewControlPackage;

import java.io.IOException;

public class Chess {

    public static void main(String[] args) throws IOException {
        ChessGame chessGame = new ChessGame();
        new ChessGUI(chessGame);
    }
}
