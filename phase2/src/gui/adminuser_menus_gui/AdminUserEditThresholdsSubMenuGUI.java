package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserEditThresholdsController;
import demomanager.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserEditUserThresholdsWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Used for admin user edit thresholds submenu
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserEditThresholdsSubMenuGUI {
    private JPanel rootPanel;
    private JButton editTheMaxNumberButton;
    private JButton editTheMaxNumberButton1;
    private JButton editTheNumberOfButton;
    private JButton editTheMaxEditsButton;
    private JButton backButton;

    /**
     * Constructor of adminUserEditThresholdsSubMenuGUI
     * @param guiDemo GUIDemo
     * @param adminUserEditThresholdsController adminUserEditThresholdsController
     */
    public AdminUserEditThresholdsSubMenuGUI(GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController
                                             ) {
        editTheMaxNumberButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberTransactions();
                int option = 1;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
            }
        });
        editTheMaxNumberButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberIncompleteTransactions();
                int option = 2;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                }
        });
        editTheNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMustLendNumber();
                int option = 3;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                }
        });
        editTheMaxEditsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getEditMaxEdits();
                int option = 4;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                //close this window
                //guiDemo.closeWindow(rootPanel);

            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runSave();
                guiDemo.runAdminUserMainMenu();

            }
        });
    }

    /**
     * Run admin user edit thresholds sub mnue gui
     * @param guiDemo GUIDemo
     * @param adminUserEditThresholdsController adminUserEditThresholdsController
     */
    public void run(GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        JFrame frame = new JFrame("AdminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsSubMenuGUI(guiDemo, adminUserEditThresholdsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
