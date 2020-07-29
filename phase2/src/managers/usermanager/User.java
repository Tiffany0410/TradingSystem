package managers.usermanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * An instance of this class represents an admin user in this system.
 *
 * @author Hao Du
 * @version IntelliJ IDEA 2020.1
 */
public class User extends Observable implements Observer, Serializable {

    private String username;
    private String password;
    private String email;
    private int id;
    private ArrayList<String> userFollowingLogs;
    private ArrayList<String> itemFollowingLogs;

    /**
     * Construct an User.
     * 
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public User(String username, String password, String email, Integer adminID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = adminID;

    }

    /**
     * Get the user's username.
     * 
     * @return user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the user's id.
     * 
     * @return user's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for user's password
     * @return this user's password
     */
    public String getPassword() {return password;}

    /**
     * Update accordingly after subject calls notifyObservers()
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof User){
            userFollowingLogs.add(arg.toString());
        }
        else{
            itemFollowingLogs.add(arg.toString());
        }

    }
    /**
     * Set change for Item
     */
    public void setChanged(){
    }

    public void notifyObserver(String itemChanged){

    }

}
