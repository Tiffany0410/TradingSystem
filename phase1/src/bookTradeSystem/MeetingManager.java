package bookTradeSystem;
import java.util.*;

/**
 * An instance of this class represents the list of meeting
 */
public class MeetingManager implements java.io.Serializable{
    List<Meeting> listMeeting = new ArrayList<>();

    /** set this listMeeting with listMeeting
     * @param listMeeting the list of meeting
     */
    public MeetingManager(List<Meeting> listMeeting){
        this.listMeeting = listMeeting;
    }

    /** get the list of meeting for the MeetingManager
     * @return a list of meeting for the meeting manager
     */
    public List<Meeting> getListMeeting(){
        return listMeeting;
    }

    /** set the list of meeting in the MeetingManager with listMeeting
     * @param listMeeting list of meeting
     */
    public void setListMeeting(List<Meeting> listMeeting){
        this.listMeeting = listMeeting;
    }

    /** get a list of complete meetings for a given id
     * @param userId the id for a user
     * @return a list of meeting that is completed for a given id
     */
    public List<Meeting> getCompleteMeeting(int userId){
        List<Meeting> listCompleteMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && meeting.getMeetingConfirm().
                    get(meeting.getUserId1()) && meeting.getMeetingConfirm().get(meeting.getUserId2())){
                listCompleteMeeting.add(meeting);
            }
        }listCompleteMeeting.sort(Comparator.comparing(Meeting::getTime));
        return listCompleteMeeting;
    }

    /** get a list of not complete meetings for a given user id
     * @param userId the id for a user
     * @return a list of meeting that is not completed for a given id
     */
    public List<Meeting> getUnCompleteMeeting(int userId){
        List<Meeting> listUnCompleteMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && !(meeting.getMeetingConfirm().
                    get(meeting.getUserId1()) && meeting.getMeetingConfirm().get(meeting.getUserId2()))){
                listUnCompleteMeeting.add(meeting);
            }
        }listUnCompleteMeeting.sort(Comparator.comparing(Meeting::getTime));
        return listUnCompleteMeeting;
    }
    /** get a list of meetings that has not been confirmed for the time and place
     * @param userId the id for the user
     * @return the list of meeting that is not confirmed time and place by a given user
     */
    public List<Meeting> getUnConfirmTimePlace(int userId){
        List<Meeting> listUnConfirmMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && !meeting.getTimePlaceConfirm()){
                listUnConfirmMeeting.add(meeting);
            }
        }listUnConfirmMeeting.sort(Comparator.comparing(Meeting::getTime));
        return listUnConfirmMeeting;
    }

    /** get a list of meetings that is confirmed time and place, but has no been confirmed the completeness
     * @param userId the id of the user
     * @return a list of meetings that is confirmed time and place, but has not been confirmed the completeness
     * of the meeting
     */
    public List<Meeting> getUnConfirmMeeting(int userId){
        List<Meeting> listUnConfirmMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && meeting.getTimePlaceConfirm() &&
                    !(meeting.getMeetingConfirm().get(meeting.getUserId1()) && meeting.getMeetingConfirm().
                            get(meeting.getUserId2()))){
                listUnConfirmMeeting.add(meeting);
            }
        }listUnConfirmMeeting.sort(Comparator.comparing(Meeting::getTime));
        return listUnConfirmMeeting;
    }
    /** get a list of meetings for a given trade id
     * @param tradeId the id for a trade
     * @return a list of meetings for a given trade id
     */
    public List<Meeting> getMeetingById(int tradeId){
        List<Meeting> listMeetingById = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if (meeting.getTradeId() == tradeId){
                listMeetingById.add(meeting);
            }
        }listMeetingById.sort(Comparator.comparing(Meeting::getMeetingNum));
        return listMeetingById;
    }

    /** set to confirm the completeness of a meeting
     * @param tradeManager the list of trade
     * @param meeting the meeting for a specific trade
     * @param userId the id for whom is going to confirm te completeness of the meeting
     * @return true iff confirm is successful(the confirm is
     * successful iff the meeting is for the user and the user has not
     * confirmed yet)
     */
    public Boolean setMeetingConfirm(TradeManager tradeManager, Meeting meeting, int userId){
        if (meeting.getMeetingConfirm().get(meeting.getUserId1()) &&meeting.getMeetingConfirm().get(meeting.
                getUserId2())){
            return false;
        }else if(!meeting.getMeetingConfirm().get(meeting.getUserId1())&&!meeting.getMeetingConfirm().
                get(meeting.getUserId2())&& meeting.getTime().before(new Date())){
            meeting.getMeetingConfirm().replace(userId, true);
            return true;
        }else{
            if (meeting.getMeetingConfirm().get(userId) || meeting.getTime().after(new Date())){
                return false;
            }else{
                meeting.getMeetingConfirm().replace(userId, true);
                if(tradeManager.checkInManager(meeting.getTradeId()) &&(tradeManager.getTradeById(meeting.getTradeId())
                        .tradeType.equals("Permanent") || meeting.getMeetingNum() == 2 )){
                    tradeManager.getTradeById(meeting.getTradeId()).closedTrade();
                    return true;
                }else if (tradeManager.checkInManager(meeting.getTradeId())&&tradeManager.getTradeById
                        (meeting.getTradeId()).tradeType.equals("Temporary") && meeting.getMeetingNum()
                        == 1){
                    this.addMeeting(meeting.getTradeId(), meeting.getUserId1(),meeting.getUserId2(), 2,
                            tradeManager);
                    return true;
                }
            }
        }return false;}

    /** get whether or not a trade is go over one month and one day.
     * @return true iff the trade is not complete in one month and a day.
     */
    public Boolean getOverTime(TradeManager tradeManager, Meeting meeting){
        Calendar time1 = Calendar.getInstance();
        time1.setTime(meeting.getTime());
        time1.add(Calendar.MONTH,1);
        time1.add(Calendar.DATE,1);
        Date time2 = time1.getTime();
        return tradeManager.checkInManager(meeting.getTradeId())&& tradeManager.getTradeById(meeting.getTradeId()).
                tradeStatus.equals("Open") && meeting.getMeetingConfirm().get(meeting.getUserId1()) &&
                meeting.getMeetingConfirm().get(meeting.getUserId2())  && time2.before(new Date());
    }

    /** get a list of trades that go over one month and one day.
     * @param tradeManager a list of trade
     * @return a list of trades that have not finished in 1 month and 1 day.
     */
    public List<Meeting> getListOverTime(TradeManager tradeManager){
        List<Meeting> listOverTime = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if (this.getOverTime(tradeManager, meeting)){
                listOverTime.add(meeting);
            }
        }return listOverTime;
    }

    /** add a meeting to the MeetingManager
     * @param tradeId the id for a trade
     * @param userId1 the id for the user1
     * @param userId2 the id for the user2
     * @param meetingNum the order of the meeting for the trade
     * @return true iff the meeting is added to the system
     */
    public Boolean addMeeting(int tradeId, int userId1, int userId2, int meetingNum, TradeManager tradeManager){
        for (Meeting meeting: listMeeting){
            if (meeting.getTradeId() == tradeId && meeting.getMeetingNum() == meetingNum){
                return false;
            }
        }
        Meeting meeting = new Meeting(tradeId, userId1, userId2, meetingNum);
        listMeeting.add(meeting);
        tradeManager.getTradeById(tradeId).openTrade();
        return true;
    }

    /** If a meeting is edited more than 3 times by both users without confirmation, and if it's a first meeting,
     * delete the meeting and the trade with printing the transaction is cancelled. If it's second meeting, only
     * print an empty string
     * @param tradeManager a list of trade
     * @param meeting the meeting for the trade
     * @return a string shows that the transaction is cancelled if the meeting is edited over threshold, otherwise,
     * return a empty string.
     */
    public String getEditOverThreshold(TradeManager tradeManager, Meeting meeting){
        if (!meeting.getTimePlaceConfirm() && meeting.getTimePlaceEdit().size() >= 6 && meeting.getMeetingNum() ==1){
            this.removeMeeting(meeting);
            tradeManager.removeTrade(meeting.getTradeId());
            return "Your transaction with id" + meeting.getTradeId() + "has been cancelled.";
            }else if (!meeting.getTimePlaceConfirm() && meeting.getTimePlaceEdit().size() >= 6 &&
                meeting.getMeetingNum() ==2){
            return "";
        }return "";
        }
    /** remove a meeting from the MeetingManager
     * @param meeting a meeting for a trade
     * @return true iff the meeting is removed from the system.
     */
    public Boolean removeMeeting(Meeting meeting){
        if (listMeeting.contains(meeting)){
            listMeeting.remove(meeting);
            return true;
        }else {
            return false;
        }
    }
    /** override the toString method to describe a list of meeting
     * @return a string show the detailed information about the meetings in the MeetingManager
     */
    public String toString(){
        StringBuilder string1 = new StringBuilder(new String(""));
        for(Meeting meeting: listMeeting){
            string1.append(meeting.toString());
            string1.append(System.lineSeparator());
        }return string1.toString();
    }
}
