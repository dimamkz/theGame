package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.List;

public class GamePanel extends JPanel {
    private SupportPanel supportPanel;
    private final String imgPath = ".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\picture\\";
    private Map<String, ImageIcon> map;
    private int[][] mapInt;
    private ActionObjects tank1;
    private ActionObjects tank2;
    private List<Shell> shellList = new ArrayList<Shell>();
    private List<ActionObjects> actionObjectsList = new ArrayList<ActionObjects>();
    private int level = 0;
    private String fileLevel = ".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\saves\\level";
    private final int numberOfPlayers;

    public GamePanel(final SupportPanel supportPanel, final int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        setLocation(0, 0);
        setSize(600, 600);
        setLayout(null);
        setBackground(Color.BLACK);
        this.supportPanel = supportPanel;
        map = new HashMap<String, ImageIcon>();
        mapInt = new int[getWidth()][getHeight()];
        loadMap();
        supportPanel.changeLife1(3);
        if (numberOfPlayers == 2) {
            supportPanel.changeLife2(3);
        }
        Runnable runnable = new GameRunnable(actionObjectsList, shellList, this, mapInt, supportPanel);
        Thread thread = new Thread(runnable);
        thread.start();
        final JButton keyListener = new JButton();
        keyListener.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (tank1.getAction(e.getKeyCode())) {
                    tank1.setActionMove(e.getKeyCode());
                    tank1.setActionShot(e.getKeyCode());
                }
                if (numberOfPlayers == 2) {
                    if (tank2.getAction(e.getKeyCode())) {
                        tank2.setActionMove(e.getKeyCode());
                        tank2.setActionShot(e.getKeyCode());
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (tank1.getAction(e.getKeyCode())) {
                    tank1.setActionMove(-1);
                    tank1.setActionShot(-1);
                }
                if (numberOfPlayers == 2) {
                    if (tank2.getAction(e.getKeyCode())) {
                        tank2.setActionMove(-1);
                        tank2.setActionShot(-1);
                    }
                }
            }
        });
        add(keyListener);
        setVisible(true);
    }

    public void loadTank1() {
        tank1 = new Tank("player1_tank1", supportPanel, mapInt, shellList);
        actionObjectsList.add(tank1);
    }

    public void loadTank2() {
        tank2 = new Tank("player2_tank1", supportPanel, mapInt, shellList);
        actionObjectsList.add(tank2);
    }

    public void loadMap() {
        loadTank1();
        if (numberOfPlayers == 2) {
            loadTank2();
        }
        level++;
        map.clear();
        try {
            Scanner sc = new Scanner(new File(fileLevel + level + "_map.txt"));
            while (sc.hasNext()) {
                String key = sc.nextLine();
                int value = Integer.parseInt(sc.nextLine());
                map.put(key, new ImageIcon(imgPath +"wall_"+ value + ".png"));
            }
            sc.close();
            sc = new Scanner(new File(fileLevel + level + "_mapInt.txt"));
            for (int i = 0; i < getHeight(); i++) {
                for (int j = 0; j < getWidth(); j++) {
                    int key = Integer.parseInt(sc.nextLine());
                    mapInt[j][i] = key;
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i=280; i<320;i++) {
            for (int j=560; j<600; j++) {
                mapInt[i][j]=-1;
            }
        }
        map.put("280_560",new ImageIcon(imgPath +"eagle.png"));
        repaint();
    }


    protected synchronized void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.black);

        for (String key : map.keySet()) {
            String[] coordinates = key.split("_");
            int curX = Integer.parseInt(coordinates[0]);
            int curY = Integer.parseInt(coordinates[1]);
            ImageIcon curImg = map.get(key);
            g2.drawImage(curImg.getImage(), curX, curY, curImg.getImageObserver());
            for (int i = 0; i < 40; i++) {
                for (int j = 0; j < 40; j++) {
                    if (mapInt[curX + i][curY + j] == 0) {
                        g2.drawRect(curX + i, curY + j, 1, 1);
                    }
                }
            }
        }
        for (Shell shell : shellList) {
            g2.drawImage(shell.getImg().getImage(), shell.getX(), shell.getY(), shell.getImg().getImageObserver());
        }
        for (ActionObjects tank : actionObjectsList) {
            g2.drawImage(tank.getImg().getImage(), tank.getX(), tank.getY(), tank.getImg().getImageObserver());
        }
    }
}
