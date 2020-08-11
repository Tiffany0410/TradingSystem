package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
/**
 * Used to edit user historical actions
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserManageUsersConfirmInventoryWindow {
    private JTextPane textPane1;
    private JTextField userInput;
    private JRadioButton notApprovedRadioButton;
    private JRadioButton approvedRadioButton;
    private JButton cancelButton;
    private JButton confirmButton;
    private JPanel rootPanel;
    private JScrollPane scrollPane;

    /**
     * Constructor of admin user manage users confirm inventory window
     * @param string info of related operation
     * @param itemsToAdd list of items want to add
     * @param muc admin user manager users controller
     * @param guiDemo GUIDemo
     * @param sm system message
     * @param idc regular user id checker
     * @param oic admin user other info checker
     */
    public AdminUserManageUsersConfirmInventoryWindow(String string, ArrayList<Item> itemsToAdd, AdminUserManagerUsersController muc,
                                                      GUIDemo guiDemo, SystemMessage sm, RegularUserIDChecker idc,
                                                      AdminUserOtherInfoChecker oic){
        ButtonGroup group = new ButtonGroup();
        group.add(approvedRadioButton);
        group.add(notApprovedRadioButton);

        textPane1.setText(string);
        textPane1.setEditable(false);
        textPane1.setBackground(new Color(242,242,242));
        textPane1.setVisible(true);
        scrollPane.setVisible(true);

        confirmButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click confirm button and do related operation
             * @param e click confirm button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmInventoryExecute(idc, oic, itemsToAdd, muc, guiDemo, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click cancel button and return to menu
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    /**
     * This method execute operation
     * @param itemsToAdd list of items want to add
     * @param muc admin user manager users controller
     * @param guiDemo GUIDemo
     * @param sm system message
     * @param idc regular user id checker
     * @param oic admin user other info checker
     */

    private void confirmInventoryExecute(RegularUserIDChecker idc, AdminUserOtherInfoChecker oic, ArrayList<Item> itemsToAdd, AdminUserManagerUsersController muc, GUIDemo guiDemo, SystemMessage sm) {
        String input = userInput.getText();
        if (idc.checkInt(input)){
            int itemToAddNum = Integer.parseInt(input);
            if (oic.checkItemToAddNum(itemsToAdd.size(), itemToAddNum)){
                if (approvedRadioButton.isSelected()){
                    muc.addItemOrNot(itemToAddNum, true);
                    guiDemo.printNotification(sm.msgForResult(true));
                    guiDemo.closeWindow(rootPanel);
                }
                else if (notApprovedRadioButton.isSelected()){
                    muc.addItemOrNot(itemToAddNum, false);
                    guiDemo.printNotification(sm.msgForResult(true));
                    guiDemo.closeWindow(rootPanel);
                }
                else{
                    guiDemo.printNotification("Please select approve or not.");
                }
            }
            else{
                guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else{
            guiDemo.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
    }

    /**
     * Run admin user manage users confirm inventory window
     * @param string info of related operation
     * @param itemsToAdd list of items want to add
     * @param muc admin user manager users controller
     * @param guiDemo GUIDemo
     * @param sm system message
     * @param idc regular user id checker
     * @param oic admin user other info checker
     */
    public void run(String string, ArrayList<Item> itemsToAdd, AdminUserManagerUsersController muc,
                    GUIDemo guiDemo, SystemMessage sm, RegularUserIDChecker idc,
                    AdminUserOtherInfoChecker oic){
        JFrame frame = new JFrame("Confirm item to be added to inventory");
        frame.setContentPane(new AdminUserManageUsersConfirmInventoryWindow(string, itemsToAdd, muc, guiDemo, sm, idc, oic).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
