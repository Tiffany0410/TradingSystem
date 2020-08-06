package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
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

    public AdminUserManageUsersSubMenuGUI(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo,
                                          SystemMessage sm, RegularUserIDChecker idc, AdminUserOtherInfoChecker oic) {
        freezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserManagerUsersController.getAllUnfreezeUser();
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                String result = adminUserManagerUsersController.freezeUser(guiUserInputInfo.getTempUserInput());
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
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                String result = adminUserManagerUsersController.unfreezeUser(guiUserInputInfo.getTempUserInput());
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
                //adminUserManagerUsersController.confirmInventoryAdd();
                List<Item> listItemToAdd = adminUserManagerUsersController.seeListItemToAdd();
                String str = sm.printListNumberedObject(new ArrayList<>(listItemToAdd));
                guiDemo.printNotification("Here's a list of items-to-add requests: " + str);
                String askItemRequestNum = "Please enter the number beside the # of the request you want to act on: ";
                String input1 = getInPut(askItemRequestNum, guiUserInputInfo);
                String askAddOrNot = "Do you choose to add or not add this item to the corresponding user's wish list? enter (1 - add, 2 - not add).";
                String input2 = getInPut(askAddOrNot, guiUserInputInfo);
                if (idc.checkInt(input1) && idc.checkInt(input2)){
                    int itemToAddNum = Integer.parseInt(input1);
                    int addOrNot = Integer.parseInt(input2);
                    if (oic.checkItemToAddNum(listItemToAdd.size(), itemToAddNum) && (addOrNot == 1 || addOrNot == 2)){
                        if (addOrNot == 1){
                            adminUserManagerUsersController.addItemOrNot(itemToAddNum, true);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                        else{
                            adminUserManagerUsersController.addItemOrNot(itemToAddNum, false);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                    }
                    else{
                        sm.tryAgainMsgForWrongInput();
                    }
                    }
                else{
                    sm.tryAgainMsgForWrongFormatInput();
                }

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
                guiDemo.runAdminUserMainMenu();
                // TODO: Need method to close this window


            }
        });
    }

    public void run(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo) {
        JFrame frame = new JFrame("adminUserManageUsersSubMenuGUI");
        frame.setContentPane(new AdminUserManageUsersSubMenuGUI(adminUserManagerUsersController, guiDemo, guiUserInputInfo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
