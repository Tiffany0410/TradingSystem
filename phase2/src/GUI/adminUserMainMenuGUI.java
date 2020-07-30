package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminUserMainMenuGUI {
    private JPanel rootPanel;
    private JLabel topLabel;
    private JButton manageUserButton;
    private JButton editThresholdsButton;
    private JButton manageHistroicalActionsButton;
    private JButton othersButton;
    private JButton logoutButton;

    public adminUserMainMenuGUI() {
        manageUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Call Admin Manage User Sub Menu and close this menu

            }
        });
        editThresholdsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Admin Edit Thresholds Sub Menu and close this menu

            }
        });
        manageHistroicalActionsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Admin Manage Histroical Action Sub Menu and close this window

            }
        });
        othersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Admin Other Sub Menu and close this window

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
                //TODO: Return to Trading System Init Menu and close this Menu

            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("adminUserMainMenuGUI");
        frame.setContentPane(new adminUserMainMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
