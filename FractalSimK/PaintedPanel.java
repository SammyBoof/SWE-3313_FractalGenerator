package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

abstract class PaintedPanel extends JPanel{
    //define attributes
    private final BufferedImage fractalImg;
    public static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int FULL_SIZE= (int) ((SCREEN_SIZE.getHeight()*23)/24);
    public static final int DEF_SIZE = (int) ((SCREEN_SIZE.getHeight()*11)/24);
    private final Graphics2D img;

    public PaintedPanel(){
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
    }//end constructor

    public void paint(Graphics g){
        //create fractal image
        super.paint(g);
        g.drawImage(fractalImg,0,0,this);
    }//end paintComponent

    //getters
    public BufferedImage getBufferedImg(){return fractalImg;}
    public Graphics2D getG2D(){return img;}

    //draw current fractal
    abstract void drawFractal(Graphics2D g);//end drawFractal
}//end PaintedPanel
