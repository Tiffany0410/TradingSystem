package managers.usermanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represents an admin user in this system.
 *
 * @author Hao Du
 * @version IntelliJ IDEA 2020.1
 */
public class User implements Serializable {

    private String username;
    private String password;
    private String email;
    private int id;
    //friends related
    private ArrayList<Integer> friend;
    private ArrayList<String[]> friendRequests;
    //Vacation related
    private String homeCity;
    private boolean onVacation;
    //Follow
    private ArrayList<Integer> followers;
    private ArrayList<Integer> userFollowed;
    private ArrayList<Integer> itemFollowed;

    /**
     * Construct an User.
     * 
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public User(String username, String password, String email, Integer adminID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = adminID;
        this.onVacation = false;
        friend = new ArrayList<>();
        friendRequests = new ArrayList<>();
        followers = new ArrayList<>();
        userFollowed = new ArrayList<>();
        itemFollowed = new ArrayList<>();

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
    public void setOnVacation(boolean onVacation) {
        this.onVacation = onVacation;
    }
    /**
     * Get the user's homeCity
     *
     * @return homeCity.
     */
    public String getHome() {
        return homeCity;
    }
    /**
     * Get the flag indicates whether the user is on vacation
     *
     * @return OnVacation.
     */
    public boolean getOnVacation() {
        return onVacation;
    }

    /**
     * Get the user's username.
     * 
     * @return user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the user's id.
     * 
     * @return user's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for user's password
     * @return this user's password
     */
    public String getPassword() {return password;}
    /**
     * Get the list of user's friends.
     * @return friend A list of this user's friends.
     */
    public ArrayList<Integer> getFriend() {
        return friend;
    }

    /**
     * Add a friend to friends list by id
     * @param id the friend's id
     */
    public void addToFriends(Integer id) {
        friend.add(id);
    }
    /**
     * Remove a friend from the friends list by id
     * @param id the friend's id
     */
    public void removeFromFriends(Integer id) {
        friend.remove(id);
    }


    /**
     * Get the list of user's Followers.
     * @return A list of this user's Followers.
     */

    public ArrayList<Integer> getFollowers() {
        return followers;
    }
    /**
     * Add a follower to the user's  Followers list by id
     * @param userId the followers's id
     */
    public void addFollowers(Integer userId) {
        followers.add(id);
    }
    /**
     * Remove a follower from the user's followers list by id
     * @param userId the followers's id
     */
    public void removeFollowers(Integer userId) {
        followers.remove(userId);
    }

    /**
     * Get the list of user's Followings.
     * @return A list of users that this user Following.
     */

    public ArrayList<Integer> getUserFollowed() {
        return userFollowed;
    }

    /**
     * Add a user to the user's Followings list by id
     * @param userId the Followings's id
     */
    public void followUser(Integer userId) {
        userFollowed.add(userId);
    }
    /**
     * Remove a item from the user's Followings list by id
     * @param userId the item id that this user want to unfollow
     */
    public void unfollowUser(Integer userId) {
        userFollowed.remove(userId);
    }

    /**
     * Add a item to the user's Followings list by id
     * @param itemId the Followings's id
     */
    public void followItem(Integer itemId) {
        itemFollowed.add(itemId);
    }
    /**
     * Remove a item from the user's Followings list by id
     * @param itemId the item id that this user want to unfollow
     */
    public void unfollowItem(Integer itemId) {
        itemFollowed.remove(itemId);
    }
}
