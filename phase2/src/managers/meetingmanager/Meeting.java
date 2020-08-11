package managers.meetingmanager;
import java.util.*;

/**
 * An instance of this class represents the meeting that holds for a trade.
 * @author Jianhong Guo
 * @version IntelliJ IDEA 2020.1
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
    protected void setTime(int year, int month, int date, int hour, int min, int sec){
        Calendar time1 = Calendar.getInstance();
        time1.set(year, month-1, date, hour, min, sec);
        time = time1.getTime();
    }

    /** Get the place for the meeting
     * @return the place for the meeting
     */
    public String getPlace(){
        return place;
    }

    /** set the place of the meeting with place
     * @param place the place for the meeting
     */
    protected void setPlace(String place){
        this.place = place;
    }

    /** get the trade id for this meeting
     * @return the trade id related to this meeting
     */
    public int getTradeId(){
        return tradeId;
    }

    /** get the id of the first user of the meeting
     * @return the first user's id for this meeting
     */
    protected int getUserId1(){
        return userId1;
    }

    /** get the id of the second user of the meeting
     * @return the second user's id for the meeting
     */
    protected int getUserId2(){
        return userId2;
    }

    /** get the number of the meeting
     * @return 1(first meeting of the trade) or 2(second meeting of the trade)
     */
    public int getMeetingNum(){
        return meetingNum;
    }

    /** get whether or not the time and the place of the meeting is confirmed.
     * @return true if the meeting time and place is confirmed
     */
    protected boolean getTimePlaceConfirm(){
        return timePlaceConfirm;
    }

    /** confirm the time and place for a meeting
     */
    protected void setTimePlaceConfirm(boolean timePlaceConfirm){ this.timePlaceConfirm = timePlaceConfirm; }

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
    protected List<Integer> getTimePlaceEdit(){
        return timePlaceEdit;
    }

    /** set the timPlaceEdit with the listId.
     * @param listId the list of user id that change the time and place
     */
    protected void setTimePlaceEdit(List<Integer> listId){
        timePlaceEdit = listId;
    }

    /** override the to string to describe the meeting
     * @return a string to describe the meeting.
     */
    public String toString(){
        if (meetingConfirm.get(userId1) && meetingConfirm.get(userId2)){
            return "Trade id: " + tradeId + ", Meeting number: " + meetingNum + ", User1: " + userId1 +", " +
                    "User2: " + userId2 + ", Place: " + place +", Time: "+ time + ". List of users " +
                    "edited the time and place: " + timePlaceEdit + ".\n"+"The meeting is complete.";
        }else{
            return "Trade id: " + tradeId + ", Meeting number: " + meetingNum + ", User1: " + userId1 +
                    ", User2: " + userId2 + ", Place: " + place +", Time: "+ time + ". List of users " +
                    "edited the time and place: " + timePlaceEdit + ".\n"+"The confirm status for the " +
                    "place and time is: " + timePlaceConfirm + ", " + "and the meeting is not complete.";
        }
    }

    /** override the equals method for two meetings
     * @param meeting the meeting for a trade
     * @return true if this tradeId, userId1, userId2 and meetingNum equals to the meeting's tradeId,
     * userId1, userId2 and meetingNum.
     */
    protected boolean equals(managers.meetingmanager.Meeting meeting){
        return this.getTradeId() == meeting.getTradeId() && this.getMeetingNum() == meeting.getMeetingNum()
                && this.getUserId1() == meeting.getUserId1() && this.getUserId2() == meeting.getUserId2();
    }
    }






