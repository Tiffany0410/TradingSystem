package gui.adminuser_menus_gui;

import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI of admin user main menu
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserMainMenuGUI {
    private JPanel rootPanel;
    private JLabel topLabel;
    private JButton manageUserButton;
    private JButton editThresholdsButton;
    private JButton manageHistoricalActionsButton;
    private JButton othersButton;
    private JButton logoutButton;
    private JButton notificationButton;

    /**
     * Constructor of admin user main menu gui
     * @param guiDemo GUIDemo
     * @param adminPartOfNotification admin user notification
     */
    public AdminUserMainMenuGUI(GUIDemo guiDemo, String adminPartOfNotification) {
        notificationButton.addActionListener(new ActionListener() {
         /**
          * Invoked when click button and do related operations
          *
          * @param e the event to be processed
          */
         @Override
         public void actionPerformed(ActionEvent e) {
             guiDemo.printNotification(adminPartOfNotification);
         }
         });
        manageUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserManageUsersSubMenu();

            }
        });
        editThresholdsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserEditThresholdsSubMenu();


            }
        });
        manageHistoricalActionsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do realted operations
             *
             * @param e clicj button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserHistoricalActionsSubMenu();


            }
        });
        othersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
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
        logoutButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do realted operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runTradingSystemInitMenuGUI();
            }
        });
    }

    /**
     * Run admin user main menu
     * @param guiDemo GUIDemo
     * @param adminNotification admin user notification
     */
    public void run(GUIDemo guiDemo, String adminNotification) {
        JFrame frame = new JFrame("adminUserMainMenuGUI");
        frame.setContentPane(new AdminUserMainMenuGUI(guiDemo, adminNotification).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
