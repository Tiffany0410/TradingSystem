package gui.adminuser_menus_gui;

import gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserMainMenuGUI {
    private JPanel rootPanel;
    private JLabel topLabel;
    private JButton manageUserButton;
    private JButton editThresholdsButton;
    private JButton manageHistoricalActionsButton;
    private JButton othersButton;
    private JButton logoutButton;

    public AdminUserMainMenuGUI(GUIController guiController) {
        manageUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.runAdminUserManageUsersSubMenu();
                // TODO: Need method to close this window


            }
        });
        editThresholdsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                    guiController.runAdminUserEditThresholdsSubMenu();
                // TODO: Need method to close this window


            }
        });
        manageHistoricalActionsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.runAdminUserHistoricalActionsSubMenu();

                // TODO: Need method to close this window


            }
        });
        othersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.runAdminUserOtherSubMenu();

                // TODO: Need method to close this window




            }
        });
        logoutButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiController.runTradingSystemInitMenuGUI();

                // TODO: Need method to close this window


            }
        });
    }

    public void run(GUIController guiController) {
        JFrame frame = new JFrame("adminUserMainMenuGUI");
        frame.setContentPane(new AdminUserMainMenuGUI(guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
