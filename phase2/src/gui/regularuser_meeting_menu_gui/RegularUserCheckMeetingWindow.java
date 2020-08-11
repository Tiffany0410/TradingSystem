package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * A class that is responsible for the view and getting input for user
 * when user tries to edit or confirm a meeting's time and place.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserCheckMeetingWindow {
    private JPanel contentPane;
    private JButton back;
    private JButton confirm;
    private JButton edit;
    private JTextField meetingNum;
    private JTextField tradeId;
    private JTextArea textArea1;


    /**
     * Constructs a RegularUserCheckMeetingWindow.
     * @param guiD The GUI helper.
     * @param str The string representation of the meetings that need to be confirmed for time and place.
     * @param mmc The RegularUserMeetingMenuController.
     * @param maxEditsTP The maximum number of datetime edits allowed.
     * @param sm The presenter.
     * @param dtc The datetime checker.
     * @param idc The id checker.
     */
    public RegularUserCheckMeetingWindow(GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int maxEditsTP, SystemMessage sm,
                                         RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        textArea1.setText(str);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setBackground(new Color(242,242,242));

        back.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //go back to meeting menu gui
                guiD.runRegularUserMeetingMenu();
                guiD.closeWindow(contentPane);
            }

        });

        confirm.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmTP(idc, mmc, maxEditsTP, sm, guiD);

            }
        });
        edit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {String tradeIdInS = tradeId.getText();
                editTP(tradeIdInS, idc, mmc, dtc, maxEditsTP, sm, guiD);

            }
        });
    }

    private void editTP(String tradeIdInS, RegularUserIDChecker idc, RegularUserMeetingMenuController mmc, RegularUserDateTimeChecker dtc, int maxEditsTP, SystemMessage sm, GUIDemo guiD) {
        String meetingNumInS = meetingNum.getText();
        if (idc.checkInt(tradeIdInS) && idc.checkInt(meetingNumInS)) {
            int tradeId = Integer.parseInt(tradeIdInS);
            int meetingNum = Integer.parseInt(meetingNumInS);
            if (mmc.checkValidMeeting(tradeId, meetingNum)) {
                editTimeAndPlace(mmc, tradeId, meetingNum, idc, dtc, maxEditsTP, sm, guiD);
            } else {
                guiD.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else{
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        //GO back to main menu
        guiD.runSave();
        guiD.closeWindow(contentPane);
    }

    private void confirmTP(RegularUserIDChecker idc, RegularUserMeetingMenuController mmc, int maxEditsTP, SystemMessage sm, GUIDemo guiD) {
        String tradeIdInS = tradeId.getText();
        String meetingNumInS = meetingNum.getText();
        if (idc.checkInt(tradeIdInS) && idc.checkInt(meetingNumInS)) {
            int tradeId = Integer.parseInt(tradeIdInS);
            int meetingNum = Integer.parseInt(meetingNumInS);
            if (mmc.checkValidMeeting(tradeId, meetingNum)) {
                confirmTimeAndPlace(mmc, tradeId, meetingNum, maxEditsTP, sm, guiD);
            } else {
                guiD.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else{
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        //GO back to main menu
        guiD.runSave();
        guiD.runRegularUserMeetingMenu();
        guiD.closeWindow(contentPane);
    }

    private void editTimeAndPlace(RegularUserMeetingMenuController mmc, int tradeId, int meetingNum,
                                  RegularUserIDChecker idc, RegularUserDateTimeChecker dtc,
                                  int maxEditsTP, SystemMessage sm, GUIDemo guiD)  {
        if (mmc.checkOverEdit(tradeId, meetingNum, maxEditsTP).equals("")){
            EditMeetingWindow editMeetingWindow = new EditMeetingWindow(tradeId, meetingNum, idc, dtc, mmc, maxEditsTP,
                    guiD, sm);
            editMeetingWindow.run(tradeId, meetingNum, idc, dtc, mmc, maxEditsTP,
                    guiD, sm);
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
            guiD.printNotification(sm.msgForTPcannotConfirm());
        }
    }


    /**
     * Responsible for getting this window running.
     * @param guiD The GUI helper.
     * @param str The string representation of the meetings that need to be confirmed for time and place.
     * @param mmc The RegularUserMeetingMenuController.
     * @param maxEditsTP The maximum number of datetime edits allowed.
     * @param sm The presenter.
     * @param dtc The datetime checker.
     * @param idc The id checker.
     */
    public void run (GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int maxEditsTP, SystemMessage sm,
                     RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        JFrame frame = new JFrame("regularUserEditOrConfirmMeetingTPMenuGUI");
        frame.setContentPane(new RegularUserCheckMeetingWindow(guiD,str, mmc, maxEditsTP, sm, dtc, idc).contentPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
