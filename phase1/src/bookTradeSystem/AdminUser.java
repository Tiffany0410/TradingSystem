package bookTradeSystem;

import java.io.Serializable;

/**
 * An instance of this class represents an admin user in this system.
 *
 * @author
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
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public AdminUser(String username, String password, String email, Integer adminID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = adminID;
    }

    /**
     * Get the user's email.
     * 
     * @return the email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Get the user's username.
     * 
     * @return username.
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Get the user's id.
     * 
     * @return id.
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
