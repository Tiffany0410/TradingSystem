package controllers.maincontrollers;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.regularusersubcontrollers.RegularUserThresholdController;
import gateway.FilesReaderWriter;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;
import exception.InvalidIdException;

import java.io.IOException;

/**
 * This class represent the whole system, control the whole system and communicate user with controller classes.
 *
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1.1
 */

public class TradingSystem {
   private UserManager userManager;
   private DisplaySystem displaySystem;
   private TradeManager tradeManager;
   private MeetingManager meetingManager;
   private LoginValidator loginValidator;
   private AccountCreator accountCreator;
   private FilesReaderWriter frw;
   private RegularUserThresholdController tc;
   private SystemMessage sm;
   private ItemManager itemManager;
   private FeedbackManager feedbackManager;

   /**
    * constructor of trading system
    * need demo pass in several things
    */
   public TradingSystem(UserManager userManager, MeetingManager meetingManager, LoginValidator loginValidator,
                        TradeManager tradeManager, DisplaySystem displaySystem,
                        AccountCreator accountCreator, ItemManager itemManager, FeedbackManager feedbackManager)
           throws IOException, ClassNotFoundException {
      this.userManager = userManager;
      this.displaySystem = displaySystem;
      this.meetingManager = meetingManager;
      this.loginValidator = loginValidator;
      this.tradeManager = tradeManager;
      this.accountCreator = accountCreator;
      this.sm = new SystemMessage();
      this.itemManager = itemManager;
      this.frw = new FilesReaderWriter();
      this.feedbackManager = feedbackManager;
   }


   /**
    * Initial trading system menu
    * @return false when user exit trading system, true when user not exit the system
    */
   public boolean tradingSystemInital() throws IOException, ClassNotFoundException {
      displaySystem.printOut("Welcome to the trading system");
      displaySystem.printOut(" ");

      int option;
      option = displaySystem.getMenuAnswer("./menus/TradingSystemInitMenu.csv");

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

   private void login() throws IOException, ClassNotFoundException {
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
      frw.saveManagerToFile(userManager, "./src/managers/usermanager/SerializedUserManager.ser");
      frw.saveManagerToFile(tradeManager, "./src/managers/trademanager/SerializedTradeManager.ser");
      frw.saveManagerToFile(meetingManager, "./src/managers/meetingmanager/SerializedMeetingManager.ser");
      frw.saveManagerToFile(itemManager, "./src/managers/itemmanager/SerializedMeetingManager.ser");
      frw.saveManagerToFile(feedbackManager, "./src/managers/feedbackmanager/SerializedFeedbackManager.ser");
      this.displaySystem.printOut("Log out success.");

   }


   /**
    * For regular user menu
    */

   private void regularUserMain(String userName) throws IOException, ClassNotFoundException {
      RegularUserController regularUserController = new RegularUserController(this.displaySystem,
              this.tradeManager, this.meetingManager, this.userManager, this.itemManager,
              userName);
      // Initialize the threshold controller
      tc = new RegularUserThresholdController(displaySystem, tradeManager, meetingManager, userManager,
              userName, userManager.usernameToID(userName));
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(sm.RegUserAlerts(this.userManager, this.tc, this.frw, this.displaySystem,
              userName));

      int option;
      option = displaySystem.getMenuAnswer("./menus/RegularUserMainMenu.csv");


      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      try {
         // Option 1 is Account Info
         if (option == 1) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./menus/RegularUserAccountMenu.csv");
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
               int suboption = displaySystem.getMenuAnswer("./menus/RegularUserTradingMenu.csv");
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
               int suboption = displaySystem.getMenuAnswer("./menus/RegularUserMeetingMenu.csv");
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

   private void adminUserMain() throws IOException, ClassNotFoundException {
      AdminUserController adminUserController = new AdminUserController(this.accountCreator, this.displaySystem,
              this.userManager, this.itemManager);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(sm.AdminUserAlerts(frw));

      int option;
      option = displaySystem.getMenuAnswer("./menus/AdminUserMainMenu.csv");

      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      // Option 1 is manage users
      if (option == 1){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./menus/AdminUserManageUsersSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./menus/AdminUserEditThresholdsSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 3 is other
      else if (option == 3){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./menus/AdminUserOtherSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();

      }

   }

}
