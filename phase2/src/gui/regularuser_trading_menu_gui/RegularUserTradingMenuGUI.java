package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
                                     int numLentBeforeBorrow,  RegularUserIDChecker idC, boolean guest){


        requestATradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    RequestATrade(atc, sm, maxNumTransactionAWeek, idC, numLentBeforeBorrow, guiD);
                    guiD.runSave();
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    respondToTradeRequest(atc, sm, maxNumTransactionAWeek, idC, guiD);
                    guiD.runSave();
                }
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    viewTrades(sm, atc.viewOpenTrades(), "open", guiD);
                    guiD.runSave();
                }

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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    viewTrades(sm, atc.viewClosedTrades(), "closed", guiD);
                    guiD.runSave();
                }
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    confirmATradeIsCompleted(atc, sm, idC, guiD);
                    guiD.runSave();
                }

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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    seeTopThreePartners(atc, sm, guiD);
                    guiD.runSave();
                }
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else {
                    viewTrades(sm, atc.viewCancelledTrades(), "cancelled", guiD);
                    guiD.runSave();
                }
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
                if (guest){
                    guiD.printNotification(sm.msgForGuest());
                }
                else if (atc.hasTradeSuggestion()){
                    String str = sm.printListObject(new ArrayList<>(atc.mostReasonableTradeSuggestions()));
                    guiD.printNotification("Trade suggestion for you (first number = item id, second number = this item's owner's id): \n" + str);
                }
                else{
                    guiD.printNotification(sm.msgForNo(" recommended trade suggestion."));
                }
                guiD.runSave();

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
                //GO back to main menu
                guiD.runRegularUserMainMenu(guest);
                guiD.closeWindow(rootPanel);
            }
        });
    }

    private void seeTopThreePartners(RegularUserTradingMenuController atc, SystemMessage sm, GUIDemo guiD) {
        if (atc.hasTopThree()){
            //has top three
            String str = sm.printListObject(new ArrayList<>(atc.seeTopThreePartners()));
            guiD.printNotification("Here's your list of top three partners: \n" + str);
        } else {
            guiD.printNotification(sm.msgForNothing("here."));
        }
    }

    private void confirmATradeIsCompleted(RegularUserTradingMenuController atc, SystemMessage sm, RegularUserIDChecker idC,
                                          GUIDemo guiD) {
        List<Trade> openTrades = atc.viewOpenTrades();
        boolean result;
        viewTrades(sm, openTrades, "open", guiD);
        if (openTrades.size() == 0){
            guiD.printNotification(sm.msgForNothing("that you can confirm whether it's completed for now"));
        }
        else{
            ConfirmTradeCompleteWindow confirmTradeCompleteWindow = new ConfirmTradeCompleteWindow(idC, atc, guiD, sm);
            confirmTradeCompleteWindow.run(idC, atc, guiD, sm);
        }
    }

    private void viewTrades(SystemMessage sm, List<Trade> trades, String type, GUIDemo guiD) {
        if (trades.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(trades));
            guiD.printNotification("Here's your list of " + type + " trades: \n" + str);
        } else {
            guiD.printNotification(sm.msgForNothing("here."));
        }
    }

    private void respondToTradeRequest(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, RegularUserIDChecker idC,
                                       GUIDemo guiD) {
        List<Trade> tradeRequests;
        if (atc.lockThresholdOrNot()) {
            guiD.printNotification(sm.lockMessageForThreshold(maxNumTransactionAWeek));
        }
        else{
            tradeRequests = atc.tradeRequestsToRespond();
            if (tradeRequests.size() != 0){
                String strTR = sm.printListObject(new ArrayList<>(tradeRequests));
                RespondToTradeRequestsWindow respondToTradeRequestsWindow = new RespondToTradeRequestsWindow(strTR, idC, atc, guiD, sm);
                respondToTradeRequestsWindow.run(strTR, idC, atc, guiD, sm);
            }
            else{
                //case with no trade requests
                guiD.printNotification(sm.msgForNothing("that you need to respond to here"));
            }
        }
    }

    private void RequestATrade(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, RegularUserIDChecker idC,  int numLentBeforeBorrow, GUIDemo guiD) {
        if (atc.lockThresholdOrNot()) {
            guiD.printNotification(sm.lockMessageForThreshold(maxNumTransactionAWeek));
        }
        else{
            RequestATradeWindow requestATradeWindow = new RequestATradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow);
            requestATradeWindow.run(idC,guiD,atc,sm, numLentBeforeBorrow);

        }
    }



    public void run(GUIDemo guiD, RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek,
                    int numLentBeforeBorrow, RegularUserIDChecker idC, boolean guest) {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI(guiD, atc, sm, maxNumTransactionAWeek, numLentBeforeBorrow,idC, guest).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
