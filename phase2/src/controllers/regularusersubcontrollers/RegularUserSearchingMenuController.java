package controllers.regularusersubcontrollers;
import gui.GUIDemo;
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

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

public class RegularUserSearchingMenuController {
    private SystemMessage sm;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private int userId;

    public RegularUserSearchingMenuController( TradeManager tm, MeetingManager mm,
                                              UserManager um, ItemManager im, ActionManager am,
                                               SystemMessage sm, String username) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.sm = sm;
        this.userId = this.um.usernameToID(username);

    }

    public void sortRating(){
        List<TradableUser> l = um.sortRating();
        if (l.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(l));
        }
    }

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



    public void allMeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getMeetingsByUserId(userId));
        if (m.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(m));
        }
    }

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

    public void completeMeetingSortByDate() {
        List<managers.meetingmanager.Meeting> m = mm.sortByDate(mm.getCompleteMeeting(userId));
        if (m.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(m));
        }
    }



    public void filterByCategory(Category category) {
        ArrayList<Integer> c = im.getCategoryItem(category);
        if (c.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(c));
        }
    }

    public void searchItemByName(String name) {
        ArrayList<Integer> c = im.searchItem(name);
        if (c.size() == 0) {
            sm.msgForNothing();
        } else {
            sm.printResult(new ArrayList<>(c));
        }
    }

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

