package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;
import java.util.List;

import managers.actionmanager.Action;


public class AdminUserHistoricalActionController {

    private AdminUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private String username;
    private Integer userId;

    // Constructor
    public AdminUserHistoricalActionController(DisplaySystem ds, AccountCreator ac, UserManager um, ItemManager im,
                                               ActionManager am, FeedbackManager fm, String username) {
        this.ds = ds;
        this.um = um;
        this.im = im;
        this.am = am;
        this.fm = fm;
        this.userId = um.usernameToID(username);
        this.otherInfoGetter = new AdminUserOtherInfoGetter(ds, am, um);
    }


    public void printOutAllHistorialAction() {
        ds.printOut("Here are all the Historical Actions: \n");
        ds.printHistoricalActions(am.getListOfAllActions());
        am.addActionToAllActionsList(userId, "adminUser", "3.1", 0, "");
    }

    public void searchRevocableActionByUserID() {
        ds.printOut("Here are all the TradableUser Id: \n");
        ds.printListUser(um.getListTradableUser());
        int regularUserID = otherInfoGetter.getRegularUserID();
        am.searchRevocableActionByID(regularUserID);
        am.addActionToAllActionsList(userId, "adminUser", "3.3", regularUserID, "");
    }

    public void cancelRevocableAction() throws InvalidIdException {

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


    private boolean helper_cancelHistoricalAction(int actionID) throws InvalidIdException {
        Action targetAction = am.findActionByID(actionID);
        String[] menuOption = targetAction.getMenuOption().split("\\.");
        int mainOption = Integer.parseInt(menuOption[0]);
        int subOption = Integer.parseInt(menuOption[1]);
        switch (mainOption) {
            // call helper function to cancel the Revocable Action in RegularUserAccountMenu.csv
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
        }
        return false;
    }


    private boolean helper_cancelAccountMenu(Action targetAction, int subOption) throws InvalidIdException {
        // get the id of item from action
        int itemID = targetAction.getAdjustableInt();
        // get the id of other user from action for action 1.15/1.16
        int targetUserID = targetAction.getAdjustableInt();
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
                    um.comeFromVacation(userId);
                    im.setTradable(um.getUserInventory(userId), true);
                }
                // if user set own on-vacation status into "come from vacation", then change it into "go on vacation"
                else {
                    um.goOnVacation(userId);
                    im.setTradable(um.getUserInventory(userId), false);
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
            // 1.15: Write a review for an user
            case 15:
                // call FeedbackManager to delete the review for an user
                return fm.deleteReview(targetUserID, userId);
            // 1.16: Report an user
            case 16:
                // call FeedbackManager to delete the report for an user
                return fm.deleteReport(targetUserID, userId);
        }
        return false;
    }


    private boolean helper_cancelTradeMenu(Action targetAction, int subOption) {
        switch (subOption) {
            // TODO:2.1: Request a trade
            case 1:
                break;
            // TODO:2.2: Respond to trade requests
            case 2:
                break;
            // TODO:2.5: Confirm that a trade has been completed
            case 5:
                break;
        }
        return false;
    }


    private boolean helper_cancelMeetingMenu(Action targetAction, int subOption) {
        switch (subOption) {
            // TODO:3.1: Suggest/edit time and place for meetings
            case 1:
                break;
            // TODO:3.2: Confirm time and place for meetings
            case 2:
                break;
            // TODO:3.3: Confirm the meeting took place
            case 3:
                break;
        }
        return false;
    }
}
