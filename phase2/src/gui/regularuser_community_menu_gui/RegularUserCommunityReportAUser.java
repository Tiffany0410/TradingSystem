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

public class RegularUserCommunityReportAUser {
    private JPanel rootPanel;
    private JTextField idField;
    private JTextArea reasonArea;
    private JButton cancelButton;
    private JButton createButton;

    public RegularUserCommunityReportAUser(SystemMessage sm, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guidemo){
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = idField.getText();
                String reason = reasonArea.getText();
                if (idC.checkInt(userId)) {
                    guidemo.printNotification(sm.msgForResult(cmc.reportUser(Integer.parseInt(userId), reason)));
                    guidemo.closeWindow(rootPanel);
                }
                else {
                    guidemo.printNotification("Please enter valid information.");
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
