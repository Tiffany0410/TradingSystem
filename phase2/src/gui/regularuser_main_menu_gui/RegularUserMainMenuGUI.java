package gui.regularuser_main_menu_gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserMainMenuGUI {
    private JPanel rootPanel;
    private JButton accountInformationButton;
    private JButton tradingInformationButton;
    private JButton notificationButton;
    private JButton meetingInformationButton;
    private JButton searchingInformationButton;
    private JButton logoutButton;
    private JButton communityInformationButton;

    public void run(boolean Guest) {
        JFrame frame = new JFrame("regularUserMainMenuGUI");
        frame.setContentPane(new RegularUserMainMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        //TODO: Call Notification which should be a JDialog
    }

    public RegularUserMainMenuGUI(boolean Guest) {
        notificationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Notification which should be a JDialog

            }
        });
        accountInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Account Info Menu and close this window

            }
        });
        tradingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Trading Info Menu and close this window
            }
        });
        meetingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Meeting Info Menu and close this window

            }
        });
        searchingInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Searching Info Menu and close this window

            }
        });
        communityInformationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Call Community Info Menu and colse this window
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Trading System Init Menu and close this window

            }
        });
    }
}
