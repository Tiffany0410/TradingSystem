package GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controllers.LoginValidator;

public class loginGUI implements GUIrunable {
    private JPanel rootPanel;
    private JLabel usernameLabel;
    private JTextField usernameText;
    private JLabel passwordLabel;
    private JPasswordField passwordText;
    private JButton loginButton;
    private JButton cancelButton;

    public void run(LoginValidator loginValidator) {
        JFrame frame = new JFrame("loginGUI");
        frame.setContentPane(new loginGUI(loginValidator).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    public loginGUI(LoginValidator loginValidator) {
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

                    if (type.equals("False")){
                        // TODO: Give a JDialog window said wrong password
                    } else if(type.equals("Admin")){
                        // TODO: Call admin user main menu and close this window
                    }
                    else if (type.equals("User")){
                        // TODO: Call regular user main menu and close this window
                    }

                } else{
                    // TODO: Give a JDialog window said wrong username
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
