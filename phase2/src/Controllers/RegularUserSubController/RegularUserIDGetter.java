package Controllers.RegularUserSubController;

import Managers.ItemManager.Item;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * An instance of this class represents the id
 * getter for the RegularUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserIDGetter {

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserIDGetter with a DisplaySystem, a TradeManager,
     * a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserIDGetter(DisplaySystem ds, TradeManager tm, MeetingManager mm,
                               UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
    }

    /**
     * Gets the item id from the user.
     * @param potentialItems The list of potential items
     *                       that should contain item with the item id
     *                       input by the user.
     * @param type The type of the item id to get from the user. It could
     *             be from user's wish list or other list of items.
     * @return The valid item id input by the user.
     */
    public int getItemID(ArrayList<Item> potentialItems, int type) {
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        boolean okInput = false;
        // all possible ids the user can pick from
        ArrayList<Integer> potentialIds;
        // depends on the option the user chooses
        if (type == 1) {
            potentialIds = getItemsIDs(potentialItems);
        } else {
            potentialIds = um.findUser(userId).getWishList();
        }
        Scanner sc = new Scanner(System.in);
        int itemId = 0;
        do {
            ds.printOut("Please enter the id of the item: ");
            // if the input is int
            if (sc.hasNextInt()) {
                itemId = sc.nextInt();
                // if the input is valid
                if (potentialIds.contains(itemId)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id for this purpose!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return itemId;
    }


    private ArrayList<Integer> getItemsIDs(ArrayList<Item> allOtherItems) {
        ArrayList<Integer> potentialIds = new ArrayList<>();
        //get the id of all the items in the given arraylist
        for (Item item : allOtherItems) {
            potentialIds.add(item.getItemId());
        }
        return potentialIds;
    }


    /**
     * Gets the actual item object with a given item id.
     * @param id The id of the item.
     * @return The actual item object with the id.
     */
    public Item idToItem(int id) {
        //Get all the items in the system
        ArrayList<Item> allOtherItems = getAllItems();
        //find the item with <id>
        for (Item item : allOtherItems) {
            // compare item id with given id
            if (item.getItemId() == id) {
                return item;
            }
        }
        return null;
    }


    /**
     * Gets all the items in the system.
     * @return All the items in the system.
     */
    public ArrayList<Item> getAllItems() {
        ArrayList<Item> allOtherItems = um.allItems(userId);
        allOtherItems.addAll(um.findUser(userId).getInventory());
        return allOtherItems;
    }

    /**
     * Gets the user id from the user.
     * @param type The type of user the system wants the id from.
     * @return A valid user id for the type of user.
     */
    protected int getUserID(String type){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int userId = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the userId of the " + type + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                userId = sc.nextInt();
                // if the input is valid
                if (um.checkUser(um.idToUsername(userId))) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return userId;
    }

    /**
     * Asks the user for the trade id.
     * @return A valid trade id input by the user.
     */
    protected int getTradeID() {
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int tradeId = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the id of the trade : ");
            // if the input is int
            if (sc.hasNextInt()) {
                tradeId = sc.nextInt();
                // if the trade with this tradeId rests in the tradeManager
                if (tm.checkInManager(tradeId)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return tradeId;
    }
}
