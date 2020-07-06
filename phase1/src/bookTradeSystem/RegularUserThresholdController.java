package bookTradeSystem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    // TODO: MOVE TO ThresholdController class
    protected void reassessNumTransactionsLeftForTheWeek(User thisUser) {
        if (isFirstDayOfTheWeek() && !thresholdReassessed){
            thisUser.setTransactionLeftForTheWeek(User.getMaxNumTransactionsAllowedAWeek());
            thresholdReassessed = true;
        }
        else if (!isFirstDayOfTheWeek()){
            thresholdReassessed = false;
        }
    }

    // TODO: MOVE TO ThresholdController class
    protected void changeNumTradesLeftForTheWeek(User thisUser){
        /*
        Based on code by Kashif from https://stackoverflow.com/questions/18600257/how-to-get-the-weekday-of-a-date
         */
        int currentVal = thisUser.getNumTransactionLeftForTheWeek();
        thisUser.setTransactionLeftForTheWeek(currentVal-1);
    }


    // TODO: MOVE TO ThresholdController class
    protected boolean isFirstDayOfTheWeek(){
        Calendar c = Calendar.getInstance();
        return c.getFirstDayOfWeek() == c.get(Calendar.DAY_OF_WEEK);
    }

    // TODO: MOVE TO ThresholdController class
    protected boolean freezeUserOrNot(User thisUser){
        int numFrozen = thisUser.getNumFrozen();
        // find the num of uncompleted transactions
        int numUncompletedTransactions = numUncompletedTransactions();
        // if user went over the threshold
        // or if the user's been frozen for three times -- freeze the account every time = permanent freeze
        int threshold =  User.getMaxNumTransactionIncomplete() + (numFrozen * User.getMaxNumTransactionIncomplete());
        if (numUncompletedTransactions > threshold || thisUser.getNumFrozen() == 3) {
            um.freezeUser(username);
            thisUser.addOneToNumFrozen();
            return true;
        }
        return false;
    }

    // TODO: MOVE TO ThresholdController class
    protected int numUncompletedTransactions() {
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
