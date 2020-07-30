package GUI;

import controllers.AccountCreator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton createButton;
    private JButton cancelButton;

    public adminCreateAccountGUI(AccountCreator accountCreator) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!new String(passwordField1.getPassword()).equals(new String(passwordField2.getPassword()))){
                    //TODO: Show a JDialog said two password are not same, check again

                } else {
                    //TODO: Call Account Creator to create an admin account
                }
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
                //TODO: Back to Admin Main Menu and close this window
            }
        });
    }

    public void run(AccountCreator accountCreator) {
        JFrame frame = new JFrame("createAccountGUI");
        frame.setContentPane(new adminCreateAccountGUI(accountCreator).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
