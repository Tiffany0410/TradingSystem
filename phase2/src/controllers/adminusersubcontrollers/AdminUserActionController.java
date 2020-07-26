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

public class AdminUserActionController {

    private AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FilesReaderWriter frw;

    // Constructor
    public AdminUserActionController(AccountCreator ac, DisplaySystem ds,
                                     UserManager um, ItemManager im, ActionManager am) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.sm = new SystemMessage();
    }}





    public void printOutAllHistorialAction() {

    }

    public void cancelRevocableAction() {
        // Print all the Historical Actions which can be cancelled
        ds.printOut("Here are all the Historical Actions which can be cancelled");
        ds.printHistoricalActions(am.getListOfAllActions());
        helper_cancelHistoricalAction(otherInfoGetter.getActionID());
    }

    private void helper_cancelHistoricalAction(int actionID) {
        Action targetAction = am.findActionByID(actionID);
        String menuOption = targetAction.getMenuOption();
        switch (menuOption) {
            // 1.2: Add to own Wish List
            case "1.2":
                break;
            // 1.4: Remove from own Wish List
            case "1.4":
                break;
            // 1.5: Remove from own Inventory
            case "1.5":
                break;
            // 1.7: Request to add item to your inventory
            case "1.7":
                break;
            // 2.1: Request a trade
            case "2.1":
                break;
            // 2.5: Confirm that a trade has been completed
            case "2.5":
                break;
            // 3.2: Confirm time and place for meetings
            case "3.2":
                break;
            // 3.3: Confirm the meeting took place
            case "3.3":
                break;

        }
}
