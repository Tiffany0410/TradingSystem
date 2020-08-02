package controllers.regularusersubcontrollers;
import gui.GUIDemo;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import managers.itemmanager.ItemManager;
import presenter.SystemMessage;
import exception.InvalidIdException;
import managers.itemmanager.Category;
import java.util.ArrayList;
import java.util.List;
import managers.itemmanager.Item;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class RegularUserSearchingMenuController {
    private SystemMessage sm;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private int userId;

    /** Constructor
     * @param tm trade manager
     * @param mm meeting manager
     * @param um user manager
     * @param im item manager
     * @param sm system message
     * @param username user name
     */
    public RegularUserSearchingMenuController( TradeManager tm, MeetingManager mm,
                                              UserManager um, ItemManager im,
                                               SystemMessage sm, String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.sm = sm;
        this.userId = this.um.usernameToID(username);

    }

    /** Sort user by rating
     * print the list of user by sorted rating
     */
    public void sortRating(){
        List<TradableUser> l = um.sortRating();
        if (l.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(l));
        }
    }

    /** Recent three users traded with this user
     * print a list of user id (3) that traded with this user
     */
    public void recentThreePartner(){
        try{
            List<Integer> filter = tm.recentThreePartners(userId);
            if (filter.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(filter));
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        }
    }


    /** Sort all users that traded with this user by quantity
     * print a list of users in order
     */
    public void sortAllTradedPartner() {
        try {
            List<Integer> filter = tm.allPartners(userId);
            if (filter.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(filter));
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        }
    }

    /** filter this user's complete trade
     * return this user's complete trade
     */
    public void filterCompleteTrade() {
        try {
            List<managers.trademanager.Trade> filter = tm.filterHistory(userId);
            if (filter.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(filter));
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        }
    }
    /** filter this user's incomplete trade
     * return this user's incomplete trade
     */
    public void filterIncompleteTrade() {
        try {
            List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
            List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
            filter1.addAll(filter2);
            if (filter1.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(filter1));
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        }
    }


    /** sort all meetings of this user by date
     * print all meetings of this user by date
     */
    public void allMeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
        if (m.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(m));
        }
    }

    /** sort all incomplete meetings of this user by date
     * print all incomplete meetings of this user by date
     */
    public void unCompleteMeetingSortByDate(){
        try {
            List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));
            if (m.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(m));
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        } catch (Exception e) {
            sm.invalidInput();
        }
    }

    /** sort all complete meetings of this user by date
     * print all complete meetings of this user by date
     */
    public void completeMeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getCompleteMeeting(userId));
        if (m.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(m));
        }
    }


    /** filter user's wanted category
     * @param category user input category
     */
    public void filterByCategory(String category) {
        try {
            Category ca = Category.valueOf(category);
            ArrayList<Integer> c = im.getCategoryItem(ca);
            if (c.size() == 0) {
                sm.msgForNothing();
            } else {
                sm.printResult(new ArrayList<>(c));
            }
        }catch(Exception e){
                sm.invalidInput();
            }
    }

    /** helper list  for gui
     * @return the list of category that user can input
     */
    public String listCategory(){
        return "Here is a list of category you can type:" + "\n" +
                "appliances, clothing, electronics, furniture, beauty, "+ "\n" +
                "jewellery, books, supplies, toys, others";
    }

    /** Search item by name and print it
     * @param name item name
     */
    public void searchItemByName(String name) {
        ArrayList<Integer> c = im.searchItem(name);
        if (c.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(c));
        }
    }

    /** Get an item's description by id
     * @param itemId item id
     */
    public void getItemById(int itemId) {
        try {
            Item c = im.getItembyId(itemId);
            sm.printOut(c.getDescription());
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        } catch (Exception e) {
            sm.invalidInput();
        }
    }

    /** Print sorted item by follows
     *
     */
    public void sortItemByFollows() {
        try {
            ArrayList<Item> c = im.getSortedItemByFollows(um);
            if (c.size() == 0) {
                sm.msgForNothing();
            } else {
                for (Item i : c) {
                    sm.printOut(i.getName() + "\n");
                }
            }
        } catch (InvalidIdException e) {
            sm.printInvalidID();
        } catch (Exception e) {
            sm.invalidInput();
        }
    }
}

