package gui.regularuser_account_menus_gui;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserAccountMainMenuGUI {
    private JPanel rootPanel;
    private JButton manageItemButton;
    private JButton accountSettingButton;
    private JButton backButton;
    private JButton followOthersItemsButton;
    private JButton feedbackButton;

    public RegularUserAccountMainMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc) {

        manageItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call manage Item menu and close this window
                // guest allowed
                if (amc.seeIfFrozen()){
                    guiD.printNotification(sm.lockMessageForFrozen());
                }
                else {
                    guiD.runRegularUserAccountManageItemsMenu();
                    guiD.closeWindow(rootPanel);
                }
            }
        });
        accountSettingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call account setting menu and close this window
                // guest not allowed
                if (!isGuest){
                    guiD.runRegularUserAccountSettingsMenu();
                    guiD.closeWindow(rootPanel);
                }
                else{
                    guiD.printNotification(sm.msgForGuest());
                }

            }
        });

        followOthersItemsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call account setting menu and close this window
                // guest not allowed
                if (!isGuest){
                    guiD.runRegularUserAccountFollowMenu();
                    guiD.closeWindow(rootPanel);
                }
                else{
                    guiD.printNotification(sm.msgForGuest());
                }
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
                // Go back to regular user main menu and close this window
                guiD.runRegularUserMainMenu(isGuest);
                guiD.closeWindow(rootPanel);
            }
        });
    }


    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc) {
        JFrame frame = new JFrame("regularUserAccountMenuGUI");
        frame.setContentPane(new RegularUserAccountMainMenuGUI(isGuest, sm, guiD,amc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}

