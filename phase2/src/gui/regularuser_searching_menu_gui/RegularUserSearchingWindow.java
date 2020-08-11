package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to show regular searching window
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserSearchingWindow {
    private JPanel rootPanel;
    private javax.swing.JLabel JLabel;
    private JTextField textField;
    private JButton cancelButton;
    private JButton confirmButton;

    /**
     * Constructor of regular user searching window
     * @param inputName name of user input
     * @param option actions option
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param idC RegularUserIDChecker
     */
    public RegularUserSearchingWindow(String inputName, int option, GUIDemo guiDemo, SystemMessage systemMessage,
                                      RegularUserSearchingMenuController regularUserSearchingMenuController,
                                      RegularUserIDChecker idC) {
        JLabel.setText(inputName);

        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                searchingExecute(option, regularUserSearchingMenuController, guiDemo, systemMessage, idC);
                guiDemo.closeWindow(rootPanel);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    /**
     * This method execute related operations
     * @param option actions option
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param idC RegularUserIDChecker
     */
    private void searchingExecute(int option, RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo, SystemMessage systemMessage, RegularUserIDChecker idC) {
        if (option == 2){
            ArrayList<Item> c = regularUserSearchingMenuController.searchItemByName(textField.getText());

            if (c.size() == 0) {
                guiDemo.printNotification(systemMessage.msgForNothing());
            } else {
                guiDemo.printNotification(systemMessage.printListObject(new ArrayList<>(c)));
            }

        }


        if (option == 3) {
            try {
                int id = Integer.parseInt(textField.getText());
                if (idC.checkItemID(id)){
                    Item item = regularUserSearchingMenuController.getItemObjectById(id);
                    ArrayList<Item> items = new ArrayList<>();
                    items.add(item);
                    guiDemo.printNotification(systemMessage.printListObject(new ArrayList<>(items)));
                    guiDemo.runSave();
                }
                else{
                    guiDemo.printNotification("This item id is invalid :(");
                }
            }
            catch (NumberFormatException ex) {
                guiDemo.printNotification("Please enter number!");
            }
        }
    }

    /**
     * Run regular user searching window
     * @param inputName name of user input
     * @param option actions option
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param idC RegularUserIDChecker
     */
    public void run(String inputName, int option, GUIDemo guiDemo, SystemMessage systemMessage,
                    RegularUserSearchingMenuController regularUserSearchingMenuController, RegularUserIDChecker idC) {
        JFrame frame = new JFrame("RegularUserSearchingWindow");
        frame.setContentPane(new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
