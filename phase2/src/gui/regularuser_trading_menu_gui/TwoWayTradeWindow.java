package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 /**
 * Used to show regular user two-way-trade request
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class TwoWayTradeWindow {
    private JTextField userid1;
    private JTextField userid2;
    private JTextField item1;
    private JTextField item2;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel panel;

    /**
     * Constructor of regular user request for tnw-way-trade window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     * @param tradeType Trade type ("Permanent" or "Temporary")
     */
    public TwoWayTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                             SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        requestButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, request for two-way-trade
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                requestTwoWayTrade(idC, guiD, atc, numLentBeforeBorrow, tradeType, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /** Invoke when click button and close this window
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(panel);
            }
        });
    }

    private void requestTwoWayTrade(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc, int numLentBeforeBorrow, String tradeType, SystemMessage sm) {
        String user1 = userid1.getText();
        String user2 = userid2.getText();
        String item1_input = item1.getText();
        String item2_input = item2.getText();

        if (idC.checkInt(user1) && idC.checkInt(user2) && idC.checkInt(item1_input) && idC.checkInt(item2_input)){
            int userId1 = Integer.parseInt(user1);
            int userId2 = Integer.parseInt(user2);
            int itemId1 = Integer.parseInt(item1_input);
            int itemId2 = Integer.parseInt(item2_input);
            if (idC.checkUserID(userId1) && idC.checkUserID(userId2) && idC.checkItemID(itemId1) && idC.checkItemID(itemId2)) {
                guiD.printNotification(atc.requestTrade(2, userId1, userId2, itemId1, itemId2, numLentBeforeBorrow, tradeType));
                guiD.runSave();
            }
            else{
                guiD.printNotification(sm.tryAgainMsgForWrongInput());
            }
        }
        else{
            guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
        }
        guiD.runSave();
        guiD.closeWindow(panel);
    }

    /**
     * Run two-way-trade request window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     * @param tradeType Trade type ("Permanent" or "Temporary")
     */
    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        JFrame frame = new JFrame("Two-way-trade request");
        frame.setContentPane(new TwoWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, tradeType).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
