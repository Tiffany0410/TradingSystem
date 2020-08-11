package gui.regularuser_account_menus_gui;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that is responsible for the view and getting input for user
 * when user browses the account main menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserAccountMainMenuGUI {
    private JPanel rootPanel;
    private JButton manageItemButton;
    private JButton accountSettingButton;
    private JButton backButton;
    private JButton followOthersItemsButton;

    /**
     * Constructs the RegularUserAccountMainMenuGUI.
     * @param isGuest Whether or not to grant guest access.
     * @param sm The presenter.
     * @param guiD The GUI helper.
     * @param amc The RegularUserAccountMenuController.
     */
    public RegularUserAccountMainMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc) {

        manageItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                manageItems(amc, guiD, sm);
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
                accountSettings(isGuest, guiD, sm);

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
                followOthers(isGuest, guiD, sm);
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

    private void followOthers(boolean isGuest, GUIDemo guiD, SystemMessage sm) {
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

    private void accountSettings(boolean isGuest, GUIDemo guiD, SystemMessage sm) {
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

    private void manageItems(RegularUserAccountMenuController amc, GUIDemo guiD, SystemMessage sm) {
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


    /**
     * Responsible for running this window.
     * @param sm The presenter.
     * @param guiD The GUI helper.
     * @param amc The RegularUserAccountMenuController.
     */
    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiD, RegularUserAccountMenuController amc) {
        JFrame frame = new JFrame("regularUserAccountMenuGUI");
        frame.setContentPane(new RegularUserAccountMainMenuGUI(isGuest, sm, guiD,amc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}

