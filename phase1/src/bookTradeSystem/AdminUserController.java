package bookTradeSystem;

import java.io.Serializable;

public class AdminUserController implements Serializable, Controllable {

    UserManager um;
    int adminUserId;

//    TODO: consider refactoring - maybe can pass in as param instead of in the constructor?
    public AdminUserController(UserManager um, int adminUserId) {
        this.um = um;
        this.adminUserId = adminUserId;
    }

    @Override
    public void alerts() {
//      TODO: TO BE DECIDED
    }

    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) {
         /*
        1. decide the menu options
        2. decide what use case method to call for each menu option (discuss with Gabriel)
        3. decide what presenter method to call to print the results for each menu option (discuss with Jiaqi)

        */
        switch(mainMenuOption){
            case 1:
                adminManageUsersMenuResponse(subMenuOption);
                break;
            case 2:
                adminEditThresholdMenuResponse(subMenuOption);
                break;
            case 3:
                adminOthersMenuResponse(subMenuOption);
                break;

        }

    }

    private void adminManageUsersMenuResponse(int subMenuOption) {
        /*1.Freeze/ unfreeze users (system auto-freeze and admin can freeze and unfreeze)
        2.Confirm and add item to user’s inventory
        3.Remove item from user’s wishlist
         */

        switch (subMenuOption) {
            case 1:
//                TODO
                break;
            case 2:
//                TODO
                break;
            case 3:
//                TODO
                break;

        }
    }

    private void adminEditThresholdMenuResponse(int subMenuOption) {
        /*
        1.Edit the max number of transactions allowed a week
        2.Edit the max number of transactions that can be incomplete before the account is frozen
        3.Edit the number of books users must lend before users can borrow
        4.Edit the max Edits per user for meeting’s date + time
         */
        switch (subMenuOption) {
            case 1:
//                TODO
                break;
            case 2:
//                TODO
                break;
            case 3:
//                TODO
                break;
            case 4:
//                TODO
                break;
        }
    }

    private void adminOthersMenuResponse(int subMenuOption) {
        /*
        Add subsequent admin users
         */
        if (subMenuOption == 1){
//            TODO: add other adminUser
        }

    }

}