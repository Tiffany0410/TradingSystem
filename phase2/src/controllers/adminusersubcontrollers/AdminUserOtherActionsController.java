package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import controllers.regularusersubcontrollers.RegularUserDateTimeChecker;
import managers.actionmanager.ActionManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.usermanager.UserManager;

import java.util.Calendar;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the adding new admin user actions part.
 *
 * @author Chengle Yang, Jiaqi Gong
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


    /**
     * Check if the year, month and day user select are valid
     * @param year Year
     * @param month Month
     * @param day Day
     *
     * @return true if the date are valid
     */
    public boolean checkSystemTime(int year, int month, int day) {
        return this.regularUserDateTimeChecker.isValidDay(year,month, day);
    }


    /**
     * Set the System time
     * @param year Year
     * @param month Month
     * @param day Day
     *
     */
    public void setSystemTime(int year, int month, int day){
        Calendar current = this.mm.getSystemDate();
        String time = current.get(Calendar.YEAR) + "." + current.get(Calendar.MONTH) + "." +
                current.get(Calendar.DAY_OF_MONTH) + ";" + year + "." + month + "." + day;
        this.am.addActionToAllActionsList(userId, "adminUser", "4.2", 0, time);
        this.mm.setSystemTime(year, month, day);
    }
}
