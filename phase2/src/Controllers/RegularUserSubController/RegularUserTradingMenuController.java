package Controllers.RegularUserSubController;

import Gateway.FilesReaderWriter;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.Trade;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.User;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;
import Presenter.SystemMessage;
import Exception.InvalidIdException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter, for the trade menu part.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserTradingMenuController {

    private RegularUserThresholdController tc;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private RegularUserIDGetter idGetter;
    private SystemMessage sm;
    private Presenter.DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserTradingMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserTradingMenuController(DisplaySystem ds,
                                            TradeManager tm, MeetingManager mm,
                                            UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
        this.tc = new RegularUserThresholdController(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, username, userId);
        this.sm = new SystemMessage();
    }

    /**
     * If the user has top three trading partners,
     * print it to the screen. Else, print
     * an appropriate message to inform the user
     * of so.
     * @throws InvalidIdException In case the id is invalid.
     */
    public void seeTopThreePartners() throws InvalidIdException {
        if (tm.getTradeHistory(userId).size() != 0){
            List<Integer> topThreeIDS= tm.topThreePartners(userId);
            List<Managers.UserManager.User> topThree = new ArrayList<>();
            for (int id : topThreeIDS) {
                topThree.add(um.findUser(id));
                ds.printResult(new ArrayList<>(topThree));
            }
        }
        else{
            // because the user do not have any trade
            sm.msgForNothing(ds);
        }
    }

    /**
     * Asks the user to for the trade id and let the user
     * know if the trade with this trade id is completed
     * or not.
     * If there're no open trades, print an appropriate
     * message.
     * @throws InvalidIdException In case the id is invalid.
     */
    public void confirmTradeComplete() throws InvalidIdException {
        if (tm.getOpenTrade(userId).size() != 0) {
            ds.printResult(new ArrayList<>(tm.getOpenTrade(userId)));
            int tradeId = idGetter.getTradeID();
//           let user enter trade id and we use it to confirm complete
            if (tm.confirmComplete(tradeId)){
                ds.printOut("This trade is completed.");
            }
            else{
                ds.printOut("This trade is Incomplete.");
            }
        }
        else{
            sm.msgForNothing("that you can confirm whether it's completed for now", ds);
        }
    }

    /**
     * Let the presenter to print to screen
     * <trades></trades>, if there are
     * any. If there aren't any, print
     * an appropriate message.
     * @param trades The list of <trades></trades>.
     */
    public void viewTrades(List<Managers.TradeManager.Trade> trades) {
        if (trades.size() != 0) {
            ds.printResult(new ArrayList<>(trades));
        } else {
            sm.msgForNothing(ds);
        }
    }

    /**
     * Gets from user the information about the trade the user
     * wants to respond to and determine whether the
     * response is successfully sent or not. If there're no
     * outstanding trade requests or if the user has reached
     * the maximum number of transactions
     * for a week threshold, print an appropriate message.
     * @param thisUser The user to respond to the request.
     * @throws InvalidIdException In case the id is invalid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void respondToTradeRequests(Managers.UserManager.User thisUser) throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (thisUser.getNumTransactionLeftForTheWeek() == 0) {
            // the case with user reaching the max number of transactions for the week
            sm.lockMessageForThreshold(ds, thresholdValues.get(0));
        }
        else {
            // user haven't reach the max number of transactions a week threshold
            // there're no trade request to respond to
            if (tradeRequestsToRespond().size() == 0){
                sm.msgForNothing("that you need to respond to here", ds);
            }
            // there is trade request to respond to
            // no need to check if the user agreed before
            // because they won't be able to agree / disagree for
            // a second time
            else {
                // print the trade requests
                ds.printResult(new ArrayList<>(tradeRequestsToRespond()));
                // asks for trade id
                int tradeID = idGetter.getTradeID();
                // get the actual trade object
                Managers.TradeManager.Trade trade = tm.getTradeById(tradeID);
                // will be used if two-way-trade
                int itemid22 = 0;
                // if it's one-way-trade
                // only need borrower id, lender id, and the item id
//              TODO: tm need to have a method such that when given the trade id and the index,
//              TODO: it can return the right id
//              TODO: replace trade.getIds()
                int userId11 = trade.getIds().get(1);
                int userId22 = trade.getIds().get(2);
                int itemId11 = trade.getIds().get(3);
//              TODO: tm need to have a method such that when given the trade id
//              TODO: it can return whether it's one way or two way trade
//              TODO: replace trade.getIsOneWayTrade()
                if (!trade.getIsOneWayTrade()) {
                    // two-way-trade - need one more item id
                    itemid22 = trade.getIds().get(4);
                }
                // the result of the response
                respondResult(tradeID, trade, itemid22, userId11, userId22, itemId11);
            }
        }
    }

    private List<Managers.TradeManager.Trade> tradeRequestsToRespond() throws InvalidIdException {
        //assume wait-to-be-opened = wait for the other user's response
        List<Managers.TradeManager.Trade> requests = new ArrayList<>();
        // only print ones that user haven't agree on
        for (Managers.TradeManager.Trade t: tm.getWaitTrade(userId)){
            if (t.getUserStatus(userId).equals("Disagree")){
                requests.add(t);
            }
        }
        return requests;
    }

    private void respondResult(int tradeID, Managers.TradeManager.Trade trade, int itemid22, int userId11, int userId22, int itemId11) throws InvalidIdException {
        String tradeStatus = otherInfoGetter.getAgreeOrNot();
        trade.setUserStatus(userId, tradeStatus);
        //remove items -- if agree
        if (tradeStatus.equals("Agree")) {
            respondAgree(tradeID, trade, itemid22, userId11, userId22, itemId11);
        } else {
//          TODO: need a tm method that cancels the trade with the given trade id
//          TODO: replace trade.cancelTrade()
            // cancel the trade so user can see it's cancelled in the list of cancelled trades
            trade.cancelTrade();
        }
        ds.printResult("Your response to this trade request", true);
    }

    private void respondAgree(int tradeID, Managers.TradeManager.Trade trade, int itemid22, int userId11, int userId22, int itemId11) throws InvalidIdException {
        // remove + record the borrowing/lending
        um.removeItemFromUsers(userId11, userId22, itemId11);
        if (!trade.getIsOneWayTrade()) {
            // remove + record the borrowing/lending
            um.removeItemFromUsers(userId11, userId22, itemid22);
        }
        // change the status to open
        // so it won't be among the list of trade requests again
//      TODO: need a tm method that opens the trade with the given trade id
//      TODO: replace trade.openTrade()
        trade.openTrade();
        mm.addMeeting(tradeID, userId11, userId22, 1, tm);
    }

    /**
     * Gets from user the information about the trade the user
     * request and determine whether the request is sent
     * successfully sent or not. If the user has reached
     * the maximum number of transactions
     * for a week threshold, print an appropriate message.
     * @param thisUser The user who wants to request the trade.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void requestTrade(Managers.UserManager.User thisUser) throws FileNotFoundException {
        // read threshold values in from the csv file
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        // if the user has no more transactions left
        if (thisUser.getNumTransactionLeftForTheWeek() == 0){
            // the case with user reaching the max number of transactions for the week
            sm.lockMessageForThreshold(ds, thresholdValues.get(0));
        }
        else {
            // get whether it is one-way-trade or two-way-trade
            int numKindOfTrade = otherInfoGetter.getNumKindOfTrade();
            // will store the validation value
            boolean ok;
            // will store the trade object
            Managers.TradeManager.Trade trade;
            // will store the second item id if it's two-way-trade
            int itemId2 = 0;
            //get info for trade
            // get borrower1lender2's user Id
            int userId1 = idGetter.getUserID("borrower (if one-way-trade) or borrower for the first item and lender for the second item (if two-way-trade)");
            // get borrower2lender1's user Id
            int userId2 = idGetter.getUserID("lender (if one-way-trade) or lender for the first item and borrower for the second item (if two-way-trade)");
            // Get the id for the first item
            ds.printOut("For the first item: ");
            int itemId = idGetter.getItemID(idGetter.getAllItems(), 1);
            // get the trade id
            int tradeID = determineTradeID();
            // get the trade type (permanent or temporary)
            String tradeType = otherInfoGetter.getTradeType();
            // if two-way-trade
            if (numKindOfTrade == 2){
                // asks for the item id for the second item
                ds.printOut("For the second item: ");
                itemId2 = idGetter.getItemID(idGetter.getAllItems(), 1); }
            //get the trade object
            trade = getTrade(numKindOfTrade, itemId2, userId1, userId2, itemId, tradeID, tradeType);
            // get the validation from the item side
            ok = getValidationForItems(numKindOfTrade, itemId2, userId1, userId2, itemId);
            // use all these to determine the result
            requestResult(thisUser, ok, trade, userId1);
        }
    }

    private int determineTradeID() {
        int tradeID;
        //add the trade id so that there're no duplicates
        if (tm.getListTrade().size() != 0) {tradeID = tm.getListTrade().size() + 1;}
        else {tradeID = 1;}
        return tradeID;
    }

    private boolean getValidationForItems(int numKindOfTrade, int itemId2, int userId1, int userId2, int itemId) {
        boolean ok;
        if (numKindOfTrade == 1) {
            // pass in borrower, lender, item
            ok = validateItems(userId1, userId2, itemId);
        }
        else {
            // pass in (borrower for itemId + lender for itemId2) and (borrower for itemId2 + lender for itemId)
            ok = validateItems(userId1, userId2, itemId, itemId2);
        }
        return ok;
    }


    private Managers.TradeManager.Trade getTrade(int numKindOfTrade, int itemId2, int userId1, int userId2, int itemId, int tradeID, String tradeType) {
        Managers.TradeManager.Trade trade;
//      TODO: replace them with createTrade instead - can't just call the constructor
        if (numKindOfTrade == 1) {
            // new one-way-trade
            trade = new Managers.TradeManager.Trade(userId1, userId2, itemId, tradeType, true, tradeID);
        }
        else {
            // new two-way-trade
            trade = new Managers.TradeManager.Trade(userId1, userId2, itemId, itemId2, tradeType, false, tradeID);
        }
        return trade;
    }

    private void requestResult(Managers.UserManager.User thisUser, boolean ok, Managers.TradeManager.Trade trade, int userId1) throws FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (tm.validateTrade(trade, um.findUser(userId1), thresholdValues.get(2)) && ok) {
            requestSuccess(thisUser, trade);
        }
        else {
            requestFail(thisUser);
        }
    }

    private void requestFail(Managers.UserManager.User thisUser) {
        //if the trade request failed
        ds.printResult(false);
        // system auto-freeze
        // user borrow more than lend
        if (thisUser.getNumBorrowed() > thisUser.getNumLent()){
            um.freezeUser(username);
            ds.printOut("You're frozen because you borrowed more than you lend.");
        }
    }

    private void requestSuccess(User thisUser, Trade trade) {
        // add trade
        tm.addTrade(trade);
        // tell the user it's successful
        ds.printResult(true);
        // set status for the person who requested the trade
        trade.setUserStatus(userId, "Agree");
        // change the threshold value
        tc.changeNumTradesLeftForTheWeek(thisUser);
    }


    private boolean validateItems(int borrower, int lender, int itemId){
        // return true iff the borrower has the item in his/her wishlist and
        // the lender has the item in his/her inventory
        return um.findUser(borrower).getWishList().contains(itemId) &&
                um.findUser(lender).getInventory().contains(idGetter.idToItem(itemId));
    }


    private boolean validateItems(int borrower1Lender2, int borrower2lender1, int itemId1, int itemId2){
        // return true iff the borrower has the item in his/her wishlist and
        // the lender has the item in his/her inventory for both items
        // to be traded
        return validateItems(borrower1Lender2, borrower2lender1, itemId1) &&
                validateItems(borrower2lender1, borrower1Lender2, itemId2);
    }

}
