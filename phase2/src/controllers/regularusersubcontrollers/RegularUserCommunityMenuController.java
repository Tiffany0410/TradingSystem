package controllers.regularusersubcontrollers;

import controllers.maincontrollers.RegularUserController;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.messagemanger.Message;
import managers.messagemanger.MessageManager;
import managers.trademanager.TradeManager;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;

import java.util.ArrayList;

public class RegularUserCommunityMenuController {

    private SystemMessage sm;
    private RegularUserIDGetter idGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private MessageManager messageManager;
    private String username;
    private int userId;

    /**
     * Constructs a RegularUserFriendMenuController
     * @param ds The presenter class used to print to screen.
     * @param um The current state of the UserManager.
     * @param am The current state of the ActionManager.
     * @param userId The user id of the regular user.
     */
    public RegularUserCommunityMenuController(DisplaySystem ds, TradeManager tm, MeetingManager mm, UserManager um,
                                              ItemManager im, ActionManager am, FeedbackManager fm,MessageManager messageManager,
                                              String username, int userId){
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.fm = fm;
        this.username = username;
        this.userId = userId;
        this.sm = new SystemMessage();
        this.messageManager = messageManager;
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, im, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
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
    public void reviewUser() {
        int userToReview = idGetter.getUserID("user you want to review");
        // Asks the user for an integer input x --> must satisfy 1 <= x <= 10
        int rating = otherInfoGetter.getNumRating();
        String reason = otherInfoGetter.getMessage("Enter the reason(s) why you gave this review");
        ds.printResult(fm.setReview(userToReview, userId, rating, reason));
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.1", userToReview, rating + " and reason: " + reason);
        am.addActionToAllActionsList(userId, "regularUser", "5.1", userToReview, rating + " and reason: " + reason);
    }

    /**
     * Gets user's input of the id of the user to be reported as well as a message explaining the reason
     * and uses them to update the report system.
     */
    public void reportUser() {
        int userToReport = idGetter.getUserID("user you want to report");
        String reason = otherInfoGetter.getMessage("Enter the reason(s) why you report this user");
        ds.printResult(fm.updateReport(userToReport, userId, reason));
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.2", userToReport, reason);
        am.addActionToAllActionsList(userId, "regularUser", "5.2", userToReport, reason);
    }

    /**
     * Gets user's input of the id of the user to be searched for the rating for
     * and uses it to find the rating, which is printed by the printer.
     */
    public void findRatingForUser() {
        int user = idGetter.getUserID("user who you want to find out what his/her rating is");
        ds.printOut("The rating of this user is:" + fm.calculateRate(user));
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.3", user, "");
        am.addActionToAllActionsList(userId, "regularUser", "5.3", user, "");

    }

    /**
     * Lets the presenter print a list of users in the same
     * city as this user.
     * @param asGuest The determiner of access to this menu option.
     */
    public void seeUsersInSameHC(boolean asGuest) {
        if (!asGuest) {
            // print all users in the same city as this user.
            ds.printResult(new ArrayList<>(um.sameCity(userId)));
            am.addActionToAllActionsList(userId, "regularUser", "5.4", 0, "");
        }
        else{
            sm.msgForGuest(ds);
        }
    }


    /**
     * @return a list of friends for this user
     */
    public ArrayList<TradableUser> getFriends(){
        return  um.getFriends(userId);
    }

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
                am.addActionToCurrentRevocableList(userId, "regularUser", "5.6", userToId, "");
                am.addActionToAllActionsList(userId, "regularUser", "5.6", userToId, "");
            }
            return requestOrNot;
    }

    /** check if there has friend request for the user
     * @return true if there is no friend request
     */
    public boolean checkEmptyFR(){
        return getFriendRequest().isEmpty();
    }

    /** get a list of user who send a friend request to the user.
     * @return a list of user who send a friend request to the user.
     */
    public ArrayList<TradableUser> getFriendRequest(){
        ArrayList<String[]> requestFriends = um.friendsRequesting(userId);
        ArrayList<TradableUser> friends = new ArrayList<>();
        for(String[] strings: requestFriends){
            String friendName = strings[1];
            friends.add(um.findUser(friendName));
        }return friends;
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
        am.addActionToCurrentRevocableList(userId, "regularUser", "5.7", id1, "");
        am.addActionToAllActionsList(userId, "regularUser", "5.7", id1, "");
        return true;
    }return false;}

    /** delete a friend request
     * @param id1 the id of user
     * @return true if the friend request is deleted
     */
    public boolean deleteFriendRequest(int id1){
        String userTo = um.idToUsername(userId);
        String userFrom = um.idToUsername(id1);
        String[] strings = new String[2];
        strings[0] = userTo;
        strings[1] = userFrom;
        if(um.getListFriendRequest().contains(strings)){
        um.getListFriendRequest().remove(strings);
        return true;}return false;
    }

    public boolean unfriendUser(String username){
        int userId = um.usernameToID(username);
        boolean unfriendOrNot = um.removeFriend(this.userId, userId);
        if (unfriendOrNot){
            am.addActionToAllActionsList(this.userId, "regularUser", "5.8", userId, username);
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
            return true;
        }return false;
    }

    /** get a list of messages for this user
     * @return a list of messages that this user receive
     */
    public ArrayList<Message> getAllMessages(){
        return messageManager.getMessageFor(userId);
    }


}
