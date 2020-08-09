package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RespondToTradeRequestsWindow {
    private JTextArea textArea1;
    private JTextField tradeId;
    private JRadioButton agreeRadioButton;
    private JRadioButton disagreeRadioButton;
    private JButton cancelButton;
    private JButton OKButton;
    private JPanel contentPane;

    public RespondToTradeRequestsWindow(String str, RegularUserIDChecker idC, RegularUserTradingMenuController atc,
                                        GUIDemo guiD, SystemMessage sm) {
        textArea1.setText(str);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setBackground(new Color(242,242,242));

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(contentPane);
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
                ButtonGroup buttonGroup = new ButtonGroup();
                buttonGroup.add(agreeRadioButton);
                buttonGroup.add(disagreeRadioButton);
                String tradeIdS = tradeId.getText();
                if (idC.checkInt(tradeIdS)){
                    int tradeId = Integer.parseInt(tradeIdS);
                    if (idC.checkTradeID(tradeId)){
                        //TODO: don't know if buttonGroup.getSelection().toString() works
                        atc.respondToTradeRequests(tradeId, buttonGroup.getSelection().toString());
                        guiD.printNotification(sm.msgForRequestProcess(true));
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.runSave();
                guiD.closeWindow(contentPane);

            }
        });
    }
    public void run(String str, RegularUserIDChecker idC, RegularUserTradingMenuController atc,
                    GUIDemo guiD, SystemMessage sm){
        JFrame frame = new JFrame("RespondToATradeRequestsWindow");
        frame.setContentPane(new RespondToTradeRequestsWindow(str, idC, atc, guiD, sm).contentPane);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
