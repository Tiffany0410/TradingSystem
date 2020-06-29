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
    private ArrayList<Integer> wishList = new ArrayList<>();
    private ArrayList<Item> inventory = new ArrayList<>();
    private int id;
    protected int numLent;
    protected int numBorrowed;
    protected static int maxThreshold;
    protected Boolean isFrozen;
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
     * Add itemID to user's wishlist
     *
     * @param itemID the id of Item
     */
    public void addToWishList(Integer itemID) {
        if (! wishList.contains(itemID))
        {wishList.add(itemID);}
    }

    /**
     * Add item to user's inventory
     *
     * @param item the Item object
     */
    public void addToInventory(Item item) {
        inventory.add(item);
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
     * Getter for this user's password
     * @return this user's password
     */
    public String getPassword() { return password;}
}
