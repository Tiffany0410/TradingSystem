package controllers;

import gateway.FilesReaderWriter;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to create a new User account
 * @author Gabriel
 * @version IntelliJ IDEA 2020.1
 */
public class AccountCreator {
    private UserManager um;
    private DisplaySystem ds;
    private FilesReaderWriter frw;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds) throws IOException, ClassNotFoundException {
        this.um = um;
        this.ds = ds;
        this.frw = new FilesReaderWriter();
    }

    /**
     * Creates and adds a new User
     * @param type The type of account: normal user or admin
     * @return true if the User was successfully added, false otherwise
     */
    public boolean createAccount(String type) throws IOException {
        boolean out = false;
        HashMap<String, String> userInfo = um.userPasswords();
        HashMap<String, String> adminInfo = um.adminPasswords();
        ArrayList<managers.usermanager.User> listPeople = um.getListUser();

        String username;
        String password;
        String email;


        username = ds.getUsername();
        password = ds.getPassword();
        email = ds.getEmail();

        if (username.toLowerCase().equals("guest")){
            return out;
        }

        if (type.equals("Regular")) {
            if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)) {
                um.addUser(username, password, email);
                out = true;
                //Write the UserManger into ser file in order to save the data
                frw.saveManagerToFile(um, "./src/managers/usermanager/SerializedUserManager.ser");
                frw.saveUserInfoToCSVFile("./src/managers/otherfiles/RegularUserUsernameAndPassword.csv",
                        username, password, email);
            }
        }

       else if (type.equals("Admin")) {
           if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)){
               um.addAdmin(username, password, email);
               out = true;
               //Write the UserManger into ser file in order to save the data
               frw.saveManagerToFile(um, "./src/managers/usermanager/SerializedUserManager.ser");
               frw.saveUserInfoToCSVFile("./src/managers/otherfiles/AdminUserUsernameAndPassword.csv",
                       username, password, email);
            }
        }

        return out;
    }
}