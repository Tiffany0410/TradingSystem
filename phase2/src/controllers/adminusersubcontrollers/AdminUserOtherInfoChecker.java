package controllers.adminusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import java.util.Scanner;

/**
 * An instance of this class represents the other
 * information getter for the AdminUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherInfoChecker {

    private ActionManager am;
    private UserManager um;

    /**
     * Constructs the AdminUserOtherInfoGetter with a DisplaySystem
     * @param am The current ActionManager which used to record all action
     * @param um The current UserManager which used to record all AdminUser and RegularUser
     */
    public AdminUserOtherInfoChecker(ActionManager am, UserManager um){

        this.am = am;
        this.um = um;

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

    /**
     * Checks if the id of the regular user is valid.
     * @param regularUserId The id of a regularUser to be checked.
     * @return result of the id is valid or not.
     */
    public boolean checkRegularUserID(int regularUserId){
        return um.getListTradableUser().contains(regularUserId);
    }


    /**
     * Checks if the username for the new admin is valid.
     * @param newAdminUserName The username of the new admin to be checked.
     * @return If the username is valid.
     */
    public boolean checkNewAdminUserName(int newAdminUserName){
        return !um.getListAdminUserName().contains(newAdminUserName);
    }

    /**
     * Checks if the password for the new admin is valid.
     * @param newAdminPassword The password for the new admin to be checked.
     * @return If the password is valid.
     */
    public boolean checkNewAdminPassword(String newAdminPassword){
        //Possible regex thing here (ex. A-Za-z0-9...etc)
        return newAdminPassword != null;
    }

}
