package managers.usermanager;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * An instance of this class represents a regular user in our system.
 *
 * @author Hao Du, Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class User implements Serializable {

    //basic info
    private String username;
    private String password;
    private String email;
    private ArrayList<Integer> wishList;
    private ArrayList<Integer> inventory;
    private int id;
    //status related
    private int numLent;
    private int numBorrowed;
    private Boolean isFrozen;
    // stores the number of times the user's been frozen.
    private int numFrozen;
    private int transactionsLeftForTheWeek = 3;

    /**
     * Construct an User.
     *
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public User(String username, String password, String email, Integer userID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = userID;
        wishList = new ArrayList<>();
        inventory = new ArrayList<>();
        numLent = 0;
        numBorrowed = 0;
        isFrozen = false;
    }

    /**
     * Getter for this user's password.
     * @return This user's password.
     */
    public String getPassword() { return password;}

    /**
     * Get the user's email.
     *
     * @return the email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the user's username.
     *
     * @return username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets user's wishlist to a new one.
     * @param newWishList The new wishlist.
     */
    public void setWishList(ArrayList<Integer> newWishList){
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
     * @param newInventory The new inventory.
     */
    public void setInventory(ArrayList<Integer> newInventory){
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
        return this.id;
    }

    /**
     * Get the number of items lent by the user.
     *
     * @return The number of items lent by the user.
     */
    public int getNumLent(){
        return numLent;
    }

    /**
     * Increment the number of items lent by the user by one.
     */
    public void addOneToNumLent(){
        numLent ++;
    }

    /**
     * Get the number of items borrowed by the user.
     *
     * @return The number of items borrowed by the user.
     */
    public int getNumBorrowed(){
        return numBorrowed;
    }

    /**
     * Increment the number of items borrowed by the user by one.
     */
    public void addOneToNumBorrowed(){
        numBorrowed ++;
    }

    /**
     * Get user's status (frozen or unfrozen).
     * @return Whether this user is frozen.
     */
    public boolean getIfFrozen(){
        return isFrozen;
    }

    /**
     * Set user's status (to frozen or unfrozen)
     * @param newStatus The new status to be assigned.
     */
    public void setIfFrozen(boolean newStatus){
        isFrozen = newStatus;
    }

    /**
     * Getter for the number of transactions left
     * for this week.
     * @return the number of transactions left for
     * this week.
     */
    public int getNumTransactionLeftForTheWeek(){
        return transactionsLeftForTheWeek;
    }

    /**
     * Setter for the number of transactions left
     * for this week
     * @param newVal The new number of transactions
     *               left for the week.
     */
    public void setTransactionLeftForTheWeek(int newVal){
        transactionsLeftForTheWeek = newVal;
    }

    /**
     * Getter for the number of times the user
     * has been frozen.
     * @return the number of times the user has
     * been frozen
     */
    public int getNumFrozen(){
        return numFrozen;
    }

    /**
     * Increments the number of times
     * the user has been frozen by one.
     */
    public void addOneToNumFrozen(){
        numFrozen ++;
    }

    /** Override the to string to describe the user
     * @return A string description of this user
     */
    @Override
    public String toString(){
        return "This user has" + "username: " + this.username + ", id: " + this.id + ", email: " + this.email
                + " ." + "He/she has borrowed " + numBorrowed + " items and lent " + numLent + " items." + "" +
                "The answer to whether he/she is frozen is " + isFrozen;
    }

}