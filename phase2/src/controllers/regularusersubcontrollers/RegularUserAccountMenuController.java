package controllers.regularusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;
import exception.InvalidIdException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter, for the account menu part.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserAccountMenuController {

    private SystemMessage sm;
    private RegularUserIDChecker idGetter;
    private RegularUserOtherInfoChecker otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserAccountMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, an UserManager, an ItemManager,
     * an ActionManager, a FeedbackManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param am       The current state of the ActionManager.
     * @param fm       The current state of the FeedbackManager.
     * @param username The username of the regular user.
     * @param userId   The user id of the regular user.
     */
    public RegularUserAccountMenuController(DisplaySystem ds, TradeManager tm, MeetingManager mm, UserManager um,
                                            ItemManager im, ActionManager am, FeedbackManager fm,
                                            String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.fm = fm;
        this.username = username;
        this.userId = userId;
        this.sm = new SystemMessage();
        this.idGetter = new RegularUserIDChecker(ds, tm, mm, um, im, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoChecker(ds, tm, mm, um, username, userId);
    }

    /**
     * Let the presenter print user's wishlist and inventory.
     */
//    public void viewWishListInventory(){
//        //calling this method means user is not a guest
//        // get user
//        TradableUser thisTradableUser = um.findUser(userId);
//        // get user's wishlist and inventory
//        ArrayList<Integer> wishlistIDs = um.getUserWishlist(userId);
//        ArrayList<Item> wishlist = im.getItemsByIds(wishlistIDs);
//        ArrayList<Integer> inventoryIDs = um.getUserInventory(userId);
//        ArrayList<Item> inventory = im.getItemsByIds(inventoryIDs);
//        // print user's wishlist and inventory
//        ds.printOut("Your wishlist: ");
//        ds.printResult(new ArrayList<>(wishlist));
//        ds.printOut("\n");
//        ds.printOut("Your inventory: ");
//        ds.printResult(new ArrayList<>(inventory));
//        ds.printOut("\n");
//        am.addActionToAllActionsList(userId, "regularUser", "1.9", 0, "");
//    }

    public ArrayList<Item> getWishList(){
        TradableUser thisTradableUser = um.findUser(userId);
        ArrayList<Integer> wishlistIDs = um.getUserWishlist(userId);
        ArrayList<Item> wishlist = im.getItemsByIds(wishlistIDs);
        return wishlist;
    }

    public ArrayList<Item> getInventory(){
        TradableUser thisTradableUser = um.findUser(userId);
        ArrayList<Integer> inventoryIDs = um.getUserInventory(userId);
        ArrayList<Item> inventory = im.getItemsByIds(inventoryIDs);
        return inventory;
    }

    public ArrayList<Item> getAllTradableFromOther(){
        return im.allTradableItemsFromOtherUser(userId);
    }

    /**
     * Let the user manager add the appropriate item id for the item user wants to add to his/her wish list.
     */
//    public void addToWishList(int tempItemID) {
//        //calling this method means user is not a guest
//        // add the id to user's wishlist
//        if (im.allTradableItemsFromOtherUser(userId).size() != 0) {
//            //this id must be from the list of tradable items from other user
//            //int tempItemID = idGetter.getItemID(allOtherItems, 1);
//            ds.printResult(um.addItemWishlist(tempItemID, username));
//            am.addActionToCurrentRevocableList(userId, "regularUser", "1.2", tempItemID, "");
//            am.addActionToAllActionsList(userId, "regularUser", "1.2", tempItemID, "");
//        } else {
//            sm.msgForNothing("that can be added to your wishlist for now", ds);
//        }
//    }

    public boolean addToWishList(int tempItemID){
        boolean result = um.addItemWishlist(tempItemID, username);
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.2", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.2", tempItemID, "");
        return result;
    }

    /**
     * Receives the request to add item to his/her inventory from the user
     * and let the user manager handle it.
     */
    public void requestAddItem(String tempItemName) {
        //calling this method means user is not a guest
        im.requestAddItem(tempItemName, otherInfoGetter.getMessage("Enter the description of the item"), userId, otherInfoGetter.getItemType());
        ds.printResult("Your add-item request", true);
        am.addActionToCurrentRevocableList(userId, "regular","1.7", im.getRequestItemIDByName(tempItemName), "");
        am.addActionToAllActionsList(userId, "regular","1.7", im.getRequestItemIDByName(tempItemName), "");
    }

    /**
     * Receives the request to unfreeze from the user
     * and let the user manager handle it.
     */
    public void RequestToUnfreeze(String msg) {
        //calling this method means user is not a guest
        ds.printOut("Please note that the admin may only unfreeze you if you promise to lend more.");
        ds.printResult("Your unfreeze request", um.requestUnfreeze(username, msg));
        am.addActionToAllActionsList(userId, "regularUser", "1.6", 0, "");
    }

    /**
     * Let the presenter print user's most recent three items traded.
     * If it doesn't apply to the user, an appropriate message will be
     * printed.
     */
    public void seeMostRecentThreeItems() {
        //calling this method means user is not a guest
        List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
        List<Item> threeItems = im.getItemsByIds((ArrayList<Integer>)(recentThreeTradedIds));
        if (threeItems.size() != 0) {
            ds.printResult(new ArrayList<>(threeItems));
            am.addActionToAllActionsList(userId, "regularUser", "1.8", 0, "");
        } else {
            sm.msgForNothing();
        }
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     */
//    public void removeFromInventory(int tempItemID)  {
//        //calling this method means user is not a guest
//        ArrayList<Integer> userInventoryIDs = um.getUserInventory(userId);
//        ArrayList<Item> userInventory = im.getItemsByIds(userInventoryIDs);
//        if (userInventory.size() != 0) {
//            ds.printResult(new ArrayList<>(userInventory));
//            // this item id must be in user's inventory
//            //int tempItemID = idGetter.getItemID(userInventory, 1);
//            ds.printResult(um.removeItemInventory(tempItemID, username));
//            im.addItemToListDeletedItem(im.getItembyId(tempItemID));
//            am.addActionToCurrentRevocableList(userId, "regularUser", "1.5", tempItemID, "");
//            am.addActionToAllActionsList(userId, "regularUser", "1.5", tempItemID, "");
//        } else {
//            sm.msgForNothing("in your inventory", ds);
//        }
//    }

    public boolean removeFromWishlist(int tempItemID){
        boolean result = um.removeItemWishlist(tempItemID, username);
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.3", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.3", tempItemID, "");
        return result;
    }

    public boolean removeFromInventory(int tempItemID){
        boolean result = um.removeItemInventory(tempItemID, username);
        im.addItemToListDeletedItem(im.getItembyId(tempItemID));
        am.addActionToCurrentRevocableList(userId, "regularUser", "1.1.4", tempItemID, "");
        am.addActionToAllActionsList(userId, "regularUser", "1.1.4", tempItemID, "");
        return  result;
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     * an appropriate message will be printed.
     */
//    public void removeFromWishList(int tempItemID)  {
//        //calling this method means user is not a guest
//        // remove the item id from wishlist
//        if (um.getUserWishlist(userId).size() != 0) {
//            //this item id must be in user's wishlist
//            //int tempItemID = idGetter.getItemID(...);
//            ds.printResult(um.removeItemWishlist(tempItemID, username));
//            im.addItemToListDeletedItem(im.getItembyId(tempItemID));
//            am.addActionToCurrentRevocableList(userId, "regularUser", "1.4", tempItemID, "");
//            am.addActionToAllActionsList(userId, "regularUser", "1.4", tempItemID, "");
//        } else {
//            sm.msgForNothing("in your wish list", ds);
//        }
//    }



    /**
     * Lets the presenter print all the tradable items in the system
     * for the user to browse through.
     * @param allOtherItems The items that will be displayed to the user.
     */
    public void browseItems(ArrayList<Item> allOtherItems) {
        //calling this method means user can be a guest/non-guest
        if (allOtherItems.size() != 0) {
            // print all items that are tradable
            ds.printResult(new ArrayList<>(allOtherItems));
            am.addActionToAllActionsList(userId, "regularUser", "1.1", 0, "");
        }
        else{
            sm.msgForNothing(ds);
        }
    }

    public ArrayList<Item> getTradables(){
        ArrayList<Item> tradableItems = im.getAllTradableItems();
        return tradableItems;
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
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.10", 0, "go on vacation");
            am.addActionToAllActionsList(userId, "regularUser", "1.10", 0, "go on vacation");
        } else {
            um.comeFromVacation(userId);
            im.setTradable(um.getUserInventory(userId), true);
            am.addActionToCurrentRevocableList(userId, "regularUser", "1.10", 0, "come from vacation");
            am.addActionToAllActionsList(userId, "regularUser", "1.10", 0, "come from vacation");
        }

    }

    /**
     * Lets the presenter print the tradable status for each of
     * inventory items of the user and let the user change the
     * tradable status for an item.
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the id is invalid.
     */
    public void setTradableStatusForItem(boolean asGuest, int itemId, int optionN) {
        //calling this method means user is not a guest
        //print out user's items
        ArrayList<Item> inventory = im.getItemsByIds(um.getUserInventory(userId));
        ArrayList<Integer> itemIDs = new ArrayList<>();
        ds.printOut("Here's the list of items that you can change the tradable status of: \\n");
        ds.printResult(new ArrayList<>(inventory));
        ds.printOut("Find the item id of the item that you want to change the tradable status of. ");
        //asks user for the item id
        //note: the itemId below must be in user's inventory
        itemIDs.add(itemId);
        //asks user for what to do with this item
        //int optionN = otherInfoGetter.getNumKindOfResponse("set item to tradable", "set item to non-tradable");
        //set the status appropriately / print out a message
        setTradableBasedOnResponse(itemIDs, optionN);
    }

    private void setTradableBasedOnResponse(ArrayList<Integer> itemIDs, int optionN) {
        if (optionN == 1){
            if (im.getTradable(itemIDs.get(0))) {
                sm.msgNoNeedToSetTradableStatus(true);
            }
            else{
                im.setTradable(itemIDs, true);
                int itemID = itemIDs.get(0);
                am.addActionToCurrentRevocableList(userId, "regularUser", "1.11", itemID, "tradable");
                am.addActionToAllActionsList(userId, "regularUser", "1.11", itemID, "tradable");
            }
        }
        else{
            if (!im.getTradable(itemIDs.get(0))) {
                sm.msgNoNeedToSetTradableStatus(false);
            }
            else{
                im.setTradable(itemIDs, false);
                int itemID = itemIDs.get(0);
                am.addActionToCurrentRevocableList(userId, "regularUser", "1.11", itemID, "non-tradable");
                am.addActionToAllActionsList(userId, "regularUser", "1.11", itemID, "non-tradable");
            }
        }
    }


    /**
     * Gets user's input of the new home city and then
     * change user's home city.
     */
    public void changeUserHC(String newHC) {
        //calling this method means user is not a guest
        um.changeHome(userId, newHC);
        ds.printResult(true);
        am.addActionToAllActionsList(userId, "regularUser", "1.13", 0, newHC);
    }


    /**
     * Lets presenter print the items this user
     * can lend to a given user. The random number
     * part is Based on code by Bill the Lizard from:
     * @link https://stackoverflow.com/questions/363681
     * /how-do-i-generate-random-integers-within-a-specific-range-in-java
     * @throws InvalidIdException In case the id is invalid.
     *
     */
    public void suggestItemToLend(int lendToUserId) {
        //calling this method means user is not a guest
        Random r = new Random();
        //Asks the user for the user id of the user this user wants to lend to
        //int lendToUserId = idGetter.getUserID("user you want to lend item(s) to");
        //Calls um’s method with the user id of the person who wants to lend(2) and
        // of the person to lend to(1) and the method returns the item that (2) can lend to (1) in return
        ArrayList<Integer> itemsCanLend = um.wantedItems(lendToUserId, userId);
        //use ds to print the list of actual items (converted using im’s method)
        if (itemsCanLend.size() != 0) {
            ds.printOut("Below are suggestions of items you can lend to that user: \\n");
            ds.printResult(new ArrayList<>(im.getItemsByIds(um.wantedItems(lendToUserId, userId))));
            am.addActionToAllActionsList(userId, "regularUser", "1.14", 0, "");
        }
        // If the list is empty -- return an appropriate message + print a random one
        else {
            ds.printOut("No good suggestions available... \\n");
            int range = um.getUserInventory(userId).size() + 1;
            int randInt = r.nextInt(range);
            ds.printOut("Here's a randomly generated one:");
            itemsCanLend.add(um.getUserInventory(userId).get(randInt));
            ds.printResult(new ArrayList<>(im.getItemsByIds(itemsCanLend)));
        }
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
        return um.userFollow(userId, userToFollowUserId);
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
        return um.itemFollow(userId, itemToFollowId);

    }

    public ArrayList<String> seeRecentStatusOfFollowedUser () {
        //calling this method means user is not a guest
        return um.getUserFollowingLogs(userId);
    }

    public ArrayList<String> seeRecentStatusOfFollowedItem () {
        //calling this method means user is not a guest
        return um.getItemFollowingLogs(userId);
    }
    
    }


