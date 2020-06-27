package bookTradeSystem;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.List;

public class RegularUserController implements Serializable, Controllable {
    TradeManager tm;
    MeetingManager mm;
    UserManager um;
// TODO: just leave one -- username / id
    String username;
    int userId;

//    TODO: consider refactoring - maybe can pass in as param instead of in the constructor?
    public RegularUserController(TradeManager tm, MeetingManager mm, UserManager um, String username, int userId) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
    }

    @Override
    public void alerts() {
       /*
        1. get the list of things to be printed
        2. call the presenter class to let it format it and then print
        */
//      TODO: TO BE DECIDED
    }
    // this method'll take the option other than the logout option
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) {
       /*
        1. decide the menu options
        2. decide what use case method to call for each menu option (discuss with Gabriel)
        3. decide what presenter method to call to print the results for each menu option (discuss with Jiaqi)

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
//   TODO: considering refactoring
    // takes all menuOption other than ExitMenu
    private void userAccountMenuResponse(int subMenuOption) {
        /*
         1.Browse all the books in other users inventories
         2.Add to own Wish List

//   FIXME: 3.Add to own Inventory (create a new item in the system with a name and description)
      --> don't need this bc of 7

         4.Remove from own Wish List
         5.Remove from own Inventory
         6.Request to unfreeze account
         7.Request that an item be added to your inventory
         8.See most recent three items traded
         */
        switch(subMenuOption){
            case 1:
                List<User> allUsers = um.getListUser();
//              TODO: instead of search book...return a list of books of other users instead?
                break;
            case 2:
                um.addItemWishlist(getItem("ADD"), username);
//              TODO: call presenter class to pass the boolean to and print msg
                break;
            case 3:
                um.addItemInventory(getItem("ADD"), username);
//              TODO: call presenter class to pass the boolean to and print msg
//              break;
            case 4:
                um.removeItemWishlist(getItem("REMOVE"), username);
//              TODO: call presenter class to pass the boolean to and print msg
                break;
            case 5:
                um.removeItemInventory(getItem("REMOVE"), username);
//              TODO: call presenter class to pass the boolean to and print msg
                break;
            case 6:
//              TODO: maybe have a setter for the listUnfreezeRequest in userManager?
//              TODO: add the user's request to the list in proper format*
                break;
            case 7:
//              TODO: maybe there can be a listAddItemRequest in userManager?
//              TODO: don't need case 3 bc admin need to review it anyway :p
                break;
            case 8:
//              TODO: top three items traded --> id plz!!!
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
//              TODO: CALL PRESENTER TO PRINT MSG -- SUCCESSFUL OR NOT
                break;
            case 2:
 //             TODO: PRINT THE LIST OF WAIT TO BE OPENED TRADES (OR MAYBE HAVE A WAIT TO BE AGREED LIST OF TRADES?)
 //             TODO: ASKS THE USER WHETHER TO ENTER TRADE ID AND ENTER AGREE OR DISAGREE
//              TODO: WE CAN JUST SET THE MAP^ USE USERID TO SET THE BOOLEAN TO TRUE/FALSE
//              TODO: CALL PRESENTER TO PRINT MSG -- SUCCESSFUL OR NOT
                break;
            case 3:
                tm.getOpenTrade(userId);
//              TODO: call presenter class
                break;
            case 4:
                tm.getClosedTrade(userId);
//              TODO: call presenter class
                break;
            case 5:
//              TODO: print all the open trades
                tm.getOpenTrade(userId);
//              TODO: let user enter trade id and we use it to confirm complete
//              tm.confirmComplete();
//              TODO: call presenter class
                break;
            case 6:
//              TODO: we need id for the top three partners :) - storage in User class
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
        5.See the list of meetings that have been confirmed (assume --> took place??)
        6.Set up second meeting for temporary trades
        7.View to-be-opened trades and set up first meeting
        */

        switch(subMenuOption) {
            case 1:
                List<Meeting> unconfirmedMeetings = mm.getUnConfirmTimePlace(userId);
//              TODO: call presenter to print it
//              TODO: ask the user to enter the meeting id and the time and place
//              TODO: have a method in meetingManager -- given id - return meeting
//              TODO: call the setTimePlaceEdit method to pass in param + edit (*pass in date object for time)
//              TODO: increment user's num edited by 1 --> WHAT IS TIMEPLACEEDIT IN MEETING CLASS???
//               --> IDEALLY WE HAVE MAYBE A MAP OF USERID TO nunEdits FOR A MEETING??
//              TODO: call presenter to print msg of successful or not
                break;
            case 2:
                //List<Meeting> unconfirmedMeetings = mm.getUnConfirmTimePlace(userId); (dupli. code)
//              TODO: call presenter to print it
//              TODO: ask the user to enter the meeting id
//              TODO: do get meeting by trade id / should have another one for meeting id?
//              TODO: setTimePlaceConfirm(userId) -- for the meeting to be confirmed
//               (also - both users need to confirm on the time and place I think...
//              TODO: call presenter to print msg of successful or not
                break;
            case 3:
                List<Meeting> meetingsToBeConfirmed = mm.getUnConfirmMeeting(userId);
//              TODO: call presenter to print it (all the meetings)
//              TODO: ask the user to enter the meeting id of the meeting to be confirmed
//              TODO: a setMeetingConfirmed method? -- so we can let the user to confirm the meeting took place
//              TODO: call presenter to print msg of successful or not
                break;
            case 4:
                // List<Meeting> meetingsToBeConfirmed = mm.getUnConfirmMeeting(userId); (dupli. code)
//              TODO: call the presenter to print it
                break;
            case 5:
                List<Meeting> meetingsConfirmed = mm.getCompleteMeeting(userId);
//              TODO: call the presenter to print it
                break;
            case 6:
//              TODO: get and print all temporary trades for the user (assume it'll be 'TEMPORARY' for the tradeType?)
//              TODO: let the presenter print it
                break;
            case 7:
//              TODO: get to-be-opened trades for the userId
//              TODO: let presenter prints it
//              TODO: user can enter the trade id for the one that he/she wants to set up the first meeting for
//              TODO: user can enter the other user's id or username and we'll set it up
//              TODO: add it to the meetingManager
//              TODO: let presenter print msg of successful or not
                break;
        }

    }

}