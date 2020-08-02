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

public class RegularUserOtherInfoChecker {

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
    public RegularUserOtherInfoChecker(DisplaySystem ds, TradeManager tm, MeetingManager mm,
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
     *
     * @param TypeOfMessage The part of the message that relates to the context.
     * @return User's message in string format.
     */
    public String getMessage(String TypeOfMessage) {
        //TODO: delete later
        Scanner sc = new Scanner(System.in);
        ds.printOut(TypeOfMessage + "" + "[enter OK to stop]: ");
        StringBuilder fullMsg = new StringBuilder();
        //read the first line
        String msg = sc.nextLine();
        //read in + append until user enters "OK"
        while (!msg.equals("OK")) {
            fullMsg.append(msg);
            msg = sc.nextLine();
        }
        return fullMsg.toString();
    }

    /**
     * Checks the type of the trade input by the user.
     * For now, there're only permanent and temporary.
     *
     * @return If the trade type is valid.
     */
    protected boolean checkTradeType(String tradeType) {
        return tradeType.equals("Permanent") || tradeType.equals("Temporary");

    }


    /**
     * Checks the response from the user to
     * the agree or not question.
     *
     * @return If user's response is valid.
     */
    protected boolean checkAgreeOrNot(String response) {
        return response.equals("Agree") || response.equals("Disagree");
    }

    /**
     * Checks user's input of the meeting number.
     *
     * @return If the meeting number is valid.
     */
    public boolean checkNumMeeting(int meetingNum) {
        return meetingNum == 1 || meetingNum == 2;
    }

    /**
     * Gets and returns user's input of
     * the option number. Based on code by Yassine.b from:
     *
     * @return User's input of the option number.
     * @link https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
     */
    protected int getNumKindOfResponse(int userInput, String option1, String option2) {
        //TODO: delete later
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
     *
     * @return User's input of the type of the item.
     */
    protected Category getItemType() {
        //TODO: delete later
        ArrayList<String> categories = new ArrayList<>();
        String cateG;
        for (Category category : Category.values()) {
            categories.add(category.name());
        }
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the type of the item: ");
        ds.printOut("It must be in one of the categories below (all UPPERCASE)!");
        ds.printResult(new ArrayList<>(categories));
        do {
            cateG = sc.nextLine();
        } while (!categories.contains(cateG));
        return Category.valueOf(cateG);
    }


    /**
     * Checks user's input of the item category
     *
     * @return If it's a valid category or not.
     */
    protected boolean checkItemType(String userInputCategory) {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.name());
        }
        return categories.contains(userInputCategory);
        // use Category.valueOf(cateG) in the gui class and then plug it in to the method
        // for the param with enum Category
    }

    /**
     * Checks user's input of
     * the rating.
     *
     * @return If the rating is valid.
     */
    protected boolean getNumRating(int rating) {
        return 1 <= rating && rating <= 10;
    }


    /**
     * Checks the tradable user id input by the user.
     *
     * @return If the id is valid or not.
     */
    protected boolean checkTradableUserId(int tradableUserId) {
        return tradableUserId == 0 || um.getListTradableUser().contains(tradableUserId);

    }
}
