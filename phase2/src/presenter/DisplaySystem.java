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
                    switch (menuOption) {
                        // 1.1: Browse all tradable items in the system
                        case "1.1":
                            this.printOut(helper_regular_action_prefix(action) + "browse all tradable items in the system");
                            break;
                        // 1.2: Add to own Wish List
                        case "1.2":
                            this.printOut(helper_regular_action_prefix(action) + "add Item #" + action.getAdjustableInt() + " to own Wish List" + "\n");
                            break;
                        // 1.3: Search item
                        case "1.3":
                            this.printOut(helper_regular_action_prefix(action) + "search Item with name of " + action.getAdjustableStr() + "\n");
                            break;
                        // 1.4: Remove from own Wish List
                        case "1.4":
                            this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Wish List" + "\n");
                            break;
                        // 1.5: Remove from own Inventory
                        case "1.5":
                            this.printOut(helper_regular_action_prefix(action) + "remove Item #" + action.getAdjustableInt() + " from own Inventory" + "\n");
                            break;
                        // 1.6: Request to unfreeze account
                        case "1.6":
                            this.printOut(helper_regular_action_prefix(action) + "request to unfreeze account" + "\n");
                            break;
                        // 1.7: Request to add item to your inventory
                        case "1.7":
                            this.printOut(helper_regular_action_prefix(action) + "request to add Item #" + action.getAdjustableInt() + "\n");
                            break;
                        // 1.8: See most recent three items traded
                        case "1.8":
                            this.printOut(helper_regular_action_prefix(action) + "check most recent three items traded" + "\n");
                            break;
                        // 1.9: View your wishlist and inventory
                        case "1.9":
                            this.printOut(helper_regular_action_prefix(action) + "view own wishlist and inventory" + "\n");
                            break;
                        // 1.10: Set your on-vacation status
                        case "1.10":
                            this.printOut(helper_regular_action_prefix(action) + "set own on-vacation status into " + action.getAdjustableStr() + "\n");
                            break;
                        // 1.11: Change tradable status for an inventory item
                        case "1.11":
                            this.printOut(helper_regular_action_prefix(action) + "change tradable status of inventory item #"
                                    + action.getAdjustableInt() + " into " + action.getAdjustableStr() + "\n");
                            break;
                        // 2.1: Request a trade
                        case "2.1":
                            this.printOut(helper_regular_action_prefix(action) + " request a trade" + "\n");
                            break;
                        //
                        //
                        //
                        //
                        //
                        // 2.5: Confirm that a trade has been completed
                        case "2.5":
                            this.printOut(helper_regular_action_prefix(action)  + " confirm that a trade has been completed" + "\n");
                            break;
                        // 3.2: Confirm time and place for meetings
                        case "3.2":
                            this.printOut(helper_regular_action_prefix(action) + "confirm time and place for meetings" + "\n");
                            break;
                        // 3.3: Confirm the meeting took place
                        case "3.3":
                            this.printOut(helper_regular_action_prefix(action) + "confirm the meeting took place" + "\n");
                            break;
            }
                case "adminUser":
                    switch (menuOption) {
                        case "1.1":
                            break;
                        case "1.2":
                            break;
                        case "1.3":
                            break;
                    }

            }
        }

    }


    private String helper_regular_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": RegularUser #" + action.getActionOwnerID() + " ";
    }

    private String helper_admin_action_prefix(Action action) {
        return "Action #" + action.getActionID() + ": AdminrUser #" + action.getActionOwnerID() + " ";
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
