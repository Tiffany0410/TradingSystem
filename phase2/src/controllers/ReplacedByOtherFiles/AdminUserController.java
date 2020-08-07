package controllers.maincontrollers;

import controllers.AccountCreator;
import controllers.adminusersubcontrollers.*;
import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

//TODO: can delete this class now
public class AdminUserController {

    private AdminUserOtherInfoChecker otherInfoGetter;
    private SystemMessage sm;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ItemManager im;
    private TradeManager tm;
    private ActionManager am;
    private FeedbackManager fm;
    private AdminUserManagerUsersController muc;
    private AdminUserHistoricalActionController hac;
    private AdminUserOtherActionsController oac;
    private AdminUserEditThresholdsController etc;
    private FilesReaderWriter frw;
    private int userId;

    /**
     * Constructs the AdminUserController with a AccountCreator, DisplaySystem,
     * FilesReadWriter, an UserManager, an ItemManager and an adminUserId.
     * @param ac The controller class used to create an account.
     * @param ds The presenter class used to print to screen.
     * @param im The current state of the ItemManager.
     * @param um The current state of the UserManager.
     * @param tm The current state of the TradeManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserController(AccountCreator ac, DisplaySystem ds, UserManager um, ItemManager im,
                               FeedbackManager fm, TradeManager tm,
                               ActionManager am, String username) {
        this.ac = ac;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
        this.um = um;
        this.im = im;
        this.tm = tm;
        this.am = am;
        this.fm = fm;
        this.userId = um.usernameToID(username);
        this.sm = new SystemMessage();
        this.muc = new AdminUserManagerUsersController(ds, ac, um, im, am, username);
        this.hac = new AdminUserHistoricalActionController(ds,um, im, tm, am, fm, username);
        this.oac = new AdminUserOtherActionsController(ac, ds, um, am, username);
        this.etc = new AdminUserEditThresholdsController(ds, um, am, username);
        this.otherInfoGetter = new AdminUserOtherInfoChecker(ds, am, um);
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
    public void actionResponse(int mainMenuOption, int subMenuOption, String thresholdValuesFilePath) throws IOException, InvalidIdException, ParseException {
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
                etc.editMaxTransactions1(thresholdValuesFilePath);
                break;
            case 2:
                etc.editMaxTransactions2(thresholdValuesFilePath);
                break;
            case 3:
               etc.editBookNumber(thresholdValuesFilePath);
               break;
            case 4:
                etc.editMaxEdits(thresholdValuesFilePath);
                break;
        }
    }

    private void adminUserActionResponse(int subMenuOption) throws InvalidIdException, FileNotFoundException, ParseException {
        /*
        1.List all the historical actions in the system
        2.Cancel the revocable historical actions of tradableUser
        3.Find all the revocable historical actions of specific tradableUser
         */
        switch (subMenuOption) {
            case 1:
                hac.printOutAllHistoricalAction();
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
        if (subMenuOption == 1) {
            oac.addNewAdmin();
        }

    }

}
