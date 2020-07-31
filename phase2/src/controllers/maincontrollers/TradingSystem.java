package controllers.maincontrollers;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.regularusersubcontrollers.RegularUserThresholdController;
import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
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
   private ActionManager actionManager;
   private FilesReaderWriter frw;
   private RegularUserThresholdController tc;
   private SystemMessage sm;
   private ItemManager itemManager;
   private FeedbackManager feedbackManager;
   private int option;

   /**
    * constructor of trading system
    * need demo pass in several things
    */
   public TradingSystem(UserManager userManager, MeetingManager meetingManager, LoginValidator loginValidator,
                        TradeManager tradeManager, DisplaySystem displaySystem,
                        AccountCreator accountCreator, ItemManager itemManager, ActionManager actionManager,
                        FeedbackManager feedbackManager) {
      this.userManager = userManager;
      this.displaySystem = displaySystem;
      this.meetingManager = meetingManager;
      this.loginValidator = loginValidator;
      this.tradeManager = tradeManager;
      this.accountCreator = accountCreator;
      this.actionManager = actionManager;
      this.sm = new SystemMessage();
      this.itemManager = itemManager;
      this.frw = new FilesReaderWriter();
      this.feedbackManager = feedbackManager;
   }


   /**
    * Initial trading system menu
    *
    * @return false when user exit trading system, true when user not exit the system
    */
   public boolean tradingSystemInital(int option){
      //displaySystem.printOut("Welcome to the trading system");
      //displaySystem.printOut(" ");

      //int option;
      //option = displaySystem.getMenuAnswer("./menus/TradingSystemInitMenu.csv");

      try {

         // Option 0 is exit system
         if (option == 0) {
            return false;
         }

         // Option 1 is login
         if (option == 1) {
            this.login();
         }

         // Option 2 is login as a guest
         if (option == 2) {
            // asGuest = true
            this.regularUserMain("Guest", true);
         }

         // Option 3 is create new account
         if (option == 3) {
            boolean condition = false;

            while (!condition) {
               condition = accountCreator.createAccount("Regular");

               // If fail, give the reason why fail
               if (!condition) {
                  displaySystem.printOut("Username already exist, please try another one.");
               }
               displaySystem.printResult(condition);

            }
         }
      } catch (IOException | InvalidIdException ex){
         displaySystem.printOut("Please try to clean the content of the files in Managers folder");
      }

      return true;
   }

   /**
    * Login to the trade system
    */

   private void login() throws IOException, InvalidIdException {
      String userName;
      String userPassword;

      // get the type of account
      userName = displaySystem.getUsername();
      if(loginValidator.checkUsername(userName)){

         userPassword = displaySystem.getPassword();
         String userType = loginValidator.checkPassword(userPassword);

         switch (userType) {
            case "False":
               displaySystem.printWrongPassword();
               break;
            case "User":
               // asGuest = false
               this.regularUserMain(userName, false);
               break;
            case "Admin":
               this.adminUserMain(userName);
               break;
         }
      }
      else{displaySystem.printWrongUsername();}

   }

   /**
    * For log out this account
    */

   private void logOut() throws IOException{
      // serialize all managers before log out
      frw.saveManagerToFile(userManager, "./configs/serializedmanagersfiles/SerializedUserManager.ser");
      frw.saveManagerToFile(tradeManager, "./configs/serializedmanagersfiles/SerializedTradeManager.ser");
      frw.saveManagerToFile(meetingManager, "./configs/serializedmanagersfiles/SerializedMeetingManager.ser");
      frw.saveManagerToFile(itemManager, "./configs/serializedmanagersfiles/SerializedMeetingManager.ser");
      frw.saveManagerToFile(feedbackManager, "./configs/serializedmanagersfiles/SerializedFeedbackManager.ser");
      this.displaySystem.printOut("Log out success.");

   }


   /**
    * For regular user menu
    */

   private void regularUserMain(String userName, boolean asGuest) throws IOException {
      RegularUserController regularUserController = new RegularUserController(this.displaySystem,
              this.tradeManager, this.meetingManager, this.userManager, this.itemManager, this.actionManager,
              this.feedbackManager, userName, asGuest);

      if (!asGuest) {
         // Initialize the threshold controller
         tc = new RegularUserThresholdController(displaySystem, tradeManager, meetingManager, userManager,
                 userName, userManager.usernameToID(userName));
         displaySystem.printOut("######### Notification ########");
         displaySystem.printOut(sm.RegUserAlerts(this.userManager, this.tc, this.frw, this.displaySystem,
                 userName, "./configs/thresholdvaluesfile/ThresholdValues.csv"));
      }

      int option;
      option = displaySystem.getMenuAnswer("./configs/menus/RegularUserMainMenu.csv");


      // Option 0 is log out
      if (option == 0) {
         this.logOut();
      }

      try {
         // Option 1 is Account Info
         if (option == 1) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./configs/menus/RegularUserAccountMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  regularUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");
               }
            }
            this.regularUserMain(userName, asGuest);
         }

         // Option 2 is Trading Info
         else if (option == 2) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./configs/menus/RegularUserTradingMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  if (asGuest) {
                     sm.msgForGuest(displaySystem);
                  } else {
                     regularUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");
                  }
               }
            }
            this.regularUserMain(userName, asGuest);
         }

         // Option 3 is Meeting Info
         else if (option == 3) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./configs/menus/RegularUserMeetingMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  if (asGuest) {
                     sm.msgForGuest(displaySystem);
                  } else {
                     regularUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");
                  }
               }
            }
            this.regularUserMain(userName, asGuest);
         }

         // Option 4 Searching Info
         else if (option == 4) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./configs/menus/RegularUserSearchingMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  if (asGuest) {
                     sm.msgForGuest(displaySystem);
                  } else {
                     regularUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");
                  }
               }
            }
            this.regularUserMain(userName, asGuest);
         }

         // Option 5 Community Info
         else if (option == 5) {
            boolean condition = true;
            while (condition) {
               int suboption = displaySystem.getMenuAnswer("./configs/menus/RegularUserCommunityMenu.csv");
               if (suboption == 0) {
                  condition = false;
               } else {
                  if (asGuest) {
                     sm.msgForGuest(displaySystem);
                  } else {
                     regularUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");
                  }
               }
            }
            this.regularUserMain(userName, asGuest);
         }
      }
      catch (InvalidIdException ex){
         displaySystem.printOut("This user can not do this, Invalid ID");
      }
   }

   /**
    * For admin user menu
    */

   private void adminUserMain(String userName) throws IOException, InvalidIdException {
      AdminUserController adminUserController = new AdminUserController(this.accountCreator, this.displaySystem,
              this.userManager, this.itemManager, this.feedbackManager, this.actionManager, userName);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(sm.AdminUserAlerts(frw));

      int option;
      option = displaySystem.getMenuAnswer("./configs/menus/AdminUserMainMenu.csv");

      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      // Option 1 is manage users
      if (option == 1){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./configs/menus/AdminUserManageUsersSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");}
         }
         this.adminUserMain(userName);
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./configs/menus/AdminUserEditThresholdsSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");}
         }
         this.adminUserMain(userName);
      }

      // Option 3 is other
      else if (option == 3){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("./configs/menus/AdminUserOtherSubMenu.csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption, "./configs/thresholdvaluesfile/ThresholdValues.csv");}
         }
         this.adminUserMain(userName);

      }

   }

}
