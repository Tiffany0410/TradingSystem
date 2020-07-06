package bookTradeSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter.
 */
public class RegularUserController implements Serializable, Controllable {

    private RegularUserAccountMenuController amc;
    private RegularUserTradingMenuController atc;
    private RegularUserMeetingMenuController mmc;
    private RegularUserThresholdController tc;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private RegularUserInstanceGetter instanceGetter;
    private RegularUserIDGetter idGetter;
    private RegularUserDateTimeGetter dateTimeGetter;
    private SystemMessage sm;
    private DisplaySystem ds;
    private FilesReaderWriter rw;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;
    // whether the max transactions per week threshold is reassessed
    private boolean thresholdReassessed;

    /**
     * Constructs a RegularUserController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserController(DisplaySystem ds, FilesReaderWriter rw,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
        this.thresholdReassessed = false;
        // for other controllers / presenters
        this.amc = new RegularUserAccountMenuController(ds, rw, tm, mm, um, username);
        this.atc = new RegularUserTradingMenuController(ds, rw, tm, mm, um, username);
        this.amc = new RegularUserAccountMenuController(ds, rw, tm, mm, um, username);
        this.atc = new RegularUserTradingMenuController(ds, rw, tm, mm, um, username);
        this.mmc = new RegularUserMeetingMenuController(ds, rw, tm, mm, um, username);
        this.tc = new RegularUserThresholdController(ds, rw, tm, mm, um, username);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, rw, tm, mm, um, username);
        this.instanceGetter = new RegularUserInstanceGetter(ds, rw, tm, mm, um, username);
        this.idGetter = new RegularUserIDGetter(ds, rw, tm, mm, um, username);
        this.dateTimeGetter = new RegularUserDateTimeGetter(ds, rw, tm, mm, um, username);
        this.sm = new SystemMessage();
    }

    // TODO: move to a presenter class
    /**
     * This method gathers all the necessary notifications
     * for the regular user.
     *
     * @return Notifications as properly formatted strings.
     * @throws FileNotFoundException In case the file can't be found.
     */
    @Override
    public String alerts() throws IOException {
        //Read this in from file
        //Exception needs to be resolved in main or TradingSystem.
        User regUser = um.findUser(username);
        StringBuilder notification;
        notification = new StringBuilder();
        String filepath = "./src/Alerts/UserAlerts.csv";
        notification.append(rw.readFromMenu(filepath)).append("\n");
        activeAlerts(regUser, notification);
        return notification.toString();
    }

    private void activeAlerts(User regUser, StringBuilder notification) {
        if (!regUser.getIfFrozen()) {
            // this check if for the uncompletedTransactions one
           if (tc.freezeUserOrNot(regUser)){
               ds.printOut("You are frozen because you have exceeded the maximum number of uncompleted transactions limit.");
           }
        }
        notification.append("The answer to you're frozen is ").append(regUser.getIfFrozen()).append("\n");
        notification.append("You have borrowed:").append(regUser.getNumBorrowed()).append("\n");
        notification.append("You have lent:").append(regUser.getNumLent()).append("\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append("\n");
        notification.append("Max number of transactions a week = ").append(User.getMaxNumTransactionsAllowedAWeek()).append("\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(User.getMaxNumTransactionIncomplete()).append("\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(User.getNumLendBeforeBorrow()).append("\n");
        notification.append("Max edits per user for meeting’s date + time = ").append(User.getMaxMeetingDateTimeEdits()).append("\n");
    }

    /**
     * This method calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     *
     * @param mainMenuOption The main menu option chosen by the regular user.
     * @param subMenuOption  The sub menu option for a particular sub menu chosen by the regular user.
     * @throws InvalidIdException In case the id is invalid.
     *
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) throws InvalidIdException {
        User thisUser = um.findUser(userId);
        switch (mainMenuOption) {
            case 1:
                userAccountMenuResponse(subMenuOption);
                break;
            case 2:
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                    userTradingMenuResponse(subMenuOption);
                }
                break;
            case 3:
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                    userMeetingMenuResponse(subMenuOption);
                }
                break;
        }

    }
    private void userAccountMenuResponse(int subMenuOption) throws InvalidIdException {
        /*
        1.Browse all the books in other users inventories
        2.Add to own Wish List
        3.Search item
        4.Remove from own Wish List
        5.Remove from own Inventory
        6.Request to unfreeze account
        7.Request that an item be added to your inventory
        8.See most recent three items traded (add)
        0.Exit menu
         */
        ArrayList<Item> allOtherItems = um.allItems(userId);
        switch (subMenuOption) {
            case 1:
                amc.browseBooks(allOtherItems);
                break;
            case 2:
                amc.addToWishList(allOtherItems);
                break;
            case 3:
                amc.searchItem();
                break;
            case 4:
                amc.removeFromWishList(allOtherItems);
                break;
            case 5:
                amc.removeFromInventory();
                break;
            case 6:
                amc.RequestToUnfreeze();
                break;
            case 7:
                amc.requestAddItem();
                break;
            case 8:
                amc.seeMostRecentThreeItems();
                break;
            case 9:
                amc.viewWishListInventory();
        }
    }


    private void userTradingMenuResponse(int subMenuOption) throws InvalidIdException {
        /*
          1.Request a trade (lend / borrow / two-way)
          2.Respond to trade requests (agree / disagree) - first meeting is set up by system
          3.View open trades
          4.View closed trades
          5.Confirm that a trade has been completed
          6.See top three most frequent trading partners
          7.View transactions that have been cancelled
         */
        User thisUser = um.findUser(userId);
        // reassess it at the first day of the week - only once
        // TODO: small bug - user has to login in other days (non-Sundays) to re-enable this function for next Sunday
        //  and can only reassess it on Sunday (the first day of the week)
        tc.reassessNumTransactionsLeftForTheWeek(thisUser);
        switch (subMenuOption) {
            case 1:
                atc.requestTrade(thisUser);
                break;
            case 2:
                atc.respondToTradeRequests(thisUser);
                break;
            case 3:
                atc.viewOpenTrades(tm.getOpenTrade(userId));
                break;
            case 4:
                atc.viewOpenTrades(tm.getClosedTrade(userId));
                break;
            case 5:
                atc.confirmTradeComplete();
                break;
            case 6:
                atc.seeTopThreePartners();
                break;
            case 7:
                atc.viewOpenTrades(tm.getCancelledTrade(userId));
                break;

        }
    }


    private void userMeetingMenuResponse(int subMenuOption) throws InvalidIdException {
       /*
    1.Suggest/edit time and place for meetings
    2.Confirm time and place for meetings
    3.Confirm the meeting took place
    4.See the list of meetings need to be confirmed (that it took place)
    5.See the list of meetings that have been confirmed (that have taken place)
    6.See the list of meetings with time and place that need to be confirmed
        */
        switch (subMenuOption) {
            case 1:
                mmc.EditMeetingTandP();
                break;
            case 2:
                mmc.confirmMeetingTandP();
                break;
            case 3:
                mmc.confirmMeetingTookPlace();
                break;
            case 4:
                mmc.seeMeetingsToBeConfirmed(mm.getUnConfirmMeeting(userId), "that needs to be confirmed");
                break;
            case 5:
                mmc.seeMeetingsToBeConfirmed(mm.getCompleteMeeting(userId), " that have been confirmed");
                break;
            case 6:
                // See the list of meetings that has not yet been confirmed for time and place
                mmc.unconfirmedTandPMeetings();
                break;

        }

    }

}

