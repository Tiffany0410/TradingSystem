package controllers.maincontrollers;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserMeetingMenuController;
import controllers.regularusersubcontrollers.RegularUserThresholdController;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;
import presenter.DisplaySystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter.
 *
 *  @author Yu Xin Yan
 *  @version IntelliJ IDEA 2020.1
 */
public class RegularUserController implements Controllable {

    private RegularUserAccountMenuController amc;
    private RegularUserTradingMenuController atc;
    private RegularUserMeetingMenuController mmc;
    private RegularUserThresholdController tc;
    private SystemMessage sm;
    private DisplaySystem ds;
    private FilesReaderWriter frw;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, an UserManager, an ItemManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param username The username of the regular user.
     */
    public RegularUserController(DisplaySystem ds, TradeManager tm, MeetingManager mm, UserManager um,
                                 ItemManager im, String username) throws IOException, ClassNotFoundException {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.username = username;
        this.userId = um.usernameToID(username);
        this.frw = new FilesReaderWriter();
        // for other controllers / presenters
        this.amc = new RegularUserAccountMenuController(ds, tm, mm, um, im, username, userId);
        this.atc = new RegularUserTradingMenuController(ds, tm, mm, um, im, username, userId);
        this.mmc = new RegularUserMeetingMenuController(ds, tm, mm, um, username, userId);
        this.tc = new RegularUserThresholdController(ds, tm, mm, um, username, userId);
        this.sm = new SystemMessage();
    }


    /**
     * Calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     *
     * @param mainMenuOption The main menu option chosen by the regular user.
     * @param subMenuOption  The sub menu option for a particular sub menu chosen by the regular user.
     * @throws InvalidIdException In case the id is invalid.
     * @throws FileNotFoundException In case the file cannot be found.
     *
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) throws FileNotFoundException, InvalidIdException {
        switch (mainMenuOption) {
            case 1:
                userAccountMenuResponse(subMenuOption);
                break;
            case 2:
                if (um.getFrozenStatus(userId)){
                    ds.printOut("This menu is locked");}
                else{
                    userTradingMenuResponse(subMenuOption);
                }
                break;
            case 3:
                if (um.getFrozenStatus(userId)){
                    ds.printOut("This menu is locked");}
                else{
                    userMeetingMenuResponse(subMenuOption);
                }
                break;
        }

    }
    private void userAccountMenuResponse(int subMenuOption) throws InvalidIdException {
        /*
        1.Browse all the books in other users inventories (ok)
        2.Add to own Wish List (ok)
        3.Search item (ok)
        4.Remove from own Wish List (ok)
        5.Remove from own Inventory (ok)
        6.Request to unfreeze account (ok)
        7.Request that an item be added to your inventory (ok)
        8.See most recent three items traded (add) (assume ok)
        0.Exit menu
         */
        //TODO: hmm, maybe it needs to be an im method
        // or return only ids and i use im's method
        // to turn them into items
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


    private void userTradingMenuResponse(int subMenuOption) throws InvalidIdException, FileNotFoundException {
        /*
          1.Request a trade (lend / borrow / two-way) (tried)
          2.Respond to trade requests (agree / disagree) - first meeting is set up by system (should be ok)
          3.View open trades (ok)
          4.View closed trades (ok)
          5.Confirm that a trade has been completed (ok)
          6.See top three most frequent trading partners (ok)
          7.View transactions that have been cancelled (ok)
         */
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile("./src/others/ThresholdValues.csv");
        // reassess it at the first day of the week - only once
        // Thing to note: user has to login on other days (non-Sundays) to re-enable this function for next Sunday (***)
        // and can only reassess it on Sunday (the first day of the week)
        tc.reassessNumTransactionsLeftForTheWeek(thresholdValues.get(0));
        switch (subMenuOption) {
            case 1:
                atc.requestTrade();
                break;
            case 2:
                atc.respondToTradeRequests();
                break;
            case 3:
                atc.viewTrades(tm.getOpenTrade(userId));
                break;
            case 4:
                atc.viewTrades(tm.getClosedTrade(userId));
                break;
            case 5:
                atc.confirmTradeComplete();
                break;
            case 6:
                atc.seeTopThreePartners();
                break;
            case 7:
                atc.viewTrades(tm.getCancelledTrade(userId));
                break;

        }
    }


    private void userMeetingMenuResponse(int subMenuOption) throws InvalidIdException, FileNotFoundException {
       /*
    1.Suggest/edit time and place for meetings (tried)
    2.Confirm time and place for meetings (**)
    3.Confirm the meeting took place (**)
    4.See the list of meetings need to be confirmed (that it took place) (ok)
    5.See the list of meetings that have been confirmed (that have taken place) (ok)
    6.See the list of meetings with time and place that need to be confirmed (ok)
        */
        switch (subMenuOption) {
            case 1:
                // show user a list of potential meetings to edit the time and place for
                // and then let him/her edit it
                mmc.EditMeetingTandP();
                break;
            case 2:
                mmc.confirmMeetingTandP();
                break;
            case 3:
                mmc.confirmMeetingTookPlace();
                break;
            case 4:
                mmc.seeMeetings(mm.getUnConfirmMeeting(userId), "that needs to be confirmed");
                break;
            case 5:
                mmc.seeMeetings(mm.getCompleteMeeting(userId), " that have been confirmed");
                break;
            case 6:
                // See the list of meetings that has not yet been confirmed for time and place
                mmc.unconfirmedTandPMeetings();
                break;

        }

    }

}

