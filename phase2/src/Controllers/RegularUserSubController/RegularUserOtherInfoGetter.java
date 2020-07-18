package Controllers.RegularUserSubController;

import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;

import java.util.Scanner;

/**
 * An instance of this class represents the other information
 * getter for the RegularUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserOtherInfoGetter {

    private Presenter.DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private Managers.TradeManager.TradeManager tm;
    private Managers.MeetingManager.MeetingManager mm;
    private Managers.UserManager.UserManager um;
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
     * @param userId   The userid of the regular user.
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

    /**
     * Gets the name of the item from the user.
     * @return The name of the item.
     */
    public String getItemName() {
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the item: ");
        return sc.nextLine();
    }


    /**
     * Gets user's message, which can be in any length.
     * @param TypeOfMessage The part of the message that relates to the context.
     * @return User's message in string format.
     */
    public String getMessage(String TypeOfMessage){
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

    /**
     * Gets the type of the trade from the user.
     * For now, there're only permanent and temporary.
     * @return The type of the trade.
     */
    protected String getTradeType(){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the type of this trade (Permanent or Temporary) : ");
        //read the first line
        String tradeType = sc.nextLine();
        //read in + append until user enters "OK"
        while(!tradeType.equals("Permanent") && !tradeType.equals("Temporary")){
            ds.printOut("Please enter a proper type (the system is case sensitive)");
            tradeType = sc.nextLine();
        }
        return tradeType;
    }


    /**
     * Gets the response from the user to
     * the agree or not question.
     * @return User's response.
     */
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


    /**
     * Gets user's input of the place.
     * @return User's input of the place.
     */
    public String getPlace(){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the place: ");
        String place;
        //read the first line
        place = sc.nextLine();
        return place;
    }

    /**
     * Gets user's input of the meeting number.
     * @return User's valid input of the meeting number.
     */
    public int getNumMeeting(){
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

    /**
     * Gets user's input of the kind of trade.
     * For now, there are one-way-trade and
     * two-way-trade.
     * @return User's input of the kind of trade.
     */
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
