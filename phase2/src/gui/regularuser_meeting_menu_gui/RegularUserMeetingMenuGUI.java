package gui.regularuser_meeting_menu_gui;

import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserMeetingMenuGUI {
    private JPanel rootPanel;

    public void run() {
        JFrame frame = new JFrame("regularUserMeetingMenuGUI");
        frame.setContentPane(new RegularUserMeetingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton confirmTPMeetingButton;
    private JButton outStandingMeetingButton;
    private JButton suggestTPMeetingButton;
    private JButton backButton;
    private JButton confirmTheMeetingTookButton;
    private JButton confirmedMeetingsButton;
    private JButton meetingsNeedToConfirmTPButton;

    public RegularUserMeetingMenuGUI() {

        suggestTPMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
            /*
            if (mmc.isEmpty(getUnConfirmTimePlace())){
                sm.msgForNothing("here that requires action", ds);
             }
           else{
             // print the meetings with unconfirmed time and place
             str = sm.printObjects(mmc.getUnconfirmedTimePlace());
             printNote("Here's the list of meetings with time and place
             that need to be confirmed" + str);
             // asks for user input...
             == split point ==
             // validate the meeting  + check threshold
             // if yes:
                - call the editMeetingTandP method
                - confirm time place
                - getEditOverThreshold
                - msg for success / fail
                    - if fail: "not your turn
            // if no:
                - msg for meeting DNE or threshold's reached
             }
		    */

            }
        });
        confirmTPMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                	/*
		if (mmc.isEmpty(getUnConfirmTimePlace())){
			sm.msgForNothing("that requires action", ds);
	     }
		else{
			// print the meetings with unconfirmed time and place
			 str = sm.printObjects(mmc.getUnconfirmedTimePlace());
			 printNote("Here's the list of meetings with time and place
			 that need to be confirmed" + str);


			 // asks for user input...
			 == split point ==
			 // validate the meeting (check valid meeting)
			 // if yes:
				- call the confirmMeetingTandP method
				  - if true = success
				  - if false = ds.printOut("It's not your turn to confirm." + "\n");
			// if no:
			    - msg for meeting DNE		     }

	    }
	}

		*/
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
    }


    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String UserResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return string;

    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }








}
