package Controllers.RegularUserSubController;

import Managers.MeetingManager.Meeting;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.User;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;

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

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;
    // whether the max transactions per week threshold is reassessed
    private boolean thresholdReassessed;

    /**
     * Constructs a RegularUserThresholdController with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserThresholdController(DisplaySystem ds,
                                          TradeManager tm, MeetingManager mm,
                                          UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
        this.thresholdReassessed = false;
    }

    /**
     * Re-assesses user's number of transactions left for the week.
     */
    public void reassessNumTransactionsLeftForTheWeek(int maxNumTransactionAllowedAWeek) {
        if (isFirstDayOfTheWeek() && !thresholdReassessed){
            um.setThreshold(userId, "TransactionLeftForTheWeek", maxNumTransactionAllowedAWeek);
            thresholdReassessed = true;
        }
        else if (!isFirstDayOfTheWeek()){
            thresholdReassessed = false;
        }
    }

    /**
     * Decrements the number of trades left for the week
     * for this user by one.
     */
    protected void changeNumTradesLeftForTheWeek(){
        /*
        Based on code by Kashif from https://stackoverflow.com/questions/18600257/how-to-get-the-weekday-of-a-date
         */
        int currentVal = um.getThreshold(userId, "TransactionLeftForTheWeek");
        // deduct the number of transactions left by one
        um.setThreshold(userId, "TransactionLeftForTheWeek", currentVal-1);
    }


    /**
     * Finds out if now, or today, is the first day of the week.
     * @return Whether now is the first day of the week.
     */
    protected boolean isFirstDayOfTheWeek(){
        Calendar c = Calendar.getInstance();
        return c.getFirstDayOfWeek() == c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Judges whether the user should be frozen and actually do so
     * based on the maximum uncompleted transactions allowed before
     * the user is frozen threshold.
     * @return Whether the user is frozen or not.
     */
    public boolean freezeUserOrNot(int maxNumTransactionIncomplete){
        int numFrozen = um.getThreshold(userId, "NumFrozen");
        // find the num of uncompleted transactions
        int numUncompletedTransactions = numUncompletedTransactions();
        // if user went over the threshold
        // or if the user's been frozen for three times -- freeze the account every time = permanent freeze
        int threshold =  maxNumTransactionIncomplete + (numFrozen * maxNumTransactionIncomplete);
        if (numUncompletedTransactions > threshold || numFrozen == 3) {
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
//          TODO: need a mm method to check if a meeting's trade id is 0 or not
//          TODO: replace getTradeId()
            int tradeID = meeting.getTradeId();
            if (!uniqueTradeIDs.contains(tradeID)){
                uniqueTradeIDs.add(tradeID);
            }
        }
        return uniqueTradeIDs.size();
    }
}
