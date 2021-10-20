package com.company;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotCurve extends PaintedPanel implements Runnable{
    //attributes
    private static int itr,xPixel,yPixel;
    private static Graphics2D img;
    private static BufferedImage fractalImg;
    //constructor
    public MandelbrotCurve(int i){
        super();
        itr = i;
        xPixel = yPixel = 0;
        fractalImg = super.getBufferedImg();
        img = super.getG2D();
        Thread mandThread = new Thread(this,"Mandelbrot Thread");
        mandThread.start();
    }//end constructor
    @Override
    public void run(){
        //delay is needed to execute repaint from queue
        int delay = 2;
        if(FractalGUI.isFullScreen) delay = 4;
        for(int i = 0; i < fractalImg.getHeight(); i++){
            for(int j = 0; j < fractalImg.getWidth(); j++){
                drawFractal(img);
                cursorForward();
            }//end for loop
            repaint(1);
            try{Thread.sleep(delay);}
            catch(Exception e){System.out.println(e.getMessage());}
        }//end for loop
    }//end run

    @Override
    void drawFractal(Graphics2D g){
        double xc = -.5,yc = 0,size = 2;
        double x0 = xc - (size / 2) + ((size * xPixel) / fractalImg.getHeight());
        double y0 = yc - (size / 2) + ((size * yPixel) / fractalImg.getWidth());
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

        //draw pixel
        g.setColor(c);
        g.drawRect(xPixel, fractalImg.getHeight() - 1 - yPixel, 1 , 1);
    }//end drawFractal

    //move pixel forward
    public void cursorForward(){
        if(xPixel < fractalImg.getWidth()) ++yPixel;
        else xPixel = 0;
        if(yPixel > fractalImg.getHeight()){
            ++xPixel;
            yPixel = 0;
        }//end if statement
    }//end cursorForward

    //carry out mandelbrot equation
    public static int mand(ComplexNumber c, int max){
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
}//end MandelbrotCurve
