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
                String input = guiD.getInPut(askUserid, guiUserInputInfo);
                if (idChecker.checkInt(input)){
                    int userId = Integer.parseInt(input);
                    if (idChecker.checkUserID(userId)){
                        guiD.printNotification(sm.msgForResult(atc.followAnUser(userId)));
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
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
                String input = guiD.getInPut(askItemid, guiUserInputInfo);
                if (idChecker.checkInt(input)){
                    int itemId = Integer.parseInt(input);
                    if (idChecker.checkItemID(itemId, 1)){
                        guiD.printNotification(sm.msgForResult(atc.followAnItem(itemId)));
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
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
                viewUpdates(sm, atc.seeRecentStatusOfFollowedUser(), "users", guiD);
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
                viewUpdates(sm, atc.seeRecentStatusOfFollowedItem(), "items", guiD);
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

    private void viewUpdates (SystemMessage sm, List<String> updates, String type, GUIDemo guiDemo) {
        if (updates.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(updates));
            guiDemo.printNotification("Here's the list of updates for the " + type + " you're following \n" + str);
        } else {
            guiDemo.printNotification(sm.msgForNo("updates for now."));
        }
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
