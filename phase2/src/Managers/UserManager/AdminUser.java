package Managers.UserManager;

import java.io.Serializable;

/**
 * An instance of this class represents an admin user in this system.
 *
 * @author Hao Du
 * @version IntelliJ IDEA 2020.1
 */
public class AdminUser implements Serializable {

    private String username;
    private String password;
    private String email;
    private int id;

    /**
     * Construct an User.
     * 
     * @param username admin user's username.
     * @param password admin user's password.
     * @param email    admin user's email
     */
    public AdminUser(String username, String password, String email, Integer adminID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = adminID;
    }

    /**
     * Get the admin user's username.
     * 
     * @return Admin user's username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the admin user's id.
     * 
     * @return Admin user's id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter for admin user's password
     * @return this admin user's password
     */
    public String getPassword() {return password;}
}
