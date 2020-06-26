package bookTradeSystem;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MeetingManager implements java.io.Serializable{
    List<Meeting> listMeeting = new ArrayList<>();
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
                    MeetingManager.addMeeting(meeting.getTradeId(), meeting.getUserId1(),meeting.getUserId2(), 2);
                    return true;
                }
            }
        }return false;}

    /**
     * @return true iff the trade is not complete in one month and a day.
     */
    public Boolean goOverTime(Meeting meeting){
        Calendar time1 = Calendar.getInstance();
        time1.setTime(meeting.getTime());
        time1.add(Calendar.MONTH,1);
        time1.add(Calendar.DATE,1);
        Date time2 = time1.getTime();
        return TradeManager.getTradeStatus(meeting.getTradeId()) == "open" && meeting.getMeetingConfirm().
                get(meeting.getUserId1()) && meeting.getMeetingConfirm().get(meeting.getUserId2())  &&
                time2.before(new Date());
    }
}
