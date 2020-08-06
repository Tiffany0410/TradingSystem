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
                String msg = getLongInput("Please enter a message indicating reasons why you should " +
                        " be unfrozen", guiUserInputInfo);
                atc.RequestToUnfreeze(msg);
                sm.msgForRequestProcess(true);

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
                String input1 = getInPut(askResponse, guiUserInputInfo);
                if (idc.checkInt(input1)) {
                    int response = Integer.parseInt(input1);
                    if (response == 1) {
                        atc.setOnVacationStatus(true);
                        printNote(sm.msgForResult(true));
                    } else if (response == 2) {
                        atc.setOnVacationStatus(false);
                        printNote(sm.msgForResult(true));
                    } else {
                        printNote(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    printNote(sm.tryAgainMsgForWrongFormatInput());
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
                String homeCity = getInPut(askHC, guiUserInputInfo);
                atc.changeUserHC(homeCity);
                printNote(sm.msgForResult(true));
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
                //TODO: check if there's toString method in the action class
                ArrayList<Action> ownRevocableAction = atc.seeOwnRevocableAction();
                if (ownRevocableAction.size() != 0){
                    String str = sm.printListObject(new ArrayList<>(ownRevocableAction));
                    printNote("Here's your list of revocable actions: \n " + str);
                }
                else{
                    printNote(sm.msgForNothing("here."));
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
                String input1 = getInPut(askResponse, guiUserInputInfo);
                if (idc.checkInt(input1)) {
                    int actionId = Integer.parseInt(input1);
                   if (auIDC.checkActionId(actionId)){
                       atc.requestUndoARevocableAction(actionId);
                       sm.msgForRequestProcess(true);
                    }
                   else {
                        printNote(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    printNote(sm.tryAgainMsgForWrongFormatInput());
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
                guiD.runRegularUserAccountMainMenuGUI(false);
            }


        });

    }

    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String userResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return userResponse;

    }

    public String getLongInput(String str, GUIUserInputInfo guiUserInputInfo){
        UserInputGUI userInputGUI = new UserInputGUI(str, guiUserInputInfo);
        userInputGUI.run(str, guiUserInputInfo);
        String userResponse = guiUserInputInfo.getTempUserInput();
        // TODO: need to close first
        return userResponse;
    }

    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }

    public void run(GUIDemo guiD, RegularUserAccountMenuController atc, GUIUserInputInfo guiUserInputInfo,
                    RegularUserIDChecker idChecker, SystemMessage sm) {
        JFrame frame = new JFrame("regularUserAccountSettingsMenuGUI");
        frame.setContentPane(new RegularUserFollowMenuGUI(guiD, atc, guiUserInputInfo, idChecker, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
