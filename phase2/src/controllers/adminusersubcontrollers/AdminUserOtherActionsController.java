package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import managers.actionmanager.ActionManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.usermanager.UserManager;


/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the adding new admin user actions part.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherActionsController {

    private UserManager um;
    private ActionManager am;
    private MeetingManager mm;
    private RegularUserDateTimeChecker regularUserDateTimeChecker;
    private int userId;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserOtherActionsController (UserManager um, ActionManager am, String username, MeetingManager mm){

        this.um = um;
        this.am = am;
        this.mm = mm;
        this.regularUserDateTimeChecker = new RegularUserDateTimeChecker();
        this.userId = this.um.usernameToID(username);

    }


    /**
     * Add new subsequent admin users
     *
     */
    public void addNewAdmin(String username) {
            am.addActionToAllActionsList(userId, "adminUser", "4.1", 0, username);
        }

    public boolean checkSystemTime(int year, int month, int day) {
        return this.regularUserDateTimeChecker.isValidDay(year,month, day);
    }

    public void setSystemTime(int year, int month, int day){
        this.mm.setSystemTime(year, month, day);
    }
}
