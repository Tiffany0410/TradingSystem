package gui.tradingSystemInitMenuGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.LoginValidator;

public class LoginGUI {
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton cancelButton;

    public void run(LoginValidator loginValidator) {
        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new LoginGUI(loginValidator).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public LoginGUI(LoginValidator loginValidator) {
        loginButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e action user did
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Login");
                if (loginValidator.checkUsername(usernameText.getName())){
                    String type;

                    type = loginValidator.checkPassword(new String(passwordText.getPassword()));

                    switch (type) {
                        case "False":
                            NotificationGUI notificationGUI = new NotificationGUI("Wrong password, please check again");

                            break;
                        case "Admin":
                            // TODO: Call admin user main menu and close this window
                            break;
                        case "User":
                            // TODO: Call regular user main menu and close this window
                            break;
                    }

                } else{
                    NotificationGUI notificationGUI = new NotificationGUI("Uername does not exist, please check again");
                }

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
                // TODO: Return to trading system init menu and close this window
            }
        });
    }
}
