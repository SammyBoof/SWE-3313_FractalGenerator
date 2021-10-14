package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.lang.Math.*;
import java.util.Random;

abstract public class KochCurveScreen implements KeyListener {
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    static MyJFrame f;

    public void Main() {
        f.addKeyListener(this);
        f.setFocusable(true);
    }

    public static void main(String[] a) {
        f = new MyJFrame();
        f.setTitle("Simulation");


        if (Main.isFullScreen) {
            device.setFullScreenWindow(f);
        } else {
            f.setBounds(100, 50, 640, 480);
        }
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public void keyTyped(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
            f.dispose();
    }

    static class MyJFrame extends JFrame {
        public void paint(Graphics g) {

            Color c = new Color(0,0,0);
            g.setColor(c);
            makeEquilateralTriangleDown(300, 100, 100, g);

        }

        public void makeEquilateralTriangleDown(int sideLength, int offsetX, int offsetY, Graphics g) {
            int altitude = (int)(Math.sqrt(3)/2*sideLength);
            int[] xPoints = new int[]{offsetX,sideLength+offsetX,(sideLength/2)+offsetX,offsetX};
            int[] yPoints = new int[]{offsetY,offsetY,altitude+offsetY,offsetY};
            g.drawPolyline(xPoints,yPoints,4);
        }
        //TODO: MAKE THIS CODE ACTUALLY GENERATE A RIGHT-SIDE-UP EQUILATERAL TRIANGLE
        public void makeEquilateralTriangleUp(int sideLength, int offsetX, int offsetY, Graphics g) {
            int altitude = (int)(Math.sqrt(3)/2*sideLength);
            int[] xPoints = new int[]{offsetX,sideLength+offsetX,(sideLength/2)+offsetX,offsetX};
            int[] yPoints = new int[]{offsetY,offsetY,altitude+offsetY,offsetY};
            g.drawPolyline(xPoints,yPoints,4);
        }
    }

}