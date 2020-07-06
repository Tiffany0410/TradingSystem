package bookTradeSystem;

import java.util.Scanner;

/**
 * An instance of this class represents the instance
 * getter for the AdminUserController class.
 */
public class AdminUserInstanceGetter {

    private DisplaySystem ds;

    /**
     * Constructs the AdminUserInstanceGetter with a DisplaySystem
     * @param ds The presenter class used to print to screen.
     */
    public AdminUserInstanceGetter(DisplaySystem ds){
        this.ds = ds;
    }

    protected int getItem(int numItemsToAdd){
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
            ds.printOut("Enter the number of the item in the above list ");
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

}
