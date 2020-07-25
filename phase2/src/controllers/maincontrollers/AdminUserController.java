package controllers.maincontrollers;

import controllers.AccountCreator;
import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoGetter;
import gateway.FilesReaderWriter;
import managers.actionmanager.Action;
import managers.actionmanager.ActionManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

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

    private AdminUserOtherInfoGetter otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private AdminUserManagerUsersController muc;
    private FilesReaderWriter frw;

    /**
     * Constructs the AdminUserController with a AccountCreator, DisplaySystem,
     * FilesReadWriter, an UserManager, an ItemManager and an adminUserId.
     * @param ac The controller class used to create an account.
     * @param ds The presenter class used to print to screen.
     * @param im The current state of the ItemManager.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     */
    public AdminUserController(AccountCreator ac, DisplaySystem ds, UserManager um, ItemManager im, ActionManager am,
                               AdminUserManagerUsersController muc) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.muc = muc;
        this.sm = new SystemMessage();
        this.otherInfoGetter = new AdminUserOtherInfoGetter(ds);
    }


    /**
     * Calls appropriate methods based on user input of
     * the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the admin user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the admin user.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @throws IOException In case the file can't be found.
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption, String thresholdValuesFilePath) throws IOException {
        switch(mainMenuOption){
            case 1:
                adminManageUsersMenuResponse(subMenuOption);
                break;
            case 2:
                adminEditThresholdMenuResponse(subMenuOption, thresholdValuesFilePath);
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
          4. Cancel the historical action of tradableUser
         */

        switch (subMenuOption) {
            case 1:
                muc.freezeUser();
                break;
            case 2:
                muc.unfreezeUser();
                break;
            case 3:
                muc.confirmInventoryAdd();
                break;
            case 4:

                break;
        }
    }


    private void adminEditThresholdMenuResponse(int subMenuOption, String thresholdValuesFilePath) throws IOException {
        /*
        1.Edit the max number of transactions allowed a week
        2.Edit the max number of transactions that can be incomplete before the account is frozen
        3.Edit the number of books users must lend before users can borrow
        4.Edit the max Edits per user for meeting’s date + time
         */
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
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
        frw.saveThresholdValuesToCSVFile(thresholdValues, thresholdValuesFilePath);
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
