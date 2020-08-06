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
                // print "Here are all the TradableUser Id: \n"
                // print all tradable user id
                // get the id enter by admin
                int userID = 0;
                ArrayList<Action> allAction = hac.searchRevocableActionByUserID(userID);
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
