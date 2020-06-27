package bookTradeSystem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A login validator of this system that stores username and password for users and administrative users.
 */
public class LoginValidator implements Serializable {
    private Map<String, String> userLoginInfo;
    private Map<String, String> adminUserLoginInfo;

    /** Constructor of LoginValidator
     * Set this userLoginInfo with userLoginInfo, set this adminUserLoginInfo with adminUserLoginInfo
     * @param userLoginInfo A map that stores user's username as key and maps it to user's password as value
     * @param adminUserLoginInfo A map that stores administrative user's username as key and maps it to user's password
     *                           as value
     */
    public LoginValidator(Map<String, String> userLoginInfo, Map<String, String> adminUserLoginInfo){
        this.userLoginInfo = userLoginInfo;
        this.adminUserLoginInfo = adminUserLoginInfo;
    }

    /** Verify user's login with username and password
     * @param username The user's username
     * @param password The user's password
     * @return string "User" if and only if user is user with correct username and password; return string "Admin" if
     * and only if user is administrative user with correct username and password; otherwise, return "False".
     */
    public String verifyLogin(String username, String password){
        if (userLoginInfo.containsKey(username)){
            if (userLoginInfo.get(username).equals(password)) {
                return "User";
            }
        }
        if (adminUserLoginInfo.containsKey(username)) {
            if (adminUserLoginInfo.get(username).equals(password)){
                return "Admin";
            }
        }
        return "False";
    }
}
