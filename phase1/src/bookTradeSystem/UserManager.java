package bookTradeSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserManager implements Serializable {

    private List<User> listUser;
    private List<AdminUser> listAdmin;
    private List<String> listUnfreezeRequest;

    /**
     * Constructs a UserManager with no Users or AdminUsers
     */
    public UserManager(){
        this.listUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listUnfreezeRequest = new ArrayList<>();
    }

    /**
     * Constructs a UserManager with the given Users and AdminUsers
     * @param users The initial Users
     * @param admins The initial AdminUsers
     */
    public UserManager(ArrayList<User> users, ArrayList<AdminUser> admins){
        this.listUser = users;
        this.listAdmin = admins;
        this.listUnfreezeRequest = new ArrayList<>();
    }

    public List<AdminUser> getListAdmin() {
        return listAdmin;
    }

    public List<User> getListUser() {
        return listUser;
    }

    public void setListAdmin(ArrayList<AdminUser> listAdmin) {
        this.listAdmin = listAdmin;
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public List<String> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }

    /**
     * Searches for an Item in all the Users' inventories
     * @param item The prefix of the name of the Item searched for
     * @return A list of all the Items with the prefix in their name same as item
     */
    public List<Item> searchItem(String item){
        List<Item> out = new ArrayList<>();
        //TODO
        return out;
    }

    /**
     * Freezes a User
     * @param username The username of the the User that is being frozen
     * @return true if the User was frozen, false otherwise
     */
    public boolean freezeUser(String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Unfreezes a User
     * @param username The username of the User that is being unfrozen
     * @return true if the User was unfrozen, false otherwise
     */
    public boolean unfreezeUser(String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Checks if the User exists
     * @param username The username of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String username){
        boolean out = false;
        //TODO
        return false;
    }

    /**
     * Creates a new AdminUser
     * @param username Username of the new AdminUser
     * @param password Password of the new AdminUser
     * @param email Email of the new AdminUser
     */
    public void addAdmin(String username, String password, String email){
        //TODO
    }

    /**
     * Gives the Users who are not lending enough
     * @return A list of usernames of the Users who are not lending enough
     */
    public List<String> underLending(){
        List<String> out = new ArrayList<>();
        //TODO
        return out;
    }

    /**
     * Removes an Item from a User's wishlist
     * @param item The item to be removed
     * @param username The username of the User to remove the item from their wishlist
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemWishlist(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Removes an Item from a User's inventory
     * @param item The item to be removed
     * @param username The username of the User to remove the item from their inventory
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemInventory(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Adds an Item to a User's wishlist
     * @param item The item that is being added
     * @param username The username of the User to add the item into their wishlist
     * @return true if the item was added successfully, false otherwise
     */
    public boolean addItemWishlist(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Adds an Item to a User's inventory
     * @param item The Item that is being added
     * @param username The username of the User to add the item into their inventory
     * @return true if the item was added successfully, false otherwise
     */
    public boolean addItemInventory(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    /**
     * Gives all the usernames and passwords of all AdminUser
     * @return A map of usernames to passwords for all User
     */
    public Map<String, String> userPasswords(){
        Map<String, String> out = new HashMap<>();
        //TODO
        return out;
    }

    /**
     * Gives all the usernames and passwords of all AdminUser
     * @return A map of usernames to passwords for all AdminUser
     */
    public Map<String, String> adminPasswords(){
        Map<String, String> out = new HashMap<>();
        //TODO
        return out;
    }

    /**
     * Searches for a User
     * @param username The username of the User being searched for
     * @return The User that is being searched for
     */
    public User findUser(String username){
        User out = new User();
        //TODO
        return out;
    }

    /**
     * Changes the threshold of how many more times a user has to lend before they can borrow
     * @param change The new threshold
     */
    public void changeThreshold(int change){
        //TODO
    }


}
