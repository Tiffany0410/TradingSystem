package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminUserManageUsersSubMenuGUI {
    private JPanel rootPanel;
    private JButton freezeUsersButton;
    private JButton unfreezeUsersButton;
    private JButton confirmAndAddItemButton;
    private JButton backButton;

    public AdminUserManageUsersSubMenuGUI(AdminUserManagerUsersController muc, GUIDemo guiDemo, SystemMessage sm,
                                          RegularUserIDChecker idc, AdminUserOtherInfoChecker oic) {
        freezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
//                String regularUserName = guiDemo.getInPut(muc.getAllUnfreezeUser());
//
//                if (!regularUserName.equals("User inputs nothing")) {
//                    String result = muc.freezeUser(regularUserName);
//                }
//                guiDemo.runSave();


//                String string = muc.getAllUnfreezeUser();
//                guiDemo.getInPut(string);
//                String regularUserName = guiUserInputInfo.getTempUserInput();
//                if (regularUserName != null) {String result = muc.freezeUser(regularUserName);
//                    guiDemo.printNotification(result);}

                int option = 1;
                String info = muc.getAllUnfreezeUser();
                String inputName = "Please enter the username of the user you want to freeze: ";

                AdminUserManagerUsersWindow adminUserManagerUsersWindow = new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc);
                adminUserManagerUsersWindow.run(option, guiDemo, inputName, info, muc);

                guiDemo.runSave();


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
//                String string = muc.getWantUnfreezeUser();
//                String result = muc.unfreezeUser(guiDemo.getInPut(string));
//                guiDemo.printNotification(result);
//                guiDemo.runSave();

                int option = 2;
                String info = muc.getWantUnfreezeUser();
                String inputName = "Please enter the username of the user you want to unfreeze: ";

                AdminUserManagerUsersWindow adminUserManagerUsersWindow = new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc);
                adminUserManagerUsersWindow.run(option, guiDemo, inputName, info, muc);

                guiDemo.runSave();
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
                //adminUserManagerUsersController.confirmInventoryAdd();
                ArrayList<Item> listItemToAdd = new ArrayList<>(muc.seeListItemToAdd());

                if (listItemToAdd.isEmpty()){
                    guiDemo.printNotification("There is no items-to-add requests.");
                }
                else{
                    String string = "Here's a list of items-to-add requests:\n" +
                            sm.printListNumberedObject(new ArrayList<>(listItemToAdd)) +
                            "\nPlease enter the number beside the # of the request you want to act on: \n";
                    AdminUserManageUsersConfirmInventoryWindow window = new
                            AdminUserManageUsersConfirmInventoryWindow(string, listItemToAdd, muc, guiDemo, sm, idc, oic);
                    window.run(string, listItemToAdd, muc, guiDemo, sm, idc, oic);
                }

                guiDemo.runSave();

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
                //close window and then go back to main menu
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserMainMenu();


            }
        });
    }

    public void run(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiDemo,
                    SystemMessage sm, RegularUserIDChecker idc, AdminUserOtherInfoChecker oic) {
        JFrame frame = new JFrame("adminUserManageUsersSubMenuGUI");
        frame.setContentPane(new AdminUserManageUsersSubMenuGUI(adminUserManagerUsersController, guiDemo, sm, idc, oic).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
