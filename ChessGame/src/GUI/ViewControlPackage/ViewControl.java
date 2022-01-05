package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class ViewControl extends JFrame implements ActionListener {

    JPanel messagePanel;
    JPanel gamePanel;
    JLabel gameStatus;
    JLabel blackCheckStatus;
    JLabel whiteCheckStatus;
    Board gameboard = new Board();

    public ViewControl() throws IOException, NullPointerException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupView();
    }

    public void setupView() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;

        addComponents();
        pack();
        setVisible(true);
    }

    public void addComponents(){
        add(messagePanel);
        setupMessagePanel();
        add(gamePanel);
        setupGamePanel();

    }

    public void setupMessagePanel(){
        messagePanel.setLayout(new GridLayout(3,1));
        messagePanel.add(gameStatus);
        messagePanel.add(blackCheckStatus);
        messagePanel.add(whiteCheckStatus);
    }

    public void setupGamePanel(){
        gamePanel.setLayout(new GridLayout(8,8));
        gamePanel.add(gameboard);
    }

    public static void main(String[] args) throws IOException, NullPointerException {
        new ViewControl();
    }

    public void actionPerformed(ActionEvent e) {
        gameboard.actionPerformed(e);
    }
}
