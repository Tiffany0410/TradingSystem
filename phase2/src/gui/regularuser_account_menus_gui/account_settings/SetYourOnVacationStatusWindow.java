package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import demomanager.GUIDemo;
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
                if ((response.equals("on-vacation") && atc.seeIfOnVacation()) || (response.equals("not on-vacation") && !atc.seeIfOnVacation())){
                    guiD.printNotification(sm.msgForStatusChangeResult(false));
                }
                else if(response.equals("on-vacation") && !atc.seeIfOnVacation()){
                    atc.setOnVacationStatus(true);
                    guiD.printNotification(sm.msgForStatusChangeResult(true));
                }
                else{
                    atc.setOnVacationStatus(false);
                    guiD.printNotification(sm.msgForStatusChangeResult(true));
                }
                guiD.runSave();
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
