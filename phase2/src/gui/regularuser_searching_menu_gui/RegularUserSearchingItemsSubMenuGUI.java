package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserSearchingItemsSubMenuGUI {
    private JButton filterByCategoryButton;
    private JPanel rootPanel;
    private JButton searchItemByNameButton;
    private JButton searchItemByIdButton;
    private JButton sortByNumberOfButton;
    private JButton backButton;

    public RegularUserSearchingItemsSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                               GUIDemo guiDemo, SystemMessage systemMessage) {
        filterByCategoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
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
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputName = "Please enter the item name:";
                int option = 2;

                RegularUserSearchingWindow regularUserSearchingWindow = new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController);
                regularUserSearchingWindow.run(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController);

                //close this window
                //guiDemo.closeWindow(rootPanel);

//                UserInputGUI userInputGUI1 = new UserInputGUI(string, guiUserInputInfo);
//                userInputGUI1.run(string, guiUserInputInfo);
//
//                String name = guiUserInputInfo.getTempUserInput();
//                UserInputGUI userInputGUI2 = new UserInputGUI(name, guiUserInputInfo);
//                userInputGUI2.run(name, guiUserInputInfo);


            }
        });
        searchItemByIdButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputName = "Please enter the item ID:";
                int option = 3;

                RegularUserSearchingWindow regularUserSearchingWindow = new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage, regularUserSearchingMenuController);
                regularUserSearchingWindow.run(inputName, option, guiDemo, systemMessage, regularUserSearchingMenuController);

                //close this window
                //guiDemo.closeWindow(rootPanel);
            }
        });
        sortByNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> items = regularUserSearchingMenuController.sortItemByFollows();
                guiDemo.printNotification(systemMessage.printItemResult(items));
                guiDemo.closeWindow(rootPanel);
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runRegularUserSearchingMenuGUI();
            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingItemsSubMenu");
        frame.setContentPane(new RegularUserSearchingItemsSubMenuGUI(regularUserSearchingMenuController, guiDemo,
                systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }



}
