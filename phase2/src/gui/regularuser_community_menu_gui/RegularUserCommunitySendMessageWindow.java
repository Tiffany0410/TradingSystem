package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunitySendMessageWindow {
    private JTextPane textPane1;
    private JTextField id;
    private JTextArea msg;
    private JButton cancelButton;
    private JButton sendButton;
    private JPanel rootPanel;

    public RegularUserCommunitySendMessageWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        JFrame frame = new JFrame("Send a message");
        frame.setContentPane(new RegularUserCommunitySendMessageWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
