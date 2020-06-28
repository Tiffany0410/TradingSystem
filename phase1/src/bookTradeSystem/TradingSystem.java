package bookTradeSystem;


import com.sun.xml.internal.bind.v2.TODO;

public class TradingSystem {
   private UserManager userManager;
   private DisplaySystem displaySystem;
   private TradeManager tradeManager;
   private MeetingManager meetingManager;
   private LoginValidator loginValidator;
   private AccountCreator accountCreator;
   private RegularUserController regularUserController;
   private AdminUserController adminUserController;

   /**
    * constructor of trading system
    */
   public TradingSystem(UserManager userManager, MeetingManager meetingManager, LoginValidator loginValidator, TradeManager tradeManager){
      this.userManager = userManager;
      this.displaySystem = new DisplaySystem();
      this.meetingManager = meetingManager;
      this.loginValidator = loginValidator;
      this.tradeManager = tradeManager;
      this.accountCreator = new AccountCreator(this.userManager, this.displaySystem);
      this.tradingSystemInital();
   }


   /**
    * Initial trading system menu
    */
   public void tradingSystemInital(){
      int option;
      option = displaySystem.getMenuAnswer("TradingSystemInitMenu");

      // Option 1 is login
      if (option == 1){
         this.Login();
      }

      // Option 2 is create new account
      if (option == 2){
         boolean condition = false;

         while(!condition){
            condition = accountCreator.createAccount(displaySystem.getUsername(), displaySystem.getPassword(),
                    displaySystem.getEmail());
         }
      }

   }

   /**
    * Login to the trade system
    */

   public void Login() {
      String type;
      String userName;
      String userPassword;

      // get the type of account
      userName = displaySystem.getUsername();
      userPassword = displaySystem.getPassword();
      type = loginValidator.verifyLogin(userName, userPassword );

      switch (type) {
         case "fail":
            displaySystem.failLogin();
            break;
         case "user":
            this.regularUserMain(userName);
            break;
         case "admin":
            this.adminUserMain(userName);
            break;
      }

   }

   /**
    * For log out this account
    */

   public void logOut(){
      // TODO: serialize what?

      this.tradingSystemInital();
   }

   /**
    * Return the notification message for the user passed in
    * @param userName
    * @return message
    */
   public String sendNotification(String userName){
      return displaySystem.getNotification(userName);
   }

   /**
    * For regular user menu
    */

   public void regularUserMain(String userName){
      this.regularUserController = new RegularUserController(this.tradeManager, this.meetingManager, this.userManager, userName);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(this.sendNotification(userName));

      int option;
      option = displaySystem.getMenuAnswer("RegularUserMainMenu");


      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      int suboption = 0;
      // Option 1 is Account Info
      if (option == 1){
         suboption = displaySystem.getMenuAnswer("RegularUserAccountMenu");
      }

      // Option 2 is Trading Info
      else if (option == 2){
         suboption = displaySystem.getMenuAnswer("RegularUserTradingMenu");
      }

      // Option 3 is Meeting Info
      else if (option == 3){
         suboption = displaySystem.getMenuAnswer("RegularUserMeetingMenu");
      }

      if (suboption == 0){
         this.regularUserMain(userName);
      }else{
         regularUserController.actionResponse(option, suboption);
      }


   }


   /**
    * For admin user menu
    */

   private void adminUserMain(String userName) {
      this.adminUserController = new AdminUserController(this.tradeManager, this.meetingManager, this.userManager, userName);
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(this.sendNotification(userName));

      int option;
      option = displaySystem.getMenuAnswer("AdminUserMainMenu");

      // Option 0 is log out
      if (option == 0){
         this.logOut();
      }

      int suboption = 0;
      // Option 1 is manage users
      if (option == 1){
         suboption = displaySystem.getMenuAnswer("AdminUserManageUsersSubMenu");
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         suboption = displaySystem.getMenuAnswer("AdminUserEditThresholdsSubMenu");
      }

      // Option 3 is other
      else if (option == 3){
         suboption = displaySystem.getMenuAnswer("AdminUserOtherSubMenu");
      }

      if (suboption == 0){
         this.adminUserMain(userName);
      }else{
         adminUserController.actionResponse(option, suboption);
      }
   }

}
