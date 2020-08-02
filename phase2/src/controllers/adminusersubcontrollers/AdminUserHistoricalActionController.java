package controllers.adminusersubcontrollers;

import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the historical actions part.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserHistoricalActionController {

    private AdminUserOtherInfoChecker otherInfoGetter;
    private DisplaySystem ds;
    private UserManager um;
    private TradeManager tm;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private MeetingManager mm;
    private String username;
    private Integer userId;
    private FilesReaderWriter frw;
    private String thresholdValuesFilePath;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param ds The presenter class used to print to screen.
     * @param im The current state of the ItemManager.
     * @param um The current state of the UserManager.
     * @param tm The current state of the TradeManager
     * @param am The current state of the ActionManager.
     * @param fm The current state of the FeedbackManager.
     * @param username The username of the Admin user.
     */
    public AdminUserHistoricalActionController(DisplaySystem ds, UserManager um, ItemManager im, TradeManager tm,
                                               MeetingManager mm,
                                               ActionManager am, FeedbackManager fm, String username) {
        this.ds = ds;
        this.um = um;
        this.im = im;
        this.tm = tm;
        this.am = am;
        this.fm = fm;
        this.mm = mm;
        this.userId = um.usernameToID(username);
        this.otherInfoGetter = new AdminUserOtherInfoChecker(ds, am, um);
        this.frw = new FilesReaderWriter();
        String thresholdValuesFilePath = "./configs/thresholdvaluesfile/ThresholdValues.csv";
    }


    /**
     * Lets the presenter print out all the actions done by RegularUser and AdminUser in the system
     */
    public void printOutAllHistoricalAction() {
        ds.printOut("Here are all the Historical Actions: \n");
        ds.printHistoricalActions(am.getListOfAllActions());
        am.addActionToAllActionsList(userId, "adminUser", "3.1", 0, "");
    }


    /**
     * Lets the presenter print out all the revocable actions done by RegularUser in the system
     * by the RegularUser id provided by AdminUser
     */
    public void searchRevocableActionByUserID() {
        ds.printOut("Here are all the TradableUser Id: \n");
        ds.printListUser(um.getListTradableUser());
        int regularUserID = otherInfoGetter.getRegularUserID();
        am.searchRevocableActionByID(regularUserID);
        am.addActionToAllActionsList(userId, "adminUser", "3.3", regularUserID, "");
    }

    /**
     * Lets the presenter print out all the revocable actions and cancel the revocable actions done by RegularUser
     * in the system
     */
    public void cancelRevocableAction() throws InvalidIdException, FileNotFoundException, ParseException {

        ds.printOut("Here are all the Historical Actions which can be cancelled: \n");
        // Print all the Historical Actions which can be cancelled
        ds.printHistoricalActions(am.getListOfAllActions());
        ds.printOut("Please enter the ID of action that you want to cancel: \n");
        // get the number select by adminUser
        int actionID = otherInfoGetter.getActionID();
        // call helper function to cancel different action and tell admin user that action has been cancelled
        ds.printResult(helper_cancelHistoricalAction(actionID));

        Action temp = am.findActionByID(actionID);
        // delete action from current Revocable Action List in ActionManager
        am.deleteAction(actionID);
        // add action into deleted Revocable Action List in ActionManager
        am.addActionToDeletedRevocableList(temp);
        // add action into All Historical Action List in ActionManager
        am.addActionToAllActionsList(userId, "adminUser", "3.2", actionID, "");
    }

    /**
     * Helper Function used to do the cancel part for revocable actions and classify the different action
     * into different helper functions.
     */
    private boolean helper_cancelHistoricalAction(int actionID) throws InvalidIdException, FileNotFoundException, ParseException {
        Action targetAction = am.findActionByID(actionID);
        String[] menuOption = targetAction.getMenuOption().split("\\.");
        int mainOption = Integer.parseInt(menuOption[0]);
        int subOption = Integer.parseInt(menuOption[1]);
        switch (mainOption) {
            // call helper function to cancel the Revocable Action in RegularUserAccountMainMenu.csv
            case 1:
                helper_cancelAccountMenu(targetAction, subOption);
                break;
            // call helper function to cancel the Revocable Action in RegularUserTradingMenu.csv
            case 2:
                helper_cancelTradeMenu(targetAction, subOption);
                break;
            // call helper function to cancel the Revocable Action in RegularUserMeetingMenu.csv
            case 3:
                helper_cancelMeetingMenu(targetAction, subOption);
                break;
            // call helper function to cancel the Revocable Action in RegularUserSearchingMenu.csv
            case 4:
                helper_cancelSearchingMenu(targetAction, subOption);
                break;
            // call helper function to cancel the Revocable Action in RegularUserCommunityMenu.csv
            case 5:
                helper_cancelCommunityMenu(targetAction, subOption);
                break;
        }
        return false;
    }

    /**
     * Helper Function used to do the cancel part for revocable actions in Account Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Account Menu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelAccountMenu(Action targetAction, int subOption) throws InvalidIdException {
        // get the id of item from action
        int itemID = targetAction.getAdjustableInt();
        // get the status of item from action for action 1.11
        String tradable = targetAction.getAdjustableStr();
        // get the status of on-vacation status from action for action 1.10
        String vacationStatus = targetAction.getAdjustableStr();
        // get the action owner id from the action
        int actionOwnerID = targetAction.getActionOwnerID();
        // translate userID into username
        String username = um.idToUsername(actionOwnerID);

        switch (subOption) {
            // 1.2: Add to own Wish List
            case 2:
                // call UserManager to remove the item from Wish List
                return um.removeItemWishlist(itemID, username);
            // 1.4: Remove from own Wish List
            case 4:
                // call UserManager to add the item into Wish List
                return um.addItemWishlist(itemID, username);
            // 1.5: Remove from own Inventory
            case 5:
                // call UserManager to remove the item from Inventory
                return um.addItemInventory(itemID, username);
            // 1.7: Request to add item to your inventory
            case 7:
                return im.removeFromListItemToAdd(itemID);
            // 1.10: Set your on-vacation status
            case 10:
                // if user set own on-vacation status into "go on vacation", then change it into "come from vacation"
                if (vacationStatus.equals("go on vacation")) {
                    um.comeFromVacation(actionOwnerID);
                    im.setTradable(um.getUserInventory(actionOwnerID), true);
                }
                // if user set own on-vacation status into "come from vacation", then change it into "go on vacation"
                else {
                    um.goOnVacation(actionOwnerID);
                    im.setTradable(um.getUserInventory(actionOwnerID), false);
                }
                return true;
            // 1.11: Change tradable status for an inventory item
            case 11:
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(itemID);
                // if user set item status into tradable, then change it into non-tradable
                // if user set item status into non-tradable, then change it into tradable
                im.setTradable(temp, !tradable.equals("tradable"));
                return true;
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Trading Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param  subOption The menu option number in Trading Menu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelTradeMenu(Action targetAction, int subOption) throws InvalidIdException {
        switch (subOption) {
            // TODO:2.1: Request a trade
            case 1:
                tm.removeTrade(targetAction.getAdjustableInt());
                break;
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Meeting Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Meeting Menu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelMeetingMenu(Action targetAction, int subOption) throws FileNotFoundException, ParseException {
        switch (subOption) {
            // TODO:3.1: Suggest/edit time and place for meetings
            case 1:
                String[] temp = targetAction.getAdjustableStr().split("\\.");
                int targetUserID = Integer.parseInt(temp[0]);
                String place = temp[1];
                int tradeID = Integer.parseInt(temp[2]);
                //get year, month, day, hour, min, sec
                int year = Integer.parseInt(temp[3]);
                int month = Integer.parseInt(temp[4]);
                int day = Integer.parseInt(temp[5]);
                int hour = Integer.parseInt(temp[6]);
                int minute = Integer.parseInt(temp[7]);
                Meeting meeting = mm.getMeetingByIdNum(tradeID, targetAction.getAdjustableInt());
                return mm.undoEditTimePlace(meeting, targetUserID, year, month, day, hour, minute, 0, place);
            // TODO:3.2: Confirm time and place for meetings
            case 2:
                int tradeID2 = Integer.parseInt(targetAction.getAdjustableStr());
                return mm.undoConfirmTandP(tradeID2, targetAction.getAdjustableInt());
            // TODO:3.3: Confirm the meeting took place
            case 3:
                int tradeID3 = Integer.parseInt(targetAction.getAdjustableStr());
                return mm.undoConfirmTookPlace(tradeID3, targetAction.getAdjustableInt(), targetAction.getActionOwnerID());
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Searching Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Searching Menu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelSearchingMenu(Action targetAction, int subOption) {
        switch (subOption) {
            //TODO

        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Community Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Community Menu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelCommunityMenu(Action targetAction, int subOption) {
        // get the id of other user from action for action 5.1/5.2
        int targetUserID = targetAction.getAdjustableInt();
        // get the action owner id from the action
        int actionOwnerID = targetAction.getActionOwnerID();

        switch (subOption) {
            // 5.1: Write a review for an user
            case 1:
                // call FeedbackManager to delete the review for an user
                return fm.deleteReview(targetUserID, actionOwnerID);
            // 5.2: Report an user
            case 2:
                // call FeedbackManager to delete the report for an user
                return fm.deleteReport(targetUserID, actionOwnerID);
            // 5.8: Unfriend a user
            case 8:
                // call UserManager to remove friend
                return um.removeFriend(targetUserID, actionOwnerID);
        }
        return false;
    }
}
