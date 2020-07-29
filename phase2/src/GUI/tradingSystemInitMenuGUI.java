package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.maincontrollers.TradingSystem;

public class tradingSystemInitMenuGUI {
    private JButton a1LoginButton;
    private JPanel panel1;
    private JButton a2LoginAsGuestButton;
    private JButton a3CreateAccountButton;
    private JButton exitButton;
    private JTextArea welcomeToTradingSystemTextArea;
    private TradingSystem tradingSystem;



    public void run(TradingSystem tradingSystem) {
        this.tradingSystem = tradingSystem;
        JFrame frame = new JFrame("Trading System");
        frame.setContentPane(new tradingSystemInitMenuGUI(tradingSystem).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public tradingSystemInitMenuGUI(TradingSystem tradingSystem){

        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                tradingSystemInitMenuGUI.this.tradingSystem.tradingSystemInital(1);
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
                tradingSystemInitMenuGUI.this.tradingSystem.tradingSystemInital(2);
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
                tradingSystemInitMenuGUI.this.tradingSystem.tradingSystemInital(3);
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
                tradingSystemInitMenuGUI.this.tradingSystem.tradingSystemInital(0);
            }
        });

    }

}
