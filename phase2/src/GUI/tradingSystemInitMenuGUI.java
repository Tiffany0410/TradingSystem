package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.maincontrollers.TradingSystem;

public class tradingSystemInitMenuGUI{
    private JButton a1LoginButton;
    private JPanel panel1;
    private JButton a2LoginAsGuestButton;
    private JButton a3CreateAccountButton;
    private JButton exitButton;
    private TradingSystem tradingSystem;



    public void run(AccountCreator accountCreator, LoginValidator loginValidator) {
        JFrame frame = new JFrame("Trading System");
        frame.setContentPane(new tradingSystemInitMenuGUI(accountCreator, loginValidator).panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public tradingSystemInitMenuGUI(AccountCreator accountCreator, LoginValidator loginValidator){

        a1LoginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click the button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //login
                loginGUI login = new loginGUI(loginValidator);
                login.run(loginValidator);
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
                //TODO: Implement login as guest

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
                regularUserCreateAccountGUI createAccount = new regularUserCreateAccountGUI(accountCreator);
                createAccount.run(accountCreator);
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
