package gui.adminuser_menus_gui;

import controllers.AccountCreator;
import controllers.adminusersubcontrollers.AdminUserOtherActionsController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Used to show admin create account GUI
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JButton cancelButton;
    private JButton createButton1;

    /**
     * Constructor of admin user create account gui
     * @param accountCreator account creator
     * @param guiDemo GUIDemo
     * @param systemMessage system message
     * @param adminUserOtherActionsController adminUserOtherActionsController
     */
    public AdminUserCreateAccountGUI(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage,
                                     AdminUserOtherActionsController adminUserOtherActionsController) {
        createButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when click create button
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameTextField.getText();
                boolean result = accountCreator.createAccount("Admin", username,
                        new String(passwordField1.getPassword()), "None", "None");

                guiDemo.printNotification( "Create account " + systemMessage.msgForResult(result));

                if (result){adminUserOtherActionsController.addNewAdmin(username);}
                guiDemo.runSave();

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserOtherSubMenu();

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and return to menu
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserOtherSubMenu();
            }
        });
    }

    /**
     * Run admin user create account gui
     * @param accountCreator account creator
     * @param guiDemo GUIDemo
     * @param systemMessage system message
     * @param adminUserOtherActionsController adminUserOtherActionsController
     */
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
