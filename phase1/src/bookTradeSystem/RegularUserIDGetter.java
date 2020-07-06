package bookTradeSystem;

import java.util.ArrayList;
import java.util.Scanner;

public class RegularUserIDGetter {

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserIDGetter with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserIDGetter(DisplaySystem ds, FilesReaderWriter rw,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
    }

    protected int getItemID(ArrayList<Item> potentialItems, int type) {
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
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return itemId;
    }

    // TODO: move to IDGetter class
    //TODO maybe put this somewhere else
    protected ArrayList<Integer> getItemsIDs(ArrayList<Item> allOtherItems) {
        ArrayList<Integer> potentialIds = new ArrayList<>();
        //get the id of all the items in the given arraylist
        for (Item item : allOtherItems) {
            potentialIds.add(item.getItemId());
        }
        return potentialIds;
    }

    // TODO: move to IdGetter class
    //TODO maybe put this somewhere else - best to put it in itemManager
    //TODO MAKE SURE ALL IDS IN RECENT THREE ITEMS METHOD EXIST IN THE ARRAYLIST
    protected Item idToItem(int id) {
        //Get all the items in the system
        ArrayList<Item> allOtherItems = getAllItems();
        //find the item with <id>
        for (Item item : allOtherItems) {
            if (item.getOwnerId() == id) {
                return item;
            }
        }
        return null;
    }

    // TODO: move to IdGetter class - best to put it in itemManager
    protected ArrayList<Item> getAllItems() {
        ArrayList<Item> allOtherItems = um.allItems(userId);
        allOtherItems.addAll(um.findUser(userId).getInventory());
        return allOtherItems;
    }

    // TODO: move to IdGetter class
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

    // move to IdGetter class
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
