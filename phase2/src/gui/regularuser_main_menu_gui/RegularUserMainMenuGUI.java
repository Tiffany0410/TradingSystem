package gui.regularuser_main_menu_gui;

import controllers.regularusersubcontrollers.*;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
                    RegularUserOtherInfoChecker otherInfoChecker, GUIUserInputInfo guiUserInputInfo,
                    int numLentBeforeBorrow, int maxNumTransactionsAWeek, int maxEditsTP, int maxIncompleteTransactionsBeforeFrozen) {

        JFrame frame = new JFrame("regularUserMainMenuGUI");
        frame.setContentPane(new RegularUserMainMenuGUI(guest,sm, guiD, amc, mmc, atc, idChecker, dateTimeChecker,
                otherInfoChecker, guiUserInputInfo, numLentBeforeBorrow, maxNumTransactionsAWeek, maxEditsTP, maxIncompleteTransactionsBeforeFrozen).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public RegularUserMainMenuGUI(boolean guest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc,
                                  RegularUserMeetingMenuController mmc, RegularUserTradingMenuController atc,
                                  RegularUserIDChecker idChecker, RegularUserDateTimeChecker dateTimeChecker,
                                  RegularUserOtherInfoChecker otherInfoChecker, GUIUserInputInfo guiUserInputInfo,
                                  int numLentBeforeBorrow, int maxNumTransactionsAWeek, int maxEditsTP, int maxIncompleteTransactionsBeforeFrozen) {
        notificationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Notification which should be a JDialog
                // call RegUserAlerts from sm and pass in the relevant params -> printNote

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
                guiD.runRegularUserAccountMenuGUI();

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
                //Call Trading Info Menu and close this window
                guiD.runRegularUserTradingMenuGUI();
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
                //Call Meeting Info Menu and close this window
                guiD.runRegularUserMeetingMenu(guiD, mmc, sm, maxEditsTP, guiUserInputInfo,
                        idChecker);

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
                // Call Searching Info Menu and close this window
                guiD.runRegularUserSearchingMenuGUI();


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
                // Call Community Info Menu and close this window
                guiD.runRegularUserCommunityMenuGUI();
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
