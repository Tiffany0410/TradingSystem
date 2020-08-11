package managers.usermanager;

import java.io.Serializable;


/**
 * An instance of this class represents an admin user in this system.
 *
 * @author Hao Du
 * @version IntelliJ IDEA 2020.1
 */
public class User  implements  Serializable {

    private String username;
    private String password;
    private String email;
    private int id;

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
    protected int getId() {
        return this.id;
    }

    /**
     * Getter for user's password
     *
     * @return this user's password
     */
    protected String getPassword() {
        return password;
    }

    /**
     * Getter for user's email.
     *
     * @return this user's email
     */
    protected String getEmail(){return this.email;}





}
