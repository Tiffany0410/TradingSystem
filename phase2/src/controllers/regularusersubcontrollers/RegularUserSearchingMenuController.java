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

import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingMenuController {
    private RegularUserThresholdController tc;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private RegularUserIDGetter idGetter;
    private SystemMessage sm;
    private presenter.DisplaySystem ds;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private String username;
    private int userId;

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
        this.tc = new RegularUserThresholdController(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, im, username, userId);
        this.sm = new SystemMessage();
    }

    public void filterCompleteTrade() throws InvalidIdException {
        List<managers.trademanager.Trade> filter = tm.filterHistory(userId);
        if (filter.size() == 0) {
            sm.msgForNothing(ds);
        } else {
            ds.printResult(new ArrayList<>(filter));
        }
    }

    public void meetingSortByDate() {
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

}

