package bookTradeSystem;

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
     * Re-assess user's number of transactions left for the week.
     * @param thisUser The user to be re-assessed the number of transactions
     *                 left for the week for.
     */
    protected void reassessNumTransactionsLeftForTheWeek(User thisUser, int maxNumTransactionAllowedAWeek) {
        if (isFirstDayOfTheWeek() && !thresholdReassessed){
            thisUser.setTransactionLeftForTheWeek(maxNumTransactionAllowedAWeek);
            thresholdReassessed = true;
        }
        else if (!isFirstDayOfTheWeek()){
            thresholdReassessed = false;
        }
    }

    /**
     * Decrement the number of trades left for the week
     * for this user by one.
     * @param thisUser The user whose number of trade left for
     *                 the week is to be changed.
     */
    protected void changeNumTradesLeftForTheWeek(User thisUser){
        /*
        Based on code by Kashif from https://stackoverflow.com/questions/18600257/how-to-get-the-weekday-of-a-date
         */
        int currentVal = thisUser.getNumTransactionLeftForTheWeek();
        thisUser.setTransactionLeftForTheWeek(currentVal-1);
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
     * Judge whether the user should be frozen and actually do so
     * based on the maximum uncompleted transactions allowed before
     * the user is frozen threshold.
     * @param thisUser The user to be determined whether he/she should be frozen or not.
     * @return Whether the user is frozen or not.
     */
    protected boolean freezeUserOrNot(User thisUser, int maxNumTransactionIncomplete){
        int numFrozen = thisUser.getNumFrozen();
        // find the num of uncompleted transactions
        int numUncompletedTransactions = numUncompletedTransactions();
        // if user went over the threshold
        // or if the user's been frozen for three times -- freeze the account every time = permanent freeze
        int threshold =  maxNumTransactionIncomplete + (numFrozen * maxNumTransactionIncomplete);
        if (numUncompletedTransactions > threshold || thisUser.getNumFrozen() == 3) {
            um.freezeUser(username);
            thisUser.addOneToNumFrozen();
            return true;
        }
        return false;
    }


     private int numUncompletedTransactions() {
        List<Integer> uniqueTradeIDs = new ArrayList<>();
        List<Meeting> overTimeMeetings = mm.getListOverTime(userId);
        for (Meeting meeting : overTimeMeetings){
            int tradeID = meeting.getTradeId();
            if (!uniqueTradeIDs.contains(tradeID)){
                uniqueTradeIDs.add(tradeID);
            }
        }
        return uniqueTradeIDs.size();
    }
}
