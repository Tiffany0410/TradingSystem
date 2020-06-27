package bookTradeSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserManager implements Serializable {

    private ArrayList<User> listUser;
    private ArrayList<AdminUser> listAdmin;
    private ArrayList<String> listUnfreezeRequest;

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

    public ArrayList<AdminUser> getListAdmin() {
        return listAdmin;
    }

    public ArrayList<User> getListUser() {
        return listUser;
    }

    public void setListAdmin(ArrayList<AdminUser> listAdmin) {
        this.listAdmin = listAdmin;
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public ArrayList<String> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }

    public void setListUnfreezeRequest(ArrayList<String> listUnfreezeRequest) {
        this.listUnfreezeRequest = listUnfreezeRequest;
    }

    /**
     * Searches for an Item in all the Users' inventories
     * @param item The prefix of the name of the Item searched for
     * @return A list of all the Items with the prefix in their name same as item
     */
    public ArrayList<Item> searchItem(String item){
        ArrayList<Item> out = new ArrayList<>();
        for (User person: listUser){
            for (Item thing: person.inventory){
                if (thing.getName().contains(item)){
                    out.add(thing);
                }
            }
        }
        return out;
    }

    /**
     * Freezes a User
     * @param username The username of the the User that is being frozen
     * @return true if the User was frozen, false otherwise
     */
    public boolean freezeUser(String username){
        boolean out = false;
        User person = findUser(username);
        if (person != null){
            if (person.isFrozen){
                person.isFrozen = false;
                out = true;
            }
        }
        return out;
    }

    /**
     * Unfreezes a User
     * @param username The username of the User that is being unfrozen
     * @return true if the User was unfrozen, false otherwise
     */
    public boolean unfreezeUser(String username){
        boolean out = false;
        User person = findUser(username);
        if (person != null){
            if (!person.isFrozen){
                person.isFrozen = true;
                out = true;
            }
        }
        return out;
    }

    /**
     * Checks if the User exists
     * @param username The username of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String username){
        for (User person: listUser){
            if (person.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a new AdminUser
     * @param username Username of the new AdminUser
     * @param password Password of the new AdminUser
     * @param email Email of the new AdminUser
     */
    public void addAdmin(String username, String password, String email){
        AdminUser toAdd = new AdminUser(username, password, email);
        this.listAdmin.add(toAdd);
    }

    /**
     * Gives the Users who are not lending enough
     * @return A list of usernames of the Users who are not lending enough
     */
    public ArrayList<String> underLending(){
        ArrayList<String> out = new ArrayList<>();
        for (User person: listUser){
            if (person.getNumBorrowed() - person.getNumLent < person.threshold){
                out.add(person.getUsername());
            }
        }
        return out;
    }

    /**
     * Removes an Item from a User's wishlist
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their wishlist
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemWishlist(int itemID, String username){
        boolean out = false;
        User person = findUser(username);
        if (person != null){
            if (person.wishlist.contains(itemID)){
                person.wishlist.remove(itemID);
                out = true;
            }
        }
        return out;
    }

    /**
     * Removes an Item from a User's inventory
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their inventory
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemInventory(int itemID, String username){
        boolean out = false;
        Item toRemove = null;
        User person = findUser(username);
        if (person != null){
            for (Item thing: person.inventory){
                if (thing.getItemId().equals(itemID)){
                    toRemove = thing;
                    out = true;
                }
            }
            if (out){
                person.inventory.remove(toRemove);
            }
        }
        return out;
    }

    /**
     * Adds an Item to a User's wishlist
     * @param itemID The ID of the Item that is being added
     * @param username The username of the User to add the item into their wishlist
     * @return true if the item was added successfully, false otherwise
     */
    public boolean addItemWishlist(int itemID, String username){
        boolean out = false;
        User person = findUser(username);
        if (person != null){
            if (!person.wishlist.contains(itemID)){
                person.wishlist.add(itemID);
                out = true;
            }
        }
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
        User person = findUser(username);
        if (person != null){
            if (!person.inventory.contains(item)) {
                person.inventory.add(item);
                out = true;
            }
        }
        return out;
    }

    /**
     * Gives all the usernames and passwords of all AdminUser
     * @return A map of usernames to passwords for all User
     */
    public HashMap<String, String> userPasswords(){
        HashMap<String, String> out = new HashMap<>();
            for (User person: listUser){
                String name = person.getUsername();
                String pass = person.getPassword();
                out.put(name, pass);
            }
        return out;
    }

    /**
     * Gives all the usernames and passwords of all AdminUser
     * @return A map of usernames to passwords for all AdminUser
     */
    public HashMap<String, String> adminPasswords(){
        HashMap<String, String> out = new HashMap<>();
            for (AdminUser person: listAdmin){
                String name = person.getUsername();
                String pass = person.getPassword();
                out.put(name, pass);
            }
        return out;
    }

    /**
     * Searches for a User
     * @param username The username of the User being searched for
     * @return The User that is being searched for
     */
    public User findUser(String username){
        User out = null;
        for (User person : listUser) {
            if (person.getUsername().equals(username)) {
                out = person;
            }
        }
        return out;
    }

    /**
     * Changes the threshold of how many more times a user has to lend before they can borrow
     * @param change The new threshold
     */
    public void changeThreshold(int change){
        User.threshold = change;
    }


}
