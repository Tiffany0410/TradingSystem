package Controllers.MainControllers;

import Managers.UserManager.UserManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserController implements Controllable {

    private bookTradeSystem.AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private FilesReaderWriter rw;
    private Managers.UserManager.UserManager um;

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
        this.sm = new SystemMessage();
        this.otherInfoGetter = new bookTradeSystem.AdminUserOtherInfoGetter(ds);
    }


    /**
     * Calls appropriate methods based on user input of
     * the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the admin user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the admin user.
     * @throws IOException In case the file can't be found.
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) throws IOException {
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
//              let presenter print the msg of successful or not
                ds.printResult(um.freezeUser(ds.getUsername()));
                break;
            case 2:
                ds.printOut("Here's the list of user who request to be unfrozen:");
                ds.printResult(new ArrayList<>(um.getListUnfreezeRequest()));
//              asks the admin for the username of the user to UNFREEZE
                ds.printOut("Please enter the username of the user to UNFREEZE");
//              let presenter print the msg of successful or not
                ds.printResult(um.unfreezeUser(ds.getUsername()));
                break;
            case 3:
                ArrayList<Item> listItemToAdd = um.getListItemToAdd();
                int len = listItemToAdd.size();
                responseToToAddListSize(listItemToAdd, len);
                break;


        }
    }

    // create another class and move them for phase 2
    private void responseToToAddListSize(ArrayList<Item> listItemToAdd, int len) {
        if (len != 0) {
            // get the list of item to be added to inventories
            ds.printResult(new ArrayList<>(listItemToAdd));
            Item itemSelected = listItemToAdd.get(otherInfoGetter.getItem(len) - 1);
            addOrNotAdd(itemSelected);
            //either add or not add - need to remove from to-be-added list
            //need a method to remove item from um's getListItemToAdd (***)
            um.getListItemToAdd().remove(itemSelected);
        }
        else{
            // print systemMessage's there's nothing here method
            sm.msgForNothing(ds);
        }
    }

    private void addOrNotAdd(Item itemSelected) {
        if (otherInfoGetter.getAddOrNot()) {
            //if add
            um.addItemToAllItemsList(itemSelected);
            ds.printResult(um.addItemInventory(itemSelected, um.idToUsername(itemSelected.getOwnerId())));
        } else {
            ds.printResult(true);
        }
    }

    private void adminEditThresholdMenuResponse(int subMenuOption) throws IOException {
        /*
        1.Edit the max number of transactions allowed a week
        2.Edit the max number of transactions that can be incomplete before the account is frozen
        3.Edit the number of books users must lend before users can borrow
        4.Edit the max Edits per user for meeting’s date + time
         */
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        switch (subMenuOption) {
            case 1:
                sm.msgForThresholdValue(thresholdValues.get(0),ds);
                // editMaxNumTransactionsAllowedAWeek
                thresholdValues.set(0, otherInfoGetter.getThresholdAns());
                break;
            case 2:
                sm.msgForThresholdValue(thresholdValues.get(1),ds);
                // editMaxNumTransactionIncomplete
                thresholdValues.set(1, otherInfoGetter.getThresholdAns());
                break;
            case 3:
                sm.msgForThresholdValue(thresholdValues.get(2),ds);
                // editNumLendBeforeBorrow
                thresholdValues.set(2, otherInfoGetter.getThresholdAns());
                break;
            case 4:
                sm.msgForThresholdValue(thresholdValues.get(3),ds);
                //editMaxMeetingDateTimeEdits
                thresholdValues.set(3, otherInfoGetter.getThresholdAns());
                break;
        }
        rw.saveThresholdValuesToCSVFile(thresholdValues, "./src/Others/ThresholdValues.csv");
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

}
