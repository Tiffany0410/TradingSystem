package gui.regularuser_account_menus_gui.manage_items.manageItemsWindows;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to show regular user change tradable status for items window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserManageItemsTradableStatusWindow {
    private JTextPane textPane1;
    private JTextField userInput;
    private JButton cancelButton;
    private JButton setButton;
    private JPanel rootPanel;
    private JRadioButton setToNONTRADABLERadioButton;
    private JRadioButton setToTRADABLERadioButton;

    /**
     * Constructor for regular user change tradable status for items window
     * @param inventory A list of items in inventory
     * @param string A string representation of items in inventory
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public RegularUserManageItemsTradableStatusWindow(ArrayList<Item> inventory, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        ButtonGroup group = new ButtonGroup();
        group.add(setToNONTRADABLERadioButton);
        group.add(setToTRADABLERadioButton);

        setButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, set tradable status
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTradableStatus(idChecker, inventory, amc, guiDemo, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, close this window
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void changeTradableStatus(RegularUserIDChecker idChecker, ArrayList<Item> inventory, RegularUserAccountMenuController amc, GUIDemo guiDemo, SystemMessage sm) {
        boolean result;
        String input = userInput.getText();
        if (idChecker.checkInt(input)){
            int itemId = Integer.parseInt(input);
            if (idChecker.checkItemID(inventory, itemId)){
                if (setToNONTRADABLERadioButton.isSelected()){
                    result = amc.setTradableBasedOnResponse(itemId, false);
                    guiDemo.printNotification(sm.msgForSetTradable(result, false));
                }
                else if (setToTRADABLERadioButton.isSelected()) {
                    result = amc.setTradableBasedOnResponse(itemId, true);
                    guiDemo.printNotification(sm.msgForSetTradable(result, true));
                }
                else{
                    guiDemo.printNotification("Please select an option,");
                }
            }
            else{
                guiDemo.printNotification("Please enter a valid item id that is on the list.");
            }
        }
        else{
            guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guiDemo.closeWindow(rootPanel);
    }

    /**
     * Run regular user change tradable status for items window
     * @param inventory A list of items in inventory
     * @param string A string representation of items in inventory
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public void run(ArrayList<Item> inventory, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Set Tradable Status for Item");
        frame.setContentPane(new RegularUserManageItemsTradableStatusWindow(inventory, string, guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
