package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import gui.GUIDemo;
import gui.NotificationGUI;
import gui.UserInputGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserManageUsersSubMenuGUI {
    private JPanel rootPanel;
    private JButton freezeUsersButton;
    private JButton unfreezeUsersButton;
    private JButton confirmAndAddItemButton;
    private JButton backButton;

    public AdminUserManageUsersSubMenuGUI(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiController) {
        freezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserManagerUsersController.getAllUnfreezedUser();
                UserInputGUI userInputGUI = new UserInputGUI(string, guiController);
                userInputGUI.run(string, guiController);

                String result = adminUserManagerUsersController.freezeUser(guiController.getTempUserInput());
                NotificationGUI notificationGUI = new NotificationGUI(result);
                notificationGUI.run(result);


                // TODO: Need method to close this window

            }
        });
        unfreezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserManagerUsersController.getWantUnfreezeUser();
                UserInputGUI userInputGUI = new UserInputGUI(string, guiController);
                userInputGUI.run(string, guiController);

                String result = adminUserManagerUsersController.unfreezeUser(guiController.getTempUserInput());
                NotificationGUI notificationGUI = new NotificationGUI(result);
                notificationGUI.run(result);

                // TODO: Need method to close this window


            }
        });
        confirmAndAddItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Confirm Item and Add Item window and close this window

                // TODO: Need method to close this window


            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.runAdminUserMainMenu();
                // TODO: Need method to close this window


            }
        });
    }

    public void run(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiController) {
        JFrame frame = new JFrame("adminUserManageUsersSubMenuGUI");
        frame.setContentPane(new AdminUserManageUsersSubMenuGUI(adminUserManagerUsersController, guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
