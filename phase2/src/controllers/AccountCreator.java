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
    private String regularUserFilePath;
    private String adminUserFilePath;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     */
    public AccountCreator(UserManager um, String regularUserFilePath, String adminUserFilePath){
        this.um = um;
        this.frw = new FilesReaderWriter();
        this.regularUserFilePath = regularUserFilePath;
        this.adminUserFilePath = adminUserFilePath;
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
            this.userInfoFilePath = this.regularUserFilePath;
        }else{
            this.userInfoFilePath = this.adminUserFilePath;
        }

        try {
            if (!userInfo.containsKey(username) && !adminInfo.containsKey(username)) {
                um.addUser(username, password, email, home);
                out = true;


                    //Write the UserManger into ser file in order to save the data
                    frw.saveManagerToFile(um, this.userInfoFilePath);
                    frw.saveUserInfoToCSVFile(this.userInfoFilePath, username, password, email);

                }
            } catch (IOException e){
                String info = "Please check " + type +" user information files";
                NotificationGUI notificationGUI = new NotificationGUI(info);
                notificationGUI.run(info);
        }

        return out;
    }
}
