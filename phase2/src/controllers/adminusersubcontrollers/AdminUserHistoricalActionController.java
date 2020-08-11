package controllers.adminusersubcontrollers;

import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the historical actions part.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserHistoricalActionController {

    private UserManager um;
    private TradeManager tm;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private MeetingManager mm;
    private Integer userId;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param im The current state of the ItemManager.
     * @param um The current state of the UserManager.
     * @param tm The current state of the TradeManager
     * @param am The current state of the ActionManager.
     * @param fm The current state of the FeedbackManager.
     * @param username The username of the Admin user.
     */
    public AdminUserHistoricalActionController(UserManager um, ItemManager im, TradeManager tm,
                                               MeetingManager mm, ActionManager am, FeedbackManager fm, String username) {

        this.um = um;
        this.im = im;
        this.tm = tm;
        this.am = am;
        this.fm = fm;
        this.mm = mm;
        this.userId = um.usernameToID(username);
    }

    /**
     * Return a list of All Historical Actions in the system
     * @return an arraylist of All Historical Actions in the system
     */
    public ArrayList<Action> getAllAction() {
        am.addActionToAllActionsList(userId, "adminUser", "3.1", 0, "");
        return am.getListOfAllActions();
    }


    /**
     * Return a list of All Historical Revocable Actions in the system
     *
     * @return an arraylist of All Historical Revocable Actions in the system
     */
    public ArrayList<Action> getAllRevocableAction() {
        am.addActionToAllActionsList(userId, "adminUser", "3.2", 0, "");
        return am.getListOfCurrentRevocableActions();
    }


    /**
     * Return a list of All TradableUser in the system
     *
     * @return an arraylist of All TradableUser in the system
     */
    public ArrayList<TradableUser> getAllTradableUser() {
        return um.getListTradableUser();
    }


    /**
     * Lets the presenter print out all the revocable actions done by RegularUser in the system
     * by the RegularUser id provided by AdminUser
     *
     * @return an Arraylist of all the revocable actions done by RegularUser in the system
     * by the RegularUser id provided by AdminUser
     */
    public ArrayList<Action> searchRevocableActionByUserID(int regularUserID) {
        am.addActionToAllActionsList(userId, "adminUser", "3.3", regularUserID, "");
        return am.searchRevocableActionByID(regularUserID);
    }

    /**
     * Return Action by given action id
     *
     * @return action by given action id
     */
    public Action findActionByID(int actionID) {
        return am.findActionByID(actionID);
    }

    /**
     * Getter for all undo request submitted by RegularUser in system
     *
     * @return The Map of all undo request submitted by RegularUser in system
     */
    public Map<Integer, Integer> getUndoRequest() { return am.getMapOfUndoRequest(); }


    /**
     * Return true if mapOfUndoRequest contains the provided actionID
     *
     * @param actionID The ID of revocable actions
     *
     * @return True if mapOfUndoRequest contains the provided actionID, vice versa
     */
    public boolean checkUndoRequest(int actionID) { return am.checkUndoRequest(actionID); }



    /**
     * Lets the presenter print out all the undo request from regular user and admin user confirm to
     * cancel related revocable actions in the system
     *
     * @param actionID The ID of revocable actions
     *
     * @return true if successfully cancel the target action, vice versa
     */
    public boolean confirmRequestAndCancelAction(int actionID) {
        Action targetAction = am.findActionByID(actionID);
        boolean flag = cancelRevocableAction(targetAction);
        // add action into All Historical Action List in ActionManager
        am.addActionToAllActionsList(userId, "adminUser", "3.5", actionID, "");
        return flag;
    }


    /**
     * Return true if listOfCurrentRevocableActions contains the provided Action
     *
     * @param targetAction The Action need to be checked
     *
     * @return true if listOfCurrentRevocableActions contains the provided Action, vice versa
     */
    public boolean checkRevocable(Action targetAction) {
        return am.checkRevocable(targetAction);
    }


    /**
     * Checks if the User exists
     *
     * @param targetUserUsername The username of the User being searched for
     *
     * @return true if the user exists, vice versa
     */
    public boolean checkUser(String targetUserUsername) {
        return um.checkUser(targetUserUsername);
    }

    /**
     * Translate the username to userID
     *
     * @param targetUserUsername The username of the User
     *
     * @return userID
     */
    public int getUserID(String targetUserUsername) {
        return um.usernameToID(targetUserUsername);
    }

    /**
     * Cancel revocable actions and classify the different action into different helper functions.
     *
     * @param targetAction the revocable action which need to undo
     *
     * @return true if successfully cancel target action, vice versa
     */
    public boolean cancelRevocableAction(Action targetAction) {

        int actionID = targetAction.getActionID();
        // delete action from current Revocable Action List in ActionManager (if admin chooses to undo the
        // action in option 4) without going through option 5
        am.deleteUndoRequest(actionID);
        // delete action from current Revocable Action List in ActionManager
        am.deleteAction(actionID);
        // add action into deleted Revocable Action List in ActionManager
        am.addActionToDeletedRevocableList(targetAction);
        // add action into All Historical Action List in ActionManager
        am.addActionToAllActionsList(userId, "adminUser", "3.4", actionID, "");

        String[] menuOption = targetAction.getMenuOption().split("\\.");
        int mainOption = Integer.parseInt(menuOption[0]);
        int subOption = Integer.parseInt(menuOption[1]);
        int subSubOption = 0;
        if (menuOption.length == 3) {subSubOption = Integer.parseInt(menuOption[2]);}
        switch (mainOption) {
            // call helper function to cancel the Revocable Action in RegularUserAccountMainMenu.csv
            case 1:
                return helper_cancelAccountMainMenu(targetAction, subOption, subSubOption);
            // call helper function to cancel the Revocable Action in RegularUserTradingMenu.csv
            case 2:
                return helper_cancelTradeMenu(targetAction, subOption);
            // call helper function to cancel the Revocable Action in RegularUserMeetingMenu.csv
            case 3:
                return helper_cancelMeetingMenu(targetAction, subOption);
            // call helper function to cancel the Revocable Action in RegularUserCommunityMenu.csv
            case 5:
                return helper_cancelCommunityMenu(targetAction, subOption);
        }
        return false;
    }


    /**
     * Cancel revocable actions and classify the different action into different helper functions.
     *
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option in AccountMainMenu
     * @param subSubOption The menu option number in sub menus of AccountMain
     *
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelAccountMainMenu(Action targetAction, int subOption, int subSubOption) {
        switch (subOption) {
            // call helper function to cancel the Revocable Action in RegularUserManageItemMenu.csv
            case 1:
                return helper_cancelManageItemMenu(targetAction, subSubOption);
            // call helper function to cancel the Revocable Action in RegularUserAccountSettingsMenu.csv
            case 2:
                return helper_cancelAccountSettingsMenu(targetAction, subSubOption);
            // call helper function to cancel the Revocable Action in RegularUserFollowMenu.csv
            case 3:
                return helper_cancelFollowMenu(targetAction, subSubOption);
        }
        return false;
    }

    /**
     * Helper Function used to do the cancel part for revocable actions in ManageItemMenu
     *
     * @param targetAction The action which AdminUser want to cancel
     * @param subSubOption The menu option number in ManageItemMenu
     *
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelManageItemMenu(Action targetAction, int subSubOption) {
        // get the status of item from action for action 1.1.8
        String tradable = targetAction.getAdjustableStr();

        switch (subSubOption) {
            // 1.1.2: Add to own Wish List
            case 2:
                // call UserManager to remove the item from Wish List
                return um.removeItemWishlist(targetAction.getAdjustableInt(), um.idToUsername(targetAction.getActionOwnerID()));
            // 1.1.3: Remove from own Wish List
            case 3:
                // call UserManager to add the item into Wish List
                return um.addItemWishlist(targetAction.getAdjustableInt(), um.idToUsername(targetAction.getActionOwnerID()));
            // 1.1.4: Remove from own Inventory
            case 4:
                // call UserManager to remove the item from Inventory
                return um.addItemInventory(targetAction.getAdjustableInt(), um.idToUsername(targetAction.getActionOwnerID()));
            // 1.1.5: Request to add item to your inventory
            case 5:
                // call ItemManager to remove item from inventory request list
                return im.removeFromListItemToAdd(targetAction.getAdjustableInt());
            // 1.1.8: Change tradable status for an inventory item
            case 8:
                // call ItemManager to change tradable status for an inventory item
                ArrayList<Integer> temp = new ArrayList<>();
                temp.add(targetAction.getAdjustableInt());
                // if user set item status into tradable, then change it into non-tradable
                // if user set item status into non-tradable, then change it into tradable
                im.setTradable(temp, !tradable.equals("tradable"));
                return true;
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in AccountSettingsMenu
     *
     * @param targetAction The action which AdminUser want to cancel
     * @param subSubOption The menu option number in AccountSettingsMenu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelAccountSettingsMenu(Action targetAction, int subSubOption) {
        // get the status of on-vacation status from action for action 1.2.2
        String vacationStatus = targetAction.getAdjustableStr();

        // 1.2.2: Set your on-vacation status
        if (subSubOption == 2) {
            // if user set own on-vacation status into "go on vacation", then change it into "come from vacation"
            if (vacationStatus.equals("go on vacation")) {
                um.comeFromVacation(targetAction.getActionOwnerID());
                im.setTradable(um.getUserInventory(targetAction.getActionOwnerID()), true);
            }
            // if user set own on-vacation status into "come from vacation", then change it into "go on vacation"
            else {
                um.goOnVacation(targetAction.getActionOwnerID());
                im.setTradable(um.getUserInventory(targetAction.getActionOwnerID()), false);
            }
            return true;
        }
        return false;
    }

    /**
     * Helper Function used to do the cancel part for revocable actions in FollowMenu
     *
     * @param targetAction The action which AdminUser want to cancel
     * @param subSubOption The menu option number in FollowMenu
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelFollowMenu(Action targetAction, int subSubOption) {
        switch (subSubOption){
            // 1.3.1: Follow an user
            case 1:
                // call UserManager to unfollow an user
                return um.userUnfollow(targetAction.getActionOwnerID(), targetAction.getAdjustableInt());
            // 1.3.2: Follow an item
            case 2:
                // call UserManager to unfollow an item
                return um.itemUnfollow(targetAction.getActionOwnerID(), im.getItembyId(targetAction.getAdjustableInt()));
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Trading Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param  subOption The menu option number in Trading Menu
     *
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelTradeMenu(Action targetAction, int subOption) {
        // 2.1: Request a trade
        if (subOption == 1) {
            // call TradeManger to remove the trade
            if (targetAction.getAdjustableStr().equals(" and succeed")) {
                tm.removeTrade(targetAction.getAdjustableInt());
                return true;
            }
        }
        return false;
    }


    /**
     * Helper Function used to do the cancel part for revocable actions in Meeting Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Meeting Menu
     *
     * @return Return true if cancel action successfully, vice versa
     */
    private boolean helper_cancelMeetingMenu(Action targetAction, int subOption) {
        // 3.2: Confirm the meeting took place
        if (subOption == 2) {// call MeetingManger to unconfirm the meeting took place
            int tradeID = Integer.parseInt(targetAction.getAdjustableStr());
            return mm.undoConfirmTookPlace(tradeID, targetAction.getAdjustableInt(), targetAction.getActionOwnerID());
        }
        return false;
    }

    /**
     * Helper Function used to do the cancel part for revocable actions in Community Menu
     * @param targetAction The action which AdminUser want to cancel
     * @param subOption The menu option number in Community Menu
     *
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
                // call UserManager to add friend
                return um.addFriend(targetUserID, actionOwnerID);
        }
        return false;
    }
}
