package gui.regularuser_account_menus_gui;

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

    public RegularUserAccountMainMenuGUI(boolean isGuest, SystemMessage sm) {
        feedBackButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call regular user feed back menu and close this window
                // guest not allowed
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
                //TODO: Call manage Item menu and close this window
                // guest allowed

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
                //TODO; Call account setting menu and close this window
                // guest not allowed

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
                //TODO: Go back to regular user main menu and close this window
            }
        });
    }

    public void run(Boolean isGuest, SystemMessage sm) {
        JFrame frame = new JFrame("regularUserAccountMenuGUI");
        frame.setContentPane(new RegularUserAccountMainMenuGUI(isGuest, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
