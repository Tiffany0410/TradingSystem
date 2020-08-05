package gui.regularuser_account_menus_gui;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
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

    public RegularUserAccountSettingsMenuGUI(RegularUserAccountMenuController atc, SystemMessage sm,
                                             GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idc,
                                             GUIDemo guiD) {
        requestToUnfreezeAccountButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: class that allows user to enter long msg - editable text area
                atc.RequestToUnfreeze();
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
                if (idc.checkInt(input1)){
                    int response = Integer.parseInt(input1);
                    if (response == 1) {
                        atc.setOnVacationStatus(true);
                        printNote(sm.msgForResult(true));
                    }
                    else if (response == 2){
                        atc.setOnVacationStatus(false);
                        printNote(sm.msgForResult(true));
                        }
                    else{
                        printNote(sm.tryAgainMsgForWrongInput());
                        }
                    }
                }
                else{
                    printNote(sm.tryAgainMsgForWrongFormatInput());
                }
                }

        );
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
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //GO back to main menu
                guiD.runRegularUserMainMenu(false);
            }


        });
    }

    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String UserResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return string;

    }

    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }

}
