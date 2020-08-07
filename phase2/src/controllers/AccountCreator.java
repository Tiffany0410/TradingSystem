package controllers;

import gateway.FilesReaderWriter;
import managers.usermanager.TradableUser;
import managers.usermanager.UserManager;
import controllers.maincontrollers.DisplaySystem;

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
    private FilesReaderWriter frw;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds){
        this.um = um;
        this.frw = new FilesReaderWriter();
    }

    /**
     * Creates and adds a new User
     * @param type The type of account: normal user or admin
     * @return true if the User was successfully added, false otherwise
     */
    public boolean createAccount(String type, String username, String password, String email, String home){
        boolean out = false;
        HashMap<String, String> userInfo = um.userPasswords();
        HashMap<String, String> adminInfo = um.adminPasswords();
        ArrayList<TradableUser> listPeople = um.getListTradableUser();



        if (username.toLowerCase().equals("guest")) {
            return out;
        }
        if (type.equals("Regular")) {
            if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)) {
                um.addUser(username, password, email, home);
                out = true;

                try {
                    //Write the UserManger into ser file in order to save the data
                    frw.saveManagerToFile(um, "./configs/serializedmanagersfiles/SerializedUserManager.ser");
                    frw.saveUserInfoToCSVFile("./configs/secureinfofiles/RegularUserUsernameAndPassword.csv",
                            username, password, email);
                } catch (IOException e){
                    System.out.println("Please check regular user information files");
                }
            }
        }

       else if (type.equals("Admin")) {
           if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)) {
               um.addAdmin(username, password, email);
               out = true;

               try {
                   //Write the UserManger into ser file in order to save the data
                   frw.saveManagerToFile(um, "./configs/serializedmanagersfiles/SerializedUserManager.ser");
                   frw.saveUserInfoToCSVFile("./configs/secureinfofiles/AdminUserUsernameAndPassword.csv",
                           username, password, email);
               } catch (IOException e){
                   System.out.println("Please check admin user information files");
               }
           }
        }

        return out;
    }
}
