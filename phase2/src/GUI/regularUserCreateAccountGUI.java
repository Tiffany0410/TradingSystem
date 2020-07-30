package GUI;

import controllers.AccountCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class regularUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JTextField emailTextField;
    private JTextField cityTextField;
    private JButton createButton;
    private JButton cancelButton;

    public regularUserCreateAccountGUI(AccountCreator accountCreator) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Account creator to create a regular user account and close this window

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: return to Trading System Init Meun and close this window
            }
        });
    }

    public void run(AccountCreator accountCreator) {
        JFrame frame = new JFrame("regularUserCreateAccount");
        frame.setContentPane(new regularUserCreateAccountGUI(accountCreator).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
