package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A class that is responsible for the view and getting input for user
 * when user wants to confirm a meeting's time and place.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

public class ConfirmTradeCompleteWindow {
    private JTextField tradeId;
    private JButton checkButton;
    private JButton cancelButton;
    private JPanel rootPanel;

    /**
     * Constructs a ConfirmTradeCompleteWindow.
     * @param idC The id checker.
     * @param atc The RegularUserTradingMenuController.
     * @param guiD The GUI helper.
     * @param sm The system message.
     */
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
                checkTradeComplete(idC, atc, guiD, sm);

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

    private void checkTradeComplete(RegularUserIDChecker idC, RegularUserTradingMenuController atc, GUIDemo guiD, SystemMessage sm) {
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

    /**
     * Responsible for getting this window running.
     * @param idC The id checker.
     * @param atc The RegularUserTradingMenuController.
     * @param guiD The GUI helper.
     * @param sm The system message.
     */
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
