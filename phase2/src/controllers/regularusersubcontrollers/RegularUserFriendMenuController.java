package controllers.regularusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.TradableUser;
import managers.usermanager.User;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;

public class RegularUserFriendMenuController {

    private SystemMessage sm;
    private DisplaySystem ds;
    private UserManager um;
    private ActionManager am;
    private int userId;

    /**
     * Constructs a RegularUserFriendMenuController
     * @param ds The presenter class used to print to screen.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param userId The user id of the regular user.
     */
    public RegularUserFriendMenuController(DisplaySystem ds, UserManager um, ActionManager am, int userId){
        this.ds = ds;
        this.um = um;
        this.am = am;
        this.userId = userId;
        this.sm = new SystemMessage();
    }

    /**
     * Let the presenter print user's list of friends
     * @param asGuest The determiner of access to this menu option.
     */
    public void viewListFriends(boolean asGuest){
        if (!asGuest){
            TradableUser user = um.findUser(userId);
            ArrayList<TradableUser> friends = um.getFriends(userId);
            if (friends.isEmpty()){
                ds.printOut("Your list of friends is empty.");
            }
            else{
            ds.printOut("Your list of friends: ");
            ds.printResult(new ArrayList<>(friends));
            am.addActionToAllActionsList(userId, "regularUser", "1.18", 0, "");
            }
        }
        else{
            sm.msgForGuest(ds);
        }
    }

    // TODO: Send friend request
    // TODO: Respond to friend requests
    // TODO: Send a message to a selected friend
    // TODO: View messages sent by a friend

}
