package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
        });

        confirmTheMeetingTookButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                		/*
		if (mmc.isEmpty(getUnconfirmedMeeting){
			sm.msgForNothing("that needs to be confirmed", ds);
	    }
	   else{
		 //print the list of unconfirmed meeting
		 // asks for user input...
		 == split point ==
		 // validate the meeting (check valid meeting)
		 // if yes:
			- call the confirmMeetingTookPlace method
			  - if true = success
			  - if false = ds.printOut("fail");
		// if no:
		    - msg for meeting DNE		     }

		*/

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
                //new meeting controller method for get completed meetings
                //print the list of completed meetings
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
                /*
                // print the meetings with unconfirmed time and place
                str = sm.printObjects(mmc.getUnconfirmedTimePlace());
                printNote("Here's the list of meetings with time and place
                        that need to be confirmed" + str);
               */
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


    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String UserResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return string;

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
