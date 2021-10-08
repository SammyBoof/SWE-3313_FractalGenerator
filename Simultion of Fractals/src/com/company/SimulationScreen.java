package com.company;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.lang.Math.*;
import java.util.Random;
import java.io.Serializable;


abstract public class SimulationScreen implements KeyListener, ActionListener {
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    static MyJFrame f;

    public void Main() {
        f.addKeyListener(this);
        f.addActionListener(this);
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

    public void actionPerformed(ActionEvent e) {
        JComboBox cb = (JComboBox)e.getSource();
        String generationType = (String)cb.getSelectedItem();
        updateLabel(generationType);
    }


    public void keyTyped(KeyEvent ke) {
        if (ke.getKeyCode() == KeyEvent.VK_ESCAPE)
            f.dispose();
    }

    static class MyJFrame extends JFrame {
        public void paint(Graphics g) {

            double xc = -.5;
            double yc = 0;
            double size = 2;

            int n;   // create n-by-n image
            int m;
            if (Main.isFullScreen) {
                n = 980;
                m = 980;

            } else {
                n = 380;
                m = 380;
            }
            int max;   // maximum number of iterations
            //THIS DISPLAYS MANDELBROT FROM START TO FINISH(??)
for(max = 0; max <= 500; max++) {
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            double x0 = xc - size / 2 + size * i / n;
            double y0 = yc - size / 2 + size * j / n;
            ComplexNumber z0 = new ComplexNumber(x0, y0);
            int gray = (max - mand(z0, max))%255;
            Random r = new Random();
            Color c = new Color(gray, gray, gray);
            g.setColor(c);
            g.drawRect(i, n - 1 - j, 1, 1);

                    /*
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     */
        }
    }
}
//THIS DISPLAYS MANDELBROT IN REVERSE
            for(max = 500; max > 0; max--) {
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        double x0 = xc - size / 2 + size * i / n;
                        double y0 = yc - size / 2 + size * j / n;
                        ComplexNumber z0 = new ComplexNumber(x0, y0);
                        int gray = (max - mand(z0, max))%255;
                        Random r = new Random();
                        Color c = new Color(gray, gray, gray);
                        g.setColor(c);
                        g.drawRect(i, n - 1 - j, 1, 1);

                    /*
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                     */
                    }
                }
            }
        }
    }

    public static int mand(ComplexNumber z0, int max) {
        ComplexNumber z = new ComplexNumber(z0.mod(), 0);
        for (int t = 0; t < max; t++) {
            if (z.mod() > 2.0) return t;
            z.multiply(z, z);
            z.add(z0);
        }
        return max;
    }
}


