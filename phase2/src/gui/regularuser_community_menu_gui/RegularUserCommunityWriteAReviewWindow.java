package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import gui.GUIDemo;
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
                    int point = Integer.parseInt(sPoint);
                    if (idC.checkUserID(user_id)) {
                        boolean yesOrNo = cmc.reviewUser(user_id, point, sReason);
                        guidemo.printNotification(sm.msgForResult(yesOrNo));
                        guidemo.closeWindow(rootPanel);
                    }
                    else{
                        guidemo.printNotification("Please enter a valid information.");
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
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
    }
}
