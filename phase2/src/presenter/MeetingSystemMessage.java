package presenter;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
class MeetingSystemMessage {


    /**
     * Constructs MeetingSystemMessage.
     */
    MeetingSystemMessage(){

    }

    /**
     * Puts together the message for invalid date and time input.
     * @return The message for invalid date and time input.
     */
    protected String tryAgainMsgForWrongDatetime(){
        return "Invalid input: the year must be between 2020 and 2024, inclusive." +
                " Also, you cannot set the date to be earlier than today or" +
                " today.";
    }

    /**
     * Puts together a message telling the user why the time and place cannot be confirmed.
     * @return A message telling the user why the time and place cannot be confirmed.
     */
    protected String msgForTPcannotConfirm(){
        return "You can't confirm right now because no time/place has been suggested or you have just suggested time/place your" +
                " self. In the second case, the system automatically sets your status as confirmed.";
    }

    /**
     * Puts together a message telling the user that it's not their turn to edit.
     * @return A message telling the user that it's not their turn to edit.
     */
    protected String msgForNotYourTurn(){
        return "It's not your turn to edit.";
    }

    /**
     * Puts together a message that indicates the result of user's attempt to confirm a meeting took place.
     * @param validator Whether or not the attempt is successful.
     * @return A message that indicates the result of user's attempt to confirm a meeting took place.
     */
    protected String msgForMeetingTookPlaceResult(boolean validator){
        if (!validator){
            return "Failed. It's probably because you have already confirmed it or the meeting time hasn't arrived.";
        }
        return "Success!";
    }
}

