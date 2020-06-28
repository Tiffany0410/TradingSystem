package bookTradeSystem;

public class AdminUser {
    /**
     * username is the user's username. password is the user's password. email is
     * the user's email.
     */
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
    User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
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
}
