package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import gui.GUIDemo;
import managers.actionmanager.Action;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
                printObjects(allAction, sm, guiDemo);
                try {
                    guiDemo.runSave();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
                printObjects(allAction, sm, guiDemo);
                try {
                    guiDemo.runSave();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
                printObjects(allAction, sm, guiDemo);
                try {
                    guiDemo.runSave();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
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
                        try {
                            guiDemo.runSave();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        guiDemo.printNotification("Successfully delete target action");
                    }
                } catch (NumberFormatException ex){
                    sm.printInvalidID();
                }
                //close this window
//                guiDemo.closeWindow(rootPanel);
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
                        try {
                            guiDemo.runSave();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        guiDemo.printNotification("Successfully delete target action");
                    }

                } catch (NumberFormatException ex){
                    sm.printInvalidID();
                }

                //close this window
//                guiDemo.closeWindow(rootPanel);

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
                //close this window
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void printObjects(ArrayList<Action> actions, SystemMessage sm, GUIDemo guiDemo){
        if (actions.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here."));
        }
        else{
            String str = sm.printHistoricalAction(new ArrayList<>(actions));
            guiDemo.printNotification(str);
        }
    }


    public void run(GUIDemo guiDemo, SystemMessage sm, AdminUserHistoricalActionController hac) {
        JFrame frame = new JFrame("AdminUserHistoricalActionsSubMenu");
        frame.setContentPane(new AdminUserHistoricalActionsSubMenu(guiDemo, sm, hac).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
