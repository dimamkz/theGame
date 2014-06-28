package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MainPanel extends JPanel {
    private List<JButton> buttonList;
    private final SupportPanel supportPanel;
    public MainPanel(SupportPanel supportPanel) {
        this.supportPanel=supportPanel;
        setBackground(Color.BLACK);
        setLayout(null);
        setLocation(100, 0);
        setSize(600, 600);
        menu();
        setVisible(true);
    }

    private class StartGameAction implements ActionListener {
        private final int numberOfPlayers;

        public StartGameAction(final int numberOfPlayers) {
            this.numberOfPlayers = numberOfPlayers;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            enableMenu(false);
            final JPanel gamePanel = new GamePanel(supportPanel,numberOfPlayers);
            gamePanel.setLocation(0, 0);
            gamePanel.setSize(600, 600);
            add(gamePanel);
            repaint();
        }
    }

    private class RedactorAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            final JPanel redactorPanel = new RedactorPanel(supportPanel);
            add(redactorPanel);
            enableMenu(false);
            repaint();
        }
    }

    private void menu() {
        buttonList = new ArrayList<JButton>();
        JButton player1Button = new JButton("1 Игрок");
        player1Button.addActionListener(new StartGameAction(1));
        buttonList.add(player1Button);
        JButton player2Button = new JButton("2 Игрока");
        player2Button.addActionListener(new StartGameAction(2));
        buttonList.add(player2Button);
        JButton redactorButton = new JButton("Редактор");
        redactorButton.addActionListener(new RedactorAction());
        buttonList.add(redactorButton);
        JButton controllerButton = new JButton("Контроллер");
        controllerButton.addActionListener(new ControllerAction());
        buttonList.add(controllerButton);
        final int buttonsInt = buttonList.size();
        int x = (getWidth() - 100) / 2;
        int y = (getHeight() - buttonsInt * 40) / 2;
        for (JButton button : buttonList) {
            button.setSize(120, 30);
            button.setLocation(x, y);
            add(button);
            y += 40;
        }
    }

    private void enableMenu(final boolean t) {
        for (JButton button : buttonList) {
            button.setVisible(t);
        }
    }

    private class ControllerAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ControllerFrame(supportPanel);


        }
    }
}
