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
    private Arraylist<Integer> wishList;
    private Arraylist<Item> inventory;
    private int id;
    protected int numLent;
    protected int numBorrowed;
    int maxThreshold;
    protected Boolean isForzen;
    protected Boolean isAdmin;
    private Arraylist<User> topThreePartners;
    private Arraylist<Trade> mostRecentThreeTrade;
    private int numTransaction;
    private int numUncompleteTransaction;

    /**
     * Construct an User.
     * 
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
     * @param username the user's username.
     */
    public void setUsername(String name) {
        this.username = name;
    }

    /**
     * Set the user's wishList.
     *
     * @param wishList the user's wishList.
     */
    public void setWishList(Arraylist<Integer> wishList) {
        this.wishList = wishList;
    }

    /**
     * Set the user's inventory.
     *
     * @param inventory the user's inventory.
     */
    public void setInventory(Arraylist<Item> inventory) {
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
    public Arraylist<Integer> getWishList() {
        return wishList;
    }

    /**
     * Get the user's inventory.
     * 
     * @return inventory.
     */
    public Arraylist<Item> getInventory() {
        return inventory;
    }

    /**
     * Get the user's topThreePartners.
     * 
     * @return topThreePartners.
     */
    public Arraylist<User> getTopThreePartners() {
        return topThreePartners;
    }

    /**
     * Get the user's mostRecentThreeTrade.
     * 
     * @return mostRecentThreeTrade.
     */
    public Arraylist<Trade> getMostRecentThreeTrade() {
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
     * Get the user's numUncompleteTransaction.
     * 
     * @return numUncompleteTransaction.
     */
    public int getNumUncompleteTransaction() {
        return numUncompleteTransaction;
    }
}
