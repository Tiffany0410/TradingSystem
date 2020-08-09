package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import demomanager.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserHistoricalActionsWindow;
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
                printObjects(allAction, sm, guiDemo);
                guiDemo.runSave();
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
                guiDemo.runSave();
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
                String info = "Here are all the TradableUser: \n";
                ArrayList<TradableUser> allUser = hac.getAllTradableUser();

                //print all tradable user
                info = info + sm.printListUser(allUser);

                //get the user username enter by admin
                int option = 3;
                String inputName = "Please enter the username that you want to search:";

                AdminUserHistoricalActionsWindow adminUserHistoricalActionsWindow = new AdminUserHistoricalActionsWindow(
                         inputName, info, option, guiDemo, hac, sm);
                adminUserHistoricalActionsWindow.run(inputName, info, option, guiDemo, hac, sm);

//                String username = guiDemo.getInPut(string);
//                int userID = hac.getUserID(username);
//                ArrayList<Action> allAction;
//
//                if (hac.checkUser(username)) {
//                    allAction = hac.searchRevocableActionByUserID(userID);
//                    printObjects(allAction, sm, guiDemo);
//                    guiDemo.runSave();
//                }
//                else {
//                    guiDemo.printNotification("Please enter correct username");
//                }
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
                String inputName = "Please enter the ID of action that you want to cancel: ";
                String info = "Here is Cancel the revocable historical actions of tradableUser by actionID window: \n";
                int option = 4;

                AdminUserHistoricalActionsWindow adminUserHistoricalActionsWindow = new AdminUserHistoricalActionsWindow(
                        inputName, info, option, guiDemo, hac, sm);
                adminUserHistoricalActionsWindow.run(inputName, info, option, guiDemo, hac, sm);


//                try {
//                    String userInput = guiDemo.getInPut(string);
//                    int actionID = Integer.parseInt(userInput);
//
//                    boolean flag = false;
//                    Action targetAction = hac.findActionByID(actionID);
//
//                    // check if the action id in current revocable list
//                    if (hac.checkRevocable(targetAction)) {
//                        if (hac.cancelRevocableAction(targetAction)) {
//                            guiDemo.printNotification("Successfully delete target action");
//                            guiDemo.runSave();
//                        }
//                    } else {
//                        guiDemo.printNotification("Please enter correct actionID");
//                    }
//
//                } catch (NumberFormatException ex){
//                    guiDemo.printNotification(sm.printInvalidID());
//                }

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
                StringBuilder info = new StringBuilder();

                info.append("Here is undo request: \n");

                for (Map.Entry<Integer, Integer> entry : undoRequests.entrySet()) {
                    info.append("Tradable User# ").append(entry.getValue()).append(" request to undo Revocable Action #").append(entry.getKey()).append("\n");
                }

                int option = 5;

                String inputName = "Please enter the Action Number that you wan to undo: ";

                AdminUserHistoricalActionsWindow adminUserHistoricalActionsWindow = new AdminUserHistoricalActionsWindow(
                        inputName, info.toString(), option, guiDemo, hac, sm);
                adminUserHistoricalActionsWindow.run(inputName, info.toString(), option, guiDemo, hac, sm);


//                //get the actionID enter by admin
//                try {
//                    int actionID = Integer.parseInt(guiDemo.getInPut(string));
//
//
//                    boolean flag = false;
//                    if (hac.checkUndoRequest(actionID)) {
//                        flag = hac.confirmRequestAndCancelAction(actionID);
//                    } else {
//                        guiDemo.printNotification("Please enter correct actionID");
//                    }
//
//                    if (flag) {
//                        guiDemo.runSave();
//                        guiDemo.printNotification("Successfully delete target action");
//                    }
//
//                } catch (NumberFormatException ex){
//                    sm.printInvalidID();
//                }


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
                //close this window
                guiDemo.closeWindow(rootPanel);

                guiDemo.runAdminUserMainMenu();
            }
        });
    }

    private void printObjects(ArrayList<Action> actions, SystemMessage sm, GUIDemo guiDemo){
        if (actions.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here"));
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
        frame.setLocationRelativeTo(null);
    }
}
