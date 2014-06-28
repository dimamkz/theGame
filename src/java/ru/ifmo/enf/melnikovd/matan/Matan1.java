package ru.ifmo.enf.melnikovd.matan;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Matan1 {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new Matan1Frame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class Matan1Frame extends JFrame {
    public Matan1Frame() {
        setSize(1000,1000);
        add(new Matan2Panel());
    }
}

class Matan1Panel extends JPanel {
    private int X=400;
    private int Y=400;
    public Matan1Panel(){
        setSize(1000,1000);
        setLayout(null);
        setLocation(0,0);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Random rnd = new Random();
        //int T=5;  /*мой*/
        double T=Math.PI;  /*Женя*/
        g.setColor(Color.BLACK);
        g.drawLine(0,Y,1000,Y);
        g.drawLine(X,0,X,1000);
        for (int i : new int[]{1,3,5,50}) {
            g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
            //double a0 = 4/5;  /*мой*/
            double a0=-2/Math.PI; /*Женя*/
            //for (double t=-10; t<=10; t=t+0.001) {  /*мой*/
            for (double t=-Math.PI; t<=Math.PI; t=t+0.001) { /*Женя*/
                double sum=0;
                for (int n=1; n<=i; n++) {
//            /*Мой */double an = 2/(Math.PI*n)*Math.sin(2*Math.PI*n)+5/(2*Math.PI*Math.PI*n*n)*(Math.cos(2*Math.PI*n)-Math.cos(6*Math.PI*n/5));
//            /*Мой */double bn = -2/(Math.PI*n)*Math.cos(2 * Math.PI * n)+5/(2*Math.PI*Math.PI*n*n)*(Math.sin(2 * Math.PI * n)-Math.sin(6 * Math.PI * n / 5));
                    double an = -2/(Math.PI*(1-4*n*n));
                    double bn = -(Math.pow(-1,n))*4*n/(Math.PI*(1-4*n*n));
                    sum+=(an*Math.cos(n*2*Math.PI/T*(t))+bn*Math.sin(n*2*Math.PI/T*(t)));
                }
                sum+=a0/2;
                int y = -(int) (200*sum);
                int x= (int) (200*t);
                g.drawRect(x+X,y+Y,1,1);
            }
        }
    }
}
class Matan2Panel extends JPanel {
    private int X=100;
    private int Y=500;
    public Matan2Panel(){
        setSize(1000,1000);
        setLayout(null);
        setLocation(0,0);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Random rnd = new Random();
        //int T=5;
        double T=2*Math.PI;
        g.setColor(Color.BLACK);
        g.drawLine(0,Y,1000,Y);
        g.drawLine(X,0,X,1000);
        for (int i : new int[]{1,3,10,50}) {
            g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
            //for (double t=-10; t<=10; t=t+0.001) { /*мой*/
            for (double t=-Math.PI; t<=Math.PI; t=t+0.001) { /*Женя*/
                double sum=0;
                for (int n=1; n<=i; n++) {
                    double bn = -Math.cos(Math.PI*n/2)*2*n/Math.PI/(1-n*n);
//               /*мой*/sum+=(-4/(Math.PI*n)*Math.cos(Math.PI*n)+5/(2*n*n*Math.PI*Math.PI)*(Math.sin(Math.PI*n)-Math.sin(3*Math.PI*n/5))*Math.sin(2*Math.PI*t*n/5));
              /*Женя*/sum+=bn*Math.sin(n*2*Math.PI*t/T);
                }
                int y = -(int) (100*sum);
                int x= (int) (100*t);
                g.drawRect(x+X,y+Y,1,1);
            }
        }
    }
}
class Matan3Panel extends JPanel {
    private int X=500;
    private int Y=500;
    public Matan3Panel(){
        setSize(1000,1000);
        setLayout(null);
        setLocation(0,0);

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Random rnd = new Random();
        int T=5;
        g.setColor(Color.BLACK);
        g.drawLine(0, Y, 1000, Y);
        g.drawLine(X,0,X,1000);
        for (int i : new int[]{1,3,10,50}) {
            g.setColor(new Color(rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)));
            for (double t=-10; t<=10; t=t+0.001) {
                double sum=0;
                for (int n=1; n<=i; n++) {
                    sum+=(4*Math.sin(Math.PI*n)/(Math.PI*n)+10/(Math.PI*Math.PI*n*n)*(Math.cos(Math.PI*n)-Math.cos(3*Math.PI*n/5)))*Math.cos(n*2*Math.PI*t/5);
                }
                sum+=2/5;
                int y = -(int) (100*sum);
                int x= (int) (100*t);
                g.drawRect(x+X,y+Y,1,1);
            }
        }
    }
}