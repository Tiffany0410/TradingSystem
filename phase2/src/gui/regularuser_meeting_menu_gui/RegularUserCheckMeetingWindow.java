package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import exception.InvalidIdException;
import gui.GUIDemo;
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

    public RegularUserCheckMeetingWindow(GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int tradeId,
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
                confirmTimeAndPlace(mmc, tradeId, meetingNum, maxEditsTP, sm, guiD);
                //GO back to main menu
                guiD.runRegularUserMainMenu(false);
                guiD.closeWindow(contentPane);
            }
        });

        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //go back to meeting menu gui
                onBack(guiD, mmc, maxEditsTP, sm, guiUserInputInfo, idc);
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack(guiD, mmc, maxEditsTP, sm, guiUserInputInfo, idc);
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack(guiD, mmc, maxEditsTP, sm, guiUserInputInfo, idc);
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
                editTimeAndPlace(mmc, tradeId, meetingNum, maxEditsTP, guiUserInputInfo, idc, dtc, sm, guiD);
                //GO back to main menu
                guiD.runRegularUserMainMenu(false);
                guiD.closeWindow(contentPane);

            }
        });
    }

    private void onBack(GUIDemo guiD, RegularUserMeetingMenuController mmc,
                        int maxEditsTP, SystemMessage sm, GUIUserInputInfo guiUserInputInfo,
                        RegularUserIDChecker idc){
        // Go back to regular user main menu and close this window
        guiD.runRegularUserMeetingMenu(guiD, mmc, sm, maxEditsTP, guiUserInputInfo, idc);
        guiD.closeWindow(contentPane);
    }
    private void editTimeAndPlace(RegularUserMeetingMenuController mmc, int tradeId, int meetingNum,
                                  int maxEditsTP, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idc,
                                  RegularUserDateTimeChecker dtc, SystemMessage sm, GUIDemo guiD)  {
        if (mmc.checkOverEdit(tradeId, meetingNum, maxEditsTP).equals("")){
            //int year, int month, int day, int hour, int min, int sec, String place,
            //asks for input
            String askYear = "Please enter the year (for the new time).";
            String input1 = guiD.getInPut(askYear, guiUserInputInfo);
            String askMonth = "Please enter the month (for the new time).";
            String input2 = guiD.getInPut(askMonth, guiUserInputInfo);
            String askDay= "Please enter the day (for the new time).";
            String input3 = guiD.getInPut(askDay, guiUserInputInfo);
            String askHour = "Please enter the hour (for the new time).";
            String input4 = guiD.getInPut(askHour, guiUserInputInfo);
            String askMin = "Please enter the minute (for the new time).";
            String input5 = guiD.getInPut(askMin, guiUserInputInfo);
            String askPlace = "Please enter the new place.";
            String place = guiD.getInPut(askPlace, guiUserInputInfo);
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
                        guiD.printNotification(sm.msgForResult(true));
                    }
                    else{
                        guiD.printNotification(sm.msgForNotYourTurn());
                    }
                }
            }
            else{
                guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
            }
        }
        else{
            guiD.printNotification(sm.lockMessageForTPLimit());
        }
    }

    private void confirmTimeAndPlace(RegularUserMeetingMenuController mmc,  int tradeId,
                      int meetingNum, int maxEditsTP, SystemMessage sm, GUIDemo guiD) {
        boolean confirmed = mmc.confirmMeetingTandP(tradeId, meetingNum, maxEditsTP);
        if (confirmed){
            guiD.printNotification(sm.msgForResult(true));
        }
        else{
            guiD.printNotification(sm.msgForNotYourTurn());
        }
    }


    public void run (GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int tradeId,
                     int meetingNum, int maxEditsTP, SystemMessage sm, GUIUserInputInfo guiUserInputInfo,
                     RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        RegularUserCheckMeetingWindow dialog = new RegularUserCheckMeetingWindow(guiD, str, mmc, tradeId,
                meetingNum, maxEditsTP, sm, guiUserInputInfo, dtc, idc);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
