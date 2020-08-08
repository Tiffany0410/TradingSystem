package gui.trading_system_init_menu_gui;

import controllers.AccountCreator;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserCreateAccountGUI {
    private JPanel rootPanel;
    private JTextField usernameTextField;
    private JPasswordField passwordField1;
    private JTextField emailTextField;
    private JButton cancelButton;
    private JButton createButton1;
    private JComboBox comboBox1;

    public RegularUserCreateAccountGUI(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage) {
        createButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result = accountCreator.createAccount("Regular", usernameTextField.getText(),
                        new String(passwordField1.getPassword()), emailTextField.getText(), (String)comboBox1.getSelectedItem());

                guiDemo.printNotification( "Create Account " + systemMessage.printResult(result));
                guiDemo.runSave();
                guiDemo.runTradingSystemInitMenuGUI();
                guiDemo.closeWindow(rootPanel);
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

    public void run(AccountCreator accountCreator, GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("regularUserCreateAccount");
        frame.setContentPane(new RegularUserCreateAccountGUI(accountCreator, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
