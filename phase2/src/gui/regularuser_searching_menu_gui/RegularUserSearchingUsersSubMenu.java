package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserSearchingUsersSubMenu {
    private JPanel rootPanel;
    private JButton recentTradeUserButton;
    private JButton frequentTradeUserButton;
    private JButton sortUserByRatingButton;
    private JButton backButton;

    public RegularUserSearchingUsersSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo) {
        recentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.recentThreePartner();
                // TODO: Need method to close this window

            }
        });
        frequentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call controller frequent user method
                // TODO: Need method to close this window

            }
        });
        sortUserByRatingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call controller sort user by rating method
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
                guiDemo.runRegularUserSearchingMenuGUI();
                // TODO: Need method to close this window

            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo) {
        JFrame frame = new JFrame("RegularUserSearchingUsersSubMenu");
        frame.setContentPane(new RegularUserSearchingUsersSubMenu(regularUserSearchingMenuController, guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
