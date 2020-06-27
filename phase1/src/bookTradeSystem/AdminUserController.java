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
        /*1.Freeze a user
          2. Unfreeze users
          3. Confirm and add item to user’s inventory
//        FIXME: 3.Remove item from user’s wishlist --> don't need this --> should happen for trade
         */

        switch (subMenuOption) {
            case 1:
//              TODO: asks the admin for the username of the user TO FREEZE
//              um.freezeUser('*user's username here*')
//              TODO: let presenter print the msg of successful or not
                break;
            case 2:
//              TODO: asks the admin for the username of the user to UNFREEZE
//              um.unfreezeUser('*user's username here*')
//              TODO: let presenter print the msg of successful or not
            case 3:
//              TODO: NEED A LIST OF the item-to-be-added request so presenter can print
//              TODO: adminUser 1) input id of item to confirm(initial status of item = not confirmed)
//              TODO: Add item to user's inventory
//              TODO: let presenter print the msg of successful or not
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
//              TODO: where is it stored?
//              TODO: let presenter print the msg of successful or not
                break;
            case 2:
//              TODO: where is it stored?
//              TODO: let presenter print the msg of successful or not
                break;
            case 3:
//              TODO: where is it stored?
//              TODO: let presenter print the msg of successful or not
                break;
            case 4:
//              TODO: where is it stored?
//              TODO: let presenter print the msg of successful or not
                break;
        }
    }

    private void adminOthersMenuResponse(int subMenuOption) {
        /*
        Add subsequent admin users
         */
        if (subMenuOption == 1){
//            TODO: ask for newAdmin username, password, and email
//            um.addAdmin();
        }

    }

}