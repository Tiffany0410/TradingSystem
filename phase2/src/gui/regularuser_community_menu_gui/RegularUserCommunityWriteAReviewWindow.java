package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import gui.GUIDemo;
import gui.trading_system_init_menu_gui.RegularUserCreateAccountGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunityWriteAReviewWindow {
    private JTextField userId;
    private JTextField point;
    private JTextField reason;
    private JButton createButton1;
    private JButton cancelButton;
    private JPanel rootPanel;

    RegularUserCommunityWriteAReviewWindow(GUIDemo guidemo, RegularUserIDChecker idC, RegularUserOtherInfoChecker otherChecker, RegularUserCommunityMenuController cmc, SystemMessage sm){
        createButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sUserId = userId.getText();
                String sPoint = point.getText();
                String sReason = reason.getText();
                if (idC.checkInt(sUserId) && idC.checkInt(sPoint)) {
                    int user_id = Integer.parseInt(sUserId);
                    int point = Integer.parseInt(sPoint);
                    if (idC.checkUserID(user_id) && otherChecker.getNumRating(point)) {
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
        frame.pack();
        frame.setVisible(true);
    }
}
