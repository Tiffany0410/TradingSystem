package bookTradeSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    /**
     * username is the user's username. password is the user's password. email is
     * the user's email.
     */
    private String username;
    private String password;
    private String email;
    private ArrayList<Integer> wishList;
    private ArrayList<Item> inventory;
    private int id;
    protected int numLent;      // Maybe not needed -> can loop over trade to get numLent
    protected int numBorrowed;      // Maybe not needed
    int maxThreshold;
    protected Boolean isFrozen;
    protected Boolean isAdmin;          // Maybe not needed
    private ArrayList<Integer> topThreePartners;    // Maybe not needed -> TradeManager has tradeHistory method
    private ArrayList<Integer> mostRecentThreeTrade;    // Maybe not needed
    private int numTransaction;     // Maybe not needed
    private int numUncompletedTransaction;      // Maybe not needed
    private static int idNumber = 1;

    /**
     * Construct an User.
     * 
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        id = idNumber;
        idNumber ++;
    }
    /**
     * Set the user's password.
     *
     * @param password the user's password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set the user's email.
     * 
     * @param email the user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set the user's username.
     * 
     * @param name the user's username.
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Set the user's wishList.
     *
     * @param wishList the user's wishList.
     */
    public void setWishList(ArrayList<Integer> wishList) {
        this.wishList = wishList;
    }

    /**
     * Set the user's inventory.
     *
     * @param inventory the user's inventory.
     */
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }

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
     * Get the user's id.
     * 
     * @return id.
     */
    public int getId() {
        return this.id;
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
     * Get the user's inventory.
     * 
     * @return inventory.
     */
    public ArrayList<Item> getInventory() {
        return inventory;
    }

    /**
     * Get the user's topThreePartners.
     * 
     * @return topThreePartners.
     */
    public ArrayList<Integer> getTopThreePartners() {
        return topThreePartners;
    }

    /**
     * Get the user's mostRecentThreeTrade.
     * 
     * @return mostRecentThreeTrade.
     */
    public ArrayList<Integer> getMostRecentThreeTrade() {
        return mostRecentThreeTrade;
    }

    /**
     * Get the user's numTransaction.
     * 
     * @return numTransaction.
     */
    public int getNumTransaction() {
        return numTransaction;
    }

    /**
     * Get the user's numUncompletedTransaction.
     * 
     * @return numUncompletedTransaction.
     */
    public int getNumUncompletedTransaction() {
        return numUncompletedTransaction;
    }

    /**
     * Getter for this user's password
     * @return this user's password
     */
    public String getPassword() { return password;}
}
