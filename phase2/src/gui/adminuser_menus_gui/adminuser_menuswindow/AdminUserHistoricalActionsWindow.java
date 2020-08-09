package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import gui.GUIDemo;
import managers.actionmanager.Action;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminUserHistoricalActionsWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton confirmButton;
    private JButton cancelButton;
    private JTextArea textArea;
    private JScrollBar scrollBar1;
    private javax.swing.JLabel JLabel;

    public AdminUserHistoricalActionsWindow(String inputName, String info, int option, GUIDemo guiDemo, AdminUserHistoricalActionController adminUserHistoricalActionController, SystemMessage systemMessage) {
        JLabel.setText(inputName);
        textArea.setText(info);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));


        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option == 3){
                    String username = textField.getText();
                    int userID = adminUserHistoricalActionController.getUserID(username);
                    ArrayList<Action> allAction;

                    if (adminUserHistoricalActionController.checkUser(username)) {
                        allAction = adminUserHistoricalActionController.searchRevocableActionByUserID(userID);
                        printObjects(allAction, systemMessage , guiDemo);
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

                guiDemo.closeWindow(rootPanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                //guiDemo.runAdminUserHistoricalActionsSubMenu();

            }
        });
    }

    public void run(String inputName, String info, int option, GUIDemo guiDemo, AdminUserHistoricalActionController adminUserHistoricalActionController, SystemMessage systemMessage) {
        JFrame frame = new JFrame("AdminUserHistoricalActionsWindow");
        frame.setContentPane(new AdminUserHistoricalActionsWindow(inputName, info, option, guiDemo, adminUserHistoricalActionController, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(500, 300);
        //frame.pack();
        frame.setVisible(true);
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

}
