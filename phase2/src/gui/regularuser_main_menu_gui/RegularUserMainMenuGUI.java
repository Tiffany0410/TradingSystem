package gui.regularuser_main_menu_gui;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserThresholdController;
import demomanager.GUIDemo;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserMainMenuGUI {
    private JPanel rootPanel;
    private JButton accountInformationButton;
    private JButton tradingInformationButton;
    private JButton notificationButton;
    private JButton meetingInformationButton;
    private JButton searchingInformationButton;
    private JButton logoutButton;
    private JButton communityInformationButton;

    public void run(boolean guest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc,
                    RegularUserThresholdController tc, String username, UserManager um, String menuPartOfAlert,
                    ArrayList<Integer> thresholdValues) {

        JFrame frame = new JFrame("regularUserMainMenuGUI");
        frame.setContentPane(new RegularUserMainMenuGUI(guest, sm, guiD, amc,tc, username, um, menuPartOfAlert,
                thresholdValues).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    public RegularUserMainMenuGUI(boolean guest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc,
                                  RegularUserThresholdController tc, String username, UserManager um, String menuPartOfAlert,
                                  ArrayList<Integer> thresholdValues) {
        notificationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!guest) {
                    guiD.printNotification(sm.regUserAlerts(um, tc, username, menuPartOfAlert, thresholdValues));
                }
                else{
                    guiD.printNotification(sm.msgForGuest());
                }

            }
        });
        accountInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //Call Account Info Menu and close this window
                guiD.runRegularUserAccountMainMenuGUI();
                guiD.closeWindow(rootPanel);

            }
        });
        tradingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (amc.seeIfFrozen()){
                    guiD.printNotification(sm.lockMessageForFrozen());
                }
                else if (amc.seeIfOnVacation()){
                    guiD.printNotification(sm.lockMessageForVacation());
                }
                else{
                    guiD.runRegularUserTradingMenuGUI();
                    guiD.closeWindow(rootPanel);
                }
            }
        });
        meetingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (amc.seeIfFrozen()){
                    guiD.printNotification(sm.lockMessageForFrozen());
                }
                else if (amc.seeIfOnVacation()){
                    guiD.printNotification(sm.lockMessageForVacation());
                }
                else{
                    //Call Meeting Info Menu and close this window
                    guiD.runRegularUserMeetingMenu();
                    guiD.closeWindow(rootPanel);
                }

            }
        });
        searchingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (amc.seeIfFrozen()){
                    guiD.printNotification(sm.lockMessageForFrozen());
                }
                else{
                    // Call Searching Info Menu and close this window
                    guiD.runRegularUserSearchingMenuGUI();
                    guiD.closeWindow(rootPanel);
                }
            }
        });
        communityInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (amc.seeIfFrozen()){
                    guiD.printNotification(sm.lockMessageForFrozen());
                }
                else{
                    // Call Community Info Menu and close this window
                    guiD.runRegularUserCommunityMenuGUI();
                    guiD.closeWindow(rootPanel);
                }
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
                //Call Trading System Init Menu and close this window
                guiD.runTradingSystemInitMenuGUI();
                guiD.closeWindow(rootPanel);

            }
        });
    }
}

