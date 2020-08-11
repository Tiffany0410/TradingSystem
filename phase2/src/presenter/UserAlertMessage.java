package presenter;

import controllers.regularusersubcontrollers.RegularUserThresholdController;
import managers.usermanager.UserManager;

import java.util.ArrayList;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
class UserAlertMessage {

    /**
     * Constructs UserAlertMessage.
     */
    UserAlertMessage(){

    }

    /**
     * Gathers all the necessary messages
     * for the regular user.
     * @param um The user manager.
     * @param tc The threshold controller.
     * @param username The username of the user.
     * @param menuPartOfAlert The part of the notification read from a menu.
     * @param thresholdValues A list of threshold values.
     * @return messages as properly formatted strings.
     */
    protected String regUserAlerts(UserManager um, RegularUserThresholdController tc, String username, String
            menuPartOfAlert, ArrayList<Integer> thresholdValues) {
        StringBuilder notification;
        notification = new StringBuilder();
        notification.append(menuPartOfAlert).append("\n");
        activeAlerts(notification, um, tc, username, thresholdValues);
        return notification.toString();
    }

    private void activeAlerts(StringBuilder notification, UserManager um, RegularUserThresholdController tc, String username, ArrayList<Integer> thresholdValues) {
        int userId = um.usernameToID(username);
        if (!um.getFrozenStatus(username)) {
            // this check if for the uncompletedTransactions one
            if (tc.freezeUserOrNot(thresholdValues.get(1))){
                notification.append("NOTE: You are frozen because you have reached the maximum number of uncompleted transactions limit.".toUpperCase()).append("\n\n");
            }
        }
        tc.reassessNumTransactionsLeftForTheWeek(thresholdValues.get(0));
        notification.append("Your username is ").append(username).append(".\n");
        notification.append("Your userId is ").append(userId).append(".\n");
        notification.append("The answer to you're frozen is ").append(um.getFrozenStatus(username)).append(".\n");
        notification.append("The answer to you're on-vacation is ").append(um.getInfo(userId, "Vacation") == 1).append(".\n");
        notification.append("You have borrowed:").append(um.getInfo(username, "NumBorrowed")).append(".\n");
        notification.append("Your home city is ").append(um.getHome(userId)).append(".\n");
        notification.append("You have lent:").append(um.getInfo(username, "NumLent")).append(".\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append(".\n");
        notification.append("Max number of transactions a week = ").append(thresholdValues.get(0)).append(".\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(thresholdValues.get(1)).append(".\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(thresholdValues.get(2)).append(".\n");
        notification.append("Max edits per user for meeting’s time + place = ").append(thresholdValues.get(3)).append(".\n");
        notification.append("FOR YOU...").append("\n");
        notification.append("YOU STILL HAVE ").append(um.getInfo(userId, "TransactionLeftForTheWeek")).append(" TRANSACTIONS LEFT FOR THE WEEK.");
        notification.append("If you're wondering: the transactions left for the week will be set back to full every MONDAY.");

    }
}
