package controllers.adminusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;
import controllers.maincontrollers.DisplaySystem;

import java.util.Scanner;

/**
 * An instance of this class represents the other
 * information getter for the AdminUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherInfoChecker {

    private DisplaySystem ds;
    private ActionManager am;
    private UserManager um;

    /**
     * Constructs the AdminUserOtherInfoGetter with a DisplaySystem
     * @param ds The presenter class used to print to screen.
     * @param am The current ActionManager which used to record all action
     * @param um The current UserManager which used to record all AdminUser and RegularUser
     */
    public AdminUserOtherInfoChecker(DisplaySystem ds, ActionManager am, UserManager um){
        this.ds = ds;
        this.am = am;
        this.um = um;
    }

    public boolean checkItemToAddNum(int lenListItemToAdd, int userInput){
        return 0 <= userInput && userInput <= (lenListItemToAdd-1);
    }

    /**
     * Gets the ActionID from the admin user.
     * @return The ActionID entered by admin user.
     */
    public int getActionID() {
        //TODO: delete later
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int actionID = 0;
        do{
            ds.printOut("Enter the number of action that you want to cancel");
            if(sc.hasNextInt()){
                actionID = sc.nextInt();
                if (am.getAllActionID(am.getListOfCurrentRevocableActions()).contains(actionID)) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper action number please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return actionID;
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
     * Gets the RegularUserID from the admin user.
     * @return The RegularUserID entered by admin user.
     */
    public int getRegularUserID() {
        //TODO: delete later
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int userID = 0;
        do{
            ds.printOut("Enter the Id of tradableUser that you want to search");
            if(sc.hasNextInt()){
                userID = sc.nextInt();
                if (um.getListTradableUser().contains(userID)) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper tradableUser Id please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return userID;
    }

    /**
     * Checks if the id of the regular user is valid.
     * @param regularUserId The id of a regularUser to be checked.
     * @return If the id is valid.
     */
    public boolean checkRegularUserID(int regularUserId){
        return um.getListTradableUser().contains(regularUserId);
    }

    /**
     * Gets the new AdminUser username from the admin user.
     * @return The new AdminUser username entered by admin user.
     */
    public String getNewAdminUserName() {
        //TODO: delete later
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        String userName = null;
        do{
            ds.printOut("Enter the name of new AdminUser that you want to add");
            if(sc.hasNextLine()){
                userName = sc.nextLine();
                if (!um.getListAdminUserName().contains(userName)) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper AdminUser username please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid username please");
            }
        }while(!okInput);
        return userName;
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
     * Gets the new AdminUser password from the admin user.
     * @return The new AdminUser password entered by admin user.
     */
    public String getNewAdminUserPassword() {
        //TODO: delete later
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        String pw = null;
        do{
            ds.printOut("Enter the password of new AdminUser that you want to add");
            if(sc.hasNextLine()){
                pw = sc.nextLine();
                if (pw != null) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper AdminUser password please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid password please");
            }
        }while(!okInput);
        return pw;
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
