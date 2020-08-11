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

/**
 * Used to show admin user historical acrions submenu
 * @author Jiaqi Gong, Yangle Cheng
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserHistoricalActionsSubMenu {
    private JPanel rootPanel;
    private JButton listAllTheHistoricalButton;
    private JButton cancelTheRevocableHistoricalButton;
    private JButton findAllTheRevocableByIDButton;
    private JButton confirmUndoRequestButton;
    private JButton backButton;
    private JButton listAllTheRevocableButton;

    /**
     * Constructor of admin user historical actions submenu gui
     * @param guiDemo GUIDemo
     * @param sm system message
     * @param hac adminUserHistoricalActionsController
     */
    public AdminUserHistoricalActionsSubMenu(GUIDemo guiDemo, SystemMessage sm, AdminUserHistoricalActionController hac) {
        listAllTheHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
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
             * Invoked when click button abd do related operations
             *
             * @param e click button
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
             * Invoked when click the button and do related operations
             *
             * @param e click button
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
            }
        });
        cancelTheRevocableHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click the button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputName = "Please enter the ID of action that you want to cancel: ";
                String info = "Here is Cancel the revocable historical actions of tradableUser by actionID window: \n";
                int option = 4;

                AdminUserHistoricalActionsWindow adminUserHistoricalActionsWindow = new AdminUserHistoricalActionsWindow(
                        inputName, info, option, guiDemo, hac, sm);
                adminUserHistoricalActionsWindow.run(inputName, info, option, guiDemo, hac, sm);

            }
        });
        confirmUndoRequestButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
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

                String inputName = "Please enter the Action Number that you want to undo: ";

                AdminUserHistoricalActionsWindow adminUserHistoricalActionsWindow = new AdminUserHistoricalActionsWindow(
                        inputName, info.toString(), option, guiDemo, hac, sm);
                adminUserHistoricalActionsWindow.run(inputName, info.toString(), option, guiDemo, hac, sm);
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do realted operation
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserMainMenu();
            }
        });
    }

    /**
     * This is method print the info of given action list
     * @param actions list of actions
     * @param sm system message
     * @param guiDemo GUIDemo
     */

    private void printObjects(ArrayList<Action> actions, SystemMessage sm, GUIDemo guiDemo){
        if (actions.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here"));
        }
        else{
            String str = sm.printHistoricalAction(new ArrayList<>(actions));
            if (str.equals("")){
                guiDemo.printNotification("There is no actions.");
            }
            else{
                guiDemo.printNotification(str);
            }
        }
    }

    /**
     * Run this GUI
     * @param guiDemo GUIDemo
     * @param sm system Message
     * @param hac adminUserHistoricalActionController
     */
    public void run(GUIDemo guiDemo, SystemMessage sm, AdminUserHistoricalActionController hac) {
        JFrame frame = new JFrame("AdminUserHistoricalActionsSubMenu");
        frame.setContentPane(new AdminUserHistoricalActionsSubMenu(guiDemo, sm, hac).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
