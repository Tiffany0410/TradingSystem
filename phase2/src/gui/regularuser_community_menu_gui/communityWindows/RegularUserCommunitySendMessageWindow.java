package gui.regularuser_community_menu_gui.communityWindows;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to show regular user send message window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunitySendMessageWindow {
    private JTextPane textPane1;
    private JTextField id;
    private JTextArea msg;
    private JButton cancelButton;
    private JButton sendButton;
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane1;

    /**
     * Constructor for Regular User Community Send Message Window
     * @param string A string representation of a list of users that can send message to
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public RegularUserCommunitySendMessageWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));
        textPane1.setVisible(true);
        msg.setVisible(true);
        scrollPane.setVisible(true);
        scrollPane1.setVisible(true);

        sendButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, send a message
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(idC, cmc, guidemo, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, close this window
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guidemo.closeWindow(rootPanel);
            }
        });
    }

    private void sendMessage(RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo, SystemMessage sm) {
        String userId = id.getText();
        String message = msg.getText();
        if (idC.checkInt(userId)) {
            int user_id = Integer.parseInt(userId);
            if (!cmc.checkIsFriend(user_id)) {
                guidemo.printNotification("Please enter an id of your friend!");
            }
            else {
                guidemo.printNotification(sm.msgForResult(cmc.sendMessage(user_id, message)));
            }
        }
        else {
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guidemo.closeWindow(rootPanel);
    }

    /**
     * Run Regular User Community Send Message Window
     * @param string A string representation of a list of users that can send message to
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public void run(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        JFrame frame = new JFrame("Send a message");
        frame.setContentPane(new RegularUserCommunitySendMessageWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
