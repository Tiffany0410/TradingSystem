package controllers.regularusersubcontrollers;

import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An instance of this class represents the communication system between the regular user and
 * the use cases for the account menu part.
 *
 * @author Yu Xin Yan, Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserAccountMenuController {

    private TradeManager tm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private SystemMessage sm;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserAccountMenuController with
     * a TradeManager, a MeetingManager, an UserManager, an ItemManager,
     * an ActionManager, and the regular user's username.
     *
     * @param sm       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param am       The current state of the ActionManager.
     * @param username The username of the regular user.
     */
    public RegularUserAccountMenuController(TradeManager tm, UserManager um, ItemManager im, ActionManager am,
                                            SystemMessage sm, String username) {

        this.tm = tm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.sm = sm;
        this.username = username;
        this.userId = um.usernameToID(this.username);
    }



    /**
     * Returns a list of tradable items in the system
     * @return A list of tradable items
     */
    public ArrayList<Item> getTradables(){
        ArrayList<Item> tradableItems = im.getAllTradableItems();
        am.addActionToAllActionsList(userId, "regularUser", "1.1.1", 0, "");
        return tradableItems;
    }

    /**
     * @return the user's id
     */
    public int getUserId(){
        return this.userId;
    }

    /**
     * Returns a list of tradable items from other users in the system
     * @return a list of tradable items from other users
     */
    public ArrayList<Item> getAllTradableFromOtherNotInWishlist(){
        ArrayList<Item> wishlist = getWishList();
        ArrayList<Item> allTradable = im.allTradableItemsFromOtherUser(userId);
        ArrayList<Item> out = new ArrayList<>();
        for (Item item: allTradable){
            if (!wishlist.contains(item)){
                out.add(item);
            }
        }
        return out;
    }

    /**
     * Returns true if the item added to user's wishlist successfully, false otherwise
     * @param tempItemID The item's id
     * @return true if the item added to wishlist successfully
     */
    public boolean addToWishList(int tempItemID){
        boolean result = um.addItemWishlist(tempItemID, username);
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.2", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.2", tempItemID, "");
        return result;
    }

    /**
     * Returns true if the item removed from user's wishlist successfully, false otherwise
     * @param tempItemID The item's id
     * @return true if the item removed from user's wishlist successfully
     */
    public boolean removeFromWishlist(int tempItemID){
        boolean result = um.removeItemWishlist(tempItemID, username);
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.3", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.3", tempItemID, "");
        return result;
    }

    /**
     * Returns true if the item removed from user's inventory successfully, false otherwise
     * @param tempItemID tempItemID The item's id
     * @return true if the item removed from user's inventory successfully
     */
    public boolean removeFromInventory(int tempItemID){
        boolean result = um.removeItemInventory(tempItemID, username);
        im.addItemToListDeletedItem(im.getItembyId(tempItemID));
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.4", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.4", tempItemID, "");
        return  result;
    }

    /**
     * Receives user's request to add item into inventory
     * @param itemName The item's name
     * @param description The item's description
     * @param category The item's category
     */
    public void requestAddItem(String itemName, String description, Category category){
        im.requestAddItem(itemName, description, userId, category);
        am.addActionToCurrentRevocableList(userId, "regular","1.1.5", im.getRequestItemIDByName(itemName), "");
        am.addActionToAllActionsList(userId, "regular","1.1.5", im.getRequestItemIDByName(itemName), "");
    }

    /**
     * Returns a list of three most recent traded items
     * @return A list of items
     */
    public ArrayList<Item> seeMostRecentThreeItems() {
        //calling this method means user is not a guest
        List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
        ArrayList<Item> threeItems = im.getItemsByIds((ArrayList<Integer>)(recentThreeTradedIds));
        am.addActionToAllActionsList(userId, "regularUser", "1.1.6", 0, "");
        return threeItems;
    }

    /**
     * Returns user's wishlist
     * @return a list of items
     */
    public ArrayList<Item> getWishList(){
        ArrayList<Integer> wishlistIDs = um.getUserWishlist(userId);
        return im.getItemsByIds(wishlistIDs);
    }

    /**
     * Returns user's inventory
     * @return a list of items
     */
    public ArrayList<Item> getInventory(){
        ArrayList<Integer> inventoryIDs = um.getUserInventory(userId);
        return im.getItemsByIds(inventoryIDs);
    }

    /**
     * Returns a list of tradable items that are also in user's inventory
     * @return a list of tradable items
     */
    public ArrayList<Item> getTradableItems(){
        ArrayList<Item> inventory = getInventory();
        return im.getTradableItems(inventory);
    }

    /**
     * Returns a list of non-tradable items that are also in user's inventory
     * @return a list of non-tradable items
     */
    public ArrayList<Item> getNotTradableItems(){
        ArrayList<Item> inventory = getInventory();
        return im.getNotTradableItems(inventory);
    }

    /**
     * Receives user's request to set item's tradable status to option
     * @param itemId The item's id
     * @param option The tradable status
     * @return true if tradable status set to option successfully
     */
    public boolean setTradableBasedOnResponse(int itemId, boolean option){
        boolean result;
        if (option){
            result = im.setTradable(im.getItembyId(itemId), true);
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.8", itemId, "tradable");
            am.addActionToAllActionsList(userId, "regularUser", "1.1.8", itemId, "tradable");
        }
        else {
            result = im.setTradable(im.getItembyId(itemId), false);
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.8", itemId, "non-tradable");
            am.addActionToAllActionsList(userId, "regularUser", "1.1.8", itemId, "non-tradable");}
        return result;
    }

    /**
     * Returns a list of suggested items to lend
     * @param lendToUserId The user's id that lend to
     * @return a list of suggested items to lend that are in user lendToUserId's inventory
     */
    public ArrayList<Item> getSuggestItemToLend(int lendToUserId){
        ArrayList<Integer> itemsCanLend = um.wantedItems(lendToUserId, userId);
        ArrayList<Item> suggestItems = im.getItemsByIds(itemsCanLend);
        if (!itemsCanLend.isEmpty()){
            am.addActionToAllActionsList(userId, "regularUser", "1.1.9", 0, "");
        }
        return suggestItems;
    }

    /**
     * Returns a list of random suggested items to lend
     * @param lendToUserId The user's id that lend to
     * @return a list of random suggested items to lend that are in user lendToUserId's inventory
     */
    public ArrayList<Item> getRandomSuggestion(int lendToUserId){
        ArrayList<Integer> itemsCanLend = um.wantedItems(lendToUserId, userId);
        Random r = new Random();
        int range = um.getUserInventory(userId).size() + 1;

        itemsCanLend.add(um.getUserInventory(userId).get(r.nextInt(range)));
        ArrayList<Item> suggestItems = im.getItemsByIds(itemsCanLend);
        am.addActionToAllActionsList(userId, "regularUser", "1.1.9", 0, "");
        return suggestItems;
    }




    /**
     * Receives the request to unfreeze from the user
     * and let the user manager handle it.
     */
    public boolean requestToUnfreeze(String msg) {
        //calling this method means user is not a guest
        am.addActionToAllActionsList(userId, "regularUser", "1.2.1", 0, "");
        return um.requestUnfreeze(username, msg);
    }


    /**
     * Receives user's input and set his/her on-vacation status.
     */
    public void setOnVacationStatus(boolean newStatus) {
        //calling this method means user is not a guest
        // get user's response and set the status likewise
        if (newStatus) {
            um.goOnVacation(userId);
            im.setTradable(um.getUserInventory(userId), false);
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.2.2", 0, "go on vacation");
            am.addActionToAllActionsList(userId, "regularUser", "1.2.2", 0, "go on vacation");
        } else {
            um.comeFromVacation(userId);
            im.setTradable(um.getUserInventory(userId), true);
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.2.2", 0, "come from vacation");
            am.addActionToAllActionsList(userId, "regularUser", "1.2.2", 0, "come from vacation");
        }

    }


    /**
     * Gets user's input of the new home city and then
     * change user's home city.
     */
    public String changeUserHC(String newHC) {
        //calling this method means user is not a guest
        um.changeHome(userId, newHC);

        am.addActionToAllActionsList(userId, "regularUser", "1.2.3", 0, newHC);
        return sm.msgForResult(true);
    }

    /**
     * Uses this user's input of the user id of the user
     * to follow to let this user follow the other user
     * in the system.
     * @param userToFollowUserId  User's input of the user id of the user to follow.
     * @return Whether this action succeeded or failed.
     */
    public boolean followAnUser (int userToFollowUserId){
        //calling this method means user is not a guest
        boolean success = um.userFollow(userId, userToFollowUserId);
        if (success) {
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.3.1", userToFollowUserId, "");
            am.addActionToAllActionsList(userId, "regularUser", "1.3.1", userToFollowUserId, "");
        }
        return success;
    }

    /**
     * Uses this user's input of the user id of the item
     * to follow to let this user follow the item
     * in the system.
     * @param itemToFollowId User's input of the item id of the item to follow.
     * @return Whether this action succeeded or failed.
     */
    public boolean followAnItem (int itemToFollowId){
        //calling this method means user is not a guest
        boolean success = um.itemFollow(userId, im.getItembyId(itemToFollowId));
        if (success) {
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.3.2", itemToFollowId, "");
            am.addActionToAllActionsList(userId, "regularUser", "1.3.2", itemToFollowId, "");
        }
        return success;
    }

    /**
     * Gets a list of recent status of the user(s)
     * this user is following.
     * @return A list of recent status of the user(s) this user is following.
     */
    public ArrayList<String> seeRecentStatusOfFollowedUser () {
        //calling this method means user is not a guest
        am.addActionToAllActionsList(userId, "regularUser", "1.3.3", 0, "");
        return um.getUserFollowingLogs(userId);
    }

    /**
     * Gets a list of recent status of the item(s) this user is following.
     * @return A list of recent status of the items(s) this user is following.
     */
    public ArrayList<String> seeRecentStatusOfFollowedItem () {
        //calling this method means user is not a guest
        am.addActionToAllActionsList(userId, "regularUser", "1.3.4", 0, "");
        return um.getItemFollowingLogs(userId);
    }

    /**
     * Gets a list of revocable actions for this user.
     * @return A list of revocable actions for this user.
     */
    public ArrayList<Action> seeOwnRevocableAction(){
        am.addActionToAllActionsList(userId, "regularUser", "1.2.4", 0, "");
        return am.searchRevocableActionByID(userId);

    }

    /**
     * Uses user's input of the action id to put together
     * and store the request to undo a certain action.
     * @param actionId The action id input by the user.
     */
    public void requestUndoARevocableAction(int actionId){
        am.addActionToAllActionsList(userId, "regularUser", "1.2.5", actionId, "");
        am.addUndoRequest(actionId,userId);
    }


    /**
     * Checks to see if this user is frozen.
     * @return Whether or not this user is frozen.
     */
    public boolean seeIfFrozen(){
        return um.getFrozenStatus(userId);
    }

    /**
     * Checks to see if this user is on vacation.
     * @return Whether or not this user is on vacation.
     */
    public boolean seeIfOnVacation(){
        return um.getInfo(userId, "Vacation") == 1;
    }

}





