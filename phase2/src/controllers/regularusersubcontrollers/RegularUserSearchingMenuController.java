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
     * @param username user name
     */
    public RegularUserSearchingMenuController( TradeManager tm, MeetingManager mm,
                                              UserManager um, ItemManager im, String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.userId = this.um.usernameToID(username);

    }

    public List<TradableUser> sortRating(){
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
    public List<Integer> recentThreePartner() throws InvalidIdException {
//        try{
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


    public List<Integer> sortAllTradedPartner() throws InvalidIdException {
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

    public List<managers.trademanager.Trade> filterCompleteTrade() throws InvalidIdException {
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

    public List<managers.trademanager.Trade> filterIncompleteTrade() throws InvalidIdException {
        List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
        List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
        filter1.addAll(filter2);
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
        return mm.sortByDate(mm.getMeetingsByUserId(userId));
//        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
//        if (m.size() == 0) {
//            sm.msgForNothing();
//        } else {
//            sm.printResult(new ArrayList<>(m));
//        }
    }

    public List<managers.meetingmanager.Meeting> unCompleteMeetingSortByDate() throws InvalidIdException {
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
    public String getItemById(int itemId) throws InvalidIdException {
        Item c = im.getItembyId(itemId);
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
    public ArrayList<Item>  sortItemByFollows() throws InvalidIdException {
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

