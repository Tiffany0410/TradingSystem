package controllers.regularusersubcontrollers;

import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.feedbackmanager.Review;
import managers.messagemanger.Message;
import managers.messagemanger.MessageManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An instance of this class represents the community menu controller for regular user.
 *
 * @author Shi Tang, Jianhong Guo, Chengle Yang
 * @version IntelliJ IDEA 2020.1
 *
 */
public class RegularUserCommunityMenuController {

    private UserManager um;
    private ActionManager am;
    private FeedbackManager fm;
    private MessageManager messageManager;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserFriendMenuController
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param fm FeedbackManager
     * @param messageManager MessageManager
     * @param username Username
     * */
    public RegularUserCommunityMenuController(UserManager um, ActionManager am, FeedbackManager fm,
                                              MessageManager messageManager, String username){
        this.um = um;
        this.am = am;
        this.fm = fm;
        this.username = username;
        this.userId = um.usernameToID(this.username);
        this.messageManager = messageManager;
    }

    /** getter for user id
     * @return the user id
     */
    public int getUserId(){
        return userId;
    }

    /**
     * Gets user's input of the id of the user to be reviewed, the rating to give,
     * as well as a message explaining the reason and uses them to update the review
     * system.
     */
    public boolean reviewUser(int userToReview, int rating, String reason) {
        boolean result = fm.setReview(userToReview, userId, rating, reason);
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.1", userToReview,
                rating + " and reason: " + reason);
        am.addActionToAllActionsList(userId, "regularUser", "5.1", userToReview,
                rating + " and reason: " + reason);
        return result;
    }

    /**
     * Gets user's input of the id of the user to be reported as well as a message explaining the reason
     * and uses them to update the report system.
     */
    public boolean reportUser(int userToReport, String reason) {
        if (um.checkUser(userToReport)){
        boolean result = fm.updateReport(userToReport, userId, reason);
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.2", userToReport, reason);
        am.addActionToAllActionsList(userId, "regularUser", "5.2", userToReport, reason);
        return result;
        }
        return false;
    }

    /**
     * Gets user's input of the id of the user to be searched for the rating for
     * and uses it to find the rating, which is printed by the printer.
     */
    public double findRatingForUser(int id) {
        am.addActionToAllActionsList(userId, "regularUser", "5.3", id, "");
        return fm.calculateRate(id);
    }
   
    /**
     * @param userId the user id
     * @return a list of reviews for the id
     */
    public ArrayList<Review> getAllReviews(int userId){
        am.addActionToAllActionsList(userId, "regularUser", "5.11", 0, "");
        return fm.getReviewById(userId);
    }

    /**
     * @param id The id of the user
     * @return true if this user's id is in the system
     */
    public boolean checkUserId(int id){
        return um.checkUser(id);
    }

    /**
     * @return a list of tradable users that are in the same homecity with the user
     */
    public ArrayList<TradableUser> seeUsersInSameHC() {
        am.addActionToAllActionsList(userId, "regularUser", "5.4", 0, "");
        return new ArrayList<>(um.sameCity(userId));
    }

    /**
     * @return a list of friends for this user
     */
    public ArrayList<TradableUser> getFriends(){
        am.addActionToAllActionsList(userId, "regularUser", "5.5", 0, "");
        return um.getFriends(userId);
    }

    /**
     * @return a list of users that are not friend with this.user
     */
    public ArrayList<TradableUser> getNotFriends() { return um.getUsersNotFriends(userId);}

    /**
     * Receives user's request to send friend request to other user.
     * @param userToId The receiver's id
     * @param msg Message for receiver
     * @return true iff friend request sent successfully
     */
    public boolean sendFriendRequest(int userToId, String msg){
            String userFrom = um.idToUsername(userId);
            String userTo = um.idToUsername(userToId);
            boolean requestOrNot = um.requestFriend(msg, userTo,userFrom);
            if (requestOrNot){
                am.addActionToAllActionsList(userId, "regularUser", "5.6", userToId, "");
            }
            return requestOrNot;
    }

    /** get a list of user who send a friend request to the user.
     * @return a list of user who send a friend request to the user.
     */
    public HashMap<TradableUser, String> getFriendRequest(){
        ArrayList<String[]> requestFriends = um.friendsRequesting(userId);
        HashMap<TradableUser, String> out = new HashMap<>();
        for(String[] strings: requestFriends){
            out.put(um.findUser(strings[1]), strings[2]);
        }
        return out;
    }

    /** check if a user in the friend request or not.
     * @param userId1 the id of user
     * @return true if the id in the friend request.
     */
    public boolean checkIdInRequest(int userId1){
        ArrayList<String[]> requestFriends = um.friendsRequesting(userId);
        for(String[] strings: requestFriends){
            String friendName = strings[1];
            if(um.usernameToID(friendName)==userId1){
            return true;}
        }return false;
    }

    /** add user with id1 to be a friend
     * @param id1 the id of user
     * @return true if the user with id1 is added to be a friend
     */
    public boolean addFriend(int id1){
        String userTo = um.idToUsername(userId);
        String userFrom = um.idToUsername(id1);
        boolean yesOrNo = um.addFriend(userFrom, userTo);
        if(yesOrNo){
        am.addActionToAllActionsList(userId, "regularUser", "5.7", id1, userFrom);
        return true;
    }return false;}

    /**
     * Unfriend an user
     * @param id The user's id that this.user wants to unfriend
     * @return true if unfriend successfully, false otherwise
     */
    public boolean unfriendUser(int id){
        boolean unfriendOrNot = um.removeFriend(id,userId);
        if (unfriendOrNot){
            String targetUserName = um.idToUsername(id);
            am.addActionToCurrentRevocableList(this.userId, "regularUser", "5.8", id, targetUserName);
            am.addActionToAllActionsList(this.userId, "regularUser", "5.8", id, targetUserName);
        }
        return unfriendOrNot;
    }

    /** check if this user is a friend of the user with userId1
     * @param userId1 the user id
     * @return true if this user is a friend of the user with the userId1
     */
    public boolean checkIsFriend(int userId1){
        ArrayList<TradableUser> friends = getFriends();
        TradableUser user = um.findUser(userId1);
        return friends.contains(user);
    }

    /** send a message to the receiver
     * @param receiverId the id of the receiver
     * @param message the message
     * @return true if the message is send to the receiver
     */
    public boolean sendMessage(int receiverId, String message){
        if (checkIsFriend(receiverId)){
            messageManager.createNewMessage(userId,receiverId,message);
            am.addActionToAllActionsList(userId, "regularUser", "5.9", receiverId, message);
            return true;
        }return false;
    }

    /** get a list of messages for this user
     * @return a list of messages that this user receive
     */
    public ArrayList<Message> getAllMessages(){
        am.addActionToAllActionsList(userId,"regularUser", "5.10", 0, "");
        return messageManager.getMessageFor(userId);
    }

}
