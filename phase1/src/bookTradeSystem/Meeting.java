package bookTradeSystem;
import java.util.*;

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
    private List<Integer> timePlaceEdit;

    /** set this tradeId with tradeId, set this userId1 with userId1, set this userId2 with userId2, set this meetingNum
     *  with meetingNum, and set the rest variables with default.
     * @param tradeId the trade id that related to this meeting
     * @param userId1 first user's id for this meeting
     * @param userId2 second user's id for this meeting
     * @param meetingNum first or second meeting for the trade
     */
    public Meeting(int tradeId, int userId1, int userId2, int meetingNum){
        time = new Date();
        place = "";
        this.tradeId = tradeId;
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.meetingNum = meetingNum;
        timePlaceConfirm = false;
        meetingConfirm = new HashMap<>();
        meetingConfirm.put(userId1, false);
        meetingConfirm.put(userId2, false);
        timePlaceEdit = new ArrayList<>();

    }

    /** get the time for the meeting
     * @return the time for this meeting
     */
    public Date getTime(){
        return time;
    }

    /** set the time such that this year with year, this month with month, this hour with hour , this min with min,
     * and this sec with sec.
     * @param year the year for the meeting
     * @param month the month for the meeting(from 1 to 12)
     * @param date the date for the meeting
     * @param hour the hour for the meeting(24 hours)
     * @param min the minute for the meeting
     * @param sec the second for the meeting
     */
    public void setTime(int year, int month, int date, int hour, int min, int sec){
        Calendar time1 = Calendar.getInstance();
        time1.set(year, month-1, date, hour, min, sec);
        time = time1.getTime();
    }

    /** get the place for the meeting
     * @return the place for the meeting
     */
    public String getPlace(){
        return place;
    }

    /** set the place of the meeting with place
     * @param place the place for the meeting
     */
    public void setPlace(String place){
        this.place = place;
    }

    /** get the trade id for this meeting
     * @return the trade id related to this meeting
     */
    public int getTradeId(){
        return tradeId;
    }

    /** set the trade id of the meeting with tradeId
     * @param tradeId the trade id related to this meeting
     */
    public void setTradeId(int tradeId){
        this.tradeId = tradeId;
    }

    /** get the id of the first user of the meeting
     * @return the first user's id for this meeting
     */
    public int getUserId1(){
        return userId1;
    }

    /** set the id of the first user of the meeting with userId1
     * @param userId1 the first user's id for the meeting
     */
    public void setUserId1(int userId1){
        this.userId1 = userId1;
    }

    /** get the id of the second user of the meeting
     * @return the second user's id for the meeting
     */
    public int getUserId2(){
        return userId2;
    }

    /** set the id of the second user of the meeting with userId2
     * @param userId2 the second user's id for the meeting
     */
    public void setUserId2(int userId2){
        this.userId2 = userId2;
    }

    /** get the number of the meeting
     * @return 1(first meeting of the trade) or 2(second meeting of the trade)
     */
    public int getMeetingNum(){
        return meetingNum;
    }

    /** set the number of the meeting with meetingNum
     * @param meetingNum shows the first or second meeting of a trade
     */
    public void setMeetingNum(int meetingNum){
        this.meetingNum = meetingNum;
    }

    /** get whether or not the time and the place of the meeting is confirmed.
     * @return true if the meeting time and place is confirmed
     */
    public Boolean getTimePlaceConfirm(){
        return timePlaceConfirm;
    }

    /** confirm the time and place of the meeting
     * @param  userId the id of a user
     * @return true iff the meeting time and place is confirmed successfully.
     */
    public Boolean setTimePlaceConfirm(int userId){
        if (!timePlaceConfirm && timePlaceEdit.size() < User.getMaxMeetingDateTimeEdits() && ! timePlaceEdit.isEmpty() &&
                timePlaceEdit.get(timePlaceEdit.size()-1)!=userId &&(userId==userId1||userId==userId2)){
            timePlaceConfirm = true;
            timePlaceEdit.add(userId);
            return true;
        }return false;
    }

    /** get whether or not the completeness of the meeting is confirmed by two users
     * @return true iff the completeness of the meeting is confirmed by
     * the users
     */
    public Map<Integer, Boolean> getMeetingConfirm(){
        return meetingConfirm;
    }

    /** get the list of users id that edit the time and place of the meeting
     * @return the list of user id edit the meeting time and place
     */
    public List<Integer> getTimePlaceEdit(){
        return timePlaceEdit;
    }

    /** set the timPlaceEdit with the listId.
     * @param listId the list of user id that change the time and place
     */
    public void setTimePlaceEdit(List<Integer> listId){
        timePlaceEdit = listId;
    }

    /** edit the time and place of a meeting
     * @param userId the id for whom to edit the meeting time and place
     * @return true iff the change to the numEdit happen
     */
    public Boolean setTimePlaceEdit(int userId, int year, int month, int day, int hour, int min, int sec,
                                    String place){
    if (!timePlaceConfirm &&(userId == userId1 ||userId == userId2)&&(timePlaceEdit.isEmpty()
            || (timePlaceEdit.get(timePlaceEdit.size()-1) != userId && timePlaceEdit.size()<User.
            getMaxMeetingDateTimeEdits()))){
        this.setTime(year, month, day, hour, min,sec);
        this.place = place;
        timePlaceEdit.add(userId);
        return true;
    }else{return false;}
    }

    /** count the number of time the user with userId who edited the time and place
     * @param userId id of the user
     * @return the number that the user edited the time and place
     */
    public int countEdit(int userId){
        int num = 0;
        for(int userId3: timePlaceEdit){
            if (userId3 == userId){
                num++;
            }
        }
        return num;
    }

    /** override the to string to describe the meeting
     * @return a string to describe the meeting.
     */
    public String toString(){
        if (meetingConfirm.get(userId1) && meetingConfirm.get(userId2)){
            return "The number "+ meetingNum +" meeting with trade id " + tradeId + " between Users " + userId1 +" " +
                    "and " + userId2 + " was " + "on " + place +" "+ time + ", and the meeting is complete.";
        }else{
            return "The number "+ meetingNum +" meeting with trade id " + tradeId + " between Users " + userId1 +
                    " and " + userId2 + " was/is on " + place +" "+ time + ", and the confirm status for the " +
                    "place and time is " + timePlaceConfirm + ", " + "and the meeting is not complete.";
        }
    }

    /** override the equals method for two meetings
     * @param meeting the meeting for a trade
     * @return true iff this tradeId, userId1, userId2 and meetingNum equals to the meeting's tradeId,
     * userId1, userId2 and meetingNum.
     */
    public Boolean equals(Meeting meeting){
        return this.getTradeId() == meeting.getTradeId() && this.getMeetingNum() == meeting.getMeetingNum()
                && this.getUserId1() == meeting.getUserId1() && this.getUserId2() == meeting.getUserId2();
    }
    }






