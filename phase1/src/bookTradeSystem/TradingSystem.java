package bookTradeSystem;

import java.io.FileNotFoundException;

public class TradingSystem {
   private UserManager userManager;
   private DisplaySystem displaySystem;
   private TradeManager tradeManager;
   private MeetingManager meetingManager;
   private LoginValidator loginValidator;
   private AccountCreator accountCreator;
   private FilesReaderWriter filesReaderWriter;

   /**
    * constructor of trading system
    * need demo pass in several things
    */
   public TradingSystem(UserManager userManager, MeetingManager meetingManager, LoginValidator loginValidator,
                        TradeManager tradeManager, FilesReaderWriter filesReaderWriter,DisplaySystem displaySystem,
                        AccountCreator accountCreator) {
      this.userManager = userManager;
      this.displaySystem = displaySystem;
      this.meetingManager = meetingManager;
      this.loginValidator = loginValidator;
      this.tradeManager = tradeManager;
      this.filesReaderWriter = filesReaderWriter;
      this.accountCreator = accountCreator;
   }


   /**
    * Initial trading system menu
    * @return false when user exit trading system, true when user not exit the system
    */
   public boolean tradingSystemInital() throws FileNotFoundException, InvalidIdException {
      displaySystem.printOut("Welcome to book trading system");

      int option;
      option = displaySystem.getMenuAnswer("Menus/TradingSystemInitMenu.csv");

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

   private void login() throws FileNotFoundException, InvalidIdException {
      String userName;
      String userPassword;

      // get the type of account
      userName = displaySystem.getUsername();
      userPassword = displaySystem.getPassword();
      String userType = loginValidator.verifyLogin(userName, userPassword);

      switch (userType) {
         case "fail":
            displaySystem.failLogin();
            this.tradingSystemInital();
            break;
         case "user":
            this.regularUserMain(userName);
            break;
         case "admin":
            this.adminUserMain();
            break;
      }

   }

   /**
    * For log out this account
    */

   private void logOut(){
      this.displaySystem.printOut("Log out success.");
   }


   /**
    * For regular user menu
    */

   private void regularUserMain(String userName) throws FileNotFoundException, InvalidIdException {
      RegularUserController regularUserController = new RegularUserController(this.displaySystem,
              this.filesReaderWriter, this.tradeManager, this.meetingManager, this.userManager, userName);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(regularUserController.alerts());

      int option;
      option = displaySystem.getMenuAnswer("Menus/RegularUserMainMenu.csv");


      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      // Option 1 is Account Info
      if (option == 1){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/RegularUserAccountMenu,csv");
            if (suboption == 0) { condition = false; }
            else{regularUserController.actionResponse(option, suboption);}
         }
         this.regularUserMain(userName);
      }

      // Option 2 is Trading Info
      else if (option == 2){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/RegularUserTradingMenu,csv");
            if (suboption == 0) { condition = false; }
            else{regularUserController.actionResponse(option, suboption);}
         }
         this.regularUserMain(userName);
      }

      // Option 3 is Meeting Info
      else if (option == 3){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/RegularUserMeetingMenu,csv");
            if (suboption == 0) { condition = false; }
            else{regularUserController.actionResponse(option, suboption);}
         }
         this.regularUserMain(userName);
      }
   }

   /**
    * For admin user menu
    */

   private void adminUserMain() throws FileNotFoundException {
      AdminUserController adminUserController = new AdminUserController(this.accountCreator, this.displaySystem,
              this.filesReaderWriter, this.userManager);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(adminUserController.alerts());

      int option;
      option = displaySystem.getMenuAnswer("Menus/AdminUserMainMenu.csv");

      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      // Option 1 is manage users
      if (option == 1){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/AdminUserManageUsersSubMenu,csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/AdminUserEditThresholdsSubMenu,csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();
      }

      // Option 3 is other
      else if (option == 3){
         boolean condition = true;
         while(condition) {
            int suboption = displaySystem.getMenuAnswer("Menus/AdminUserOtherSubMenu,csv");
            if (suboption == 0) { condition = false; }
            else{adminUserController.actionResponse(option, suboption);}
         }
         this.adminUserMain();

      }

   }

}
