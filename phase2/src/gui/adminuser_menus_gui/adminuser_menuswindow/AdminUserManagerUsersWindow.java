package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to manage users
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserManagerUsersWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton cancelButton;
    private JButton confirmButton;
    private javax.swing.JLabel JLabel;
    private JTextPane textPane1;
    private JScrollPane scrollPane;

    /**
     * Run AdminManagerUserWindow
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param inputName name of things want user input
     * @param info related description
     * @param muc admin user manager users controller
     */
    public void run(int option, GUIDemo guiDemo, String inputName,String info, AdminUserManagerUsersController muc) {
        JFrame frame = new JFrame("AdminUserManagerUsersWindow");
        frame.setContentPane(new AdminUserManagerUsersWindow(option, guiDemo, inputName, info, muc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Run AdminManagerUserWindow
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param putinName name of things want user input
     * @param info related description
     * @param muc admin user manager users controller
     */
    public AdminUserManagerUsersWindow(int option, GUIDemo guiDemo, String putinName,String info, AdminUserManagerUsersController muc) {
        textPane1.setText(info);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));

        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        JLabel.setText(putinName);



        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click cancel button and return to menu
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click confirm button and do related actions
             *
             * @param e click confirm button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                manageUserExecute(option, muc, guiDemo);
                guiDemo.runSave();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    /**
     * This method execute manager user operation
     * @param option operation number
     * @param guiDemo GUIDemo
     * @param muc admin user manager users controller
     */
    private void manageUserExecute(int option, AdminUserManagerUsersController muc, GUIDemo guiDemo) {
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
    }
}
