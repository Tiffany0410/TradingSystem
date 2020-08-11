package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserEditThresholdsController;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to edit user thresholds
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserEditUserThresholdsWindow {
    private JPanel rootPanel;
    private JTextField textField;
    private JButton Cancel;
    private JButton confirmButton;
    private JLabel Jlabel;
    private JLabel infoLabel;

    /**
     * This is the constructor of admin user edit thresholds window
     * @param string information of this operation
     * @param option option number
     * @param guiDemo GUIDemo
     * @param adminUserEditThresholdsController adminUserEditThresholdsController
     */
    public AdminUserEditUserThresholdsWindow(String string, int option, GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        Jlabel.setText("Please enter new value");
        infoLabel.setText(string);

        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click confirm button and call controller to perform actions
             * @param e action
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                editUserExcute(option, guiDemo, adminUserEditThresholdsController);
                guiDemo.runSave();
                guiDemo.closeWindow(rootPanel);

            }
        });
        Cancel.addActionListener(new ActionListener() {
            /**
             * Invoked when click cancel button, back to upper level
             * @param e action
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);

            }
        });
    }

    /**
     * This is a private function help to excute the operation
     * @param option option number
     * @param guiDemo GUIDemo
     * @param adminUserEditThresholdsController adminUserEditThresholdsController
     */
    private void editUserExcute(int option, GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        if (option == 1){
            try {
                int futureValue = Integer.parseInt(textField.getText());
                guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberTransactions(futureValue));
            }catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }

        if (option == 2){
            try {
                int futureValue = Integer.parseInt(textField.getText());
                guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberIncompleteTransactions(futureValue));
            }catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }

        if (option == 3){
            try {
                int futureValue = Integer.parseInt(textField.getText());
                guiDemo.printNotification(adminUserEditThresholdsController.editMustLendNumber(futureValue));
            }catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }

        if (option == 4){
            try {
                int futureValue = Integer.parseInt(textField.getText());
                guiDemo.printNotification(adminUserEditThresholdsController.editMaxEdits(futureValue));
            }catch (NumberFormatException ex){
                guiDemo.printInvalidNumber();
            }
        }
    }

    /**
     * This is method run this window
     * @param string information of this operation
     * @param option option number
     * @param guiDemo GUIDemo
     * @param adminUserEditThresholdsController adminUserEditThresholdsController
     */
    public void run(String string, int option, GUIDemo guiDemo, AdminUserEditThresholdsController adminUserEditThresholdsController) {
        JFrame frame = new JFrame("AdminUserEditUserThresholdsWindow");
        frame.setContentPane(new AdminUserEditUserThresholdsWindow(string, option, guiDemo, adminUserEditThresholdsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300,300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
