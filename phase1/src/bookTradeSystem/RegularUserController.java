package bookTradeSystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter.
 */
public class RegularUserController implements Serializable, Controllable {
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;
    // whether the max transactions per week threshold is reassessed
    private boolean thresholdReassessed;

    /**
     * Constructs a RegularUserController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserController(DisplaySystem ds, FilesReaderWriter rw,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
        this.thresholdReassessed = false;
    }

    /**
     * This method gathers all the necessary notifications
     * for the regular user.
     *
     * @return Notifications as properly formatted strings.
     * @throws FileNotFoundException In case the file can't be found.
     */
    @Override
    public String alerts() throws IOException {
        //Read this in from file
        //Exception needs to be resolved in main or TradingSystem.
        User regUser = um.findUser(username);
        StringBuilder notification;
        notification = new StringBuilder();
        String filepath = "UserAlerts.csv"; // move it to src and not the bookTradeSystem
        notification.append(rw.readFromMenu(filepath)).append("/n");
        // Your current status:   (frozen / unfrozen) + corresponding messages.
        // check if we should freeze this user based on the number of incomplete transactions this user has so far
        // Q: what do we do with the case when the admin unfreezes the user?
        // A: extends the threshold value for the user -- but if numFrozen = 3 -- permanent frozen
        // if user is not frozen
        if (!regUser.getIfFrozen()) {
            // this check if for the uncompletedTransactions one
            freezeUserOrNot(regUser);
            ds.printOut("You are frozen because you have exceeded the maximum number of uncompleted transactions limit.");
        }
        notification.append("Your current status:").append(regUser.getIfFrozen()).append("/n");
        notification.append("You have borrowed:").append(regUser.getNumBorrowed());
        notification.append("You have lent:").append(regUser.getNumLent());
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES");
        notification.append("Max number of transactions a week = ").append(User.getMaxNumTransactionsAllowedAWeek());
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(User.getMaxNumTransactionIncomplete());
        notification.append("Max umber of books you must lend before you can borrow = ").append(User.getNumLendBeforeBorrow());
        notification.append("Max edits per user for meeting’s date + time = ").append(User.getMaxMeetingDateTimeEdits());
        return notification.toString();
    }

    /**
     * This method calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     *
     * @param mainMenuOption The main menu option chosen by the regular user.
     * @param subMenuOption  The sub menu option for a particular sub menu chosen by the regular user.
     * @throws InvalidIdException In case the id is invalid.
     *
     */
    @Override
    public void actionResponse(int mainMenuOption, int subMenuOption) throws InvalidIdException {
       /*
        1. decide the menu options
        1.5 decide how to read in user's input
        2. decide what use case method to call for each menu option
        3. decide what presenter method to call to print the results for each menu option

        */
        User thisUser = um.findUser(userId);
        switch (mainMenuOption) {
            case 1:
                userAccountMenuResponse(subMenuOption);
                break;
            case 2:
                //TODO: lock here or in the options
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                    userTradingMenuResponse(subMenuOption);
                }
                break;
            case 3:
                //TODO: lock here or in the options
                if (thisUser.getIfFrozen()){
                    ds.printOut("This menu is locked");}
                else{
                    userMeetingMenuResponse(subMenuOption);
                }
                break;
        }

    }

    private void userAccountMenuResponse(int subMenuOption) throws InvalidIdException {
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
        ArrayList<Item> allOtherItems = um.allItems(userId);
        switch (subMenuOption) {
            case 1:
                // print items in all users inventory except this user
                ds.printResult(allOtherItems);
                break;
            case 2:
                // add the id to user's wishlist
                ds.printResult(um.addItemWishlist(getItemID(allOtherItems, 1), username));
                break;
            case 3:
                // print all the items being searched for
                ds.printResult(um.searchItem(getItemName()));
            case 4:
                // remove the item id from wishlist
                ds.printResult(um.removeItemWishlist(getItemID(allOtherItems, 0), username));
                break;
            case 5:
                ArrayList<Item> userInventory = um.findUser(userId).getInventory();
                ds.printResult(userInventory);
                ds.printResult(um.removeItemInventory(getItemID(userInventory, 1), username));
                break;
            case 6:
                ds.printResult(um.requestUnfreeze(username, getMessage("Leave your unfreeze request message")));
                break;
            case 7:
                um.requestAddItem(getItemName(), getMessage("Enter the description of the item"), userId);
                ds.printResult(true);
                break;
            case 8:
                List<Item> threeItems = new ArrayList<>();
                List<Integer> recentThreeTradedIds = tm.recentThreeItem(userId);
                for (int id: recentThreeTradedIds) {
                    threeItems.add(idToItem(id));
                }
                ds.printResult(threeItems);
                break;
        }
    }

    private void userTradingMenuResponse(int subMenuOption) throws InvalidIdException {
        /*
          1.Request a trade (lend / borrow / two-way) !!!!- NEED to remove item from wishlist &/ inventory (maybe in constructor???)
          2.Respond to trade requests (agree / disagree)
          3.View open trades
          4.View closed trades
          5.Confirm that a trade has been completed
          6.See top three most frequent trading partners
          7.View transactions that have been cancelled
         */
        User thisUser = um.findUser(userId);
        // reassess it at the first day of the week - only once
        // TODO: small bug - user has to login in other days (non-Sundays) to re-enable this function for next Sunday
        //  and can only reassess it on Sunday (the first day of the week)
        reassessNumTransactionsLeftForTheWeek(thisUser);
        switch (subMenuOption) {
            case 1:
                if (thisUser.getNumTransactionLeftForTheWeek() == 0){
                    // the case with user reaching the max number of transactions for the week
                    lockMessageForThreshold();
                }
                else {
                    // get whether it is one-way-trade or two-way-trade
                    // 1 - one-way-trade
                    // 2 - two-way-trade
                    int numKindOfTrade = getNumKindOfTrade();
                    Trade trade;
                    int itemId2 = 0;
                    //get info for 1 way trade
                    int userId1 = getUserID("borrower (if one-way-trade) or borrower-and-lender 1 (if two-way-trade)");
                    int userId2 = getUserID("lender (if one-way-trade) or borrower-and-lender 2 (if two-way-trade)");
                    int itemId = getItemID(getAllItems(), 1);
                    if (numKindOfTrade == 2){
                        itemId2 = getItemID(getAllItems(), 1);
                    }
                    String tradeType = getTradeType();
                    // preparing the trade object
                    if (numKindOfTrade == 1) {
                        // new one-way-trade
                        trade = new Trade(userId1, userId2, itemId, tradeType, true);
                    }
                    else {
                        // new two-way-trade
                        trade = new Trade(userId1, userId2, itemId, itemId2, tradeType, false);
                    }
                    // validate the trade
                    // pass in trade, borrower, lender
                    if (tm.validateTrade(trade, um.findUser(userId1))) {
                        // add trade
                        tm.addTrade(trade);
                        // tell the user it's successful
                        ds.printResult(true);
                        // set status for the person who requested the trade
                        trade.setUserStatus(userId, "Agree");
                        // change the threshold value
                        changeNumTradesLeftForTheWeek(thisUser);
                    }
                    else {
                        ds.printResult(false);
                        // TODO: should I put this here?
                        // system auto-freeze
                        // user borrow more than lend
                        if (thisUser.getNumBorrowed() > thisUser.getNumLent()){
                            um.freezeUser(username);
                            ds.printOut("You're frozen because you borrowed more than lend.");
                        }
                    }
                }
                break;
            case 2:
                if (thisUser.getNumTransactionLeftForTheWeek() == 0) {
                    // the case with user reaching the max number of transactions for the week
                    lockMessageForThreshold();
                }
                else {
                    //ASKS THE USER TO ENTER TRADE ID AND ENTER AGREE OR DISAGREE
                    //TODO: so here assume wait-to-be-opened = wait for the other user's response i guess
                    ds.printResult(tm.getWaitTrade(userId));
                    Trade trade = tm.getTradeById(getTradeID());
                    int itemid22 = 0;
                    // if it's one-way-trade
                    // only need borrower id, lender id, and the item id
                    int userId11 = trade.getIds().get(1);
                    int userId22 = trade.getIds().get(2);
                    //TODO: tm needs to add itemId1 = 0 for the one-way-trade constructor
                    int itemId11 = trade.getIds().get(3);
                    if (!trade.getIsOneWayTrade()){
                        // two-way-trade
                        // need one more item id
                        itemid22 = trade.getIds().get(4);
                    }
                    String tradeStatus = getAgreeOrNot();
                    //set the tradeStatus for this trade
                    trade.setUserStatus(userId, tradeStatus);
                    //remove items -- if agree
                    if (tradeStatus.equals("Agree")) {
                        // remove + record the borrowing/lending
                        removeItemFromUsers(userId11, userId22, itemId11);
                        if (!trade.getIsOneWayTrade()) {
                            // remove + record the borrowing/lending
                            removeItemFromUsers(userId11, userId22, itemid22);
                        }
                    }
                    else{
                        // cancel the trade so user can see it's cancelled
                        trade.cancelTrade();
                    }
                    ds.printResult(true);
                }
                break;
            case 3:
                ds.printResult(tm.getOpenTrade(userId));
                break;
            case 4:
                ds.printResult(tm.getClosedTrade(userId));
                break;
            case 5:
                ds.printResult(tm.getOpenTrade(userId));
                int tradeId = getTradeID();
//              let user enter trade id and we use it to confirm complete
                ds.printResult(tm.confirmComplete(tradeId));
                break;
            case 6:
                List<Integer> topThreeIDS= tm.topThreePartners(userId);
                List<User> topThree = new ArrayList<>();
                for (int id : topThreeIDS){
                    topThree.add(um.findUser(id));
                }
                ds.printResult(topThree);
                break;
            case 7:
                ds.printResult(tm.getCancelledTrade(userId));
                break;

        }
    }

    private void userMeetingMenuResponse(int subMenuOption) throws InvalidIdException {
       /*
    1.Suggest/edit time and place for meetings
    2.Confirm time and place for meetings
    3.Confirm the meeting took place
    4.See the list of meetings need to be confirmed (that it took place)
    5.See the list of meetings that have been confirmed (that have taken place)
    6.View to-be-opened trades and set up first meeting
        */

        switch (subMenuOption) {
            case 1:
                Meeting meeting = getMeeting();
                int year = getYear();
                int month = getMonth();
                int day = getDay(year, month);
                int hour = getHour();
                int min = getMin();
                int sec = 0;
                String place = getPlace();
                //int year, int month, int day, int hour, int min, int sec
//              call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
                ds.printResult(meeting.setTimePlaceEdit(userId, year, month, day, hour, min, sec, place));
                // for the edit threshold
                ds.printOut(mm.getEditOverThreshold(tm, meeting));
                break;
            case 2:
                Meeting meeting2 = getMeeting();
                ds.printResult(meeting2.setTimePlaceConfirm(userId));
                break;
            case 3:
//              "confirmed" means the meeting haven't take place but time and place are confirmed
                ds.printResult(mm.getUnConfirmMeeting(userId));
                Meeting meeting3 = getMeeting();
                ds.printResult(mm.setMeetingConfirm(tm, meeting3, userId));
                break;
            case 4:
                ds.printResult(mm.getUnConfirmMeeting(userId));
                break;
            case 5:
                ds.printResult(mm.getCompleteMeeting(userId));
                break;
            case 6:
                // print a list of trades waiting to be opened -- to have the 1st meeting
                // because once the meeting is set up --> open
                // so need to set up first meeting for the waiting to be opened trades
                ds.printResult(tm.getWaitTrade(userId));
                //public Meeting(int tradeId, int userId1, int userId2, int meetingNum)
                int tradeId = getTradeID();
                int userId1 = getUserID("borrower or borrower-and-lender 1 (if two-way-trade)");
                int userId2 = getUserID("lender or borrower-and-lender 2 (if two-way-trade)");
                ds.printResult(mm.addMeeting(tradeId, userId1, userId2,1, tm));
                break;
        }

    }

    private void reassessNumTransactionsLeftForTheWeek(User thisUser) {
        if (isFirstDayOfTheWeek() && !thresholdReassessed){
            thisUser.setTransactionLeftForTheWeek(User.getMaxNumTransactionsAllowedAWeek());
            thresholdReassessed = true;
        }
        else if (!isFirstDayOfTheWeek()){
            thresholdReassessed = false;
        }
    }

    private void lockMessageForThreshold() {
        ds.printOut("This option is locked");
        ds.printOut("You have reached the" + User.getMaxNumTransactionIncomplete() + "transactions a week limit");
    }

    private void changeNumTradesLeftForTheWeek(User thisUser){
        /*
        Based on code by Kashif from https://stackoverflow.com/questions/18600257/how-to-get-the-weekday-of-a-date
         */
        int currentVal = thisUser.getNumTransactionLeftForTheWeek();
        thisUser.setTransactionLeftForTheWeek(currentVal-1);
    }

    private boolean isFirstDayOfTheWeek(){
        Calendar c = Calendar.getInstance();
        return c.getFirstDayOfWeek() == c.get(Calendar.DAY_OF_WEEK);
    }


    private boolean freezeUserOrNot(User thisUser){
        int numFrozen = thisUser.getNumFrozen();
        // find the num of uncompleted transactions
        int numUncompletedTransactions = numUncompletedTransactions();
        // if user went over the threshold
        // or if the user's been frozen for three times -- freeze the account every time = permanent freeze
        int threshold =  User.getMaxNumTransactionIncomplete() + (numFrozen * User.getMaxNumTransactionIncomplete());
        if (numUncompletedTransactions > threshold || thisUser.getNumFrozen() == 3) {
            um.freezeUser(username);
            thisUser.addOneToNumFrozen();
            return true;
        }
        return false;
    }

    private int numUncompletedTransactions() {
        List<Integer> uniqueTradeIDs = new ArrayList<>();
        List<Meeting> overTimeMeetings = mm.getListOverTime(userId);
        for (Meeting meeting : overTimeMeetings){
            int tradeID = meeting.getTradeId();
            if (!uniqueTradeIDs.contains(tradeID)){
                uniqueTradeIDs.add(tradeID);
            }
        }
        return uniqueTradeIDs.size();
    }

    private Meeting getMeeting() throws InvalidIdException {
        ds.printResult(mm.getUnConfirmTimePlace(userId, tm));
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = getTradeID();
        int numMeeting = getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }

    /**
     * Other ask-user-for-input methods
     */
    private int getItemID(ArrayList<Item> potentialItems, int type) {
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        boolean okInput = false;
        // all possible ids the user can pick from
        ArrayList<Integer> potentialIds;
        // depends on the option the user chooses
        if (type == 1) {
            potentialIds = getItemsIDs(potentialItems);
        } else {
            potentialIds = um.findUser(userId).getWishList();
        }
        Scanner sc = new Scanner(System.in);
        int itemId = 0;
        do {
            ds.printOut("Please enter the id of the item: ");
            // if the input is int
            if (sc.hasNextInt()) {
                itemId = sc.nextInt();
                // if the input is valid
                if (potentialIds.contains(itemId)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return itemId;
    }

    //TODO maybe put this somewhere else
    private ArrayList<Integer> getItemsIDs(ArrayList<Item> allOtherItems) {
        ArrayList<Integer> potentialIds = new ArrayList<>();
        //get the id of all the items in the given arraylist
        for (Item item : allOtherItems) {
            potentialIds.add(item.getItemId());
        }
        return potentialIds;
    }

    private String getItemName() {
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the prefix of the item being searched for: ");
        return sc.nextLine();
    }

    //TODO maybe put this somewhere else
    //TODO MAKE SURE ALL IDS IN RECENT THREE ITEMS METHOD EXIST IN THE ARRAYLIST
    private Item idToItem(int id) {
        //Get all the items in the system
        ArrayList<Item> allOtherItems = getAllItems();
        //find the item with <id>
        for (Item item : allOtherItems) {
            if (item.getOwnerId() == id) {
                return item;
            }
        }
        return null;
    }

    private ArrayList<Item> getAllItems() {
        ArrayList<Item> allOtherItems = um.allItems(userId);
        allOtherItems.addAll(um.findUser(userId).getInventory());
        return allOtherItems;
    }

    private String getMessage(String TypeOfMessage){
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

    private int getUserID(String type){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int userId = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the userId of the " + type + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                userId = sc.nextInt();
                // if the input is valid
                if (um.checkUser(um.idToUsername(userId))) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return userId;
    }

    /* useless for now...
    private enum TradeType{
        Permanent, Temporary
    }*/

    private String getTradeType(){
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


    private int getTradeID() {
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int tradeId = 0;
        boolean okInput = false;
        do {
            ds.printOut("Please enter the id of the trade : ");
            // if the input is int
            if (sc.hasNextInt()) {
                tradeId = sc.nextInt();
                // if the trade with this tradeId rests in the tradeManager
                if (tm.checkInManager(tradeId)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid id!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return tradeId;
    }

    private String getAgreeOrNot(){
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

    private int getYear(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int year = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the year (2020-2030) " + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                year = sc.nextInt();
                // if the input is valid
                if (isValidYear(year)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid year!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return year;
    }

    private int getMonth(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int month = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the month (1-12)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                month = sc.nextInt();
                // if the input is valid
                if (isValidMonth(month)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid month!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return month;
    }

    private int getDay(int year, int month){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int day = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the day" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                day = sc.nextInt();
                // if the input is valid
                if (isValidDay(year, month, day)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid day!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return day;

    }

    private int getHour(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int hour = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the hour (1-24)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                hour = sc.nextInt();
                // if the input is valid
                if (isValidHour(hour)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid hour!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return hour;

    }

    private int getMin(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int min = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the minute (0-59)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                min = sc.nextInt();
                // if the input is valid
                if (isValidMin(min)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid minute!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return min;

    }

    private boolean isValidYear(int year){
        return 2020 <= year && year <= 2030;
    }

    private boolean isValidMonth(int month){
        return 1 <= month && month <= 12;
    }

    private boolean isValidDay(int year, int month, int day){
        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return 1 <= day && day <= 31;
        }
        else if(month == 4 || month == 6|| month == 9 || month == 11){
            return 1 <= day && day <= 30;
        }
        else{
            if (year % 4 == 0 && (year % 100 != 0 || year % 100 == 0 && year % 400 == 0)){
                return 1 <= day && day <= 29;
            }
            return 1 <= day && day <= 28;
        }
    }
    private boolean isValidHour(int hour){
        return 1 <= hour && hour <= 24;
    }
    private boolean isValidMin(int min){
        return 0 <= min && min <= 59;
    }

    private String getPlace(){
        Scanner sc = new Scanner(System.in);
        ds.printOut("Please enter the name of the place: ");
        String place;
        //read the first line
        place = sc.nextLine();
        return place;
    }

    private int getNumMeeting(){
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

    private int getNumKindOfTrade(){
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


    private void removeItemFromUsers(int userId1, int userId2, int itemId) {
        User user1 = um.findUser(userId1);
        User user2 = um.findUser(userId2);
        //TODO: shouldn't call contains here - maybe have a method for it in
        // the item manager
        if (user1.getWishList().contains(itemId)) {
            //user1 = borrower
            um.removeItemWishlist(itemId, user1.getUsername());
            // record the borrow
            user1.addOneToNumBorrowed();
            //remove the item from user2's inventory
            um.removeItemInventory(itemId, user2.getUsername());
            // record the lend
            user2.addOneToNumLent();
        } else {
            //user2 = borrower
            um.removeItemWishlist(itemId, user2.getUsername());
            // record the borrow
            user2.addOneToNumBorrowed();
            //remove item from user1's inventory
            um.removeItemInventory(itemId, user1.getUsername());
            // record the lend
            user1.addOneToNumLent();

        }
    }
}

