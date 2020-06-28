package bookTradeSystem;

import java.io.Serializable;

/**
 * An instance of this class represents the communication system between the admin user,
 * the use cases, and the presenter.
 */
public class AdminUserController implements Serializable, Controllable {

    private UserManager um;
    private int adminUserId;

    /**
     * Constructs the AdminUserController with a UserManager
     * and an adminUserId.
     * @param um The current state of the UserManager.
     * @param adminUserId The user ID of the admin user.
     */
    public AdminUserController(UserManager um, int adminUserId) {
        this.um = um;
        this.adminUserId = adminUserId;
    }


    /**
     * This method gathers all the necessary notifications
     * for the admin user.
     * @return Notifications as properly formatted strings.
     */
    @Override
    public String alerts() {
       // read the following in from a file
       // 1. Please respond to user's request to add item to their inventory
       // 2. Please respond to user's request to unfreeze.
    }

    /**
     * This method calls appropriate methods based on user input of
     * the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the admin user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the admin user.
     */
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
         */

        switch (subMenuOption) {
            case 1:
//              TODO: asks the admin for the username of the user TO FREEZE
//              um.freezeUser('*user's username here*')
//              TODO: let presenter print the msg of successful or not [Jiaqi]
                break;
            case 2:
//              TODO: asks the admin for the username of the user to UNFREEZE
//              um.unfreezeUser('*user's username here*')
//              TODO: let presenter print the msg of successful or not [Jiaqi]
            case 3:
//              TODO: NEED A LIST OF the item-to-be-added request so presenter can print - getter
//              TODO: adminUser chooses the number and then we do the item adding
//              TODO: Add item to user's inventory
//              TODO: AND I GUESS REMOVE THE ITEM FROM LIST IF IT'S ADDED
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
//              TODO: where is it stored? [User]
//              TODO: maxNumTransactionsAllowed (need getter + setter)
//              TODO: let presenter print the msg of successful or not
                break;
            case 2:
//              TODO: where is it stored? [User]
//              TODO: maxNumTransactionIncomplete (need getter + setter)
//              TODO: let presenter print the msg of successful or not
                break;
            case 3:
//              TODO: where is it stored? [User]
//              TODO: numLendBeforeBorrow (need getter + setter)
//              TODO: let presenter print the msg of successful or not
                break;
            case 4:
//              TODO: where is it stored? [User]
//              TODO: maxMeetingDateTimeEdits (need getter + setter)
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
//            TODO: let presenter print the msg of successful or not
        }

    }

}