package controllers.regularusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;

import java.util.Calendar;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user
 * and the use cases, for the meeting menu part.
 *
 * @author Yu Xin Yan, Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserMeetingMenuController {
    private TradeManager tm;
    private MeetingManager mm;
    private ActionManager am;
    private int userId;

    /**
     * Constructs a RegularUserMeetingMenuController with
     * a TradeManager, a MeetingManager, an actionManager, and the regular user's userId.
     *
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param am       The current state of the ActionManager.
     * @param userId   The userid of the regular user.
     */
    public RegularUserMeetingMenuController(TradeManager tm, MeetingManager mm, ActionManager am, int userId) {
        this.tm = tm;
        this.mm = mm;
        this.am = am;
        this.userId = userId;
    }


    /**
     * @return The list of meetings that unconfirmed it took place.
     */
    public List<Meeting> getUnconfirmedMeeting(){
        return mm.getUnConfirmMeeting(userId);
    }

    /**
     * @return The list of meetings that are completed.
     */
    public List<Meeting> getCompletedMeetings(){
        am.addActionToAllActionsList(userId, "regularUser", "3.3", 0, "");
        return mm.getCompleteMeeting(userId);
    }

    /** check if the list of meeting is empty.
     * @param meetings the meeting of a trade
     * @return True if the list of meeting is empty.
     */
    public boolean isEmpty(List<Meeting> meetings){
        return meetings.isEmpty();
    }

    /** check if a meeting is in a meeting manager or not
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @return True if the meeting is in the meeting manager.
     */
    public boolean checkValidMeeting(int tradeId, int numMeeting){
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        return mm.checkMeeting(meeting);
    }

    /** confirm the meeting took place
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits the max number for each user to edit time and place
     * @return True if the the meeting is successfully confirmed took place.
     */
    public boolean confirmMeetingTookPlace(int tradeId, int numMeeting, int maxMeetingTimePlaceEdits)  {
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        boolean yesOrNO= mm.setMeetingConfirm(tm, meeting, userId, maxMeetingTimePlaceEdits);
        if(yesOrNO) {
            String tradeID3 = String.valueOf(meeting.getTradeId());
            am.addActionToAllActionsList(userId, "regularUser", "3.2", meeting.getMeetingNum(), tradeID3);
            am.addActionToCurrentRevocableList(userId, "regularUser", "3.2", meeting.getMeetingNum(), tradeID3);
            return true;
        }return false;}


    /**
     * @return A list of meeting that the time and place is not confirmed.
     */
    public List<Meeting> getUnConfirmTimePlace(){
        return mm.getUnConfirmTimePlace(userId,tm);
    }

    /**
     * confirm the time and place of a meeting
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits The maximum number of time and place edits allowed.
     * @return True if the confirmation successful.
     */
    public boolean confirmMeetingTandP(int tradeId, int numMeeting, int maxMeetingTimePlaceEdits) {
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        boolean confirmSuccess = mm.confirmTimePlace(meeting, userId, maxMeetingTimePlaceEdits);
        if (confirmSuccess) {
            String tradeID2 = String.valueOf(meeting.getTradeId());
            am.addActionToAllActionsList(userId, "regularUser", "3.1", meeting.getMeetingNum(), tradeID2);
            return true;
            }return false;
        }


    /**
     * edit the time and place of the meeting
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param time a list of integers represents the time
     * @param place the place
     * @param maxMeetingTimePlaceEdits The maximum number of time and place edits allowed.
     * @return True if the meeting time and place is edited successfully.
     */
    public boolean editMeetingTandP(int tradeId, int numMeeting,  List<Integer> time, String place,int
            maxMeetingTimePlaceEdits){
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        Calendar time1 = Calendar.getInstance();
        time1.set(time.get(0), time.get(1), time.get(2), time.get(3), time.get(4), 0);
        if (mm.getEditOverThreshold(tm, meeting, maxMeetingTimePlaceEdits).equals("")) {
            boolean editSuccess= mm.editTimePlace(meeting, userId, time.get(0), time.get(1), time.get(2),
                        time.get(3), time.get(4), 0, place, maxMeetingTimePlaceEdits);
            if (editSuccess) {
                int tradeID = meeting.getTradeId();
                //by default, this user confirms the time and place.
                mm.confirmTimePlace(meeting, userId, maxMeetingTimePlaceEdits);
                String previousTime = time.get(0) + "." + time.get(1) + "." + time.get(2) + "." + time.get(3) + "." + time.get(4);
                String iDAndPreviousTimeAndPlace = userId + "." + meeting.getPlace() + "." + tradeID + "." + previousTime;
                    am.addActionToAllActionsList(userId, "regularUser", "3.1",
                        meeting.getMeetingNum(), iDAndPreviousTimeAndPlace);
                    return true;}}return false; }

    /** check if a meeting is edited too many time or not
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits the max number of times a user can edit the time and place
     * @return A string to describe the edit is over time or not.
     */
    public String checkOverEdit(int tradeId, int numMeeting,int maxMeetingTimePlaceEdits){
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        return mm.getEditOverThreshold(tm,meeting, maxMeetingTimePlaceEdits);
    }
}

