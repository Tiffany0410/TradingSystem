package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserSearchingMeetingsSubMenu {
    private JPanel rootPanel;
    private JButton sortByDateButton;
    private JButton incompleteMeetingButton;
    private JButton completeMeetingButton;
    private JButton backButton;

    public RegularUserSearchingMeetingsSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo) {
        sortByDateButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.allMeetingSortByDate();
                // TODO: Need method to close this window
            }
        });
        incompleteMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.unCompleteMeetingSortByDate();
                // TODO: Need method to close this window
            }
        });
        completeMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                regularUserSearchingMenuController.completeMeetingSortByDate();
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
        JFrame frame = new JFrame("RegularUserSearchingMeetingsSubMenu");
        frame.setContentPane(new RegularUserSearchingMeetingsSubMenu(regularUserSearchingMenuController, guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
