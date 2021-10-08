package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    public static boolean isFullScreen = false;
    static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    public static void main(String[] args) {

        JFrame frame = new JFrame("Welcome to CSE Fractal Simulation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton start_sim = new JButton("Start Simulation");
        JButton enter_settings = new JButton("Settings");
        JButton quit_game = new JButton("Quit");
        Rectangle R = new Rectangle(640, 480);
        Dimension D = new Dimension(640, 480);

        start_sim.setBounds(40, 40, 400, 100);
        enter_settings.setBounds(40, 160, 400, 100);
        quit_game.setBounds(40, 280, 400, 100);

        ActionListener startButtonPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame.dispose();
                //TODO: Allow for selection of Koch Curve or other types (currently Mandelbrot in Simulation
                //CURRENTLY, JUST UNCOMMENT THE DESIRED ALGORITHM
                SimulationScreen.main(null);
                //KochCurveScreen.main(null);
            }
        };
        ActionListener settingsButtonPressed = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JButton fullscreen = new JButton("Fullscreen");
                JButton normalscreen = new JButton("Default Resolution");
                JButton back = new JButton("Back");
                if(isFullScreen) {

                    fullscreen.setBounds(200, 100, 1000, 200);
                    normalscreen.setBounds(200, 350, 1000, 200);
                    back.setBounds(200, 600, 1000, 200);
                    frame.repaint();
                }

                else {

                    fullscreen.setBounds(40, 40, 400, 100);
                    normalscreen.setBounds(40, 160, 400, 100);
                    back.setBounds(40, 280, 400, 100);
                    frame.repaint();
                }
                ActionListener fullscreenPressed = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.setFullScreenWindow(frame);
                        isFullScreen = true;
                        fullscreen.setBounds(200, 100, 1000, 200);
                        normalscreen.setBounds(200, 350, 1000, 200);
                        back.setBounds(200, 600, 1000, 200);
                        frame.repaint();
                    }
                };
                ActionListener normalPressed = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        device.setFullScreenWindow(null);
                        isFullScreen = false;
                        fullscreen.setBounds(40, 40, 400, 100);
                        normalscreen.setBounds(40, 160, 400, 100);
                        back.setBounds(40, 280, 400, 100);
                        frame.repaint();
                    }
                };
                ActionListener backPressed = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.remove(fullscreen);
                        frame.remove(normalscreen);
                        frame.remove(back);
                        frame.add(start_sim);
                        frame.add(enter_settings);
                        frame.add(quit_game);
                        if(isFullScreen) {
                            start_sim.setBounds(200, 100, 1000, 200);
                            enter_settings.setBounds(200, 350, 1000, 200);
                            quit_game.setBounds(200, 600, 1000, 200);
                            frame.repaint();
                        }

                        else {
                            start_sim.setBounds(40, 40, 400, 100);
                            enter_settings.setBounds(40, 160, 400, 100);
                            quit_game.setBounds(40, 280, 400, 100);
                            frame.repaint();
                        }
                    }
                };
                fullscreen.addActionListener(fullscreenPressed);
                normalscreen.addActionListener(normalPressed);
                back.addActionListener(backPressed);


                frame.remove(start_sim);
                frame.remove(enter_settings);
                frame.remove(quit_game);

                frame.add(fullscreen);
                frame.add(normalscreen);
                frame.add(back);
                frame.repaint();

            }
        };

        ActionListener exitButtonPressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };

        start_sim.addActionListener(startButtonPressed);
        enter_settings.addActionListener(settingsButtonPressed);
        quit_game.addActionListener(exitButtonPressed);

        frame.repaint();
        frame.add(start_sim);
        frame.add(enter_settings);
        frame.add(quit_game);
        frame.setSize(D);

        frame.setLayout(null);
        frame.setVisible(true);
    }
}
