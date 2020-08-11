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
 * Used to show regular user report window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCommunityReportAUser {
    private JPanel rootPanel;
    private JTextField idField;
    private JTextPane reasonArea;
    private JButton cancelButton;
    private JButton createButton;
    private JScrollPane scrollPane;

    /**
     * Constructor for RegularUserCommunityRatingWindow
     * @param sm SystemMessage
     * @param idC RegularUserIDChecker
     * @param cmc RegularUserCommunityMenuController
     * @param guidemo GUIDemo
     */
    public RegularUserCommunityReportAUser(SystemMessage sm, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo){
        reasonArea.setVisible(true);
        scrollPane.setVisible(true);
        
        createButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, create a report
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                reportUser(idC, cmc, guidemo, sm);
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

    private void reportUser(RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo, SystemMessage sm) {
        String userId = idField.getText();
        String reason = reasonArea.getText();
        if (idC.checkInt(userId)) {
            boolean sOrF = cmc.reportUser(Integer.parseInt(userId),reason);
            if(cmc.getUserId()==Integer.parseInt(userId)){
                guidemo.printNotification("Fail. Please do not report yourself.");}
            else if(sOrF){
                guidemo.printNotification(sm.msgForResult(true));}
            else if(idC.checkUserID(Integer.parseInt(userId)))
            {guidemo.printNotification("Fail. You have reported this user.");}
            else{guidemo.printNotification("Fail. This user does not exist.");}
            guidemo.closeWindow(rootPanel);
        }
        else {
            guidemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
    }

    /**
     * run Regular user community report window
     * @param sm SystemMessage
     * @param idC RegularUserIDChecker
     * @param cmc RegularUserCommunityMenuController
     * @param guidemo GUIDemo
     */
    public void run(SystemMessage sm, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo){
        JFrame frame = new JFrame("Report an user");
        frame.setContentPane(new RegularUserCommunityReportAUser(sm, idC, cmc, guidemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
