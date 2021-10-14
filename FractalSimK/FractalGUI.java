package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FractalGUI{
    //declare attributes
    private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private static MyJFrame menuFrame;
    private static JButton startSim,settings,quitSim,fullscreen,normalscreen,back;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int maxHeight = (int) screenSize.getHeight();
    private static final int maxWidth = (int) screenSize.getWidth();
    public static boolean isFullScreen = false;

    //create listeners for all buttons in start and settings frames
    public static class startButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //get rid of the start frame and call the simulation
            menuFrame.setVisible(false);
            menuFrame.dispose();
            SimulationScreenK.main(null);
            //SimulationScreenK sim = new SimulationScreenK();
        }//end actionPerformed
    }//end startButtonPressed
    public static class settingsButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //go from start menu to settings menu
            if(isFullScreen) {
                fullscreen.setBounds(maxWidth/4, maxHeight/8, maxWidth/2, maxHeight/6);
                normalscreen.setBounds(maxWidth/4, (maxHeight*5)/12, maxWidth/2, maxHeight/6);
                back.setBounds(maxWidth/4, (maxHeight*17)/24, maxWidth/2, maxHeight/6);
            }//end if statement
            else {
                fullscreen.setBounds(maxWidth/8, maxHeight/24, maxWidth/4, maxHeight/12);
                normalscreen.setBounds(maxWidth/8, (maxHeight*4)/24, maxWidth/4, maxHeight/12);
                back.setBounds(maxWidth/8, (maxHeight*7)/24, maxWidth/4, maxHeight/12);
            }//end else statement
            menuFrame.repaint();
            menuFrame.remove(startSim);
            menuFrame.remove(settings);
            menuFrame.remove(quitSim);
            menuFrame.add(fullscreen);
            menuFrame.add(normalscreen);
            menuFrame.add(back);
            menuFrame.repaint();
        }//end actionPerformed
    }//end settingsButtonPressed
    public static class quitButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //exit the program
            System.exit(0);
        }//end actionPerformed
    }//end quitButtonPressed
    public static class fullscreenPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //check if frame is already in fullscreen
            if(isFullScreen) return;
            isFullScreen = true;
            //set to full screen
            device.setFullScreenWindow(menuFrame);
            fullscreen.setBounds(maxWidth/4, maxHeight/8, maxWidth/2, maxHeight/6);
            normalscreen.setBounds(maxWidth/4, (maxHeight*5)/12, maxWidth/2, maxHeight/6);
            back.setBounds(maxWidth/4, (maxHeight*17)/24, maxWidth/2, maxHeight/6);
            menuFrame.repaint();
        }//end actionPerformed
    }//end fullscreenPressed
    public static class normalscreenPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //check if frame is already in default resolution
            if(!isFullScreen) return;
            isFullScreen = false;
            //set to default resolution
            device.setFullScreenWindow(null);
            fullscreen.setBounds(maxWidth/8, maxHeight/24, maxWidth/4, maxHeight/12);
            normalscreen.setBounds(maxWidth/8, (maxHeight*4)/24, maxWidth/4, maxHeight/12);
            back.setBounds(maxWidth/8, (maxHeight*7)/24, maxWidth/4, maxHeight/12);
            menuFrame.repaint();
        }//end actionPerformed
    }//end normalScreenPressed
    public static class backButtonPressed implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //go from back menu to settings menu;
            menuFrame.remove(fullscreen);
            menuFrame.remove(normalscreen);
            menuFrame.remove(back);
            menuFrame.add(startSim);
            menuFrame.add(settings);
            menuFrame.add(quitSim);
            if(isFullScreen) {
                startSim.setBounds(maxWidth/4, maxHeight/8, maxWidth/2, maxHeight/6);
                settings.setBounds(maxWidth/4, (maxHeight*5)/12, maxWidth/2, maxHeight/6);
                quitSim.setBounds(maxWidth/4, (maxHeight*17)/24, maxWidth/2, maxHeight/6);
            }

            else {
                startSim.setBounds(maxWidth/8, maxHeight/24, maxWidth/4, maxHeight/12);
                settings.setBounds(maxWidth/8, (maxHeight*4)/24, maxWidth/4, maxHeight/12);
                quitSim.setBounds(maxWidth/8, (maxHeight*7)/24, maxWidth/4, maxHeight/12);
            }
            menuFrame.repaint();
        }//end actionPerformed
    }//end backButtonPressed

    //constructor
    public FractalGUI(){
        //create frame and buttons
        menuFrame = new MyJFrame("Welcome to CSE Fractal Simulation");
        startSim = new JButton("Start Simulation");
        settings = new JButton("Settings");
        quitSim = new JButton("Quit");
        fullscreen = new JButton("Fullscreen");
        normalscreen = new JButton("Default Resolution");
        back = new JButton("Back");
        //call method to start thread
        kickoff();
    }//end constructor

    //create the menu
    public static void createStartMenu(){
        //instantiate listeners for start menu
        startButtonPressed startList = new startButtonPressed();
        settingsButtonPressed setList = new settingsButtonPressed();
        quitButtonPressed qList = new quitButtonPressed();
        fullscreenPressed fList = new fullscreenPressed();
        normalscreenPressed nList = new normalscreenPressed();
        backButtonPressed bList = new backButtonPressed();
        //create frame
        menuFrame.setSize(maxWidth/2,maxHeight/2);
        menuFrame.setLayout(null);
        //start button
        startSim.addActionListener(startList);
        menuFrame.add(startSim);
        //settings button
        settings.addActionListener(setList);
        menuFrame.add(settings);
        //quit button
        quitSim.addActionListener(qList);
        menuFrame.add(quitSim);
        //set bounds for buttons
        startSim.setBounds(maxWidth/8, maxHeight/24, maxWidth/4, maxHeight/12);
        settings.setBounds(maxWidth/8, (maxHeight*4)/24, maxWidth/4, maxHeight/12);
        quitSim.setBounds(maxWidth/8, (maxHeight*7)/24, maxWidth/4, maxHeight/12);
        menuFrame.repaint();

        //create buttons for setting menu
        //fullscreen button
        fullscreen.addActionListener(fList);
        //normalscreen button
        normalscreen.addActionListener(nList);
        //back button
        back.addActionListener(bList);
    }//end createStartMenu

    //GUI thread
    public static void kickoff() {
        SwingUtilities.invokeLater(FractalGUI::createStartMenu);
    }//end kickoff

    //run program
    public static void main(String[] args){
        //run the start menu and settings menu
        FractalGUI runFractal = new FractalGUI();
    }//end main
}//end FractalGUI
