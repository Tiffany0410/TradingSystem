package gui.regularuser_searching_menu_gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserSearchingMenuGUI {
    private JPanel rootPanel;
    private JButton searchingItemsButton;


    public RegularUserSearchingMenuGUI() {
        searchingItemsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchingUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        searchingMeetingsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("regularUserSearchingMenuGUI");
        frame.setContentPane(new RegularUserSearchingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton searchingUsersButton;
    private JButton searchingMeetingsButton;
    private JButton backButton;
    private JButton searchingTradesButton;
}
