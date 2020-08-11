package gui.regularuser_account_menus_gui.follow_menu;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * A class that is responsible for the view and getting input for user
 * when user wants to follow an item.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

public class FollowAnItemWindow {
    private JTextField textField1;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    /**
     * Constructs a FollowAnItemWindow.
     * @param guiD The GUI helper.
     * @param idChecker The id checker.
     * @param sm The presenter.
     * @param amc The RegularUserAccountMenuController
     */
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
                followAnItemOk(idChecker, guiD, sm, amc);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                guiD.closeWindow(rootPanel);
            }
        });
    }

    private void followAnItemOk(RegularUserIDChecker idChecker, GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController amc) {
        String input = textField1.getText();
        if (idChecker.checkInt(input)){
            int itemId = Integer.parseInt(input);
            if (idChecker.checkItemID(itemId)){
                guiD.printNotification(sm.msgForFollowResult(amc.followAnItem(itemId)));
            }
            else{
                guiD.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else {
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        guiD.runSave();
        guiD.closeWindow(rootPanel);
    }

    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param idChecker The id checker.
     * @param sm The presenter.
     * @param amc The RegularUserAccountMenuController
     */
    public void run(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm,
                    RegularUserAccountMenuController amc) {
        JFrame frame = new JFrame("FollowAnItemWindow");
        frame.setContentPane(new FollowAnItemWindow(guiD, idChecker, sm, amc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
