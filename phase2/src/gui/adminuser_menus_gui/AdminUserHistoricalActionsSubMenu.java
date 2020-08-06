package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
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

    public AdminUserHistoricalActionsSubMenu(GUIDemo guiDemo, SystemMessage systemMessage,
                                             AdminUserHistoricalActionController adminUserHistoricalActionController,
                                             GUIUserInputInfo guiUserInputInfo) {
        listAllTheHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Action> allAction = adminUserHistoricalActionController.getAllAction();
                printObjects(allAction, systemMessage);
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
                ArrayList<Action> allAction = adminUserHistoricalActionController.getAllRevocableAction();
                printObjects(allAction, systemMessage);
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

                String string = "Here are all TradableUser ID: \n";
                string = string + adminUserHistoricalActionController.getAllTradableUser();
                string = string + "Please enter the user ID: ";

                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);
                String input = guiDemo.getUserInput();

                try{
                    int id = Integer.parseInt(input);
                    //int userID = 0;
                    ArrayList<Action> allAction = adminUserHistoricalActionController.searchRevocableActionByUserID(id);
                    printObjects(allAction, systemMessage);
                } catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
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
                // TODO:print "Please enter the id of action that you want to cancel: \n"
                // TODO:get the action id enter by admin

                String string = "Here are the list of all actions: \n";
                string = string + adminUserHistoricalActionController.getAllAction();
                string = string + "Please enter the action ID you want to cancel: \n";

                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);
                String input = guiDemo.getUserInput();

                try{
                    int actionID = Integer.parseInt(input);
                    //int actionID = 0;
                    Action targetAction = adminUserHistoricalActionController.findActionByID(actionID);
                    // check if the action id in current revocable list
                    if (adminUserHistoricalActionController.checkRevocable(targetAction)) {
                        adminUserHistoricalActionController.cancelRevocableAction(targetAction);}
                } catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
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
                //TODO:not sure about this new feature
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


    public void run(GUIDemo guiDemo, SystemMessage systemMessage,
                    AdminUserHistoricalActionController adminUserHistoricalActionController,
                    GUIUserInputInfo guiUserInputInfo) {
        JFrame frame = new JFrame("AdminUserHistroicalActionsSubMenu");
        frame.setContentPane(new AdminUserHistoricalActionsSubMenu(guiDemo, systemMessage, adminUserHistoricalActionController, guiUserInputInfo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
