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
 * Used to show regular searching submenu
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserSearchingItemsSubMenuGUI {
    private JButton filterByCategoryButton;
    private JPanel rootPanel;
    private JButton searchItemByNameButton;
    private JButton searchItemByIdButton;
    private JButton sortByNumberOfButton;
    private JButton backButton;

    /**
     * Constructor of regular user searching item sub menu
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     * @param idC regularUserIDChecker
     */
    public RegularUserSearchingItemsSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                               GUIDemo guiDemo, SystemMessage systemMessage, RegularUserIDChecker idC) {
        filterByCategoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related actions
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RegularUserSearchingItemByCatWindow window = new
                        RegularUserSearchingItemByCatWindow(regularUserSearchingMenuController, systemMessage, guiDemo);
                window.run(regularUserSearchingMenuController, systemMessage, guiDemo);
            }
        });
        searchItemByNameButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputName = "Please enter the keyword you want to search for:";
                int option = 2;

                RegularUserSearchingWindow regularUserSearchingWindow = new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController, idC);
                regularUserSearchingWindow.run(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController, idC);

            }
        });
        searchItemByIdButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputName = "Please enter the item ID:";
                int option = 3;
                RegularUserSearchingWindow regularUserSearchingWindow = new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage, regularUserSearchingMenuController, idC);
                regularUserSearchingWindow.run(inputName, option, guiDemo, systemMessage, regularUserSearchingMenuController, idC);
            }
        });
        sortByNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> items = regularUserSearchingMenuController.sortItemByFollows();
                if (items.isEmpty()){
                    guiDemo.printNotification(systemMessage.msgForNothing());
                }
                else{
                guiDemo.printNotification(systemMessage.printItemResult(items));}
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click button and do related operations
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runRegularUserSearchingMenuGUI();
            }
        });
    }

    /**
     * Run regular user searching items sub menu gui
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     * @param idC RegularUserIDChecker
     */
    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage, RegularUserIDChecker idC) {
        JFrame frame = new JFrame("RegularUserSearchingItemsSubMenu");
        frame.setContentPane(new RegularUserSearchingItemsSubMenuGUI(regularUserSearchingMenuController, guiDemo,
                systemMessage, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }



}
