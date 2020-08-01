package gui.adminuser_menus_gui;

import gui.GUIDemo;

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

    public AdminUserMainMenuGUI(GUIDemo guiDemo) {
        manageUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserManageUsersSubMenu();
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
                    guiDemo.runAdminUserEditThresholdsSubMenu();
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
                guiDemo.runAdminUserHistoricalActionsSubMenu();

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
                guiDemo.runAdminUserOtherSubMenu();

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
                guiDemo.runTradingSystemInitMenuGUI();

                // TODO: Need method to close this window


            }
        });
    }

    public void run(GUIDemo guiDemo) {
        JFrame frame = new JFrame("adminUserMainMenuGUI");
        frame.setContentPane(new AdminUserMainMenuGUI(guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
