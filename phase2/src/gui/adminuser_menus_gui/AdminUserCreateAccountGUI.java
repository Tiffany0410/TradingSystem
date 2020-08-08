package gui.adminuser_menus_gui;

import controllers.AccountCreator;
import controllers.adminusersubcontrollers.AdminUserOtherActionsController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton createButton;
    private JButton cancelButton;

    public AdminUserCreateAccountGUI(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage,
                                     AdminUserOtherActionsController adminUserOtherActionsController) {
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                boolean result = accountCreator.createAccount("Admin", username,
                        new String(passwordField1.getPassword()), "None", "None");

                guiDemo.printNotification(systemMessage.printResult(result));

                if (result){adminUserOtherActionsController.addNewAdmin(username);}

                guiDemo.runSave();
                guiDemo.runAdminUserMainMenu();

                //close this window
                guiDemo.closeWindow(rootPanel);

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
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserOtherSubMenu();


            }
        });
    }

    public void run(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage,
                    AdminUserOtherActionsController adminUserOtherActionsController) {
        JFrame frame = new JFrame("createAccountGUI");
        frame.setContentPane(new AdminUserCreateAccountGUI(accountCreator, guiDemo, systemMessage, adminUserOtherActionsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
