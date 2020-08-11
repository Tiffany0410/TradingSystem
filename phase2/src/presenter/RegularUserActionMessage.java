package presenter;

import managers.actionmanager.Action;

/**
 * Regular user action message
 * @author Yangle Cheng, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserActionMessage {
    /**
     * Constructor of regular user action message
     */
    public RegularUserActionMessage(){}

    /**
     * Prefix of all regular user action
     * @param action action
     * @return the prefix of admin user actions
     */
    private String helper_regular_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": RegularUser #" + action.getActionOwnerID() + " ";
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    public String regularUserAction(Action action) {
        String string = "";

        String[] menuOption = action.getMenuOption().split("\\.");
        int mainMenuOption = Integer.parseInt(menuOption[0]);
        int subMenuOption = Integer.parseInt(menuOption[1]);
        int subSubMenuOption;
        if (menuOption.length == 3) {subSubMenuOption = Integer.parseInt(menuOption[2]);}
        else {subSubMenuOption = 0;}


        switch (mainMenuOption) {
            // MainMenuOption <1>  corresponding to RegularUserAccountMainMenu.csv
            case 1:
                string = string + regularUserAccountAction(action, subMenuOption, subSubMenuOption);
                break;
            // MainMenuOption <2>  corresponding to RegularUserTradingMenu.csv
            case 2:
                string = string + regularUserTradeAction(action, subMenuOption);
                break;
            // MainMenuOption <3>  corresponding to RegularUserMeetingMenu.csv
            case 3:
                string = string + regularUserMeetingAction(action, subMenuOption);
                break;
            // MainMenuOption <4>  corresponding to RegularUserSearchingMainMenu.csv
            case 4:
                string = string + regularUserSearchingAction(action, subMenuOption, subSubMenuOption);
                break;
            // MainMenuOption <5>  corresponding to RegularUserCommunityMenu.csv
            case 5:
                string = string + regularUserCommunityAction(action, subMenuOption);
                break;
        }

        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserAccountAction(Action action, int subMenuOption, int subSubMenuOption) {
        String string = "";
        switch (subMenuOption) {
            // 1.1 SubMenuOption <1>  corresponding to RegularUserManageItemsMenu.csv
            case 1:
                string = string + regularUserManageItemsAction(action, subSubMenuOption);
                break;
            // 1.2 SubMenuOption <2>  corresponding to RegularUserAccountSettingsMenu.csv
            case 2:
                string = string + regularUserAccountSettingsAction(action, subSubMenuOption);
                break;
            // 1.3 SubMenuOption <3>  corresponding to RegularUserFollowMenu.csv
            case 3:
                string = string + regularUserFollowAction(action, subSubMenuOption);
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserManageItemsAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 1.1.1: Browse all tradable items in the system
            case 1:
                string = string + helper_regular_action_prefix(action) + "browse all tradable items in the system";
                break;
            // 1.1.2: Add to own Wish List
            case 2:
                string = string + helper_regular_action_prefix(action) + "add Item #" + action.getAdjustableInt() + " to own Wish List" + "\n";
                break;
            // 1.1.3: Remove from own Wish List
            case 3:
                string = string + helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Wish List" + "\n";
                break;
            // 1.1.4: Remove from own Inventory
            case 4:
                string = string + helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Inventory" + "\n";
                break;
            // 1.1.5: Request to add item to your inventory
            case 5:
                string = string + helper_regular_action_prefix(action) + "request to add Item #" + action.getAdjustableInt() + "\n";
                break;
            // 1.1.6: See most recent three items traded
            case 6:
                string = string + helper_regular_action_prefix(action) + "check most recent three items traded" + "\n";
                break;
            // 1.1.7: View your wishlist and inventory
            case 7:
                string = string + helper_regular_action_prefix(action) + "view own wishlist and inventory" + "\n";
                break;
            // 1.1.8: Change tradable status for an inventory item
            case 8:
                string = string + helper_regular_action_prefix(action) + "change tradable status of inventory item #"
                        + action.getAdjustableInt() + " into " + action.getAdjustableStr() + "\n";
                break;
            // 1.1.9: Get suggestions for item(s) that you can lend to a given user
            case 9:
                string = string + helper_regular_action_prefix(action) + "get suggestions for item(s) that he/she can lend to a given user" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserAccountSettingsAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 1.2.1: Request to unfreeze account
            case 1:
                string = string + helper_regular_action_prefix(action) + "request to unfreeze account" + "\n";
                break;
            // 1.2.2: Set your on-vacation status
            case 2:
                string = string + helper_regular_action_prefix(action) + "set own on-vacation status into " + action.getAdjustableStr() + "\n";
                break;
            // 1.2.3: Change your home city
            case 3:
                string = string + helper_regular_action_prefix(action) + "change home city to " + action.getAdjustableStr() + "\n";
                break;
            // 1.2.4: Review own revocable action
            case 4:
                string = string + helper_regular_action_prefix(action) + "review own revocable action" + "\n";
                break;
            // 1.2.5: Request undo a revocable action
            case 5:
                string = string + helper_regular_action_prefix(action) + "request undo a revocable action #" + action.getAdjustableInt() + "\n";
                break;
        }

        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserFollowAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 1.3.1: Follow an user
            case 1:
                string = string + helper_regular_action_prefix(action) + "follow user #" + action.getAdjustableInt() + "\n";
                break;
            // 1.3.2: Follow an item
            case 2:
                string = string + helper_regular_action_prefix(action) + "follow item #" + action.getAdjustableInt() + "\n";
                break;
            // 1.3.3: See recent status of user(s) you're following
            case 3:
                string = string + helper_regular_action_prefix(action) + "check recent status of user(s) he/she follows" + "\n";
                break;
            // 1.3.4: See recent status of item(s) you're following
            case 4:
                string = string + helper_regular_action_prefix(action) + "check recent status of item(s) he/she follows" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserTradeAction(Action action, int subMenuOption) {
        String string = "";
        switch (subMenuOption) {
            // 2.1: Request a trade
            case 1:
                string = string + helper_regular_action_prefix(action) + " request a trade #" + action.getAdjustableInt() + action.getAdjustableStr() + "\n";
                break;
            // 2.2: Respond to trade requests
            case 2:
                string = string + helper_regular_action_prefix(action) + "respond to trade requests with Trade #" + action.getAdjustableInt() + "\n";
                break;
            // 2.3: View open trades
            case 3:
                string = string + helper_regular_action_prefix(action) + "view open trades" + "\n";
                break;
            // 2.4: View closed trades
            case 4:
                string = string + helper_regular_action_prefix(action) + "view closed trades" + "\n";
                break;
            // 2.5: Confirm that a trade has been completed
            case 5:
                string = string + helper_regular_action_prefix(action) + "check if the trade #" + action.getAdjustableInt() + " is completed or not" + "\n";
                break;
            // 2.6: See top three most frequent trading partners
            case 6:
                string = string + helper_regular_action_prefix(action) + "check the top three most frequent trading partners" + "\n";
                break;
            // 2.7: View transactions that have been cancelled
            case 7:
                string = string + helper_regular_action_prefix(action) + "view transactions that have been cancelled" + "\n";
                break;
            // 2.8: Suggestion for the most reasonable trade
            case 8:
                string = string + helper_regular_action_prefix(action) + "get the suggestion for the most reasonable trade" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserMeetingAction(Action action, int subMenuOption) {
        String string = "";
        switch (subMenuOption) {
            // 3.1: Suggest/confirm time and place for meetings
            case 1:
                string = string + helper_regular_action_prefix(action) + "successfully edit/confirm the time and place for meeting #" + action.getAdjustableInt() + "\n";
                break;
            // 3.2: Confirm the meeting took place
            case 2:
                string = string + helper_regular_action_prefix(action) + "confirm the meeting #" + action.getAdjustableInt() + "took place" + "\n";
                break;
            // 3.3: See the list of meetings need to be confirmed that it took place
//            case 3:
//                string = string + helper_regular_action_prefix(action) + "see the list of meetings need to be confirmed that it took place" + "\n";
//                break;
            // 3.3: See the list of meetings that have been confirmed
            case 3:
                string = string + helper_regular_action_prefix(action) + "see the list of meetings that have been confirmed" + "\n";
                break;
            // 3.5: See the list of meetings with time and place that need to be confirmed
//            case 5:
//                string = string + helper_regular_action_prefix(action) + "see the list of meetings with time and place that need to be confirmed" + "\n";
//                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserSearchingAction(Action action, int subMenuOption, int subSubMenuOption) {
        String string = "";

        switch (subMenuOption) {
            // 4.1: SubMenuOption <1>  corresponding to RegularUserSearchingItemsMenu.csv
            case 1:
                string = string + regularUserSearchingItemsAction(action, subSubMenuOption);
                break;
            // 4.2: SubMenuOption <2>  corresponding to RegularUserSearchingUsersMenu.csv
            case 2:
                string = string + regularUserSearchingUsersAction(action,subSubMenuOption);
                break;
            // 4.3: SubMenuOption <3>  corresponding to RegularUserSearchingMeetingsMenu.csv
            case 3:
                string = string + regularUserSearchingMeetingsAction(action, subSubMenuOption);
                break;
            // 4.4: SubMenuOption <4>  corresponding to RegularUserSearchingTradesMenu.csv
            case 4:
                string = string + regularUserSearchingTradesAction(action, subSubMenuOption);
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserSearchingItemsAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 4.1.1: Filter by category
            case 1:
                string = string + helper_regular_action_prefix(action) + "filter item by category: " + action.getAdjustableStr() + "\n";
                break;
            // 4.1.2: Search item by name
            case 2:
                string = string + helper_regular_action_prefix(action) + "search Item with name of " + action.getAdjustableStr() + "\n";
                break;
            // 4.1.3: Search item by id
            case 3:
                string = string + helper_regular_action_prefix(action) + "search Item with id of " + action.getAdjustableInt() + "\n";
                break;
            // 4.1.4: Sort by number of follows
            case 4:
                string = string + helper_regular_action_prefix(action) + "sort items by number of follows" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserSearchingUsersAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 4.2.1: Recent trade user
            case 1:
                string = string + helper_regular_action_prefix(action) + "check recent three users traded with him/her" + "\n";
                break;
            // 4.2.2: Frequent trade user
            case 2:
                string = string + helper_regular_action_prefix(action) + "sort all trade partners" + "\n";
                break;
            // 4.2.3: Sort user by rating
            case 3:
                string = string + helper_regular_action_prefix(action) + "sort all users by rating" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserSearchingMeetingsAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 4.3.1: Sort by date
            case 1:
                string = string + helper_regular_action_prefix(action) + "sort all meeting by date" + "\n";
                break;
            // 4.3.2: Incomplete meeting
            case 2:
                string = string + helper_regular_action_prefix(action) + "search for all incomplete meeting" + "\n";
                break;
            // 4.2.3: Complete meeting
            case 3:
                string = string + helper_regular_action_prefix(action) + "search for all complete meeting" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserSearchingTradesAction(Action action, int subSubMenuOption) {
        String string = "";

        switch (subSubMenuOption) {
            // 4.4.1: Incomplete trades
            case 1:
                string = string + helper_regular_action_prefix(action) + "search for all incomplete trade" + "\n";
                break;
            // 4.4.2: Complete trades
            case 2:
                string = string + helper_regular_action_prefix(action) + "search for all complete trade" + "\n";
                break;
        }
        return string;
    }

    /**
     * Give the string of regular user actions
     * @param action action
     * @return string of description this action
     */
    private String regularUserCommunityAction(Action action, int subMenuOption) {
        String string = "";

        switch (subMenuOption) {
            // 5.1: Write a review for an user
            case 1:
                string = string + helper_regular_action_prefix(action) + "rate user #" + action.getAdjustableInt() + " with rating score: " + action.getAdjustableStr() + "\n";
                break;
            // 5.2: Report an user
            case 2:
                string = string + helper_regular_action_prefix(action) + "report user #" + action.getAdjustableInt() + " with reason: " + action.getAdjustableStr() + "\n";
                break;
            // 5.3: Find the rating for a given user
            case 3:
                string = string + helper_regular_action_prefix(action) + "search the rate of user #" + action.getAdjustableInt() + "\n";
                break;
            // 5.4: See users in your home city
            case 4:
                string = string + helper_regular_action_prefix(action) + "check the users in his/her home city" + "\n";
                break;
            // 5.5: View your list of friends
            case 5:
                string = string + helper_regular_action_prefix(action) + "view his/her own friends list" + "\n";
                break;
            // 5.6: Send a friend request for a given user
            case 6:
                string = string + helper_regular_action_prefix(action) + "send friend request to other user #" + action.getAdjustableInt() + "\n";
                break;
            // 5.7: Respond to friend requests
            case 7:
                string = string + helper_regular_action_prefix(action) + "allow to add user #" + action.getAdjustableInt() + " with the username: " + action.getAdjustableStr() + " as friend" + "\n";
                break;
            // 5.8: Unfriend a user
            case 8:
                string = string + helper_regular_action_prefix(action) + "unfriend user #" + action.getAdjustableInt() + " with the username: " + action.getAdjustableStr() + "\n";
                break;
            // 5.9: Send message to friends
            case 9:
                string = string + helper_regular_action_prefix(action) + "send message to friend #" + action.getAdjustableInt() + " : " + action.getAdjustableStr() + "\n";
                break;
            // 5.10: View all message
            case 10:
                string = string + helper_regular_action_prefix(action) + "view all message" + "\n";
                break;
            // 5.11: View your rating and review
            case 11:
                string = string + helper_regular_action_prefix(action) + "view own rating and review" + "\n";
                break;
        }
        return string;
    }

}
