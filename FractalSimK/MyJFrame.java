package com.company;
import javax.swing.*;
import java.awt.*;
public class MyJFrame extends JFrame{
    final static int SIZE = 500;
    public MyJFrame(String title){
        super(title);
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        setDefaultLookAndFeelDecorated(true);
        int width = (int)((monitorWidth() - SIZE + 0.5)/2) ;
        int height = (int)((monitorHeight() - SIZE + 0.5)/2);
        this.setLocation(width,height);
        this.setSize(SIZE,SIZE);
    } //end Constructor

    public static double monitorWidth(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getWidth();
    }
    public static double monitorHeight(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return screenSize.getHeight();
    }
}