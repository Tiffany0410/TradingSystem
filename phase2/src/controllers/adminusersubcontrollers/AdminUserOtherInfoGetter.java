package controllers.adminusersubcontrollers;

import presenter.DisplaySystem;

import java.util.Scanner;

/**
 * An instance of this class represents the other
 * information getter for the AdminUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUserOtherInfoGetter {

    private DisplaySystem ds;

    /**
     * Constructs the AdminUserOtherInfoGetter with a DisplaySystem
     * @param ds The presenter class used to print to screen.
     */
    public AdminUserOtherInfoGetter(DisplaySystem ds){
        this.ds = ds;
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

}
