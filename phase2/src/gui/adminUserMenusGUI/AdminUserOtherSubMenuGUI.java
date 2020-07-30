package gui.adminUserMenusGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserOtherSubMenuGUI {
    private JPanel rootPanel;
    private JButton addNewAdminUserButton;
    private JButton backButton;

    public AdminUserOtherSubMenuGUI() {
        addNewAdminUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: Call Admin User Create Account and close this window

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
                //TODO: Back to Admin User Main Menu and close this window
            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("adminUserOtherSubMenuGUI");
        frame.setContentPane(new AdminUserOtherSubMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
