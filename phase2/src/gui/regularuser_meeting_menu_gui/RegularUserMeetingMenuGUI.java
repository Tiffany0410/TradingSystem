package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.*;
import demomanager.GUIDemo;
import managers.meetingmanager.Meeting;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is responsible for the view and getting input for user
 * when user browses the meeting menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserMeetingMenuGUI {

    private JPanel rootPanel;
    private JButton suggestOrConfirmTPButton;
    private JButton backButton;
    private JButton confirmTheMeetingTookButton;
    private JButton seeconfirmedMeetingsButton;

    /**
     * Constructs a RegularUserMeetingMenuGUI.
     * @param guiD The GUI helper.
     * @param mmc The RegularUserMeetingMenuController.
     * @param sm The presenter.
     * @param maxNumTPEdits The maximum number of time and place edits.
     * @param idC The id checker.
     * @param dtc The datetime checker.
     * @param guest Determines whether guest access is granted.
     */
    public RegularUserMeetingMenuGUI(GUIDemo guiD, RegularUserMeetingMenuController mmc, SystemMessage sm,
                                     int maxNumTPEdits, RegularUserIDChecker idC,
                                     RegularUserDateTimeChecker dtc, boolean guest) {

        suggestOrConfirmTPButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                suggestOrConfirmTP(guest, guiD, sm, mmc, maxNumTPEdits, dtc, idC);
            }
        });

        confirmTheMeetingTookButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmTheMeetingTookPlace(guest, guiD, sm, mmc, maxNumTPEdits, idC);
            }
        });

        seeconfirmedMeetingsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                seeConfirmedMeetings(guest, guiD, sm, mmc);
            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //GO back to main menu
                guiD.closeWindow(rootPanel);
                guiD.runRegularUserMainMenu(guest);
            }
        });
    }

    private void seeConfirmedMeetings(boolean guest, GUIDemo guiD, SystemMessage sm, RegularUserMeetingMenuController mmc) {
        if (guest) {
            guiD.printNotification(sm.msgForGuest());
        } else {
            //print the list of completed meetings
            List<Meeting> meetings = mmc.getCompletedMeetings();
            if (meetings.size() != 0) {
                String str = sm.printListObject(new ArrayList<>(meetings));
                guiD.printNotification("Here's your list of completed meetings: \n" + str);
            } else {
                guiD.printNotification(sm.msgForNothing("here."));
            }
            guiD.runSave();

        }
    }

    private void confirmTheMeetingTookPlace(boolean guest, GUIDemo guiD, SystemMessage sm, RegularUserMeetingMenuController mmc, int maxNumTPEdits, RegularUserIDChecker idC) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else {
            //1 window - print out meetings that need to confirmed that it took place --  plus meeting id and trade num -- plus confirm button on bottom
            confirmMeetingTookPlace(mmc, sm, maxNumTPEdits, idC, guiD);
            guiD.runSave();
        }
    }

    private void suggestOrConfirmTP(boolean guest, GUIDemo guiD, SystemMessage sm, RegularUserMeetingMenuController mmc, int maxNumTPEdits, RegularUserDateTimeChecker dtc, RegularUserIDChecker idC) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else {
            editAndConfirmMeetingTP(guiD, mmc, sm, maxNumTPEdits, dtc, idC);
            guiD.runSave();
        }
    }

    private void editAndConfirmMeetingTP(GUIDemo guiD, RegularUserMeetingMenuController mmc, SystemMessage sm,
                                         int maxEditTP, RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        if (mmc.isEmpty(mmc.getUnConfirmTimePlace())) {
            guiD.printNotification(sm.msgForNothing("here that requires action"));
        } else {
        String meetingsWUnconfirmedTP = sm.printListObject(new ArrayList<>(mmc.getUnConfirmTimePlace()));
        RegularUserCheckMeetingWindow suggestOrConfirmMeetingTPgui =
                        new RegularUserCheckMeetingWindow(guiD, meetingsWUnconfirmedTP, mmc, maxEditTP,
                                sm,  dtc, idc);
        suggestOrConfirmMeetingTPgui.run(guiD, meetingsWUnconfirmedTP, mmc, maxEditTP, sm,
                     dtc, idc);

        }
    }

    private void confirmMeetingTookPlace(RegularUserMeetingMenuController mmc, SystemMessage sm, int maxEditsTP, RegularUserIDChecker idc,
                                         GUIDemo guiD) {
        if (mmc.isEmpty(mmc.getUnconfirmedMeeting())) {
            guiD.printNotification(sm.msgForNothing("that needs to be confirmed"));
        }
        else {
        // print the meetings with unconfirmed meeting
        String str = sm.printListObject(new ArrayList<>(mmc.getUnconfirmedMeeting()));
        RegularUserSuggestMeetingWindow confirmMeetingGui =
                new RegularUserSuggestMeetingWindow(str, mmc, sm, maxEditsTP,
                        guiD, idc);
        confirmMeetingGui.run(str, mmc, sm, maxEditsTP,
                guiD, idc);

        }
    }


    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param mmc The RegularUserMeetingMenuController.
     * @param sm The presenter.
     * @param maxNumTPEdits The maximum number of time and place edits.
     * @param idC The id checker.
     * @param dtc The datetime checker.
     * @param isGuest Determines whether guest access is granted.
     */
    public void run(GUIDemo guiD, RegularUserMeetingMenuController mmc, SystemMessage sm,
                    int maxNumTPEdits,  RegularUserIDChecker idC,
                    RegularUserDateTimeChecker dtc, boolean isGuest) {
        JFrame frame = new JFrame("regularUserMeetingMenuGUI");
        frame.setContentPane(new RegularUserMeetingMenuGUI(guiD, mmc, sm, maxNumTPEdits,
                idC, dtc, isGuest).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }





}
