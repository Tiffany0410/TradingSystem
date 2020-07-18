package Controllers.MainControllers;

import Controllers.AccountCreator;
import Controllers.LoginValidator;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;

import java.io.IOException;

/**
 * This class represent the whole system, control the whole system and communicate user with controller classes.
 *
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1.1
 */

public class TradingSystem {
   private Managers.UserManager.UserManager userManager;
   private DisplaySystem displaySystem;
   private TradeManager tradeManager;
   private MeetingManager meetingManager;
   private LoginValidator loginValidator;
   private AccountCreator accountCreator;
   private FilesReaderWriter filesReaderWriter;
   private RegularUserThresholdController tc;
   private SystemMessage sm;

   /**
    * constructor of trading system
    * need demo pass in several things
    */
   public TradingSystem(UserManager userManager, MeetingManager meetingManager, LoginValidator loginValidator,
                        TradeManager tradeManager, FilesReaderWriter filesReaderWriter, DisplaySystem displaySystem,
                        AccountCreator accountCreator) {
      this.userManager = userManager;
      this.displaySystem = displaySystem;
      this.meetingManager = meetingManager;
      this.loginValidator = loginValidator;
      this.tradeManager = tradeManager;
      this.filesReaderWriter = filesReaderWriter;
      this.accountCreator = accountCreator;
      this.sm = new SystemMessage();
   }


   /**
    * Initial trading system menu
    * @return false when user exit trading system, true when user not exit the system
    */
   public boolean tradingSystemInital() throws IOException {
      displaySystem.printOut("Welcome to the trading system");
      displaySystem.printOut(" ");

      int option;
      option = displaySystem.getMenuAnswer("./src/Menus/TradingSystemInitMenu.csv");

      // Option 0 is exit system
      if (option == 0){
         return false;
      }

      // Option 1 is login
      if (option == 1){
         this.login();
      }

      // Option 2 is create new account
      if (option == 2){
         boolean condition = false;

         while(!condition){
            condition = accountCreator.createAccount( "Regular");

            // If fail, give the reason why fail
            if (!condition){
               displaySystem.printOut("Username already exist, please try another one.");
            }
            displaySystem.printResult(condition);

         }
      }

      return true;
   }

   /**
    * Login to the trade system
    */

   private void login() throws IOException {
      String userName;
      String userPassword;

      // get the type of account
      userName = displaySystem.getUsername();
      userPassword = displaySystem.getPassword();
      String userType = loginValidator.verifyLogin(userName, userPassword);

      switch (userType) {
         case "False":
            displaySystem.failLogin();
            break;
         case "User":
            this.regularUserMain(userName);
            break;
         case "Admin":
            this.adminUserMain();
            break;
      }

   }

   /**
    * For log out this account
    */

   private void logOut() throws IOException{
      // serialize all managers before log out
      FilesReaderWriter.saveUserManagerToFile(userManager, "./src/Managers/SerializedUserManager.ser");
      FilesReaderWriter.saveTradeManagerToFile(tradeManager, "./src/Managers/SerializedTradeManager.ser");
      FilesReaderWriter.saveMeetingManagerToFile(meetingManager, "./src/Managers/SerializedMeetingManager.ser");
      this.displaySystem.printOut("Log out success.");

   }


   /**
    * For regular user menu
    */

   private void regularUserMain(String userName) throws IOException {
      RegularUserController regularUserController = new RegularUserController(this.displaySystem,
              this.filesReaderWriter, this.tradeManager, this.meetingManager, this.userManager, userName);
      // Initialize the threshold controller
      tc = new RegularUserThresholdController(displaySystem, tradeManager, meetingManager, userManager,
              userName, userManager.usernameToID(userName));
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(sm.RegUserAlerts(this.userManager, this.tc, this.filesReaderWriter, this.displaySystem,
              userName));

      int option;
      option = displaySystem.getMenuAnswer("./src/Menus/RegularUserMainMenu.csv");


      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      try {
         // Option 1 is Account Info
         if (option == 1) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./src/Menus/RegularUserAccountMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  regularUserController.actionResponse(option, suboption);
               }
            }
            this.regularUserMain(userName);
         }

         // Option 2 is Trading Info
         else if (option == 2) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./src/Menus/RegularUserTradingMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  regularUserController.actionResponse(option, suboption);
               }
            }
            this.regularUserMain(userName);
         }

         // Option 3 is Meeting Info
         else if (option == 3) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./src/Menus/RegularUserMeetingMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  regularUserController.actionResponse(option, suboption);
               }
            }
            this.regularUserMain(userName);
         }
      }catch (InvalidIdException ex){
         displaySystem.printOut("This user can not do this, Invalid ID");
      }
   }

   /**
    * For admin user menu
    */

   private void adminUserMain() throws IOException {
      AdminUserController adminUserController = new AdminUserController(this.accountCreator, this.displaySystem,
              this.filesReaderWriter, this.userManager);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(sm.AdminUserAlerts(filesReaderWriter));

      int option;
      option = displaySystem.getMenuAnswer("./src/Menus/AdminUserMainMenu.csv");

      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      // Option 1 is manage users
      if (option == 1){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./src/Menus/AdminUserManageUsersSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./src/Menus/AdminUserEditThresholdsSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 3 is other
      else if (option == 3){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./src/Menus/AdminUserOtherSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();

      }

   }

}
