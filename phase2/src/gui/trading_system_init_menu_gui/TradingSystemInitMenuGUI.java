package gui.trading_system_init_menu_gui;


import gui.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TradingSystemInitMenuGUI {
    private JButton a1LoginButton;
    private JPanel panel1;
    private JButton a2LoginAsGuestButton;
    private JButton a3CreateAccountButton;
    private JButton exitButton;




    public void run(GUIDemo guiDemo) {
        JFrame frame = new JFrame("Trading System");
        frame.setContentPane(new TradingSystemInitMenuGUI(guiDemo).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocation(550, 300);
    }

    public TradingSystemInitMenuGUI(GUIDemo guiDemo){

        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login
                guiDemo.runLogin();
                guiDemo.closeWindow(panel1);
            }
        });

        a2LoginAsGuestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login as guest
                guiDemo.runRegularUserMainMenu(true);
                guiDemo.closeWindow(panel1);
            }
        });
        a3CreateAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Create account
                guiDemo.runRegularUserCreateAccount();
            }
        });
        exitButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit system
                System.exit(0);
            }
        });

    }

}
