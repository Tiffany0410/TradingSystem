package bookTradeSystem;

import java.util.ArrayList;
import java.util.List;

public class RegularUserMeetingMenuController {

    private SystemMessage sm;
    private RegularUserIDGetter idGetter;
    private RegularUserDateTimeGetter dateTimeGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserMeetingMenuController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserMeetingMenuController(DisplaySystem ds,
                                            TradeManager tm, MeetingManager mm,
                                            UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.dateTimeGetter = new RegularUserDateTimeGetter();
        this.sm = new SystemMessage();
    }

    protected void seeMeetingsToBeConfirmed(List<Meeting> unConfirmMeeting, String s) {
        if (unConfirmMeeting.size() == 0) {
            sm.msgForNothing(s, ds);
        } else {
            ds.printResult(new ArrayList<>(unConfirmMeeting));
        }
    }

    protected void confirmMeetingTookPlace() throws InvalidIdException {
        if (mm.getMeetingsByUserId(userId).size() == 0) {
            sm.msgForNothing(" that needs to be confirmed", ds);
        } else {
//              "confirmed" means the meeting haven't take place but time and place are confirmed
            ds.printResult(new ArrayList<>(mm.getUnConfirmMeeting(userId)));
            Meeting meeting3 = getMeeting();
            if (meeting3.getTradeId() != 0) {
                ds.printResult(mm.setMeetingConfirm(tm, meeting3, userId));
            } else {
                sm.msgForMeetingDNE(ds);
            }
        }
    }

    protected void confirmMeetingTandP() throws InvalidIdException {
        if (mm.getMeetingsByUserId(userId).size() == 0) {
            sm.msgForNothing(" that needs to be confirmed", ds);
        } else {
            Meeting meeting2 = getMeeting();
            if (meeting2.getTradeId() != 0) {
                ds.printResult(meeting2.setTimePlaceConfirm(userId));
            } else {
                sm.msgForMeetingDNE(ds);
            }
        }
    }

    protected void EditMeetingTandP() throws InvalidIdException {
        if (mm.getMeetingsByUserId(userId).size() == 0) {
            sm.msgForNothing(" here that requires action", ds);
        } else {
            Meeting meeting = getMeeting();
            if (meeting.getTradeId() != 0) {

                List<Integer> list = this.dateTimeGetter.getValidDate(ds);
                String place = otherInfoGetter.getPlace();

                //int year, int month, int day, int hour, int min, int sec
                //call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
                ds.printResult(meeting.setTimePlaceEdit(userId, list.get(0), list.get(1), list.get(2), list.get(3), list.get(4), 0, place));
                // for the edit threshold
                ds.printOut(mm.getEditOverThreshold(tm, meeting));
            } else {
                sm.msgForMeetingDNE(ds);
            }
        }
    }

    protected void unconfirmedTandPMeetings() throws InvalidIdException {
        ds.printResult(new ArrayList<>(mm.getUnConfirmTimePlace(userId, tm)));
    }

    private Meeting getMeeting() throws InvalidIdException {
        unconfirmedTandPMeetings();
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = idGetter.getTradeID();
        int numMeeting = otherInfoGetter.getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }
}
