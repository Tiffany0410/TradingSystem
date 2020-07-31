package controllers.regularusersubcontrollers;
import managers.actionmanager.ActionManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.Trade;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import managers.itemmanager.ItemManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;
import exception.InvalidIdException;
import managers.itemmanager.Category;
import java.util.ArrayList;
import java.util.List;
import managers.itemmanager.Item;

public class RegularUserSearchingMenuController {
    private SystemMessage sm;
    private presenter.DisplaySystem ds;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private String username;
    private int userId;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private RegularUserIDGetter idGetter;
    public RegularUserSearchingMenuController(DisplaySystem ds, TradeManager tm, MeetingManager mm,
                                              UserManager um, ItemManager im, ActionManager am,
                                              String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.username = username;
        this.userId = userId;
        this.sm = new SystemMessage();
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, im, username, userId);
    }
    public void recentThreePartner() throws InvalidIdException{
        List<Integer> filter = tm.recentThreePartners(userId);
        if (filter.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(filter));
        }
    }

    public void sortAllTradedPartner() throws InvalidIdException {
        List<Integer> filter = tm.allPartners(userId);
        if (filter.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(filter));
        }
    }

    public void filterCompleteTrade() throws InvalidIdException {
        List<managers.trademanager.Trade> filter = tm.filterHistory(userId);
        if (filter.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(filter));
        }
    }

    public void filterIncompleteTrade() throws InvalidIdException {
        List<managers.trademanager.Trade> filter1 = tm.getOpenTrade(userId);
        List<managers.trademanager.Trade> filter2 = tm.getWaitTrade(userId);
        filter1.addAll(filter2);
        if (filter1.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(filter1));
        }
    }


    public void allMeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
        if (m.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(m));
        }
    }
    public void unCompleteMeetingSortByDate() throws InvalidIdException {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getUnCompleteMeeting(userId, tm));
        if (m.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(m));
        }
    }
    public void completeMeetingSortByDate(){
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getCompleteMeeting(userId));
        if (m.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(m));
        }
    }

    public void MeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
        if (m.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(m));
        }
    }
    public void getCompleteMeeting() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getCompleteMeeting(userId));
        if (m.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(m));
        }
    }
    public void filterByCategory(){
        ArrayList<Integer> c = im.getCategoryItem(otherInfoGetter.getItemType());
        if (c.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(c));
        }
    }
    public void searchItemByName() {
        ArrayList<Integer> c = im.searchItem(otherInfoGetter.getItemName());
        if (c.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(c));
        }
    }
    public void getItemById() throws InvalidIdException {
        Item c = im.getItembyId(idGetter.getItemID(im.getAllItem(), 1));
        ds.printOut(c.getDescription());
        }

    public void sortItemByFollows() throws InvalidIdException {
        ArrayList<Item> c = im.getSortedItemByFollows(um);
        if (c.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            for (Item i: c){
                ds.printOut(i.getName());
            }
        }
    }
    }

