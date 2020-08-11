package gui.trading_system_init_menu_gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.LoginValidator;
import demomanager.GUIDemo;
import gui.NotificationGUI;

/**
 * Used to show user login window
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class LoginGUI {
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton cancelButton;
    private JButton loginButton1;

    /**
     * This method run the window of login
     * @param loginValidator login validator
     * @param guiDemo GUIDemo
     */
    public void run(LoginValidator loginValidator, GUIDemo guiDemo) {
        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new LoginGUI(loginValidator, guiDemo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

    }

    /**
     * This is constructor of LoginGUI
     * @param loginValidator login validator
     * @param guiDemo GUIDemo
     */
    public LoginGUI(LoginValidator loginValidator, GUIDemo guiDemo) {
        loginButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e action user did
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                loginExecute(loginValidator, guiDemo);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e action user did
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runTradingSystemInitMenuGUI();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    /**
     * This method do related operation
     * @param loginValidator login validator
     * @param guiDemo GUIDemo
     */
    private void loginExecute(LoginValidator loginValidator, GUIDemo guiDemo) {
        if (loginValidator.checkUsername(usernameText.getText())){
            String type;

            type = loginValidator.checkPassword(new String(passwordText.getPassword()));


            switch (type) {
                case "False":
                    String string = "Wrong password, please check again";
                    NotificationGUI notificationGUI = new NotificationGUI(string);
                    notificationGUI.run(string);
                    break;
                case "Admin":
                    guiDemo.setTempUsername(usernameText.getText());
                    guiDemo.runAdminUserMainMenu();
                    guiDemo.closeWindow(rootPanel);
                    break;
                case "User":
                    guiDemo.setTempUsername(usernameText.getText());
                    guiDemo.runRegularUserMainMenu(false);
                    guiDemo.closeWindow(rootPanel);
                    break;
            }

        } else{
            String string = "Username does not exist, please check again";
            NotificationGUI notificationGUI = new NotificationGUI(string);
            notificationGUI.run(string);
        }
    }
}
