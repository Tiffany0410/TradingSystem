package managers.usermanager;

import exception.InvalidIdException;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;

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
    private ArrayList<String[]> listFriendRequest;

    /**
     * Constructs a UserManager with no Users or AdminUsers
     */
    public UserManager(){
        this.listTradableUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listUnfreezeRequest = new ArrayList<>();
        this.listFriendRequest = new ArrayList<>();
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
        this.listFriendRequest = new ArrayList<>();
    }

    /**
     * Gets the list of User
     * @return List of User
     */
    public ArrayList<TradableUser> getListTradableUser() {
        return listTradableUser;
    }

    /**
     * Gets the list of AdminUser
     * @return List of AdminUser
     */
    public ArrayList<User> getListAdminUser() {
        return listAdmin;
    }


    /**
     * Gets the list of AdminUser username
     * @return List of AdminUser username
     */
    public ArrayList<String> getListAdminUserName() {
        ArrayList<String> usernameList = new ArrayList<>();
        for (User adminUser: listAdmin) {
            usernameList.add(adminUser.getUsername());
        }
        return usernameList;
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
     * Checks if the User exists
     * @param userID The ID of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(int userID){
        for (TradableUser person: listTradableUser){
            if (person.getId() == (userID)){
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
    public void addUser(String username, String password, String email, String home){
        int userID;
        if (listTradableUser.size() != 0) {userID = listTradableUser.size() + 1;}
        else {userID = 1;}

        TradableUser toAdd = new TradableUser(username, password, email, userID);
        toAdd.setHome(home);
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
    public boolean addItemWishlist(Integer itemID, String username){
        boolean out = false;
        TradableUser person = findUser(username);
        if (person != null){
            ArrayList<Integer> temp = person.getWishList();
            if (!temp.contains(itemID)){
                temp.add(itemID);
                out = true;
                person.setWishList(temp);
            }
            ArrayList<Integer> usersFollowing = usersFollowingUser(username);
            for (int id: usersFollowing){
                TradableUser human = findUser(id);
                if (human != null){
                    String[] log = {username, "wishlist", itemID.toString()};
                    human.logUserActivity(log);
                }
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
    public boolean addItemInventory(Integer item, String username){
        boolean out = false;
        TradableUser person = findUser(username);
        if (person != null){
            ArrayList<Integer> temp = person.getInventory();
            if (!temp.contains(item)) {
                temp.add(item);
                out = true;
                person.setInventory(temp);
            }
            ArrayList<Integer> usersFollowing = usersFollowingUser(username);
            for (int id: usersFollowing){
                TradableUser human = findUser(id);
                if (human != null){
                    String[] log = {username, "inventory", itemID.toString()};
                    human.logUserActivity(log);
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

    public int getInfo(int userID, String threshold){
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
                    boolean convert = person.getOnVacation();
                    if (convert){
                        return 1;
                    } else {
                        return 0;
                    }
            }
        }
        return -1;
    }

    public int getInfo(String username, String threshold){
        int num = usernameToID(username);
        return getInfo(num, threshold);
    }

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
        if (checkUser(userTo) && checkUser(userFrom)) {
            for (String[] request: listFriendRequest){
                if (request[0].equals(userTo) && request[1].equals(userFrom)){
                    return false;
                }
            }
            String[] request = {userTo, userFrom, message};
            listFriendRequest.add(request);
            out = true;
        }
        return out;
    }

    public boolean addFriend(String user1, String user2){
        if (!checkUser(user1) || ! checkUser(user2)){
            return false;
        }
        int id1 = usernameToID(user1);
        int id2 = usernameToID(user2);
        return addFriend(id1, id2);
    }

    public boolean addFriend(int user1, int user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        if (person1 != null && person2 != null){
            person1.addToFriends(user2);
            person2.addToFriends(user1);
            return true;
        }
        String[] toRemove = {};
        for (String[] request: listFriendRequest){
            int id1 = usernameToID(request[0]);
            int id2 = usernameToID(request[1]);
            if ((user1 == id1 && user2 == id2) || (user1 == id2 && user2 == id1)){
                toRemove = request;
            }
        }
        if (toRemove.length != 0) {
            listFriendRequest.remove(toRemove);
        }
        return false;
    }



    public boolean removeFriend(String user1, String user2){
        if (!checkUser(user1) || ! checkUser(user2)){
            return false;
        }
        int id1 = usernameToID(user1);
        int id2 = usernameToID(user2);
        return removeFriend(id1, id2);
    }

    public boolean removeFriend(int user1, int user2){
        TradableUser person1 = findUser(user1);
        TradableUser person2 = findUser(user2);
        if (person1 != null && person2 != null){
            person1.removeFromFriends(user2);
            person2.removeFromFriends(user1);
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
        if (person1 == null || person2 == null){
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

    public boolean userFollow(int userID, int toFollow){
        TradableUser person = findUser(userID);
        TradableUser following = findUser(userID);
        if (person == null || following == null){
            return false;
        }
        if (person.getFollowers().contains(toFollow)){
            return false;
        }
        person.followUser(toFollow);
        following.addFollowers(userID);
        return true;
    }

    public boolean itemFollow(int userID, int toFollow){
        TradableUser person = findUser(userID);
        if (person == null){
            return false;
        }
        if (person.getItemFollowed().contains(toFollow)){
            return false;
        }
        person.followItem(toFollow);
        return true;
    }

    public HashMap<Integer, ArrayList<Integer>> itemsFollowed(){
        HashMap<Integer, ArrayList<Integer>> out = new HashMap<>();
        for (TradableUser person: listTradableUser){
            out.put(person.getId(), person.getItemFollowed());
        }
        return out;
    }

    public ArrayList<String[]> friendsRequesting(int userID){
        ArrayList<String[]> out = new ArrayList<>();
        String username = idToUsername(userID);
        for (String[] request: listFriendRequest){
            if (request[0].equals(username)){
                out.add(request);
            }
        }
        return out;
    }

    public ArrayList<String> getUserFollowingLogs (int userID){
        TradableUser person = findUser(userID);
        ArrayList<String> out = new ArrayList<>();
        if (person == null){
            return out;
        }
        out = person.getUserFollowingLogs();
        return out;
    }

    public ArrayList<String> getItemFollowingLogs (int userID){
        TradableUser person = findUser(userID);
        ArrayList<String> out = new ArrayList<>();
        if (person == null){
            return out;
        }
        out = person.getItemFollowingLogs();
        return out;
    }

    private ArrayList<TradableUser> merge (ArrayList<TradableUser> lst1, ArrayList<TradableUser> lst2) {
        int i = 0;
        int j = 0;
        ArrayList<TradableUser> out = new ArrayList<>();
        while (i != lst1.size() && j != lst2.size()){
            if (lst1.get(i).getRating() < lst2.get(j).getRating()){
                out.add(lst1.get(i));
                i++;
            } else {
                out.add(lst2.get(j));
                j++;
            }

            while (i != lst1.size()){
                out.add(lst1.get(i));
                i++;
            }

            while (j != lst2.size()){
                out.add(lst2.get(j));
                j++;
            }
        }
        return out;
    }

    private ArrayList<TradableUser> mergeSort (ArrayList<TradableUser> lst){
        if (lst.size() < 2){
            return lst;
        } else {
            int mid_i = lst.size()/2;
            ArrayList<TradableUser> left = new ArrayList<>(lst.subList(0, mid_i));
            ArrayList<TradableUser> right = new ArrayList<>(lst.subList(mid_i, lst.size()));
            ArrayList<TradableUser> sortedL = mergeSort(left);
            ArrayList<TradableUser> sortedR = mergeSort(right);
            return merge(sortedL, sortedR);
        }
    }

    public ArrayList<TradableUser> sortRating (){
        ArrayList<TradableUser> listCopy = new ArrayList<>();
        for (TradableUser person: listTradableUser) {
            listCopy.add(person);
        }
        return mergeSort(listCopy);
    }

    public ArrayList<String[]> getListFriendRequest() {
        return listFriendRequest;
    }

    public void setListFriendRequest(ArrayList<String[]> listFriendRequest) {
        this.listFriendRequest = listFriendRequest;
    }

    public ArrayList<Integer> usersFollowingUser (int userID){
        ArrayList<Integer> out = new ArrayList<>();
        if (!checkUser(userID)){
            return out;
        }
        for (TradableUser person: listTradableUser){
            if (person.getUserFollowed().contains(userID)){
                out.add(person.getId());
            }
        }
        return out;
    }

    public ArrayList<Integer> usersFollowingUser (String username){
        if (!checkUser(username)){
            return new ArrayList<>();
        }
        int id = usernameToID(username);
        return usersFollowingUser(id);
    }

    public ArrayList<Integer> usersFollowingItem (int itemID){
        ArrayList<Integer> out = new ArrayList<>();
        if (!checkUser(itemID)){
            return out;
        }
        for (TradableUser person: listTradableUser){
            if (person.getItemFollowed().contains(itemID)){
                out.add(person.getId());
            }
        }
        return out;
    }

    public ArrayList<Integer> usersFollowingItem (String username){
        if (!checkUser(username)){
            return new ArrayList<>();
        }
        int id = usernameToID(username);
        return usersFollowingItem(id);
    }

    /**
     * @param userID The user's ID
     * @return a list of users which are not friend with this user and not in this user's friend requests
     */
    public ArrayList<TradableUser> getUsersNotFriends(int userID){
        ArrayList<TradableUser> out = new ArrayList<>();
        if (findUser(userID) != null){
            ArrayList<Integer> friends = findUser(userID).getFriend();
            for (String[] request: friendsRequesting(userID)){
                if (request[0].equals(idToUsername(userID))){
                    friends.add(usernameToID(request[1]));
                }
            }
            for (TradableUser user: listTradableUser ){
                if (! friends.contains(user.getId())){
                    out.add(user);
                }
            }
        }
        return out;
    }

}
