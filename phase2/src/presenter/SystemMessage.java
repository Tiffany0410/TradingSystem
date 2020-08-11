package presenter;

import controllers.regularusersubcontrollers.RegularUserThresholdController;
import managers.actionmanager.Action;
import managers.feedbackmanager.Review;
import managers.itemmanager.Item;
import managers.messagemanger.Message;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class SystemMessage {

    /**
     * Constructs a SystemMessage instance.
     */
    public SystemMessage(){

    }

    /**
     * Puts together the message for when there is nothing to be shown.
     * @return The message for when there is nothing to be shown.
     */
    public String msgForNothing(){
        return "There's nothing here.";
    }

    /**
     * Puts together the message for invalid input.
     * @return The message for invalid input.
     */
    public String invalidInput() {
        return "Invalid put in, please type again.";
    }

    /**
     * Puts together the message for invalid number input.
     * @return The message for invalid number input.
     */
    public String invalidNumber(){
        return "Invalid put in, please type a number.";
    }

    /**
     * Puts together the message for invalid date and time input.
     * @return The message for invalid date and time input.
     */
    public String tryAgainMsgForWrongDatetime(){
        return "Invalid input: the year must be between 2020 and 2024, inclusive." +
                " Also, you cannot set the date to be earlier than today or" +
                " today.";
    }

    /**
     * Puts together the result of action with object type.
     * @param obj The list of objects need to be printed.
     * @return The message related to the result of the action.
     */
    public String printResult(ArrayList<Object> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    /**
     * Puts together the list of items in properly formatted strings.
     * @param obj The list of items.
     * @return The list of items in properly formatted strings.
     */
    public String printItemResult(ArrayList<Item> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    /**
     * Gathers all the necessary messages
     * for the regular user.
     * @param um The user manager.
     * @param tc The threshold controller.
     * @param username The username of the user.
     * @param menuPartOfAlert The part of the notification read from a menu.
     * @param thresholdValues A list of threshold values.
     * @return messages as properly formatted strings.
     */
    public String regUserAlerts(UserManager um, RegularUserThresholdController tc, String username, String
            menuPartOfAlert, ArrayList<Integer> thresholdValues) {
        StringBuilder notification;
        notification = new StringBuilder();
        notification.append(menuPartOfAlert).append("\n");
        activeAlerts(notification, um, tc, username, thresholdValues);
        return notification.toString();
    }

    private void activeAlerts(StringBuilder notification, UserManager um, RegularUserThresholdController tc, String username, ArrayList<Integer> thresholdValues) {
        int userId = um.usernameToID(username);
        if (!um.getFrozenStatus(username)) {
            // this check if for the uncompletedTransactions one
            if (tc.freezeUserOrNot(thresholdValues.get(1))){
                notification.append("NOTE: You are frozen because you have reached the maximum number of uncompleted transactions limit.".toUpperCase()).append("\n\n");
            }
        }
        tc.reassessNumTransactionsLeftForTheWeek(thresholdValues.get(0));
        notification.append("Your username is ").append(username).append(".\n");
        notification.append("Your userId is ").append(userId).append(".\n");
        notification.append("The answer to you're frozen is ").append(um.getFrozenStatus(username)).append(".\n");
        notification.append("The answer to you're on-vacation is ").append(um.getInfo(userId, "Vacation") == 1).append(".\n");
        notification.append("You have borrowed:").append(um.getInfo(username, "NumBorrowed")).append(".\n");
        notification.append("Your home city is ").append(um.getHome(userId)).append(".\n");
        notification.append("You have lent:").append(um.getInfo(username, "NumLent")).append(".\n");
        notification.append("KEEP IN MIND OF THE FOLLOWING THRESHOLD VALUES").append(".\n");
        notification.append("Max number of transactions a week = ").append(thresholdValues.get(0)).append(".\n");
        notification.append("Max number of transactions that can be incomplete before the account is frozen = ").append(thresholdValues.get(1)).append(".\n");
        notification.append("Max number of books you must lend before you can borrow = ").append(thresholdValues.get(2)).append(".\n");
        notification.append("Max edits per user for meeting’s time + place = ").append(thresholdValues.get(3)).append(".\n");
        notification.append("FOR YOU...").append("\n");
        notification.append("YOU STILL HAVE " + um.getInfo(userId, "TransactionLeftForTheWeek") + " TRANSACTIONS LEFT FOR THE WEEK.");
        notification.append("If you're wondering: the transactions left for the week will be set back to full every MONDAY.");

    }

    /**
     * Put together the message for when the option is locked for the user
     * due to the threshold.
     * @return The message described above.
     */
    public String lockMessageForThreshold(int maxNumTransactionAWeek) {
        return "This option is locked \n" +
                "You have reached the " + maxNumTransactionAWeek + " transactions a week limit" +
                "\n";
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     */
    public String msgForThresholdValue(int currentVal)
    {
        return "The current threshold value is " + currentVal;
    }


    /**
     * Prints the message for the guest that tries to access options
     * that are meant for users.
     */
    public String msgForGuest(){return "Please login, guest account can't do this."; }

    /**
     * Prints the message for the user that tries to
     * access menus that can't access because of their
     * frozen status.
     */
    public String lockMessageForFrozen(){
        return "This menu is locked because you're frozen.";
    }


    /**
     * Put together the message for the user who tries
     * to access menus that he/she can't access because
     * their status is on-vacation.
     * @return The message described above.
     */
    public String lockMessageForVacation() {
        return"Because you're on vacation, you can't be involved in trade/meeting. \n " +
                "If you're back from vacation, please change your on-vacation status in the Account Menu.";
    }

    /**
     * Puts together a message that indicates the result.
     * @param validator The result.
     * @return The message that indicates the result.
     */
    public String msgForResult(boolean validator){
        if (validator){
            return "Success";
        }
        else {
            return "Fail";
        }
    }

    /**
     * Puts together a message that indicates whether the user set the status successfully or not.
     * @param validator The result of the status change.
     * @return The message that indicates whether the user set the status successfully or not.
     */
    public String msgForStatusChangeResult(boolean validator){
        if (validator){
            return "Success";
        }
        else {
            return "Fail because you are already in the status that you're trying to set.";
        }
    }

    /**
     * Puts together a message if user's response to request is send successfully.
     * @param validator The result of whether response to request is send successfully.
     * @return A message indicating if user's response to request is send successfully.
     */
    public String msgForRequestProcess(boolean validator){
        if (validator) {
            return "Your response to request is sent successfully";
        }
        else{
            return "Your response to request is sent unsuccessfully";
        }
    }

    /**
     * Puts together a message indicating the result of the request.
     * @param validator Whether or not the request is successful.
     * @return The result of the request, in string.
     */
    public String msgForRequestResult(boolean validator){
        if (validator) {
            return "Your request is successful.";
        }
        else{
            return "Your request is unsuccessful.";
        }
    }
    /**
     * Puts together a message telling the user he/she is frozen because of a threshold.
     * @return A message telling the user to try again because of a wrong input.
     */
    public String failMessageForFrozen(){
        return "You're frozen because you borrowed more than you lend";
    }

    /**
     * Puts together a message telling the user he/she can't edit anymore because of a threshold.
     * @return A message telling the user he/she can't edit anymore because of a threshold.
     */
    public String lockMessageForTPLimit() {
        return "You can't edit any more because the time and place edits limit is reached. This trade has been cancelled.";}

    /**
     * Puts together a message that indicates whether or not the friend requests are sent successfully.
      * @param validator Whether or not the friend requests are sent successfully.
     * @param userToID The user id of the person the user is trying to send the request to.
     * @return a messgae for friend requests
     */
    public String msgForFriendRequest(boolean validator, int userToID){
        if (validator){
            return "Your friend request has been sent to user id " + userToID + " successfully.";
        }
        else {
            return "Failed to send friend request to " + userToID + ".\n" +
                    "It seems that you are trying to send friend request twice to " + userToID +
                    " or " + userToID + " is already in your list of friends.";
        }
    }

    /** Puts together a string that describes there's nothing inside something.
     * @param string A string to describe the type of "something".
     * @return A string to describe there's nothing inside something.
     */
    public String msgForNothing(String string){
        return "There is nothing in " + string + " .";
    }

    /**
     * Print out the list of username of the Regular Users.
     * @param listOfUser The list of Regular Users.
     * @return The list of username of the Regular Users.
     */
    public String printListUser(ArrayList<TradableUser> listOfUser) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TradableUser user : listOfUser) {
            stringBuilder.append("Tradable User ID #").append(user.getId()).append(" with username: ").append(user.getUsername()).append("\n");
        } return stringBuilder.toString();
    }


    /** get a string for the list of users
     * @param requests a map of friend requests
     * @return a string to describe the list of users
     */
    public String printFriendRequest(HashMap<TradableUser, String> requests){
        StringBuilder string = new StringBuilder();
        string.append("Here is a list of friend requests:\n");
        int count = 1;
        for (HashMap.Entry<TradableUser, String> set: requests.entrySet()){
            TradableUser user = set.getKey();
            String msg = set.getValue();
            string.append("#" + count + ". Request from user " + user.getUsername() + " with id " + user.getId() +
                    "\n A message from this user: " + msg + "\n");
        }
        return string.toString();
    }


    /**
     * Puts together a string that contains all string representation
     * of the objects.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects.
     */
    public String printListObject(ArrayList<Object> objects){
        StringBuilder out = new StringBuilder();
        if (objects.isEmpty()) {return "\n";}
        for (Object object: objects){
            if (object != null) {out.append(object.toString()).append("\n");}
        }
        return out.toString();
    }

    /**
     * Puts together a string that contains all string representation
     * of the objects, numbered.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects, numbered.
     */
    public String printListNumberedObject(ArrayList<Object> objects){
        int i = 0;
        StringBuilder out = new StringBuilder();
        for (Object object: objects){
            out.append("#").append(i).append(". ").append(object.toString()).append("\n");
            i ++;
        }
        return out.toString();
    }


    /**
     * Puts together a message telling the user to try again because of a wrong input.
     * @return A message telling the user to try again because of a wrong input.
     */
    public String tryAgainMsgForWrongInput(){
        return "Please try again, one or more input(s) are invalid.";
    }


    /**
     * Puts together a message telling the user why the time and place cannot be confirmed.
     * @return A message telling the user why the time and place cannot be confirmed.
     */
    public String tryAgainMsgForWrongFormatInput(){
        return "Please try again. One or more of your input(s) were in the incorrect format (ex. we ask for int and you entered a word)." +
                "Also, don't add 0 before a number (ex. we accept 5 but don't accept 05). ";
    }

    /**
     * Puts together a message telling the user why the time and place cannot be confirmed.
     * @return A message telling the user why the time and place cannot be confirmed.
     */
    public String msgForTPcannotConfirm(){
        return "You can't confirm right now because no time/place has been suggested or you have just suggested time/place your" +
                " self. In the second case, the system automatically sets your status as confirmed.";
    }

    /**
     * Puts together a message telling the user that it's not their turn to edit.
     * @return A message telling the user that it's not their turn to edit.
     */
    public String msgForNotYourTurn(){
        return "It's not your turn to edit.";
    }

    /**
     * Puts together a message that indicates the result of user's attempt to follow an item/user.
     * @param validator The result of user's attempt.
     * @return A message that indicates the result of user's attempt to follow an item/user.
     */
    public String msgForFollowResult(boolean validator){
        if (!validator){
            return "Failed. It's probably because you're trying to follow yourself or follow the same user/item twice.";
        }
        return "Success!";
    }

    /**
     * Puts together a message that indicates reasons why there is no trade suggestion.
     * @return A message that indicates reasons why there is no trade suggestion.
     */
    public String msgForNoTradeSuggestion(){
        return msgForNo(" recommended trade suggestion." +
                " It might be because of 1) your wishlist is empty or 2) the items" +
                " in your wishlist are not tradable and the items in the same category " +
                "as the items in your wishlist are also not tradable.");
    }

    /**
     * Puts together a message that indicates the result of user's attempt to confirm a meeting took place.
     * @param validator Whether or not the attempt is successful.
     * @return A message that indicates the result of user's attempt to confirm a meeting took place.
     */
    public String msgForMeetingTookPlaceResult(boolean validator){
        if (!validator){
            return "Failed. It's probably because you have already confirmed it or the meeting time hasn't arrived.";
        }
        return "Success!";
    }

    /**
     * Puts together a message made up of "there is no" part and the input string.
     * @param string The part of the string to be displayed after the "there is no" part.
     * @return The message made up of "there is no" part and the input string.
     */
    public String msgForNo(String string){
        return "There is no " + string + " .";
    }


    /**
     * Puts together a string containing a list of messages.
     * @param messages A list of messages.
     * @return All the messages, in strings.
     */
    public String printAllMessages(ArrayList<Message> messages){
        StringBuilder out = new StringBuilder();
        for (Message msg: messages){
            out.append(msg.toString());
        }
        return out.toString();
    }

    /**
     * Puts together a message indicating whether the trade is complete or not.
     * @param result Whether or not the trade is completed.
     * @return A message indicating whether the trade is complete or not.
     */
    public String msgFortradeCompletedOrNot(boolean result){
        if (result){
            return "This trade is completed.";
        }
        else{
            return "This trade is Incomplete.";
        }
    }


    /**
     * Puts together a message for setting tradable status.
     * @param validator Whether or not the tradable status is set successfully.
     * @param status The tradable status of the item.
     * @return A message for setting the tradable status.
     */
    public String msgForSetTradable(boolean validator, boolean status){
        String tradable_status;
        if (status){
            tradable_status = "tradable";
        }
        else {
            tradable_status = "non-tradable";
        }
        if (validator){
            return "Set item's tradable status to " + tradable_status + ".";
        }
        return "The tradable status for this item is already " + tradable_status + ". ";
    }

    /**
     * Puts together all the historical actions.
     * @param listOfAction The list of historical actions.
     * @return The string representation of all the historical actions in the list.
     */
    public String printHistoricalAction(ArrayList<Action> listOfAction) {
        StringBuilder string = new StringBuilder();
        for (Action action : listOfAction) {
            String userType = action.getUserType();

            RegularUserActionMessage regularUserActionMessage = new RegularUserActionMessage();
            AdminUserActionMessage adminUserActionMessage = new AdminUserActionMessage();

            switch (userType) {
                case "regularUser":
                    string.append(regularUserActionMessage.regularUserAction(action));
                    break;
                case "adminUser":
                    string.append(adminUserActionMessage.adminUserAction(action));
                    break;
            }
        }
        return string.toString();
    }

    /**
     * Puts together a string representation of an object.
     * @param object The object.
     * @return The string representation of the object.
     */
    public String printObject(Object object){
        return object.toString();
    }

    /**
     * Puts together a message explaining why the trade request fails.
     * @return A message explaining why the trade request fails.
     */
    public String msgTradeRequestFail(){
        return "Trade request failed, please check the following conditions:\n\nFor one-way-trade:\n" +
                "1. The item is tradable. \n2. You have added the item to your wishlist.\n3. You have completed a two-way-trade before.\n\n" +
                "For two-way-trade:\n" + "1. The items are tradable.\n2. Both users have added the items to " +
                "their wishlist.\n3. The number of borrow did not exceed the number of lend.";
    }

    /**
     * Puts together a message that shows the rating and reviews that the user received
     * @param rating The rating of the user
     * @param reviews The reviews that this user received
     * @return A string representation for the rating and reviews
     */
    public String msgForRatingReview(double rating, ArrayList<Review> reviews){
        return "Your rating is: " + rating + ".\n\n" + "Here are reviews for you: \n" + printListObject(new ArrayList<>(reviews));
    }

    /**
     * Puts together a message that shows the reviews that the user received
     * @param reviews A list of reviews for an user
     * @return A string representation for the reviews
     */
    public String msgForReview(ArrayList<Review> reviews){
        return "Here are reviews for this user: \n" + printListObject(new ArrayList<>(reviews)) + "\n";
    }


}
