package ru.ifmo.enf.melnikovd.game;

import javax.swing.*;

public interface ActionObjects {
    public void setActionMove(int key);
    public void setActionShot(int key);
    public void action();
    public boolean checkBot();
    public int checkPlayer();
    public int getX();
    public int getY();
    public ImageIcon getImg();

    boolean getAction(int keyCode);
}
