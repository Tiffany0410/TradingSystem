package bookTradeSystem;

import java.io.Serializable;

public class RegularUserController implements Serializable, Controllable {
    /**TradeManager tm;
    MeetingManager mm;
    UserManager um;
    FileWriter fw;
    DisplaySystem ds;**/
    int userId;

    public RegularUserController(int userId) {
        //initializes user's id
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
         3.Add to own Inventory (create a new item in the system with a name and description)
         4.Remove from own Wish List
         5.Remove from own Inventory
         6.Request to unfreeze account
         7.Request that an item be added to your inventory
         8. See most recent three items traded
         */
        switch(subMenuOption){
            case 1:
//                TODO
                  break;
            case 2:
//                TODO
                break;
            case 3:
//                TODO
                break;
            case 4:
//                TODO
                break;
            case 5:
//                TODO
                break;
            case 6:
//                TODO
                break;
            case 7:
//                TODO
                break;
            case 8:
//                TODO
                break;
        }



    }
    private void userTradingMenuResponse(int subMenuOption) {
        /*
          1.Request a trade (lend / borrow / two-way)
          2.Respond to trade requests (agree / disagree)
          3.View open trades
          4.View closed trades
          5.Confirm that a trade has been completed
          6.See top three most frequent trading partners
          7.View transactions that have been cancelled
         */
        switch (subMenuOption) {
            case 1:
//                TODO
                break;
            case 2:
//                TODO
                break;
            case 3:
//                TODO
                break;
            case 4:
//                TODO
                break;
            case 5:
//                TODO
                break;
            case 6:
//                TODO
                break;
            case 7:
//                TODO
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
        6.Set up second meeting for temporary trades
        7. View to-be-opened trades and set up first meeting
        */

        switch(subMenuOption) {
            case 1:
//                TODO
                break;
            case 2:
//                TODO
                break;
            case 3:
//                TODO
                break;
            case 4:
//                TODO
                break;
            case 5:
//                TODO
                break;
            case 6:
//                TODO
                break;
            case 7:
//                TODO
                break;
        }

    }

}