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
 * Used to show regular user onw-way-trade request
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class OneWayTradeWindow {
    private JTextField thisUser;
    private JTextField otherUser;
    private JTextField item;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel rootPanel;

    /**
     * Constructor of regular user request for onw-way-trade window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     * @param tradeType Trade type ("Permanent" or "Temporary")
     */
    public OneWayTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                             SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        requestButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, request for onw-way-trade
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                requestOneWayTrade(idC, guiD, atc, numLentBeforeBorrow, tradeType, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /** Invoke when click button and close this window
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
    }

    private void requestOneWayTrade(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc, int numLentBeforeBorrow, String tradeType, SystemMessage sm) {
        String borrower = thisUser.getText();
        String lender = otherUser.getText();
        String item_input = item.getText();

        if (idC.checkInt(borrower) && idC.checkInt(lender) && idC.checkInt(item_input)){
            int userId1 = Integer.parseInt(borrower);
            int userId2 = Integer.parseInt(lender);
            int itemid1 = Integer.parseInt(item_input);
            if (idC.checkUserID(userId1) && idC.checkUserID(userId2) && idC.checkItemID(itemid1)) {
                guiD.printNotification(atc.requestTrade(1, userId1, userId2, itemid1,-1, numLentBeforeBorrow, tradeType));
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
        guiD.closeWindow(rootPanel);
    }

    /**
     * Run one-way-trade request window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     * @param tradeType Trade type ("Permanent" or "Temporary")
     */
    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        JFrame frame = new JFrame("One-way-trade request");
        frame.setContentPane(new OneWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, tradeType).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
