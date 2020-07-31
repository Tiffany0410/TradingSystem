package gui.trading_system_init_menu_gui;


import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TradingSystemInitMenuGUI {
    private JButton a1LoginButton;
    private JPanel panel1;
    private JButton a2LoginAsGuestButton;
    private JButton a3CreateAccountButton;
    private JButton exitButton;




    public void run(GUIDemo guiController) {
        JFrame frame = new JFrame("Trading System");
        frame.setContentPane(new TradingSystemInitMenuGUI(guiController).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public TradingSystemInitMenuGUI(GUIDemo guiController){

        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login
                guiController.runLogin();
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
                guiController.runRegularUserMainMenu(true);

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
                guiController.runRegularUserCreateAccount();
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
