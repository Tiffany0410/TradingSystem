package bookTradeSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
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
    protected void seeTopThreePartners() throws InvalidIdException {
        if (tm.getTradeHistory(userId).size() != 0){
            List<Integer> topThreeIDS= tm.topThreePartners(userId);
            List<User> topThree = new ArrayList<>();
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
     * Asks the user to confirm the trade to be complete.
     * If there're no open trades, print an appropriate
     * message.
     * @throws InvalidIdException In case the id is invalid.
     */
    protected void confirmTradeComplete() throws InvalidIdException {
        if (tm.getOpenTrade(userId).size() != 0) {
            ds.printResult(new ArrayList<>(tm.getOpenTrade(userId)));
            int tradeId = idGetter.getTradeID();
//           let user enter trade id and we use it to confirm complete
            ds.printResult(tm.confirmComplete(tradeId));
        }
        else{
            sm.msgForNothing(ds);
        }
    }

    /**
     * Let the presenter to print to screen
     * <trades></trades>, if there are
     * any. If there aren't any, print
     * an appropriate message.
     * @param trades The list of <trades></trades>.
     */
    protected void viewTrades(List<Trade> trades) {
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
    protected void respondToTradeRequests(User thisUser) throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (thisUser.getNumTransactionLeftForTheWeek() == 0) {
            // the case with user reaching the max number of transactions for the week
            sm.lockMessageForThreshold(ds, thresholdValues.get(0));
        }
        else if (tm.getTradeHistory(userId).size() == 0){
            sm.msgForNothing(ds);
        }
        else {
            TradeRequestsToRespond();
            // asks for trade id
            int tradeID = idGetter.getTradeID();
            // get the actual trade object
            Trade trade = tm.getTradeById(tradeID);
            // will be used if two-way-trade
            int itemid22 = 0;
            // will store if the user agreed before
            boolean agreedBefore = false;
            // if it's one-way-trade
            // only need borrower id, lender id, and the item id
            int userId11 = trade.getIds().get(1);
            int userId22 = trade.getIds().get(2);
            int itemId11 = trade.getIds().get(3);
            if (!trade.getIsOneWayTrade()){
                // two-way-trade - need one more item id
                itemid22 = trade.getIds().get(4);
            }
            // see if the user already agreed to the request
            agreedBefore = isAgreedBefore(agreedBefore, trade.getUserStatus(userId).equals("Agree"), true);
            // the result of the response
            respondResult(tradeID, trade, itemid22, agreedBefore, userId11, userId22, itemId11);
        }
    }

    private void TradeRequestsToRespond() throws InvalidIdException {
        //assume wait-to-be-opened = wait for the other user's response
        List<Trade> requests = new ArrayList<>();
        // only print ones that user haven't agree on
        for (Trade t: tm.getWaitTrade(userId)){
            if (t.getUserStatus(userId).equals("Disagree")){
                requests.add(t);
            }
        }
        ds.printResult(new ArrayList<>(requests));
    }

    private boolean isAgreedBefore(boolean agreedBefore, boolean agree, boolean b) {
        if (agree) {
            agreedBefore = b;
        }
        return agreedBefore;
    }

    private void respondResult(int tradeID, Trade trade, int itemid22, boolean agreedBefore, int userId11, int userId22, int itemId11) throws InvalidIdException {
        // if the user haven't agreed before
        if (!agreedBefore) {
            String tradeStatus = otherInfoGetter.getAgreeOrNot();
            trade.setUserStatus(userId, tradeStatus);
            //remove items -- if agree
            if (tradeStatus.equals("Agree")) {
                respondAgree(tradeID, trade, itemid22, userId11, userId22, itemId11);
            } else {
                // cancel the trade so user can see it's cancelled in the list of cancelled trades
                trade.cancelTrade();
            }
            ds.printResult("Your response to this trade request", true);
        }
        else {
            //because the user already agreed so false request
            ds.printResult(false);
        }
    }

    private void respondAgree(int tradeID, Trade trade, int itemid22, int userId11, int userId22, int itemId11) throws InvalidIdException {
        // remove + record the borrowing/lending
        um.removeItemFromUsers(userId11, userId22, itemId11);
        if (!trade.getIsOneWayTrade()) {
            // remove + record the borrowing/lending
            um.removeItemFromUsers(userId11, userId22, itemid22);
        }
        // change the status to open
        // so it won't be among the list of trade requests again
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
    protected void requestTrade(User thisUser) throws FileNotFoundException {
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
            Trade trade;
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


    private Trade getTrade(int numKindOfTrade, int itemId2, int userId1, int userId2, int itemId, int tradeID, String tradeType) {
        Trade trade;
        if (numKindOfTrade == 1) {
            // new one-way-trade
            trade = new Trade(userId1, userId2, itemId, tradeType, true, tradeID);
        }
        else {
            // new two-way-trade
            trade = new Trade(userId1, userId2, itemId, itemId2, tradeType, false, tradeID);
        }
        return trade;
    }

    private void requestResult(User thisUser, boolean ok, Trade trade, int userId1) throws FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (tm.validateTrade(trade, um.findUser(userId1), thresholdValues.get(2)) && ok) {
            requestSuccess(thisUser, trade);
        }
        else {
            requestFail(thisUser);
        }
    }

    private void requestFail(User thisUser) {
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
