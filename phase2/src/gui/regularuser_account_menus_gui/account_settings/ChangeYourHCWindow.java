package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that is responsible for the view and getting input for user
 * when user wants to change his/her home city.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class ChangeYourHCWindow {
    private JComboBox comboBox1;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    /**
     * Constructs a ChangeYourHCWindow.
     * @param atc The RegularUserAccountMenuController.
     * @param guiDemo The GUI helper.
     * @param sm The presenter.
     */
    public ChangeYourHCWindow(RegularUserAccountMenuController atc, GUIDemo guiDemo, SystemMessage sm){

        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                changeUserHCOk(guiDemo, atc, sm);
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void changeUserHCOk (GUIDemo guiDemo, RegularUserAccountMenuController atc, SystemMessage sm) {
        String city = (String) comboBox1.getSelectedItem();
        if (city.equals("Please Select")) {
            guiDemo.printNotification("Please select an option.");
        }
        else {
            guiDemo.printNotification(atc.changeUserHC(city));
            guiDemo.runSave();
            guiDemo.printNotification(sm.msgForResult(true));
            guiDemo.runSave();
            guiDemo.closeWindow(rootPanel);
        }
    }

    /**
     * Responsible for getting this window running.
     * @param atc RegularUserAccountMenuController.
     * @param guiDemo Helper class in GUI.
     * @param sm The presenter.
     */
    public void run(RegularUserAccountMenuController atc, GUIDemo guiDemo, SystemMessage sm){
        JFrame frame = new JFrame("ChangeYourHomeCityWindow");
        frame.setContentPane(new ChangeYourHCWindow(atc, guiDemo, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
