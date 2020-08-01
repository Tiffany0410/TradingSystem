package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserSearchingTradesSubMenu {
    private JPanel rootPanel;
    private JButton incompleteTradesButton;
    private JButton completeTradesButton;

    public RegularUserSearchingTradesSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo) {
        incompleteTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.filterIncompleteTrade();
                // TODO: Need method to close this window

            }
        });
        completeTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.filterCompleteTrade();
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
        JFrame frame = new JFrame("RegularUserSearchingTradesSubMenu");
        frame.setContentPane(new RegularUserSearchingTradesSubMenu(regularUserSearchingMenuController, guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton backButton;
}
