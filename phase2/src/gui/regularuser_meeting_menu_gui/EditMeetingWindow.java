package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class EditMeetingWindow {
    private JTextField place;
    private JTextField minute;
    private JTextField hour;
    private JTextField day;
    private JTextField month;
    private JTextField year;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    public EditMeetingWindow(int tradeId, int meetingNum, RegularUserIDChecker idc, RegularUserDateTimeChecker dtc,
                             RegularUserMeetingMenuController mmc, int maxEditsTP, GUIDemo guiD, SystemMessage sm) {
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String yearS = year.getText();
                String monthS = month.getText();
                String dayS = day.getText();
                String hourS = hour.getText();
                String minuteS = minute.getText();
                String placeS = place.getText();
                if (idc.checkInt(yearS) && idc.checkInt(monthS) && idc.checkInt(dayS) &&
                        idc.checkInt(hourS) && idc.checkInt(minuteS)) {
                    int year = Integer.parseInt(yearS);
                    int month = Integer.parseInt(monthS);
                    int day = Integer.parseInt(dayS);
                    int hour = Integer.parseInt(hourS);
                    int min = Integer.parseInt(minuteS);
                    if (dtc.isValidDay(year, month, day) && dtc.isValidTime(hour, min)) {
                        ArrayList<Integer> time = new ArrayList<>();
                        Collections.addAll(time, year, month, day, hour, min);
                        if (mmc.editMeetingTandP(tradeId, meetingNum, time, placeS, maxEditsTP)) {
                            guiD.printNotification(sm.msgForResult(true));
                        } else {
                            guiD.printNotification(sm.msgForNotYourTurn());
                        }
                    } else {
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.runSave();
                guiD.closeWindow(rootPanel);
            }
        });
    }
    public void run(int tradeId, int meetingNum, RegularUserIDChecker idc, RegularUserDateTimeChecker dtc,
                    RegularUserMeetingMenuController mmc, int maxEditsTP, GUIDemo guiD, SystemMessage sm){
        JFrame frame = new JFrame("EditMeetingWindow");
        frame.setContentPane(new EditMeetingWindow(tradeId, meetingNum, idc, dtc, mmc, maxEditsTP, guiD, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}