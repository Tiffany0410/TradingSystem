package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegularUserCheckMeetingWindow extends JDialog {
    private JPanel contentPane;
    private JButton back;
    private JButton confirm;
    private JButton edit;
    private JTextField meetingNum;
    private JTextField tradeId;
    private JTextArea textArea1;

    public RegularUserCheckMeetingWindow(GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int maxEditsTP, SystemMessage sm,
                                         RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        textArea1.setText(str);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setBackground(new Color(242,242,242));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(back);

        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //go back to meeting menu gui
                onBack(guiD);
            }

        });

        confirm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
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
                guiD.closeWindow(contentPane);

            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack(guiD);

            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack(guiD);
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        edit.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {String tradeIdInS = tradeId.getText();
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
                guiD.runRegularUserMainMenu(false);
                guiD.closeWindow(contentPane);

            }
        });
    }

    private void onBack(GUIDemo guiD) {
        // Go back to regular user main menu and close this window
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
            guiD.printNotification(sm.msgForNotYourTurn());
        }
    }


    public void run (GUIDemo guiD, String str, RegularUserMeetingMenuController mmc, int maxEditsTP, SystemMessage sm,
                     RegularUserDateTimeChecker dtc, RegularUserIDChecker idc) {
        RegularUserCheckMeetingWindow dialog = new RegularUserCheckMeetingWindow(guiD, str, mmc, maxEditsTP,
                sm, dtc, idc);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }
}
