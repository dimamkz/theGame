package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;
import java.util.List;
import java.util.Random;

public class Tank implements ActionObjects {
    private final ImageIcon img;
    private final String imgPath = ".\\src\\java\\ru\\ifmo\\enf\\melnikovd\\resurses\\picture\\";
    private final ControllerKeys controllerKeys;
    private int x;
    private int y;
    private String nameTank;
    private int[][] mapInt;
    private int keyMove;
    private int keyShot;
    private List<Shell> shellList;
    private long timeShot = 0;
    private final long TIME = 1000;
    private boolean bot=false;
    private long timeMove = 0;
    private long lastTimeMove = 0;
    private final Random rnd = new Random();
    private int player=0;
    public Tank(String nameTank, SupportPanel supportPanel, int[][] mapInt, List<Shell> shellList) {
        this.nameTank = nameTank;
        this.mapInt = mapInt;
        this.shellList = shellList;
        img = new ImageIcon(imgPath + nameTank + "_1.png");
        if (nameTank.equals("player1_tank1")) {
            controllerKeys = supportPanel.getControlPlayer1();
            x = (600 - 120) / 2 - img.getIconWidth();
            y = 600 - img.getIconHeight();
            player=1;
        } else if (nameTank.equals("player2_tank1")) {
            controllerKeys = supportPanel.getControlPlayer2();
            x = (600 + 120) / 2;
            y = 600 - img.getIconHeight();
            player=2;
        } else {
            controllerKeys = new ControllerKeys(new int[]{0,1,2,3,4,5,6});
            bot=true;
            int[] coordinatesX = {0,280,560};
            x=coordinatesX[rnd.nextInt(3)];
            y=0;
        }
    }

    @Override
    public void setActionMove(int keyMove) {
        this.keyMove = keyMove;
    }

    @Override
    public void setActionShot(int keyShot) {

        this.keyShot = keyShot;
    }

    @Override
    public void action() {
        if (bot) {
            if (System.currentTimeMillis()-lastTimeMove>=timeMove) {
                keyMove = rnd.nextInt(4);
                lastTimeMove=System.currentTimeMillis();
                timeMove = rnd.nextInt(500)+200;
            }
            keyShot = rnd.nextInt(2)+4;
        }
        if (keyMove >= 0) {
            x = controllerKeys.moveX(keyMove, x);
            y = controllerKeys.moveY(keyMove, y);
            checkBounds();
        }
        if ((keyShot == controllerKeys.getA()) && (System.currentTimeMillis() - timeShot >= TIME)) {
            Shell shell = new Shell(controllerKeys.getDirection(), x, y, bot, mapInt);
            shellList.add(shell);
            timeShot = System.currentTimeMillis();
        }
    }

    @Override
    public boolean checkBot() {
        return bot;
    }
    public int checkPlayer() {
        return player;
    }

    private void checkBounds() {
        if (x < 0) {
            x = 0;
        }
        if (x > 600 - img.getIconWidth()) {
            x = 600 - img.getIconWidth();
        }
        if (y < 0) {
            y = 0;
        }
        if (y > 600 - img.getIconHeight()) {
            y = 600 - img.getIconHeight();
        }
        boolean t = true;
        while (t) {
            t = false;
            for (int i = 0; i < img.getIconHeight(); i++) {
                for (int j = 0; j < img.getIconWidth(); j++) {
                    if ((mapInt[x + j][y + i] != 0) && (mapInt[x + j][y + i] != 4)) {
                        t = true;
                    }
                }
            }
            if (t) {
                if (controllerKeys.getDirection() == 1) {
                    y++;
                }
                if (controllerKeys.getDirection() == 2) {
                    x--;
                }
                if (controllerKeys.getDirection() == 3) {
                    y--;
                }
                if (controllerKeys.getDirection() == 4) {
                    x++;
                }
            }
        }
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public ImageIcon getImg() {
        return new ImageIcon(imgPath + nameTank + "_" + controllerKeys.getDirection() + ".png");
    }

    @Override
    public boolean getAction(int keyCode) {
        return ( controllerKeys.getA()==keyCode || controllerKeys.getB()==keyCode || controllerKeys.getDown()==keyCode || controllerKeys.getLeft()==keyCode || controllerKeys.getRight()==keyCode || controllerKeys.getUp()==keyCode);
    }
}
