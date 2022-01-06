package GUI.ViewControlPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;



class ViewControl extends JFrame implements ActionListener {
    JPanel messagePanel = new JPanel();
    JPanel gamePanel = new JPanel();
    JLabel gameStatus = new JLabel();
    JLabel blackCheckStatus = new JLabel();
    JLabel whiteCheckStatus = new JLabel();
    Board gameboard;

    public ViewControl() throws IOException, NullPointerException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Amazing Chess Game");
        setSize(800, 1000);
        gameboard = new Board();
        setupView();
    }

    public void setupView() {
        setLayout(new GridBagLayout());
        addComponents();
        //pack();
        setVisible(true);
    }

    public void addComponents(){
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;
        c.gridx = 0;
        c.gridy = 0;
        add(messagePanel, c);
        c.gridx = 0;
        c.gridy = 1;
        add(gamePanel, c);
        setupMessagePanel();
        setupGamePanel();

    }

    public void setupMessagePanel(){
        messagePanel.setSize(600,200);
        messagePanel.setLayout(new GridLayout(4,1));
        messagePanel.add(gameStatus);
        messagePanel.add(blackCheckStatus);
        messagePanel.add(whiteCheckStatus);

        gameStatus.setFont(new Font("Serif", Font.BOLD, 20));
        gameStatus.setText(gameboard.getGameStatus());
        blackCheckStatus.setText("Svart kung i schack");
        whiteCheckStatus.setText("Vit kung i schack");
    }

    public void setupGamePanel(){
        //gamePanel.setLayout(new GridLayout(8,8));
        gamePanel.add(gameboard);
        gamePanel.setVisible(true);
        gameboard.setVisible(true);
    }

    public void updateStatus(){
        gameStatus.setText(gameboard.gameStatus);
        blackCheckStatus.setText("");
        whiteCheckStatus.setText("");
        if (gameboard.blackKingCheck) {
            blackCheckStatus.setText("Svart kung i schack");
        }
        if (gameboard.whiteKingCheck) {
            whiteCheckStatus.setText("Vit kung i schack");
        }
    }

    public void actionPerformed(ActionEvent e) {
        gameboard.actionPerformed(e);
        updateStatus();
    }

    public static void main(String[] args) throws IOException, NullPointerException {
        new ViewControl();
    }

}
