package controllers.adminusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;

/**
 * An instance of this class represents the other
 * information getter for the AdminUserController class.
 *
 * @author Yu Xin Yan, Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherInfoChecker {

    private ActionManager am;

    /**
     * Constructs the AdminUserOtherInfoGetter with a DisplaySystem
     * @param am The current ActionManager which used to record all action
     */
    public AdminUserOtherInfoChecker(ActionManager am){
        this.am = am;

    }

    /**
     * Check the number user put in
     * @param lenListItemToAdd length of the list of the item to add
     * @param userInput number of user input
     * @return result of user input is valid or not
     */
    public boolean checkItemToAddNum(int lenListItemToAdd, int userInput){
        return 0 <= userInput && userInput <= (lenListItemToAdd-1);
    }

    /**
     * Checks if the action id is valid.
     * @param actionID The id of the action to be checked.
     * @return If this action id is valid.
     */
    public boolean checkActionId(int actionID){
        return am.getAllActionID(am.getListOfCurrentRevocableActions()).contains(actionID);
    }

}
