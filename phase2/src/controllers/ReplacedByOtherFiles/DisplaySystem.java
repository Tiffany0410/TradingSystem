package controllers.maincontrollers;

import gateway.FilesReaderWriter;
import gui.NotificationGUI;
import managers.actionmanager.Action;
import managers.usermanager.TradableUser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * An instance of this class represents the communication bridge from the trading system to the user.
 *
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1.1
 */


public class DisplaySystem {
    private FilesReaderWriter fileReader;
    private int option;

    public DisplaySystem() {
        fileReader = new FilesReaderWriter();
    }

    /**
     * print the put in sentence to the screen
     * convinces to change from txt to window
     *
     * @param str the string need to be print out
     */
    public void printOut(String str) {
        NotificationGUI notificationGUI = new NotificationGUI(str);
        notificationGUI.run(str);
    }

    //////////////////////  Below method are replaced by GUI /////////////////////////////////////////////
    /**
     * Print menu and get the option user put in
     *
     * @param filePath the path of file need to be read
     * @return option
     */

    public int getMenuAnswer(String filePath) throws IOException {

        // get valid option user typed in
        while (true) {

            Scanner sc = new Scanner(System.in);

            printMenu(filePath);  // print all options
            this.printOut("Please enter the number of your option:");

            String typeIn = sc.nextLine();  // get number user typed in

            try {
                option = Integer.parseInt(typeIn);

                // check the number user typed in
                int menuLength = getMenuLength(filePath);
                if (0 <= option && option <= menuLength) {
                    break;
                } else {
                    this.printOut("Please enter a number provide in the menu");
                }
            } catch (Exception InputMismatchException) {
                // If user type in not int
                this.printOut("Please enter a number!");

            }

        }
        return option;
    }


    /**
     * Get the number of options
     *
     * @param filePath the path of file need to be read
     * @return int the length of menu
     */

    private int getMenuLength(String filePath) throws FileNotFoundException {
        return fileReader.MenuLength(filePath);
    }


    /**
     * Show the menu on the screen by taking the file name and show the options in the file
     * to the screen
     * In the future, this will change to another UI not just text
     *
     * @param filePath the path of file need to be read
     */

    private void printMenu(String filePath) throws IOException {
        this.printOut(fileReader.readFromMenu(filePath));
    }


    /**
     * Get the username user put in
     *
     * @return username
     */

    public String getUsername() {
        String userName;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter user name");
        userName = sc.nextLine();  // get username user typed in
        return userName;

    }

    /**
     * Get the password user put in
     *
     * @return password
     */

    public String getPassword() {
        String password;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter password");
        password = sc.nextLine();  // get password user typed in
        return password;
    }

    /**
     * Get email address user put in
     */

    public String getEmail() {
        String emailAddress;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter your email address");
        emailAddress = sc.nextLine();  // get email address user typed in
        return emailAddress;
    }

    /**
     * Get home city user typed in
     */

    public String getHomeCity() {
        String city;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter your email address");
        city = sc.nextLine();  // get city user typed in
        return city;
    }






    /**
     * Print out the list of username of the Regular Users
     *
     * @param listOfUser the list of Regular Users
     */
    public void printListUser(ArrayList<TradableUser> listOfUser) {
        for (TradableUser user : listOfUser) {
            printOut("#"+ user.getId() + ". "+user.getUsername()+ ".\n");
        }
    }

    // Replaced by GUI
    /**
     * Print out the result of action with boolean type
     *
     * @param result the result of user's operation
     */
    public void printResult(boolean result) {
        if (result) {
            printOut("Success");
        } else {
            printOut("Fail");
        }
        printOut("\n");
    }

    //Replaced by GUI
    /**
     * print out the result of action with object type
     *
     * @param obj the list of objects need to be printed
     */
    public void printResult(ArrayList<Object> obj) {
        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                this.printOut("#" + count + ". " + o.toString() + "\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                this.printOut("#" + count + ". " + "\n" + "Username: " + strings[0]);
                this.printOut("Message: " + strings[1] + "\n");
            }
            count++;
        }
    }
//////////////////////////////// Above methods are replaced by GUI ////////////////////////////////////////////////


    /**
     * print out the result of action with string and boolean type
     *
     * @param str    the sentence want to present
     * @param result the result of the action
     */

    public void printResult(String str, boolean result) {
        if (result) {
            printOut(str + " is sent successfully");
            printOut("\n");
        } else {
            printOut(str + " fails to be sent");
            printOut("\n");
        }
    }


    /**
     * Print out all historical Action which can be cancelled
     */
    public void printHistoricalActions(ArrayList<Action> listOfAction) {
        for (Action action : listOfAction) {
            String userType = action.getUserType();
            String menuOption = action.getMenuOption();
            switch (userType) {
                case "regularUser":
                    this.regularUserAction(action);
                    break;
                case "adminUser":
                    this.adminUserAction(action);
                    break;
            }

        }
    }


    private void regularUserAction(Action action) {
        String[] menuOption = action.getMenuOption().split("\\.");
        int mainMenuOption = Integer.parseInt(menuOption[0]);
        int subMenuOption = Integer.parseInt(menuOption[1]);
        int subSubMenuOption;
        if (menuOption.length == 3) {subSubMenuOption = Integer.parseInt(menuOption[2]);}
        else {subSubMenuOption = 0;}


        switch (mainMenuOption) {
            // MainMenuOption <1>  corresponding to RegularUserAccountMainMenu.csv
            case 1:
                regularUserAccountAction(action, subMenuOption, subSubMenuOption);
                break;
            // MainMenuOption <2>  corresponding to RegularUserTradingMenu.csv
            case 2:
                regularUserTradeAction(action, subMenuOption);
                break;
            // MainMenuOption <3>  corresponding to RegularUserMeetingMenu.csv
            case 3:
                regularUserMeetingAction(action, subMenuOption);
                break;
            // MainMenuOption <4>  corresponding to RegularUserSearchingMainMenu.csv
            case 4:
                regularUserSearchingAction(action, subMenuOption, subSubMenuOption);
                break;
            // MainMenuOption <5>  corresponding to RegularUserCommunityMenu.csv
            case 5:
                regularUserCommunityAction(action, subMenuOption);
                break;
        }
    }

    private void regularUserAccountAction(Action action, int subMenuOption, int subSubMenuOption) {
        switch (subMenuOption) {
            // 1.1 SubMenuOption <1>  corresponding to RegularUserManageItemsMenu.csv
            case 1:
                regularUserManageItemsAction(action, subSubMenuOption);
                break;
            // 1.2 SubMenuOption <2>  corresponding to RegularUserAccountSettingsMenu.csv
            case 2:
                regularUserAccountSettingsAction(action, subSubMenuOption);
                break;
            // 1.3 SubMenuOption <3>  corresponding to RegularUserFollowMenu.csv
            case 3:
                regularUserFollowAction(action, subSubMenuOption);
                break;
        }
    }

    public void regularUserManageItemsAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 1.1.1: Browse all tradable items in the system
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "browse all tradable items in the system");
                break;
            // 1.1.2: Add to own Wish List
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "add Item #" + action.getAdjustableInt() + " to own Wish List" + "\n");
                break;
            // 1.1.3: Remove from own Wish List
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Wish List" + "\n");
                break;
            // 1.1.4: Remove from own Inventory
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Inventory" + "\n");
                break;
            // 1.1.5: Request to add item to your inventory
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "request to add Item #" + action.getAdjustableInt() + "\n");
                break;
            // 1.1.6: See most recent three items traded
            case 6:
                this.printOut(helper_regular_action_prefix(action) + "check most recent three items traded" + "\n");
                break;
            // 1.1.7: View your wishlist and inventory
            case 7:
                this.printOut(helper_regular_action_prefix(action) + "view own wishlist and inventory" + "\n");
                break;
            // 1.1.8: Change tradable status for an inventory item
            case 8:
                this.printOut(helper_regular_action_prefix(action) + "change tradable status of inventory item #"
                        + action.getAdjustableInt() + " into " + action.getAdjustableStr() + "\n");
                break;
            // 1.1.9: Get suggestions for item(s) that you can lend to a given user
            case 9:
                this.printOut(helper_regular_action_prefix(action) + "get suggestions for item(s) that he/she can lend to a given user" + "\n");
                break;
        }
    }

    public void regularUserAccountSettingsAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 1.2.1: Request to unfreeze account
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "request to unfreeze account" + "\n");
                break;
            // 1.2.2: Set your on-vacation status
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "set own on-vacation status into " + action.getAdjustableStr() + "\n");
                break;
            // 1.2.3: Change your home city
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "change home city to " + action.getAdjustableStr() + "\n");
                break;
            // 1.2.4: Review own revocable action
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "review own revocable action" + "\n");
                break;
            // 1.2.5: Request undo a revocable action
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "request undo a revocable action #" + action.getAdjustableInt() + "\n");
                break;
        }
    }

    public void regularUserFollowAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 1.3.1: Follow an user
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "follow user #" + action.getAdjustableInt() + "\n");
                break;
            // 1.3.2: Follow an item
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "follow item #" + action.getAdjustableInt() + "\n");
                break;
            // 1.3.3: See recent status of user(s) you're following
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "check recent status of user(s) he/she follows" + "\n");
                break;
            // 1.3.4: See recent status of item(s) you're following
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "check recent status of item(s) he/she follows" + "\n");
                break;
        }
    }

    private void regularUserTradeAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 2.1: Request a trade
            case 1:
                this.printOut(helper_regular_action_prefix(action) + " request a trade #" + action.getAdjustableInt() + action.getAdjustableStr() + "\n");
                break;
            // 2.2: Respond to trade requests
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "respond to trade requests with Trade #" + action.getAdjustableInt() + "\n");
                break;
            // 2.3: View open trades
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "view open trades" + "\n");
                break;
            // 2.4: View closed trades
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "view closed trades" + "\n");
                break;
            // 2.5: Confirm that a trade has been completed
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "check if the trade #" + action.getAdjustableInt() + " is completed or not" + "\n");
                break;
            // 2.6: See top three most frequent trading partners
            case 6:
                this.printOut(helper_regular_action_prefix(action) + "check the top three most frequent trading partners" + "\n");
                break;
            // 2.7: View transactions that have been cancelled
            case 7:
                this.printOut(helper_regular_action_prefix(action) + "view transactions that have been cancelled" + "\n");
                break;
            // 2.8: Suggestion for the most reasonable trade
            case 8:
                this.printOut(helper_regular_action_prefix(action) + "get the suggestion for the most reasonable trade" + "\n");
                break;
        }
    }


    private void regularUserMeetingAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 3.1: Suggest/confirm time and place for meetings
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "successfully edit/confirm the time and place for meeting #" + action.getAdjustableInt() + "\n");
                break;
            // 3.2: Confirm the meeting took place
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "confirm the meeting #" + action.getAdjustableInt() + "took place" + "\n");
                break;
            // 3.3: See the list of meetings need to be confirmed that it took place
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "see the list of meetings need to be confirmed that it took place" + "\n");
                break;
            // 3.4: See the list of meetings that have been confirmed
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "see the list of meetings that have been confirmed" + "\n");
                break;
            // 3.5: See the list of meetings with time and place that need to be confirmed
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "see the list of meetings with time and place that need to be confirmed" + "\n");
                break;
        }
    }

    private void regularUserSearchingAction(Action action, int subMenuOption, int subSubMenuOption) {
        switch (subMenuOption) {
            // 4.1: SubMenuOption <1>  corresponding to RegularUserSearchingItemsMenu.csv
            case 1:
                regularUserSearchingItemsAction(action, subSubMenuOption);
                break;
            // 4.2: SubMenuOption <2>  corresponding to RegularUserSearchingUsersMenu.csv
            case 2:
                regularUserSearchingUsersAction(action,subSubMenuOption);
                break;
            // 4.3: SubMenuOption <3>  corresponding to RegularUserSearchingMeetingsMenu.csv
            case 3:
                regularUserSearchingMeetingsAction(action, subSubMenuOption);
                break;
            // 4.4: SubMenuOption <4>  corresponding to RegularUserSearchingTradesMenu.csv
            case 4:
                regularUserSearchingTradesAction(action, subSubMenuOption);
                break;
        }
    }

    private void regularUserSearchingItemsAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 4.1.1: Filter by category
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "filter item by category: " + action.getAdjustableStr() + "\n");
                break;
            // 4.1.2: Search item by name
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "search Item with name of " + action.getAdjustableStr() + "\n");
                break;
            // 4.1.3: Search item by id
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "search Item with id of " + action.getAdjustableInt() + "\n");
                break;
            // 4.1.4: Sort by number of follows
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "sort items by number of follows" + "\n");
                break;
        }
    }

    private void regularUserSearchingUsersAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 4.2.1: Recent trade user
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "check recent three users traded with him/her" + "\n");
                break;
            // 4.2.2: Frequent trade user
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "sort all trade partners" + "\n");
                break;
            // 4.2.3: Sort user by rating
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "sort all users by rating" + "\n");
                break;
        }
    }

    private void regularUserSearchingMeetingsAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 4.3.1: Sort by date
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "sort all meeting by date" + "\n");
                break;
            // 4.3.2: Incomplete meeting
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "search for all incomplete meeting" + "\n");
                break;
            // 4.2.3: Complete meeting
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "search for all complete meeting" + "\n");
                break;
        }
    }

    private void regularUserSearchingTradesAction(Action action, int subSubMenuOption) {
        switch (subSubMenuOption) {
            // 4.4.1: Incomplete trades
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "search for all incomplete trade" + "\n");
                break;
            // 4.4.2: Complete trades
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "search for all complete trade" + "\n");
                break;
        }
    }

    private void regularUserCommunityAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 5.1: Write a review for an user
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "rate user #" + action.getAdjustableInt() + " with rating score: " + action.getAdjustableStr() + "\n");
                break;
            // 5.2: Report an user
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "report user #" + action.getAdjustableInt() + " with reason: " + action.getAdjustableStr() + "\n");
                break;
            // 5.3: Find the rating for a given user
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "search the rate of user #" + action.getAdjustableInt() + "\n");
                break;
            // 5.4: See users in your home city
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "check the users in his/her home city" + "\n");
                break;
            // 5.5: View your list of friends
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "view his/her own friends list" + "\n");
                break;
            // 5.6: Send a friend request for a given user
            case 6:
                this.printOut(helper_regular_action_prefix(action) + "send friend request to other user #" + action.getAdjustableInt() + "\n");
                break;
            // 5.7: Respond to friend requests
            case 7:
                this.printOut(helper_regular_action_prefix(action) + "allow to add user #" + action.getAdjustableInt() + " with the username: " + action.getAdjustableStr() + " as friend" + "\n");
                break;
            // 5.8: Unfriend a user
            case 8:
                this.printOut(helper_regular_action_prefix(action) + "unfriend user #" + action.getAdjustableInt() + " with the username: " + action.getAdjustableStr() + "\n");
                break;
            // 5.9: Send message to friends
            case 9:
                this.printOut(helper_regular_action_prefix(action) + "send message to friend #" + action.getAdjustableInt() + " : " + action.getAdjustableStr() + "\n");
                break;
            // 5.10: View all message
            case 10:
                this.printOut(helper_regular_action_prefix(action) + "view all message" + "\n");
                break;
        }
    }


    private void adminUserAction(Action action) {
        String[] menuOption = action.getMenuOption().split("\\.");
        int mainMenuOption = Integer.parseInt(menuOption[0]);
        int subMenuOption = Integer.parseInt(menuOption[1]);

        switch (mainMenuOption) {
            // MainMenuOption <1>  corresponding to AdminUserManageUsersSubMenu.csv
            case 1:
                adminUserManageUsersAction(action, subMenuOption);
                break;
            // MainMenuOption <2>  corresponding to AdminUserEditThresholdsSubMenu.csv
            case 2:
                adminUserEditThresholdsAction(action, subMenuOption);
                break;
            // MainMenuOption <3>  corresponding to AdminUserHistoricalActionSubMenu.csv
            case 3:
                adminUserActionAction(action, subMenuOption);
                break;
            // MainMenuOption <4>  corresponding to AdminUserOtherSubMenu.csv
            case 4:
                adminUserOtherAction(action, subMenuOption);
                break;
        }
    }

    private void adminUserManageUsersAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 1.1: Freeze tradableUsers
            case 1:
                this.printOut(helper_admin_action_prefix(action) + "freeze tradableUser #" + action.getAdjustableInt() + " with username: " + action.getAdjustableStr() + "\n");
                break;
            // 1.2: Unfreeze tradableUsers
            case 2:
                this.printOut(helper_admin_action_prefix(action) + "unfreeze tradableUser #" + action.getAdjustableInt() + " with username: " + action.getAdjustableStr() + "\n");
                break;
            // 1.3: Confirm and add item to tradableUser’s inventory
            case 3:
                this.printOut(helper_admin_action_prefix(action) + "confirm and add item #" + action.getAdjustableInt() + " into inventory of Regular User #" + Integer.parseInt(action.getAdjustableStr()) + "\n");
                break;
        }
    }

    private void adminUserEditThresholdsAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 2.1: Edit the max number of transactions allowed a week
            case 1:
                this.printOut(helper_admin_action_prefix(action) + "edit <the max number of transactions allowed a week> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n");
                break;
            // 2.2: Edit the max number of transactions that can be incomplete before the account is frozen
            case 2:
                this.printOut(helper_admin_action_prefix(action) + "edit <the max number of transactions that can be incomplete before the account is frozen> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n");
                break;
            // 2.3: Edit the number of books tradableUsers must lend before tradableUsers can borrow
            case 3:
                this.printOut(helper_admin_action_prefix(action) + "edit <the number of books tradableUsers must lend before tradableUsers can borrow> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n");
                break;
            // 2.4: Edit the max Edits per user for meeting’s date + time
            case 4:
                this.printOut(helper_admin_action_prefix(action) + "edit <the max Edits per user for meeting’s date + time> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr()) + "\n");
                break;
        }
    }

    private void adminUserActionAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 3.1: List all the historical actions in the system
            case 1:
                this.printOut(helper_admin_action_prefix(action) + "list all the historical actions in the system" + "\n");
                break;
            // 3.2: List all the historical revocable actions in the system
            case 2:
                this.printOut(helper_admin_action_prefix(action) + "list all the historical revocable actions in the system" + "\n");
                break;
            // 3.3: Find all the revocable historical actions of specific tradableUser
            case 3:
                this.printOut(helper_admin_action_prefix(action) + "search all the revocable historical actions of tradableUser #" + action.getAdjustableInt() + "\n");
                break;
            // 3.4: Cancel the revocable historical actions of tradableUser by actionID
            case 4:
                this.printOut(helper_admin_action_prefix(action) + "cancel the revocable historical actions #" + action.getAdjustableInt() + "\n");
                break;
            // 3.4: Confirm undo request and undo revocable historical actions
            case 5:
                this.printOut(helper_admin_action_prefix(action) + "confirm undo request and undo revocable historical action #" + action.getAdjustableInt() + "\n");
                break;
        }
    }

    private void adminUserOtherAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 4.1: Add subsequent admin Users
            case 1:
                this.printOut(helper_admin_action_prefix(action) + "add subsequent Admin Users with username: " + action.getAdjustableStr() + "\n");
                break;
        }
    }

    private String helper_regular_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": RegularUser #" + action.getActionOwnerID() + " ";
    }

    private String helper_admin_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": AdminUser #" + action.getActionOwnerID() + " ";
    }

    /**
     * Print this when the username does not exist
     */

    public void printWrongUsername() {
        this.printOut("This username doesn't exists, please check again or crate one");
    }

    /**
     * Print this when the password is not correct
     */

    public void printWrongPassword() {
        this.printOut("This password is not correct, please check again");
    }
}