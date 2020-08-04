package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import exception.InvalidIdException;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class RegularUserCheckMeetingWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonConfirm;
    private JButton buttonBack;
    private JButton editButton;
    private JTextArea textArea;

    public RegularUserCheckMeetingWindow(String str, RegularUserMeetingMenuController mmc, int tradeId,
                                         int meetingNum, int maxEditsTP, SystemMessage sm, GUIUserInputInfo guiUserInputInfo,
                                         RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        textArea.setText(str);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonConfirm);

        buttonConfirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                confirmTimeAndPlace(mmc, tradeId, meetingNum, maxEditsTP, sm);
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        editButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                editTimeAndPlace(mmc, tradeId, meetingNum, maxEditsTP, guiUserInputInfo, idc, dtc, sm);

            }
        });
    }

    private void editTimeAndPlace(RegularUserMeetingMenuController mmc, int tradeId, int meetingNum, int maxEditsTP, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idc, RegularUserDateTimeChecker dtc, SystemMessage sm) throws InvalidIdException {
        if (mmc.checkOverEdit(tradeId, meetingNum, maxEditsTP).equals("")){
            //int year, int month, int day, int hour, int min, int sec, String place,
            //asks for input
            String askYear = "Please enter the year (for the new time).";
            String input1 = getInPut(askYear, guiUserInputInfo);
            String askMonth = "Please enter the month (for the new time).";
            String input2 = getInPut(askMonth, guiUserInputInfo);
            String askDay= "Please enter the day (for the new time).";
            String input3 = getInPut(askDay, guiUserInputInfo);
            String askHour = "Please enter the hour (for the new time).";
            String input4 = getInPut(askHour, guiUserInputInfo);
            String askMin = "Please enter the minute (for the new time).";
            String input5 = getInPut(askMin, guiUserInputInfo);
            String askPlace = "Please enter the new place.";
            String place = getInPut(askPlace, guiUserInputInfo);
            if (idc.checkInt(input1) && idc.checkInt(input2) && idc.checkInt(input3) &&
            idc.checkInt(input4) && idc.checkInt(input5)){
                int year = Integer.parseInt(input1);
                int month = Integer.parseInt(input2);
                int day = Integer.parseInt(input3);
                int hour = Integer.parseInt(input4);
                int min = Integer.parseInt(input5);
                if (dtc.isValidDay(year, month, day) && dtc.isValidTime(hour, min)){
                    ArrayList<Integer> time = new ArrayList<>();
                    Collections.addAll(time, year, month, day, hour, min);
                    if (mmc.editMeetingTandP(tradeId, meetingNum, time, place, maxEditsTP)){
                        printNote(sm.msgForResult(true));
                    }
                    else{
                        printNote(sm.msgForNotYourTurn());
                    }
                }
            }
            else{
                printNote(sm.tryAgainMsgForWrongFormatInput());
            }
        }
        else{
            printNote(sm.lockMessageForTPLimit());
        }
    }

    private void confirmTimeAndPlace(RegularUserMeetingMenuController mmc,  int tradeId,
                      int meetingNum, int maxEditsTP, SystemMessage sm) {
        boolean confirmed = mmc.confirmMeetingTandP(tradeId, meetingNum, maxEditsTP);
        if (confirmed){
            printNote(sm.msgForResult(true));
        }
        else{
            printNote(sm.msgForNotYourTurn());
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
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

    public void run (String str) {
        RegularUserCheckMeetingWindow dialog = new RegularUserCheckMeetingWindow(str);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
