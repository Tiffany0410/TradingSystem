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
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param username The username of the Admin user.
     */
    public AdminUserOtherActionsController (UserManager um,
                                            ActionManager am, String username){
        //this.ac = ac;          不需要这三个了，pass in那里这三个对应的已经删除掉了
        //this.ds = ds;
        this.um = um;
        this.am = am;
        this.userId = um.usernameToID(username);
        //this.otherInfoGetter = new AdminUserOtherInfoChecker(ds, am, um);

    }


    /**
     * Add new subsequent admin users
     *
     */
    public void addNewAdmin(String username) {
            //String username = otherInfoGetter.getNewAdminUserName();            GUI已经执行了新建用户的操作，这里不需要执行了
            //String pw = otherInfoGetter.getNewAdminUserPassword();
            //ds.printResult(this.ac.createAccount("Admin", username, pw, "None", "None"));
            am.addActionToAllActionsList(userId, "adminUser", "4.1", 0, username);
        }
}
