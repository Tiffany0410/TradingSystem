package managers.usermanager;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represents a regular user in our system.
 *
 * @author Hao Du, Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class TradableUser extends User implements Serializable, PropertyChangeListener {

    //basic info
    private ArrayList<Integer> wishList;
    private ArrayList<Integer> inventory;
    //status related
    private int numLent;
    private int numBorrowed;
    private Boolean isFrozen;
    // stores the number of times the user's been frozen.
    private int numFrozen;
    private int transactionsLeftForTheWeek = 3;
    //friends related
    private ArrayList<Integer> friend;
    //Vacation related
    private String homeCity;
    private boolean onVacation;
    //Follow
    private ArrayList<Integer> followers;
    private ArrayList<Integer> userFollowed;
    private ArrayList<Integer> itemFollowed;
    private ArrayList<String> userFollowingLogs;
    private ArrayList<String> itemFollowingLogs;

    /**
     * Construct an User.
     *
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public TradableUser(String username, String password, String email, Integer userID) {
        super(username, password, email, userID);
        wishList = new ArrayList<>();
        inventory = new ArrayList<>();
        numLent = 0;
        numBorrowed = 0;
        isFrozen = false;
        onVacation = false;
        friend = new ArrayList<>();
        followers = new ArrayList<>();
        userFollowed = new ArrayList<>();
        itemFollowed = new ArrayList<>();
        userFollowingLogs = new ArrayList<>();
        itemFollowingLogs = new ArrayList<>();


    }

    /**
     * Sets user's wishlist to a new one.
     *
     * @param newWishList The new wishlist.
     */
    public void setWishList(ArrayList<Integer> newWishList) {
        wishList = newWishList;
    }


    /**
     * Get the user's wishList.
     *
     * @return wishList.
     */
    public ArrayList<Integer> getWishList() {
        return wishList;
    }

    /**
     * Sets user's inventory to a new one.
     *
     * @param newInventory The new inventory.
     */
    public void setInventory(ArrayList<Integer> newInventory) {
        inventory = newInventory;
    }

    /**
     * Get the user's inventory.
     *
     * @return inventory.
     */
    public ArrayList<Integer> getInventory() {
        return inventory;
    }


    /**
     * Get the user's id.
     *
     * @return id.
     */
    public int getId() {
        return super.getId();
    }

    /**
     * Get the number of items lent by the user.
     *
     * @return The number of items lent by the user.
     */
    public int getNumLent() {
        return numLent;
    }

    /**
     * Increment the number of items lent by the user by one.
     */
    protected void addOneToNumLent() {
        numLent++;
    }

    /**
     * Get the number of items borrowed by the user.
     *
     * @return The number of items borrowed by the user.
     */
    public int getNumBorrowed() {
        return numBorrowed;
    }

    /**
     * Increment the number of items borrowed by the user by one.
     */
    protected void addOneToNumBorrowed() {
        numBorrowed++;
    }

    /**
     * Get user's status (frozen or unfrozen).
     *
     * @return Whether this user is frozen.
     */
    public boolean getIfFrozen() {
        return isFrozen;
    }

    /**
     * Set user's status (to frozen or unfrozen)
     *
     * @param newStatus The new status to be assigned.
     */
    protected void setIfFrozen(boolean newStatus) {
        isFrozen = newStatus;
    }

    /**
     * Getter for the number of transactions left
     * for this week.
     *
     * @return the number of transactions left for
     * this week.
     */
    protected int getNumTransactionLeftForTheWeek() {
        return transactionsLeftForTheWeek;
    }

    /**
     * Setter for the number of transactions left
     * for this week
     *
     * @param newVal The new number of transactions
     *               left for the week.
     */
    protected void setTransactionLeftForTheWeek(int newVal) {
        transactionsLeftForTheWeek = newVal;
    }

    /**
     * Getter for the number of times the user
     * has been frozen.
     *
     * @return the number of times the user has
     * been frozen
     */
    protected int getNumFrozen() {
        return numFrozen;
    }

    /**
     * Increments the number of times
     * the user has been frozen by one.
     */
    protected void addOneToNumFrozen() {
        numFrozen++;
    }

    /**
     * Set the user's homeCity
     *
     * @param homeCity the user's homeCity
     */
    public void setHome(String homeCity) {
        this.homeCity = homeCity;
    }

    /**
     * Set the flag indicates whether the user is on vacation
     *
     * @param onVacation the flag indicates whether the user is on vacation
     */
    protected void setOnVacation(boolean onVacation) {
        this.onVacation = onVacation;
    }

    /**
     * Get the user's homeCity
     *
     * @return homeCity.
     */
    protected String getHome() {
        return homeCity;
    }

    /**
     * Get the flag indicates whether the user is on vacation
     *
     * @return OnVacation.
     */
    protected boolean getOnVacation() {
        return onVacation;
    }

    /**
     * Get the list of user's friends.
     *
     * @return friend A list of this user's friends.
     */
    protected ArrayList<Integer> getFriend() {
        return friend;
    }

    /**
     * Add a friend to friends list by id
     *
     * @param id the friend's id
     */
    protected void addToFriends(Integer id) {
        friend.add(id);
    }

    /**
     * Remove a friend from the friends list by id
     *
     * @param id the friend's id
     */
    protected void removeFromFriends(Integer id) {
        friend.remove(id);
    }


    /**
     * Get the list of user's Followers.
     *
     * @return A list of this user's Followers.
     */

    public ArrayList<Integer> getFollowers() {
        return followers;
    }

    /**
     * Add a follower to the user's  Followers list by id
     *
     * @param userId the followers's id
     */
    protected void addFollowers(Integer userId) {
        followers.add(userId);
    }

    /**
     * Remove a follower from the user's followers list by id
     *
     * @param userId the followers's id
     */
    protected void removeFollowers(Integer userId) {
        followers.remove(userId);
    }

    /**
     * Get the list of user's Followings.
     *
     * @return A list of users that this user Following.
     */

    public ArrayList<Integer> getUserFollowed() {
        return userFollowed;
    }

    /**
     * Add a user to the user's Followings list by id
     *
     * @param userId the Followings's id
     */
    protected void followUser(Integer userId) {
        userFollowed.add(userId);
    }

    /**
     * Remove a item from the user's Followings list by id
     *
     * @param userId the item id that this user want to unfollow
     */
    protected void unfollowUser(Integer userId) {
        userFollowed.remove(userId);
    }

    /**
     * Add a item to the user's Followings list by id
     *
     * @param itemId the Followings's id
     */
    protected void followItem(Integer itemId) {
        itemFollowed.add(itemId);
    }

    /**
     * Remove a item from the user's Followings list by id
     *
     * @param itemId the item id that this user want to unfollow
     */
    protected void unfollowItem(Integer itemId) {
        itemFollowed.remove(itemId);
    }


    /**
     * Override the to string to describe the user
     *
     * @return A string description of this user
     */
    @Override
    public String toString() {
        return "This user has" + " username: " + super.getUsername() + ", id: " + super.getId() + ", email: " + super.getEmail()
                + " ." + "\nHe/she has borrowed " + numBorrowed + " items and lent " + numLent + " items." + "" +
                "\nThe answer to whether he/she is frozen is " + isFrozen + ".\n";
    }

    /**
     * Get a list of item  that this user followed
     *
     * @return A list of item that this user followed
     */
    public ArrayList<Integer> getItemFollowed() {
        return itemFollowed;
    }
    /**
     * Get a list of user following log
     *
     * @return A list of string records user following log
     */
    public ArrayList<String> getUserFollowingLogs() {
        return userFollowingLogs;
    }
    /**
     * Get a list of item following log
     *
     * @return A list of string records item following log
     */
    public ArrayList<String> getItemFollowingLogs() {
        return itemFollowingLogs;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ((boolean)evt.getNewValue()) {
            itemFollowingLogs.add(evt.getPropertyName() +  " has changed to tradable." );
        } else {
            itemFollowingLogs.add(evt.getPropertyName() + " has changed to non-tradable." );
        }
    }

    public void setUserFollowingLogs(ArrayList<String> userFollowingLogs) {
        this.userFollowingLogs = userFollowingLogs;
    }
}