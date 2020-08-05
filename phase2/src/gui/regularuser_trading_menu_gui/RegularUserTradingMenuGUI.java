package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
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
                                     int numLentBeforeBorrow, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC,
                                     RegularUserOtherInfoChecker oiC){

        requestATradeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestATrade(atc, sm, maxNumTransactionAWeek, guiUserInputInfo, idC, oiC, numLentBeforeBorrow);
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
                respondToTradeRequest(atc, sm, maxNumTransactionAWeek, guiUserInputInfo, idC, oiC);
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
                viewTrades(sm, atc.viewOpenTrades(), "open");

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
                viewTrades(sm, atc.viewClosedTrades(), "closed");
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
                confirmATradeIsCompleted(atc, sm, guiUserInputInfo, idC);


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
                seeTopThreePartners(atc, sm);
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
                viewTrades(sm, atc.viewCancelledTrades(), "cancelled");
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
                if (atc.hasTradeSuggestion()){
                    String str = sm.printListObject(new ArrayList<>(atc.mostReasonableTradeSuggestions()));
                    printNote("Trade suggestion for you (first number = item id, second number = this item's owner's id): \n" + str);
                }
                else{
                    printNote(sm.msgForNo(" recommended trade suggestion."));
                }

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
                guiD.runRegularUserMainMenu(false);
            }
        });
    }

    private void seeTopThreePartners(RegularUserTradingMenuController atc, SystemMessage sm) {
        if (atc.hasTopThree()){
            //has top three
            String str = sm.printListObject(new ArrayList<>(atc.seeTopThreePartners()));
            printNote("Here's your list of top three partners: \n" + str);
        } else {
            printNote(sm.msgForNothing("here."));
        }
    }

    private void confirmATradeIsCompleted(RegularUserTradingMenuController atc, SystemMessage sm, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC) {
        List<Trade> openTrades = atc.viewOpenTrades();
        boolean result;
        viewTrades(sm, openTrades, "open");
        if (openTrades.size() == 0){
            printNote(sm.msgForNothing("that you can confirm whether it's completed for now"));
        }
        else{
            String askTradeId = "Please enter the trade id of the trade for which you want to check its completion of";
            String input = getInPut(askTradeId, guiUserInputInfo);
            if (idC.checkInt(input)) {
                int tradeId = Integer.parseInt(input);
                if (idC.checkTradeID(tradeId)) {
                    result = atc.confirmTradeComplete(tradeId);
                    printNote(sm.msgFortradeCompletedOrNot(result));
                } else {
                    printNote(sm.tryAgainMsgForWrongInput());
                }
            }
            else{
                printNote(sm.tryAgainMsgForWrongFormatInput());
            }
        }
    }

    private void viewTrades(SystemMessage sm, List<Trade> trades, String type) {
        if (trades.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(trades));
            printNote("Here's your list of " + type + " trades: \n" + str);
        } else {
            printNote(sm.msgForNothing("here."));
        }
    }

    private void respondToTradeRequest(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC, RegularUserOtherInfoChecker oiC) {
        List<Trade> tradeRequests;
        if (atc.lockThresholdOrNot()) {
            printNote(sm.lockMessageForThreshold(maxNumTransactionAWeek));
        }
        else{
            //case with no trade requests
            tradeRequests = atc.tradeRequestsToRespond();
            if (tradeRequests.size() != 0){
                String strTR = sm.printListObject(new ArrayList<>(tradeRequests));
                printNote("Here's a list of trade requests: \n" + strTR);
                String askTradeId = "Please enter the trade id of the trade request you wish to respond to.";
                String input1 = getInPut(askTradeId, guiUserInputInfo);
                String askResponse = "Do you agree or disagree? Please enter the word in all lowercase.";
                String response = getInPut(askResponse, guiUserInputInfo);
                if (idC.checkInt(input1)){
                    int tradeId = Integer.parseInt(input1);
                    if (idC.checkTradeID(tradeId) && oiC.checkAgreeOrNot(response)){
                        atc.respondToTradeRequests(tradeId, response);
                        printNote(sm.msgForRequestProcess(true));
                    }
                    else{
                        printNote(sm.tryAgainMsgForWrongInput());
                    }
                }
                else {
                    printNote(sm.tryAgainMsgForWrongFormatInput());
                }
            }
            else{
                printNote(sm.msgForNothing("that you need to respond to here"));
            }
        }
    }

    private void RequestATrade(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC, RegularUserOtherInfoChecker oiC, int numLentBeforeBorrow) {
        if (atc.lockThresholdOrNot()) {
            printNote(sm.lockMessageForThreshold(maxNumTransactionAWeek));
        }
        else{
            //asks for input
            String askTradeKind = "Please enter the kind of trade - (1 - one-way-trade/2 - two-way-trade).";
            String input1 = getInPut(askTradeKind, guiUserInputInfo);
            String askUserid1 = "Please enter the user id of the borrower (if one-way-trade) " +
                    "or borrower for the first item and lender for the second item (if two-way-trade).";
            String input2 = getInPut(askUserid1, guiUserInputInfo);
            String askUserid2= "Please enter the user id of the lender (if one-way-trade) or lender for the first item " +
                    "and borrower for the second item (if two-way-trade).";
            String input3 = getInPut(askUserid2, guiUserInputInfo);
            String askItemid1 = "Please enter the item id of the first item (or the item if it's a one-way-trade).";
            String input4 = getInPut(askItemid1, guiUserInputInfo);
            String askItemid2 = "Please enter the item id of the second item (or a random number if it's a one-way-trade).";
            String input5 = getInPut(askItemid2, guiUserInputInfo);
            String askTradeType = "Please enter trade type (permanent / temporary).";
            String tradeType = getInPut(askItemid2, guiUserInputInfo);

            if (idC.checkInt(input1) && idC.checkInt(input2) && idC.checkInt(input3) && idC.checkInt(input4)
                    && idC.checkInt(input5)){
                int tradeKind = Integer.parseInt(input1);
                int userId1 = Integer.parseInt(input2);
                int userId2 = Integer.parseInt(input3);
                int itemid1 = Integer.parseInt(input4);
                int itemid2 = Integer.parseInt(input5);
                if ((tradeKind == 1 || tradeKind == 2) && idC.checkUserID(userId1) && idC.checkUserID(userId2)
                    && idC.checkItemID(itemid1, 1) && idC.checkItemID(itemid2, 1)
                        && oiC.checkTradeType(tradeType)) {
                        printNote(atc.requestTrade(tradeKind, userId1, userId2, itemid1, itemid2, numLentBeforeBorrow, tradeType));
                }
                 else{
                     printNote(sm.tryAgainMsgForWrongInput());
                }
            }
            else{
                printNote(sm.tryAgainMsgForWrongFormatInput());
            }
        }
    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String userResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return userResponse;

    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public void printNote(String msg){
            NotificationGUI msgGUI = new NotificationGUI(msg);
            msgGUI.run(msg);
            // TODO: need to close first
    }



    public void run(GUIDemo guiD, RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek,
                    int numLentBeforeBorrow, GUIUserInputInfo guiUserInputInfo, RegularUserIDChecker idC,
                    RegularUserOtherInfoChecker oiC) {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI(guiD, atc, sm, maxNumTransactionAWeek, numLentBeforeBorrow, guiUserInputInfo, idC, oiC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
