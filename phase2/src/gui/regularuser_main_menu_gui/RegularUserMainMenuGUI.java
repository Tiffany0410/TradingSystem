package gui.regularuser_main_menu_gui;

import controllers.regularusersubcontrollers.*;
import gateway.FilesReaderWriter;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
                    RegularUserMeetingMenuController mmc, RegularUserTradingMenuController atc,
                    RegularUserIDChecker idChecker, RegularUserDateTimeChecker dateTimeChecker,
                    RegularUserThresholdController tc, RegularUserOtherInfoChecker otherInfoChecker, GUIUserInputInfo guiUserInputInfo,
                    String username, UserManager um, String menuPartOfAlert,
                    ArrayList<Integer> thresholdValues) {

        JFrame frame = new JFrame("regularUserMainMenuGUI");
        frame.setContentPane(new RegularUserMainMenuGUI(guest, sm, guiD, amc, mmc, atc, idChecker, dateTimeChecker, tc, otherInfoChecker,
                guiUserInputInfo, username, um, menuPartOfAlert, thresholdValues).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public RegularUserMainMenuGUI(boolean guest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc,
                                  RegularUserMeetingMenuController mmc, RegularUserTradingMenuController atc,
                                  RegularUserIDChecker idChecker, RegularUserDateTimeChecker dateTimeChecker,
                                  RegularUserThresholdController tc, RegularUserOtherInfoChecker otherInfoChecker, GUIUserInputInfo guiUserInputInfo,
                                  String username, UserManager um, String menuPartOfAlert,
                                  ArrayList<Integer> thresholdValues) {
        notificationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: do the following in GUIDemo:
                // //String filepath = "./src/Alerts/UserAlerts.csv";
                // //rw.readFromMenu(filepath)
                // ^ to read the menuPartOfAlert
                // // thresholdValuesFilePath = "./configs/thresholdvaluesfile/ThresholdValues.csv"
                // //List<Integer> thresholdValues = rw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
                // ^ to read the thresholdValues
                guiD.printNotification(sm.regUserAlerts(um, tc, username, menuPartOfAlert, thresholdValues));

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
                if (!guest) {
                    //Call Trading Info Menu and close this window
                    guiD.runRegularUserTradingMenuGUI();
                }
                else{
                    sm.msgForGuest();
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
                if (!guest) {
                    //Call Meeting Info Menu and close this window
                    guiD.runRegularUserMeetingMenu(guiD, mmc, sm, thresholdValues.get(3), guiUserInputInfo,
                            idChecker);
                }
                else{
                    sm.msgForGuest();
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
                if (!guest) {
                    // Call Searching Info Menu and close this window
                    guiD.runRegularUserSearchingMenuGUI();
                }
                else{
                    sm.msgForGuest();
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
                if (!guest) {
                    // Call Community Info Menu and close this window
                    guiD.runRegularUserCommunityMenuGUI();
                }
                else{
                    sm.msgForGuest();
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

            }
        });
    }
}
