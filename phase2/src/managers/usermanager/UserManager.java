package managers.usermanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Stores all the Users and AdminUsers. Manages the Users and the actions that they can make.
 * @author Gabriel
 * @version IntelliJ IDEA 2020.1
 */
public class UserManager implements Serializable {

    private ArrayList<TradableUser> listTradableUser;
    private ArrayList<User> listAdmin;
    private ArrayList<String[]> listUnfreezeRequest;
    private HashMap<String, ArrayList<String[]>> listFriendRequest;

    /**
     * Constructs a UserManager with no Users or AdminUsers
     */
    public UserManager(){
        this.listTradableUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listUnfreezeRequest = new ArrayList<>();
        this.listFriendRequest = new HashMap<>();
    }

    /**
     * Constructs a UserManager with the given Users and AdminUsers
     * @param tradableUsers The initial Users
     * @param admins The initial AdminUsers
     */
    public UserManager(ArrayList<TradableUser> tradableUsers, ArrayList<User> admins){
        this.listTradableUser = tradableUsers;
        this.listAdmin = admins;
        this.listUnfreezeRequest = new ArrayList<>();
        this.listFriendRequest = new HashMap<>();
    }

    /**
     * Gets the list of User
     * @return List of User
     */
    public ArrayList<TradableUser> getListTradableUser() {
        return listTradableUser;
    }

    /**
     * Get the list of usernames and messages of User that request to be unfrozen
     * @return The list of usernames and messages
     */
    public ArrayList<String[]> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }


    /**
     * Searches for an Item in all the Users' inventories
     * @param item The prefix of the name of the Item searched for
     * @return A list of all the Items with the prefix in their name same as item
     */
    //TODO: I think this should probably be moved to the item manager
    /*
    public ArrayList<Integer> searchItem(String item){
        ArrayList<Integer> out = new ArrayList<>();
        for (User person: listUser){
            for (Integer thing: person.getInventory()){
                //Change this condition accordingly
                if (thing.getName().contains(item)){
                    out.add(thing);
                }
            }
        }
        return out;
    }
    */

    /**
     * Freezes a User
     * @param username The username of the the User that is being frozen
     * @return true if the User was frozen, false otherwise
     */
    public boolean freezeUser(String username){
        boolean out = false;
        TradableUser person = findUser(username);
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
        TradableUser person = findUser(username);
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
        for (TradableUser person: listTradableUser){
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
        if (listTradableUser.size() != 0) {userID = listTradableUser.size() + 1;}
        else {userID = 1;}

        TradableUser toAdd = new TradableUser(username, password, email, userID);
        this.listTradableUser.add(toAdd);
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
        User toAdd = new User(username, password, email, adminID);
        this.listAdmin.add(toAdd);
    }

    /**
     * Removes an Item from a User's wishlist
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their wishlist
     * @return true if the item was removed successfully, false otherwise
     */
    public boolean removeItemWishlist(Integer itemID, String username){
        boolean out = false;
        TradableUser person = findUser(username);
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
        TradableUser person = findUser(username);
        if (person != null){
            ArrayList<Integer> temp = person.getInventory();
            if (temp.contains(itemID)){
                temp.remove(itemID);
                out = true;
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
        TradableUser person = findUser(username);
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
    //TODO remove from listItemtoAdd somehow
    public boolean addItemInventory(int item, String username){
        boolean out = false;
        TradableUser person = findUser(username);
        if (person != null){
            ArrayList<Integer> temp = person.getInventory();
            if (!temp.contains(item)) {
                temp.add(item);
                out = true;
                person.setInventory(temp);
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
        for (TradableUser person: listTradableUser){
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
        for (User person: listAdmin){
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
    public TradableUser findUser(String username){
        TradableUser out = null;
        for (TradableUser person : listTradableUser) {
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
    public TradableUser findUser(int ID){
        TradableUser out = null;
        for (TradableUser person : listTradableUser) {
            if (person.getId() == ID) {
                out = person;
            }
        }
        return out;
    }

    /**
     * Gives the username for the User with the given ID
     * @param ID The ID of the User
     * @return The username of the User
     */
    public String idToUsername(int ID){
        String out = null;
        for (TradableUser person: listTradableUser){
            if (person.getId() == ID){
                out = person.getUsername();
            }
        }
        for (User person: listAdmin){
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
        for (TradableUser person: listTradableUser){
            if (person.getUsername().equals(username)){
                out = person.getId();
            }
        }
        for (User person: listAdmin){
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
    //TODO Refactor this or move to ItemManager
    public ArrayList<Integer> allItems(String username){
        ArrayList<Integer> out = new ArrayList<>();
        for (TradableUser person: listTradableUser){
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
    //TODO Refactor this or move to ItemManager
    /*
    public ArrayList<Item> allTradableItems(int ID){
        ArrayList<Item> out = new ArrayList<>();
        for (User person: listUser){
            if (person.getId() != (ID)){
                out.addAll(person.getInventory());
            }
        }
        return out;
    }
    */

    /**
     * Sends a request to unfreeze a User
     * @param username The username of the User
     * @param message The message of the User to unfreeze
     * @return true if the request was successful, false otherwise
     */
    public boolean requestUnfreeze(String username, String message){
        TradableUser person = findUser(username);
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
     * Remove item with itemId from the user with userId1 and
     * the user with userId2 appropriately.
     * @param userId1 The first user.
     * @param userId2 The second user.
     * @param itemId The id of the item to be removed.
     */
    public void removeItemFromUsers(int userId1, int userId2, int itemId) {
        TradableUser tradableUser1 = findUser(userId1);
        TradableUser tradableUser2 = findUser(userId2);
        if (tradableUser1.getWishList().contains(itemId)) {
            //user1 = borrower
            removeItemWishlist(itemId, tradableUser1.getUsername());
            // record the borrow
            tradableUser1.addOneToNumBorrowed();
            //remove the item from user2's inventory
            removeItemInventory(itemId, tradableUser2.getUsername());
            // record the lend
            tradableUser2.addOneToNumLent();
        } else {
            //user2 = borrower
            removeItemWishlist(itemId, tradableUser2.getUsername());
            // record the borrow
            tradableUser2.addOneToNumBorrowed();
            //remove item from user1's inventory
            removeItemInventory(itemId, tradableUser1.getUsername());
            // record the lend
            tradableUser1.addOneToNumLent();

        }
    }

    //TODO Write Javadoc for all below
    public boolean getFrozenStatus(String username) {
        for (TradableUser person : listTradableUser){
            if (person.getUsername().equals(username)) {
                return person.getIfFrozen();
            }
        }
        return false;
    }

    public boolean getFrozenStatus(int userID) {
        for (TradableUser person : listTradableUser){
            if (person.getId() == (userID)) {
                return person.getIfFrozen();
            }
        }
        return false;
    }

    public ArrayList<Integer> getUserInventory(String username){
        TradableUser person = findUser(username);
        if (person != null){
            return person.getInventory();
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getUserInventory(int userID){
        TradableUser person = findUser(userID);
        if (person != null){
            return person.getInventory();
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getUserWishlist(String username){
        TradableUser person = findUser(username);
        if (person != null){
            return person.getWishList();
        }
        return new ArrayList<>();
    }

    public ArrayList<Integer> getUserWishlist(int userID){
        TradableUser person = findUser(userID);
        if (person != null){
            return person.getWishList();
        }
        return new ArrayList<>();
    }

    public void setThreshold (int userID, String threshold, int change){
        TradableUser person = findUser(userID);
        if (person != null){
            switch (threshold){
                case "TransactionLeftForTheWeek":
                    person.setTransactionLeftForTheWeek(change);
                    break;
                case "NumFrozen":
                    person.addOneToNumFrozen();
                    break;
                }
        }
    }

    public void setThreshold (String username, String threshold, int change){
        int num = usernameToID(username);
        setThreshold(num, threshold, change);
    }

    public int getThreshold (int userID, String threshold){
        TradableUser person = findUser(userID);
        if (person != null){
            switch (threshold){
                case "TransactionLeftForTheWeek":
                    return person.getNumTransactionLeftForTheWeek();
                case "NumFrozen":
                    return person.getNumFrozen();
                case "NumLent":
                    return person.getNumLent();
                case "NumBorrowed":
                    return person.getNumBorrowed();
                case "Vacation":
                    return person.getOnVacation();
            }
        }
        return -1;
    }

    public int getThreshold (String username, String threshold){
        int num = usernameToID(username);
        return getThreshold(num, threshold);
    }

    //TODO Not sure if this should return Integers or Users
    public ArrayList<TradableUser> getFriends(int userID){
        TradableUser person = findUser(userID);
        ArrayList<TradableUser> out = new ArrayList<>();
        if (person != null){
            ArrayList<Integer> temp = person.getFriend();
            for (int num: temp){
                TradableUser friend = findUser(num);
                if (friend != null){
                    out.add(friend);
                }
            }

        }
        return out;
    }

    public boolean requestFriend(String message, String userTo, String userFrom){
        boolean out = false;
        if (checkUser(userTo)) {
            if (!listFriendRequest.containsKey(userTo)){
                ArrayList<String[]> toAdd = new ArrayList<>();
                String[] request = {message, userFrom};
                toAdd.add(request);
                listFriendRequest.put(userTo, toAdd);
                out = true;
            } else {
                ArrayList<String[]> temp = listFriendRequest.get(userTo);
                for (String[] request: temp){
                    if (request[0].equals(userFrom)){
                        return false;
                    }
                }
                String[] request = {message, userFrom};
                temp.add(request);
                listFriendRequest.put(userTo, temp);
                out = true;
            }
        }
        return out;
    }

    //TODO finish this when friends list is created
    public boolean addFriend(String user1, String user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        if (person1 != null && person2 != null){
            Integer id1 = usernameToID(user1);
            Integer id2 = usernameToID(user2);
            person1.addToFriends(id2);
            person2.addToFriends(id1);
            return true;
        }
        return false;
    }

    //TODO finish this when friends list is created
    public boolean removeFriend(String user1, String user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        if (person1 != null && person2 != null){
            Integer id1 = usernameToID(user1);
            Integer id2 = usernameToID(user2);
            person1.removeFromFriends(id2);
            person2.removeFromFriends(id1);
            return true;
        }
        return true;
    }

    public boolean goOnVacation(int userID){
        TradableUser person = findUser(userID);
        if (person != null){
            if (person.getOnVacation()){
                return false;
            } else {
                person.setOnVacation(true);
                return true;
            }
        }
        return false;
    }

    public boolean comeFromVacation(int userID){
        TradableUser person = findUser(userID);
        if (person != null){
            if (!person.getOnVacation()){
                return false;
            } else {
                person.setOnVacation(false);
                return true;
            }
        }
        return false;
    }

    public ArrayList<TradableUser> sameCity (int userID){
        ArrayList<TradableUser> out = new ArrayList<>();
        TradableUser homosapien = findUser(userID);
        if (homosapien == null){
            return out;
        }
        for (TradableUser person: listTradableUser){
            if (person.getHome().equals(homosapien.getHome())){
                out.add(person);
            }
        }
        return out;
    }

    public ArrayList<Integer> wantedItems (int wantUser, int haveUser){
        ArrayList<Integer> out = new ArrayList<>();
        TradableUser person1 = findUser(wantUser);
        TradableUser person2 = findUser(haveUser);
        if (person1 == null && person2 == null){
            return out;
        }
        ArrayList<Integer> wantList = person1.getWishList();
        ArrayList<Integer> haveList = person2.getInventory();
        for (int thing: wantList){
            if (haveList.contains(thing)){
                out.add(thing);
            }
        }
        return out;
    }

    public String getHome(int userID){
        TradableUser person = findUser(userID);
        if(person != null){
            return person.getHome();
        }
        return "";
    }

    public void changeHome(int userID, String newHome){
        TradableUser person = findUser(userID);
        if(person != null){
            person.setHome(newHome);
        }
    }
}
