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
    }


    public void printOutAllHistorialAction() {
        ds.printHistoricalActions(am.getListOfAllActions());
        am.addActionToListAllActions(userId, "adminUser", "3.1", 0, "");
    }

    public void cancelRevocableAction() {
        // Print all the Historical Actions which can be cancelled
        ds.printOut("Here are all the Historical Actions which can be cancelled");
        ds.printHistoricalActions(am.getListOfAllActions());
        helper_cancelHistoricalAction(otherInfoGetter.getActionID());
        
        //TODO: interact with admin user,
        // 1)provide am.addActionToListRevocableActions();
        // 2)get the number select by adminuser
        // 3)call method in AdminUserActionController
        // 4)return success if undo the action

        int actionID = 0;
        am.addActionToListAllActions(userId, "adminUser", "3.2", actionID, "");
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
}
