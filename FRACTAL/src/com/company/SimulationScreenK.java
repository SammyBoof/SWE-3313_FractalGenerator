package com.company;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class SimulationScreenK implements Runnable{
    //declare attributes
    private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    private static MyJFrame fractalFrame;
    private static PaintPanel fractalPanel;
    public static JComboBox generationType,type,iterations,set;
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int maxHeight = (int) screenSize.getHeight();
    private static final int maxWidth = (int) screenSize.getWidth();
    private static final String[] algorithms = {"Mandelbrot","Koch Curve","third","fourth"};
    private static final String[] types = {"Triangle","Curve","3","4"};
    private static final String[] recursiveRuns = {"1","3","5","10","1000","10000","50000"};
    private static final String[] setsArray = {"a","b","c","d"};

    //create listeners
    public static class generationTypeListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            fractalFrame.remove(fractalPanel);
            int pos = generationType.getSelectedIndex();
            PaintPanel.selection = pos;
            fractalPanel = new PaintPanel(Integer.parseInt(recursiveRuns[pos]));
            fractalFrame.add(fractalPanel);
            fractalFrame.repaint();
            fractalFrame.setVisible(true);

            //update paint
        }//end actionPerformed
    }//end generationTypeListener
    public static class typeListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            int pos = type.getSelectedIndex();
            PaintPanel.type = pos;
        }//end actionPerformed
    }//end typeListener
    public static class iterationListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //create new panel with new iteration count
            fractalFrame.remove(fractalPanel);
            int pos = iterations.getSelectedIndex();
            fractalPanel = new PaintPanel(Integer.parseInt(recursiveRuns[pos]));
            fractalFrame.add(fractalPanel);
            fractalFrame.repaint();
            fractalFrame.setVisible(true);
        }//end actionPerformed
    }//end iterationsListener
    public static class setListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //update paint
        }//end actionPerformed
    }//end setListener
    public static class escapeListener implements KeyListener{
        @Override
        public void keyTyped(KeyEvent e) {/*do nothing*/}
        @Override
        public void keyPressed(KeyEvent e) {/*do nothing*/}
        @Override
        public void keyReleased(KeyEvent e) {
            //close when escape is pressed
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
                fractalFrame.dispose();
                System.exit(0);
        }//end keyReleased
    }//end escapeListener

    public SimulationScreenK(){
        //create frame
        fractalFrame = new MyJFrame("Simulation");
        Thread simulation = new Thread(this,"SimulationThread");
        simulation.start();
    }//end constructor

    @Override
    public void run() {createSimulation();}//end run

    public void createSimulation(){
        //create listeners
        generationTypeListener gList = new generationTypeListener();
        typeListener tList = new typeListener();
        iterationListener iList = new iterationListener();
        setListener sList = new setListener();
        escapeListener eList = new escapeListener();

        //create frame
        if (FractalGUI.isFullScreen) device.setFullScreenWindow(fractalFrame);
        else fractalFrame.setSize((maxWidth/2), (maxHeight/2));
        fractalFrame.setLayout(null);
        fractalFrame.addKeyListener(eList);
        fractalFrame.setFocusable(true);

        //create panel for painting
        fractalPanel = new PaintPanel(1);
        fractalFrame.add(fractalPanel);

        //create panel for comboBoxes
        JPanel comboPanel = new JPanel();
        comboPanel.setBackground(Color.LIGHT_GRAY);
        comboPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.setLayout(new GridLayout(4,2));
        //generation ComboBox/Jlabel
        JLabel genDescription = new JLabel("Generation Type:",JLabel.CENTER);
        genDescription.setBackground(Color.LIGHT_GRAY);
        genDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(genDescription);
        generationType = new JComboBox<String>(algorithms);
        generationType.addActionListener(gList);
        generationType.addKeyListener(eList);
        generationType.setSelectedIndex(0);
        comboPanel.add(generationType);
        //type ComboBox/Jlabel
        JLabel typeDescription = new JLabel("Fractal Type:",JLabel.CENTER);
        typeDescription.setBackground(Color.LIGHT_GRAY);
        typeDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(typeDescription);
        type = new JComboBox<String>(types);
        type.addActionListener(tList);
        type.addKeyListener(eList);
        type.setSelectedIndex(0);
        comboPanel.add(type);
        //iterations ComboBox/Jlabel
        JLabel itrDescription = new JLabel("Iterations:",JLabel.CENTER);
        itrDescription.setBackground(Color.LIGHT_GRAY);
        itrDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(itrDescription);
        iterations = new JComboBox<String>(recursiveRuns);
        iterations.addActionListener(iList);
        iterations.addKeyListener(eList);
        iterations.setSelectedIndex(0);
        comboPanel.add(iterations);
        //setComboBox/Jlabel
        JLabel setDescription = new JLabel("Set:",JLabel.CENTER);
        setDescription.setBackground(Color.LIGHT_GRAY);
        setDescription.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        comboPanel.add(setDescription);
        set = new JComboBox<String>(setsArray);
        set.addActionListener(sList);
        set.addKeyListener(eList);
        set.setSelectedIndex(0);
        comboPanel.add(set);
        //add comboBox panel to frame
        //height adjusted to account for title at top
        int hAdjust = maxHeight/24;
        if(FractalGUI.isFullScreen)comboPanel.setBounds((maxHeight*23)/24,0,(maxWidth-(maxHeight*23)/24)-10,maxHeight-hAdjust);
        else comboPanel.setBounds(((maxHeight*11)/24),0,((maxWidth/2)-((maxHeight*11)/24))-10,(maxHeight/2)-hAdjust);
        fractalFrame.add(comboPanel);
        fractalFrame.repaint();
        fractalFrame.setVisible(true);
    }//end createSimulation
}//end SimulateScreenK
