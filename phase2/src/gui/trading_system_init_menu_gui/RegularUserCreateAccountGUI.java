package gui.trading_system_init_menu_gui;

import controllers.AccountCreator;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to create account
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JTextField emailTextField;
    private JButton cancelButton;
    private JButton createButton1;
    private JComboBox comboBox1;

    public RegularUserCreateAccountGUI(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage, RegularUserOtherInfoChecker infoChecker) {
        createButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccountExecute(infoChecker, accountCreator, guiDemo, systemMessage);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runTradingSystemInitMenuGUI();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void createAccountExecute(RegularUserOtherInfoChecker infoChecker, AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage) {
        String username = usernameTextField.getText();
        String password = new String(passwordField1.getPassword());
        String email = emailTextField.getText();
        String city = (String) comboBox1.getSelectedItem();
        if (!username.equals("") && !password.equals("") && !email.equals("") && !city.equals("Please Select")) {
            if (infoChecker.checkEmail(email)) {
                boolean result = accountCreator.createAccount("Regular", username, password, email, city);
                if (result) {
                    guiDemo.printNotification("Create Account " + systemMessage.msgForResult(true));
                    guiDemo.closeWindow(rootPanel);
                    guiDemo.runTradingSystemInitMenuGUI();
                    guiDemo.runSave();
                } else {
                    guiDemo.printNotification("Username is taken, please try again :)");
                }
            }
            else{
                guiDemo.printNotification("Please enter a valid email address.");
            }
        }
        else{
            guiDemo.printNotification("Please enter a valid information in order to create an account.");
        }
    }

    public void run(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage, RegularUserOtherInfoChecker infoChecker) {
        JFrame frame = new JFrame("regularUserCreateAccount");
        frame.setContentPane(new RegularUserCreateAccountGUI(accountCreator, guiDemo, systemMessage, infoChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
