package gui.regularuser_account_menus_gui;

import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.*;
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
                                             GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idc,
                                             GUIDemo guiD, AdminUserOtherInfoChecker auIDC) {
        requestToUnfreezeAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String msg = guiD.getLongInput("Please enter a message indicating reasons why you should " +
                        " be unfrozen", guiUserInputInfo);
                guiD.printNotification(sm.msgForRequestProcess(atc.RequestToUnfreeze(msg)));

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
                String askResponse = "Please enter a number (1 - set on-vacation status to true, 2 - set on-vacation status to false)";
                String input1 = guiD.getInPut(askResponse);
                if (idc.checkInt(input1)) {
                    int response = Integer.parseInt(input1);
                    if (response == 1) {
                        atc.setOnVacationStatus(true);
                        guiD.printNotification(sm.msgForResult(true));
                    } else if (response == 2) {
                        atc.setOnVacationStatus(false);
                        guiD.printNotification(sm.msgForResult(true));
                    } else {
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
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
                String askHC = "Please enter the name of the new home city.";
                String homeCity = guiD.getInPut(askHC);
                atc.changeUserHC(homeCity);
                guiD.printNotification(sm.msgForResult(true));
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
                ArrayList<Action> ownRevocableAction = atc.seeOwnRevocableAction();
                if (ownRevocableAction.size() != 0){
                    String str = sm.printListObject(new ArrayList<>(ownRevocableAction));
                    guiD.printNotification("Here's your list of revocable actions: \n " + str);
                }
                else{
                    guiD.printNotification(sm.msgForNothing("here."));
                }
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
                String askResponse = "Please enter the action id for the revocable action you would like to request to undo: ";
                String input1 = guiD.getInPut(askResponse);
                if (idc.checkInt(input1)) {
                    int actionId = Integer.parseInt(input1);
                   if (auIDC.checkActionId(actionId)){
                       atc.requestUndoARevocableAction(actionId);
                       sm.msgForRequestProcess(true);
                    }
                   else {
                       guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
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

    public void run(RegularUserAccountMenuController atc, SystemMessage sm,
                    GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idc,
                    GUIDemo guiD, AdminUserOtherInfoChecker auIDC) {
        JFrame frame = new JFrame("regularUserAccountSettingsMenuGUI");
        frame.setContentPane(new RegularUserAccountSettingsMenuGUI(atc, sm, guiUserInputInfo, idc, guiD, auIDC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
