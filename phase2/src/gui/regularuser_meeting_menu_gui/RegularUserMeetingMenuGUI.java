package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import exception.InvalidIdException;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import managers.meetingmanager.Meeting;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserMeetingMenuGUI {
    private JPanel rootPanel;
    private JButton outStandingMeetingButton;
    private JButton suggestOrConfirmTPButton;
    private JButton backButton;
    private JButton confirmTheMeetingTookButton;
    private JButton confirmedMeetingsButton;
    private JButton meetingsNeedToConfirmTPButton;

    public RegularUserMeetingMenuGUI(GUIDemo guiD, RegularUserMeetingMenuController mmc, SystemMessage sm,
                                     int maxNumTPEdits, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC) {

        suggestOrConfirmTPButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                editAndConfirmMeetingTP(mmc, sm, guiUserInputInfo, idC);
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

                confirmMeetingTookPlace(mmc, sm, guiUserInputInfo, idC, maxNumTPEdits);
            }
        });

        outStandingMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //print the list of unconfirmed meetings
                viewMeetings(sm, mmc.getUnconfirmedMeeting(), "unconfirmed");

            }
        });
        confirmedMeetingsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //print the list of completed meetings
                viewMeetings(sm, mmc.getCompletedMeetings(), "completed");

            }
        });
        meetingsNeedToConfirmTPButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // print the meetings with unconfirmed time and place
                viewMeetings(sm, mmc.getUnConfirmTimePlace(), "time and place unconfirmed");

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
                guiD.runRegularUserMainMenu(false);
            }
        });
    }

    private void editAndConfirmMeetingTP(RegularUserMeetingMenuController mmc, SystemMessage sm, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC) throws InvalidIdException {
        if (mmc.isEmpty(mmc.getUnConfirmTimePlace())) {
            printNote(sm.msgForNothing("here that requires action"));
        } else {
            // print the meetings with unconfirmed time and place
            String str = sm.printListObject(new ArrayList<>(mmc.getUnConfirmTimePlace()));
            printNote("Here's the list of meetings with time and place that need to be confirmed: + \n" + str);
            // asks for user input for the meeting to edit / confirm
            String askTradeId = "Please enter the trade id of the meeting you wish to edit / confirm its time and place.";
            String input1 = getInPut(askTradeId, guiUserInputInfo);
            String askMeetingNum = "Please enter the meeting number (enter 1 for first meeting and 2 for second meeting).";
            String input2 = getInPut(askMeetingNum, guiUserInputInfo);
            if (idC.checkInt(input1) && idC.checkInt(input2)) {
                int tradeId = Integer.parseInt(input1);
                int meetingNum = Integer.parseInt(input2);
                if (mmc.checkValidMeeting(tradeId, meetingNum)) {
                    String meetingInfo = mmc.getMeeting(tradeId, meetingNum).toString();
                    RegularUserCheckMeetingWindow suggestOrConfirmMeetingTPgui =
                            new RegularUserCheckMeetingWindow(meetingInfo);
                    suggestOrConfirmMeetingTPgui.run(meetingInfo);
                } else {
                    printNote(sm.tryAgainMsgForWrongInput());
                }
            } else {
                printNote(sm.tryAgainMsgForWrongFormatInput());
            }

        }
    }

    private void confirmMeetingTookPlace(RegularUserMeetingMenuController mmc, SystemMessage sm, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC, int maxNumTPEdits) {
        if (mmc.isEmpty(mmc.getUnconfirmedMeeting())) {
            sm.msgForNothing("that needs to be confirmed");
        }
        else {
        // print the meetings with unconfirmed meeting
        String str = sm.printListObject(new ArrayList<>(mmc.getUnconfirmedMeeting()));
        printNote("Here's the list of meetings that need to be confirmed that it took place: + \n" + str);
        //asks for user input
        String askTradeId = "Please enter the trade id of the meeting that you wish to confirm that it took place.";
        String input1 = getInPut(askTradeId, guiUserInputInfo);
        String askMeetingNum = "Please enter the meeting number (enter 1 for first meeting and 2 for second meeting).";
        String input2 = getInPut(askMeetingNum, guiUserInputInfo);
        if (idC.checkInt(input1) && idC.checkInt(input2)) {
            int tradeId = Integer.parseInt(input1);
            int meetingNum = Integer.parseInt(input2);
            if (mmc.checkValidMeeting(tradeId, meetingNum)) {
                String meetingInfo = mmc.getMeeting(tradeId, meetingNum).toString();
                RegularUserSuggestMeetingWindow confirmMeetingGui =
                        new RegularUserSuggestMeetingWindow(meetingInfo, mmc, sm, tradeId, meetingNum, maxNumTPEdits);
                confirmMeetingGui.run(meetingInfo);
            } else {
                printNote(sm.tryAgainMsgForWrongInput());
            }
        } else {
            printNote(sm.tryAgainMsgForWrongFormatInput());
        }
        }
    }

    private void viewMeetings(SystemMessage sm, List<Meeting> meetings, String type) {
        if (meetings.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(meetings));
            printNote("Here's your list of " + type + " meetings: \n" + str);
        } else {
            printNote(sm.msgForNothing("here."));
        }
    }

    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String userResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return userResponse;

    }

    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }


    public void run(GUIDemo guiD, RegularUserMeetingMenuController mmc, SystemMessage sm,
                    int maxNumTPEdits, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC) {
        JFrame frame = new JFrame("regularUserMeetingMenuGUI");
        frame.setContentPane(new RegularUserMeetingMenuGUI(guiD, mmc, sm,
        maxNumTPEdits, guiUserInputInfo, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }





}
