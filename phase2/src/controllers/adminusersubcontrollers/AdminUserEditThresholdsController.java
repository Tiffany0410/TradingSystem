package controllers.adminusersubcontrollers;

import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the adding new admin user actions part.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserEditThresholdsController {

    private AdminUserOtherInfoChecker otherInfoGetter;
    private SystemMessage sm;
    private DisplaySystem ds;
    private ActionManager am;
    private Integer userId;
    private FilesReaderWriter frw;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param ds The presenter class used to print to screen.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserEditThresholdsController(DisplaySystem ds, UserManager um,
                                             ActionManager am, String username){
        this.ds = ds;
        this.am = am;
        this.userId = um.usernameToID(username);
        this.sm = new SystemMessage();
        this.otherInfoGetter = new AdminUserOtherInfoChecker(ds, am, um);
        this.frw = new FilesReaderWriter();
    }


    /**
     * Edit the max number of transactions allowed a week
     *
     * @throws FileNotFoundException Cannot don't find the related thresholdValuesFile in the provided filePath
     */
    public void editMaxTransactions1(String thresholdValuesFilePath) throws FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        int currentValue1 = thresholdValues.get(0);
        sm.msgForThresholdValue(currentValue1);
        int futureValue1 = otherInfoGetter.getThresholdAns();
        // editMaxNumTransactionsAllowedAWeek
        thresholdValues.set(0, futureValue1);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.1", currentValue1, String.valueOf(futureValue1));
    }


    /**
     * Edit the max number of transactions that can be incomplete before the account is frozen
     *
     * @throws FileNotFoundException Cannot don't find the related thresholdValuesFile in the provided filePath
     */
    public void editMaxTransactions2(String thresholdValuesFilePath) throws FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        int currentValue = thresholdValues.get(1);
        sm.msgForThresholdValue(currentValue);
        int futureValue = otherInfoGetter.getThresholdAns();
        // editMaxNumTransactionIncomplete
        thresholdValues.set(1, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.2", currentValue, String.valueOf(futureValue));
    }


    /**
     * Edit the number of books users must lend before users can borrow
     *
     * @throws FileNotFoundException Cannot don't find the related thresholdValuesFile in the provided filePath
     */
    public void editBookNumber(String thresholdValuesFilePath) throws FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        int currentValue = thresholdValues.get(2);
        sm.msgForThresholdValue(currentValue);
        int futureValue = otherInfoGetter.getThresholdAns();
        // editNumLendBeforeBorrow
        thresholdValues.set(2, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.3", currentValue, String.valueOf(futureValue));
    }


    /**
     * Edit the max Edits per user for meeting’s date + time
     *
     * @throws FileNotFoundException Cannot don't find the related thresholdValuesFile in the provided filePath
     */
    public void editMaxEdits(String thresholdValuesFilePath) throws FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        int currentValue = thresholdValues.get(3);
        sm.msgForThresholdValue(currentValue,ds);
        int futureValue = otherInfoGetter.getThresholdAns();
        //editMaxMeetingDateTimeEdits
        thresholdValues.set(3, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.4", currentValue, String.valueOf(futureValue));
    }
}
