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
    //vacation related
    private String homeCity;
    private boolean onVacation;

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
}
