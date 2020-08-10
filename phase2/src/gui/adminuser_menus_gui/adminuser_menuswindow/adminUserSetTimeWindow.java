package gui.adminuser_menus_gui.adminuser_menuswindow;

import controllers.adminusersubcontrollers.AdminUserOtherActionsController;
import demomanager.GUIDemo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class adminUserSetTimeWindow {
    private JPanel rootPanel;
    private javax.swing.JLabel JLabel;
    private JComboBox yearBox;
    private JLabel year;
    private JLabel Month;
    private JComboBox monthBox;
    private JComboBox dayBox;
    private JButton cancelButton;
    private JButton confirmButton;

    public adminUserSetTimeWindow(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
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
                String result = adminUserOtherActionsController.checkSystemTime((String) yearBox.getSelectedItem(),
                        (String) monthBox.getSelectedItem(), (String) dayBox.getSelectedItem());

                if (result.equals("Unselected")){guiDemo.printNotification("Please select year, month and day");}
                else if(result.equals("false")){guiDemo.printNotification("Please select a valid date");}
                else{

                    guiDemo.printNotification("System time set succeed");}

            }
        });
    }

    public void run(GUIDemo guiDemo, AdminUserOtherActionsController adminUserOtherActionsController) {
        JFrame frame = new JFrame("adminUserSetTimeWindow");
        frame.setContentPane(new adminUserSetTimeWindow(guiDemo, adminUserOtherActionsController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
