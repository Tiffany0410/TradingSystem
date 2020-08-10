package gui.regularuser_community_menu_gui.communityWindows;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunityRespondRequestWindow {
    private JTextPane textPane1;
    private JTextField id;
    private JButton cancelButton;
    private JButton acceptButton;
    private JPanel rootPanel;
    private JScrollPane scrollPane;

    public RegularUserCommunityRespondRequestWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc,
                                                    RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));
        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_id = id.getText();
                if (idC.checkInt(user_id)) {
                    int userToID = Integer.parseInt(user_id);
                    if (cmc.checkIdInRequest(userToID)){
                        guidemo.printNotification(sm.msgForResult(cmc.addFriend(userToID)));
                    }
                    else {
                        guidemo.printNotification("Please enter a valid user id in the list");
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
        JFrame frame = new JFrame("Respond to friend request");
        frame.setContentPane(new RegularUserCommunityRespondRequestWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
