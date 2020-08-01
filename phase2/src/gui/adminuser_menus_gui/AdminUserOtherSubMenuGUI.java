package gui.adminuser_menus_gui;

import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserOtherSubMenuGUI {
    private JPanel rootPanel;
    private JButton addNewAdminUserButton;
    private JButton backButton;

    public AdminUserOtherSubMenuGUI(GUIDemo guiDemo) {
        addNewAdminUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserCreateAccount();
                // TODO: Need method to close this window


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
                guiDemo.runAdminUserMainMenu();
                // TODO: Need method to close this window

            }
        });
    }

    public void run(GUIDemo guiDemo) {
        JFrame frame = new JFrame("adminUserOtherSubMenuGUI");
        frame.setContentPane(new AdminUserOtherSubMenuGUI(guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
