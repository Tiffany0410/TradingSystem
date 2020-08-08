package gui.regularuser_account_menus_gui.follow_menu;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FollowAnUserWindow {
    private JTextField textField1;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    public FollowAnUserWindow(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm,
                              RegularUserAccountMenuController amc) {
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = guiD.getInPut(textField1.getText());
                if (idChecker.checkInt(input)){
                    int userId = Integer.parseInt(input);
                    if (idChecker.checkUserID(userId)){
                        guiD.printNotification(sm.msgForResult(amc.followAnUser(userId)));
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.closeWindow(rootPanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
    }
    public void run(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm,
                    RegularUserAccountMenuController amc) {
        JFrame frame = new JFrame("FollowAnUserWindow");
        frame.setContentPane(new FollowAnUserWindow(guiD, idChecker, sm, amc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
