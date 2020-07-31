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
    private RegularUserIDGetter idGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
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
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, im, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
    }

    /**
     * Let the presenter print user's wishlist and inventory.
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the item id provided is not valid.
     */
    public void viewWishListInventory(boolean asGuest) throws InvalidIdException{
        if (!asGuest) {
            // get user
            TradableUser thisTradableUser = um.findUser(userId);
            // get user's wishlist and inventory
            ArrayList<Integer> wishlistIDs = um.getUserWishlist(userId);
            ArrayList<Item> wishlist = im.getItemsByIds(wishlistIDs);
            ArrayList<Integer> inventoryIDs = um.getUserInventory(userId);
            ArrayList<Item> inventory = im.getItemsByIds(inventoryIDs);
            // print user's wishlist and inventory
            ds.printOut("Your wishlist: ");
            ds.printResult(new ArrayList<>(wishlist));
            ds.printOut("\n");
            ds.printOut("Your inventory: ");
            ds.printResult(new ArrayList<>(inventory));
            ds.printOut("\n");
            am.addActionToAllActionsList(userId, "regularUser", "1.9", 0, "");
        }
        else{
            sm.msgForGuest(ds);
        }

    }

    /**
     * Let the user manager add the appropriate item id for the item user wants to add to his/her wish list.
     * @param allOtherItems The potential items user can add to his/her wish list.
     * @param asGuest The determiner of access to this menu option.
     */
    public void addToWishList(ArrayList<Item> allOtherItems, boolean asGuest) {
        if (!asGuest) {
            // add the id to user's wishlist
            if (allOtherItems.size() != 0) {
                int tempItemID = idGetter.getItemID(allOtherItems, 1);
                ds.printResult(um.addItemWishlist(tempItemID, username));
                am.addActionToCurrentRevocableList(userId, "regularUser", "1.2", tempItemID, "");
                am.addActionToAllActionsList(userId, "regularUser", "1.2", tempItemID, "");
            } else {
                sm.msgForNothing("that can be added to your wishlist for now", ds);
            }
        }
        else{
            sm.msgForGuest(ds);
        }

    }

    /**
     * Receives the request to add item to his/her inventory from the user
     * and let the user manager handle it.
     * @param asGuest The determiner of access to this menu option.
     */
    public void requestAddItem(boolean asGuest) throws InvalidIdException {
        if (!asGuest) {
            String tempItemName = otherInfoGetter.getItemName();
            im.requestAddItem(tempItemName, otherInfoGetter.getMessage("Enter the description of the item"), userId, otherInfoGetter.getItemType());
            ds.printResult("Your add-item request", true);
            am.addActionToCurrentRevocableList(userId, "regular","1.7", im.getRequestItemIDByName(tempItemName), "");
            am.addActionToAllActionsList(userId, "regular","1.7", im.getRequestItemIDByName(tempItemName), "");
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    /**
     * Receives the request to unfreeze from the user
     * and let the user manager handle it.
     * @param asGuest The determiner of access to this menu option.
     */
    public void RequestToUnfreeze(boolean asGuest) {
        if (!asGuest) {
            ds.printOut("Please note that the admin may only unfreeze you if you promise to lend more.");
            ds.printResult("Your unfreeze request", um.requestUnfreeze(username, otherInfoGetter.getMessage("Leave your unfreeze request message")));
            am.addActionToAllActionsList(userId, "regularUser", "1.6", 0, "");
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    /**
     * Let the presenter print user's most recent three items traded.
     * If it doesn't apply to the user, an appropriate message will be
     * printed.
     * @param asGuest The determiner of access to this menu option.
     */
    public void seeMostRecentThreeItems(boolean asGuest) {
        if (!asGuest) {
            try {
                List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
                List<Item> threeItems = im.getItemsByIds((ArrayList<Integer>)(recentThreeTradedIds));
                if (threeItems.size() != 0) {
                    ds.printResult(new ArrayList<>(threeItems));
                    am.addActionToAllActionsList(userId, "regularUser", "1.8", 0, "");
                } else {
                    sm.msgForNothing(ds);
                }
            } catch (InvalidIdException ex) {
                this.ds.printOut("Invalid ID");
            }
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     * an appropriate message will be printed.
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the id is not valid.
     */
    public void removeFromInventory(boolean asGuest) throws InvalidIdException {
        if (!asGuest) {
            ArrayList<Integer> userInventoryIDs = um.getUserInventory(userId);
            ArrayList<Item> userInventory = im.getItemsByIds(userInventoryIDs);
            if (userInventory.size() != 0) {
                ds.printResult(new ArrayList<>(userInventory));
                int tempItemID = idGetter.getItemID(userInventory, 1);
                ds.printResult(um.removeItemInventory(tempItemID, username));
                im.addItemToListDeletedItem(im.getItembyId(tempItemID));
                am.addActionToCurrentRevocableList(userId, "regularUser", "1.5", tempItemID, "");
                am.addActionToAllActionsList(userId, "regularUser", "1.5", tempItemID, "");
            } else {
                sm.msgForNothing("in your inventory", ds);
            }
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     * an appropriate message will be printed.
     * @param allOtherItems The list of items that contain user's wishlist items.
     * @param asGuest The determiner of access to this menu option.
     */
    public void removeFromWishList(ArrayList<Item> allOtherItems, boolean asGuest) throws InvalidIdException {
        if (!asGuest) {
            // remove the item id from wishlist
            if (um.getUserWishlist(userId).size() != 0) {
                int tempItemID = idGetter.getItemID(allOtherItems, 0);
                ds.printResult(um.removeItemWishlist(tempItemID, username));
                im.addItemToListDeletedItem(im.getItembyId(tempItemID));
                am.addActionToCurrentRevocableList(userId, "regularUser", "1.4", tempItemID, "");
                am.addActionToAllActionsList(userId, "regularUser", "1.4", tempItemID, "");
            } else {
                sm.msgForNothing("in your wish list", ds);
            }
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    /**
     * Gets the item name from the user and let the user manager search for it
     * in the system.
     * @throws InvalidIdException In case the id is not valid.
     */
    public void searchItem() throws InvalidIdException {
        // print all the items being searched for
        String name = otherInfoGetter.getItemName();
        // get the items in the system that match the name
        ArrayList<Item> matchItems = im.getItemsByIds(im.searchItem(name));
        am.addActionToAllActionsList(userId, "regularUser", "1.3", 0, "name");
        if (matchItems.size() == 0){
            sm.msgForNothing("that matches your input", ds);
        }
        else{
            ds.printResult(new ArrayList<>(matchItems));
        }
    }

    /**
     * Lets the presenter print all the tradable items in the system
     * for the user to browse through.
     * @param allOtherItems The items that will be displayed to the user.
     */
    public void browseItems(ArrayList<Item> allOtherItems) {
        if (allOtherItems.size() != 0) {
            // print all items that are tradable
            ds.printResult(new ArrayList<>(allOtherItems));
            am.addActionToAllActionsList(userId, "regularUser", "1.1", 0, "");
        }
        else{
            sm.msgForNothing(ds);
        }
    }

    /**
     * Receives user's input and set his/her on-vacation status
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the id is not valid.
     */
    public void setOnVacationStatus(boolean asGuest) throws InvalidIdException {
        if (!asGuest) {
            // get user's response and set the status likewise
            if (otherInfoGetter.getNumKindOfResponse("set to true", "set to false") == 1) {
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
        else{
            sm.msgForGuest(ds);
        }

    }

    /**
     * Lets the presenter print the tradable status for each of
     * inventory items of the user and let the user change the
     * tradable status for an item.
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the id is invalid.
     */
    public void setTradableStatusForItem(boolean asGuest) throws InvalidIdException{
        if (!asGuest) {
            //print out user's items
            ArrayList<Item> inventory = im.getItemsByIds(um.getUserInventory(userId));
            ArrayList<Integer> itemIDs = new ArrayList<>();
            ds.printOut("Here's the list of items that you can change the tradable status of: \\n");
            ds.printResult(new ArrayList<>(inventory));
            ds.printOut("Find the item id of the item that you want to change the tradable status of. ");
            //asks user for the item id
            itemIDs.add(idGetter.getItemID(inventory, 1));
            //asks user for what to do with this item
            int optionN = otherInfoGetter.getNumKindOfResponse("set item to tradable", "set item to non-tradable");
            //set the status appropriately / print out a message
            setTradableBasedOnResponse(itemIDs, optionN);
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    private void setTradableBasedOnResponse(ArrayList<Integer> itemIDs, int optionN) throws InvalidIdException {
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
     * @param asGuest The determiner of access to this menu option.
     */
    public void changeUserHC(boolean asGuest) {
        if (!asGuest) {
            String newHC = otherInfoGetter.getHomeCity();
            um.changeHome(userId, newHC);
            ds.printResult(true);
            am.addActionToAllActionsList(userId, "regularUser", "1.13", 0, newHC);
        }
        else{
            sm.msgForGuest(ds);
        }
    }


    /**
     * Lets presenter print the items this user
     * can lend to a given user. The random number
     * part is Based on code by Bill the Lizard from:
     * @link https://stackoverflow.com/questions/363681
     * /how-do-i-generate-random-integers-within-a-specific-range-in-java
     * @param asGuest The determiner of access to this menu option.
     * @throws InvalidIdException In case the id is invalid.
     *
     */
    public void suggestItemToLend(boolean asGuest) throws InvalidIdException {
        if (!asGuest) {
            Random r = new Random();
            //Asks the user for the user id of the user this user wants to lend to
            int lendToUserId = idGetter.getUserID("user you want to lend item(s) to");
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

        } else {
            sm.msgForGuest(ds);
        }
    }

    /**
     * Uses this user's input of the user id of the user
     * to follow to let this user follow the other user
     * in the system.
     * @param userToFollowUserId  User's input of the user id of the user to follow.
     * @return Whether this action succeeded or failed.
     */
    public String followAnUser (int userToFollowUserId){
        //output msg = "please enter the user id of the user you want to follow"
        boolean result = um.userFollow(userId, userToFollowUserId);
        if (result) {
            return "success";
        }
        return "fail";
    }

    /**
     * Uses this user's input of the user id of the item
     * to follow to let this user follow the item
     * in the system.
     * @param itemToFollowId User's input of the item id of the item to follow.
     * @return Whether this action succeeded or failed.
     * @throws InvalidIdException In case if the id this user entered is invalid.
     */
    public String followAnItem ( int itemToFollowId) throws InvalidIdException {
        //output msg = "please enter the user id of the item you want to follow"
        boolean result = um.itemFollow(userId, itemToFollowId);
        if (result) {
            return "success";
        }
        return "fail";


    }

    public ArrayList<String> seeRecentStatusOfFollowedUser () {
        return um.getUserFollowingLogs(userId);
    }

    public ArrayList<String> seeRecentStatusOfFollowedItem () {
        return um.getItemFollowingLogs(userId);
    }



    }


}