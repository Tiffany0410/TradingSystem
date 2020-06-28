package bookTradeSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

/**
 * An instance of this class represents the communication system between the user and
 * the use cases and entities
 */
public class RegularUserController implements Serializable, Controllable {
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserController with a TradeManager, a MeetingManager, a UserManager,
     * user's username and userId.
     * @param tm The current state of the TradeManager.
     * @param mm The current state of the MeetingManager.
     * @param um The current state of the UserManager.
     * @param username The username of the user.
     * @param userId The user ID of the user.
     */
    public RegularUserController(TradeManager tm, MeetingManager mm, UserManager um, String username, int userId) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId; // A method of username to userID for now? [Gabriel]
    }

    /**
     * This method gathers all the necessary notifications
     * for the user and then calls on the
     * relevant presenter class method.
     */
    @Override
    public void alerts() {
       /*
        1. get the list of things to be printed
        2. call the presenter class to let it format it and then print
        */
//      TODO: TO BE DECIDED
    }

    /**
     * This method calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the user.
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) {
       /*
        1. decide the menu options
        1.5 decide how to read in user's input
        2. decide what use case method to call for each menu option
        3. decide what presenter method to call to print the results for each menu option

        */
        switch(mainMenuOption){
            case 1:
                userAccountMenuResponse(subMenuOption);
                break;
            case 2:
                userTradingMenuResponse(subMenuOption);
                break;
            case 3:
                userMeetingMenuResponse(subMenuOption);
                break;
        }

    }

    private void userAccountMenuResponse(int subMenuOption) {
        /*
        1.Browse all the books in other users inventories
        2.Add to own Wish List
        3.Search item
        4.Remove from own Wish List
        5.Remove from own Inventory
        6.Request to unfreeze account
        7.Request that an item be added to your inventory
        8.See most recent three items traded
        0.Exit menu
         */
        switch(subMenuOption){
            case 1:
                List<User> allUsers = um.getListUser();
//              TODO: return a list of books of all other users [Gabriel]
                break;
            case 2:
                um.addItemWishlist(getItem("ADD"), username);
//              TODO: call presenter class to pass the boolean to and print msg [Jiaqi]
                break;
            case 3:
//              TODO: get the itemName from user  [Jiaqi / me?]
//              um.searchItem(*item name*)
//              TODO: call presenter to print the result of the method call [Jiaqi]

            case 4:
                um.removeItemWishlist(getItem("REMOVE"), username);
//              TODO: call presenter class to pass the boolean to and print msg [Jiaqi]
                break;
            case 5:
                um.removeItemInventory(getItem("REMOVE"), username);
//              TODO: call presenter class to pass the boolean to and print msg [Jiaqi]
                break;
            case 6:
//              TODO: maybe have a setter for the listUnfreezeRequest in userManager?
//              TODO: add the user's request to the list in proper format*
//               (a message with user id so admin can use it to unfreeze)
                break;
            case 7:
//              TODO: receive user input of all the details needed to create an item
//              TODO: maybe there can be a listAddItemRequest in userManager?
//               (like each element are strings with all the information needed for the admin to create
//               the item instance (when the admin wants to confirm it)
                break;
            case 8:
//              TODO: top three items traded --> id plz!!! [Daniel]
                break;
        }
    }

    private Item getItem(String action) {
        if (action.equals("ADD")){
            System.out.println("ITEM TO BE ADDED");
        }
        else{
            System.out.println("ITEM TO BE DELETED");
        }
        // Reads in from the keyboard
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//      TODO: CREATE SEVERAL ITERATOR CLASSES TO PASS PROMPTS
    }


    private void userTradingMenuResponse(int subMenuOption) {
        /*
          1.Request a trade (lend / borrow / two-way) !!!!- NEED to remove item from wishlist &/ inventory (maybe in constructor???)
          2.Respond to trade requests (agree / disagree)
          3.View open trades
          4.View closed trades
          5.Confirm that a trade has been completed
          6.See top three most frequent trading partners
          7.View transactions that have been cancelled
         */
        switch (subMenuOption) {
            case 1:
//              TODO: let user enter borrower id
//              TODO: let user enter lender id
//              TODO: let user enter item id
//              TODO: let user enter tradeType ('PERMANENT' OR 'TEMPORARY')
//              TODO: THERE SHOULD BE ... MAYBE A MAP OF USERID TO BOOLEAN
//              TODO: AND THE DEFAULT IS THE PERSON WHO REQUEST THE TRADE = AGREE
//              TODO: CALL PRESENTER TO PRINT MSG -- SUCCESSFUL OR NOT [Jiaqi]
                break;
            case 2:
 //             TODO: PRINT THE LIST OF WAIT TO BE OPENED TRADES (OR MAYBE HAVE A WAIT TO BE AGREED LIST OF TRADES?)
 //             TODO: ASKS THE USER WHETHER TO ENTER TRADE ID AND ENTER AGREE OR DISAGREE
//              TODO: WE CAN JUST SET THE MAP^ USE USERID TO SET THE BOOLEAN TO TRUE/FALSE [
//              TODO: CALL PRESENTER TO PRINT MSG -- SUCCESSFUL OR NOT [Jiaqi]
                break;
            case 3:
                tm.getOpenTrade(userId);
//              TODO: call presenter class [Jiaqi]
                break;
            case 4:
                tm.getClosedTrade(userId);
//              TODO: call presenter class [Jiaqi]
                break;
            case 5:
//              TODO: print all the open trades [Jiaqi]
                tm.getOpenTrade(userId);
//              TODO: let user enter trade id and we use it to confirm complete
//              tm.confirmComplete();
//              TODO: call presenter class [Jiaqi]
                break;
            case 6:
//              TODO: we need id for the top three partners :) - storage in User class [Daniel]
//              TODO: with that, userManager can have a searchUser function (id - user instance) and then
//              TODO: I'll pass in the ids, get the user instance and then pass it to the presenter class
                break;
            case 7:
//              TODO: get a boolean variable for whether the trade is cancelled or not
//              TODO: get list of trades with cancelled status with a given user id
                break;

        }
    }

    private void userMeetingMenuResponse(int subMenuOption) {
       /*
    1.Suggest/edit time and place for meetings
    2.Confirm time and place for meetings
    3.Confirm the meeting took place
    4.See the list of meetings need to be confirmed
    5.See the list of meetings that have been confirmed
    6.View to-be-opened trades and set up first meeting
        */

        switch(subMenuOption) {
            case 1:
                List<Meeting> unconfirmedMeetings = mm.getUnConfirmTimePlace(userId);
//              TODO: call presenter to print it [Jiaqi]
//              TODO: ask the user to enter the meeting id and the time and place
//              TODO: have a method in meetingManager -- given id - return meeting
                public Meeting getMeetingByIdNum(int tradeId, int numMeeting)
//              TODO: call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
//              TODO: call presenter to print msg of successful or not [Jiaqi]
                break;
            case 2:
                //List<Meeting> unconfirmedMeetings = mm.getUnConfirmTimePlace(userId); (dupli. code)
//              TODO: call presenter to print it [Jiaqi]
//              TODO: ask the user to enter the meeting id
//              TODO: do get meeting by trade id / should have another one for meeting id?
//              TODO: setTimePlaceConfirm(userId) -- for the meeting to be confirmed
//               (both users need to confirm on the time and place I think... (one suggest and one confirmed)
//              TODO: call presenter to print msg of successful or not [Jiaqi]
                break;
            case 3:
                List<Meeting> meetingsToBeConfirmed = mm.getUnConfirmMeeting(userId);
//              TODO: call presenter to print it (all the meetings) [Jiaqi]
//              TODO: ask the user to enter the meeting id of the meeting to be confirmed
//              TODO: public Boolean setMeetingConfirm(TradeManager tradeManager, Meeting meeting, int userId)
//               a setMeetingConfirmed method?(I put it in the MeetingManager
//              TODO: call presenter to print msg of successful or not [Jiaqi]
                break;
            case 4:
                // List<Meeting> meetingsToBeConfirmed = mm.getUnConfirmMeeting(userId); (dupli. code)
//              TODO: call the presenter to print it [Jiaqi]
                break;
            case 5:
                List<Meeting> meetingsConfirmed = mm.getCompleteMeeting(userId);
//              TODO: call the presenter to print it [Jiaqi]
                break;
            case 6:
//              TODO: get to-be-opened trades for the userId [getWaitTrade]
//              TODO: let presenter prints it [Jiaqi]
//              TODO: user can enter the trade id for the one that he/she wants to set up the first meeting for
//              TODO: user can enter the other user's id or username and we'll set it up
//              TODO: add it to the meetingManager
//              TODO: let presenter print msg of successful or not [Jiaqi]
                break;
        }

    }

}