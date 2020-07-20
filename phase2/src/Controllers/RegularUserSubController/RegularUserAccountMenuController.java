package Controllers.RegularUserSubController;

import Managers.ItemManager.Item;
import Managers.ItemManager.ItemManager;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.User;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;
import Presenter.SystemMessage;
import Exception.InvalidIdException;

import java.util.ArrayList;
import java.util.List;

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
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserAccountMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, an UserManager, an ItemManager,
     * the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserAccountMenuController(DisplaySystem ds,
                                            TradeManager tm, MeetingManager mm,
                                            UserManager um, ItemManager im, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.username = username;
        this.userId = userId;
        this.sm = new SystemMessage();
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
    }

    /**
     * Let the presenter print user's wishlist and inventory.
     * @throws InvalidIdException In case the item id provided is not valid.
     */
    public void viewWishListInventory() throws InvalidIdException{
        // get user
        User thisUser = um.findUser(userId);
        // get user's wishlist and inventory
        ArrayList<Integer> wishlistIDs = um.getUserWishlist(userId);
        ArrayList<Item> wishlist = new ArrayList<>();
        ArrayList<Integer> inventoryIDs = um.getUserInventory(userId);
        ArrayList<Item> inventory = new ArrayList<>();
        //TODO:  let im have a method that
        // takes in a list of item ids and returns a list of items
        //get the real item objects for the wishlist
        for (int id: wishlistIDs){
            wishlist.add(im.getItembyId(id));
        }
        // get the real item objects for the inventory
        for (int id: inventoryIDs){
            inventory.add(im.getItembyId(id));
        }
        // print user's wishlist and inventory
        ds.printOut("Your wishlist: ");
        ds.printResult(new ArrayList<>(wishlist));
        ds.printOut("\n");
        ds.printOut("Your inventory: ");
        ds.printResult(new ArrayList<>(inventory));
        ds.printOut("\n");

    }

    /**
     * Let the user manager add the appropriate item id for the item user wants to add to his/her wish list.
     * @param allOtherItems The potential items user can add to his/her wish list.
     */
    public void addToWishList(ArrayList<Item> allOtherItems) {
        // add the id to user's wishlist
        if (allOtherItems.size() != 0) {
            ds.printResult(um.addItemWishlist(idGetter.getItemID(allOtherItems, 1), username));
        }
        else{
            sm.msgForNothing("that can be added to your wishlist for now", ds);
        }

    }

    /**
     * Receives the request to add item to his/her inventory from the user
     * and let the user manager handle it.
     */
    public void requestAddItem() {
        im.requestAddItem(otherInfoGetter.getItemName(), otherInfoGetter.getMessage("Enter the description of the item"), userId);
        ds.printResult("Your add-item request", true);
    }

    /**
     * Receives the request to unfreeze from the user
     * and let the user manager handle it.
     */
    public void RequestToUnfreeze() {
        ds.printOut("Please note that the admin may only unfreeze you if you promise to lend more.");
        ds.printResult("Your unfreeze request", um.requestUnfreeze(username, otherInfoGetter.getMessage("Leave your unfreeze request message")));
    }

    /**
     * Let the presenter print user's most recent three items traded.
     * If it doesn't apply to the user, an appropriate message will be
     * printed.
     */
    public void seeMostRecentThreeItems() {
        try {
            List<Item> threeItems = new ArrayList<>();
            List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
            //TODO:  let im have a method that
            // takes in a list of item ids and returns a list of items
            for (int id : recentThreeTradedIds) {
                threeItems.add(im.getItembyId(id));
            }
            if (threeItems.size() != 0) {
                ds.printResult(new ArrayList<>(threeItems));
            } else {
                sm.msgForNothing(ds);
            }
        } catch (InvalidIdException ex){this.ds.printOut("Invalid ID");}
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     * an appropriate message will be printed.
     */
    public void removeFromInventory() {
        ArrayList<Integer> userInventoryIDs = um.getUserInventory(userId);
        //TODO:  let im have a method that
        // takes in a list of item ids and returns a list of items
        ArrayList<Item> userInventory = im.idsToItems(userInventoryIDs);
        if (userInventory.size() != 0) {
            ds.printResult(new ArrayList<>(userInventory));
            ds.printResult(um.removeItemInventory(idGetter.getItemID(userInventory, 1), username));
        }
        else{
            sm.msgForNothing("in your inventory", ds);
        }
    }

    /**
     * Receives user's request to remove item from his/her inventory
     * and let the user manager remove it. If user has nothing to remove,
     * an appropriate message will be printed.
     * @param allOtherItems The list of items that contain user's wishlist items.
     */
    public void removeFromWishList(ArrayList<Item> allOtherItems) {
        // remove the item id from wishlist
        if (um.getUserWishlist(userId).size() != 0) {
            ds.printResult(um.removeItemWishlist(idGetter.getItemID(allOtherItems, 0), username));
        }
        else{
            sm.msgForNothing("in your wish list", ds);
        }
    }

    /**
     * Gets the item name from the user and let the user manager search for it
     * in the system.
     */
    public void searchItem() {
        // print all the items being searched for
        String name = otherInfoGetter.getItemName();
//      TODO: use im's searchItem method
        ArrayList<Item> matchItems = um.searchItem(name);
        if (matchItems.size() == 0){
            sm.msgForNothing("that matches your input", ds);
        }
        else{
            ds.printResult(new ArrayList<>(matchItems));
        }
    }

    /**
     * Let the presenter print the items that should be displayed
     * for the user to browse through.
     * @param allOtherItems The items that will be displayed to the user.
     */
    public void browseBooks(ArrayList<Item> allOtherItems) {
        if (allOtherItems.size() != 0) {
            // print items in all users inventory except this user
            ds.printResult(new ArrayList<>(allOtherItems));
        }
        else{
            sm.msgForNothing(ds);
        }
    }
}
