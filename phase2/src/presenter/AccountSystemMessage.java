package presenter;

import managers.feedbackmanager.Review;
import managers.messagemanger.Message;
import managers.usermanager.TradableUser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan, Shi Tang, Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
class AccountSystemMessage {

    /**
     * Constructs AccountSystemMessage.
     */
    AccountSystemMessage(){

    }

    /**
     * Puts together a message that indicates whether or not the friend requests are sent successfully.
     * @param validator Whether or not the friend requests are sent successfully.
     * @param userToID The user id of the person the user is trying to send the request to.
     * @return a messgae for friend requests
     */
    protected String msgForFriendRequest(boolean validator, int userToID){
        if (validator){
            return "Your friend request has been sent to user id " + userToID + " successfully.";
        }
        else {
            return "Failed to send friend request to " + userToID + ".\n" +
                    "It seems that you are trying to send friend request twice to " + userToID +
                    " or " + userToID + " is already in your list of friends.";
        }
    }

    /** get a string for the list of users
     * @param requests a map of friend requests
     * @return a string to describe the list of users
     */
    protected String printFriendRequest(HashMap<TradableUser, String> requests){
        StringBuilder string = new StringBuilder();
        string.append("Here is a list of friend requests:\n");
        int count = 1;
        for (HashMap.Entry<TradableUser, String> set: requests.entrySet()){
            TradableUser user = set.getKey();
            String msg = set.getValue();
            string.append("#").append(count).append(". Request from user ").append(user.getUsername()).append(" with id ").append(user.getId()).append("\n A message from this user: ").append(msg).append("\n");
        }
        return string.toString();
    }



    /**
     * Puts together a message that indicates the result of user's attempt to follow an item/user.
     * @param validator The result of user's attempt.
     * @return A message that indicates the result of user's attempt to follow an item/user.
     */
    protected String msgForFollowResult(boolean validator){
        if (!validator){
            return "Failed. It's probably because you're trying to follow yourself or follow the same user/item twice.";
        }
        return "Success!";
    }


    /**
     * Puts together a string containing a list of messages.
     * @param messages A list of messages.
     * @return All the messages, in strings.
     */
    protected String printAllMessages(ArrayList<Message> messages){
        StringBuilder out = new StringBuilder();
        for (Message msg: messages){
            out.append(msg.toString());
        }
        return out.toString();
    }

    /**
     * Puts together a message for setting tradable status.
     * @param validator Whether or not the tradable status is set successfully.
     * @param status The tradable status of the item.
     * @return A message for setting the tradable status.
     */
    protected String msgForSetTradable(boolean validator, boolean status){
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
     * Puts together a message that shows the rating and reviews that the user received
     * @param rating The rating of the user
     * @param reviews The reviews that this user received
     * @return A string representation for the rating and reviews
     */
    protected String msgForRatingReview(double rating, ArrayList<Review> reviews, PrintObjectMessage pom){
        return "Your rating is: " + rating + ".\n\n" + "Here are reviews for you: \n" + pom.printListObject(new ArrayList<>(reviews));
    }

    /**
     * Puts together a message that shows the reviews that the user received
     * @param reviews A list of reviews for an user
     * @return A string representation for the reviews
     */
    protected String msgForReview(ArrayList<Review> reviews, PrintObjectMessage pom){
        return "Here are reviews for this user: \n" + pom.printListObject(new ArrayList<>(reviews)) + "\n";
    }

}
