package bookTradeSystem;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
/**
 * An instance of this class represents the meeting that holds for a trade.
 */
public class Meeting implements java.io.Serializable{
    private Date time;
    private String place;
    private int tradeId;
    private int userId1;
    private int userId2;
    private int meetingNum;
    private Boolean timePlaceConfirm;
    private Map<Integer, Boolean> meetingConfirm;
    private Map<Integer, Integer> numEdit;

    /**
     * @param tradeId the trade id that related to this meeting
     * @param userId1 first user's id for this meeting
     * @param userId2 second user's id for this meeting
     * @param meetingNum first or second meeting for the trade
     */
    public Meeting(int tradeId, int userId1, int userId2, int meetingNum){
        time = new Date();
        place = new String("");
        this.tradeId = tradeId;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.meetingNum = meetingNum;
        timePlaceConfirm = false;
        meetingConfirm = new HashMap<>();
        meetingConfirm.put(userId1, false);
        meetingConfirm.put(userId2, false);
        numEdit = new HashMap<>();
        numEdit.put(userId1, 0);
        numEdit.put(userId2, 0);
    }

    /**
     * @return the time for this meeting
     */
    public Date getTime(){
        return time;
    }

    /**
     * @param year the year for the meeting
     * @param month the month for the meeting
     * @param date the date for the meeting
     * @param hour the hour for the meeting
     * @param min the minute for the meeting
     * @param sec the second for the meeting
     */
    public void setTime(int year, int month, int date, int hour, int min, int sec){
        Calendar time1 = Calendar.getInstance();
        time1.set(year, month, date, hour, min, sec);
        time = time1.getTime();
    }

    /**
     * @return the place for the meeting
     */
    public String getPlace(){
        return place;
    }

    /**
     * @param place the place for the meeting
     */
    public void setPlace(String place){
        this.place = place;
    }

    /**
     * @return the trade id related to this meeting
     */
    public int getTradeId(){
        return tradeId;
    }

    /**
     * @param tradeId the trade id related to this meeting
     */
    public void setTradeId(int tradeId){
        this.tradeId = tradeId;
    }

    /**
     * @return the first user's id for this meeting
     */
    public int getUserId1(){
        return userId1;
    }

    /**
     * @param userId1 the first user's id for the meeting
     */
    public void setUserId1(int userId1){
        this.userId1 = userId1;
    }

    /**
     * @return the second user's id for the meeting
     */
    public int getUserId2(){
        return userId2;
    }

    /**
     * @param userId2 the second user's id for the meeting
     */
    public void setUserId2(int userId2){
        this.userId2 = userId2;
    }

    /**
     * @return 1(first meeting of the trade) or 2(second meeting of the trade)
     */
    public int getMeetingNum(){
        return meetingNum;
    }

    /**
     * @param meetingNum shows the first or second meeting of a trade
     */
    public void setMeetingNum(int meetingNum){
        this.meetingNum = meetingNum;
    }

    /**
     * @return true if the meeting time and place is confirmed
     */
    public Boolean getTimePlaceConfirm(){
        return timePlaceConfirm;
    }

    /**
     * @param timePlaceConfirm whether or not the time and place is confirmed by user
     */
    public void setTimePlaceConfirm(Boolean timePlaceConfirm){
        this.timePlaceConfirm = timePlaceConfirm;
    }

    /**
     * @return true iff the completeness of the meeting is confirmed by
     * the users
     */
    public Map<Integer, Boolean> getMeetingConfirm(){
        return meetingConfirm;
    }

    /**
     * @return the num of edit for users
     */
    public Map<Integer, Integer> getNumEdit(){
        return numEdit;
    }

    /**
     * @param userId the id for whom to edit the meeting time and place
     * @return true iff the change to the numEdit happen
     */
    public Boolean setNumEdit(int userId){
        if (numEdit.containsKey(userId)){
            numEdit.replace(userId, numEdit.get(userId) + 1);
            return true;
        }else{
            return false;
        }}

    /**
     * @return a string to describe the meeting.
     */
    public String toString(){
        if (meetingConfirm.get(userId1) && meetingConfirm.get(userId2)){
            return "The meeting with trade id" + tradeId + "between Users" + userId1 +"and" + userId2 + "was on" +
                    place + time + ", and the meeting is complete.";
        }else{
            return "The meeting with trade id" + tradeId + "between Users" + userId1 +"and" + userId2 + "was/is on" +
                    place + time + ". The confirm status for the place and time is" + timePlaceConfirm + "," +
                    "and the meeting is not complete.";
        }
    }


}




