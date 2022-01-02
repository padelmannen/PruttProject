package GUI;

import javax.swing.*;
import java.util.ArrayList;

public class ViewControl extends JFrame {
    private final String[] colors = {"Black","White"};
    private final Integer[] rows = {1,2,3,4,5,6,7,8};
    private final String[] columns = {"a","b","c","d","e","f","g","h"};

    public ViewControl(){
        createBoard();

        setVisible(true);
    }

    private void createBoard() {
            for(Integer row : rows) {
                for (String column : columns) {

            }
        }
    }
}
