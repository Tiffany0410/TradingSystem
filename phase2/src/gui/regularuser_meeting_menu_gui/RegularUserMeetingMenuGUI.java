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
