package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SupportPanel extends JPanel {
    private ControllerKeys controlPlayer1;
    private ControllerKeys controlPlayer2;
    private final int keys[][];
    private final JLabel xLabel;
    private final JLabel yLabel;
    private final JLabel lifeLabel1;
    private final JLabel lifeLabel2;
    private final JLabel enemyLabel;
    private final JButton menuButton;
    private int life1 = 0;
    private int life2 = 0;
    private int enemy =5;
    private final MainFrame mainFrame;
    private boolean pause = false;
    private boolean work = true;
    public SupportPanel(MainFrame mainFrame) {
        this.keys = new int[3][7];
        this.mainFrame=mainFrame;
        setLayout(null);
        setSize(100, 600);
        setLocation(700, 0);
        menuButton = new JButton("Menu");
        menuButton.setLocation(10, 10);
        menuButton.setSize(80, 40);
        menuButton.addActionListener(new MenuAction(this));

        xLabel=new JLabel("X : ");
        xLabel.setSize(80, 40);
        xLabel.setLocation(10, 310);
        yLabel=new JLabel("Y : ");
        yLabel.setSize(80, 40);
        yLabel.setLocation(10, 360);

        lifeLabel1=new JLabel("Life 1: "+life1);
        lifeLabel1.setSize(80, 40);
        lifeLabel1.setLocation(10, 360);
        lifeLabel2=new JLabel("Life 2: "+life2);
        lifeLabel2.setSize(80, 40);
        lifeLabel2.setLocation(10, 410);
        enemyLabel=new JLabel("Enemy : "+enemy);
        enemyLabel.setSize(80, 40);
        enemyLabel.setLocation(10, 310);

        add(xLabel);
        add(yLabel);
        add(lifeLabel1);
        add(lifeLabel2);
        add(enemyLabel);
        add(menuButton);
        loadGame(false);
        loadRedactor(false);
        loadKeys();
        setVisible(true);
    }
    public void loadRedactor(boolean t) {
        xLabel.setVisible(t);
        yLabel.setVisible(t);
        menuButton.setVisible(t);
    }

    public void changeCoordinates(int x,int y) {
        xLabel.setText("X : "+x);
        yLabel.setText("Y : "+y);
    }
    public void loadGame(boolean t) {
        lifeLabel1.setVisible(t);
        lifeLabel2.setVisible(t);
        enemyLabel.setVisible(t);
        menuButton.setVisible(t);
        work=true;
       }
    public void changeLife1(int k) {
        life1=k;
        lifeLabel1.setText("Life : "+life1);
    }
    public void changeLife2(int k) {
        life2=k;
        lifeLabel2.setText("Life : "+life2);
    }

    public int getLife1(){
        return life1;
    }
    public int getLife2(){
        return life2;
    }

    public boolean getPause(){
        return pause;
    }
    private void loadKeys() {
        final File file = new File(".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\controls\\controls.txt");
        Scanner reader = null;
        try {
            reader = new Scanner(file);
            for (int i = 1; i <= 2; i++) {
                for (int j = 0; j < keys[i].length; j++) {
                    if (reader.hasNext()) {
                        keys[i][j] = Integer.parseInt(reader.nextLine());
                    } else {
                        keys[i][j] = 0;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        setControlPlayer1(keys[1]);
        setControlPlayer2(keys[2]);

    }

    public void setControlPlayer1(int[] keys) {
        controlPlayer1 = new ControllerKeys(keys);
    }

    public void setControlPlayer2(int[] keys) {
        controlPlayer2 = new ControllerKeys(keys);
    }

    public ControllerKeys getControlPlayer1() {
        return controlPlayer1;
    }

    public ControllerKeys getControlPlayer2() {
        return controlPlayer2;
    }

    public int getEnemy() {
        return enemy;
    }
    public void changeEnemy(int k) {
        enemy +=k;
        enemyLabel.setText("Enemy : " + enemy);
    }

    public boolean gameIsWorking() {
        return work;
    }

    public void gameOver() {
        final JLabel gameOverLabel = new JLabel("Вы проиграли!",SwingConstants.CENTER);
        gameOverLabel.setSize(200, 50);
        gameOverLabel.setLocation(300, 250);
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setOpaque(true);
        final JButton exitButton = new JButton("Выйти в главное меню");
        exitButton.setSize(200, 50);
        exitButton.setLocation(300, 300);
        mainFrame.getContentPane().add(gameOverLabel);
        mainFrame.getContentPane().add(exitButton);
        exitButton.repaint();
        gameOverLabel.repaint();
        final SupportPanel supportPanel= this;
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.getContentPane().remove(gameOverLabel);
                mainFrame.getContentPane().remove(exitButton);
                mainFrame.getContentPane().remove(1);
                mainFrame.getContentPane().add(new MainPanel(supportPanel));
                loadRedactor(false);
                loadGame(false);
                mainFrame.repaint();
            }
        });
    }

    private class MenuAction implements ActionListener {
        private SupportPanel supportPanel;
        public MenuAction(SupportPanel supportPanel) {
            this.supportPanel=supportPanel;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            pause=true;
            final JButton resumeButton = new JButton("Продолжить");
            resumeButton.setSize(200, 50);
            resumeButton.setLocation(300, 250);
            final JButton exitButton = new JButton("Выйти в главное меню");
            exitButton.setSize(200, 50);
            exitButton.setLocation(300, 300);
            mainFrame.getContentPane().add(resumeButton);
            mainFrame.getContentPane().add(exitButton);
            resumeButton.repaint();
            exitButton.repaint();
            resumeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pause = false;
                    mainFrame.getContentPane().remove(resumeButton);
                    mainFrame.getContentPane().remove(exitButton);
                    mainFrame.repaint();
                }
            });
            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    work=false;
                    pause=false;
                    mainFrame.getContentPane().remove(resumeButton);
                    mainFrame.getContentPane().remove(exitButton);
                    mainFrame.getContentPane().remove(1);
                    mainFrame.getContentPane().add(new MainPanel(supportPanel));
                    supportPanel.loadRedactor(false);
                    supportPanel.loadGame(false);
                    mainFrame.repaint();
                }
            });
        }
    }
}
