package controllers.regularusersubcontrollers;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;

import java.util.ArrayList;
import java.util.List;


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
    private ActionManager am;
    private int userId;

    /** Constructor
     * @param tm trade manager
     * @param mm meeting manager
     * @param am ActionManager
     * @param um user manager
     * @param im item manager
     * @param username user name
     */
    public RegularUserSearchingMenuController(TradeManager tm, MeetingManager mm, ActionManager am,
                                              UserManager um, ItemManager im, FeedbackManager fm,String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.fm = fm;
        this.am = am;
        this.userId = this.um.usernameToID(username);

    }

    /**
     * @return a list of sorted tradeable user
     */
    public List<TradableUser> sortRating(){
        am.addActionToAllActionsList(userId, "regularUser", "4.2.3", 0, "");
        return um.sortRating(fm);
    }

    /** Recent three users traded with this user
     * print a list of user id (3) that traded with this user
     */
    public List<Integer> recentThreePartner(){
        am.addActionToAllActionsList(userId, "regularUser", "4.2.1", 0, "");
        return tm.recentThreePartners(userId);
    }


    /** sort all traded partner
     * @return a list of user id that sorted in order
     */
    public List<Integer> sortAllTradedPartner(){
        am.addActionToAllActionsList(userId, "regularUser", "4.2.2", 0, "");
        return tm.allPartners(userId);}

    /** filter complete trade
     * @return  a list of trade that status is complete
     */
    public List<managers.trademanager.Trade> filterCompleteTrade(){
        am.addActionToAllActionsList(userId, "regularUser", "4.4.2", 0, "");
        return tm.filterHistory(userId);}


    /** filter incomplete trade
     * @return  a list of trade that status is incomplete
     */
    public List<managers.trademanager.Trade> filterIncompleteTrade(){
        List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
        List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
        filter1.addAll(filter2);
        am.addActionToAllActionsList(userId, "regularUser", "4.4.1", 0, "");

        return filter1;
    }

    /** Sort all meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> allMeetingSortByDate() {
        am.addActionToAllActionsList(userId, "regularUser", "4.3.1", 0, "");
        return mm.sortByDate(mm.getMeetingsByUserId(userId));
    }

    /** Sort all incomplete meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> unCompleteMeetingSortByDate(){
        am.addActionToAllActionsList(userId, "regularUser", "4.3.2", 0, "");
        return mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));}

    /** Sort all complete meetings by date
     * @return a list of meeting
     */
    public List<managers.meetingmanager.Meeting> completeMeetingSortByDate() {
        am.addActionToAllActionsList(userId, "regularUser", "4.3.3", 0, "");
        return mm.sortByDate(mm.getCompleteMeeting(userId));
    }


    /** Filter a list of integer that category by user input
     * @param category category
     * @return a list of integer
     */
    public ArrayList<Item> filterByCategory(Category category) {
        ArrayList<Integer> ids = im.getCategoryItem(category);
        return  im.getItemsByIds(ids);
    }


    /** Search item by item name
     * @param name item name
     * @return a list of integer
     */
    public ArrayList<Item> searchItemByName(String name) {
        am.addActionToAllActionsList(userId, "regularUser", "4.1.2", 0, name);
        ArrayList<Integer> ids =  im.searchItem(name);
        return im.getItemsByIds(ids);
    }

    /** Get an item by id
     * @param itemId item id
     * @return an Item by giving item id
     */
    public Item getItemObjectById(int itemId){
        return im.getItembyId(itemId);
    }


    /** Sort items by their follows
     * @return a list of item that sorted by follows
     */
    public ArrayList<Item>  sortItemByFollows(){
        am.addActionToAllActionsList(userId, "regularUser", "4.1.4", 0, "");
        return im.getSortedItemByFollows(um);
    }
}

