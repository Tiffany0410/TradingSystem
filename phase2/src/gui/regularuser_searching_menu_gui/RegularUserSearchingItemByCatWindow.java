package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserSearchingItemByCatWindow {
    private JComboBox comboBox1;
    private JButton cancelButton;
    private JButton searchButton;
    private JPanel rootPanel;

    public RegularUserSearchingItemByCatWindow(RegularUserSearchingMenuController smc, SystemMessage systemMessage, GUIDemo guiDemo){
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBox1.getSelectedItem();
                Category cat = Category.valueOf(selected);
                ArrayList<Item> items = smc.filterByCategory(cat);
                if (items.size() == 0) {
                    guiDemo.printNotification("There is no items in this category, please check later :)");
                }
                else {
                    guiDemo.printNotification(systemMessage.printListObject(new ArrayList<>(items)));
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

    public void run(RegularUserSearchingMenuController smc, SystemMessage systemMessage, GUIDemo guiDemo){
        JFrame frame = new JFrame("Searching Items by Category");
        frame.setContentPane(new RegularUserSearchingItemByCatWindow(smc, systemMessage, guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
