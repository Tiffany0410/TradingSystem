package Controllers;

import Gateway.FilesReaderWriter;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;

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
    private FilesReaderWriter fr;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds, FilesReaderWriter fr){
        this.um = um;
        this.ds = ds;
        this.fr = fr;
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
        ArrayList<Managers.UserManager.User> listPeople = um.getListUser();

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
                FilesReaderWriter.saveUserManagerToFile(um, "./src/Managers/SerializedUserManager.ser");
                fr.saveUserInfoToCSVFile("./src/Managers/RegularUserUsernameAndPassword.csv", username, password, email);
            }
        }

       else if (type.equals("Admin")) {
           if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)){
               um.addAdmin(username, password, email);
               out = true;
               //Write the UserManger into ser file in order to save the data
               FilesReaderWriter.saveUserManagerToFile(um, "./src/Managers/SerializedUserManager.ser");
               fr.saveUserInfoToCSVFile("./src/Managers/AdminUserUsernameAndPassword.csv", username, password, email);
            }
        }

        return out;
    }
}
