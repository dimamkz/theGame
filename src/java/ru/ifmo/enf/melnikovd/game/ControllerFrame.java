package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ControllerFrame extends JFrame {
    private JLabel playerLabel;
    private JLabel keyLabel;
    private String[] keysMessage = {"UP", "DOWN", "LEFT", "RIGHT", "A", "B", "START"};
    private int[][] keys = new int[3][keysMessage.length];
    private int counter = keysMessage.length;
    private int curPlayer = 0;
    private final SupportPanel supportPanel;
    public ControllerFrame(SupportPanel supportPanel) {
        this.supportPanel=supportPanel;
        setResizable(false);
        setSize(250, 200);
        setLocationRelativeTo(null);
        setFocusableWindowState(true);
        setLayout(null);
        addWidgets();
        setVisible(true);
    }

    private void addWidgets() {

        JButton player1 = new JButton("Игрок 1");
        player1.setSize(80, 30);
        player1.setLocation(20, 20);
        player1.addActionListener(new ControllerAction(1));
        JButton player2 = new JButton("Игрок 2");
        player2.setSize(80, 30);
        player2.setLocation(20, 60);
        player2.addActionListener(new ControllerAction(2));
        add(player1);
        add(player2);
        playerLabel = new JLabel("Select player", SwingConstants.CENTER);
        playerLabel.setSize(120, 30);
        playerLabel.setLocation(110, 20);
        add(playerLabel);
        keyLabel = new JLabel("", SwingConstants.CENTER);
        keyLabel.setSize(120, 30);
        keyLabel.setLocation(110, 60);
        add(keyLabel);
        player1.addKeyListener(new ControlKeyListener());
        player2.addKeyListener(new ControlKeyListener());
        JButton okButton = new JButton("Ok");
        okButton.setSize(50,30);
        okButton.setLocation(50, 120);
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\controls\\controls.txt"));
                    for (int i=1; i<=2; i++) {
                        for(int j=0; j<keys[i].length; j++) {
                            pw.println(keys[i][j]);
                        }
                    }
                    pw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                supportPanel.setControlPlayer1(keys[1]);
                supportPanel.setControlPlayer2(keys[2]);
                dispose();
            }
        });
        add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cancelButton.setSize(80,30);
        cancelButton.setLocation(120, 120);
        add(cancelButton);
    }

    private class ControllerAction implements ActionListener {
        private int n;

        public ControllerAction(int n) {
            this.n = n;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            playerLabel.setText("Player " + n);
            curPlayer=n;
            counter = 0;
            keyLabel.setText(keysMessage[counter]);
        }
    }

    private class ControlKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (counter < keys[curPlayer].length) {
                keys[curPlayer][counter] = e.getKeyCode();
            }
            counter++;
            if (counter < keysMessage.length) {
                keyLabel.setText(keysMessage[counter]);
            }
            if (counter==keysMessage.length) {
                playerLabel.setText("Select player");
                keyLabel.setText("");
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    }
}
