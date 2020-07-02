package bookTradeSystem;
import com.sun.deploy.util.StringUtils;

import java.util.*;

/**
 * An instance of this class represents the list of meeting
 */
public class MeetingManager implements java.io.Serializable{
    private List<Meeting> listMeeting;
    private String filePath;

    /** set this listMeeting to an empty list of meeting.
     */
    public MeetingManager(String filePath){
        this.filePath = filePath;
        listMeeting = FilesReaderWriter.readMeetingsFromFile(filePath);
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

    /** search the list of meetings by a given userId
     * @param userId the id of the user
     * @return a list f meeting for a given user id
     */
    public List<Meeting> getMeetingsByUserId(int userId){
        List<Meeting> listMeeting1 = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if (meeting.getUserId1() == userId || meeting.getUserId2() == userId){
                listMeeting1.add(meeting);
            }
        }listMeeting1.sort(Comparator.comparing(Meeting::getTime));
        return listMeeting1;
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
    public List<Meeting> getUnCompleteMeeting(int userId, TradeManager tradeManager){
        List<Meeting> listUnCompleteMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && !(meeting.getMeetingConfirm().
                    get(meeting.getUserId1()) && meeting.getMeetingConfirm().get(meeting.getUserId2()))&& !tradeManager
                    .getTradeById(meeting.getTradeId()).tradeStatus.equals("Cancelled")){
                listUnCompleteMeeting.add(meeting);
            }
        }listUnCompleteMeeting.sort(Comparator.comparing(Meeting::getTime));
        return listUnCompleteMeeting;
    }
    /** get a list of meetings that has not been confirmed for the time and place
     * @param userId the
     *              id for the user
     * @return the list of meeting that is not confirmed time and place by a given user
     */
    public List<Meeting> getUnConfirmTimePlace(int userId, TradeManager tradeManager){
        List<Meeting> listUnConfirmMeeting = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && !meeting.getTimePlaceConfirm()
            &&!tradeManager.getTradeById(meeting.getTradeId()).tradeStatus.equals("Cancelled")){
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
    public List<Meeting> getMeetingByTradeId(int tradeId){
        List<Meeting> listMeetingById = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if (meeting.getTradeId() == tradeId){
                listMeetingById.add(meeting);
            }
        }listMeetingById.sort(Comparator.comparing(Meeting::getMeetingNum));
        return listMeetingById;
    }

    /**check if the meeting is in the MeetingManager or not.
     * @param tradeId  the id of the trade.
     * @param numMeeting the number of the meeting
     * @return true if the meeting is in the MeetingManage
     */
    public Boolean checkInManager(int tradeId, int numMeeting){
        for (Meeting meeting:listMeeting){
            if (meeting.getTradeId() == tradeId && meeting.getMeetingNum() == numMeeting){
                return true;
            }
        }return false;
    }

    /** search a meeting in the MeetingManager by a given tradeId and numMeeting
     * @param tradeId the id of the trade
     * @param numMeeting the number of the meeting for a given trade
     * @return a meeting with the given tradeId and numMeeting
     */
    public Meeting getMeetingByIdNum(int tradeId, int numMeeting){
        for(Meeting meeting: listMeeting){
            if (meeting.getTradeId() == tradeId && meeting.getMeetingNum() == numMeeting){
                return meeting;
            }}return new Meeting(0,0,0,0);
    }

    /** set to confirm the completeness of a meeting, if the meeting is confirmed by both user, and the trade is
     * Permanent or is the second meeting, then close the trade. If the meeting is confirmed by both user, but the
     * trade is temporary and the meeting is the first meeting, then remains the trade open and create the second
     * meeting for the trade which is one month after the first meeting and the same location.
     * @param tradeManager the list of trade
     * @param meeting the meeting for a specific trade
     * @param userId the id for whom is going to confirm te completeness of the meeting
     * @return true iff confirm is successful(the confirm is
     * successful iff the meeting is for the user and the user has not
     * confirmed yet)
     */
    public Boolean setMeetingConfirm(TradeManager tradeManager, Meeting meeting, int userId){
        if (meeting.getTimePlaceConfirm() && meeting.getTime().before(new Date()) &&!meeting.getMeetingConfirm().
                get(userId) ){
            meeting.getMeetingConfirm().replace(userId, true);
            if (meeting.getMeetingConfirm().get(meeting.getUserId1()) &&meeting.getMeetingConfirm().get(meeting.
                    getUserId2())&&(tradeManager.getTradeById(meeting.getTradeId())
                    .tradeType.equals("Permanent") || meeting.getMeetingNum() == 2 )){
                tradeManager.getTradeById(meeting.getTradeId()).closedTrade();
            }else if (meeting.getMeetingConfirm().get(meeting.getUserId1()) &&meeting.getMeetingConfirm().get(meeting.
                    getUserId2())&&(tradeManager.getTradeById(meeting.getTradeId())
                    .tradeType.equals("Temporary")&&meeting.getMeetingNum() == 1)){
                Meeting meeting1 = this.addMeeting(meeting.getTradeId(), meeting.getUserId1(),meeting.getUserId2(),
                        2, tradeManager);
                Calendar time1 = Calendar.getInstance();
                time1.setTime(meeting.getTime());
                meeting1.setTimePlaceEdit(userId,time1.get(Calendar.YEAR),time1.get(Calendar.MONTH)+2,
                        time1.get(Calendar.DAY_OF_MONTH), time1.get(Calendar.HOUR_OF_DAY),time1.get(Calendar.MINUTE),
                        time1.get(Calendar.SECOND),meeting.getPlace());
                if(meeting.getUserId1() != userId){
                    meeting1.setTimePlaceConfirm(meeting.getUserId1());
                }else {meeting1.setTimePlaceConfirm(meeting.getUserId2());}
                meeting1.setTimePlaceEdit(new ArrayList<>());
            }
        }else {
            return false;
        }return true;
        }

    /** check whether or not a meeting is not confirmed by users after one day of the meeting should happen.
     * @return true iff the meeting is not confirmed after one day of the real life meeting time.
     */
    public Boolean getOverTime(Meeting meeting){
        Calendar time1 = Calendar.getInstance();
        time1.setTime(meeting.getTime());
        time1.add(Calendar.DATE,1);
        Date time2 = time1.getTime();
        return !((meeting.getMeetingConfirm().get(meeting.getUserId1()))|| (meeting.getMeetingConfirm().
                get(meeting.getUserId2()))) && meeting.getTimePlaceConfirm() && time2.before(new Date());
    }

    /** get a list of meetings that go over one day for a given user id.
     * @return a list of meetings that have not confirmed after one day of the real life meeting time for a given user
     * id.
     */
    public List<Meeting> getListOverTime(int userId){
        List<Meeting> listOverTime = new ArrayList<>();
        for (Meeting meeting: listMeeting){
            if ((meeting.getUserId1() == userId || meeting.getUserId2() == userId) && this.getOverTime(meeting)){
                listOverTime.add(meeting);
            }
        }return listOverTime;
    }

    /** create and add a meeting to the MeetingManager, once a meeting is created, the trade is open.
     * @param tradeId the id for a trade
     * @param userId1 the id for the user1
     * @param userId2 the id for the user2
     * @param meetingNum the order of the meeting for the trade
     * @return the new created meeting
     */
    public Meeting addMeeting(int tradeId, int userId1, int userId2, int meetingNum, TradeManager tradeManager){
        Meeting meeting1 =new Meeting(tradeId, userId1, userId2, meetingNum);
        listMeeting.add(meeting1);
        FilesReaderWriter.saveMeetingsToFile(listMeeting, filePath);
        tradeManager.getTradeById(tradeId).openTrade();
        return meeting1;
    }

    /** If a meeting is edited more than 3 times by both users without confirmation, and if it's a first meeting,
     * change the trade status to cancelled with returning string that the transaction is cancelled.
     * If it's second meeting, return a string "You have edited too many times". If it is not go over threshold,
     * return an empty string.
     * @param tradeManager a list of trade
     * @param meeting the meeting for the trade
     * @return a string shows that the transaction is cancelled if the meeting is edited over threshold, otherwise,
     * return a empty string.
     */
    public String getEditOverThreshold(TradeManager tradeManager, Meeting meeting){
        if (!meeting.getTimePlaceConfirm() && meeting.getTimePlaceEdit().size() >= User.getMaxMeetingDateTimeEdits() &&
                meeting.getMeetingNum() ==1){
            tradeManager.getTradeById(meeting.getTradeId()).cancelTrade();
            return "Your transaction with id " + meeting.getTradeId() + " has been cancelled.";
            }else if (!meeting.getTimePlaceConfirm() && meeting.getTimePlaceEdit().size() >=
                User.getMaxMeetingDateTimeEdits() && meeting.getMeetingNum() ==2){
            return "You have edited too many times";
        }return "";
        }
    /** remove a meeting from the MeetingManager
     * @param meeting a meeting for a trade
     * @return true iff the meeting is removed from the system.
     */
    public Boolean removeMeeting(Meeting meeting){
        if (listMeeting.contains(meeting)){
            listMeeting.remove(meeting);
            FilesReaderWriter.saveMeetingsToFile(listMeeting, filePath);
            return true;
        }else {
            return false;
        }
    }
    /** override the toString method to describe a list of meeting
     * @return a string show the detailed information about the meetings in the MeetingManager
     */
    public String toString(){
        StringBuilder string1 = new StringBuilder();
        for(Meeting meeting: listMeeting){
            string1.append(meeting.toString());
            string1.append("\n");
        }return string1.toString();
    }
}
