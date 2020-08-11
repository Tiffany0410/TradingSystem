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
 * Used to show regular user remove from inventory window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserManageItemsRemoveInvtyWindow {
    private JTextPane textPane1;
    private JButton cancelButton;
    private JButton removeButton;
    private JTextField userInput;
    private JPanel rootPanel;

    /**
     * Constructor for Regular User Remove Items From Inventory Window
     * @param items A list of items that is in user's inventory
     * @param string String representation for the items in inventory
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public RegularUserManageItemsRemoveInvtyWindow(ArrayList<Item> items, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242, 242, 242));

        removeButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, remove from wishlist
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromInventory(idChecker, items, amc, guiDemo, sm);
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

    private void removeFromInventory(RegularUserIDChecker idChecker, ArrayList<Item> items, RegularUserAccountMenuController amc, GUIDemo guiDemo, SystemMessage sm) {
        String input = userInput.getText();
        if (idChecker.checkInt(input)) {
            int itemId = Integer.parseInt(input);
            if (idChecker.checkItemID(items, itemId)) {
                boolean result = amc.removeFromInventory(itemId);
                guiDemo.printNotification(sm.msgForResult(result));
            }
            else {
                guiDemo.printNotification("Invalid item id was entered, please try again.");
            }
        }
        else {
            guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guiDemo.closeWindow(rootPanel);
    }

    /**
     * Run Regular User Remove Items From Inventory Window
     * @param items A list of items that is in user's inventory
     * @param string String representation for the items in inventory
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public void run(ArrayList<Item> items, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Remove From Inventory");
        frame.setContentPane(new RegularUserManageItemsRemoveInvtyWindow(items, string, guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
