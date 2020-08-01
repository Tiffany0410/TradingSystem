package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.UserInputGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserSearchingItemsSubMenu {
    private JButton filterByCategoryButton;
    private JPanel rootPanel;
    private JButton searchItemByNameButton;
    private JButton searchItemByIdButton;
    private JButton sortByNumberOfButton;
    private JButton backButton;

    public RegularUserSearchingItemsSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo) {
        filterByCategoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call filter by category method and close window
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
                String string = "Please enter the item name:";

                UserInputGUI userInputGUI1 = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI1.run(string, guiUserInputInfo);

                String name = guiUserInputInfo.getTempUserInput();
                UserInputGUI userInputGUI2 = new UserInputGUI(name, guiUserInputInfo);
                userInputGUI2.run(name, guiUserInputInfo);

                regularUserSearchingMenuController.searchItemByName(guiDemo.getUserInput());

                // TODO: Need method to close this window

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
                String string = "Please enter the item ID:";

                UserInputGUI userInputGUI1 = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI1.run(string, guiUserInputInfo);

                String ID = guiUserInputInfo.getTempUserInput();
                UserInputGUI userInputGUI2 = new UserInputGUI(ID, guiUserInputInfo);
                userInputGUI2.run(ID, guiUserInputInfo);

                try{
                    int id = Integer.parseInt(guiDemo.getUserInput());
                    regularUserSearchingMenuController.getItemById(id);
                } catch (NumberFormatException ex){
                    guiDemo.printNotification("Please enter number!");
                }


                // TODO: Need method to close this window
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
                regularUserSearchingMenuController.sortItemByFollows();

                // TODO: Need method to close this window
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
                guiDemo.runRegularUserSearchingMenuGUI();
                // TODO: Need method to close this window
            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController, GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo) {
        JFrame frame = new JFrame("RegularUserSearchingItemsSubMenu");
        frame.setContentPane(new RegularUserSearchingItemsSubMenu(regularUserSearchingMenuController, guiDemo, guiUserInputInfo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
