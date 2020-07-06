package bookTradeSystem;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class UserManager implements Serializable {

    private ArrayList<User> listUser;
    private ArrayList<AdminUser> listAdmin;
    private ArrayList<String[]> listUnfreezeRequest;
    private ArrayList<Item> listItemToAdd;
    private ArrayList<Item> listAllItems;

    /**
     * Constructs a UserManager with no Users or AdminUsers
     */
    public UserManager(){
        this.listUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listUnfreezeRequest = new ArrayList<>();
        this.listItemToAdd = new ArrayList<>();
        this.listAllItems = new ArrayList<>();
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
        this.listItemToAdd = new ArrayList<>();
    }

    /**
     * Gets the list of AdminUser
     * @return List of AdminUser
     */
    public ArrayList<AdminUser> getListAdmin() {
        return listAdmin;
    }

    /**
     * Gets the list of User
     * @return List of User
     */
    public ArrayList<User> getListUser() {
        return listUser;
    }

    /**
     * Sets the list of AdminUser
     * @param listAdmin List of AdminUser
     */
    public void setListAdmin(ArrayList<AdminUser> listAdmin) {
        this.listAdmin = listAdmin;
    }

    /**
     * Sets the list of User
     * @param listUser List of User
     */
    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    /**
     * Get the list of usernames and messages of User that request to be unfrozen
     * @return The list of usernames and messages
     */
    public ArrayList<String[]> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }

    /**
     * Set the list of usernames and messages of User that request to be unfrozen
     * @param listUnfreezeRequest The list of usernames and messages
     */
    //TODO:Delete
    public void setListUnfreezeRequest(ArrayList<String[]> listUnfreezeRequest) {
        this.listUnfreezeRequest = listUnfreezeRequest;
    }

    /**
     * Get the list of Items to be added
     * @return The list of Items
     */
    public ArrayList<Item> getListItemToAdd() {
        return listItemToAdd;
    }

    /**
     * Set the list of Items to be added
     * @param listItemToAdd The list of Items
     */
    public void setListItemToAdd(ArrayList<Item> listItemToAdd) {
        this.listItemToAdd = listItemToAdd;
    }

    /**
     * Searches for an Item in all the Users' inventories
     * @param item The prefix of the name of the Item searched for
     * @return A list of all the Items with the prefix in their name same as item
     */
    //TODO:Delete
    public ArrayList<Item> searchItem(String item){
        ArrayList<Item> out = new ArrayList<>();
        for (User person: listUser){
            for (Item thing: person.getInventory()){
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
            if (!person.getIfFrozen()){
                person.setIfFrozen(true);
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
            if (person.getIfFrozen()){
                person.setIfFrozen(false);
                out = true;
            }
        }
        if (out) {
            for (String[] request : listUnfreezeRequest) {
                if (request[0].equals(username)) {
                    listUnfreezeRequest.remove(request);
                    return true;
                }
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
     * Creates a new User
     * @param username Username of the new User
     * @param password Password of the new User
     * @param email Email of the new AdminUser
     */
    public void addUser(String username, String password, String email){
        int userID;
        if (listUser.size() != 0) {userID = listUser.size() + 1;}
        else {userID = 1;}

        User toAdd = new User(username, password, email, userID);
        this.listUser.add(toAdd);
    }

    /**
     * Creates a new AdminUser
     * @param username Username of the new AdminUser
     * @param password Password of the new AdminUser
     * @param email Email of the new AdminUser
     */
    public void addAdmin(String username, String password, String email){
        int adminID;
        if (listAdmin.size() != 0) {adminID = listAdmin.size() + 1;}
        else {adminID = 1;}
        AdminUser toAdd = new AdminUser(username, password, email, adminID);
        this.listAdmin.add(toAdd);
    }


    /**
     * Gives the Users who are not lending enough
     * @return A list of usernames of the Users who are not lending enough
     */
    //TODO:Delete
    public ArrayList<String> underLending(){
        ArrayList<String> out = new ArrayList<>();
        for (User person: listUser){
            if (person.getNumBorrowed() - person.getNumLent() < User.getNumLendBeforeBorrow()){
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
    public boolean removeItemWishlist(Integer itemID, String username){
        boolean out = false;
        User person = findUser(username);
        if (person != null){
            ArrayList<Integer> temp = person.getWishList();
            if (temp.contains(itemID)){
                temp.remove(itemID);
                person.setWishList(temp);
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
            ArrayList<Item> temp = person.getInventory();
            for (Item thing: temp){
                if (thing.getItemId()== itemID){
                    toRemove = thing;
                    out = true;
                }
            }
            if (out){
                temp.remove(toRemove);
                person.setInventory(temp);
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
            ArrayList<Integer> temp = person.getWishList();
            if (!temp.contains(itemID)){
                temp.add(itemID);
                out = true;
                person.setWishList(temp);
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
            ArrayList<Item> temp = person.getInventory();
            if (!temp.contains(item)) {
                temp.add(item);
                out = true;
                person.setInventory(temp);
                if (listItemToAdd.contains(item)) {
                    listItemToAdd.remove(item);
                }
            }
        }
        return out;
    }

    /**
     * Gives all the usernames and passwords of all User
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
     * Searches for a User
     * @param ID The ID of the User being searched for
     * @return The User that is being searched for
     */
    public User findUser(int ID){
        User out = null;
        for (User person : listUser) {
            if (person.getId() == ID) {
                out = person;
            }
        }
        return out;
    }

    /**
     * Searches for a AdminUser
     * @param username The username of the AdminUser
     * @return The AdminUser that is being searched for
     */
    //TODO:Delete
    public AdminUser findAdmin(String username){
        AdminUser out = null;
        for (AdminUser person: listAdmin){
            if (person.getUsername().equals(username)){
                out = person;
            }
        }
        return out;
    }

    /**
     * Changes the threshold of how many more times a user has to lend before they can borrow
     * @param change The new threshold
     */
    public void editNumLendBeforeBorrow(int change){
        User.setNumLendBeforeBorrow(change);
    }

    /**
     * Change the maximum number of transactions Users can make per week
     * @param change The new maximum number
     */
    public void editMaxNumTransactionsAllowedAWeek(int change){
        User.setMaxNumTransactionsAllowedAWeek(change);
    }

    /**
     * Change the maximum number of incomplete transactions a User can have
     * @param change The new maximum number
     */
    public void editMaxNumTransactionIncomplete(int change){
        User.setMaxNumTransactionIncomplete(change);
    }

    /**
     * Changes the maximum number of times a User can edit the Meeting date or time
     * @param change The maximum number
     */
    public void editMaxMeetingDateTimeEdits(int change){
        User.setMaxMeetingDateTimeEdits(change);
    }


    /**
     * Gives the username for the User with the given ID
     * @param ID The ID of the User
     * @return The username of the User
     */
    public String idToUsername(int ID){
        String out = null;
        for (User person: listUser){
            if (person.getId() == ID){
                out = person.getUsername();
            }
        }
        for (AdminUser person: listAdmin){
            if (person.getId() == ID){
                out = person.getUsername();
            }
        }
        return out;
    }

    /**
     * Gives the ID for the User with the given username
     * @param username The username of the User
     * @return The ID of the User
     */
    public int usernameToID(String username){
        int out = 0;
        for (User person: listUser){
            if (person.getUsername().equals(username)){
                out = person.getId();
            }
        }
        for (AdminUser person: listAdmin){
            if (person.getUsername().equals(username)){
                out = person.getId();
            }
        }
        return out;
    }

    /**
     * Gives all the Items in all the Users' inventories except the one with the given username
     * @param username The username of the User
     * @return A list of all the Items of all the Users' inventories except the one with the given username
     */
    public ArrayList<Item> allItems(String username){
        ArrayList<Item> out = new ArrayList<>();
        for (User person: listUser){
            if (!person.getUsername().equals(username)){
                out.addAll(person.getInventory());
            }
        }
        return out;
    }

    /**
     * Gives all the Items in all the Users' inventories except the one with the given ID
     * @param ID ID of the User
     * @return A list of all the Items in all the Users' inventories except the one with the given ID
     */
    public ArrayList<Item> allItems(int ID){
        ArrayList<Item> out = new ArrayList<>();
        for (User person: listUser){
            if (person.getId() != (ID)){
                out.addAll(person.getInventory());
            }
        }
        return out;
    }

    /**
     * Sends a request to unfreeze a User
     * @param username The username of the User
     * @param message The message of the User to unfreeze
     * @return true if the request was successful, false otherwise
     */
    public boolean requestUnfreeze(String username, String message){
        User person = findUser(username);
        if (person == null){
            return false;
        }
        if (!person.getIfFrozen()){
            return false;
        }
        for (String[] request: listUnfreezeRequest) {
            if (request[0].equals(username)) {
                return false;
            }
        }
        String[] toAdd = {username, message};
        listUnfreezeRequest.add(toAdd);
        return true;
    }

    /**
     * Sends a request for an Item to be added
     * @param name The name of the Item
     * @param description The description of the Item
     * @param ownerID The ID of the User who owns the Item
     */
    public void requestAddItem(String name, String description, int ownerID){
        int temp_itemID;
        //the id of the first item wait for add is 1
        if (listAllItems.isEmpty() && listItemToAdd.isEmpty()) {temp_itemID = 1;}
        //when list of items which already been add is empty
        else if (listAllItems.isEmpty()) {temp_itemID = listItemToAdd.get(listItemToAdd.size() - 1).getItemId() + 1;}
        //when list of items wait for add is empty
        else if (listItemToAdd.isEmpty()) {temp_itemID = listAllItems.get(listAllItems.size() - 1).getItemId() + 1;}
        //find the max ids in the list of items which already been add and the list of items wait for add
        //then use the (largest id + 1) as the new item id
        else {
            //max ids in the list of items wait for add
            int temp_itemID1 = listItemToAdd.get(listItemToAdd.size() - 1).getItemId();
            //max id in the list of items which already been add
            int temp_itemID2 = listAllItems.get(listAllItems.size() - 1).getItemId();
            if (temp_itemID1 >= temp_itemID2) {temp_itemID = temp_itemID1 + 1;}
            else {temp_itemID = temp_itemID2 + 1;}
        }


        Item out = new Item(name, description, ownerID, temp_itemID);
        listItemToAdd.add(out);
    }


    /**
     * Add the item into the list which contains all items
     * @param new_item the item which is allowed to add into specific user inventory
     */
    public void addItemToAllItemsList (Item new_item) {
        listAllItems.add(new_item);
    }

}
