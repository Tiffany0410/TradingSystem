package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import demomanager.GUIDemo;
import managers.actionmanager.Action;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to edit user historical actions
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserHistoricalActionsWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton confirmButton;
    private JButton cancelButton;
    private javax.swing.JLabel JLabel;
    private JScrollPane scrollPane;
    private JTextPane textPane1;

    /**
     * Constructor of admin user historical action window
     * @param inputName name of info want user input
     * @param info related message of this operation
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param adminUserHistoricalActionController admin user historical action controller
     * @param systemMessage system message
     */
    public AdminUserHistoricalActionsWindow(String inputName, String info, int option, GUIDemo guiDemo, AdminUserHistoricalActionController adminUserHistoricalActionController, SystemMessage systemMessage) {
        JLabel.setText(inputName);
        textPane1.setText(info);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click confirm button and run related method in controller
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                historicalActionExcute(option, adminUserHistoricalActionController, systemMessage, guiDemo);

                guiDemo.closeWindow(rootPanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click cancel button and return to menu
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    /**
     * This is a private method to execute the operation
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param adminUserHistoricalActionController admin user historical action controller
     * @param systemMessage system message
     */
    private void historicalActionExcute(int option, AdminUserHistoricalActionController adminUserHistoricalActionController, SystemMessage systemMessage, GUIDemo guiDemo) {
        if (option == 3){
            String username = textField.getText();
            int userID = adminUserHistoricalActionController.getUserID(username);
            ArrayList<Action> allAction;

            if (adminUserHistoricalActionController.checkUser(username)) {
                allAction = adminUserHistoricalActionController.searchRevocableActionByUserID(userID);
                printObjects(allAction, systemMessage, guiDemo);
                guiDemo.runSave();
            }
            else {
                guiDemo.printNotification("Please enter correct username");
            }

        }

        if (option == 4){

            try {
                String userInput = textField.getText();
                int actionID = Integer.parseInt(userInput);

                boolean flag = false;
                Action targetAction = adminUserHistoricalActionController.findActionByID(actionID);

                // check if the action id in current revocable list
                if (adminUserHistoricalActionController.checkRevocable(targetAction)) {
                    if (adminUserHistoricalActionController.cancelRevocableAction(targetAction)) {
                        guiDemo.printNotification("Successfully delete target action");
                        guiDemo.runSave();
                    }
                } else {
                    guiDemo.printNotification("Please enter correct actionID");
                }

            } catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }

        if (option == 5){
            //get the actionID enter by admin
            try {
                int actionID = Integer.parseInt(textField.getText());


                boolean flag = false;
                if (adminUserHistoricalActionController.checkUndoRequest(actionID)) {
                    flag = adminUserHistoricalActionController.confirmRequestAndCancelAction(actionID);
                } else {
                    guiDemo.printNotification("Please enter correct actionID");
                }

                if (flag) {
                    guiDemo.runSave();
                    guiDemo.printNotification("Successfully delete target action");
                }

            } catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }
    }

    /**
     * Run this window
     * @param inputName name of info want user input
     * @param info related message of this operation
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param adminUserHistoricalActionController admin user historical action controller
     * @param systemMessage system message
     */
    public void run(String inputName, String info, int option, GUIDemo guiDemo, AdminUserHistoricalActionController adminUserHistoricalActionController, SystemMessage systemMessage) {
        JFrame frame = new JFrame("AdminUserHistoricalActionsWindow");
        frame.setContentPane(new AdminUserHistoricalActionsWindow(inputName, info, option, guiDemo, adminUserHistoricalActionController, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * A helper method to print related message
     * @param actions users' actions
     * @param sm system message
     * @param guiDemo GUIDemo
     */
    private void printObjects(ArrayList<Action> actions, SystemMessage sm, GUIDemo guiDemo){
        if (actions.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here"));
        }
        else{
            String str = sm.printHistoricalAction(new ArrayList<>(actions));
            guiDemo.printNotification(str);
        }
    }

}
