package bookTradeSystem;

import java.util.Scanner;

public class RegularUserOtherInfoGetter {

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserOtherInfoGetter with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserOtherInfoGetter(DisplaySystem ds, TradeManager tm, MeetingManager mm,
                                            UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
    }

    // TODO: move to InfoGetter class
    protected String getItemName() {
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the item: ");
        return sc.nextLine();
    }



    // TODO: move to InfoGetter class
    protected String getMessage(String TypeOfMessage){
        Scanner sc = new Scanner(System.in);
        ds.printOut(TypeOfMessage + "" + "[enter OK to stop]: ");
        StringBuilder fullMsg = new StringBuilder();
        //read the first line
        String msg = sc.nextLine();
        //read in + append until user enters "OK"
        while(!msg.equals("OK")){
            fullMsg.append(msg);
            msg = sc.nextLine();
        }
        return fullMsg.toString();
    }


    /* useless for now...
    private enum TradeType{
        Permanent, Temporary
    }*/

    // move to InfoGetter class
    protected String getTradeType(){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the type of this trade (Permanent or Temporary) : ");
        //read the first line
        String tradeType = sc.nextLine();
        //read in + append until user enters "OK"
        while(!tradeType.equals("Permanent") && !tradeType.equals("Temporary")){
            ds.printOut("Please enter a proper type!!!");
            tradeType = sc.nextLine();
        }
        return tradeType;
    }


    // move to InfoGetter class
    protected String getAgreeOrNot(){
        Scanner sc = new Scanner(System.in);
        boolean ok = false;
        String response;
        do {
            ds.printOut("Agree / Disagree?");
            response = sc.nextLine();
            if (!response.equals("Agree") && !response.equals("Disagree")) {
                ds.printOut("Invalid string (the system is case sensitive)! Please enter again");
            } else {
                ok = true;
            }
        }
        while(!ok);
        return response;
    }


    // move to InfoGetter class
    protected String getPlace(){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the place: ");
        String place;
        //read the first line
        place = sc.nextLine();
        return place;
    }

    // move to InfoGetter class
    protected int getNumMeeting(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int num = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the meeting number (1 - first, 2 - second)"  + " : ");
            // if the input is int
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                // if the input is valid
                if (num == 1 || num == 2) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid meeting number!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return num;

    }

    // move to infoGetter class
    protected int getNumKindOfTrade(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int num = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter an integer (1 - one-way-trade, 2 - two-way-trade)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                // if the input is valid
                if (num == 1 || num == 2) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid integer!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return num;
    }

}
