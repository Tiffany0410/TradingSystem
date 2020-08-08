package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SetYourOnVacationStatusWindow {
    private JComboBox comboBox1;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel rootPanel;

    public SetYourOnVacationStatusWindow(RegularUserAccountMenuController atc, SystemMessage sm, GUIDemo guiD) {

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String response = (String)comboBox1.getSelectedItem();
                if (response.equals("on-vacation")) {
                    atc.setOnVacationStatus(true);
                }
                else{
                    atc.setOnVacationStatus(false);
                }
                guiD.printNotification(sm.msgForResult(true));
                guiD.closeWindow(rootPanel);
            }

        });
    }

    public void run(RegularUserAccountMenuController atc, SystemMessage sm, GUIDemo guiD){
        JFrame frame = new JFrame("SetYourOnVacationStatusWindow");
        frame.setContentPane(new SetYourOnVacationStatusWindow(atc, sm, guiD).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
