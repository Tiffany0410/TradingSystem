package presenter;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
class GeneralSystemMessage {

    /**
     * Constructs GeneralSystemMessage.
     */
    GeneralSystemMessage(){

    }

    /**
     * Puts together the message for when there is nothing to be shown.
     * @return The message for when there is nothing to be shown.
     */
    protected String msgForNothing(){
        return "There's nothing here.";
    }

    /**
     * Puts together the message for invalid input.
     * @return The message for invalid input.
     */
    protected String invalidInput() {
        return "Invalid put in, please type again.";
    }

    /**
     * Puts together the message for invalid number input.
     * @return The message for invalid number input.
     */
    protected String invalidNumber(){
        return "Invalid put in, please type a number.";
    }


    /**
     * Prints the message for the guest that tries to access options
     * that are meant for users.
     */
    protected String msgForGuest(){return "Please login, guest account can't do this."; }

    /**
     * Prints the message for the user that tries to
     * access menus that can't access because of their
     * frozen status.
     */
    protected String lockMessageForFrozen(){
        return "This menu is locked because you're frozen.";
    }


    /**
     * Put together the message for the user who tries
     * to access menus that he/she can't access because
     * their status is on-vacation.
     * @return The message described above.
     */
    protected String lockMessageForVacation() {
        return"Because you're on vacation, you can't be involved in trade/meeting. \n " +
                "If you're back from vacation, please change your on-vacation status in the Account Menu.";
    }

    /**
     * Puts together a message that indicates the result.
     * @param validator The result.
     * @return The message that indicates the result.
     */
    protected String msgForResult(boolean validator){
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
    protected String msgForStatusChangeResult(boolean validator){
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
    protected String msgForRequestProcess(boolean validator){
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
    protected String msgForRequestResult(boolean validator){
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
    protected String failMessageForFrozen(){
        return "You're frozen because you borrowed more than you lend";
    }

    /** Puts together a string that describes there's nothing inside something.
     * @param string A string to describe the type of "something".
     * @return A string to describe there's nothing inside something.
     */
    protected String msgForNothing(String string){
        return "There is nothing in " + string + " .";
    }

    /**
     * Puts together a message telling the user to try again because of a wrong input.
     * @return A message telling the user to try again because of a wrong input.
     */
    protected String tryAgainMsgForWrongInput(){
        return "Please try again, one or more input(s) are invalid.";
    }


    /**
     * Puts together a message telling the user why the time and place cannot be confirmed.
     * @return A message telling the user why the time and place cannot be confirmed.
     */
    protected String tryAgainMsgForWrongFormatInput(){
        return "Please try again. One or more of your input(s) were in the incorrect format (ex. we ask for int and you entered a word)." +
                "Also, don't add 0 before a number (ex. we accept 5 but don't accept 05). ";
    }

    /**
     * Puts together a message made up of "there is no" part and the input string.
     * @param string The part of the string to be displayed after the "there is no" part.
     * @return The message made up of "there is no" part and the input string.
     */
    protected String msgForNo(String string){
        return "There is no " + string + " .";
    }

}
