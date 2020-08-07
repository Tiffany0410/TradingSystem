package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import gui.GUIDemo;
import gui.NotificationGUI;
import managers.actionmanager.Action;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminUserHistoricalActionsSubMenu {
    private JPanel rootPanel;
    private JButton listAllTheHistoricalButton;
    private JButton cancelTheRevocableHistoricalButton;
    private JButton findAllTheRevocableByIDButton;
    private JButton confirmUndoRequestButton;
    private JButton backButton;
    private JButton listAllTheRevocableButton;

    public AdminUserHistoricalActionsSubMenu(GUIDemo guiDemo, SystemMessage sm, AdminUserHistoricalActionController hac) {
        listAllTheHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Action> allAction = hac.getAllAction();
                printObjects(allAction, sm);

                // TODO: close this window
            }
        });
        listAllTheRevocableButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Action> allAction = hac.getAllRevocableAction();
                printObjects(allAction, sm);

                // TODO: close this window

            }
        });
        findAllTheRevocableByIDButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "Here are all the TradableUser: \n";
                ArrayList<TradableUser> allUser = hac.getAllTradableUser();

                //print all tradable user
                string = string + sm.printListUser(allUser);

                //get the user username enter by admin
                string = string + "\n Please enter the username: ";
                String username = guiDemo.getInPut(string);
                int userID = hac.getUserID(username);
                ArrayList<Action> allAction = new ArrayList<>();
                if (hac.checkUser(username)) {
                    allAction = hac.searchRevocableActionByUserID(userID);
                }
                else {
                    guiDemo.printNotification("Please enter correct username");
                }
                printObjects(allAction, sm);

                // TODO: close this window

            }
        });
        cancelTheRevocableHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = "Please enter the ID of action that you want to cancel: \n";

                try {
                    int actionID = Integer.parseInt(guiDemo.getInPut(string));

                    boolean flag = false;
                    Action targetAction = hac.findActionByID(actionID);
                    // check if the action id in current revocable list
                    if (hac.checkRevocable(targetAction)) {
                        flag = hac.cancelRevocableAction(targetAction);
                    } else {
                        guiDemo.printNotification("Please enter correct actionID");
                    }
                    if (flag) {
                        guiDemo.printNotification("Successfully delete target action");
                    }
                } catch (NumberFormatException ex){
                    sm.printInvalidID();
                }
                // TODO: close this window
            }
        });
        confirmUndoRequestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<Integer, Integer> undoRequests = new HashMap<>(hac.getUndoRequest());

                //print whole map(key is actionID, value is regularUser id)
                String string = undoRequests.toString();

                string = string + "Please enter the ID of action that you want to cancel: \n";

                //get the actionID enter by admin
                try {
                    int actionID = Integer.parseInt(guiDemo.getInPut(string));


                    boolean flag = false;
                    if (hac.checkUndoRequest(actionID)) {
                        flag = hac.confirmRequestAndCancelAction(actionID);
                    } else {
                        guiDemo.printNotification("Please enter correct actionID");
                    }

                    if (flag) {
                        guiDemo.printNotification("Successfully delete target action");
                    }

                } catch (NumberFormatException ex){
                    sm.printInvalidID();
                }

                // TODO: close this window

            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserMainMenu();
                //TODO: Close this window
            }
        });
    }

    private void printObjects(ArrayList<Action> actions, SystemMessage sm){
        if (actions.isEmpty()){
            printNote(sm.msgForNothing("here."));
        }
        else{
            String str = sm.printHistoricalAction(new ArrayList<>(actions));
            printNote(str);
        }
    }

    private void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }


    public void run(GUIDemo guiDemo, SystemMessage sm, AdminUserHistoricalActionController hac) {
        JFrame frame = new JFrame("AdminUserHistroicalActionsSubMenu");
        frame.setContentPane(new AdminUserHistoricalActionsSubMenu(guiDemo, sm, hac).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
