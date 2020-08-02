package controllers.adminusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

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

    /**
     * Gets the item number from the admin user.
     * @param numItemsToAdd The list of items to be added to regular users inventories.
     * @return The number of the item in the list.
     */
    public int getItem(int numItemsToAdd){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        // asks for the item name, description, owner id of the user to be added
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        // does not store the number of items but the number of the item the admin chooses
        int numItem = 0;
        do{
            ds.printOut("Enter the number of the item in the above list (the number beside the #");
            if(sc.hasNextInt()){
                numItem = sc.nextInt();
                if (1<= numItem && numItem <= numItemsToAdd) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper option please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return numItem;
    }

    /**
     * Gets the new threshold value from the admin user.
     * @return The new threshold value.
     */
    public int getThresholdAns(){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int thresholdAns = 0;
        do{
            ds.printOut("Enter the new threshold value: ");
            if(sc.hasNextInt()){
                thresholdAns = sc.nextInt();
                okInput = true;
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return thresholdAns;
    }

    /**
     * Gets admin user's response of whether to add or not.
     * @return Admin user's response of whether to add or not.
     */
    public boolean getAddOrNot(){
        /*
         * Referenced the code in the first answer in
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         * by answerer Yassine.b
         */
        boolean okInput = false;
        Scanner sc = new Scanner(System.in);
        int num = 0;
        do{
            ds.printOut("Please enter a number (1 - add, 2 - not add): ");
            if(sc.hasNextInt()){
                num = sc.nextInt();
                if (num == 1 || num == 2) {
                    okInput = true;
                }
                else{
                    ds.printOut("Enter a proper option please");
                }
            }else{
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        }while(!okInput);
        return num == 1;

    }

    /**
     * Gets the ActionID from the admin user.
     * @return The ActionID entered by admin user.
     */
    public int getActionID() {
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
     * Gets the RegularUserID from the admin user.
     * @return The RegularUserID entered by admin user.
     */
    public int getRegularUserID() {
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
     * Gets the new AdminUser username from the admin user.
     * @return The new AdminUser username entered by admin user.
     */
    public String getNewAdminUserName() {
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
     * Gets the new AdminUser password from the admin user.
     * @return The new AdminUser password entered by admin user.
     */
    public String getNewAdminUserPassword() {
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


}
