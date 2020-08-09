package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.*;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import managers.meetingmanager.Meeting;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserMeetingMenuGUI {
    private JPanel rootPanel;
    private JButton suggestOrConfirmTPButton;
    private JButton backButton;
    private JButton confirmTheMeetingTookButton;
    private JButton seeconfirmedMeetingsButton;

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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    editAndConfirmMeetingTP(guiD, mmc, sm, maxNumTPEdits, dtc, idC);
                    guiD.runSave();
                }
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    //1 window - print out meetings that need to confirmed that it took place --  plus meeting id and trade num -- plus confirm button on bottom
                    confirmMeetingTookPlace(mmc, sm, maxNumTPEdits, idC, guiD);
                    guiD.runSave();
                }
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
                guiD.runRegularUserMainMenu(guest);
                guiD.closeWindow(rootPanel);
            }
        });
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
