package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;

public class MainFrame extends JFrame {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }

    public MainFrame() {
        super("Tanks");
        setSize(800, 625);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        addWidgets();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void addWidgets() {
        final SupportPanel supportPanel = new SupportPanel(this);
        getContentPane().add(supportPanel);
        final JPanel mainPanel = new MainPanel(supportPanel);
        getContentPane().add(mainPanel);

    }

}
