package presenter;

import gateway.FilesReaderWriter;
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

    public DisplaySystem() throws IOException, ClassNotFoundException {
        fileReader = new FilesReaderWriter();
    }

    /**
     * print the put in sentence to the screen
     * convinces to change from txt to window
     * @param str the string need to be print out
     */
    public void printOut(String str){
        System.out.println(str);
    }


    /**
     * Print menu and get the option user put in
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
                if (0 <=option && option <= menuLength){
                    break;
                }
                else{
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
     * @param filePath the path of file need to be read
     */

    private void printMenu(String filePath) throws IOException {
        this.printOut(fileReader.readFromMenu(filePath));
    }


    /**
     * Get the username user put in
     * @return username
     */

    public String getUsername(){
        String userName;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter user name");
        userName = sc.nextLine();  // get username user typed in
        return userName;

    }

    /**
     * Get the password user put in
     * @return password
     */

    public String getPassword(){
        String password;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter password");
        password = sc.nextLine();  // get password user typed in
        return password;
    }

    /**
     * Get email address user put in
     */

    public String getEmail(){
        String emailAddress;
        Scanner sc = new Scanner(System.in);
        this.printOut("Please enter your email address");
        emailAddress = sc.nextLine();  // get email address user typed in
        return emailAddress;
    }


    /**
     * Print out the list of username of the Regular Users
     *
     * @param listOfUser the list of Regular Users
     */
    public void printListUser(ArrayList<TradableUser> listOfUser) {
        for (TradableUser user: listOfUser) {
            this.printOut("#" + user.getId() + ". " + user.getUsername() + "\n");
        }
    }


    /**
     * Print out the result of action with boolean type
     * @param result the result of user's operation
     */
    public void printResult(boolean result){
        if (result){
            printOut("Success");
        }else{
            printOut("Fail");
        }
        printOut("\n");
    }

    /**
     * print out the result of action with object type
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
                String[] strings = (String[])o;
                this.printOut("#" + count + ". " + "\n" + "Username: " + strings[0]);
                this.printOut("Message: " + strings[1] + "\n");
            }
            count++;
        }
    }

    /**
     * print out the result of action with string and boolean type
     * @param str the sentence want to present
     * @param result the result of the action
     */

    public void printResult(String str, boolean result){
        if (result){
            printOut(str + " is sent successfully");
            printOut("\n");
        }else{
            printOut(str + " fails to be sent");
            printOut("\n");
        }
    }


    /**
     * Print out all historical Action which can be cancelled
     */
    public void printHistoricalActions(ArrayList<Action> listOfAction) {
        for (Action action: listOfAction) {
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

        switch (mainMenuOption) {
            // MainMenuOption <1>  corresponding to RegularUserAccountMenu.csv
            case 1:
                regularUserAccountAction(action, subMenuOption);
                break;
            // MainMenuOption <2>  corresponding to RegularUserTradingMenu.csv
            case 2:
                regularUserTradeAction(action, subMenuOption);
                break;
            // MainMenuOption <3>  corresponding to RegularUserMeetingMenu.csv
            case 3:
                regularUserMeetingAction(action, subMenuOption);

        }
    }

    private void regularUserAccountAction(Action action, int subMenuOption) {
        switch (subMenuOption){
            // 1.1: Browse all tradable items in the system
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "browse all tradable items in the system");
                break;
            // 1.2: Add to own Wish List
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "add Item #" + action.getAdjustableInt() + " to own Wish List" + "\n");
                break;
            // 1.3: Search item
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "search Item with name of " + action.getAdjustableStr() + "\n");
                break;
            // 1.4: Remove from own Wish List
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Wish List" + "\n");
                break;
            // 1.5: Remove from own Inventory
            case 5:
                this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Inventory" + "\n");
                break;
            // 1.6: Request to unfreeze account
            case 6:
                this.printOut(helper_regular_action_prefix(action) + "request to unfreeze account" + "\n");
                break;
            // 1.7: Request to add item to your inventory
            case 7:
                this.printOut(helper_regular_action_prefix(action) + "request to add Item #" + action.getAdjustableInt() + "\n");
                break;
            // 1.8: See most recent three items traded
            case 8:
                this.printOut(helper_regular_action_prefix(action) + "check most recent three items traded" + "\n");
                break;
            // 1.9: View your wishlist and inventory
            case 9:
                this.printOut(helper_regular_action_prefix(action) + "view own wishlist and inventory" + "\n");
                break;
            // 1.10: Set your on-vacation status
            case 10:
                this.printOut(helper_regular_action_prefix(action) + "set own on-vacation status into " + action.getAdjustableStr() + "\n");
                break;
            // 1.11: Change tradable status for an inventory item
            case 11:
                this.printOut(helper_regular_action_prefix(action) + "change tradable status of inventory item #"
                        + action.getAdjustableInt() + " into " + action.getAdjustableStr() + "\n");
                break;
            // 1.12: See users in your home city
            case 12:
                this.printOut(helper_regular_action_prefix(action) + "check the users in his/her home city" + "\n");
                break;
            // 1.13: Change your home city
            case 13:
                this.printOut(helper_regular_action_prefix(action) + "change home city to " + action.getAdjustableStr() + "\n");
                break;
            // 1.14: Get suggestions for item(s) that you can lend to a given user
            case 14:
                this.printOut(helper_regular_action_prefix(action) + "get suggestions for item(s) that he/she can lend to a given user" + "\n");
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
                this.printOut(helper_regular_action_prefix(action)  + "check if the trade #" + action.getAdjustableInt() + " is completed or not" + "\n");
                break;
            // 2.6: See top three most frequent trading partners
            case 6:
                this.printOut(helper_regular_action_prefix(action)  + "check the top three most frequent trading partners" + "\n");
                break;
            // 2.7: View transactions that have been cancelled
            case 7:
                this.printOut(helper_regular_action_prefix(action)  + "view transactions that have been cancelled" + "\n");
                break;
            // 2.8: Suggestion for the most reasonable trade
            case 8:
                this.printOut(helper_regular_action_prefix(action) + "get the suggestion for the most reasonable trade" + "\n");
                break;
        }
    }


    private void regularUserMeetingAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 3.1: Suggest/edit time and place for meetings
            case 1:
                this.printOut(helper_regular_action_prefix(action) + "successfully edit the time and place for meeting #" + action.getAdjustableInt() + "\n");
                break;
            // 3.2: Confirm time and place for meetings
            case 2:
                this.printOut(helper_regular_action_prefix(action) + "successfully confirm time and place for meeting #" + action.getAdjustableInt() + "\n");
                break;
            // 3.3: Confirm the meeting took place
            case 3:
                this.printOut(helper_regular_action_prefix(action) + "confirm the meeting #" + action.getAdjustableInt() + "took place" + "\n");
                break;
            // 3.4: See the list of meetings need to be confirmed that it took place
            case 4:
                this.printOut(helper_regular_action_prefix(action) + "see the list of meetings need to be confirmed that it took place" + "\n");
                break;
            // 3.5: See the list of meetings that have been confirmed
            case 5:
                this.printOut(helper_regular_action_prefix(action)  + "see the list of meetings that have been confirmed" + "\n");
                break;
            // 3.6: See the list of meetings with time and place that need to be confirmed
            case 6:
                this.printOut(helper_regular_action_prefix(action)  + "see the list of meetings with time and place that need to be confirmed" + "\n");
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
            // MainMenuOption <3>  corresponding to AdminUserActionSubMenu.csv
            case 3:
                adminUserActionAction(action, subMenuOption);
            // MainMenuOption <4>  corresponding to AdminUserOtherSubMenu.csv
            case 4:
                adminUserOtherAction(action, subMenuOption);
        }
    }

    private void adminUserManageUsersAction(Action action, int subMenuOption) {
        switch (subMenuOption) {
            // 1.1: Freeze tradableUsers
            case 1:
                this.printOut(helper_admin_action_prefix(action) + "freeze tradableUser #"+ action.getAdjustableInt() + " with username: " + action.getAdjustableStr() + "\n");
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
                    this.printOut(helper_admin_action_prefix(action) + "edit <the max number of transactions allowed a week> from " + action.getAdjustableInt() + " to " + Integer.parseInt(action.getAdjustableStr())+ "\n");
                    break;
                // 2.2: Edit the max number of transactions that can be incomplete before the account is frozen
                case 2:
                    this.printOut(helper_admin_action_prefix(action) + "edit <the max number of transactions that can be incomplete before the account is frozen> from " + action.getAdjustableInt()  + " to " + Integer.parseInt(action.getAdjustableStr())+ "\n");
                    break;
                // 2.3: Edit the number of books tradableUsers must lend before tradableUsers can borrow
                case 3:
                    this.printOut(helper_admin_action_prefix(action) + "edit <the number of books tradableUsers must lend before tradableUsers can borrow> from " + action.getAdjustableInt()  + " to " + Integer.parseInt(action.getAdjustableStr())+ "\n");
                    break;
                // 2.4: Edit the max Edits per user for meeting’s date + time
                case 4:
                    this.printOut(helper_admin_action_prefix(action) + "edit <the max Edits per user for meeting’s date + time> from " + action.getAdjustableInt()  + " to " + Integer.parseInt(action.getAdjustableStr())+ "\n");
                    break;}
    }

    private void adminUserActionAction(Action action, int subMenuOption) {
            switch (subMenuOption) {
                // 3.1: List all the historical actions in the system
                case 1:
                    this.printOut(helper_admin_action_prefix(action) + "list all the historical actions in the system" + "\n");
                    break;
                // 3.2: Cancel the revocable historical actions of tradableUser
                case 2:
                    this.printOut(helper_admin_action_prefix(action) + "cancel the revocable historical actions #"+ action.getAdjustableInt() + "\n");
                    break;
            }
    }

    private void adminUserOtherAction(Action action, int subMenuOption) {
            switch (subMenuOption) {
                // 4.1: Add subsequent admin Users
                case 1:
                    this.printOut(helper_admin_action_prefix(action) + "add subsequent Admin Users #" + action.getAdjustableInt() + "\n");
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

    public void printWrongUsername(){
        this.printOut("This username doesn't exists, please check again or crate one");
    }

    /**
     * Print this when the password is not correct
     */

    public void printWrongPassword(){
        this.printOut("This password is not correct, please check again");
    }
}
