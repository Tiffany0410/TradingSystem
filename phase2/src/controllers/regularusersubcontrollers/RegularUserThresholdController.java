package controllers.regularusersubcontrollers;

import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.usermanager.UserManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * An instance of this class represents the threshold
 * controller for the RegularUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserThresholdController {

    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserThresholdController with
     * a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserThresholdController(MeetingManager mm,
                                          UserManager um, String username) {
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(this.username);
    }

    /**
     * Re-assesses user's number of transactions left for the week.
     * @param maxNumTransactionAllowedAWeek The maximum number of transactions allowed a week.
     */
    public void reassessNumTransactionsLeftForTheWeek(int maxNumTransactionAllowedAWeek) {
        // set to full if it's the first day of the week
        if (isFirstDayOfTheWeek()){
            um.setThreshold(userId, "TransactionLeftForTheWeek", maxNumTransactionAllowedAWeek);
        }
    }

    /**
     * Decrements the number of trades left for the week
     * for this user by one.
     */
    protected void changeNumTradesLeftForTheWeek(){
        //get user's num transactions left for a week
        int currentVal = um.getInfo(userId, "TransactionLeftForTheWeek");
        // deduct the number of transactions left by one
        um.setThreshold(userId, "TransactionLeftForTheWeek", currentVal-1);
    }


    /**
     * Finds out if, today, according to the system date, is the first day of the week.
     * @return Whether the today, according to the system date, is the first day of the week.
     */
    protected boolean isFirstDayOfTheWeek(){
        // changed to the mock system date
        Calendar c = mm.getSystemDate();
        return c.get(Calendar.DAY_OF_WEEK) == 2;
    }

    /**
     * Judges whether the user should be frozen and actually do so
     * based on the maximum uncompleted transactions allowed before
     * the user is frozen threshold.
     * @return Whether the user is frozen or not.
     */
    public boolean freezeUserOrNot(int maxNumTransactionIncomplete){
        int numFrozen = um.getInfo(userId, "NumFrozen");
        // find the num of uncompleted transactions
        int numUncompletedTransactions = numUncompletedTransactions();
        // if user went over the threshold
        // or if the user's been frozen for three times -- freeze the account every time = permanent freeze
        int threshold =  maxNumTransactionIncomplete + (numFrozen * maxNumTransactionIncomplete);
        if (numUncompletedTransactions >= threshold || numFrozen == 3) {
            // freeze the user if the limit's passed and the user's been frozen 3 times
            um.freezeUser(username);
            // add one to the number of times the user's frozen
            um.setThreshold(userId, "NumFrozen", numFrozen+1);
            return true;
        }
        return false;
    }


     private int numUncompletedTransactions() {
        // will store all the unique trade IDs
        List<Integer> uniqueTradeIDs = new ArrayList<>();
        // get the meetings that are overtime
        List<Meeting> overTimeMeetings = mm.getListOverTime(userId);
        /* we get unique # of trades for meetings
        that are overtime and that's how we
        get the number of uncompleted transactions
         */
        for (Meeting meeting : overTimeMeetings){
            int tradeID = mm.meetingToTradeId(meeting);
            if (!uniqueTradeIDs.contains(tradeID)){
                uniqueTradeIDs.add(tradeID);
            }
        }
        return uniqueTradeIDs.size();
    }


}
