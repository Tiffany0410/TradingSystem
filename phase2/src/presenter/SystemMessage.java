package presenter;

import controllers.regularusersubcontrollers.RegularUserThresholdController;
import gateway.FilesReaderWriter;
import managers.usermanager.UserManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class SystemMessage {

    /**
     * Constructs a SystemMessage instance.
     */
    public SystemMessage(){

    }

    /**
     * Gathers all the necessary messages
     * for the regular user.
     *
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @return messages as properly formatted strings.
     * @throws IOException In case the file can't be found.
     */
    public String RegUserAlerts(managers.usermanager.UserManager um, RegularUserThresholdController tc, FilesReaderWriter rw, DisplaySystem ds, String username, String thresholdValuesFilePath) throws IOException {
        StringBuilder notification;
        notification = new StringBuilder();
        String filepath = "./src/Alerts/UserAlerts.csv";
        notification.append(rw.readFromMenu(filepath)).append("\n");
        activeAlerts(notification, um, tc, ds, username, rw, thresholdValuesFilePath);
        return notification.toString();
    }

    private void activeAlerts(StringBuilder notification, UserManager um, RegularUserThresholdController tc, DisplaySystem ds, String username, FilesReaderWriter rw, String thresholdValuesFilePath) throws FileNotFoundException {
        List<Integer> thresholdValues = rw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        if (!um.getFrozenStatus(username)) {
            // this check if for the uncompletedTransactions one
            if (tc.freezeUserOrNot(thresholdValues.get(1))){
                ds.printOut("You are frozen because you have exceeded the maximum number of uncompleted transactions limit.");
            }
        }
        notification.append("Your username is ").append(username).append("\n");
        notification.append("Your userId is ").append(um.usernameToID(username)).append("\n");
        notification.append("The answer to you're frozen is ").append(um.getFrozenStatus(username)).append("\n");
        notification.append("You have borrowed:").append(um.getInfo(username, "NumBorrowed")).append("\n");
        notification.append("You have lent:").append(um.getInfo(username, "NumLent")).append("\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append("\n");
        notification.append("Max number of transactions a week = ").append(thresholdValues.get(0)).append("\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(thresholdValues.get(1)).append("\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(thresholdValues.get(2)).append("\n");
        notification.append("Max edits per user for meeting’s date + time = ").append(thresholdValues.get(3)).append("\n");
    }


    /**
     * Gathers all the necessary notifications
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
    public void msgForMeetingDNE(DisplaySystem ds) {
        ds.printOut("This meeting doesn't exist in the system." + "\n");
    }


    /**
     * Prints the message for when there is nothing to be shown.
     * @param ds The presenter that prints to screen.
     */
    public void msgForNothing(DisplaySystem ds){
        ds.printOut("There's nothing here.");
        ds.printOut("\n");
    }


    /**
     * Prints the message for when there is nothing to be shown.
     * @param nextPart The more specific part of the message that relates to the context.
     * @param ds The presenter that prints to screen.
     */
    public void msgForNothing(String nextPart, DisplaySystem ds){
        ds.printOut("There's nothing " + nextPart + " .");
        ds.printOut("\n");
    }


    /**
     * Put together the message for when the option is locked for the user
     * due to the threshold.
     * @return The message described above.
     */
    public String lockMessageForThreshold(int maxNumTransactionAWeek) {
        return "This option is locked \n" +
                "You have reached the" + maxNumTransactionAWeek + "transactions a week limit" +
                "\n";
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     * @param ds The presenter that prints to screen.
     */
    public void msgForThresholdValue(int currentVal, DisplaySystem ds)
    {
        ds.printOut("The current threshold value is " + currentVal);
    }


    /**
     * Prints the message for the guest that tries to access options
     * that are meant for users.
     * @param ds The presenter that prints to screen.
     */
    public void msgForGuest(DisplaySystem ds){
        ds.printOut("Please login, guest account can't do this.");
    }

    /**
     * Prints the message for the user that tries to
     * access menus that can't access because of their
     * frozen status.
     * @param ds The presenter that prints to screen.
     */
    public void lockMessageForFrozen(DisplaySystem ds){
        ds.printOut("This menu is locked because you're frozen.");
    }


    /**
     * Put together the message for the user who tries
     * to set the tradable status for an item that is
     * already in the status that he/she is trying to
     * set it in.
     * @param tradableStatus The current tradable status of the item.
     * @return The message described above.
     */
    public String msgNoNeedToSetTradableStatus(boolean tradableStatus){
        return "The tradable status for this item is already " + tradableStatus + ". ";
    }

    /**
     * Put together the message for the user who tries
     * to access menus that he/she can't access because
     * their status is on-vacation.
     * @return The message described above.
     */
    public String lockMessageForVacation() {
        return"Because you're on vacation, you can't be involved in trade/meeting. \n " +
                "If you're back from vacation, please change your on-vacation status in the Account Menu.";
    }
}
