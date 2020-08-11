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
/**
 * A class that is responsible for the view and getting input for user
 * when user tries to edit a meeting's time and place.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
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

    /**
     * Constructs an EditMeetingWindow.
     * @param tradeId The id of the trade.
     * @param meetingNum The meeting number.
     * @param idc The id checker.
     * @param dtc The datetime checker.
     * @param mmc The RegularUserMeetingMenuController.
     * @param maxEditsTP The number of maximum datetime edits.
     * @param guiD The GUI helper.
     * @param sm The presenter.
     */
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
                editMeetingOk(idc, dtc, mmc, tradeId, meetingNum, maxEditsTP, guiD, sm);
            }
        });
    }

    private void editMeetingOk(RegularUserIDChecker idc, RegularUserDateTimeChecker dtc, RegularUserMeetingMenuController mmc, int tradeId, int meetingNum, int maxEditsTP, GUIDemo guiD, SystemMessage sm) {
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
            if (dtc.isValidDayForMeetingTime(year, month, day) && dtc.isValidTime(hour, min)) {
                ArrayList<Integer> time = new ArrayList<>();
                Collections.addAll(time, year, month, day, hour, min);
                if (mmc.editMeetingTandP(tradeId, meetingNum, time, placeS, maxEditsTP)) {
                    guiD.printNotification(sm.msgForResult(true));
                } else {
                    guiD.printNotification(sm.msgForNotYourTurn());
                }
            } else {
                guiD.printNotification(sm.tryAgainMsgForWrongDatetime());
            }
        } else {
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        guiD.runSave();
        guiD.closeWindow(rootPanel);
    }

    /**
     * Responsible for running this window.
     * @param tradeId The id of the trade.
     * @param meetingNum The meeting number.
     * @param idc The id checker.
     * @param dtc The datetime checker.
     * @param mmc The RegularUserMeetingMenuController.
     * @param maxEditsTP The number of maximum datetime edits.
     * @param guiD The GUI helper.
     * @param sm The presenter.
     */
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