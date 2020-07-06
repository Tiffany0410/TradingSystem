package bookTradeSystem;

import java.util.ArrayList;
import java.util.List;

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

    protected void confirmTradeComplete() throws InvalidIdException {
        if (tm.getOpenTrade(userId).size() != 0) {
            ds.printResult(new ArrayList<>(tm.getOpenTrade(userId)));
            int tradeId = idGetter.getTradeID();
//              let user enter trade id and we use it to confirm complete
            ds.printResult(tm.confirmComplete(tradeId));
        }
        else{
            sm.msgForNothing(ds);
        }
    }

    protected void viewOpenTrades(List<Trade> openTrade) {
        if (openTrade.size() != 0) {
            ds.printResult(new ArrayList<>(openTrade));
        } else {
            sm.msgForNothing(ds);
        }
    }

    protected void respondToTradeRequests(User thisUser) throws InvalidIdException {
        if (thisUser.getNumTransactionLeftForTheWeek() == 0) {
            // the case with user reaching the max number of transactions for the week
            sm.lockMessageForThreshold(ds);
        }
        else if (tm.getTradeHistory(userId).size() == 0){
            sm.msgForNothing(ds);
        }
        else {
            //assume wait-to-be-opened = wait for the other user's response
            ds.printResult(new ArrayList<>(tm.getWaitTrade(userId)));
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

    protected boolean isAgreedBefore(boolean agreedBefore, boolean agree, boolean b) {
        if (agree) {
            agreedBefore = b;
        }
        return agreedBefore;
    }

    protected void respondResult(int tradeID, Trade trade, int itemid22, boolean agreedBefore, int userId11, int userId22, int itemId11) throws InvalidIdException {
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
            ds.printResult(true);
        }
        //because the user already agreed so false request
        ds.printResult(false);
    }

    protected void respondAgree(int tradeID, Trade trade, int itemid22, int userId11, int userId22, int itemId11) throws InvalidIdException {
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

    protected void requestTrade(User thisUser) {
        if (thisUser.getNumTransactionLeftForTheWeek() == 0){
            // the case with user reaching the max number of transactions for the week
            sm.lockMessageForThreshold(ds);
        }
        else {
            // get whether it is one-way-trade or two-way-trade
            int numKindOfTrade = otherInfoGetter.getNumKindOfTrade();
            // will store the validation value
            boolean ok = false;
            // will store the trade object
            Trade trade;
            // will store the item id if it's two-way-trade
            int itemId2 = 0;
            //get info for trade
            int userId1 = idGetter.getUserID("borrower (if one-way-trade) or borrower for the first item and lender for the second item (if two-way-trade)");
            int userId2 = idGetter.getUserID("lender (if one-way-trade) or lender for the first item and borrower for the second item (if two-way-trade)");
            int itemId = idGetter.getItemID(idGetter.getAllItems(), 1);
            int tradeID = determineTradeID();
            String tradeType = otherInfoGetter.getTradeType();
            if (numKindOfTrade == 2){ itemId2 = idGetter.getItemID(idGetter.getAllItems(), 1); }
            //get the trade object
            trade = getTrade(numKindOfTrade, itemId2, userId1, userId2, itemId, tradeID, tradeType);
            // get the validation from the item side
            ok = getValidationForItems(numKindOfTrade, itemId2, userId1, userId2, itemId);
            // use all these to determine the result
            requestResult(thisUser, ok, trade, userId1);
        }
    }

    protected int determineTradeID() {
        int tradeID;
        if (tm.getListTrade().size() != 0) {tradeID = tm.getListTrade().size() + 1;}
        else {tradeID = 1;}
        return tradeID;
    }

    protected boolean getValidationForItems(int numKindOfTrade, int itemId2, int userId1, int userId2, int itemId) {
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

    protected Trade getTrade(int numKindOfTrade, int itemId2, int userId1, int userId2, int itemId, int tradeID, String tradeType) {
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

    protected void requestResult(User thisUser, boolean ok, Trade trade, int userId1) {
        if (tm.validateTrade(trade, um.findUser(userId1)) && ok) {
            requestSuccess(thisUser, trade);
        }
        else {
            requestFail(thisUser);
        }
    }

    protected void requestFail(User thisUser) {
        //TODO if the trade request failed
        ds.printResult(false);
        // TODO: should I put this here?
        // system auto-freeze
        // user borrow more than lend
        if (thisUser.getNumBorrowed() > thisUser.getNumLent()){
            um.freezeUser(username);
            ds.printOut("You're frozen because you borrowed more than you lend.");
        }
    }

    protected void requestSuccess(User thisUser, Trade trade) {
        // add trade
        tm.addTrade(trade);
        // tell the user it's successful
        ds.printResult(true);
        // set status for the person who requested the trade
        trade.setUserStatus(userId, "Agree");
        // change the threshold value
        tc.changeNumTradesLeftForTheWeek(thisUser);
    }

    // TODO MOVE TO
    protected boolean validateItems(int borrower, int lender, int itemId){
        return um.findUser(borrower).getWishList().contains(itemId) &&
                um.findUser(lender).getInventory().contains(itemId);
    }

    // TODO MOVE TO
    protected boolean validateItems(int borrower1Lender2, int borrower2lender1, int itemId1, int itemId2){
        return validateItems(borrower1Lender2, borrower2lender1, itemId1) &&
                validateItems(borrower2lender1, borrower1Lender2, itemId2);
    }

}
