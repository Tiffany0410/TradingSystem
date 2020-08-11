package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * A class that is responsible for the view and getting input for user
 * when user wants to browse the trading menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
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

    /**
     * Constructs a RegularUserTradingMenuGUI.
     * @param guiD The GUI helper.
     * @param atc The RegularUserTradingMenuController.
     * @param sm The presenter.
     * @param maxNumTransactionAWeek The maximum number of transactions allowed a week.
     * @param numLentBeforeBorrow The number of items user must lend before the user can borrow.
     * @param idC The id checker.
     * @param guest Determines whether guest access is granted.
     */
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
                requestATrade(atc, sm, maxNumTransactionAWeek, idC, numLentBeforeBorrow, guiD, guest);

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
                respondToTradeRequest(atc, sm, maxNumTransactionAWeek, idC, guiD, guest);

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
                viewTrades(sm, atc.viewOpenTrades(), "open", guiD, guest);
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
                viewTrades(sm, atc.viewClosedTrades(), "closed", guiD, guest);
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
                confirmATradeIsCompleted(atc, sm, idC, guiD, guest);
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
                seeTopThreePartners(atc, sm, guiD, guest);
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
                viewTrades(sm, atc.viewCancelledTrades(), "cancelled", guiD, guest);
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
                suggestionForTheMostReasonableTrade(guest, guiD, sm, atc);

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

    private void suggestionForTheMostReasonableTrade(boolean guest, GUIDemo guiD, SystemMessage sm, RegularUserTradingMenuController atc) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (atc.hasTradeSuggestion()){
            Item item = atc.mostReasonableTradeSuggestions();
            String str = sm.printObject(item);
            guiD.printNotification("Trade suggestion for you:\n" + str);
        }
        else{
            guiD.printNotification(sm.msgForNoTradeSuggestion());
        }
        guiD.runSave();
    }

    private void seeTopThreePartners(RegularUserTradingMenuController atc, SystemMessage sm, GUIDemo guiD, boolean guest) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (atc.hasTopThree()){
            //has top three
            String str = sm.printListObject(new ArrayList<>(atc.seeTopThreePartners()));
            guiD.printNotification("Here's your list of top three partners: \n" + str);
        } else {
            guiD.printNotification(sm.msgForNothing("here."));
        }
        guiD.runSave();
    }

    private void confirmATradeIsCompleted(RegularUserTradingMenuController atc, SystemMessage sm, RegularUserIDChecker idC,
                                          GUIDemo guiD, boolean guest) {
        List<Trade> openTrades = atc.viewOpenTrades();
        viewTrades(sm, openTrades, "open", guiD, guest);
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (openTrades.size() == 0){
            guiD.printNotification(sm.msgForNothing("that you can confirm whether it's completed for now"));
        }
        else{
            ConfirmTradeCompleteWindow confirmTradeCompleteWindow = new ConfirmTradeCompleteWindow(idC, atc, guiD, sm);
            confirmTradeCompleteWindow.run(idC, atc, guiD, sm);
        }
        guiD.runSave();
    }

    private void viewTrades(SystemMessage sm, List<Trade> trades, String type, GUIDemo guiD, boolean guest) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (trades.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(trades));
            guiD.printNotification("Here's your list of " + type + " trades: \n" + str);
        } else {
            guiD.printNotification(sm.msgForNothing("here."));
        }
        guiD.runSave();
    }

    private void respondToTradeRequest(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, RegularUserIDChecker idC,
                                       GUIDemo guiD, boolean guest) {
        List<Trade> tradeRequests;
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (atc.lockThresholdOrNot()) {
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
        guiD.runSave();
    }

    private void requestATrade(RegularUserTradingMenuController atc, SystemMessage sm, int maxNumTransactionAWeek, RegularUserIDChecker idC,  int numLentBeforeBorrow, GUIDemo guiD, boolean guest) {
        if (guest){
            guiD.printNotification(sm.msgForGuest());
        }
        else if (atc.lockThresholdOrNot()) {
            guiD.printNotification(sm.lockMessageForThreshold(maxNumTransactionAWeek));
        }
        else{
            RequestTradeWindow window = new RequestTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow);
            window.run(idC, guiD, atc, sm, numLentBeforeBorrow);
            guiD.runSave();
        }
    }



    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param atc The RegularUserTradingMenuController.
     * @param sm The presenter.
     * @param maxNumTransactionAWeek The maximum number of transactions allowed a week.
     * @param numLentBeforeBorrow The number of items user must lend before the user can borrow.
     * @param idC The id checker.
     * @param guest Determines whether guest access is granted.
     */
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
