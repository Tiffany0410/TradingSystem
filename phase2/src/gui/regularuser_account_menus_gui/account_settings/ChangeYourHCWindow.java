package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeYourHCWindow {
    private JComboBox comboBox1;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    public ChangeYourHCWindow(RegularUserAccountMenuController atc, GUIDemo guiDemo, SystemMessage sm){

        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.printNotification(atc.changeUserHC((String)comboBox1.getSelectedItem()));
                guiDemo.printNotification(sm.msgForResult(true));
                guiDemo.runSave();
                guiDemo.closeWindow(rootPanel);
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

    public void run(RegularUserAccountMenuController atc, GUIDemo guiDemo, SystemMessage sm){
        JFrame frame = new JFrame("ChangeYourHomeCityWindow");
        frame.setContentPane(new ChangeYourHCWindow(atc, guiDemo, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
