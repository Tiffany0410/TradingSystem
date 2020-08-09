package controllers.adminusersubcontrollers;

import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserManagerUsersController {

    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private int userID;

    // constructor
    public AdminUserManagerUsersController( UserManager um, ItemManager im, ActionManager am, SystemMessage sm,
            String username) {
        this.um = um;
        this.im = im;
        this.am = am;
        this.userID = um.usernameToID(username);
    }


    public String getAllUnfreezeUser() {
        String title = "Here's the list of unfrozen users: \n";
        StringBuilder body = new StringBuilder();

        ArrayList<TradableUser> listOfUser = um.getListTradableUser();

        if (listOfUser.size() == 0){
            return "No users can be frozen";
        }

        for (TradableUser user : listOfUser) {
            if (!user.getIfFrozen()) {
                body.append("TradableUser ID#").append(user.getId()).append(" with Username: ").append(user.getUsername()).append("\n");
            }
        }

        return title + body.toString();
    }




    public String freezeUser(String regularUsername) {
        // Print out all the Regular Users' usernames
        //ds.printOut("Here are the all Regular Users in the System");
        //ds.printListUser(um.getListTradableUser());
        // Asks the admin for the username of the user TO FREEZE
        //ds.printOut("Please enter the username of the user to FREEZE");
        // Record the username, userID and if successfully freeze user
        //String regularUsername = ds.getUsername();

        int regularUserID = um.usernameToID(regularUsername);


        boolean freezeOrNot = um.freezeUser(regularUsername);

        // let presenter print the msg of successful or not
        //ds.printResult(freezeOrNot);
        if (freezeOrNot) {
            am.addActionToAllActionsList(this.userID, "adminUser", "1.1", regularUserID, regularUsername);
            return "Freeze success";
        }
        return "Freeze fail";
    }



    public String getWantUnfreezeUser(){
        String title = "Here's the list of user who request to be unfrozen: \n";
        StringBuilder body = new StringBuilder();

        int count = 1;

        if (um.getListUnfreezeRequest().isEmpty()){
            return "There are no user request to unfreeze";
        }

        for (Object o : um.getListUnfreezeRequest()) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                body.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                body.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                body.append("\nRequest message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return title + body.toString();
    }



    public String unfreezeUser(String regularUsername) {
      //////////////////////////////This part goes to above:   getWantUnfreezeUser()/////////////////////
      ///////////////////////////// Move relatead method out from displaySystem//////////////////////////
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
        if (regularUserID == 0){return "No such username, please check again";}

        boolean unfreezeOrNot = um.unfreezeUser(regularUsername);
        //let presenter print the msg of successful or not

        if (unfreezeOrNot) {
            am.addActionToAllActionsList(this.userID, "adminUser", "1.2", regularUserID, regularUsername);
            return "Success";
        }else{
            return "Fail";
        }

    }


    public String getInventoryToAdd(){
        ArrayList<Item> listItemToAdd = im.getListItemToAdd();
        StringBuilder string = new StringBuilder();
        int count = 1;
        for (Object o : listItemToAdd) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". \n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }
        return string.toString();
    }

    public List<Item> seeListItemToAdd(){
        return im.getListItemToAdd();
    }

    public void addItemOrNot(int itemNum, boolean add){
        Item itemSelected = seeListItemToAdd().get(itemNum);
        if (add){
            //first arg = item id, second arg = owner id
            um.addItemInventory(im.getIDFromWaitingItem(itemSelected).get(0), um.idToUsername(im.getIDFromWaitingItem(itemSelected).get(1)));
            im.addItemToAllItemsList(itemSelected);
            am.addActionToAllActionsList(this.userID, "adminUser", "1.3", itemSelected.getItemId(), String.valueOf(itemSelected.getOwnerId()));
        }
        //either add or not add - need to remove from to-be-added list
        im.deleteItemFromListItemToAdd(itemSelected);
    }

/*
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
            sm.msgForNothing();
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
*/

}
