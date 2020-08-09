package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserEditThresholdsController;
import gui.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserEditUserThresholdsWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton Cancel;
    private JButton confirmButton;
    private JLabel Jlabel;
    private JLabel infoLabel;

    public AdminUserEditUserThresholdsWindow(String string, int option, GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        Jlabel.setText("Please enter new value");
        infoLabel.setText(string);


        Cancel.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option == 1){
                    try {
                        int futureValue = Integer.parseInt(textField.getInputContext().toString());
                        guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberTransactions(futureValue));
                    }catch (NumberFormatException ex){
                        guiDemo.printInvalidNumber();
                    }
                }

                if (option == 2){
                    try {
                        int futureValue = Integer.parseInt(textField.getInputContext().toString());
                        guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberIncompleteTransactions(futureValue));
                    }catch (NumberFormatException ex){
                        guiDemo.printInvalidNumber();
                    }
                }

                if (option == 3){
                    try {
                        int futureValue = Integer.parseInt(textField.getInputContext().toString());
                        guiDemo.printNotification(adminUserEditThresholdsController.editMustLendNumber(futureValue));
                    }catch (NumberFormatException ex){
                        guiDemo.printInvalidNumber();
                    }
                }

                if (option == 4){
                    try {
                        int futureValue = Integer.parseInt(textField.getInputContext().toString());
                        guiDemo.printNotification(adminUserEditThresholdsController.editMaxEdits(futureValue));
                    }catch (NumberFormatException ex){
                        guiDemo.printInvalidNumber();
                    }
                }

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
                guiDemo.runAdminUserEditThresholdsSubMenu();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    public void run(String string, int option, GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        JFrame frame = new JFrame("AdminUserEditUserThresholdsWindow");
        frame.setContentPane(new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }


}
