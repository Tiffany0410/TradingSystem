package controllers.regularusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.itemmanager.Item;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.Trade;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import managers.itemmanager.ItemManager;
import presenter.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user
 * and the use cases for the trade menu part.
 *
 * @author Yu Xin Yan, Yuanze Bao
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserTradingMenuController {

    private RegularUserThresholdController tc;
    private SystemMessage sm;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserTradingMenuController with
     * a TradeManager, a MeetingManager, an UserManager, an ActionManager, the regular user's username,
     * the presenter, and the threshold controller.
     *
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager
     * @param am       The current state of the ActionManager.
     * @param username The username of the regular user.
     * @param sm       The presenter in our system.
     * @param tc       The regularUserThresholdController.
     */
    public RegularUserTradingMenuController(TradeManager tm, MeetingManager mm,
                                            UserManager um, ItemManager im, ActionManager am,
                                            String username, SystemMessage sm, RegularUserThresholdController tc) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.username = username;
        this.userId = um.usernameToID(this.username);
        this.tc = tc;
        this.sm = sm;
    }

    /**
     * Puts together the list of top three partners for the user.
     * @return This user's top three trading partners.
     */
    public List<TradableUser> seeTopThreePartners()  {
        List<Integer> topThreeIDS= tm.topThreePartners(userId);
        List<TradableUser> topThree = new ArrayList<>();
        for (int id : topThreeIDS) {
            topThree.add(um.findUser(id));
        }
        am.addActionToAllActionsList(userId, "regularUser", "2.6", 0, "");
        return topThree;
    }

    /**
     * Decides if the user has top three partners.
     * @return true if user has top three otherwise false.
     */
    public boolean hasTopThree()  {
        return tm.getTradeHistory(userId).size() != 0;
    }

    /**
     * Checks if the trade with the given trade id is
     * complete.
     * @param tradeId The trade id of the trade to be checked for completion.
     * @return Whether or not the trade that correspond to the trade id is complete.
     */
    public boolean confirmTradeComplete(int tradeId)  {
        am.addActionToAllActionsList(userId, "regularUser", "2.5", tradeId, "");
        return tm.confirmComplete(tradeId);
    }

    /**
     * Uses the given trade id and respond Status to
     * set the respond status for the user for the trade
     * that corresponds to the trade id.
     * @param tradeID The trade id input by the user.
     * @param respondStatus The respond status of the user.
     */
    public void respondToTradeRequests(int tradeID, String respondStatus){
        // will be used if two-way-trade
        int itemid22 = 0;
        // if it's one-way-trade
        // only need borrower id, lender id, and the item id
        int userId11 = tm.getId(tradeID, 1);
        int userId22 = tm.getId(tradeID, 2);
        int itemId11 = tm.getId(tradeID, 3);
        if (!tm.checkOneWayTrade(tradeID)) {
            // two-way-trade - need one more item id
            itemid22 = tm.getId(tradeID, 4);
        }
        // the result of the response
        respondResult(tradeID, itemid22, userId11, userId22, itemId11, respondStatus);
        }

    /**
     * Puts together a list of trade requests the user needs to respond to.
     * @return The list of trade requests the user needs to respond to.
     */
     public List<Trade> tradeRequestsToRespond() {
        //assume wait-to-be-opened = wait for the other user's response
        List<managers.trademanager.Trade> requests = new ArrayList<>();
        // only print ones that user haven't agree on
        for (managers.trademanager.Trade t: tm.getWaitTrade(userId)){
            if (t.getUserStatus(userId).equals("Disagree")){
                requests.add(t);
            }
        }
        return requests;
    }

    private void respondResult(int tradeID,  int itemid22, int userId11, int userId22, int itemId11, String respondStatus) {
        // set user's status for the trade (agree / disagree)
        tm.setUserStatus(tradeID, userId, respondStatus);
        //remove items -- if agree
        if (respondStatus.equals("Agree")) {
            respondAgree(tradeID, itemid22, userId11, userId22, itemId11);
            am.addActionToAllActionsList(userId, "regularUser", "2.2", tradeID, "");
        } else {
            // cancel the trade so user can see it's cancelled in the list of cancelled trades
            tm.cancelTrade(tradeID);
        }
    }

    private void respondAgree(int tradeID, int itemid22, int userId11, int userId22, int itemId11) {
        // remove + record the borrowing/lending
        um.removeItemFromUsers(userId11, userId22, itemId11);
        if (!tm.checkOneWayTrade(tradeID)) {
            // remove + record the borrowing/lending
            um.removeItemFromUsers(userId11, userId22, itemid22);
        }
        // change the status to open
        // so it won't be among the list of trade requests again
        tm.openTrade(tradeID, im);
        mm.addMeeting(tradeID, userId11, userId22, 1, tm);
    }

    /**
     * Checks to see if this user has reached the maximum number of transactions per week.
     * @return Whether or not this user has reached the maximum number of transactions per week.
     */
    public boolean lockThresholdOrNot(){
        // if the user has no more transactions left
        return um.getInfo(userId, "TransactionLeftForTheWeek") == 0;

    }


    /**
     * Requests a trade with information about the users
     * as well as the item(s) to be involved.
     * @param numKindOfTrade The kind of trade (one-way / two-way).
     * @param borrowerOrborrower1lender2 The borrower (if one-way), or the borrower
     *                                   for the first item and the lender for the second
     *                                   item (if two-way).
     * @param lenderOrlender1borrower2   The lender (if one-way), or the lender for
     *                                   the first item and the borrower for the second
     *                                   item (if two-way).
     * @param itemId1 The id of the first item (or only item) to be traded.
     * @param itemId2 The id of the second item to be traded (if two-way-trade).
     * @param numLentBeforeBorrow The number of items the user must lend before he/she can borrow.
     * @param tradeType The type of the trade (temporary / permanent).
     * @return A message for the result of the request.
     */
    public String requestTrade(int numKindOfTrade, int borrowerOrborrower1lender2, int lenderOrlender1borrower2,
                               int itemId1, int itemId2, int numLentBeforeBorrow, String tradeType) {
        // get the trade id
        int tradeID = determineTradeID();
        //get the trade object
        Trade trade = getTrade(numKindOfTrade, itemId2, borrowerOrborrower1lender2, lenderOrlender1borrower2, itemId1, tradeID, tradeType);
        // get the validation from the item side
        boolean ok = getValidationForItems(numKindOfTrade, itemId2, borrowerOrborrower1lender2, lenderOrlender1borrower2, itemId1);
        // use all these to determine the result
        return requestResult(ok, trade, tradeID, borrowerOrborrower1lender2, numLentBeforeBorrow);

    }


    /**
     * Decides if there is a trade suggestion for the user.
     * @return the system has a trade suggestion for the user.
     */
    public boolean hasTradeSuggestion() {
        if (um.getUserWishlist(userId).isEmpty()){
            return false;
        }
        return im.getMatchItem(im.getItemsByIds(um.getUserWishlist(userId))).size() != 0;
    }

    /**
     * Puts together the most reasonable trade suggestion for the user.
     * @return the most reasonable trade suggestion for the user.
     */
    public Item mostReasonableTradeSuggestions() {
        ArrayList<Integer> p = im.getMatchItem(im.getItemsByIds(um.getUserWishlist(userId)));
        am.addActionToAllActionsList(userId, "regularUser", "2.8", 0, "");
        return im.getItembyId(p.get(0));
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
            trade = tm.createTrade(userId1, userId2, itemId, tradeType, true, tradeID);
        }
        else {
            // new two-way-trade
            trade = tm.createTrade(userId1, userId2, itemId, itemId2, tradeType, false, tradeID);
        }
        return trade;
    }


    private String requestResult(boolean ok, Trade trade, int tradeId, int userId1, int numLendBeforeBorrow) {
        if (tm.validateTrade(trade, um.findUser(userId1), numLendBeforeBorrow) && ok) {
            am.addActionToAllActionsList(userId, "regularUser", "2.1", tradeId, " and succeed");
            am.addActionToCurrentRevocableList(userId, "regularUser", "2.1", tradeId, " and succeed");
            return requestSuccess(trade, tradeId);
        }
        else {
            am.addActionToCurrentRevocableList(userId, "regularUser", "2.1", tradeId, " but fail");
            am.addActionToAllActionsList(userId, "regularUser", "2.1", tradeId, " but fail");
            return requestFail();
        }
    }


    private String requestFail() {
        //if the trade request failed
        // system auto-freeze
        // user borrow more than lend
        if (um.getInfo(username, "NumBorrowed") > um.getInfo(username, "NumLent")){
            um.freezeUser(username);
            return sm.msgForRequestResult(false)+ "\n" + sm.failMessageForFrozen();
        }
        return sm.msgTradeRequestFail();
    }


    private String requestSuccess(Trade trade, int tradeId) {
        // add trade
        tm.addTrade(trade);
        // set status for the person who requested the trade
        tm.setUserStatus(tradeId, userId, "Agree");
        // change the threshold value
        tc.changeNumTradesLeftForTheWeek();
        return sm.msgForRequestResult(true);
    }



    private boolean validateItems(int borrower, int lender, int itemId) {
        // return true iff the borrower has the item in his/her wishlist and
        // the lender has the item in his/her inventory
        return um.findUser(borrower).getWishList().contains(itemId) &&
                um.findUser(lender).getInventory().contains(itemId);
    }


    private boolean validateItems(int borrower1Lender2, int borrower2lender1, int itemId1, int itemId2)  {
        // return true iff the borrower has the item in his/her wishlist and
        // the lender has the item in his/her inventory for both items
        // to be traded
        return validateItems(borrower1Lender2, borrower2lender1, itemId1) &&
                validateItems(borrower2lender1, borrower1Lender2, itemId2);
    }

    /**
     * Puts together a list of open trades for the user.
     * @return A list of open trades for the user.
     */
    public List<Trade> viewOpenTrades() {
        am.addActionToAllActionsList(userId, "regularUser", "2.3", 0, "");
        return tm.getOpenTrade(userId);
    }

    /**
     * Puts together a list of closed trades for the user.
     * @return A list of cancelled trades for the user.
     */
    public List<Trade> viewClosedTrades() {
        am.addActionToAllActionsList(userId, "regularUser", "2.4", 0, "");
        return tm.getClosedTrade(userId);
    }

    /**
     * Puts together a list of cancelled trades for the user.
     * @return A list of cancelled trades for the user.
     */
    public List<Trade> viewCancelledTrades() {
        am.addActionToAllActionsList(userId, "regularUser", "2.7", 0, "");
        return tm.getCancelledTrade(userId);
    }



}
