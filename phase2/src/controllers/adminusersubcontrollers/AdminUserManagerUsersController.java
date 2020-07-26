package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserManagerUsersController {

    private AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FilesReaderWriter frw;

    // constructor
    public AdminUserManagerUsersController(AccountCreator ac, DisplaySystem ds,
                                           UserManager um, ItemManager im, ActionManager am) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.sm = new SystemMessage();
    }

    public void freezeUser() {
        // Print out all the Regular Users' usernames
        ds.printOut("Here are the all Regular Users in the System");
        ds.printListUser(um.getListTradableUser());
        // Asks the admin for the username of the user TO FREEZE
        ds.printOut("Please enter the username of the user to FREEZE");
        // let presenter print the msg of successful or not
        ds.printResult(um.freezeUser(ds.getUsername()));
    }

    public void unfreezeUser() {
        ds.printOut("Here's the list of user who request to be unfrozen:");
        ds.printResult(new ArrayList<>(um.getListUnfreezeRequest()));
        //asks the admin for the username of the user to UNFREEZE
        ds.printOut("Please enter the username of the user to UNFREEZE");
        //let presenter print the msg of successful or not
        ds.printResult(um.unfreezeUser(ds.getUsername()));
    }

    public void confirmInventoryAdd() {
        ArrayList<Item> listItemToAdd = im.getListItemToAdd();
        int len = listItemToAdd.size();
        responseToToAddListSize(listItemToAdd, len);
    }


    private void responseToToAddListSize(ArrayList<Item> listItemToAdd, int len) {
        if (len != 0) {
            // get the list of item to be added to inventories
            ds.printResult(new ArrayList<>(listItemToAdd));
            Item itemSelected = listItemToAdd.get(otherInfoGetter.getItem(len) - 1);
            addOrNotAdd(itemSelected);
            //either add or not add - need to remove from to-be-added list
            //need a method to remove item from um's getListItemToAdd (***)
            // item id = im.getIDFromItem(itemSelected).get(0)
            im.removeFromListItemToAdd(im.getIDFromItem(itemSelected).get(0));
        }
        else{
            // print systemMessage's there's nothing here method
            sm.msgForNothing(ds);
        }
    }

    private void addOrNotAdd(Item itemSelected) {
        if (otherInfoGetter.getAddOrNot()) {
            //if add
            im.addItemToAllItemsList(itemSelected);
            // item id = im.getIDFromItem(itemSelected).get(0)
            ds.printResult(um.addItemInventory(im.getIDFromItem(itemSelected).get(0), um.idToUsername(itemSelected.getOwnerId())));
        } else {
            ds.printResult(true);
        }
    }

}
