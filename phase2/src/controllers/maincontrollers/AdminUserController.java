package controllers.maincontrollers;

import controllers.AccountCreator;
import controllers.adminusersubcontrollers.AdminUserHistoricalActionController;
import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.adminusersubcontrollers.AdminUserOtherInfoGetter;
import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.itemmanager.ItemManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.io.IOException;
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
    private AdminUserHistoricalActionController hac;
    private FilesReaderWriter frw;
    private int userId;

    /**
     * Constructs the AdminUserController with a AccountCreator, DisplaySystem,
     * FilesReadWriter, an UserManager, an ItemManager and an adminUserId.
     * @param ac The controller class used to create an account.
     * @param ds The presenter class used to print to screen.
     * @param im The current state of the ItemManager.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserController(AccountCreator ac, DisplaySystem ds, UserManager um, ItemManager im, ActionManager am,
                               String username) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.am = am;
        this.muc = muc;
        this.hac = hac;
        this.userId = um.usernameToID(username);
        this.sm = new SystemMessage();
        this.muc = new AdminUserManagerUsersController();
        this.hac = new AdminUserHistoricalActionController()
        this.otherInfoGetter = new AdminUserOtherInfoGetter(ds, am, um);
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
    public void actionResponse(int mainMenuOption, int subMenuOption, String thresholdValuesFilePath) throws IOException, InvalidIdException {
        switch(mainMenuOption){
            case 1:
                adminManageUsersMenuResponse(subMenuOption);
                break;
            case 2:
                adminEditThresholdMenuResponse(subMenuOption, thresholdValuesFilePath);
                break;
            case 3:
                adminUserActionResponse(subMenuOption);
                break;
            case 4:
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
                int currentValue1 = thresholdValues.get(0);
                sm.msgForThresholdValue(currentValue1, ds);
                int futureValue1 = otherInfoGetter.getThresholdAns();
                // editMaxNumTransactionsAllowedAWeek
                thresholdValues.set(0, futureValue1);
                am.addActionToAllActionsList(this.userId, "adminUser", "2.1", currentValue1, String.valueOf(futureValue1));
                break;
            case 2:
                int currentValue2 = thresholdValues.get(1);
                sm.msgForThresholdValue(currentValue2,ds);
                int futureValue2 = otherInfoGetter.getThresholdAns();
                // editMaxNumTransactionIncomplete
                thresholdValues.set(1, futureValue2);
                am.addActionToAllActionsList(this.userId, "adminUser", "2.2", currentValue2, String.valueOf(futureValue2));
                break;
            case 3:
                int currentValue3 = thresholdValues.get(2);
                sm.msgForThresholdValue(currentValue3,ds);
                int futureValue3 = otherInfoGetter.getThresholdAns();
                // editNumLendBeforeBorrow
                thresholdValues.set(2, futureValue3);
                am.addActionToAllActionsList(this.userId, "adminUser", "2.3", currentValue3, String.valueOf(futureValue3));
                break;
            case 4:
                int currentValue4 = thresholdValues.get(3);
                sm.msgForThresholdValue(currentValue4,ds);
                int futureValue4 = otherInfoGetter.getThresholdAns();
                //editMaxMeetingDateTimeEdits
                thresholdValues.set(3, futureValue4);
                am.addActionToAllActionsList(this.userId, "adminUser", "2.4", currentValue4, String.valueOf(futureValue4));
                break;
        }
        frw.saveThresholdValuesToCSVFile(thresholdValues, thresholdValuesFilePath);
        ds.printResult(true);
    }

    private void adminUserActionResponse(int subMenuOption) throws InvalidIdException {
        /*
        1.List all the historical actions in the system
        2.Cancel the revocable historical actions of tradableUser
        3.Find all the revocable historical actions of specific tradableUser
         */
        switch (subMenuOption) {
            case 1:
                hac.printOutAllHistorialAction();
                break;
            case 2:
                hac.cancelRevocableAction();
                break;
            case 3:
                hac.searchRevocableActionByUserID();
                break;
        }
    }

    private void adminOthersMenuResponse(int subMenuOption) throws IOException {
        /*
        1. Add subsequent admin users
         */
        if (subMenuOption == 1){
            ds.printResult(this.ac.createAccount("Admin"));
            int newUserID = um.getListAdminUser().get(-1).getId();
            am.addActionToAllActionsList(userId, "adminUser", "4.1", newUserID, "");
        }

    }

}
