package bookTradeSystem;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 */
public class SystemMessage {


    /**
     * Constructs a SystemMessage instance.
     */
    public SystemMessage(){

    }

    /**
     * This method gathers all the necessary messages
     * for the regular user.
     *
     * @return messages as properly formatted strings.
     * @throws IOException In case the file can't be found.
     */
    public String RegUserAlerts(UserManager um, RegularUserThresholdController tc, FilesReaderWriter rw, DisplaySystem ds, String username) throws IOException {
        StringBuilder notification;
        notification = new StringBuilder();
        String filepath = "./src/Alerts/UserAlerts.csv";
        notification.append(rw.readFromMenu(filepath)).append("\n");
        activeAlerts(notification, um, tc, ds, username);
        return notification.toString();
    }

    private void activeAlerts(StringBuilder notification, UserManager um, RegularUserThresholdController tc, DisplaySystem ds, String username) {
        User regUser = um.findUser(username);
        if (!regUser.getIfFrozen()) {
            // this check if for the uncompletedTransactions one
            if (tc.freezeUserOrNot(regUser)){
                ds.printOut("You are frozen because you have exceeded the maximum number of uncompleted transactions limit.");
            }
        }
        notification.append("Your username is ").append(username).append("\n");
        notification.append("Your userId is ").append(um.usernameToID(username)).append("\n");
        notification.append("The answer to you're frozen is ").append(regUser.getIfFrozen()).append("\n");
        notification.append("You have borrowed:").append(regUser.getNumBorrowed()).append("\n");
        notification.append("You have lent:").append(regUser.getNumLent()).append("\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append("\n");
        notification.append("Max number of transactions a week = ").append(User.getMaxNumTransactionsAllowedAWeek()).append("\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(User.getMaxNumTransactionIncomplete()).append("\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(User.getNumLendBeforeBorrow()).append("\n");
        notification.append("Max edits per user for meeting’s date + time = ").append(User.getMaxMeetingDateTimeEdits()).append("\n");
    }


    /**
     * This method gathers all the necessary notifications
     * for the admin user.
     * @return Notifications as properly formatted strings.
     * @throws IOException In case the file can't be found.
     */
    public String AdminUserAlerts(FilesReaderWriter rw) throws IOException {
        //Read this in from file
        //Exception needs to be resolved in main or TradingSystem.
        //String filepath = "./src/bookTradeSystem/AdminAlerts.csv";
        String filepath = "./src/Alerts/AdminAlerts.csv"; // move it to src and not the bookTradeSystem
        return rw.readFromMenu(filepath);
    }

    /**
     * Prints the message for a meeting that doesn't exist.
     * @param ds The presenter that prints to screen.
     */
    protected void msgForMeetingDNE(DisplaySystem ds) {
        ds.printOut("This meeting doesn't exist in the system." + "\n");
    }


    /**
     * Prints the message for when there is nothing to be shown.
     * @param ds The presenter that prints to screen.
     */
    protected void msgForNothing(DisplaySystem ds){
        ds.printOut("There's nothing here.");
        ds.printOut("\n");
    }


    /**
     * Prints the message for when there is nothing to be shown.
     * @param nextPart The more specific part of the message that relates to the context.
     * @param ds The presenter that prints to screen.
     */
    protected void msgForNothing(String nextPart, DisplaySystem ds){
        ds.printOut("There's nothing " + nextPart + " .");
        ds.printOut("\n");
    }


    /**
     * Prints the message for when the option is locked for the user
     * due to the threshold.
     * @param ds The presenter that prints to screen.
     */
    protected void lockMessageForThreshold(DisplaySystem ds) {
        ds.printOut("This option is locked");
        ds.printOut("You have reached the" + User.getMaxNumTransactionIncomplete() + "transactions a week limit");
        ds.printOut("\n");
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     * @param ds The presenter that prints to screen.
     */
    protected void msgForThresholdValue(int currentVal, DisplaySystem ds)
    {
        ds.printOut("The current threshold value is " + currentVal);
    }

}
