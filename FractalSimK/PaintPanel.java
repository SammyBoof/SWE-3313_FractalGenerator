package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PaintPanel extends JPanel implements Runnable{
    private BufferedImage fractalImg = null;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int fullHeight = (int) ((screenSize.getHeight())*23)/24;
    private static final int defHeight = (int) ((screenSize.getHeight())*11)/24;
    //public Graphics img;
    public static boolean isComplete;

    public PaintPanel(){
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        if (FractalGUI.isFullScreen) this.setBounds(0,0,fullHeight,fullHeight);
        else this.setBounds(0,0,defHeight,defHeight);
        this.setLayout(null);
        this.setVisible(true);
    }//end constructor
    @Override
    public void run() {
        if(FractalGUI.isFullScreen) fractalImg = new BufferedImage(fullHeight,fullHeight,BufferedImage.TYPE_INT_ARGB);
        else fractalImg = new BufferedImage(defHeight,defHeight,BufferedImage.TYPE_INT_ARGB);
        Graphics img = fractalImg.getGraphics();
        //isComplete = false;
        //paintNewPanel(img,10);
        /*long lastTime = System.nanoTime();
        double ticks = 60.0;
        double ns = 1000000000/ticks;
        double delta = 0;
        while(complete){
            paintNewPanel(img,500);
            long currentTime = System.nanoTime();
            delta += (currentTime-lastTime)/ns;
            lastTime = currentTime;
            if(delta >= 1){
                paintComponent(img);
                this.repaint();
                delta--;
            }//end if statement
        }//end while loop*/
        paintComponent(img);
        //paintNewPanel(img,defHeight);
    }//end run
    public void paintComponent(Graphics g){
        if(fractalImg == null) return;
        paintNewPanel(g,defHeight);
        g.drawImage(fractalImg,fractalImg.getWidth(),fractalImg.getHeight(),this);
        System.out.println("Image drawn");
    }
    public void paintNewPanel(Graphics graphics, int max) {
        if(fractalImg == null) return;
        System.out.println("paintNewPanel called");
        //graphics.drawRect(50,50,50,50);
        double xc = -.5;
        double yc = 0;
        double size = 2;

        int n;   // create n-by-n image
        int m;
        if (Main.isFullScreen) {
            n = fullHeight;
            m = fullHeight;

        } else {
            n = defHeight;
            m = defHeight;
        }
        for(int i = 0; i <= max; i++) {
            System.out.println("Painting");
            //paintComponent(graphics);
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    double x0 = xc - size / 2 + size * i / n;
                    double y0 = yc - size / 2 + size * j / n;
                    ComplexNumber z0 = new ComplexNumber(x0, y0);
                    int gray = (max - mand(z0, max))%255;
                    Random r = new Random();
                    Color c = new Color(gray, gray, gray);
                    graphics.setColor(c);
                    graphics.drawRect(i, n - 1 - j, 1, 1);
                }
            }
        }
        //THIS DISPLAYS MANDELBROT IN REVERSE
        for(int i = max; i > 0; i--) {
            //paintComponent(graphics);
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < n; k++) {
                    double x0 = xc - size / 2 + size * i / n;
                    double y0 = yc - size / 2 + size * j / n;
                    ComplexNumber z0 = new ComplexNumber(x0, y0);
                    int gray = (max - mand(z0, max))%255;
                    Random r = new Random();
                    Color c = new Color(gray, gray, gray);
                    graphics.setColor(c);
                    graphics.drawRect(i, n - 1 - j, 1, 1);
                }
            }
        }
        System.out.println("Done Painting");
        //isComplete = true;
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
}//end PaintPanel
