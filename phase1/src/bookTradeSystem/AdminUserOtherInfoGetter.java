package bookTradeSystem;

import java.util.Scanner;

/**
 * An instance of this class represents the other
 * information getter for the AdminUserController class.
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
    protected int getThresholdAns(){
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


    protected boolean getAddOrNot(){
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
