package controllers.regularusersubcontrollers;

import managers.itemmanager.Category;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * An instance of this class represents the other information
 * getter for the RegularUserController class.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

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
     * Gets the name of the item from the user.
     * @return The name of the item.
     */
    public String getItemName() {
        return getName("item name");
    }

    /**
     * Gets user's input of the place.
     * @return User's input of the place.
     */
    public String getPlace(){
       return getName("place");
    }

    /**
     * Gets user's input of the home city.
     * @return User's input of the home city.
     */
    public String getHomeCity(){
        return getName("home city");
    }

    private String getName(String type){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the + " + type + ": ");
        return sc.nextLine();
    }

    /**
     * Gets user's input of the meeting number.
     * Based on code by Yassine.b from:
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
     * @return User's valid input of the meeting number.
     */
    public int getNumMeeting(){
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
     * Gets and returns user's input of
     * the option number. Based on code by Yassine.b from:
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
     * @return User's input of the option number.
     */
    protected int getNumKindOfResponse(String option1, String option2){
        Scanner sc = new Scanner(System.in);
        int num = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter an integer (1 - " + option1 + ", 2 - " + option2 + " + : ");
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

    /**
     * Gets and returns user's input of
     * the type of the item
     * @return User's input of the type of the item.
     */
    protected Category getItemType(){
        ArrayList<String> categories = new ArrayList<>();
        String cateG;
        for (Category category : Category.values()){
            categories.add(category.name());
        }
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the type of the item: ");
        ds.printOut("It must be in one of the categories below (all UPPERCASE)!");
        ds.printResult(new ArrayList<>(categories));
        do {
           cateG = sc.nextLine();
        } while(!categories.contains(cateG));
        return Category.valueOf(cateG);
    }

    /**
     * Gets and returns user's input of
     * the rating. Based on code by Yassine.b from:
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
     * @return User's input of the rating.
     */
    protected int getNumRating(){
        Scanner sc = new Scanner(System.in);
        int num = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the rating (1-10).");
            // if the input is int
            if (sc.hasNextInt()) {
                num = sc.nextInt();
                // if the input is valid
                if (1 <= num && num <= 10) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid integer!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid rating please");
            }
        } while (!okInput);
        return num;

    }
}
