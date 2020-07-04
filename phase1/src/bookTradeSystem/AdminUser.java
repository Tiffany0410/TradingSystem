package bookTradeSystem;

import java.io.Serializable;

public class AdminUser implements Serializable {
    /**
     * username is the user's username. password is the user's password. email is
     * the user's email.
     */
    private String username;
    private String password;
    private String email;
    private int id;
    private static int idNumber = 1;

    /**
     * Construct an User.
     * 
     * @param username user's username.
     * @param password user's password.
     * @param email    user's email
     */
    public AdminUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        id = idNumber;
        idNumber ++;
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
