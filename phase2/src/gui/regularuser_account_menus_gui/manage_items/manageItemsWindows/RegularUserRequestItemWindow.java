package gui.regularuser_account_menus_gui.manage_items.manageItemsWindows;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to show regular user request item to add to inventory window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserRequestItemWindow {
    private JTextField name;
    private JTextArea description;
    private JComboBox category;
    private JButton cancelButton;
    private JButton addButton;
    private JPanel rootPanel;

    /**
     * Constructor for regular user request item to add to inventory window
     * @param guiDemo GUIDemo
     * @param amc RegularUserAccountMenuController
     * @param otherInfoChecker RegularUserOtherInfoChecker
     */
    public RegularUserRequestItemWindow(GUIDemo guiDemo, RegularUserAccountMenuController amc, RegularUserOtherInfoChecker otherInfoChecker){
        addButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, request add item to inventory
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                requestItem(otherInfoChecker, amc, guiDemo);
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

    private void requestItem(RegularUserOtherInfoChecker otherInfoChecker, RegularUserAccountMenuController amc, GUIDemo guiDemo) {
        String itemName = name.getText();
        String itemDescription = description.getText();
        String itemCategory = (String) category.getSelectedItem();
        if (otherInfoChecker.checkItemType(itemCategory)){
            Category item_category = Category.valueOf(itemCategory);
            amc.requestAddItem(itemName, itemDescription, item_category);
            guiDemo.runSave();
            guiDemo.printNotification("Item requested, please wait for an administrative user to confirm.");
        }
        else {
            guiDemo.printNotification("Please select the category of the item correctly.");
        }
        guiDemo.closeWindow(rootPanel);
    }

    /**
     * Run regular user request item to add to inventory window
     * @param guiDemo GUIDemo
     * @param amc RegularUserAccountMenuController
     * @param otherInfoChecker RegularUserOtherInfoChecker
     */
    public void run(GUIDemo guiDemo, RegularUserAccountMenuController amc, RegularUserOtherInfoChecker otherInfoChecker){
        JFrame frame = new JFrame("Request an item");
        frame.setContentPane(new RegularUserRequestItemWindow(guiDemo, amc, otherInfoChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
