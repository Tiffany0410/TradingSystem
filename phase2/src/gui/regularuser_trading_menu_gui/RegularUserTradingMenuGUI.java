package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import exception.InvalidIdException;
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
                            //TODO: try and catch(???) maybe do it as one on the top level? - maybe don't need invalid
                            // id exception???
                            try {
                                printNote(atc.requestTrade(tradeKind, userId1, userId2, itemid1, itemid2, numLentBeforeBorrow, tradeType));
                            } catch (InvalidIdException invalidIdException) {
                                printNote("Invalid id / id(s) was/were entered...");
                            }
                        }
                         else{
                             printNote("Please request the trade again, one or more input(s) are invalid");
                        }
                    }
                    else{
                        printNote("One or more of your input(s) were in the incorrect format (ex. we ask for int and you entered string)");
                    }
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
                boolean ok = true;
                List<Trade> tradeRequests = new ArrayList<>();
                if (atc.lockThresholdOrNot()) {
                    printNote(sm.lockMessageForThreshold(maxNumTransactionAWeek));
                }
                else{
                    //Or if (atc.tradeRequestsToRespond().size() == 0){printNote(...);}
                    //TODO: problem with getTradeHistory's exception throwing
                    //case with no trade requests
                    try {
                        tradeRequests = atc.tradeRequestsToRespond();
                    } catch (InvalidIdException invalidIdException) {
                        printNote(sm.msgForNothing("here."));
                    }
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
                                try {
                                    atc.respondToTradeRequests(tradeId, response);
                                } catch (InvalidIdException invalidIdException) {
                                    printNote(sm.msgForTradeRequest(false));
                                    ok = false;
                                }
                                if (ok){
                                    printNote(sm.msgForTradeRequest(true));
                                }
                            }
                        }
                    }
                    else{
                        printNote(sm.msgForNothing("that you need to respond to here"));
                    }
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
                //TODO: refactor to a method - 3 options with similar structure
                List<Trade> openTrades = new ArrayList<>();
                try {
                    openTrades = atc.viewOpenTrades();
                } catch (InvalidIdException invalidIdException) {
                    printNote(sm.msgForNothing("here. It might be that you have not traded before"));
                }
                if (openTrades.size() != 0){
                    String str = sm.printListObject(new ArrayList<>(openTrades));
                    printNote("Here's your list of open trades: \n" + str);
                }
                else{
                    printNote(sm.msgForNothing("here."));
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
                List<Trade> closedTrades = new ArrayList<>();
                try {
                    closedTrades = atc.viewClosedTrades();
                } catch (InvalidIdException invalidIdException) {
                    printNote(sm.msgForNothing("here. It might be that you have not traded before"));
                }
                if (closedTrades.size() != 0){
                    String str = sm.printListObject(new ArrayList<>(closedTrades));
                    printNote("Here's your list of closed trades: \n" + str);
                }
                else{
                    printNote(sm.msgForNothing("here."));
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
                List<Trade> cancelledTrades = new ArrayList<>();
                try {
                    cancelledTrades = atc.viewCancelledTrades();
                } catch (InvalidIdException invalidIdException) {
                    printNote(sm.msgForNothing("here. It might be that you have not traded before"));
                }
                if (cancelledTrades.size() != 0){
                    String str = sm.printListObject(new ArrayList<>(cancelledTrades));
                    printNote("Here's your list of cancelled trades: \n" + str);
                }
                else{
                    printNote(sm.msgForNothing("here"));
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
    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String UserResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return string;

    }

    //TODO: C&P from community menu - maybe can move this method
    // to somewhere the gui classes all have access to??
    public void printNote(String msg){
            NotificationGUI msgGUI = new NotificationGUI(msg);
            msgGUI.run(msg);
            // TODO: need to close first
    }

    public void run() {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
