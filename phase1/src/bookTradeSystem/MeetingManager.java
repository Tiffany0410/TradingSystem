package bookTradeSystem;
import java.util.*;

/**
 * An instance of this class represents the list of meeting
 */
public class MeetingManager implements java.io.Serializable{
    List<Meeting> listMeeting = new ArrayList<>();

    /**
     * @param listMeeting the list of meeting
     */
    public MeetingManager(List<Meeting> listMeeting){
        this.listMeeting = listMeeting;
    }

    /**
     * @return a list of meeting for the meeting manager
     */
    public List<Meeting> getListMeeting(){
        return listMeeting;
    }

    /**
     * @param listMeeting list of meeting
     */
    public void setListMeeting(List<Meeting> listMeeting){
        this.listMeeting = listMeeting;
    }

    /**
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

    /**
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
    /**
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

    /**
     * @param userId the id of the user
     * @return a list of meeting that is confirmed time and place, but have not confirm the completeness of the meeting
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
    /**
     * @param tradeId the id for a trade
     * @return a list of meeting for a given trade
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

    /**
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
                get(meeting.getUserId2())){
            meeting.getMeetingConfirm().replace(userId, true);
            return true;
        }else{
            if (meeting.getMeetingConfirm().get(userId)){
                return false;
            }else{
                meeting.getMeetingConfirm().replace(userId, true);
                if(tradeManager.getTradeType(meeting.getTradeId()) ==  "permanent" || meeting.getMeetingNum() == 2 ){
                    tradeManager.confirmComplete(meeting.getTradeId());
                    return true;
                }else if (tradeManager.getTradeType(meeting.getTradeId()) ==  "temporary" && meeting.getMeetingNum()
                        == 1){
                    this.addMeeting(meeting.getTradeId(), meeting.getUserId1(),meeting.getUserId2(), 2);
                    return true;
                }
            }
        }return false;}

    /**
     * @return true iff the trade is not complete in one month and a day.
     */
    public Boolean getOverTime(TradeManager tradeManager, Meeting meeting){
        Calendar time1 = Calendar.getInstance();
        time1.setTime(meeting.getTime());
        time1.add(Calendar.MONTH,1);
        time1.add(Calendar.DATE,1);
        Date time2 = time1.getTime();
        return tradeManager.getTradeStatus(meeting.getTradeId()) == "open" && meeting.getMeetingConfirm().
                get(meeting.getUserId1()) && meeting.getMeetingConfirm().get(meeting.getUserId2())  &&
                time2.before(new Date());
    }

    /**
     * @param tradeId the id for a trade
     * @param userId1 the id for the user1
     * @param userId2 the id for the user2
     * @param meetingNum the order of the meeting for the trade
     * @return true iff the meeting is added to the system
     */
    public Boolean addMeeting(int tradeId, int userId1, int userId2, int meetingNum){
        for (Meeting meeting: listMeeting){
            if (meeting.getTradeId() == tradeId && meeting.getMeetingNum() == meetingNum){
                return false;
            }
        }Meeting meeting = new Meeting(tradeId, userId1, userId2, meetingNum);
        listMeeting.add(meeting);
        return true;
    }

    /**
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
}
