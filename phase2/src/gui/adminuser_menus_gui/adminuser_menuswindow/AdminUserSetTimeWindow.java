package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserOtherActionsController;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Used to set system time
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserSetTimeWindow {
    private JPanel rootPanel;
    private javax.swing.JLabel JLabel;
    private JComboBox yearBox;
    private JLabel year;
    private JLabel Month;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private JButton cancelButton;
    private JButton confirmButton;

    /**
     * Constructor of admin user set time window
     * @param guiDemo GUIDemo
     * @param adminUserOtherActionsController admin user other actions controller
     */
    public AdminUserSetTimeWindow(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when click cancel button and return to menu
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runAdminUserOtherSubMenu();
            }
        });
        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an click confirm button and do related methid
             *
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                setSystemTimeExecute(adminUserOtherActionsController, guiDemo);
            }
        });
    }

    /**
     * This method execute operation
     * @param guiDemo GUIDemo
     * @param adminUserOtherActionsController admin user other actions controller
     */
    private void setSystemTimeExecute(AdminUserOtherActionsController adminUserOtherActionsController, GUIDemo guiDemo) {
        try{
            int year = Integer.parseInt((String) yearBox.getSelectedItem());
            int month = Integer.parseInt((String) monthBox.getSelectedItem());
            int day = Integer.parseInt((String) dayBox.getSelectedItem());

            boolean result = adminUserOtherActionsController.checkSystemTime(year, month, day);

            if(result){
                adminUserOtherActionsController.setSystemTime(year, month, day);
                guiDemo.printNotification("System time set succeed");
                }
            else{
                guiDemo.printNotification("Please select a valid date");
                }

        }catch (NumberFormatException ex){
            guiDemo.printNotification("Please select year, month and day");
        }
        guiDemo.closeWindow(rootPanel);
        guiDemo.runAdminUserOtherSubMenu();
    }

    /**
     * Run admin user set time window
     * @param guiDemo GUIDemo
     * @param adminUserOtherActionsController admin user other actions controller
     */
    public void run(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        JFrame frame = new JFrame("adminUserSetTimeWindow");
        frame.setContentPane(new AdminUserSetTimeWindow(guiDemo, adminUserOtherActionsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
