package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunityWriteAReviewWindow {
    private JTextField userId;
    private JButton createButton;
    private JPanel rootPanel;
    private JComboBox point;
    private JTextArea reason;
    private JButton cancelButton;

    public RegularUserCommunityWriteAReviewWindow(GUIDemo guidemo, RegularUserIDChecker idC, RegularUserOtherInfoChecker otherChecker, RegularUserCommunityMenuController cmc, SystemMessage sm){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sUserId = userId.getText();
                String sPoint = (String) point.getSelectedItem();
                String sReason = reason.getText();
                if (idC.checkInt(sUserId)) {
                    int user_id = Integer.parseInt(sUserId);
                    if (idC.checkInt(sPoint)) {
                        int point = Integer.parseInt(sPoint);
                        if (idC.checkUserID(user_id)) {
                            boolean yesOrNo = cmc.reviewUser(user_id, point, sReason);
                            if (cmc.getUserId() == user_id) {
                                guidemo.printNotification("Fail. Please don't review yourself.");
                            } else if (yesOrNo) {
                                guidemo.printNotification(sm.msgForResult(true));
                            } else {
                                guidemo.printNotification("Fail. You have reviewed this user.");
                            }
                            guidemo.closeWindow(rootPanel);
                        } else {
                            guidemo.printNotification("The user does not exist.");
                        }
                    }
                    else{
                        guidemo.printNotification("Please select a point.");
                    }
                }
                else {
                    guidemo.printNotification("Please enter a valid id and point.");
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guidemo.closeWindow(rootPanel);
            }
        });

    }
    public void run(GUIDemo guidemo, RegularUserIDChecker idC, RegularUserOtherInfoChecker otherChecker, RegularUserCommunityMenuController cmc, SystemMessage sm){
        JFrame frame = new JFrame("Write a review");
        frame.setContentPane(new RegularUserCommunityWriteAReviewWindow(guidemo, idC, otherChecker, cmc, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
