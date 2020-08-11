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

/**
 * A class that is responsible for the view and getting input for user
 * when user browses the account settings menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserAccountSettingsMenuGUI {
    private JButton requestToUnfreezeAccountButton;
    private JButton setYourOnVacationButton;
    private JButton changeYourHomeCityButton;
    private JButton backButton;
    private JButton reviewOwnRevocableActionButton;
    private JButton requestUndoARevocableButton;
    private JPanel rootPanel;

    /**
     * Constructs the RegularUserAccountSettingsMenuGUI.
     * @param atc The RegularUserAccountMenuController.
     * @param sm The presenter.
     * @param idc The id checker.
     * @param guiD The GUI helper.
     * @param auIDC The other information checker.
     */
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
                requestUnfreeze(guiD, sm, atc);
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
                setUserOnVacationStatus(atc, sm, guiD);
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
                changeHomeCity(atc, guiD, sm);
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
                reviewOwnRevoActions(atc, sm, guiD);
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
                requestUndoRevoActions(guiD, idc, auIDC, atc, sm);
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

    private void requestUndoRevoActions(GUIDemo guiD, RegularUserIDChecker idc, AdminUserOtherInfoChecker auIDC, RegularUserAccountMenuController atc, SystemMessage sm) {
        RequestUndoARevocableActionWindow requestUndoARevocableActionWindow = new RequestUndoARevocableActionWindow(guiD, idc, auIDC, atc, sm);
        requestUndoARevocableActionWindow.run(guiD, idc, auIDC, atc, sm);
        guiD.runSave();
    }

    private void reviewOwnRevoActions(RegularUserAccountMenuController atc, SystemMessage sm, GUIDemo guiD) {
        reviewOwnRevocableActions(atc, sm, guiD);
        guiD.runSave();
    }

    private void changeHomeCity(RegularUserAccountMenuController atc, GUIDemo guiD, SystemMessage sm) {
        ChangeYourHCWindow changeYourHCWindow = new ChangeYourHCWindow(atc, guiD, sm);
        changeYourHCWindow.run(atc, guiD, sm);
        guiD.runSave();
    }

    private void setUserOnVacationStatus(RegularUserAccountMenuController atc, SystemMessage sm, GUIDemo guiD) {
        SetYourOnVacationStatusWindow win = new SetYourOnVacationStatusWindow(atc, sm, guiD);
        win.run(atc, sm, guiD);
        guiD.runSave();
    }

    private void requestUnfreeze(GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController atc) {
        RequestToUnfreezeWindow win = new RequestToUnfreezeWindow(guiD, sm, atc);
        win.run(guiD, sm, atc);
        guiD.runSave();
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

    /**
     * Responsible for getting this window running.
     * @param atc The RegularUserAccountMenuController.
     * @param sm The presenter.
     * @param idc The id checker.
     * @param guiD The GUI helper.
     * @param auIDC The other information checker.
     */
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
