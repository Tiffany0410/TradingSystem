package controllers.regularusersubcontrollers;
import gui.GUIDemo;
import managers.feedbackmanager.FeedbackManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import managers.itemmanager.ItemManager;
import presenter.SystemMessage;
import managers.itemmanager.Category;
import java.util.ArrayList;
import java.util.List;
import managers.itemmanager.Item;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;


/**
 * Regular user searching controller
 * @author Yuanze Bao
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserSearchingMenuController {

    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private FeedbackManager fm;
    private int userId;

    /** Constructor
     * @param tm trade manager
     * @param mm meeting manager
     * @param um user manager
     * @param im item manager
     * @param username user name
     */
    public RegularUserSearchingMenuController(TradeManager tm, MeetingManager mm,
                                              UserManager um, ItemManager im, FeedbackManager fm,String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.fm = fm;
        this.userId = this.um.usernameToID(username);

    }

    /**
     * @return a list of sorted tradeable user
     */
    public List<TradableUser> sortRating(){
        return um.sortRating(fm);
    }

    /** Recent three users traded with this user
     * print a list of user id (3) that traded with this user
     */
    public List<Integer> recentThreePartner(){
            return tm.recentThreePartners(userId);
    }


    /** sort all traded partner
     * @return a list of user id that sorted in order
     */
    public List<Integer> sortAllTradedPartner(){
        return tm.allPartners(userId);}

    /** filter complete trade
     * @return  a list of trade that status is complete
     */
    public List<managers.trademanager.Trade> filterCompleteTrade(){
        return tm.filterHistory(userId);}


    /** filter incomplete trade
     * @return  a list of trade that status is incomplete
     */
    public List<managers.trademanager.Trade> filterIncompleteTrade(){
        List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
        List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
        filter1.addAll(filter2);
        return filter1;
    }

    /** Sort all meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> allMeetingSortByDate() {
        return mm.sortByDate(mm.getMeetingsByUserId(userId));
    }

    /** Sort all incomplete meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> unCompleteMeetingSortByDate(){
        return mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));}

    /** Sort all complete meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> completeMeetingSortByDate() {
        return mm.sortByDate(mm.getCompleteMeeting(userId));
    }


    /** Filter a list of integer that category by user input
     * @param category category
     * @return a list of integer
     */
    public ArrayList<Integer> filterByCategory(String category) {
        Category ca = Category.valueOf(category);
        return  im.getCategoryItem(ca);
    }

    /** helper list  for gui
     * @return the list of category that user can input
     */
    public String listCategory(){
        return "Here is a list of category you can type:" + "\n" +
                "appliances, clothing, electronics, furniture, beauty, "+ "\n" +
                "jewellery, books, supplies, toys, others";
    }


    /** Search item by item name
     * @param name item name
     * @return a list of integer
     */
    public ArrayList<Integer> searchItemByName(String name) {
        return im.searchItem(name);
    }

    /** Get an item's description by id
     * @param itemId item id
     * @return item's description
     */
    public String getItemById(int itemId){
        Item c = im.getItembyId(itemId);
        return c.getDescription();
    }

    /** Sort items by their follows
     * @return a list of item that sorted by follows
     */
    public ArrayList<Item>  sortItemByFollows(){
        return im.getSortedItemByFollows(um);

    }
}

