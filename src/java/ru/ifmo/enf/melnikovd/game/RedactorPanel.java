package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RedactorPanel extends JPanel {
    private int x;
    private int y;
    private ImageIcon img;
    private Map<String, Integer> map;
    private final SupportPanel supportPanel;
    private int curWall;
    private final int NUMBER_OF_WALL = 5;
    private final String imgPath = ".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\picture\\wall_";
    private final int[][] mapInt;

    public RedactorPanel(SupportPanel supportPanel) {
        setLocation(0, 0);
        setSize(600, 600);
        this.supportPanel = supportPanel;
        supportPanel.loadGame(false);
        supportPanel.loadRedactor(true);
        supportPanel.repaint();
        curWall = 0;
        img = new ImageIcon(imgPath + curWall + ".png");
        x = 0;
        y = getHeight() - img.getIconHeight();
        map = new HashMap<String, Integer>();
        mapInt = new int[getHeight()][getWidth()];
        setBackground(Color.BLACK);
        setLayout(null);
        final JButton keyListener = new JButton();
        keyListener.addKeyListener(new RedactorKeyListener());
        add(keyListener);
        setVisible(true);
    }

    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (String key : map.keySet()) {
            String[] coordinates = key.split("_");
            int curX = Integer.parseInt(coordinates[0]);
            int curY = Integer.parseInt(coordinates[1]);
            ImageIcon curImg = new ImageIcon(imgPath + map.get(key) + ".png");
            g2.drawImage(curImg.getImage(), curX, curY, curImg.getImageObserver());
            g2.setColor(Color.BLACK);
        }
        if (img != null) {
            g2.drawImage(img.getImage(), x, y, img.getImageObserver());
            g2.setColor(Color.BLUE);
            g2.drawRect(x, y, img.getIconWidth(), img.getIconHeight());
        }
    }

    private class RedactorKeyListener implements KeyListener {

        public RedactorKeyListener() {
        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            ControllerKeys controllerKeys = supportPanel.getControlPlayer1();
            if (e.getKeyCode() == controllerKeys.getStart()) {
                try {
                    PrintWriter pw = new PrintWriter(new FileWriter(".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\saves\\save1_mapInt.txt"));
                    for (int i = 0; i < mapInt.length; i++) {
                        for (int j = 0; j < mapInt[i].length; j++) {
                            pw.println(mapInt[i][j]);
                        }
                    }
                    pw.close();
                    pw = new PrintWriter(new FileWriter(".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\saves\\save1_map.txt"));
                    for (String key : map.keySet()) {
                        pw.println(key);
                        pw.println(map.get(key));
                    }
                    pw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
            if (e.getKeyCode() == controllerKeys.getB()) {
                curWall++;
                curWall %= NUMBER_OF_WALL;
            }
            img = new ImageIcon(imgPath + curWall + ".png");
            if (e.getKeyCode() == controllerKeys.getA()) {
                boolean t = true;
                if (curWall != 0) {
                    for (String key : map.keySet()) {
                        if (map.get(key) != 0) {
                            String[] coordinates = key.split("_");
                            int curX = Integer.parseInt(coordinates[0]);
                            int curY = Integer.parseInt(coordinates[1]);
                            ImageIcon curImg = new ImageIcon(imgPath + map.get(key) + ".png");
                            int curHeight = curImg.getIconHeight();
                            int curWidth = curImg.getIconWidth();
                            for (int i : new int[]{0, img.getIconWidth() - 1}) {
                                for (int j : new int[]{0, img.getIconHeight() - 1}) {
                                    if (((curX <= x + i) && (x + i < curX + curWidth)) &&
                                            ((curY <= y + j) && (y + j < curY + curHeight))) {
                                        t = false;
                                    }
                                }
                            }
                        }
                    }
                }
                if (t) {
                        map.put(x + "_" + y, curWall);
                    for (int i = 0; i < img.getIconWidth(); i++) {
                        for (int j = 0; j < img.getIconHeight(); j++) {
                            mapInt[y + j][x + i] = curWall;
                        }
                    }
                }
            }

            int xx = x;
            int yy = y;
            x = controllerKeys.moveX(e.getKeyCode(), x);
            y = controllerKeys.moveY(e.getKeyCode(), y);
            if (x != xx) {
                if (x > xx) {
                    x += 3;
                } else {
                    x -= 3;
                }
            }
            if (y != yy) {
                if (y > yy) {
                    y += 3;
                } else {
                    y -= 3;
                }
            }
            boundsCheck();
            supportPanel.changeCoordinates(x, y);
            repaint();
        }

        private void boundsCheck() {
            if (x < 0) {
                x = 0;
            }
            if (x > getWidth() - img.getIconWidth()) {
                x = getWidth() - img.getIconWidth();
            }
            if (y < 0) {
                y = 0;
            }
            if (y > getHeight() - img.getIconHeight()) {
                y = getHeight() - img.getIconHeight();
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }


}
