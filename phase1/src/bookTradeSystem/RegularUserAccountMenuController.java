package bookTradeSystem;

import java.util.ArrayList;
import java.util.List;

public class RegularUserAccountMenuController {

    private SystemMessage sm;
    private RegularUserIDGetter idGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserAccountMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserAccountMenuController(DisplaySystem ds,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
        this.sm = new SystemMessage();
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
    }

    protected void viewWishListInventory() {
        // get user
        User thisUser = um.findUser(userId);
        // get user's wishlist and inventory
        ArrayList<Integer> wishlistIDs = thisUser.getWishList();
        ArrayList<Item> wishlist = new ArrayList<>();
        ArrayList<Item> inventory = thisUser.getInventory();
        for (int id: wishlistIDs){
            wishlist.add(idGetter.idToItem(id));
        }
        // print user's wishlist and inventory
        ds.printOut("Your wishlist: ");
        ds.printResult(new ArrayList<>(wishlist));
        ds.printOut("\n");
        ds.printOut("Your inventory: ");
        ds.printResult(new ArrayList<>(inventory));

    }

    protected void addToWishList(ArrayList<Item> allOtherItems) {
        // add the id to user's wishlist
        ds.printResult(um.addItemWishlist(idGetter.getItemID(allOtherItems, 1), username));
    }

    protected void requestAddItem() {
        um.requestAddItem(otherInfoGetter.getItemName(), otherInfoGetter.getMessage("Enter the description of the item"), userId);
        ds.printResult(true);
    }

    protected void RequestToUnfreeze() {
        ds.printResult(um.requestUnfreeze(username, otherInfoGetter.getMessage("Leave your unfreeze request message")));
    }

    protected void seeMostRecentThreeItems() {
        try {
            List<Item> threeItems = new ArrayList<>();
            List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
            for (int id : recentThreeTradedIds) {
                threeItems.add(idGetter.idToItem(id));
            }
            if (threeItems.size() != 0) {
                ds.printResult(new ArrayList<>(threeItems));
            } else {
                sm.msgForNothing(ds);
            }
        } catch (InvalidIdException ex){this.ds.printOut("Invalid ID");}
    }

    protected void removeFromInventory() {
        ArrayList<Item> userInventory = um.findUser(userId).getInventory();
        if (userInventory.size() != 0) {
            ds.printResult(new ArrayList<>(userInventory));
            ds.printResult(um.removeItemInventory(idGetter.getItemID(userInventory, 1), username));
        }
        else{
            sm.msgForNothing(" in your inventory", ds);
        }
    }

    protected void removeFromWishList(ArrayList<Item> allOtherItems) {
        // remove the item id from wishlist
        if (um.findUser(userId).getWishList().size() != 0) {
            ds.printResult(um.removeItemWishlist(idGetter.getItemID(allOtherItems, 0), username));
        }
        else{
            sm.msgForNothing(" in your wish list", ds);
        }
    }

    protected void searchItem() {
        // print all the items being searched for
        String name = otherInfoGetter.getItemName();
        ArrayList<Item> matchItems = um.searchItem(name);
        if (matchItems.size() == 0){
            sm.msgForNothing(" that matches your input", ds);
        }
        else{
            ds.printResult(new ArrayList<>(matchItems));
        }
    }

    protected void browseBooks(ArrayList<Item> allOtherItems) {
        if (allOtherItems.size() != 0) {
            // print items in all users inventory except this user
            ds.printResult(new ArrayList<>(allOtherItems));
        }
        else{
            sm.msgForNothing(ds);
        }
    }
}
