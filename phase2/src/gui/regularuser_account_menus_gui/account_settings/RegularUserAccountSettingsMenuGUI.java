package gui.regularuser_account_menus_gui.account_settings;

import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import managers.actionmanager.Action;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserAccountSettingsMenuGUI {
    private JButton requestToUnfreezeAccountButton;
    private JButton setYourOnVacationButton;
    private JButton changeYourHomeCityButton;
    private JButton backButton;
    private JButton reviewOwnRevocableActionButton;
    private JButton requestUndoARevocableButton;
    private JPanel rootPanel;

    public RegularUserAccountSettingsMenuGUI(RegularUserAccountMenuController atc, SystemMessage sm,
                                             RegularUserIDChecker idc,
                                             GUIDemo guiD, AdminUserOtherInfoChecker auIDC) {
        requestToUnfreezeAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestToUnfreezeWindow win = new RequestToUnfreezeWindow(guiD, sm, atc);
                win.run(guiD, sm, atc);
                guiD.runSave();
            }
        });
        setYourOnVacationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                SetYourOnVacationStatusWindow win = new SetYourOnVacationStatusWindow(atc, sm, guiD);
                win.run(atc, sm, guiD);
                guiD.runSave();
            }
        });
        changeYourHomeCityButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeYourHCWindow changeYourHCWindow = new ChangeYourHCWindow(atc, guiD, sm);
                changeYourHCWindow.run(atc, guiD, sm);
                guiD.runSave();
            }
        });
        reviewOwnRevocableActionButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                reviewOwnRevocableActions(atc, sm, guiD);
                guiD.runSave();
            }
        });
        requestUndoARevocableButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestUndoARevocableActionWindow requestUndoARevocableActionWindow = new RequestUndoARevocableActionWindow(guiD, idc, auIDC, atc, sm);
                requestUndoARevocableActionWindow.run(guiD, idc, auIDC, atc, sm);
                guiD.runSave();
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //GO back to main menu
                guiD.runRegularUserAccountMainMenuGUI();
                guiD.closeWindow(rootPanel);
            }

        });

    }

    private void reviewOwnRevocableActions(RegularUserAccountMenuController atc, SystemMessage sm, GUIDemo guiD) {
        ArrayList<Action> ownRevocableAction = atc.seeOwnRevocableAction();
        if (ownRevocableAction.size() != 0){
            String str = sm.printHistoricalAction(ownRevocableAction);
            guiD.printNotification("Here's your list of revocable actions: \n " + str);
        }
        else{
            guiD.printNotification(sm.msgForNothing("here."));
        }
    }

    public void run(RegularUserAccountMenuController atc, SystemMessage sm,
                    RegularUserIDChecker idc,
                    GUIDemo guiD, AdminUserOtherInfoChecker auIDC) {
        JFrame frame = new JFrame("regularUserAccountSettingsMenuGUI");
        frame.setContentPane(new RegularUserAccountSettingsMenuGUI(atc, sm, idc, guiD, auIDC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
