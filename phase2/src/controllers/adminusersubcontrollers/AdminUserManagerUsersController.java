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
 * @author Yu Xin Yan, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserManagerUsersController {

    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private int userID;

    /**
     * This is the constructor of adminUserManageUsersController
     * @param um user manager
     * @param im item manager
     * @param am action manager
     * @param username user name of this account
     */
    public AdminUserManagerUsersController(UserManager um, ItemManager im, ActionManager am,
            String username) {
        this.um = um;
        this.im = im;
        this.am = am;
        this.userID = um.usernameToID(username);
    }

    /**
     * Get the list of all unfreeze users
     * @return list of all unfreeze users
     */
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


    /**
     * Freeze the user input
     * @param regularUsername name of user want to freeze
     * @return string of result of this operation
     */
    public String freezeUser(String regularUsername) {
        int regularUserID = um.usernameToID(regularUsername);
        boolean freezeOrNot = um.freezeUser(regularUsername);

        // let presenter print the msg of successful or not
        if (freezeOrNot) {
            am.addActionToAllActionsList(this.userID, "adminUser", "1.1", regularUserID, regularUsername);
            return "Freeze success";
        }
        return "Freeze fail";
    }

    /**
     * Get string of user want to unfreeze
     * @return string of user want to unfreeze
     */

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

    /**
     * Unfreeze the user putin
     * @param regularUsername username of the user want to unfreeze
     * @return string of the result of ths operation
     */
    public String unfreezeUser(String regularUsername) {
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


    /**
     * Get the list of the item want to add
     * @return list of items want to add
     */
    public List<Item> seeListItemToAdd(){
        return im.getListItemToAdd();
    }

    /**
     * Add the item
     * @param itemNum number of item
     * @param add add or delete command
     */
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

}
