package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserManagerUsersWindow {
    private JPanel rootPanel;
    private javax.swing.JLabel JLabel;
    private JTextField textField;
    private JButton cancleButton;
    private JButton confirmButton;
    private JTextArea textArea;

    public void run(int option, GUIDemo guiDemo, String inputName, String info, AdminUserManagerUsersController muc) {
        JFrame frame = new JFrame("AdminUserManagerUsersWindow");
        frame.setContentPane(new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



    public AdminUserManagerUsersWindow(int option, GUIDemo guiDemo, String inputName, String info, AdminUserManagerUsersController muc) {
        this.JLabel.setText(inputName);
        this.textArea.setText(info);

        cancleButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserManageUsersSubMenu();
                guiDemo.closeWindow(rootPanel);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String regularUserName = textField.getText();
                if (option == 1){
                    if (regularUserName != null) {String result = muc.freezeUser(regularUserName);
                        guiDemo.printNotification(result);}
                }
                if (option == 2){
                    if (regularUserName != null) {String result = muc.unfreezeUser(regularUserName);
                        guiDemo.printNotification(result);}
                }
                guiDemo.closeWindow(rootPanel);
            }
        });
    }
}
