package gui.adminuser_menus_gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserEditThresholdsSubMenu {
    private JPanel rootPanel;
    private JButton editTheMaxNumberButton;
    private JButton editTheMaxNumberButton1;
    private JButton editTheNumberOfButton;
    private JButton editTheMaxEditsButton;
    private JButton backButton;

    public AdminUserEditThresholdsSubMenu() {
        editTheMaxNumberButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Edit The Max Number Button controller and related window and close this menu or not
            }
        });
        editTheMaxNumberButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Edit Max Number of Transaction sth controller and  related window and close this menu or not

            }
        });
        editTheNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Edit Number Of Items User Must Lends sth controller and related window and close this menu or not

            }
        });
        editTheMaxEditsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Edit Man Edit's for sth controller and related window and close this menu or not

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
                //TODO: Call Admin Main Menu and close this window

            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("AdminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsSubMenu().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
