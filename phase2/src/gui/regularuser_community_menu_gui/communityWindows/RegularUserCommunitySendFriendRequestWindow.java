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
 * Used to show regular user send friend request window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunitySendFriendRequestWindow {
    private JTextPane textPane1;
    private JTextField userId;
    private JTextArea message;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel rootPanel;
    private JScrollPane scrollPane;
    private JScrollPane scrollPane1;

    /**
     * Constructor for Regular User Community Send Friend Request Window
     * @param string A string representation of a list of users that can send request to
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public RegularUserCommunitySendFriendRequestWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));
        textPane1.setVisible(true);
        message.setVisible(true);
        scrollPane.setVisible(true);
        scrollPane1.setVisible(true);

        requestButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, send a friend request
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                sendFriendRequest(idC, cmc, guidemo, sm);
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

    private void sendFriendRequest(RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo, SystemMessage sm) {
        String id_input = userId.getText();
        String msg = message.getText();
        if (idC.checkInt(id_input)) {
            int userToID = Integer.parseInt(id_input);
            if (userToID != cmc.getUserId()) {
                guidemo.printNotification(sm.msgForFriendRequest(cmc.sendFriendRequest(userToID, msg), userToID));

            } else {
                guidemo.printNotification("You can't send friend request to yourself :)");
            }
        }
        else {
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guidemo.closeWindow(rootPanel);
    }

    /**
     * Run Regular User Community Send Friend Request Window
     * @param string A string representation of a list of users that can send request to
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public void run(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        JFrame frame = new JFrame("Send a Friend Request");
        frame.setContentPane(new RegularUserCommunitySendFriendRequestWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
