package gui.regularuser_community_menu_gui.communityWindows;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCommunityRatingWindow {
    private JLabel text;
    private JTextField textField1;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel rootPanel;

    public RegularUserCommunityRatingWindow(GUIDemo guidemo, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String str = textField1.getText();
                if (idC.checkInt(str) && cmc.checkUserId(Integer.parseInt(str))) {
                    int id = Integer.parseInt(str);
                    double rate = cmc.findRatingForUser(id);
                    if (rate == -1.0) {
                        guidemo.printNotification("This user does not have any reviews.");
                    } else {
                        String msg = "The rating of this user is " + Math.round(cmc.findRatingForUser(id));
                        guidemo.printNotification(msg);
                    }
                }
                else {
                    guidemo.printNotification("Please enter a valid user id.");
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
    public void run(GUIDemo guidemo, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC){
        JFrame frame = new JFrame("Find rating");
        frame.setContentPane(new RegularUserCommunityRatingWindow(guidemo, cmc, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
