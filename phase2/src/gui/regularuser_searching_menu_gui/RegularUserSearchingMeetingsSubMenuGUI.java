package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import managers.meetingmanager.Meeting;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingMeetingsSubMenuGUI {
    private JPanel rootPanel;
    private JButton sortByDateButton;
    private JButton incompleteMeetingButton;
    private JButton completeMeetingButton;
    private JButton backButton;

    public RegularUserSearchingMeetingsSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                                  GUIDemo guiDemo, SystemMessage systemMessage) {
        sortByDateButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Meeting> m = regularUserSearchingMenuController.allMeetingSortByDate();
                if (m.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(m));
                }
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
                try {
                List<managers.meetingmanager.Meeting> m = regularUserSearchingMenuController.unCompleteMeetingSortByDate();
                    if (m.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(m));
                }
                } catch (Exception ex) {
                    systemMessage.invalidInput();
                }

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
                List<managers.meetingmanager.Meeting> m = regularUserSearchingMenuController.completeMeetingSortByDate();
                if (m.size() == 0) {
                systemMessage.msgForNothing();
                } else {
                systemMessage.printResult(new ArrayList<>(m));
                }
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

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingMeetingsSubMenu");
        frame.setContentPane(new RegularUserSearchingMeetingsSubMenuGUI(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
