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

    public tradingSystemInitMenuGUI(){}

    public void tradingSystemInitMenu() {
        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                TradingSystem.tradingSystemInital(1);
            }
        });
    }
}
