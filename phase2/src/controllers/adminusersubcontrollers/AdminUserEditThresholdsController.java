package controllers.adminusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import java.util.List;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the adding new admin user actions part.
 *
 * @author Chengle Yang, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserEditThresholdsController {

    private SystemMessage sm;
    private ActionManager am;
    private Integer userId;
    private UserManager um;
    private List<Integer> thresholdValues;
    private int currentValue;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     * @param sm The system message
     */
    public AdminUserEditThresholdsController(ActionManager am, UserManager um, SystemMessage sm, String username,
                                             List<Integer> thresholdValues){
        this.am = am;
        this.um = um;
        this.sm = sm;
        this.userId = this.um.usernameToID(username);
        this.thresholdValues = thresholdValues;
    }


    /**
     * Get the value of max number of transactions
     * @return string of value of max number of transactions
     */
    public String getMaxNumberTransactions(){
        this.currentValue = this.thresholdValues.get(0);
        return sm.msgForThresholdValue(this.currentValue);
    }

    /**
     * This method edit the max value of max number of transactions
     * @param futureValue the new value of max number of transactions
     * @return result message of this operation
     */
    public String editMaxNumberTransactions(int futureValue){
        this.thresholdValues.set(0, futureValue);

        am.addActionToAllActionsList(this.userId, "adminUser", "2.1", this.currentValue, String.valueOf(futureValue));
        return sm.msgForResult(true);
    }

    /**
     * Get the value of max number of incomplete transactions
     * @return string of max number of incomplete transactions
     */
    public String getMaxNumberIncompleteTransactions(){
        this.currentValue = this.thresholdValues.get(1);
        return sm.msgForThresholdValue(currentValue);
    }

    /**
     * Edit the value of max number of incomplete transactions
     * @param futureValue the new value of max number of incomplete transactions
     * @return string of the result of this operation
     */
    public String editMaxNumberIncompleteTransactions(int futureValue){
        this.thresholdValues.set(1, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.2", this.currentValue, String.valueOf(futureValue));
        return sm.msgForResult(true);
    }

    /**
     * Get the number of books users must lend before users can borrow
     * @return string of the value of must lend number
     */
    public String getMustLendNumber(){
        this.currentValue = this.thresholdValues.get(2);
        return sm.msgForThresholdValue(currentValue);
    }

    /**
     * Edit the number of books users must lend before users can borrow
     * @param futureValue the new value of number of books user must lend
     * @return string of the result of this operation
     */
    public String editMustLendNumber(int futureValue){
        this.thresholdValues.set(2, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.3", this.currentValue, String.valueOf(futureValue));
        return sm.msgForResult(true);
    }

    /**
     * Get the value of max number of edits
     * @return string of the max number of edits
     */
    public String getEditMaxEdits(){
        this.currentValue = thresholdValues.get(3);
        return sm.msgForThresholdValue(this.currentValue);
    }

    /**
     * Edit the value of max edits number
     * @param futureValue the new value of max number of edits
     * @return string of the result of this operation
     */
    public String editMaxEdits(int futureValue){
        this.thresholdValues.set(3, futureValue);
        am.addActionToAllActionsList(this.userId, "adminUser", "2.4", currentValue, String.valueOf(futureValue));
        return sm.msgForResult(true);
    }
}
