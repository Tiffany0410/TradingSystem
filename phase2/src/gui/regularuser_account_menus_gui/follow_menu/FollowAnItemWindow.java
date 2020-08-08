package gui.regularuser_account_menus_gui.follow_menu;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FollowAnItemWindow {
    private JTextField textField1;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    public FollowAnItemWindow(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm,
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
                    int itemId = Integer.parseInt(input);
                    if (idChecker.checkItemID(itemId)){
                        guiD.printNotification(sm.msgForResult(amc.followAnItem(itemId)));
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
        JFrame frame = new JFrame("FollowAnItemWindow");
        frame.setContentPane(new FollowAnItemWindow(guiD, idChecker, sm, amc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
