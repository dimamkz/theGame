package ru.ifmo.enf.melnikovd.game;

public class ControllerKeys {
    private final int MOVE = 2;
    private final int up;
    private final int down;
    private final int left;
    private final int right;
    private final int a;
    private final int b;
    private final int start;
    private int direction;
    public ControllerKeys(int[] keys){
        this.up=keys[0];
        this.down=keys[1];
        this.left=keys[2];
        this.right=keys[3];
        this.a=keys[4];
        this.b=keys[5];
        this.start=keys[6];
        direction=1;
    }
    public int moveX(final int key, int x) {
        if (key==left) {
            x-=MOVE;
            direction =4;
        }
        if (key==right) {
            x+=MOVE;
            direction=2;
        }
        return x;
    }
    public int moveY(final int key, int y) {
        if (key==up) {
            y-=MOVE;
            direction =1;
        }
        if (key==down) {
            y+=MOVE;
            direction =3;
        }
        return y;
    }
    public int getA() {
        return a;
    }
    public int getB() {
        return b;
    }
    public int getStart() {
        return start;
    }
    public int getDirection() {
        return direction;
    }
    public int getUp(){ return up;}
    public int getRight(){ return right;}
    public int getLeft(){ return left;}
    public int getDown() {return down;}
}
