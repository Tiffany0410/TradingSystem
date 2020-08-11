package presenter;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
class ThresholdSystemMessage {

    /**
     * Constructs ThresholdSystemMessage.
     */
    ThresholdSystemMessage(){

    }

    /**
     * Put together the message for when the option is locked for the user
     * due to the threshold.
     * @return The message described above.
     */
    protected String lockMessageForThreshold(int maxNumTransactionAWeek) {
        return "This option is locked \n" +
                "You have reached the " + maxNumTransactionAWeek + " transactions a week limit" +
                "\n";
    }

    /**
     * Prints the current threshold value.
     * @param currentVal The current threshold value.
     */
    protected String msgForThresholdValue(int currentVal)
    {
        return "The current threshold value is " + currentVal;
    }

    /**
     * Puts together a message telling the user he/she can't edit anymore because of a threshold.
     * @return A message telling the user he/she can't edit anymore because of a threshold.
     */
    protected String lockMessageForTPLimit() {
        return "You can't edit any more because the time and place edits limit is reached. This trade has been cancelled.";}


}
