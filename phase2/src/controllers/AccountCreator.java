package controllers;

import gateway.FilesReaderWriter;
import gui.GUIDemo;
import gui.NotificationGUI;
import managers.usermanager.UserManager;


import java.io.IOException;
import java.util.HashMap;

/**
 * Used to create a new User account
 * @author Gabriel, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class AccountCreator {
    private UserManager um;
    private FilesReaderWriter frw;
    private String userInfoFilePath;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     */
    public AccountCreator(UserManager um){
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

        if (type.equalsIgnoreCase("Regular")) {
            this.userInfoFilePath = "./configs/secureinfofiles/RegularUserUsernameAndPassword.csv";
        }else{
            this.userInfoFilePath = "./configs/secureinfofiles/AdminUserUsernameAndPassword.csv";
        }

            if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)) {
                um.addUser(username, password, email, home);
                out = true;

                try {
                    //Write the UserManger into ser file in order to save the data
                    frw.saveManagerToFile(um, "./configs/serializedmanagersfiles/SerializedUserManager.ser");
                    frw.saveUserInfoToCSVFile(this.userInfoFilePath, username, password, email);
                } catch (IOException e){
                    String info = "Please check " + type +" user information files";
                    NotificationGUI notificationGUI = new NotificationGUI(info);
                    notificationGUI.run(info);
                }
            }

        return out;
    }
}
