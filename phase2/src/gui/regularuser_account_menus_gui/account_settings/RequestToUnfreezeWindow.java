package gui.regularuser_account_menus_gui.account_settings;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that is responsible for the view and getting input for user
 * when user wants to request unfreeze.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RequestToUnfreezeWindow {
    private JTextArea textArea;
    private JButton cancelButton;
    private JButton okButton;
    private JPanel rootPanel;


    /**
     * Constructs a RequestToUnfreezeWindow.
     * @param guiD The GUI helper.
     * @param sm The presenter.
     * @param atc The RegularUserAccountMenuController.
     */
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
                requestUnfreezeOk(guiD, sm, atc);
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

    private void requestUnfreezeOk(GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController atc) {
        guiD.printNotification(sm.msgForRequestProcess(atc.requestToUnfreeze(textArea.getText())));
        guiD.runSave();
        guiD.closeWindow(rootPanel);
    }

    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param sm The presenter.
     * @param atc The RegularUserAccountMenuController.
     */
    public void run(GUIDemo guiD, SystemMessage sm, RegularUserAccountMenuController atc){
        JFrame frame = new JFrame("regularUserRequestToUnfreezeWindowGUI");
        frame.setContentPane(new RequestToUnfreezeWindow(guiD, sm, atc).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
