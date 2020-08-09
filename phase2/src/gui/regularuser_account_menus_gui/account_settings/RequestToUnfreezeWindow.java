package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestToUnfreezeWindow {
    private JTextArea textArea;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel rootPanel;


    public RequestToUnfreezeWindow(GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController atc){
        textArea.setEditable(true);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));

        okButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.printNotification(sm.msgForRequestProcess(atc.RequestToUnfreeze(textArea.getText())));
                guiD.runSave();
                guiD.closeWindow(rootPanel);
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
                guiD.closeWindow(rootPanel);
            }
        });
    }

    public void run(GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController atc){
        JFrame frame = new JFrame("regularUserRequestToUnfreezeWindowGUI");
        frame.setContentPane(new RequestToUnfreezeWindow(guiD, sm, atc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
