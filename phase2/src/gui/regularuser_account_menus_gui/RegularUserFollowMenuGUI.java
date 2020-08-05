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
import java.util.List;

public class RegularUserFollowMenuGUI {
    private JButton followAnUserButton;
    private JButton followAnItemButton;
    private JButton seeRecentStatusOfUsersButton;
    private JButton seeRecentStatusOfItemsButton1;
    private JButton backButton;
    private JPanel rootPanel;

    public RegularUserFollowMenuGUI(GUIDemo guiD, RegularUserAccountMenuController atc, GUIUserInputInfo guiUserInputInfo,
                                    RegularUserIDChecker idChecker, SystemMessage sm) {
        followAnUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String askUserid = "Please enter the user id of the user you would like to follow";
                String input = getInPut(askUserid, guiUserInputInfo);
                if (idChecker.checkInt(input)){
                    int userId = Integer.parseInt(input);
                    if (idChecker.checkUserID(userId)){
                        printNote(sm.msgForResult(atc.followAnUser(userId)));
                    }
                    else{
                        printNote(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    printNote(sm.tryAgainMsgForWrongFormatInput());
                }
            }
        });
        followAnItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String askItemid = "Please enter the item id of the item you would like to follow";
                String input = getInPut(askItemid, guiUserInputInfo);
                if (idChecker.checkInt(input)){
                    int itemId = Integer.parseInt(input);
                    if (idChecker.checkItemID(itemId, 1)){
                        printNote(sm.msgForResult(atc.followAnItem(itemId)));
                    }
                    else{
                        printNote(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    printNote(sm.tryAgainMsgForWrongFormatInput());
                }

            }
        });
        seeRecentStatusOfUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUpdates(sm, atc.seeRecentStatusOfFollowedUser(), "users");
            }
        });
        seeRecentStatusOfItemsButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUpdates(sm, atc.seeRecentStatusOfFollowedItem(), "items");
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
                guiD.runRegularUserAccountMainMenu(false);
            }
        });
    }

    private void viewUpdates (SystemMessage sm, List<String> updates, String type) {
        if (updates.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(updates));
            printNote("Here's the list of updates for the " + type + " you're following \n" + str);
        } else {
            printNote(sm.msgForNo("updates for now."));
        }
    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String userResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return userResponse;

    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }

    public void run(GUIDemo guiD, RegularUserAccountMenuController atc, GUIUserInputInfo guiUserInputInfo,
                    RegularUserIDChecker idChecker, SystemMessage sm) {
        JFrame frame = new JFrame("regularUserFollowMenuGUI");
        frame.setContentPane(new RegularUserFollowMenuGUI(guiD, atc, guiUserInputInfo, idChecker, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
