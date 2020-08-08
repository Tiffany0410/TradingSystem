package gui.regularuser_account_menus_gui.account_settings;

import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestUndoARevocableActionWindow {
    private JTextField textField1;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    public RequestUndoARevocableActionWindow(GUIDemo guiD, RegularUserIDChecker idc,
                                             AdminUserOtherInfoChecker auIDC, RegularUserAccountMenuController atc,
                                             SystemMessage sm) {
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField1.getText();
                if (idc.checkInt(input)) {
                    int actionId = Integer.parseInt(input);
                    if (auIDC.checkActionId(actionId)){
                        atc.requestUndoARevocableAction(actionId);
                        guiD.printNotification(sm.msgForRequestProcess(true));
                    }
                    else {
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                } else {
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

    public void run(GUIDemo guiD, RegularUserIDChecker idc,
                    AdminUserOtherInfoChecker auIDC, RegularUserAccountMenuController atc,
                    SystemMessage sm){
        JFrame frame = new JFrame("RequestUndoARevocableActionWindow");
        frame.setContentPane(new RequestUndoARevocableActionWindow(guiD, idc, auIDC, atc, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

}