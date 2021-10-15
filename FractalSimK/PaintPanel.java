package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

public class PaintPanel extends JPanel implements Runnable{
    //define attributes
    private BufferedImage fractalImg;
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int FULL_SIZE= (int) ((SCREEN_SIZE.getHeight()*23)/24);
    private static final int DEF_SIZE = (int) ((SCREEN_SIZE.getHeight()*11)/24);
    private static Graphics2D img;
    private static int max,xPixel,yPixel,itr;

    public PaintPanel(int m){
        //create panel
        this.setBackground(Color.WHITE);
        this.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        if (FractalGUI.isFullScreen) this.setBounds(0,0,FULL_SIZE,FULL_SIZE);
        else this.setBounds(0,0,DEF_SIZE,DEF_SIZE);
        this.setLayout(null);
        this.setFocusable(true);
        this.setVisible(true);

        if(FractalGUI.isFullScreen) fractalImg = new BufferedImage(FULL_SIZE,FULL_SIZE,BufferedImage.TYPE_INT_ARGB);
        else fractalImg = new BufferedImage(DEF_SIZE,DEF_SIZE,BufferedImage.TYPE_INT_ARGB);
        img = fractalImg.createGraphics();

        max = m;
        xPixel = yPixel = itr = 0;

        //start thread
        Thread paintThread = new Thread(this,"Painting Thread");
        paintThread.start();
    }//end constructor
    @Override
    public void run() {
        int delay = 2;
        if(FractalGUI.isFullScreen) delay = 4;
        for(int i = 0; i < max; i++){
            ++itr;
            for(int j = 0; j < fractalImg.getWidth(); j++){
                repaint(1);
                cursorForward();
                try{Thread.sleep(delay);}
                catch(Exception e){System.out.println(e.getMessage());}
            }//end for loop
        }//end for loop
        /*for(int k = max; k > 0; k--){
            --itr;
            for(int l = 0; l < fractalImg.getHeight(); l++){
                repaint(1);
                cursorReverse();
                try{Thread.sleep(delay);}
                catch(Exception e){System.out.println(e.getMessage());}
            }//end try statement
        }//end for loop*/
    }//end run
    public void paint(Graphics g){
        super.paint(g);
        draw(img,fractalImg.getHeight());
        g.drawImage(fractalImg,0,0,this);
    }//end paintComponent

    public void cursorForward(){
        if(xPixel < fractalImg.getWidth()) ++yPixel;
        else xPixel = 0;
        if(yPixel > fractalImg.getHeight()){
            ++xPixel;
            yPixel = 0;
        }//end if statement
    }//end cursorForward
    public void cursorReverse(){
        if(xPixel > 0) --yPixel;
        else xPixel = fractalImg.getWidth();
        if(yPixel < 0){
            --xPixel;
            yPixel = fractalImg.getHeight();
        }//end if statement
    }//end cursorReverse

    public void draw(Graphics2D g,int res){
        double xc = -.5,yc = 0,size = 2;
        double x0 = xc - size / 2 + size * xPixel / res;
        double y0 = yc - size / 2 + size * yPixel / res;
        ComplexNumber z0 = new ComplexNumber(x0, y0);
        int gray = (itr - mand(z0, itr))%255;
        Color c = new Color(gray, gray, gray);
        g.setColor(c);
        g.drawRect(xPixel, res - 1 - yPixel, 1 , 1);
    }//end draw

    public static int mand(ComplexNumber c, int max) {
        ComplexNumber z = new ComplexNumber();
        for (int t = 0; t < max; t++) {
            if (z.mod() > 2.0) return t;
            z = z.square();
            z.add(c);
        }//end for loop
        return max;
    }//end mand
}//end PaintPanel
