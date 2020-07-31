package gui.trading_system_init_menu_gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.LoginValidator;
import gui.GUIController;
import gui.NotificationGUI;

public class LoginGUI {
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton cancelButton;

    public void run(LoginValidator loginValidator, GUIController guiController) {
        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new LoginGUI(loginValidator, guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public LoginGUI(LoginValidator loginValidator, GUIController guiController) {
        loginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e action user did
             */
            @Override
            public void actionPerformed(ActionEvent e) {
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
                            guiController.setTempUsername(usernameText.getText());
                            guiController.runAdminUserMainMenu();
                            break;
                        case "User":
                            guiController.setTempUsername(usernameText.getText());
                            guiController.runRegularUserMainMenu(false);
                            break;
                    }

                } else{
                    String string = "Username does not exist, please check again";
                    NotificationGUI notificationGUI = new NotificationGUI(string);
                    notificationGUI.run(string);
                }

                // TODO: Need method to close this window
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
                guiController.runTradingSystemInitMenuGUI();
                // TODO: Need method to close this window


            }
        });
    }
}
