package ru.ifmo.enf.melnikovd.game;

import java.util.List;
import java.util.Random;


public class GameRunnable implements Runnable {
    private final List<ActionObjects> actionObjectsList;
    private final GamePanel gamePanel;
    private final List<Shell> shellList;
    private final int[][] mapInt;
    private final SupportPanel supportPanel;

    public GameRunnable(List<ActionObjects> actionObjectsList, List<Shell> shellList, GamePanel gamePanel, int[][] mapInt, SupportPanel supportPanel) {
        this.actionObjectsList = actionObjectsList;
        this.gamePanel = gamePanel;
        this.shellList = shellList;
        this.mapInt = mapInt;
        this.supportPanel = supportPanel;

    }

    @Override
    public void run() {
        supportPanel.loadRedactor(false);
        supportPanel.loadGame(true);
        supportPanel.repaint();
        while (((supportPanel.getLife1() > 0) || (supportPanel.getLife2() > 0)) && (supportPanel.gameIsWorking())) {
            while (supportPanel.getPause()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (supportPanel.getEnemy() == 0) {
                supportPanel.changeEnemy(20);
                actionObjectsList.clear();
                gamePanel.loadMap();
            }
            for (ActionObjects obj : actionObjectsList) {
                obj.action();
            }
            for (int i = shellList.size() - 1; i >= 0; i--) {
                shellList.get(i).move();
                if (shellList.get(i).checkEagle()) {
                    supportPanel.changeLife1(0);
                    supportPanel.changeLife2(0);
                } else if (shellList.get(i).checkBounds()) {
                    shellList.remove(i);
                } else if (shellList.get(i).check()) {
                    shellList.remove(i);
                } else {
                    for (int j = actionObjectsList.size() - 1; j >= 0; j--) {
                        if (shellList.get(i).checkTank(actionObjectsList.get(j).getX(), actionObjectsList.get(j).getY(), actionObjectsList.get(j).checkBot())) {
                            if (!actionObjectsList.get(j).checkBot()) {
                                if (actionObjectsList.get(j).checkPlayer() == 1) {
                                    if (supportPanel.getLife1() > 0) {
                                        supportPanel.changeLife1(supportPanel.getLife1()-1);
                                        gamePanel.loadTank1();
                                    }
                                }
                                if (actionObjectsList.get(j).checkPlayer() == 2) {
                                    if (supportPanel.getLife2() > 0) {
                                        supportPanel.changeLife2(supportPanel.getLife2()-1);
                                        gamePanel.loadTank2();
                                    }
                                }
                            } else {
                                supportPanel.changeEnemy(-1);
                            }
                            actionObjectsList.remove(j);
                            shellList.remove(i);
                            break;
                        }
                    }
                }
            }
            int players=0;
            if (supportPanel.getLife1()>0) {
                players++;
            }
            if (supportPanel.getLife2()>0) {
                players++;
            }
            if ((actionObjectsList.size()-players < 6) && (supportPanel.getEnemy() -actionObjectsList.size()+players> 0)) {
                ActionObjects enemyTank1 = new Tank("enemy_tank"+(new Random().nextInt(4)+1), supportPanel, mapInt, shellList);
                actionObjectsList.add(enemyTank1);
            }
            gamePanel.repaint();
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        if (((supportPanel.getLife1() ==0 ) && (supportPanel.getLife2() == 0))){
            supportPanel.gameOver();
        }
    }
}
