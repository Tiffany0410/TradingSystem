package gui.adminuser_menus_gui;

import controllers.AccountCreator;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton createButton;
    private JButton cancelButton;

    public AdminUserCreateAccountGUI(AccountCreator accountCreator, GUIDemo guiController) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                accountCreator.createAccount("Admin", usernameTextField.getText(),
                        new String(passwordField1.getPassword()), "None", "None");
                // TODO: Need method to close this window

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
                guiController.runAdminUserOtherSubMenu();
                // TODO: Need method to close this window

            }
        });
    }

    public void run(AccountCreator accountCreator, GUIDemo guiController) {
        JFrame frame = new JFrame("createAccountGUI");
        frame.setContentPane(new AdminUserCreateAccountGUI(accountCreator, guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
