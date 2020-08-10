package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserManagerUsersWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton cancleButton;
    private JButton confirmButton;
    private javax.swing.JLabel JLabel;
    private JTextPane textPane1;
    private JScrollPane scrollPane;

    public void run(int option, GUIDemo guiDemo, String inputName,String info, AdminUserManagerUsersController muc) {
        JFrame frame = new JFrame("AdminUserManagerUsersWindow");
        frame.setContentPane(new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }



    public AdminUserManagerUsersWindow(int option, GUIDemo guiDemo, String putinName,String info, AdminUserManagerUsersController muc) {
        textPane1.setText(info);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        JLabel.setText(putinName);



        cancleButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                //guiDemo.runAdminUserManageUsersSubMenu();

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
                    if (regularUserName != null) {
                        String result = muc.freezeUser(regularUserName);
                        guiDemo.printNotification(result);
                        guiDemo.runSave();}
                }

                if (option == 2){
                    if (regularUserName != null) {
                        String result = muc.unfreezeUser(regularUserName);
                        guiDemo.printNotification(result);
                        guiDemo.runSave();}
                }
                guiDemo.runSave();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }
}
