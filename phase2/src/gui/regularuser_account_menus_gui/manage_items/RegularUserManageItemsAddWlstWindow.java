package gui.regularuser_account_menus_gui.manage_items;

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

public class RegularUserManageItemsAddWlstWindow {
    private JTextPane textPane1;
    private JTextField userInput;
    private JButton cancelButton;
    private JButton addButton;
    private JPanel rootPanel;

    public RegularUserManageItemsAddWlstWindow(ArrayList<Item> tradable, String string, GUIDemo guiDemo, SystemMessage
             sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                if (idChecker.checkInt(input)){
                    int itemId = Integer.parseInt(input);
                    if (idChecker.checkItemID(tradable, itemId)){
                        boolean result = amc.addToWishList(itemId);
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

    public void run(ArrayList<Item> tradable, String string, GUIDemo guiDemo, SystemMessage
            sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Add to Wishlist");
        frame.setContentPane(new RegularUserManageItemsAddWlstWindow(tradable, string, guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
