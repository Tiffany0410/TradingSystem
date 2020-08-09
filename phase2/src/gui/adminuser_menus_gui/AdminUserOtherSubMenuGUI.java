package gui.adminuser_menus_gui;

import demomanager.GUIDemo;

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
                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserCreateAccount();
                guiDemo.runSave();
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

                //close this window
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserMainMenu();
            }
        });
    }

    public void run(GUIDemo guiDemo) {
        JFrame frame = new JFrame("adminUserOtherSubMenuGUI");
        frame.setContentPane(new AdminUserOtherSubMenuGUI(guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
