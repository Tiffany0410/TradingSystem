package managers.usermanager;

import java.util.ArrayList;
import java.util.HashMap;

public class UserInfoManager {
    /**
     * Checks if the User exists
     * @param username The username of the User being searched for
     * @return true if the user exists, false otherwise
     */
    public boolean checkUser(String username, ArrayList<TradableUser> listTradableUser){
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
    public boolean checkUser(int userID, ArrayList<TradableUser> listTradableUser){
        for (TradableUser person: listTradableUser){
            if (person.getId() == (userID)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gives all the usernames and passwords of all User
     * @return A map of usernames to passwords for all User
     */
    public HashMap<String, String> userPasswords(ArrayList<TradableUser> listTradableUser){
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
    public HashMap<String, String> adminPasswords(ArrayList<User> listAdmin){
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
    public TradableUser findUser(String username, ArrayList<TradableUser> listTradableUser){
        TradableUser out = null;
        for (TradableUser person : listTradableUser) {
            if (person.getUsername().equalsIgnoreCase(username)) {
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
    public TradableUser findUser(int ID, ArrayList<TradableUser> listTradableUser){
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
    public String idToUsername(int ID, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin) {
        String out = null;
        for (TradableUser person : listTradableUser) {
            if (person.getId() == ID) {
                out = person.getUsername();
            }
        }
        for (User person : listAdmin) {
            if (person.getId() == ID) {
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
    public int usernameToID(String username, ArrayList<TradableUser> listTradableUser, ArrayList<User> listAdmin){
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
     * Gives the home city of the User
     * @param userID The ID of the User
     * @return The home city of the User
     */
    public String getHome(int userID, ArrayList<TradableUser> listTradableUser){
        TradableUser person = findUser(userID, listTradableUser);
        if(person != null){
            return person.getHome();
        }
        return "";
    }

    /**
     * Changes the home city of a User
     * @param userID The ID of the User
     * @param newHome The new city to change to
     */
    public void changeHome(int userID, String newHome, ArrayList<TradableUser> listTradableUser){
        TradableUser person = findUser(userID, listTradableUser);
        if(person != null){
            person.setHome(newHome);
        }
    }
}

