package bookTradeSystem;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    /**
     * username is the user's username. password is the user's password. email is
     * the user's email.
     */
    //basic info
    private String username;
    private String password;
    private String email;
    private ArrayList<Integer> wishList;
    private ArrayList<Item> inventory;
    private int id;
    //status related
    private int numLent;
    private int numBorrowed;
    private Boolean isFrozen;
    // stores the number of times the user's been frozen.
    private int numFrozen;
    private int transactionsLeftForTheWeek = 3;
    //static threshold variables
    // set it to 3
    private static int maxNumTransactionsAllowedAWeek = 3;
    // set it to 3
    private static int maxNumTransactionIncomplete = 3;
    private static int numLendBeforeBorrow = 1;
    private static int maxMeetingDateTimeEdits = 3;


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
     * Get the user's email.
     *
     * @return the email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Set the user's username.
     *
     * @param name the user's username.
     */
    public void setUsername(String name) {
        //TODO: do we need this method???
        //TODO: there's no menu option that says the username can be changed anyways lol
        //TODO: and has problem if the user changes username mid-way
        //TODO: maybe delete this during refactoring phase :)
        this.username = name;
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
     * Add the id of the item to the user's wish list.
     *
     * @param itemID The id of the item to be added to the wish list.
     */
    public void addToWishList(Integer itemID) {
        if (! wishList.contains(itemID))
        {wishList.add(itemID);}
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
     * Add item to the user's inventory.
     *
     * @param item The item to be added to the inventory
     */
    public void addToInventory(Item item) {
        inventory.add(item);
    }


    /**
     * Sets user's inventory to a new one.
     * @param newInventory The new inventory.
     */
    public void setInventory(ArrayList<Item> newInventory){
        inventory = newInventory;
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
     * Getter for the maximum number of transactions allowed
     * for all the users in a week.
     * @return The maximum number of transactions allowed
     * in a week.
     */
    public static int getMaxNumTransactionsAllowedAWeek(){
        return maxNumTransactionsAllowedAWeek;
    }

    /**
     * Getter for the maximum number of uncompleted
     * transactions allowed before the user's account
     * gets frozen.
     * @return The maximum number of uncompleted transactions
     * allowed before the user's account gets frozen.
     */
    public static int getMaxNumTransactionIncomplete(){
        return maxNumTransactionIncomplete;
    }

    /**
     * Getter for the maximum number of edits to any
     * meeting's time and place for all the users.
     * @return The maximum number of edits to any
     * meeting's time and place.
     */
    public static int getMaxMeetingDateTimeEdits() {
        return maxMeetingDateTimeEdits;
    }

    /**
     * Getter for the number of items user need to
     * lend before the user can borrow, for all
     * users.
     * @return The  number of items user need to
     * lend before the user can borrow.
     *
     */
    public static int getNumLendBeforeBorrow(){
        return numLendBeforeBorrow;
    }

    /**
     * Setter for the maximum number of transactions allowed
     * for all the users in a week.
     * @param newVal The new threshold value for the
     *               number of transactions allowed
     *               for all the users in a week.
     */
    public static void setMaxNumTransactionsAllowedAWeek(int newVal){
        maxNumTransactionsAllowedAWeek = newVal;
    }

    /**
     * Setter for the maximum number of uncompleted
     * transactions allowed before the user's account
     * gets frozen.
     * @param newVal The new threshold value for
     *               uncompleted transactions allowed
     *               before the user's account gets frozen.
     */
    public static void setMaxNumTransactionIncomplete(int newVal){
        maxNumTransactionIncomplete = newVal;
    }

    /**
     * Setter for the the number of items user need to
     * lend before the user can borrow, for all
     * users.
     * @param newVal The new threshold value for
     *               the number of items user need to
     *               lend before the user can borrow
     */
    public static void setNumLendBeforeBorrow(int newVal){
        numLendBeforeBorrow = newVal;
    }

    /**
     * Setter for the maximum number of edits to any
     * meeting's time and place for all the users.
     * @param newVal The new threshold value for
     *               the number of edits to any
     *               meeting's time and place for
     *               all the users.
     */
    public static void setMaxMeetingDateTimeEdits(int newVal){
        maxMeetingDateTimeEdits = newVal;
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
}