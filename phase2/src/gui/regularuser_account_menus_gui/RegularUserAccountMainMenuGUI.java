package gui.regularuser_account_menus_gui;

import gui.GUIDemo;
import gui.NotificationGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserAccountMainMenuGUI {
    private JPanel rootPanel;
    private JButton feedBackButton;
    private JButton manageItemButton;
    private JButton accountSettingButton;
    private JButton backButton;
    private JButton followOthersItemsButton;

    public RegularUserAccountMainMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiD) {
        feedBackButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call regular user feed back menu and close this window
                // guest not allowed
                if (!isGuest){
                    guiD.runRegularUserAccountFeedBackMenu();
                }
                else{
                    printNote(sm.msgForGuest());
                }
            }
        });
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
                    guiD.runRegularUserAccountManageItemsMenu();

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
                }
                else{
                    printNote(sm.msgForGuest());
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
            }
        });
    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }

    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiD) {
        JFrame frame = new JFrame("regularUserAccountMenuGUI");
        frame.setContentPane(new RegularUserAccountMainMenuGUI(isGuest, sm, guiD).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
