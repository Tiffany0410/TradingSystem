package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegularUserSuggestMeetingWindow {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JTextField meetingNum;
    private JTextField tradeId;

    public RegularUserSuggestMeetingWindow(String str, RegularUserMeetingMenuController mmc, SystemMessage sm,
                                           int maxEditsTP, GUIDemo guiD, RegularUserIDChecker idc) {
        textArea1.setText(str);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setBackground(new Color(242,242,242));


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(mmc, sm, maxEditsTP, guiD, idc);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(guiD);
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel(guiD);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK(RegularUserMeetingMenuController mmc, SystemMessage sm, int maxEditsTP, GUIDemo guiD,
                      RegularUserIDChecker idc) {
        String tradeIdInS = tradeId.getText();
        String meetingNumInS = meetingNum.getText();
        if (idc.checkInt(tradeIdInS) && idc.checkInt(meetingNumInS)) {
            int tradeId = Integer.parseInt(tradeIdInS);
            int meetingNum = Integer.parseInt(meetingNumInS);
            if (mmc.checkValidMeeting(tradeId, meetingNum)) {
                guiD.printNotification(sm.printResult(mmc.confirmMeetingTookPlace(tradeId, meetingNum, maxEditsTP)));
            } else {
                guiD.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else{
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        guiD.runSave();
        guiD.runRegularUserMeetingMenu();
        guiD.closeWindow(contentPane);
    }

    private void onCancel(GUIDemo guiD) {
        // add your code here if necessary
        guiD.runRegularUserMeetingMenu();
        guiD.closeWindow(contentPane);
    }

    public void run(String str, RegularUserMeetingMenuController mmc, SystemMessage sm,
                    int maxEditsTP, GUIDemo guiD, RegularUserIDChecker idc) {
        JFrame frame = new JFrame("regularUserConfirmMeetingTookPlaceWindowGUI");
        frame.setContentPane(new RegularUserSuggestMeetingWindow(str, mmc, sm, maxEditsTP, guiD, idc).contentPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }
}
