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
 * Used to show regular user unfriend window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunityUnfriendWindow {
    private JTextPane textPane1;
    private JButton cancelButton;
    private JButton unfriendButton;
    private JTextField id;
    private JPanel rootPanel;
    private JScrollPane scrollPane;

    /**
     * Constructor for Regular User Community Unfriend Window
     * @param string A string representation of a list of users that can be unfriended
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
    public RegularUserCommunityUnfriendWindow(String string, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc,
                                              RegularUserIDChecker idC){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));
        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        unfriendButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, unfriend an user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                unfriend(idC, guidemo, sm, cmc);
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

    private void unfriend(RegularUserIDChecker idC, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc) {
        String user_id = id.getText();
        if (idC.checkInt(user_id)){
            int id = Integer.parseInt(user_id);
            guidemo.printNotification(sm.msgForResult(cmc.unfriendUser(id)));
        }
        else {
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guidemo.closeWindow(rootPanel);
    }

    /**
     * Run Regular User Community Unfriend Window
     * @param string A string representation of a list of users that can be unfriended
     * @param guidemo GUIDemo
     * @param sm SystemMessage
     * @param cmc RegularUserCommunityMenuController
     * @param idC RegularUserIDChecker
     */
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
