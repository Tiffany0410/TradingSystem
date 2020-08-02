package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import gui.GUIDemo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegularUserTradingMenuGUI {
    private JPanel rootPanel;
    private JButton requestATradeButton;
    private JButton respondToTradeRequestsButton;
    private JButton viewOpenTradesButton;
    private JButton viewClosedTradesButton;
    private JButton confirmThatATradeButton;
    private JButton seeTopThreeMostButton;
    private JButton viewTransactionsThatHaveButton;
    private JButton suggestionForTheMostButton;
    private JButton backButton;

    public RegularUserTradingMenuGUI(GUIDemo guiD, RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek,
                                     int numLentBeforeBorrow){

        requestATradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (atc.lockThresholdOrNot()) {
                    String lockMsg = sm.lockMessageForThreshold(maxNumTransactionAWeek);
                    NotificationGUI msgGUI = new NotificationGUI(lockMsg);
                    msgGUI.run(lockMsg);
                }
                else{
                    //TODO: ask for input for all of the things below


                    //TODO: int numKindOfTrade = otherInfoGetter.getNumKindOfResponse("one way trade", "two way trade");
                    //TODO: new SystemMessage method for "Please enter your trade type - (1 - one-way-trade/2 - two-way-trade)"
                    str = sm.enterTradeTypeMsg();
                    UserInputGUI getTradeTypeGUI = new UserInputGUI(str, guiD);
                    getTradeTypeGUI.run(str, guiD);

                    //TODO: new SystemMessage method for "Please enter your trade type - (1 - one-way-trade/2 - two-way-trade)"


                    //TODO: userId1 = idGetter.getUserID("borrower (if one-way-trade) or borrower for the first item and lender for the second item (if two-way-trade)");
                    //TODO: userId2 = idGetter.getUserID("lender (if one-way-trade) or lender for the first item and borrower for the second item (if two-way-trade)");
                    //TODO: int itemId = idGetter.getItemID(im.getAllItem(), 1);
                    //TODO: int itemId2 = idGetter.getItemID(im.getAllItem(), 1);
                    // get the trade type (permanent or temporary)
                    // TODO: String tradeType = otherInfoGetter.getTradeType();

                    //TODO: call requestTrade with the inputs


                    //TODO: print the message of success or not
                }
            }
        });


        respondToTradeRequestsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        viewOpenTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        viewClosedTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        confirmThatATradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        seeTopThreeMostButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        viewTransactionsThatHaveButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        suggestionForTheMostButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void run() {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
