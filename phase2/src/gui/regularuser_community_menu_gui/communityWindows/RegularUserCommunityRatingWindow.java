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
 * Used to show regular user rating window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunityRatingWindow {
    private JTextField textField1;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel rootPanel;

    /**
     * Constructor for RegularUserCommunityRatingWindow
     * @param guidemo GUIDemo
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public RegularUserCommunityRatingWindow(GUIDemo guidemo, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC, SystemMessage sm){
        okButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, find rating for this user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                findRating(idC, cmc, guidemo, sm);
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

    private void findRating(RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo, SystemMessage sm) {
        String str = textField1.getText();
        if (idC.checkInt(str) && cmc.checkUserId(Integer.parseInt(str))) {
            int id = Integer.parseInt(str);
            double rate = cmc.findRatingForUser(id);
            if (rate == -1.0) {
                guidemo.printNotification("This user does not have any reviews.");
            } else {
                String msg = "The rating of this user is " + Math.round(cmc.findRatingForUser(id)) + ".\n\n";
                String reviews = sm.msgForReview(cmc.getAllReviews(id));
                guidemo.printNotification(msg+reviews);
            }
        }
        else {
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guidemo.closeWindow(rootPanel);
    }

    /**
     * run Regular user community rating window
     * @param guidemo GUIDemo
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public void run(GUIDemo guidemo, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC, SystemMessage sm){
        JFrame frame = new JFrame("Find rating");
        frame.setContentPane(new RegularUserCommunityRatingWindow(guidemo, cmc, idC, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
