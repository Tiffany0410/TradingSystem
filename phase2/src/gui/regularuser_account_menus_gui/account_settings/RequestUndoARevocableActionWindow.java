package gui.regularuser_account_menus_gui.account_settings;

import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that is responsible for the view and getting input for user
 * when user wants to undo any revocable actions.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RequestUndoARevocableActionWindow {
    private JTextField textField1;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    /**
     * Constructs a RequestUndoARevocableActionWindow.
     * @param guiD The GUI helper.
     * @param idc The id checker.
     * @param auIDC The other info checker.
     * @param sm The presenter.
     * @param atc The RegularUserAccountMenuController.
     */
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
                undoRevoActionOk(idc, auIDC, atc, guiD, sm);
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

    private void undoRevoActionOk(RegularUserIDChecker idc, AdminUserOtherInfoChecker auIDC, RegularUserAccountMenuController atc, GUIDemo guiD, SystemMessage sm) {
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
        guiD.runSave();
        guiD.closeWindow(rootPanel);
    }

    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param idc The id checker.
     * @param auIDC The other info checker.
     * @param sm The presenter.
     * @param atc The RegularUserAccountMenuController.
     */
    public void run(GUIDemo guiD, RegularUserIDChecker idc,
                    AdminUserOtherInfoChecker auIDC, RegularUserAccountMenuController atc,
                    SystemMessage sm){
        JFrame frame = new JFrame("RequestUndoARevocableActionWindow");
        frame.setContentPane(new RequestUndoARevocableActionWindow(guiD, idc, auIDC, atc, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

}
