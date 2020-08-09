package gui.regularuser_account_menus_gui.manage_items.manageItemsWindows;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserRequestItemWindow {
    private JTextField name;
    private JTextArea description;
    private JComboBox category;
    private JButton cancelButton;
    private JButton addButton;
    private JPanel rootPanel;

    public RegularUserRequestItemWindow(GUIDemo guiDemo, RegularUserAccountMenuController amc, RegularUserOtherInfoChecker otherInfoChecker){
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

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
