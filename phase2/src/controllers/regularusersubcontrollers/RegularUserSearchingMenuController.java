package controllers.regularusersubcontrollers;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;

import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingMenuController {

    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private int userId;

    /** Constructor
     * @param tm trade manager
     * @param mm meeting manager
     * @param um user manager
     * @param im item manager
     * @param username user name
     */
    public RegularUserSearchingMenuController( TradeManager tm, MeetingManager mm, ActionManager am,
                                              UserManager um, ItemManager im, String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.userId = this.um.usernameToID(username);

    }

    public List<TradableUser> sortRating(){
        am.addActionToAllActionsList(userId, "regularUser", "4.2.3", 0, "");
        return um.sortRating();
        // 前面几个我把调用函数删了你自己看着加一下后面都是源代码一代没改
//        if (l.size() == 0) {
//            sm.msgForNothing();
//        } else {
//            sm.printResult(new ArrayList<>(l));
//        }
    }

    /** Recent three users traded with this user
     * print a list of user id (3) that traded with this user
     */
    public List<Integer> recentThreePartner(){
//        try{
            am.addActionToAllActionsList(userId, "regularUser", "4.2.1", 0, "");
            return tm.recentThreePartners(userId);
//            if (filter.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(filter));
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        }
    }


    public List<Integer> sortAllTradedPartner(){
        am.addActionToAllActionsList(userId, "regularUser", "4.2.2", 0, "");
        return tm.allPartners(userId);}
//        try {
//            List<Integer> filter = tm.allPartners(userId);
//            if (filter.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(filter));
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        }
//    }

    public List<managers.trademanager.Trade> filterCompleteTrade(){
        am.addActionToAllActionsList(userId, "regularUser", "4.4.2", 0, "");
        return tm.filterHistory(userId);}
//        try {
//            List<managers.trademanager.Trade> filter = tm.filterHistory(userId);
//            if (filter.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(filter));
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        }
//    }

    public List<managers.trademanager.Trade> filterIncompleteTrade(){
        List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
        List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
        filter1.addAll(filter2);
        am.addActionToAllActionsList(userId, "regularUser", "4.4.1", 0, "");
        return filter1;
//        try {
//            List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
//            List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
//            filter1.addAll(filter2);
//            if (filter1.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(filter1));
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        }
    }

    public List<managers.meetingmanager.Meeting> allMeetingSortByDate() {
        am.addActionToAllActionsList(userId, "regularUser", "4.3.1", 0, "");
        return mm.sortByDate(mm.getMeetingsByUserId(userId));
//        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
//        if (m.size() == 0) {
//            sm.msgForNothing();
//        } else {
//            sm.printResult(new ArrayList<>(m));
//        }
    }

    public List<managers.meetingmanager.Meeting> unCompleteMeetingSortByDate(){
        am.addActionToAllActionsList(userId, "regularUser", "4.3.2", 0, "");
        return mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));}
//        try {
//            List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));
//            if (m.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(m));
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        } catch (Exception e) {
//            sm.invalidInput();
//        }
//    }

    public List<managers.meetingmanager.Meeting> completeMeetingSortByDate() {
        am.addActionToAllActionsList(userId, "regularUser", "4.3.3", 0, "");
        return mm.sortByDate(mm.getCompleteMeeting(userId));
//        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getCompleteMeeting(userId));
//        if (m.size() == 0) {
//            sm.msgForNothing();
//        } else {
//            sm.printResult(new ArrayList<>(m));
//        }
    }


    public ArrayList<Integer> filterByCategory(String category) {
        Category ca = Category.valueOf(category);
        am.addActionToAllActionsList(userId, "regularUser", "4.1.1", 0, category);
        return  im.getCategoryItem(ca);
//        try {
//            Category ca = Category.valueOf(category);
//            ArrayList<Integer> c = im.getCategoryItem(ca);
//            if (c.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                sm.printResult(new ArrayList<>(c));
//            }
//        }catch(Exception e){
//                sm.invalidInput();
//            }
    }

    /** helper list  for gui
     * @return the list of category that user can input
     */
    public String listCategory(){
        return "Here is a list of category you can type:" + "\n" +
                "appliances, clothing, electronics, furniture, beauty, "+ "\n" +
                "jewellery, books, supplies, toys, others";
    }


    public ArrayList<Integer> searchItemByName(String name) {
        am.addActionToAllActionsList(userId, "regularUser", "4.1.2", 0, name);
        return im.searchItem(name);
//        if (c.size() == 0) {
//            sm.msgForNothing();
//        } else {
//            sm.printResult(new ArrayList<>(c));
//        }
    }

    /** Get an item's description by id
     * @param itemId item id
     */
    public String getItemById(int itemId){
        Item c = im.getItembyId(itemId);
        am.addActionToAllActionsList(userId, "regularUser", "4.1.3", itemId, "");
        return c.getDescription();
//        try {
//            Item c = im.getItembyId(itemId);
//            sm.printOut(c.getDescription());
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        } catch (Exception e) {
//            sm.invalidInput();
//        }
    }

    /** Print sorted item by follows
     *
     */
    public ArrayList<Item>  sortItemByFollows(){
        am.addActionToAllActionsList(userId, "regularUser", "4.1.4", 0, "");
        return im.getSortedItemByFollows(um);
//        try {
//            ArrayList<Item> c = im.getSortedItemByFollows(um);
//            if (c.size() == 0) {
//                sm.msgForNothing();
//            } else {
//                for (Item i : c) {
//                    sm.printOut(i.getName() + "\n");
//                }
//            }
//        } catch (InvalidIdException e) {
//            sm.printInvalidID();
//        } catch (Exception e) {
//            sm.invalidInput();
//        }
    }
}

