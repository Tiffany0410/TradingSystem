package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import gui.GUIDemo;
import gui.NotificationGUI;
import managers.actionmanager.Action;
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
                // TODO:print "Here are all the TradableUser Id: \n"
                // TODO:print all tradable user id
                // TODO:get the user id enter by admin
                int userID = 0;
                ArrayList<Action> allAction = new ArrayList<>();
                if (hac.checkUser(userID)) {
                    allAction = hac.searchRevocableActionByUserID(userID);
                }
                else {
                    //TODO: print "Please enter correct userID"
                }
                printObjects(allAction, sm);
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
                // TODO:print "Please enter the ID of action that you want to cancel: \n"
                // TODO:get the action id enter by admin
                int actionID = 0;
                boolean flag = false;
                Action targetAction = hac.findActionByID(actionID);
                // check if the action id in current revocable list
                if (hac.checkRevocable(targetAction)) {
                    flag = hac.cancelRevocableAction(targetAction);
                }
                else {//TODO:print"Please enter correct actionID"
                }
                if (flag) {//TODO:print "Successfully delete target action"
                }
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
                Map<Integer, Integer> undoRequests = new HashMap<>();
                undoRequests.putAll(hac.getUndoRequest());
                //TODO:print whole map(key is actionID, value is regularUser id)
                //TODO:print "Please enter the ID of action that you want to cancel: \n"
                //TODO:get the actionID enter by admin
                int actionID = 0;
                boolean flag = false;
                if (hac.checkUndoRequest(actionID)) {
                    flag = hac.confirmRequestAndCancelAction(actionID);}
                else {//TODO:print"Please enter correct actionID"
                }
                if (flag) {//TODO:print "Successfully delete target action"
                }

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
                //TODO:
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
