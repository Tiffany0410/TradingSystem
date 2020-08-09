package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConfirmTradeCompleteWindow {
    private JTextField tradeId;
    private JButton checkButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    public ConfirmTradeCompleteWindow(RegularUserIDChecker idC, RegularUserTradingMenuController atc,
                                      GUIDemo guiD, SystemMessage sm) {
        checkButton.addActionListener(new ActionListener() {
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
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean result;
                String input = tradeId.getText();
                if (idC.checkInt(input)) {
                    int tradeId1 = Integer.parseInt(input);
                    if (idC.checkTradeID(tradeId1)) {
                        result = atc.confirmTradeComplete(tradeId1);
                        guiD.printNotification(sm.msgFortradeCompletedOrNot(result));
                    } else {
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                } else {
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.runSave();
                guiD.closeWindow(rootPanel);
            }
        });
    }

    public void run(RegularUserIDChecker idC, RegularUserTradingMenuController atc,
                    GUIDemo guiD, SystemMessage sm) {
        JFrame frame = new JFrame("ConfirmTradeCompleteWindow");
        frame.setContentPane(new ConfirmTradeCompleteWindow(idC, atc, guiD, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
