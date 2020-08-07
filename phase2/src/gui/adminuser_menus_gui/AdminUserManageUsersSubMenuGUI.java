package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
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

    public AdminUserManageUsersSubMenuGUI(AdminUserManagerUsersController muc, GUIDemo guiDemo,
                                          GUIUserInputInfo guiUserInputInfo, SystemMessage sm, RegularUserIDChecker idc,
                                          AdminUserOtherInfoChecker oic) {
        freezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = muc.getAllUnfreezeUser();
                guiDemo.getInPut(string);
                String regularUserName = guiUserInputInfo.getTempUserInput();
                if (regularUserName != null) {String result = muc.freezeUser(regularUserName);
                guiDemo.printNotification(result);}
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
                String string = muc.getWantUnfreezeUser();
                String result = muc.unfreezeUser(guiDemo.getInPut(string));
                guiDemo.printNotification(result);

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
                List<Item> listItemToAdd = muc.seeListItemToAdd();
                String str = sm.printListNumberedObject(new ArrayList<>(listItemToAdd));
                guiDemo.printNotification("Here's a list of items-to-add requests: " + str);
                String askItemRequestNum = "Please enter the number beside the # of the request you want to act on: ";
                String input1 = guiDemo.getInPut(askItemRequestNum);
                String askAddOrNot = "Do you choose to add or not add this item to the corresponding user's wish list? enter (1 - add, 2 - not add).";
                String input2 = guiDemo.getInPut(askAddOrNot);
                if (idc.checkInt(input1) && idc.checkInt(input2)){
                    int itemToAddNum = Integer.parseInt(input1);
                    int addOrNot = Integer.parseInt(input2);
                    if (oic.checkItemToAddNum(listItemToAdd.size(), itemToAddNum) && (addOrNot == 1 || addOrNot == 2)){
                        if (addOrNot == 1){
                            muc.addItemOrNot(itemToAddNum, true);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                        else{
                            muc.addItemOrNot(itemToAddNum, false);
                            guiDemo.printNotification(sm.msgForResult(true));
                        }
                    }
                    else{
                        guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                    }
                else{
                    guiDemo.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }

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

    public void run(AdminUserManagerUsersController adminUserManagerUsersController, GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo,
                    SystemMessage sm, RegularUserIDChecker idc, AdminUserOtherInfoChecker oic) {
        JFrame frame = new JFrame("adminUserManageUsersSubMenuGUI");
        frame.setContentPane(new AdminUserManageUsersSubMenuGUI(adminUserManagerUsersController, guiDemo, guiUserInputInfo,
                sm, idc, oic).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
