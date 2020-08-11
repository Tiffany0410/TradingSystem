package managers.usermanager;

import java.io.Serializable;
import java.util.ArrayList;


public class UserItemManager implements Serializable {
    /**
     * Removes an Item from a User's wishlist
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their wishlist
     * @param listTradableUser List of all Users
     * @param person The User to remove from
     * @param listAdmin The list of all Admins
     * @param ucm A UserCommunityManager
     * @param uim A UserInfoManager
     * @return true if the item was removed successfully, false otherwise
     */
    protected boolean removeItemWishlist(Integer itemID, String username, TradableUser person, UserInfoManager uim,
                                      UserCommunityManager ucm, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
        boolean out = false;
        if (person != null){
            ArrayList<Integer> temp = person.getWishList();
            if (temp.contains(itemID)){
                temp.remove(itemID);
                person.setWishList(temp);
                out = true;
                int id = uim.usernameToID(username, listTradableUser, listAdmin);
                ucm.editFollowerLogs("User " + person.getUsername() + " has removed the Item with the id "
                        + itemID.toString() +
                        " to their wishlist.", id, person, listTradableUser);
            }
        }
        return out;
    }

    /**
     * Removes an Item from a User's inventory
     * @param itemID The ID of the Item to be removed
     * @param username The username of the User to remove the item from their inventory
     * @param uim A UserInfoManager
     * @param ucm A UserCommunityManager
     * @param listAdmin List of all Admins
     * @param person User to remove from
     * @param listTradableUser List of all Users
     * @return true if the item was removed successfully, false otherwise
     */
    protected boolean removeItemInventory(Integer itemID, String username, TradableUser person, UserInfoManager uim,
                                       UserCommunityManager ucm, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
        boolean out = false;
        if (person != null){
            ArrayList<Integer> temp = person.getInventory();
            if (temp.contains(itemID)){
                temp.remove(itemID);
                out = true;
                person.setInventory(temp);
                int id = uim.usernameToID(username, listTradableUser, listAdmin);
                ucm.editFollowerLogs("User " + person.getUsername() + " has removed the Item with the id " +
                        itemID.toString() +
                        " to their inventory.", id, person, listTradableUser);
            }
        }
        return out;
    }

    /**
     * Adds an Item to a User's wishlist
     * @param itemID The ID of the Item that is being added
     * @param username The username of the User to add the item into their wishlist
     * @param person User to add to
     * @param listTradableUser List of all Users
     * @param listAdmin List of all Admins
     * @param ucm A UserCommunityManager
     * @param uim A UserInformationManager
     * @return true if the item was added successfully, false otherwise
     */
    protected boolean addItemWishlist(Integer itemID, String username, TradableUser person, UserInfoManager uim,
                                   UserCommunityManager ucm, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
        boolean out = false;
        if (person != null){
            ArrayList<Integer> temp = person.getWishList();
            if (!temp.contains(itemID)){
                temp.add(itemID);
                out = true;
                person.setWishList(temp);
                int id = uim.usernameToID(username, listTradableUser, listAdmin);
                ucm.editFollowerLogs("User " + person.getUsername() + " has added the Item with the id " + itemID.toString() +
                        " to their wishlist.", id, person, listTradableUser);
            }
        }

        return out;
    }

    /**
     * Adds an Item to a User's inventory
     * @param itemID The ID of the item that is being added
     * @param username The username of the User to add the item into their inventory
     * @param person User to add to
     * @param listTradableUser List of all Users
     * @param listAdmin List of all Admins
     * @param ucm A UserCommunityManager
     * @param uim A UserInformationManager
     * @return true if the item was added successfully, false otherwise
     */
    protected boolean addItemInventory(Integer itemID, String username, TradableUser person, UserInfoManager uim,
                                    UserCommunityManager ucm, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
        boolean out = false;
        if (person != null){
            ArrayList<Integer> temp = person.getInventory();
            if (!temp.contains(itemID)) {
                temp.add(itemID);
                out = true;
                person.setInventory(temp);
                int id = uim.usernameToID(username, listTradableUser, listAdmin);
                ucm.editFollowerLogs("User " + person.getUsername() + " has added the Item with the id "
                        + itemID.toString() +
                        " to their inventory.", id, person, listTradableUser);
            }
        }
        return out;
    }

    /**
     * Remove item with itemId from the user with userId1 and
     * the user with userId2 appropriately.
     * @param itemId The id of the item to be removed.
     * @param uim A UserInfoManager
     * @param ucm A UserCommunityManager
     * @param listAdmin List of all Admins
     * @param listTradableUser List of all Users
     * @param user1 User to remove from
     * @param user2 User to remove from
     */
    protected void removeItemFromUsers(int itemId, TradableUser user1, TradableUser user2, UserInfoManager uim,
                                    UserCommunityManager ucm, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin) {
        if (user1.getWishList().contains(itemId)) {
            //user1 = borrower
            removeItemWishlist(itemId, user1.getUsername(), user1, uim, ucm, listTradableUser, listAdmin);
            // record the borrow
            user1.addOneToNumBorrowed();
            //remove the item from user2's inventory
            removeItemInventory(itemId, user2.getUsername(), user2, uim, ucm, listTradableUser, listAdmin);
            // record the lend
            user2.addOneToNumLent();
        } else {
            //user2 = borrower
            removeItemWishlist(itemId, user2.getUsername(), user2, uim, ucm, listTradableUser, listAdmin);
            // record the borrow
            user2.addOneToNumBorrowed();
            //remove item from user1's inventory
            removeItemInventory(itemId, user1.getUsername(), user1, uim, ucm, listTradableUser, listAdmin);
            // record the lend
            user1.addOneToNumLent();

        }
    }

    /**
     * Gives the inventory of the User
     * @param person User to get info of
     * @return Returns a list of integers of the Id of Items in the User's inventory
     */
    protected ArrayList<Integer> getUserInventory(TradableUser person){
        if (person != null){
            return person.getInventory();
        }
        return new ArrayList<>();
    }

    /**
     * Gives the wishlist of the User
     * @param person User to get info of
     * @return Returns a list of integers of the ID of Items in the User's wishlist
     */
    protected ArrayList<Integer> getUserWishlist(TradableUser person){
        if (person != null){
            return person.getWishList();
        }
        return new ArrayList<>();
    }

    /**
     * Checks to see if the User has anything the other User wants
     * @param person1 User that wants
     * @param person2 User that has
     * @return A list of integers of Item IDs that the User wants and the other has
     */
    protected ArrayList<Integer> wantedItems (TradableUser person1, TradableUser person2){
        ArrayList<Integer> out = new ArrayList<>();
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
}
