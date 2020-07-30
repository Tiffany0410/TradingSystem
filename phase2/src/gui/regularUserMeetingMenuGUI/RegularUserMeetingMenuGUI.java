package gui.regularUserMeetingMenuGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserMeetingMenuGUI {
    private JPanel rootPanel;

    public void run() {
        JFrame frame = new JFrame("regularUserMeetingMenuGUI");
        frame.setContentPane(new RegularUserMeetingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton checkMeetingButton;
    private JButton meetingHistoryButton;
    private JButton suggestMeetingButton;
    private JButton backButton;

    public RegularUserMeetingMenuGUI() {
        suggestMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Suggest Meeting window and close this window
                //TODO: Create a GUI called suggest meeting window
            }
        });
        checkMeetingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Check Meeting window and close this window

            }
        });
        meetingHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call regular user main menu and close this window

            }
        });
    }
}
