package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserOtherActionsController;
import demomanager.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserSetTimeWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Used to show admin user other sub menu gui
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserOtherSubMenuGUI {
    private JPanel rootPanel;
    private JButton addNewAdminUserButton;
    private JButton backButton;
    private JButton setSystemTimeButton;

    /**
     * Constructor of admin user other submenu gui
     * @param guiDemo GUIDemo
     * @param adminUserOtherActionsController adminUserOtherActionsController
     */
    public AdminUserOtherSubMenuGUI(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        addNewAdminUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runSave();
                guiDemo.runAdminUserCreateAccount();

            }
        });
        setSystemTimeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                AdminUserSetTimeWindow adminUserSetTimeWindow = new AdminUserSetTimeWindow(guiDemo, adminUserOtherActionsController);
                adminUserSetTimeWindow.run(guiDemo, adminUserOtherActionsController);
                guiDemo.closeWindow(rootPanel);
                guiDemo.runSave();

            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserMainMenu();
            }
        });

    }

    /**
     * Run admin user other sub menu gui
     * @param guiDemo GUIDemo
     * @param adminUserOtherActionsController adminUserOtherActionsController
     */
    public void run(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        JFrame frame = new JFrame("adminUserOtherSubMenuGUI");
        frame.setContentPane(new AdminUserOtherSubMenuGUI(guiDemo, adminUserOtherActionsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
