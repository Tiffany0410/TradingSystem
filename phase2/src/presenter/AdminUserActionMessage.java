package presenter;

import managers.actionmanager.Action;

/**
 * Admin user action message
 * @author Yangle Cheng, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class AdminUserActionMessage {

    /**
     * Constructor of adminUserActionMessage
     */
    public AdminUserActionMessage(){}

    /**
     * Prefix of all admin action
     * @param action action
     * @return the prefix of admin user actions
     */
    private String helper_admin_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": AdminUser #" + action.getActionOwnerID() + " ";
    }

    /**
     * Give the string of admin user actions
     * @param action action
     * @return string of description this action
     */
    public String adminUserAction(Action action) {
        String string = "";

        String[] menuOption = action.getMenuOption().split("\\.");
        int mainMenuOption = Integer.parseInt(menuOption[0]);
        int subMenuOption = Integer.parseInt(menuOption[1]);

        switch (mainMenuOption) {
            // MainMenuOption <1>  corresponding to AdminUserManageUsersSubMenu.csv
            case 1:
                string = string + adminUserManageUsersAction(action, subMenuOption);
                break;
            // MainMenuOption <2>  corresponding to AdminUserEditThresholdsSubMenu.csv
            case 2:
                string = string + adminUserEditThresholdsAction(action, subMenuOption);
                break;
            // MainMenuOption <3>  corresponding to AdminUserHistoricalActionSubMenu.csv
            case 3:
                string = string + adminUserActionAction(action, subMenuOption);
                break;
            // MainMenuOption <4>  corresponding to AdminUserOtherSubMenu.csv
            case 4:
                string = string + adminUserOtherAction(action, subMenuOption);
                break;
        }
        return string;
    }

    /**
     * Give the string of admin user actions
     * @param action action
     * @return string of description this action
     */
    private String adminUserManageUsersAction(Action action, int subMenuOption) {
        String string = "";

        switch (subMenuOption) {
            // 1.1: Freeze tradableUsers
            case 1:
                string = string + helper_admin_action_prefix(action) + "freeze tradableUser #" + action.getAdjustableInt() + " with username: " + action.getAdjustableStr() + "\n";
                break;
            // 1.2: Unfreeze tradableUsers
            case 2:
                string = string + helper_admin_action_prefix(action) + "unfreeze tradableUser #" + action.getAdjustableInt() + " with username: " + action.getAdjustableStr() + "\n";
                break;
            // 1.3: Confirm and add item to tradableUser’s inventory
            case 3:
                string = string + helper_admin_action_prefix(action) + "confirm and add item #" + action.getAdjustableInt() + " into inventory of Regular User #" + Integer.parseInt(action.getAdjustableStr()) + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of admin user actions
     * @param action action
     * @return string of description this action
     */
    private String adminUserEditThresholdsAction(Action action, int subMenuOption) {
        String string = "";

        switch (subMenuOption) {
            // 2.1: Edit the max number of transactions allowed a week
            case 1:
                string = string + helper_admin_action_prefix(action) + "edit <the max number of transactions allowed a week> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n";
                break;
            // 2.2: Edit the max number of transactions that can be incomplete before the account is frozen
            case 2:
                string = string + helper_admin_action_prefix(action) + "edit <the max number of transactions that can be incomplete before the account is frozen> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n";
                break;
            // 2.3: Edit the number of books tradableUsers must lend before tradableUsers can borrow
            case 3:
                string = string + helper_admin_action_prefix(action) + "edit <the number of books tradableUsers must lend before tradableUsers can borrow> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n";
                break;
            // 2.4: Edit the max Edits per user for meeting’s date + time
            case 4:
                string = string + helper_admin_action_prefix(action) + "edit <the max Edits per user for meeting’s date + time> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of admin user actions
     * @param action action
     * @return string of description this action
     */
    private String adminUserActionAction(Action action, int subMenuOption) {
        String string = "";

        switch (subMenuOption) {
            // 3.1: List all the historical actions in the system
            case 1:
                string = string + helper_admin_action_prefix(action) + "list all the historical actions in the system" + "\n";
                break;
            // 3.2: List all the historical revocable actions in the system
            case 2:
                string = string + helper_admin_action_prefix(action) + "list all the historical revocable actions in the system" + "\n";
                break;
            // 3.3: Find all the revocable historical actions of specific tradableUser
            case 3:
                string = string + helper_admin_action_prefix(action) + "search all the revocable historical actions of tradableUser #" + action.getAdjustableInt() + "\n";
                break;
            // 3.4: Cancel the revocable historical actions of tradableUser by actionID
            case 4:
                string = string + helper_admin_action_prefix(action) + "cancel the revocable historical actions #" + action.getAdjustableInt() + "\n";
                break;
            // 3.4: Confirm undo request and undo revocable historical actions
            case 5:
                string = string + helper_admin_action_prefix(action) + "confirm undo request and undo revocable historical action #" + action.getAdjustableInt() + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of admin user actions
     * @param action action
     * @return string of description this action
     */
    private String adminUserOtherAction(Action action, int subMenuOption) {
        String string = "";
        switch (subMenuOption) {
            // 4.1: Add subsequent admin Users
            case 1:
                string = string + helper_admin_action_prefix(action) + "add subsequent Admin Users with username: " + action.getAdjustableStr() + "\n";
                break;
            // 4.2: Set system time
            case 2:
                String[] temp = action.getAdjustableStr().split(";");
                String previous = temp[0];
                String current = temp[1];
                string = string + helper_admin_action_prefix(action) + "change system time from " + previous + " to " + current + "\n";
                break;
        }
        return string;
    }


}
