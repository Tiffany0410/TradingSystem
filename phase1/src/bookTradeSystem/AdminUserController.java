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

    private AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
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
        this.sm = new SystemMessage();
        this.otherInfoGetter = new AdminUserOtherInfoGetter(ds);
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
//              let presenter print the msg of successful or not
                ds.printResult(um.freezeUser(ds.getUsername()));
                break;
            case 2:
//              asks the admin for the username of the user to UNFREEZE
                ds.printOut("Please enter the username of the user to UNFREEZE");
//              let presenter print the msg of successful or not
                ds.printResult(um.unfreezeUser(ds.getUsername()));
                break;
            case 3:
                ArrayList<Item> listItemToAdd = um.getListItemToAdd();
                int len = listItemToAdd.size();
                ResponseToToAddListSize(listItemToAdd, len);
                break;


        }
    }

    // create another class and move them for phase 2
    private void ResponseToToAddListSize(ArrayList<Item> listItemToAdd, int len) {
        if (len != 0) {
//              get the list of item to be added to inventories
            ds.printResult(new ArrayList<Object>(listItemToAdd));
            Item itemSelected = listItemToAdd.get(otherInfoGetter.getItem(len) - 1);
            addOrNotAdd(itemSelected);
            //either add or not add - need to remove from to-be-added list
//          TODO: need a method to remove item from um's getListItemToAdd
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

    private void adminEditThresholdMenuResponse(int subMenuOption) {
        /*
        1.Edit the max number of transactions allowed a week
        2.Edit the max number of transactions that can be incomplete before the account is frozen
        3.Edit the number of books users must lend before users can borrow
        4.Edit the max Edits per user for meeting’s date + time
         */
        switch (subMenuOption) {
            case 1:
                sm.msgForThresholdValue(User.getMaxNumTransactionsAllowedAWeek(),ds);
                um.editMaxNumTransactionsAllowedAWeek(otherInfoGetter.getThresholdAns());
                break;
            case 2:
                sm.msgForThresholdValue(User.getMaxNumTransactionIncomplete(),ds);
                um.editMaxNumTransactionIncomplete(otherInfoGetter.getThresholdAns());
                break;
            case 3:
                sm.msgForThresholdValue(User.getNumLendBeforeBorrow(),ds);
                um.editNumLendBeforeBorrow(otherInfoGetter.getThresholdAns());
                break;
            case 4:
                sm.msgForThresholdValue(User.getMaxMeetingDateTimeEdits(),ds);
                um.editMaxMeetingDateTimeEdits(otherInfoGetter.getThresholdAns());
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

}
