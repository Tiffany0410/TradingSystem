package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunityUnfriendWindow {
    private JTextPane textPane1;
    private JButton cancelButton;
    private JButton unfriendButton;
    private JTextField id;
    private JPanel rootPanel;

    public RegularUserCommunityUnfriendWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc,
                                              RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        unfriendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user_id = id.getText();
                if (idC.checkInt(user_id)){
                    int id = Integer.parseInt(user_id);
                    guidemo.printNotification(sm.msgForResult(cmc.unfriendUser(id)));
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

    public void run(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc,
                    RegularUserIDChecker idC){
        JFrame frame = new JFrame("Unfriend an user");
        frame.setContentPane(new RegularUserCommunityUnfriendWindow(string, guidemo, sm, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
