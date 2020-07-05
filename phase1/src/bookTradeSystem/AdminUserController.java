package bookTradeSystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 */
public class AdminUserController implements Serializable, Controllable {

    private AccountCreator ac; //instead of this maybe make the tradingSystem's one protected?
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected?
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected?
    private UserManager um;

    /**
     * Constructs the AdminUserController with a AccountCreator, DisplaySystem,
     * FilesReadWriter, UserManager, and an adminUserId.
     * @param ac The controller class used to create an account.
     * @param ds The presenter class used to print to screen.
     * @param rw The gateway class used to read or write to file.
     * @param um The current state of the UserManager.
     */
    public AdminUserController(AccountCreator ac, DisplaySystem ds,
                               FilesReaderWriter rw, UserManager um) {
        this.ac = ac;
        this.ds = ds;
        this.rw = rw;
        this.um = um;
    }


    // TODO: move to a presenter class
    /**
     * This method gathers all the necessary notifications
     * for the admin user.
     * @return Notifications as properly formatted strings.
     * @throws FileNotFoundException In case the file can't be found.
     */
    @Override
    public String alerts() throws IOException {
        //Read this in from file
        //Exception needs to be resolved in main or TradingSystem.
        //String filepath = "./src/bookTradeSystem/AdminAlerts.csv";
        String filepath = "./src/Alerts/AdminAlerts.csv"; // move it to src and not the bookTradeSystem
        return rw.readFromMenu(filepath);
    }

    /**
     * This method calls appropriate methods based on user input of
     * the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the admin user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the admin user.
     * @throws FileNotFoundException In case the file can't be found.
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) throws IOException {
         /*
        1. decide the menu options
        2. decide what use case method to call for each menu option (discuss with Gabriel)
        3. decide what presenter method to call to print the results for each menu option (discuss with Jiaqi)

        */
        switch(mainMenuOption){
            case 1:
                adminManageUsersMenuResponse(subMenuOption);
                break;
            case 2:
                adminEditThresholdMenuResponse(subMenuOption);
                break;
            case 3:
                adminOthersMenuResponse(subMenuOption);
                break;

        }

    }

    private void adminManageUsersMenuResponse(int subMenuOption) {
        /*1. Freeze a user
          2. Unfreeze users
          3. Confirm and add item to user’s inventory
         */

        switch (subMenuOption) {
            case 1:
//              asks the admin for the username of the user TO FREEZE
                ds.printOut("Please enter the username of the user to FREEZE");
//              TODO: maybe do a bulletproof for username for later? (later)
//              let presenter print the msg of successful or not
                ds.printResult(um.freezeUser(ds.getUsername()));
                break;
            case 2:
                //TODO: THE FOLLOWING LINE IS FOR TEST!!!
                //TODO NOTE: getListAdmin is empty
                ds.printResult(um.getListUser().size() != 0);
                ds.printResult(um.getListAdmin().size() == 0);
                for (User u: um.getListUser()) {
                    ds.printOut(u.getUsername());
                }
                for (AdminUser u: um.getListAdmin()){
                    ds.printOut(u.getUsername());
                }
                //TODO: THE ABOVE LINE IS FOR TEST!!!
//              asks the admin for the username of the user to UNFREEZE
                ds.printOut("Please enter the username of the user to UNFREEZE");
//              TODO: maybe do a bulletproof for username for later? (later)
//              let presenter print the msg of successful or not
                ds.printResult(um.unfreezeUser(ds.getUsername()));
                break;
            case 3:
                ArrayList<Item> listItemToAdd = um.getListItemToAdd();
                int len = listItemToAdd.size();
//              get the list of item to be added to inventories
//              TODO: make sure items are printed one by one ;) (***)
                ds.printResult(new ArrayList<Object>(listItemToAdd));
//              TODO: maybe can improve printResult method by adding # to each thing? start w 1(***)
//              TODO: maybe add a loop so admin can keep on entering ... until chooses to exit? (later)
                Item itemSelected = listItemToAdd.get(getItem(len)-1);
                if (getAddOrNot()){
                    //if add
                    ds.printResult(um.addItemInventory(itemSelected, um.idToUsername(itemSelected.getOwnerId())));
                }
                else{
                    ds.printResult(true);
                }
                //either add or not add - need to remove from to-be-added list
//              TODO: can I just remove like this? (later)
//              TODO: need a method to remove item from um's getListItemToAdd
                um.getListItemToAdd().remove(itemSelected);
                break;


        }
    }

    private void adminEditThresholdMenuResponse(int subMenuOption) {
        /*
        1.Edit the max number of transactions allowed a week
        2.Edit the max number of transactions that can be incomplete before the account is frozen
        3.Edit the number of books users must lend before users can borrow
        4.Edit the max Edits per user for meeting’s date + time
         */
        switch (subMenuOption) {
            case 1:
                printCurrentValue(User.getMaxNumTransactionsAllowedAWeek());
                User.setMaxNumTransactionsAllowedAWeek(getThresholdAns());
                break;
            case 2:
                printCurrentValue(User.getMaxNumTransactionIncomplete());
                User.setMaxNumTransactionIncomplete(getThresholdAns());
                break;
            case 3:
                printCurrentValue(User.getNumLendBeforeBorrow());
                User.setNumLendBeforeBorrow(getThresholdAns());
                break;
            case 4:
                printCurrentValue(User.getMaxMeetingDateTimeEdits());
                User.setMaxMeetingDateTimeEdits(getThresholdAns());
                break;
        }
        ds.printResult(true);
    }

    private void adminOthersMenuResponse(int subMenuOption) throws IOException {
        /*
        1. Add subsequent admin users
         */
        if (subMenuOption == 1){
            ds.printResult(this.ac.createAccount("Admin"));
        }

    }

    /**
     * Other ask-user-for-input methods
     */

    //TODO: MOVE TO PRESENTER
    private void printCurrentValue(int currentVal){
        ds.printOut("The current value is " + currentVal);
    }

    private int getThresholdAns(){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int thresholdAns = 0;
        do{
            ds.printOut("Enter the new threshold value: ");
            if(sc.hasNextInt()){
                thresholdAns = sc.nextInt();
                okInput = true;
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return thresholdAns;
    }

    private int getItem(int numItemsToAdd){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        // asks for the item name, description, owner id of the user to be added
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        // does not store the number of items but the number of the item the admin chooses
        int numItem = 0;
        do{
            ds.printOut("Enter the number of the item in the above list ");
            if(sc.hasNextInt()){
                numItem = sc.nextInt();
                if (1<= numItem && numItem <= numItemsToAdd) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper option please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return numItem;
    }

    private boolean getAddOrNot(){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int num = 0;
        do{
            ds.printOut("Please enter a number (1 - add, 2 - not add): ");
            if(sc.hasNextInt()){
                num = sc.nextInt();
                if (num == 1 || num == 2) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper option please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return num == 1;

    }

}
