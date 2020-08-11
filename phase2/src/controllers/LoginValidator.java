package controllers;

import java.io.Serializable;
import java.util.Map;

/**
 * A login validator of this system that stores username and password for users and administrative users.
 *
 *
 * @author Shi Tang, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1.1
 */
public class LoginValidator implements Serializable {
    private Map<String, String> userLoginInfo;
    private Map<String, String> adminUserLoginInfo;
    private String userType;
    private String userName;

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

    /**
     * Check whether this username exists
     * @param username The user name uer put in
     * @return true if this user name exists
     */

    public boolean checkUsername(String  username){
        if (userLoginInfo.containsKey(username)){
            this.userType = "User";
            this.userName = username;
            return true;
        } else if (adminUserLoginInfo.containsKey(username)){
            this.userType = "Admin";
            this.userName = username;
            return true;
        } else{
            return false;
        }
    }

    /**
     *  Check the password for this user put in
     * @param password password user put in
     * @return string "User" if and only if user is user with correct username and password; return string "Admin" if
     * and only if user is administrative user with correct username and password; otherwise, return "False".
     */

    public String checkPassword(String password){
        if (this.userType.equals("User")){
            if (userLoginInfo.get(this.userName).equals(password)) {
                return "User";
            }
        } else if (this.userType.equals("Admin")){
            if (adminUserLoginInfo.get(this.userName).equals(password)){
                return "Admin";
            }
        }
        return "False";
    }

}
