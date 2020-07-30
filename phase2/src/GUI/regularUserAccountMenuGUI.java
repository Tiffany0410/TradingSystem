package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class regularUserAccountMenuGUI {
    private JPanel rootPanel;
    private JButton feedBackButton;
    private JButton manageItemButton;
    private JButton accountSettingButton;
    private JButton backButton;

    public regularUserAccountMenuGUI() {
        feedBackButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call regular user feed back menu and close this window
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

    public void run() {
        JFrame frame = new JFrame("regularUserAccountMenuGUI");
        frame.setContentPane(new regularUserAccountMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
