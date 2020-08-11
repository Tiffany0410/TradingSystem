package managers.usermanager;

import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UserCommunityManager {

    private ArrayList<String[]> listFriendRequest;

    public UserCommunityManager(){
        this.listFriendRequest = new ArrayList<>();
    }
    /**
     * Gets all the Users following a User
     * @param userID The ID of the User
     * @return A list of integers of the ID of Users that follow the User
     */
    public ArrayList<TradableUser> usersFollowingUser (int userID, TradableUser person, ArrayList<TradableUser> listTradableUser){
        ArrayList<TradableUser> out = new ArrayList<>();
        if (person == null){
            return out;
        }
        for (TradableUser human: listTradableUser){
            if (human.getUserFollowed().contains(userID)){
                out.add(human);
            }
        }
        return out;
    }

    /**
     * Adds an entry to all the logs of the Users that are following the specified User
     * @param toAdd The entry to add
     * @param userID The User to check for followers
     */
     void editFollowerLogs(String toAdd, int userID, TradableUser person, ArrayList<TradableUser> listTradableUser){
        ArrayList<TradableUser> users = usersFollowingUser(userID, person, listTradableUser);
        for (TradableUser human: users){
            ArrayList<String> temp = human.getUserFollowingLogs();
            temp.add(toAdd);
            person.setUserFollowingLogs(temp);
        }
    }

    /**
     * Lets a User follow another User
     * @param userID The ID of the User
     * @param toFollow The ID of the User to follow
     * @return true if the User was successfully followed, false otherwise
     */
    public boolean userFollow(int userID, int toFollow, TradableUser person, TradableUser following,
                              ArrayList<TradableUser> listTradableUser){
        if (userID == toFollow){
            return false;
        }
        if (person == null || following == null){
            return false;
        }
        if (following.getFollowers().contains(userID)){
            return false;
        }
        person.followUser(toFollow);
        following.addFollowers(userID);
        editFollowerLogs("User " + person.getUsername() + " followed User " + following.getUsername(), userID,
                person ,listTradableUser);
        return true;
    }

    /**
     * Lets a User unfollow another User
     * @param userID The ID of the User
     * @param toUnfollow The ID of the User to unfollow
     * @return true if the User was successfully unfollowed, false otherwise
     */
    public boolean userUnfollow(int userID, int toUnfollow, TradableUser person, TradableUser following,
                                ArrayList<TradableUser> listTradableUser){
        if (person == null || following == null){
            return false;
        }
        if (!following.getFollowers().contains(userID)){
            return false;
        }
        person.unfollowUser(toUnfollow);
        following.removeFollowers(userID);
        editFollowerLogs("User " + person.getUsername() + " unfollowed User " + following.getUsername(), userID,
                person, listTradableUser);
        return true;
    }

    /**
     * Lets a User follow an Item
     * @param userID The ID of the User
     * @param toFollow The ID of the Item to follow
     * @return true if the Item was followed successfully, false otherwise
     */
    public boolean itemFollow(int userID, Item toFollow, TradableUser person, ArrayList<TradableUser> listTradableUser){
        if (person == null){
            return false;
        }
        if (person.getItemFollowed().contains(toFollow.getItemId())){
            return false;
        }
        person.followItem(toFollow.getItemId());
        toFollow.addPropertyChangeListener(person);
        editFollowerLogs("User " + person.getUsername() + " followed the Item with id "
                + toFollow.getItemId(), userID, person, listTradableUser);
        return true;
    }


    /**
     * Lets a User unfollow an Item
     * @param userID The ID of the User
     * @param toUnfollow The ID of the Item to unfollow
     * @return true if the Item was unfollowed successfully, false otherwise
     */
    public boolean itemUnfollow(int userID, Item toUnfollow, TradableUser person, ArrayList<TradableUser> listTradableUser){
        if (person == null){
            return false;
        }
        if (!person.getItemFollowed().contains(toUnfollow.getItemId())){
            return false;
        }
        person.unfollowItem(toUnfollow.getItemId());
        toUnfollow.removePropertyChangeListener(person);
        editFollowerLogs("User " + person.getUsername() + " unfollowed the Item with id "
                + toUnfollow.getItemId(), userID, person, listTradableUser);
        return true;
    }

    /**
     * Gives the UserFollowingLogs of the User
     * @return The UserFollowingLogs of the User
     */
    public ArrayList<String> getUserFollowingLogs (TradableUser person){
        ArrayList<String> out = new ArrayList<>();
        if (person == null){
            return out;
        }
        out = person.getUserFollowingLogs();
        return out;
    }

    /**
     * Gives the ItemFollowingLogs the User
     * @return The ItemFollowingLogs of the User
     */
    public ArrayList<String> getItemFollowingLogs (TradableUser person){
        ArrayList<String> out = new ArrayList<>();
        if (person == null){
            return out;
        }
        out = person.getItemFollowingLogs();
        return out;
    }

    /**
     * Gives all the Users and the Items they follow
     * @return A map of User IDs as keys and a list of the Item IDs they follow as values
     */
    public HashMap<Integer, ArrayList<Integer>> itemsFollowed(ArrayList<TradableUser> listTradableUser){
        HashMap<Integer, ArrayList<Integer>> out = new HashMap<>();
        for (TradableUser person: listTradableUser){
            out.put(person.getId(), person.getItemFollowed());
        }
        return out;
    }
    /**
     * Gives the list of friends the User is friends with
     * @return A list of Users who are in the User's friend list
     */
    public ArrayList<TradableUser> getFriends(TradableUser person, UserInfoManager uim,
                                              ArrayList<TradableUser> listTradableUser){
        ArrayList<TradableUser> out = new ArrayList<>();
        if (person != null){
            ArrayList<Integer> temp = person.getFriend();
            for (int num: temp){
                TradableUser friend = uim.findUser(num, listTradableUser);
                if (friend != null){
                    out.add(friend);
                }
            }

        }
        return out;
    }

    /**
     * Adds a request to the list of friend requests
     * @param message The message to give to the recipient
     * @param userTo The username of the recipient
     * @param userFrom The username of the sender
     * @return true if the request was successful, false otherwise
     */
    public boolean requestFriend(String message, String userTo, String userFrom){
        for (String[] request: listFriendRequest){
            if (request[0].equals(userTo) && request[1].equals(userFrom)){
                return false;
            }
        }
        String[] request = {userTo, userFrom, message};
        listFriendRequest.add(request);
        return true;
    }

    /**
     * Adds the Users to each others' friend lists
     * @param user1 The ID of one of the Users
     * @param user2 The ID of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean addFriend(int user1, int user2, TradableUser person1, TradableUser person2,
                             ArrayList<TradableUser> listTradableUser, UserInfoManager uim, ArrayList<User> listAdmin){
        boolean out = false;
        if (person1 != null && person2 != null){
            if (!person1.getFriend().contains(user2)) {
                person1.addToFriends(user2);
                person2.addToFriends(user1);
                editFollowerLogs("User " + person1.getUsername() + " is now friends with User "
                        + person2.getUsername(), user1, person1, listTradableUser);
                editFollowerLogs("User " + person2.getUsername() + " is now friends with User "
                        + person1.getUsername(), user2, person2, listTradableUser);
                out = true;
            }
        }
        String[] toRemove = {};
        for (String[] request: listFriendRequest){
            int id1 = uim.usernameToID(request[0], listTradableUser, listAdmin);
            int id2 = uim.usernameToID(request[1], listTradableUser, listAdmin);
            if ((user1 == id1 && user2 == id2) || (user1 == id2 && user2 == id1)){
                toRemove = request;
            }
        }
        if (toRemove.length != 0) {
            listFriendRequest.remove(toRemove);
        }
        return out;
    }

    /**
     * Removes the Users from each others friend list
     * @param user1 The ID of one of the Users
     * @param user2 The ID of the other User
     * @return true if they were successfully added, false otherwise
     */
    public boolean removeFriend(int user1, int user2, TradableUser person1, TradableUser person2){
        if (person1 != null && person2 != null){
            if (person1.getFriend().contains(user2)) {
                person1.removeFromFriends(user2);
                person2.removeFromFriends(user1);
                return true;
            }
        }
        return false;
    }

    /**
     * Gives the requests of all the Users requesting to be friends
     * @param userID The ID of the User
     * @return A list of all the friend requests requested of the User
     */
    public ArrayList<String[]> friendsRequesting(int userID, String username){
        ArrayList<String[]> out = new ArrayList<>();
        for (String[] request: listFriendRequest){
            if (request[0].equals(username)){
                out.add(request);
            }
        }
        return out;
    }

    /**
     * @param userID The user's ID
     * @return a list of users which are not friend with this user and not in this user's friend requests
     */
    public ArrayList<TradableUser> getUsersNotFriends(int userID, UserInfoManager uim, String username,
                                                      ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
        ArrayList<TradableUser> out = new ArrayList<>();
        if (uim.findUser(userID, listTradableUser) != null){
            ArrayList<Integer> friends = uim.findUser(userID, listTradableUser).getFriend();
            for (String[] request: friendsRequesting(userID, username)){
                if (request[0].equals(uim.idToUsername(userID, listTradableUser, listAdmin))){
                    friends.add(uim.usernameToID(request[1], listTradableUser, listAdmin));
                }
            }
            for (TradableUser user: listTradableUser){
                if (!friends.contains(user.getId()) && user.getId()!=userID){
                    out.add(user);
                }
            }
        }
        return out;
    }

    /**
     * Gives a list of Users that are in the same city as the given User
     * @param userID The ID of the User
     * @return Returns a list of Users who have the same home city as the given User
     */
    public ArrayList<TradableUser> sameCity (int userID, TradableUser homosapien, ArrayList<TradableUser> listTradableUser){
        ArrayList<TradableUser> out = new ArrayList<>();
        if (homosapien == null){
            return out;
        }
        for (TradableUser person: listTradableUser){
            if (person.getHome().equals(homosapien.getHome()) && (!person.equals(homosapien))){
                out.add(person);
            }
        }
        return out;
    }

    private ArrayList<TradableUser> merge (ArrayList<TradableUser> lst1, ArrayList<TradableUser> lst2, FeedbackManager fm) {
        int i = 0;
        int j = 0;
        ArrayList<TradableUser> out = new ArrayList<>();
        while (i != lst1.size() && j != lst2.size()){
            if (fm.calculateRate(lst1.get(i).getId()) > (fm.calculateRate(lst2.get(j).getId()))){
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

    private ArrayList<TradableUser> mergeSort (ArrayList<TradableUser> lst, FeedbackManager fm){
        if (lst.size() < 2){
            return lst;
        } else {
            int mid_i = lst.size()/2;
            ArrayList<TradableUser> left = new ArrayList<>(lst.subList(0, mid_i));
            ArrayList<TradableUser> right = new ArrayList<>(lst.subList(mid_i, lst.size()));
            ArrayList<TradableUser> sortedL = mergeSort(left, fm);
            ArrayList<TradableUser> sortedR = mergeSort(right, fm);
            return merge(sortedL, sortedR, fm);
        }
    }

    /**
     * Sorts the Users by rating
     * @return A list of Users sorted by rating in descending order
     */
    public ArrayList<TradableUser> sortRating (FeedbackManager fm, ArrayList<TradableUser> listTradableUser){
        ArrayList<TradableUser> listCopy = new ArrayList<>(listTradableUser);
        return mergeSort(listCopy, fm);
    }
}
