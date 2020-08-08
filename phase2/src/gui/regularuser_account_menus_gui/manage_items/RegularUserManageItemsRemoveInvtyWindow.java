package gui.regularuser_account_menus_gui.manage_items;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserManageItemsRemoveInvtyWindow {
    private JTextPane textPane1;
    private JButton cancelButton;
    private JButton removeButton;
    private JTextField userInput;
    private JPanel rootPanel;

    public RegularUserManageItemsRemoveInvtyWindow(ArrayList<Item> items, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242, 242, 242));

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                    guiDemo.printNotification("Please enter an integer.");
                }
                guiDemo.closeWindow(rootPanel);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    public void run(ArrayList<Item> items, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Remove From Inventory");
        frame.setContentPane(new RegularUserManageItemsRemoveInvtyWindow(items, string, guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
