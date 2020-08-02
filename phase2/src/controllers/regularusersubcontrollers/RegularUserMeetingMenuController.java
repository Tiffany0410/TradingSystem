package controllers.regularusersubcontrollers;

import exception.InvalidIdException;
import managers.actionmanager.ActionManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.messagemanger.Message;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter, for the meeting menu part.
 *
 * @author Yu Xin Yan, Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserMeetingMenuController {
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserMeetingMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, an ItemManager, the regular user's username and userId.
     *
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param am       The current state of the ActionManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserMeetingMenuController(TradeManager tm, MeetingManager mm, UserManager um,
                                            ItemManager im, ActionManager am, String username, int userId) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.username = username;
        this.userId = userId;
    }


    /**
     * @return the list of meetings that unconfirmed it took place.
     */
    public List<Meeting> getUnconfirmedMeeting(){
        return mm.getUnConfirmMeeting(userId);
    }

    /** check if the list of meeting is empty.
     * @param meetings the meeting of a trade
     * @return true if the list of meeting is empty
     */
    public boolean isEmpty(List<Meeting> meetings){
        return meetings.isEmpty();
    }

    /** check if a meeting is in a meeting manager or not
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @return true if the meeting is in the meeting manager
     */
    public boolean checkValidMeeting(int tradeId, int numMeeting){
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        return mm.checkMeeting(meeting);
    }

    /** confirm the meeting took place
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits the max number for each user to edit time and place
     * @return true if the the meeting is successfully confirmed took place
     * @throws InvalidIdException in case of the id is not valid
     */
    public boolean confirmMeetingTookPlace(int tradeId, int numMeeting, int maxMeetingTimePlaceEdits) throws InvalidIdException {
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        boolean yesOrNO= mm.setMeetingConfirm(tm, meeting, userId, maxMeetingTimePlaceEdits);
        if(yesOrNO) {
            String tradeID3 = String.valueOf(meeting.getTradeId());
            am.addActionToAllActionsList(userId, "regularUser", "3.3", meeting.getMeetingNum(), tradeID3);
            am.addActionToCurrentRevocableList(userId, "regularUser", "3.3", meeting.getMeetingNum(), tradeID3);
            return true;
        }return false;}


    /**
     * @return a list of meeting that the time and place is not confirmed
     * @throws InvalidIdException in case that the id is invalid
     */
    public List<Meeting> getUnConfirmTimePlace() throws InvalidIdException{
        return mm.getUnConfirmTimePlace(userId,tm);
    }

    /**
     * confirm the time and place of a meeting
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits The maximum number of time and place edits allowed.
     * @return true if the confirmation successful
     * @throws InvalidIdException In case if the id is not valid.
     */
    public boolean confirmMeetingTandP(int tradeId, int numMeeting, int maxMeetingTimePlaceEdits) throws InvalidIdException {
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        boolean confirmSuccess = mm.confirmTimePlace(meeting, userId, maxMeetingTimePlaceEdits);
        if (confirmSuccess) {
            String tradeID2 = String.valueOf(meeting.getTradeId());
            am.addActionToAllActionsList(userId, "regularUser", "3.2", meeting.getMeetingNum(), tradeID2);
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
     * @return true if the meeting time and place is edited successfully
     * @throws InvalidIdException In case if the id is not valid.
     */
    public boolean EditMeetingTandP(int tradeId, int numMeeting,  List<Integer> time, String place,int
            maxMeetingTimePlaceEdits) throws InvalidIdException{
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        if (mm.getEditOverThreshold(tm, meeting, maxMeetingTimePlaceEdits).equals("")) {
            boolean editSuccess= mm.editTimePlace(meeting, userId, time.get(0), time.get(1), time.get(2),
                        time.get(3), time.get(4), 0, place, maxMeetingTimePlaceEdits);
            if (editSuccess) {
                int tradeID = meeting.getTradeId();
                String previousTime = time.get(0) + "." + time.get(1) + "." + time.get(2) + "." + time.get(3) + "." + time.get(4);
                String iDAndPreviousTimeAndPlace = userId + "." + meeting.getPlace() + "." + tradeID + "." + previousTime;
                    am.addActionToAllActionsList(userId, "regularUser", "3.1",
                        meeting.getMeetingNum(), iDAndPreviousTimeAndPlace);
                    return true;}}return false; }

    /** check if a meeting is edited too many time or not
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param maxMeetingTimePlaceEdits the max number of times a user can edit the time and place
     * @return a string to describe the edit is over time or not
     * @throws InvalidIdException in case if the id is invalid
     */
    public String checkOverEdit(int tradeId, int numMeeting,int maxMeetingTimePlaceEdits)throws InvalidIdException{
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        return mm.getEditOverThreshold(tm,meeting, maxMeetingTimePlaceEdits);
    }

    /** check if the user's turn to edit time and place.
     * @param tradeId the trade id
     * @param numMeeting the meeting number
     * @param userId the user id
     * @return true if it's this user to edit
     * @throws InvalidIdException in case if the id is invalid
     */
    public boolean checkTurn(int tradeId, int numMeeting, int userId)throws InvalidIdException{
        Meeting meeting = mm.getMeetingByIdNum(tradeId, numMeeting);
        return mm.checkTurn(meeting,userId);
    }

}
