package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import gateway.FilesReaderWriter;
import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;
import managers.actionmanager.Action;

public class AdminUserHistoricalActionController {

    private AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private String username;
    private Integer userId;
    private FilesReaderWriter frw;

    // Constructor
    public AdminUserHistoricalActionController(AccountCreator ac, DisplaySystem ds, UserManager um, ItemManager im, 
                                               ActionManager am, String username) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.userId = um.usernameToID(username);
        this.sm = new SystemMessage();
        this.otherInfoGetter = new AdminUserOtherInfoGetter(ds, am);
    }


    public void printOutAllHistorialAction() {
        ds.printHistoricalActions(am.getListOfAllActions());
        am.addActionToAllActionsList(userId, "adminUser", "3.1", 0, "");
    }

    public void cancelRevocableAction() {

        ds.printOut("Here are all the Historical Actions which can be cancelled: \n");
        // Print all the Historical Actions which can be cancelled
        ds.printHistoricalActions(am.getListOfAllActions());
        ds.printOut("Please enter the ID of action that you want to cancel: \n");
        // get the number select by adminuser
        int actionID = otherInfoGetter.getActionID();
        // call helper function to cancel different action
        helper_cancelHistoricalAction(otherInfoGetter.getActionID());
        // tell admin user that action has been cancelled
        ds.printOut("\n Success");
        Action temp = am.findActionByID(actionID);
        // delete action from current Revocable Action List in ActionManager
        am.deleteAction(actionID);
        // add action into deleted Revocable Action List in ActionManager
        am.addActionToDeletedRevocableList(temp);
        // add action into All Historical Action List in ActionManager
        am.addActionToAllActionsList(userId, "adminUser", "3.2", actionID, "");
    }

    private void helper_cancelHistoricalAction(int actionID) {
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
    }


    private void helper_cancelAccountMenu(Action targetAction, int subOption){
        switch (subOption) {
            // 1.2: Add to own Wish List
            case 2:
                break;
            // 1.4: Remove from own Wish List
            case 4:
                break;
            // 1.5: Remove from own Inventory
            case 5:
                break;
            // 1.7: Request to add item to your inventory
            case 7:
                break;
            // 1.10: Set your on-vacation status
            case 10:
                break;
            // 1.11: Change tradable status for an inventory item
            case 11:
        }
    }

    private void helper_cancelTradeMenu(Action targetAction, int subOption) {
        switch (subOption) {
            // 2.1: Request a trade
            case 1:
                break;
            // 2.5: Confirm that a trade has been completed
            case 5:
                break;
        }
    }

    private void helper_cancelMeetingMenu(Action targetAction, int subOption) {
        switch (subOption) {
            // 3.2: Confirm time and place for meetings
            case 2:
                break;
            // 3.3: Confirm the meeting took place
            case 3:
                break;
        }
    }
}
