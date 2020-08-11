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
 * @author Yu Xin Yan, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class SystemMessage {
    private PrintObjectMessage pom;
    private UserAlertMessage uam;
    private AccountSystemMessage asm;
    private TradeSystemMessage tsm;
    private MeetingSystemMessage msm;
    private ThresholdSystemMessage thsm;

    /**
     * Constructs a SystemMessage instance.
     */
    public SystemMessage(){
        pom = new PrintObjectMessage();
        uam = new UserAlertMessage();
        asm = new AccountSystemMessage();
        tsm = new TradeSystemMessage();
        msm = new MeetingSystemMessage();
        thsm = new ThresholdSystemMessage();
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
        return msm.tryAgainMsgForWrongDatetime();
    }

    /**
     * Puts together the result of action with object type.
     * @param obj The list of objects need to be printed.
     * @return The message related to the result of the action.
     */
    public String printResult(ArrayList<Object> obj) {
        return pom.printResult(obj);
    }

    /**
     * Puts together the list of items in properly formatted strings.
     * @param obj The list of items.
     * @return The list of items in properly formatted strings.
     */
    public String printItemResult(ArrayList<Item> obj) {
        return pom.printItemResult(obj);
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
        return uam.regUserAlerts(um, tc, username, menuPartOfAlert, thresholdValues);
    }

    /**
     * Put together the message for when the option is locked for the user
     * due to the threshold.
     * @return The message described above.
     */
    public String lockMessageForThreshold(int maxNumTransactionAWeek) {
        return thsm.lockMessageForThreshold(maxNumTransactionAWeek);
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     */
    public String msgForThresholdValue(int currentVal)
    {
       return thsm.msgForThresholdValue(currentVal);
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
        return thsm.lockMessageForTPLimit();
    }

    /**
     * Puts together a message that indicates whether or not the friend requests are sent successfully.
      * @param validator Whether or not the friend requests are sent successfully.
     * @param userToID The user id of the person the user is trying to send the request to.
     * @return a messgae for friend requests
     */
    public String msgForFriendRequest(boolean validator, int userToID){
        return asm.msgForFriendRequest(validator, userToID);
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
        return pom.printListUser(listOfUser);
    }


    /** get a string for the list of users
     * @param requests a map of friend requests
     * @return a string to describe the list of users
     */
    public String printFriendRequest(HashMap<TradableUser, String> requests){
        return asm.printFriendRequest(requests);
    }


    /**
     * Puts together a string that contains all string representation
     * of the objects.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects.
     */
    public String printListObject(ArrayList<Object> objects){
        return pom.printListObject(objects);
    }

    /**
     * Puts together a string that contains all string representation
     * of the objects, numbered.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects, numbered.
     */
    public String printListNumberedObject(ArrayList<Object> objects){
        return pom.printListNumberedObject(objects);
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
        return msm.msgForTPcannotConfirm();
    }

    /**
     * Puts together a message telling the user that it's not their turn to edit.
     * @return A message telling the user that it's not their turn to edit.
     */
    public String msgForNotYourTurn(){
        return msm.msgForNotYourTurn();
    }

    /**
     * Puts together a message that indicates the result of user's attempt to follow an item/user.
     * @param validator The result of user's attempt.
     * @return A message that indicates the result of user's attempt to follow an item/user.
     */
    public String msgForFollowResult(boolean validator){
        return asm.msgForFollowResult(validator);
    }

    /**
     * Puts together a message that indicates reasons why there is no trade suggestion.
     * @return A message that indicates reasons why there is no trade suggestion.
     */
    public String msgForNoTradeSuggestion(){
        return tsm.msgForNoTradeSuggestion();
    }

    /**
     * Puts together a message that indicates the result of user's attempt to confirm a meeting took place.
     * @param validator Whether or not the attempt is successful.
     * @return A message that indicates the result of user's attempt to confirm a meeting took place.
     */
    public String msgForMeetingTookPlaceResult(boolean validator){
        return msm.msgForMeetingTookPlaceResult(validator);
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
        return asm.printAllMessages(messages);
    }

    /**
     * Puts together a message indicating whether the trade is complete or not.
     * @param result Whether or not the trade is completed.
     * @return A message indicating whether the trade is complete or not.
     */
    public String msgFortradeCompletedOrNot(boolean result){
        return tsm.msgFortradeCompletedOrNot(result);
    }


    /**
     * Puts together a message for setting tradable status.
     * @param validator Whether or not the tradable status is set successfully.
     * @param status The tradable status of the item.
     * @return A message for setting the tradable status.
     */
    public String msgForSetTradable(boolean validator, boolean status){
        return asm.msgForSetTradable(validator, status);
    }

    /**
     * Puts together all the historical actions.
     * @param listOfAction The list of historical actions.
     * @return The string representation of all the historical actions in the list.
     */
    public String printHistoricalAction(ArrayList<Action> listOfAction) {
        return pom.printHistoricalAction(listOfAction);
    }

    /**
     * Puts together a string representation of an object.
     * @param object The object.
     * @return The string representation of the object.
     */
    public String printObject(Object object){
        return pom.printObject(object);
    }

    /**
     * Puts together a message explaining why the trade request fails.
     * @return A message explaining why the trade request fails.
     */
    public String msgTradeRequestFail(){
        return tsm.msgTradeRequestFail();
    }

    /**
     * Puts together a message that shows the rating and reviews that the user received
     * @param rating The rating of the user
     * @param reviews The reviews that this user received
     * @return A string representation for the rating and reviews
     */
    public String msgForRatingReview(double rating, ArrayList<Review> reviews){
        return asm.msgForRatingReview(rating, reviews, pom);
    }

    /**
     * Puts together a message that shows the reviews that the user received
     * @param reviews A list of reviews for an user
     * @return A string representation for the reviews
     */
    public String msgForReview(ArrayList<Review> reviews){
        return asm.msgForReview(reviews, pom);
    }


}
