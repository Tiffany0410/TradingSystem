package controllers.adminusersubcontrollers;

import controllers.AccountCreator;
import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter, for the adding new admin user actions part.
 *
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherActionsController {

    private AdminUserOtherInfoChecker otherInfoGetter;
    private AccountCreator ac;
    private DisplaySystem ds;
    private UserManager um;
    private ActionManager am;
    private int userId;

    /**
     * Constructs the AdminUserHistoricalActionController with DisplaySystem, AccountCreator,
     * an UserManager, an ItemManager and an adminUserId.
     * @param ac The account creator
     * @param ds The presenter class used to print to screen.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserOtherActionsController (AccountCreator ac, DisplaySystem ds, UserManager um,
                                            ActionManager am, String username){
        this.ac = ac;
        this.ds = ds;
        this.um = um;
        this.am = am;
        this.userId = um.usernameToID(username);
        this.otherInfoGetter = new AdminUserOtherInfoChecker(ds, am, um);

    }


    /**
     * Add new subsequent admin users
     *
     */
    public void addNewAdmin() {
            String username = otherInfoGetter.getNewAdminUserName();
            String pw = otherInfoGetter.getNewAdminUserPassword();
            ds.printResult(this.ac.createAccount("Admin", username, pw, "None", "None"));
            int newUserID = um.getListAdminUser().get(-1).getId();
            am.addActionToAllActionsList(userId, "adminUser", "4.1", newUserID, "");
        }
}
