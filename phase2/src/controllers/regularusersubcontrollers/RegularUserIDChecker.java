package controllers.regularusersubcontrollers;

import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An instance of this class represents the id
 * getter for the RegularUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserIDChecker {

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserIDGetter with a DisplaySystem, a TradeManager,
     * a MeetingManager, a UserManager, an ItemManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserIDChecker(DisplaySystem ds, TradeManager tm, MeetingManager mm,
                                UserManager um, ItemManager im, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.username = username;
        this.userId = userId;
    }

    /**
     * Gets the item id from the user. Based on code by Yassine.b from:
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
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
            potentialIds = im.getItemsIDs(potentialItems);
        } else {
            potentialIds = um.getUserWishlist(userId);
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

    protected boolean checkInt(String str){
        /* Based on code by Stephen C from:
         https://stackoverflow.com/questions/47686381/input-validation-for-gui
         */
        Pattern p = Pattern.compile("-?[0-9]+");
        Matcher m = p.matcher(str);
        return m.matches();

    }


    protected boolean checkUserID(int userId){
        return um.checkUser(um.idToUsername(userId));
    }

    /**
     * Asks the user for the trade id. Based on code by Yassine.b from:
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
     * @return A valid trade id input by the user.
     */
    protected int getTradeID() {
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
