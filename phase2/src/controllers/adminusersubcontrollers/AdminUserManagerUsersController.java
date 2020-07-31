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
    private String username;
    private int userID;
    private FilesReaderWriter frw;

    // constructor
    public AdminUserManagerUsersController(DisplaySystem ds, AccountCreator ac, UserManager um, ItemManager im,
                                           ActionManager am, String username) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.userID = um.usernameToID(username);
        this.sm = new SystemMessage();
    }


    public String getAllUnfreezedUser() {
        //TODO: Follow below to modify this method
    }




    public String freezeUser(String regularUsername) {
        //TODO: Follow below to modify this method

        // Print out all the Regular Users' usernames
        ds.printOut("Here are the all Regular Users in the System");
        ds.printListUser(um.getListTradableUser());
        // Asks the admin for the username of the user TO FREEZE
        ds.printOut("Please enter the username of the user to FREEZE");
        // Record the username, userID and if successfully freeze user
        String regularUsername = ds.getUsername();
        int regularUserID = um.usernameToID(regularUsername);
        boolean freezeOrNot = um.freezeUser(regularUsername);
        // let presenter print the msg of successful or not
        ds.printResult(freezeOrNot);
        if (freezeOrNot) {
            am.addActionToAllActionsList(this.userID, "adminUser", "1.1", regularUserID, regularUsername);
        }
    }

    public String getWantUnfreezeUser(){
        String title = "Here's the list of user who request to be unfrozen:";
        StringBuilder body = new StringBuilder();

        int count = 1;
        for (Object o : um.getListUnfreezeRequest()) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                body.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                body.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                body.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return title + body.toString() +"\n" +"Please enter the username of the user to UNFREEZE";
    }



    public String unfreezeUser(String regularUsername) {
      //////////////////////////////This part goes to above:   getWantUnfreezeUser()/////////////////////
      ///////////////////////////// Move related method out from displaySystem//////////////////////////
        //ds.printOut("Here's the list of user who request to be unfrozen:");
        //ds.printResult(new ArrayList<>(um.getListUnfreezeRequest()));

        //asks the admin for the username of the user to UNFREEZE
        //ds.printOut("Please enter the username of the user to UNFREEZE");


      /////////////////////////////////////////////////////////////////////////////////////////////////////

      ////////////////////////////////////// Rest part adjust to return success or not/////////////////////
        // Move related method out from displaySystem, let others pass in regular username when call this method//////////////////////////

        // Record the username, userID and if successfully freeze user
        //String regularUsername = ds.getUsername();

        int regularUserID = um.usernameToID(regularUsername);
        boolean unfreezeOrNot = um.unfreezeUser(regularUsername);
        //let presenter print the msg of successful or not

        if (unfreezeOrNot) {
            am.addActionToAllActionsList(this.userID, "adminUser", "1.2", regularUserID, regularUsername);
            return "Success";
        }else{
            return "Fail";
        }

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
            int itemId = im.getIDFromItem(itemSelected).get(0);
            int itemOwnerId = itemSelected.getOwnerId();
            ds.printResult(um.addItemInventory(itemId, um.idToUsername(itemOwnerId)));
            am.addActionToAllActionsList(this.userID, "adminUser", "1.3", itemId, String.valueOf(itemOwnerId));
        } else {
            ds.printResult(true);
        }
    }


}
