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
 * Used to show regular user write a review window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunityWriteAReviewWindow {
    private JTextField userId;
    private JButton createButton;
    private JPanel rootPanel;
    private JComboBox point;
    private JTextPane reason;
    private JButton cancelButton;
    private JScrollPane scrollPane;

    /**
     * Constructor for Regular User Community Write a Review Window
     * @param guidemo GUIDemo
     * @param idC RegularUserIDChecker
     * @param cmc RegularUserCommunityMenuController
     * @param sm SystemMessage
     */
    public RegularUserCommunityWriteAReviewWindow(GUIDemo guidemo, RegularUserIDChecker idC,
                                                  RegularUserCommunityMenuController cmc, SystemMessage sm){
        reason.setVisible(true);
        scrollPane.setVisible(true);

        createButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, write a review
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                writeAReview(idC, cmc, guidemo, sm);
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

    private void writeAReview(RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo, SystemMessage sm) {
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
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
    }

    /**
     * Run Regular User Community Write a Review Window
     * @param guidemo GUIDemo
     * @param idC RegularUserIDChecker
     * @param cmc RegularUserCommunityMenuController
     * @param sm SystemMessage
     */
    public void run(GUIDemo guidemo, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, SystemMessage sm){
        JFrame frame = new JFrame("Write a review");
        frame.setContentPane(new RegularUserCommunityWriteAReviewWindow(guidemo, idC, cmc, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
