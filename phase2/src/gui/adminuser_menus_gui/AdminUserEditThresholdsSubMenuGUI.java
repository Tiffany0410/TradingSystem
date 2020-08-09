package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserEditThresholdsController;
import gui.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserEditUserThresholdsWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserEditThresholdsSubMenuGUI {
    private JPanel rootPanel;
    private JButton editTheMaxNumberButton;
    private JButton editTheMaxNumberButton1;
    private JButton editTheNumberOfButton;
    private JButton editTheMaxEditsButton;
    private JButton backButton;

    public AdminUserEditThresholdsSubMenuGUI(GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController
                                             ) {
        editTheMaxNumberButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberTransactions();
                int option = 1;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                //close this window
                //guiDemo.closeWindow(rootPanel);
            }
        });
        editTheMaxNumberButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberIncompleteTransactions();
                int option = 2;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                //close this window
                //guiDemo.closeWindow(rootPanel);

            }
        });
        editTheNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMustLendNumber();
                int option = 3;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                //close this window
                //guiDemo.closeWindow(rootPanel);

            }
        });
        editTheMaxEditsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getEditMaxEdits();
                int option = 4;

                AdminUserEditUserThresholdsWindow adminUserEditUserThresholdsWindow = new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController);
                adminUserEditUserThresholdsWindow.run(string, option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                //close this window
                guiDemo.closeWindow(rootPanel);

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
                //close this window
                guiDemo.closeWindow(rootPanel);

                guiDemo.runAdminUserMainMenu();

            }
        });
    }

    public void run(GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        JFrame frame = new JFrame("AdminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsSubMenuGUI(guiDemo, adminUserEditThresholdsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
