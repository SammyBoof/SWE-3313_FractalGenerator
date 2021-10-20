package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SimulationScreenK implements Runnable{
    //declare attributes
    private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private static MyJFrame fractalFrame;
    private static PaintedPanel fractalPanel;
    private static JComboBox<String> generationType,type,iterations,set;
    private static JButton simulateButton;
    //resolution options
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int maxHeight = (int) screenSize.getHeight();
    private static final int maxWidth = (int) screenSize.getWidth();
    //ComboBox choices
    private static final String[] algorithms = {"Mandelbrot","Koch","Sierpinski","fourth"};
    private static final String[] types = {"Triangle","Circle","Square","4"};
    private static final String[] mandItr = {"1","10","20","50","100"};
    private static final String[] kochItr = {"0","1","3","5","10"};
    private static final String[] setsArray = {"a","b","c","d"};

    //create listeners
    public static class simulateListener implements ActionListener{
        public void actionPerformed(ActionEvent e){updatePanel();}
    }//end simulateListener
    public static class generationTypeListener implements ActionListener{
        public void actionPerformed(ActionEvent e){updateChoices();}
    }//end generationTypeListener
    public static class escapeListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {/*do nothing*/}
        @Override
        public void keyPressed(KeyEvent e) {/*do nothing*/}
        @Override
        public void keyReleased(KeyEvent e) {
            //close when escape is released
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                fractalFrame.dispose();
                System.exit(0);
        }//end keyReleased
    }//end escapeListener

    public SimulationScreenK(){
        //create frame, comboBoxes, and button
        fractalFrame = new MyJFrame("Simulation");
        generationType = new JComboBox<>(algorithms);
        iterations = new JComboBox<>(mandItr);
        type = new JComboBox<>(types);
        set = new JComboBox<>(setsArray);
        simulateButton = new JButton("Simulate");


        //start thread
        Thread simulation = new Thread(this,"SimulationThread");
        simulation.start();
    }//end constructor

    @Override
    public void run() {createSimulation();}//end run

    public void createSimulation(){
        //create listeners
        generationTypeListener gList = new generationTypeListener();
        escapeListener eList = new escapeListener();
        simulateListener bList = new simulateListener();

        //create frame
        if (FractalGUI.isFullScreen) device.setFullScreenWindow(fractalFrame);
        else fractalFrame.setSize((maxWidth/2), (maxHeight/2));
        fractalFrame.setLayout(null);
        fractalFrame.addKeyListener(eList);
        fractalFrame.setFocusable(true);

        //create panel for comboBoxes
        JPanel comboPanel = new JPanel();
        comboPanel.setBackground(Color.LIGHT_GRAY);
        comboPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.setLayout(new GridLayout(5,2));
        //generation ComboBox/Jlabel
        JLabel genDescription = new JLabel("Generation Type:",JLabel.CENTER);
        genDescription.setBackground(Color.LIGHT_GRAY);
        genDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(genDescription);
        generationType.addActionListener(gList);
        generationType.addKeyListener(eList);
        generationType.setSelectedIndex(0);
        comboPanel.add(generationType);
        //type ComboBox/Jlabel
        JLabel typeDescription = new JLabel("Fractal Type:",JLabel.CENTER);
        typeDescription.setBackground(Color.LIGHT_GRAY);
        typeDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(typeDescription);
        type.addKeyListener(eList);
        type.setSelectedIndex(0);
        comboPanel.add(type);
        //iterations ComboBox/Jlabel
        JLabel itrDescription = new JLabel("Iterations:",JLabel.CENTER);
        itrDescription.setBackground(Color.LIGHT_GRAY);
        itrDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(itrDescription);
        iterations.addKeyListener(eList);
        iterations.setSelectedIndex(0);
        comboPanel.add(iterations);
        //setComboBox/Jlabel
        JLabel setDescription = new JLabel("Set:",JLabel.CENTER);
        setDescription.setBackground(Color.LIGHT_GRAY);
        setDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(setDescription);
        set.addKeyListener(eList);
        set.setSelectedIndex(0);
        comboPanel.add(set);
        //button/JLabel
        JLabel buttonDescription = new JLabel("Press to start simulation:",JLabel.CENTER);
        buttonDescription.setBackground((Color.LIGHT_GRAY));
        buttonDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(buttonDescription);
        simulateButton.addActionListener(bList);
        simulateButton.addKeyListener(eList);
        comboPanel.add(simulateButton);
        //add comboBox panel to frame
        //height adjusted to account for title at top
        int hAdjust = maxHeight/24;
        if(FractalGUI.isFullScreen)comboPanel.setBounds((maxHeight*23)/24,0,(maxWidth-(maxHeight*23)/24)-10,maxHeight-hAdjust);
        else comboPanel.setBounds(((maxHeight*11)/24),0,((maxWidth/2)-((maxHeight*11)/24))-10,(maxHeight/2)-hAdjust);
        fractalFrame.add(comboPanel);
        fractalFrame.repaint();
        fractalFrame.setVisible(true);
    }//end createSimulation

    public static void updateChoices(){
        //change iteration options for koch fractal
        if(generationType.getSelectedIndex() == 1){
            iterations.removeAllItems();
            for(String s : kochItr){
                iterations.addItem(s);
            }//end for loop
            iterations.setSelectedIndex(0);
        }//end if statement
        if(generationType.getSelectedIndex() == 0){
            iterations.removeAllItems();
            for(String s : mandItr){
                iterations.addItem(s);
            }//end for loop
        }//end if statement
    }//end update Choices

    public static void updatePanel(){
        //determine items selected in comboBoxes
        int pos;
        pos = generationType.getSelectedIndex();
        String selectedGeneration = algorithms[pos];
        pos = type.getSelectedIndex();
        String selectedType = types[pos];
        int selectedItr;
        pos = set.getSelectedIndex();
        String selectedSet = setsArray[pos];
        pos = iterations.getSelectedIndex();

        if(fractalPanel != null) fractalFrame.remove(fractalPanel);
        switch(selectedGeneration){
            case "Mandelbrot":
                //run mandelbrot panel thread with entered iteration count
                selectedItr = Integer.parseInt(mandItr[pos]);
                fractalPanel = new MandelbrotCurve(selectedItr);
                break;
            case "Koch":
                //run koch panel thread with entered iteration count
                selectedItr = Integer.parseInt(kochItr[pos]);
                fractalPanel = new KochCurve(selectedItr);
                break;
            default:    //do nothing
                break;
        }//end switch statement
        //make updated frame visible
        fractalFrame.add(fractalPanel);
        fractalFrame.repaint();
        fractalFrame.setVisible(true);
    }//end updatePanel
}//end SimulateScreenK
