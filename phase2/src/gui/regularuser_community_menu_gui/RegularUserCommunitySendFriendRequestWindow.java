package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunitySendFriendRequestWindow {
    private JTextPane textPane1;
    private JTextField userId;
    private JTextArea message;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel rootPanel;

    public RegularUserCommunitySendFriendRequestWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    guidemo.printNotification("Please enter a valid information.");
                }
                guidemo.closeWindow(rootPanel);

            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guidemo.closeWindow(rootPanel);
            }
        });
    }

    public void run(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        JFrame frame = new JFrame("Send a Friend Request");
        frame.setContentPane(new RegularUserCommunitySendFriendRequestWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 600));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
