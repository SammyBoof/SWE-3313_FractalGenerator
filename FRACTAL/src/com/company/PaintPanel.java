package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class PaintPanel extends JPanel implements Runnable {
    //define attributes
    private static BufferedImage fractalImg;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FULL_SIZE = (int) ((SCREEN_SIZE.getHeight() * 23) / 24);
    private static final int DEF_SIZE = (int) ((SCREEN_SIZE.getHeight() * 11) / 24);
    private static Graphics2D img;
    //itr is the number of iterations
    private static int xPixel, yPixel, itr;
    public static int selection;
    public static int type;
    //private static double sin60 = Math.sin(3.14/3.);

    public PaintPanel(int i) {
        //create panel
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        if (FractalGUI.isFullScreen) this.setBounds(0, 0, FULL_SIZE, FULL_SIZE);
        else this.setBounds(0, 0, DEF_SIZE, DEF_SIZE);
        this.setLayout(null);
        this.setFocusable(true);
        this.setVisible(true);

        if (FractalGUI.isFullScreen) fractalImg = new BufferedImage(FULL_SIZE, FULL_SIZE, BufferedImage.TYPE_INT_ARGB);
        else fractalImg = new BufferedImage(DEF_SIZE, DEF_SIZE, BufferedImage.TYPE_INT_ARGB);
        img = fractalImg.createGraphics();

        itr = i;
        xPixel = yPixel = 0;

        //selection = SimulationScreenK.generationType.getSelectedIndex();
        //start thread
        Thread paintThread = new Thread(this, "Painting Thread");
        paintThread.start();
    }//end constructor

    @Override
    public void run() {
        //delay is needed to execute repaint from queue
        //SimulationScreenK.generationType.setSelectedIndex(0);
        switch (selection) {
            case 0:
                int delay = 2;
                if (FractalGUI.isFullScreen) delay = 4;
                for (int i = 0; i < fractalImg.getHeight(); i++) {
                    for (int j = 0; j < fractalImg.getWidth(); j++) {
                        drawMandelbrot(img, fractalImg.getHeight());
                        cursorForward();
                    }
                }//end for loop
                repaint(1);
                try {
                    Thread.sleep(delay);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 1:
                switch(type) {
                    case 0:
                        delay = 2;
                        if (FractalGUI.isFullScreen) delay = 4;
                        int sideLength;
                        if (FractalGUI.isFullScreen)  sideLength = (FULL_SIZE * 2)/3;
                        else sideLength = (DEF_SIZE * 2)/3;
                        int altitude = (int)((Math.sqrt(3)/2)*sideLength);
                        int recursions = itr;
                        kochRecursive(60, sideLength+60,sideLength + 60, sideLength + 60, (sideLength/2) + 60, sideLength - altitude + 60, itr, img);
                        repaint(1);
                        try {
                            Thread.sleep(delay);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 1:
                        delay = 2;
                        if (FractalGUI.isFullScreen) delay = 4;
                        if (FractalGUI.isFullScreen)  sideLength = (FULL_SIZE * 2)/3;
                        else sideLength = (DEF_SIZE * 2)/3;
                        drawKoch1(60, sideLength+60,sideLength + 60, sideLength + 60, itr, img);
                        repaint(1);
                        try {
                            Thread.sleep(delay);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                }
            case 2:
                //paint for genType 2
                break;
            case 3:
                //paint for genType 3
                break;
            default:
                break;
        }
    }//end run

    public void paint(Graphics g){
        //create fractal image
        super.paint(g);
        g.drawImage(fractalImg,0,0,this);
    }//end paintComponent

    //methods to move the current pixel in direction of painting
    public void cursorForward(){
        if(xPixel < fractalImg.getWidth()) ++yPixel;
        else xPixel = 0;
        if(yPixel > fractalImg.getHeight()){
            ++xPixel;
            yPixel = 0;
        }//end if statement
    }//end cursorForward

    public void drawKoch1(int x1, int y1, int x2, int y2,int depth, Graphics2D g) {//Koch curve & NBSP;   keleyi.com
        g.setColor(Color.BLACK);
        g.drawLine(x1, y1, x2, y2);
        if (depth<=1)
            return;
        else {//Get the third degree & NBSP;
            double x11 = (x1 * 2  + x2)  / 3;
            double y11 = (y1 * 2  + y2) / 3;

            double x22 = (x1 + x2 * 2) / 3;
            double y22 = (y1 + y2 * 2) / 3;

            double x33 = (x11 + x22) / 2 - (y11 - y22) * Math.sqrt(3) / 2;
            double y33 = (y11 + y22) / 2 - (x22 - x11) * Math.sqrt(3) / 2;

            g.setColor(g.getBackground());
            g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
            g.setColor(Color.black);
            drawKoch1((int) x1, (int) y1, (int) x11, (int) y11,depth-1, g);
            drawKoch1((int) x11, (int) y11, (int) x33, (int) y33,depth-1, g);
            drawKoch1((int) x22, (int) y22, (int) x2, (int) y2,depth-1, g);
            drawKoch1((int) x33, (int) y33, (int) x22, (int) y22,depth-1, g);
        }
    }

    public void kochRecursive(int x1,int y1,int x2,int y2,int x3,int y3,int depth, Graphics2D g){//Triangle     keleyi.com

        double s = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        g.setColor(Color.BLACK);

        g.drawLine(x1,y1,x2,y2);
        g.drawLine(x2,y2,x3,y3);
        g.drawLine(x1,y1,x3,y3);
        if(s<3)
          return;
        if (depth<=1)
            return;
        else
        {

            double x11=(x1*3+x2)/4;
            double y11=y1-(s/4)*Math.sqrt(3);

            double x12=(x1+x2*3)/4;
            double y12=y11;

            double x13=(x1+x2)/2;
            double y13=y1;


            double x21=x1-s/4;
            double y21=(y1+y3)/2;

            double x22=x1+s/4;
            double y22=y21;

            double x23=x1;
            double y23=y3;


            double x31=x2+s/4;
            double y31=(y1+y3)/2;

            double x32=x2-s/4;
            double y32=y21;

            double x33=x2;
            double y33=y3;


            kochRecursive((int)x11,(int)y11,(int)x12,(int)y12, (int)x13, (int)y13, depth-1, g);
            kochRecursive((int)x21,(int)y21,(int)x22,(int)y22, (int)x23, (int)y23, depth-1, g);
            kochRecursive((int)x31,(int)y31,(int)x32,(int)y32, (int)x33, (int)y33, depth-1, g);
        }
    }


    //draw current pixel
    public void drawMandelbrot(Graphics2D g,int res){
        double xc = -.5,yc = 0,size = 2;
        double x0 = xc - (size / 2) + ((size * xPixel) / res);
        double y0 = yc - (size / 2) + ((size * yPixel) / res);
        ComplexNumber z0 = new ComplexNumber(x0, y0);

        //logic for color choice
        Color c;
        int pigment = mand(z0,itr);
        if(pigment < (itr)/10) c = new Color(255,0,0);
        else if(pigment < (itr)/5) c = new Color(255,125,0);
        else if(pigment < (itr*3)/10) c = new Color(255,255,0);
        else if(pigment < (itr*2)/5) c = new Color(125,255,0);
        else if(pigment < (itr)/2) c = new Color(0,255,125);
        else if(pigment < (itr)*3/5) c = new Color(0,255,255);
        else if(pigment < (itr*7)/10) c = new Color(0,0,255);
        else if(pigment < (itr*4)/5) c = new Color(125,0,255);
        else if(pigment < (itr*9)/10) c = new Color(255,0,255);
        else c = new Color(0,0,0);
        g.setColor(c);
        g.drawRect(xPixel, res - 1 - yPixel, 1 , 1);


    }//end draw

    //carry out mandelbrot equation
    public static int mand(ComplexNumber c, int max) {
        //initial z is zero
        ComplexNumber z = new ComplexNumber();
        //following z values are (z(n-1))^2 + c
        for (int t = 0; t < max; t++) {
            if (z.mod() > 2.0) return t;
            z = z.square();
            z.add(c);
        }//end for loop
        //return max;
        return max;
    }//end mand

}//end PaintPanel
