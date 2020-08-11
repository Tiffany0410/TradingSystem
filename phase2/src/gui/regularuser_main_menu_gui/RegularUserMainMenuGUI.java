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

/**
 * A class that is responsible for the view and getting input for user
 * when user browses the user main menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserMainMenuGUI {
    private JPanel rootPanel;
    private JButton accountInformationButton;
    private JButton tradingInformationButton;
    private JButton notificationButton;
    private JButton meetingInformationButton;
    private JButton searchingInformationButton;
    private JButton logoutButton;
    private JButton communityInformationButton;

    /**
     * Constructs a RegularUserMainMenuGUI.
     * @param guest Whether or not guest access should be granted.
     * @param sm The presenter.
     * @param guiD The GUI helper.
     * @param amc The RegularUserAccountMenuController.
     * @param tc The threshold controller.
     * @param username User's username.
     * @param um The user manager.
     * @param menuPartOfAlert The part of the notification read from the file.
     * @param thresholdValues A list of threshold values.
     */
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
                seeNotification(guest, guiD, sm, um, tc, username, menuPartOfAlert, thresholdValues);

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
                launchAccountMenu(guiD);

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
                launchTradingMenu(amc, guiD, sm);
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
                launchMeetingMenu(amc, guiD, sm);

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
                launchSearchingMenu(amc, guiD, sm);
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
                launchCommunityMenu(amc, guiD, sm);
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

    private void launchCommunityMenu(RegularUserAccountMenuController amc, GUIDemo guiD, SystemMessage sm) {
        if (amc.seeIfFrozen()){
            guiD.printNotification(sm.lockMessageForFrozen());
        }
        else{
            // Call Community Info Menu and close this window
            guiD.runRegularUserCommunityMenuGUI();
            guiD.closeWindow(rootPanel);
        }
    }

    private void launchSearchingMenu(RegularUserAccountMenuController amc, GUIDemo guiD, SystemMessage sm) {
        if (amc.seeIfFrozen()){
            guiD.printNotification(sm.lockMessageForFrozen());
        }
        else{
            // Call Searching Info Menu and close this window
            guiD.runRegularUserSearchingMenuGUI();
            guiD.closeWindow(rootPanel);
        }
    }

    private void launchMeetingMenu(RegularUserAccountMenuController amc, GUIDemo guiD, SystemMessage sm) {
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

    private void launchTradingMenu(RegularUserAccountMenuController amc, GUIDemo guiD, SystemMessage sm) {
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

    private void launchAccountMenu(GUIDemo guiD) {
        //Call Account Info Menu and close this window
        guiD.runRegularUserAccountMainMenuGUI();
        guiD.closeWindow(rootPanel);
    }

    private void seeNotification(boolean guest, GUIDemo guiD, SystemMessage sm, UserManager um, RegularUserThresholdController tc, String username, String menuPartOfAlert, ArrayList<Integer> thresholdValues) {
        if (!guest) {
            guiD.printNotification(sm.regUserAlerts(um, tc, username, menuPartOfAlert, thresholdValues));
            guiD.runSave();
        }
        else{
            guiD.printNotification(sm.msgForGuest());
        }
    }


    /**
     * Responsible for running this window.
     * @param guest Whether or not guest access should be granted.
     * @param sm The presenter.
     * @param guiD The GUI helper.
     * @param amc The RegularUserAccountMenuController.
     * @param tc The threshold controller.
     * @param username User's username.
     * @param um The user manager.
     * @param menuPartOfAlert The part of the notification read from the file.
     * @param thresholdValues A list of threshold values.
     */
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

}

