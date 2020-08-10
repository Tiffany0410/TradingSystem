package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import demomanager.GUIDemo;
import managers.meetingmanager.Meeting;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to show regular searching window
 * @author Jiaqi Gong, Yangle Cheng
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserSearchingMeetingsSubMenuGUI {
    private JPanel rootPanel;
    private JButton sortByDateButton;
    private JButton incompleteMeetingButton;
    private JButton completeMeetingButton;
    private JButton backButton;

    /**
     * Constructor of regular user searching meeting submenu gui
     * @param regularUserSearchingMenuController regular user searching menu controller
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     */
    public RegularUserSearchingMeetingsSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                                  GUIDemo guiDemo, SystemMessage systemMessage) {
        sortByDateButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations.
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                List<Meeting> m = regularUserSearchingMenuController.allMeetingSortByDate();
                if (m.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(m)));
                }
            }
        });
        incompleteMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations.
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                List<managers.meetingmanager.Meeting> m = regularUserSearchingMenuController.unCompleteMeetingSortByDate();
                    if (m.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(m)));
                }
                } catch (Exception ex) {
                    guiDemo.printNotification(systemMessage.invalidInput());
                }
            }
        });
        completeMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations.
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                List<managers.meetingmanager.Meeting> m = regularUserSearchingMenuController.completeMeetingSortByDate();
                if (m.size() == 0) {
                guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(m)));
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations.
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runRegularUserSearchingMenuGUI();
            }
        });
    }

    /**
     * Run regular user searching meeting submenu gui
     * @param regularUserSearchingMenuController regular user searching menu controller
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     */
    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingMeetingsSubMenu");
        frame.setContentPane(new RegularUserSearchingMeetingsSubMenuGUI(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
