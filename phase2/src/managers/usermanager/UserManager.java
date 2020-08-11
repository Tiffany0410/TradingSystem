package managers.usermanager;

import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores all the Users and AdminUsers. Manages the Users and the actions that they can make.
 * @author Gabriel
 * @version IntelliJ IDEA 2020.1
 */
public class UserManager implements Serializable {

    private ArrayList<TradableUser> listTradableUser;
    private ArrayList<User> listAdmin;
    private UserCommunityManager uCommunityM;
    private UserInfoManager uInfoM;
    private UserItemManager uItemM;
    private UserThresholdManager uThresholdM;

    /**
     * Constructs a UserManager with no Users or AdminUsers
     */
    public UserManager(){
        this.listTradableUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.uCommunityM = new UserCommunityManager();
        this.uInfoM = new UserInfoManager();
        this.uItemM = new UserItemManager();
        this.uThresholdM = new UserThresholdManager();
    }

    /**
     * Constructs a UserManager with the given Users and AdminUsers
     * @param tradableUsers The initial Users
     * @param admins The initial AdminUsers
     */
    public UserManager(ArrayList<TradableUser> tradableUsers, ArrayList<User> admins){
        this.listTradableUser = tradableUsers;
        this.listAdmin = admins;
        this.uCommunityM = new UserCommunityManager();
        this.uInfoM = new UserInfoManager();
        this.uItemM = new UserItemManager();
        this.uThresholdM = new UserThresholdManager();
    }

    /**
     * Gets the list of User
     * @return List of User
     */
    public ArrayList<TradableUser> getListTradableUser() {
        return listTradableUser;
    }

    /**
     * Get the list of usernames and messages of User that request to be unfrozen
     * @return The list of usernames and messages
     */
    public ArrayList<String[]> getListUnfreezeRequest() {
        return uThresholdM.getListUnfreezeRequest();
    }


    /**
     * Freezes a User
     * @param username The username of the the User that is being frozen
     * @return true if the User was frozen, false otherwise
     */
    public boolean freezeUser(String username){
        TradableUser person = findUser(username);
        return uThresholdM.freezeUser(person);
    }

    /**
     * Unfreezes a User
     * @param username The username of the User that is being unfrozen
     * @return true if the User was unfrozen, false otherwise
     */
    public boolean unfreezeUser(String username){
        TradableUser person = findUser(username);
        return uThresholdM.unfreezeUser(username, person);
    }

    /**
     * Checks if the User exists
     * @param username The username of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String username){
        return uInfoM.checkUser(username, listTradableUser);
    }

    /**
     * Checks if the User exists
     * @param userID The ID of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(int userID){
        return uInfoM.checkUser(userID, listTradableUser);
    }

    /**
     * Creates a new User
     * @param username Username of the new User
     * @param password Password of the new User
     * @param email Email of the new AdminUser
     */
    public void addUser(String username, String password, String email, String home){
        int userID;
        if (listTradableUser.size() != 0) {userID = listTradableUser.size() + 1;}
        else {userID = 1;}

        TradableUser toAdd = new TradableUser(username, password, email, userID);
        toAdd.setHome(home);
        this.listTradableUser.add(toAdd);
    }

    /**
     * Creates a new AdminUser
     * @param username Username of the new AdminUser
     * @param password Password of the new AdminUser
     * @param email Email of the new AdminUser
     */
    public void addAdmin(String username, String password, String email){
        int adminID;
        if (listAdmin.size() != 0) {adminID = listAdmin.size() + 1;}
        else {adminID = 1;}
        User toAdd = new User(username, password, email, adminID);
        this.listAdmin.add(toAdd);
    }


    /**
     * Removes an Item from a User's wishlist
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their wishlist
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemWishlist(Integer itemID, String username){
        TradableUser person = findUser(username);
        return uItemM.removeItemWishlist(itemID, username, person, uInfoM, uCommunityM, listTradableUser, listAdmin);
    }

    /**
     * Removes an Item from a User's inventory
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their inventory
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemInventory(Integer itemID, String username){
        TradableUser person = findUser(username);
        return uItemM.removeItemInventory(itemID, username, person, uInfoM, uCommunityM, listTradableUser, listAdmin);
    }

    /**
     * Adds an Item to a User's wishlist
     * @param itemID The ID of the Item that is being added
     * @param username The username of the User to add the item into their wishlist
     * @return true if the item was added successfully, false otherwise
     */
    public boolean addItemWishlist(Integer itemID, String username){
        TradableUser person = findUser(username);
        return uItemM.addItemWishlist(itemID, username, person, uInfoM, uCommunityM, listTradableUser, listAdmin);
    }

    /**
     * Adds an Item to a User's inventory
     * @param itemID The ID of the item that is being added
     * @param username The username of the User to add the item into their inventory
     * @return true if the item was added successfully, false otherwise
     */
    public boolean addItemInventory(Integer itemID, String username){
        TradableUser person = findUser(username);
        return uItemM.addItemInventory(itemID, username, person, uInfoM, uCommunityM, listTradableUser, listAdmin);
    }

    /**
     * Gives all the usernames and passwords of all User
     * @return A map of usernames to passwords for all User
     */
    public HashMap<String, String> userPasswords(){
        return uInfoM.userPasswords(listTradableUser);
    }

    /**
     * Gives all the usernames and passwords of all AdminUser
     * @return A map of usernames to passwords for all AdminUser
     */
    public HashMap<String, String> adminPasswords(){
        return uInfoM.adminPasswords(listAdmin);
    }

    /**
     * Searches for a User
     * @param username The username of the User being searched for
     * @return The User that is being searched for
     */
    public TradableUser findUser(String username){
        return uInfoM.findUser(username, listTradableUser);
    }

    /**
     * Searches for a User
     * @param ID The ID of the User being searched for
     * @return The User that is being searched for
     */
    public TradableUser findUser(int ID){
        return uInfoM.findUser(ID, listTradableUser);
    }

    /**
     * Gives the username for the User with the given ID
     * @param ID The ID of the User
     * @return The username of the User
     */
    public String idToUsername(int ID){
        return uInfoM.idToUsername(ID, listTradableUser, listAdmin);
    }

    /**
     * Gives the ID for the User with the given username
     * @param username The username of the User
     * @return The ID of the User
     */
    public int usernameToID(String username){
        return uInfoM.usernameToID(username, listTradableUser, listAdmin);
    }

    /**
     * Sends a request to unfreeze a User
     * @param username The username of the User
     * @param message The message of the User to unfreeze
     * @return true if the request was successful, false otherwise
     */
    public boolean requestUnfreeze(String username, String message){
        TradableUser person = findUser(username);
        return uThresholdM.requestUnfreeze(username, message, person);
    }

    /**
     * Remove item with itemId from the user with userId1 and
     * the user with userId2 appropriately.
     * @param userId1 The first user.
     * @param userId2 The second user.
     * @param itemId The id of the item to be removed.
     */
    public void removeItemFromUsers(int userId1, int userId2, int itemId) {
        TradableUser tradableUser1 = findUser(userId1);
        TradableUser tradableUser2 = findUser(userId2);
        uItemM.removeItemFromUsers(itemId, tradableUser1, tradableUser2, uInfoM, uCommunityM, listTradableUser, listAdmin);
    }

    /**
     * Returns the frozen state of the User
     * @param username The username of the User
     * @return true if the User is frozen, false if the User is not
     */
    public boolean getFrozenStatus(String username) {
        TradableUser person = findUser(username);
        return uThresholdM.getFrozenStatus(person);
    }

    /**
     * Returns the frozen state of the User
     * @param userID The ID of the User
     * @return true if the User is frozen, false if the User is not
     */
    public boolean getFrozenStatus(int userID) {
        TradableUser person = findUser(userID);
        return uThresholdM.getFrozenStatus(person);
    }

    /**
     * Gives the inventory of the User
     * @param userID The ID of the User
     * @return Returns a list of integers of the Id of Items in the User's inventory
     */
    public ArrayList<Integer> getUserInventory(int userID){
        TradableUser person = findUser(userID);
        return uItemM.getUserInventory(person);
    }

    /**
     * Gives the wishlist of the User
     * @param userID The ID of the User
     * @return Returns a list of integers of the ID of Items in the User's wishlist
     */
    public ArrayList<Integer> getUserWishlist(int userID){
        TradableUser person = findUser(userID);
        return uItemM.getUserWishlist(person);
    }

    /**
     * Changes thresholds according to what is requested
     * @param userID The ID of the User
     * @param threshold The threshold to be changed
     * @param change The new threshold if there is any
     */
    public void setThreshold (int userID, String threshold, int change){
        TradableUser person = findUser(userID);
        uThresholdM.setThreshold(threshold, change, person);
    }

    /**
     * Gives back the specified information of a User
     * @param userID The ID of the User
     * @param threshold The name of the information that is wanted
     * @return The information that is requested
     */
    public int getInfo(int userID, String threshold){
        TradableUser person = findUser(userID);
        return uThresholdM.getInfo(threshold, person);
    }

    /**
     * Gives back the specified information of a User
     * @param username The username of the User
     * @param threshold The name of the information that is wanted
     * @return The information that is requested
     */
    public int getInfo(String username, String threshold){
        int num = uInfoM.usernameToID(username, listTradableUser, listAdmin);
        return getInfo(num, threshold);
    }

    /**
     * Gives the list of friends the User is friends with
     * @param userID The ID of the User
     * @return A list of Users who are in the User's friend list
     */
    public ArrayList<TradableUser> getFriends(int userID){
        TradableUser person = findUser(userID);
        return uCommunityM.getFriends(person, uInfoM, listTradableUser);
    }

    /**
     * Adds a request to the list of friend requests
     * @param message The message to give to the recipient
     * @param userTo The username of the recipient
     * @param userFrom The username of the sender
     * @return true if the request was successful, false otherwise
     */
    public boolean requestFriend(String message, String userTo, String userFrom){
        if (checkUser(userTo) && checkUser(userFrom)) {
            return uCommunityM.requestFriend(message, userTo, userFrom);
        }
        return false;
    }

    /**
     * Adds the Users to each others' friend lists
     * @param user1 The username of one of the Users
     * @param user2 The username of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean addFriend(String user1, String user2){
        int id1 = usernameToID(user1);
        int id2 = usernameToID(user2);
        return addFriend(id1, id2);
    }

    /**
     * Adds the Users to each others' friend lists
     * @param user1 The ID of one of the Users
     * @param user2 The ID of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean addFriend(int user1, int user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        return uCommunityM.addFriend(user1, user2, person1, person2, listTradableUser, uInfoM, listAdmin);

    }

    /**
     * Removes the Users from each others friend list
     * @param user1 The username of one of the Users
     * @param user2 The username of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean removeFriend(String user1, String user2){
        if (!checkUser(user1) || ! checkUser(user2)){
            return false;
        }
        int id1 = usernameToID(user1);
        int id2 = usernameToID(user2);
        return removeFriend(id1, id2);
    }

    /**
     * Removes the Users from each others friend list
     * @param user1 The ID of one of the Users
     * @param user2 The ID of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean removeFriend(int user1, int user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        return uCommunityM.removeFriend(user1, user2, person1, person2);
    }

    /**
     * Lets a User change their status to go on vacation
     * @param userID The ID of the User
     * @return true if the change was performed, false otherwise
     */
    public boolean goOnVacation(int userID){
        TradableUser person = findUser(userID);
        return uThresholdM.goOnVacation(userID, person, uCommunityM, listTradableUser);
    }

    /**
     * Lets a User change their status to come back from vacation
     * @param userID The ID of the User
     * @return true if the change was performed, false otherwise
     */
    public boolean comeFromVacation(int userID){
        TradableUser person = findUser(userID);
        return uThresholdM.comeFromVacation(userID, person, uCommunityM, listTradableUser);
    }

    /**
     * Gives a list of Users that are in the same city as the given User
     * @param userID The ID of the User
     * @return Returns a list of Users who have the same home city as the given User
     */
    public ArrayList<TradableUser> sameCity (int userID){
        TradableUser homosapien = findUser(userID);
        return uCommunityM.sameCity(homosapien, listTradableUser);
    }

    /**
     * Checks to see if the User has anything the other User wants
     * @param wantUser The User to check their wants
     * @param haveUser The User to check what they have
     * @return A list of integers of Item IDs that the User wants and the other has
     */
    public ArrayList<Integer> wantedItems (int wantUser, int haveUser){
        TradableUser person1 = findUser(wantUser);
        TradableUser person2 = findUser(haveUser);
        return uItemM.wantedItems(person1, person2);
    }

    /**
     * Gives the home city of the User
     * @param userID The ID of the User
     * @return The home city of the User
     */
    public String getHome(int userID){
        return uInfoM.getHome(userID, listTradableUser);
    }

    /**
     * Changes the home city of a User
     * @param userID The ID of the User
     * @param newHome The new city to change to
     */
    public void changeHome(int userID, String newHome){
        uInfoM.changeHome(userID, newHome, listTradableUser);
    }

    /**
     * Lets a User follow another User
     * @param userID The ID of the User
     * @param toFollow The ID of the User to follow
     * @return true if the User was successfully followed, false otherwise
     */
    public boolean userFollow(int userID, int toFollow){
        TradableUser person = findUser(userID);
        TradableUser following = findUser(toFollow);
        return uCommunityM.userFollow(userID, toFollow, person, following, listTradableUser);
    }

    /**
     * Lets a User unfollow another User
     * @param userID The ID of the User
     * @param toUnfollow The ID of the User to unfollow
     * @return true if the User was successfully unfollowed, false otherwise
     */
    public boolean userUnfollow(int userID, int toUnfollow){
        TradableUser person = findUser(userID);
        TradableUser following = findUser(toUnfollow);
        return uCommunityM.userUnfollow(userID, toUnfollow, person, following, listTradableUser);
    }

    /**
     * Lets a User follow an Item
     * @param userID The ID of the User
     * @param toFollow The ID of the Item to follow
     * @return true if the Item was followed successfully, false otherwise
     */
    public boolean itemFollow(int userID, Item toFollow){
        TradableUser person = findUser(userID);
        return uCommunityM.itemFollow(userID, toFollow, person, listTradableUser);
    }


    /**
     * Lets a User unfollow an Item
     * @param userID The ID of the User
     * @param toUnfollow The ID of the Item to unfollow
     * @return true if the Item was unfollowed successfully, false otherwise
     */
    public boolean itemUnfollow(int userID, Item toUnfollow){
        TradableUser person = findUser(userID);
        return uCommunityM.itemFollow(userID, toUnfollow, person, listTradableUser);
    }

    /**
     * Gives all the Users and the Items they follow
     * @return A map of User IDs as keys and a list of the Item IDs they follow as values
     */
    public HashMap<Integer, ArrayList<Integer>> itemsFollowed(){
        return uCommunityM.itemsFollowed(listTradableUser);
    }

    /**
     * Gives the requests of all the Users requesting to be friends
     * @param userID The ID of the User
     * @return A list of all the friend requests requested of the User
     */
    public ArrayList<String[]> friendsRequesting(int userID){
        String username = idToUsername(userID);
        return uCommunityM.friendsRequesting(username);
    }

    /**
     * Gives the UserFollowingLogs of the User
     * @param userID The ID of the User
     * @return The UserFollowingLogs of the User
     */
    public ArrayList<String> getUserFollowingLogs (int userID){
        TradableUser person = findUser(userID);
        return uCommunityM.getUserFollowingLogs(person);
    }

    /**
     * Gives the ItemFollowingLogs the User
     * @param userID The ID of the User
     * @return The ItemFollowingLogs of the User
     */
    public ArrayList<String> getItemFollowingLogs (int userID){
        TradableUser person = findUser(userID);
        return uCommunityM.getItemFollowingLogs(person);
    }

    /**
     * Sorts the Users by rating
     * @return A list of Users sorted by rating in descending order
     */
    public ArrayList<TradableUser> sortRating (FeedbackManager fm){
        return uCommunityM.sortRating(fm, listTradableUser);
    }

    /**
     * @param userID The user's ID
     * @return a list of users which are not friend with this user and not in this user's friend requests
     */
    public ArrayList<TradableUser> getUsersNotFriends(int userID){
        String username = idToUsername(userID);
        return uCommunityM.getUsersNotFriends(userID, uInfoM , username, listTradableUser, listAdmin);
    }

}
