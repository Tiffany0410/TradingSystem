package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserManageUsersConfirmInventoryWindow;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserManagerUsersWindow;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to show admin user manage user sub menu gui
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserManageUsersSubMenuGUI {
    private JPanel rootPanel;
    private JButton freezeUsersButton;
    private JButton unfreezeUsersButton;
    private JButton confirmAndAddItemButton;
    private JButton backButton;

    /**
     * Constructor of admin user manage users sub menu gui
     * @param muc admin user manager users controller
     * @param guiDemo GUIDemo
     * @param sm system manager
     * @param idc regular use id checker
     * @param oic admin user other info checker
     */
    public AdminUserManageUsersSubMenuGUI(AdminUserManagerUsersController muc, GUIDemo guiDemo, SystemMessage sm,
                                          RegularUserIDChecker idc, AdminUserOtherInfoChecker oic) {
        freezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                int option = 1;
                String info = muc.getAllUnfreezeUser();

                if (info.equalsIgnoreCase("No users can be frozen")){
                    guiDemo.printNotification("No users can be frozen");
                }else {
                    String inputName = "Please enter the username: ";
                    AdminUserManagerUsersWindow adminUserManagerUsersWindow = new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc);
                    adminUserManagerUsersWindow.run(option, guiDemo, inputName, info, muc);

                    guiDemo.runSave();
                }

            }
        });
        unfreezeUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = 2;
                String info = muc.getWantUnfreezeUser();


                if (info.equalsIgnoreCase("There are no user request to unfreeze")){
                    guiDemo.printNotification("There are no user request to unfreeze");
                }else {
                    String inputName = "Please enter the username: ";
                    AdminUserManagerUsersWindow adminUserManagerUsersWindow = new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc);
                    adminUserManagerUsersWindow.run(option, guiDemo, inputName, info, muc);

                    guiDemo.runSave();
                }
            }
        });
        confirmAndAddItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do realted operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> listItemToAdd = new ArrayList<>(muc.seeListItemToAdd());

                if (listItemToAdd.isEmpty()){
                    guiDemo.printNotification("There are no items-to-add requests.");
                }
                else{
                    String string = "Here's the list of items-to-add requests:\n" +
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
             * Invoked when click button and do realted opeations
             *
             * @param e click button
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

    /**
     * Run admin user manager users sub menus gui
     * @param adminUserManagerUsersController adminUserManagerUsersController
     * @param guiDemo GUIDemo
     * @param sm sysytem message
     * @param idc Regular user ID checker
     * @param oic admin user other info checker
     */
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
