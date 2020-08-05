package gui.regularuser_meeting_menu_gui;

import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class RegularUserSuggestMeetingWindow extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea;

    public RegularUserSuggestMeetingWindow(String str, RegularUserMeetingMenuController mmc, SystemMessage sm, int tradeId,
                                           int meetingNum, int maxEditsTP) {
        textArea.setText(str);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK(mmc, sm, tradeId, meetingNum, maxEditsTP);
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
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
    }

    private void onOK(RegularUserMeetingMenuController mmc, SystemMessage sm, int tradeId,
                      int meetingNum, int maxEditsTP) {
        boolean ok = mmc.confirmMeetingTookPlace(tradeId, meetingNum, maxEditsTP);
        sm.msgForResult(ok);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void run(String str) {
        RegularUserSuggestMeetingWindow dialog = new RegularUserSuggestMeetingWindow(str);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
