package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;

public class Shell {

    private int x;
    private int y;
    private int direction;
    private final int MOVE = 3;
    private final String imgPath = ".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\picture\\";
    private final ImageIcon img;
    private final boolean bot;
    private final int[][] mapInt;

    public Shell(int direction, int x, int y, boolean bot, int[][] mapInt) {
        this.bot = bot;
        this.mapInt = mapInt;
        this.x = x+15;
        this.y = y+15;
        this.direction = direction;
        img = new ImageIcon(imgPath + "shell" + direction + ".png");
    }

    public void move() {
        if (direction == 1) {
            y -= MOVE;
        }
        if (direction == 3) {
            y += MOVE;
        }
        if (direction == 2) {
            x += MOVE;
        }
        if (direction == 4) {
            x -= MOVE;
        }

    }

    public boolean checkTank(int xT, int yT, boolean botT) {
        if (botT != bot) {
            if (((xT <= x) && (x <= xT + 40)) && ((yT <= y) && (y <= yT + 40))) {
                return true;
            }
        }
        return false;
    }
    public boolean checkEagle() {
        return ((280 <= x) && (x <= 280 + 40)) && ((560 <= y) && (y <= 560 + 40));
    }

    public synchronized boolean check() {
        boolean b = false;
        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;
        for (int xx = 0; xx < 10; xx++) {
            for (int yy = 0; yy < 10; yy++) {
                if ((mapInt[x + xx][y + yy] == 1) || (mapInt[x + xx][y + yy] == 2)) {
                    if (mapInt[x + xx][y + yy] == 1) {
                        if (direction == 1) {
                            x1 = x - 15;
                            x2 = x + 25;
                            y1 = y - 5;
                            y2 = y + 5;
                        }
                        if (direction == 2) {
                            x1 = x + 5;
                            x2 = x + 15;
                            y1 = y - 15;
                            y2 = y + 25;
                        }
                        if (direction == 3) {
                            x1 = x - 15;
                            x2 = x + 25;
                            y1 = y + 5;
                            y2 = y + 15;
                        }
                        if (direction == 4) {
                            x1 = x - 5;
                            x2 = x + 5;
                            y1 = y - 15;
                            y2 = y + 25;
                        }
                        if (x1<0) {
                            x1=0;
                        }
                        if (x2>599) {
                            x2=599;
                        }
                        if (y1<0) {
                            y1=0;
                        }
                        if (y2>599) {
                            y2=599;
                        }
                        for (int i = x1; i <= x2; i++) {
                            for (int j = y1; j <= y2; j++) {
                                if (mapInt[i][j] == 1) {
                                    mapInt[i][j] = 0;
                                }
                            }
                        }
                    }
                    b = true;
                }
            }
        }
        return b;
    }


    public boolean checkBounds() {
        if ((x < 0) || (x + 10 > 600)) {
            return true;
        }
        if ((y < 0) || (y + 10 > 600)) {
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageIcon getImg() {
        return img;
    }
}
