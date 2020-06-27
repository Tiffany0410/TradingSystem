package bookTradeSystem;


import com.sun.xml.internal.bind.v2.TODO;

public class TradingSystem {
   private UserManager userManager;
   private TradingSystem tradingSystem;
   private DisplaySystem displaySystem;
   private MeetingManager meetingManager;
   private FileWriter fileWriter;
   private LoginValidator loginValidator;
   private AccountCreator accountCreator;

   /**
    * Initial trading system
    */
   public TradingSystem(){
      int option;
      option = displaySystem.getMenuAnswer("TradingSystemInitMenu");

      // Option 1 is login
      if (option == 1){
         this.Login();
      }

      // Option 2 is create new account
      // call controller to do actions
      if (option == 2){ }

      // Option 0 is exit
      // call controller to do actions
      if(option == 0) { }
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
      type = loginValidator(userName, userPassword );

      if (type.equals("fail")){
         displaySystem.failLogin();
      }
      else if (type.equals("user")){
         // pass in username or user id?
         this.regularUserMain(userName);
      }
      else if (type.equals("admin")){
         this.adminUserMain(userName);
      }

   }

   /**
    * For log out this account
    */

   public void logOut(){
      // TODO: implement this method
   }

   /**
    * Return the notification message for the user passed in
    * @param userName
    * @return message
    */
   public String sendNotification(String userName){
      String message;
      // TODO: get the notification for the user passed in

      return message;
   }

   /**
    * For regular user main menu
    */

   public void regularUserMain(String userName){
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(this.sendNotification(userName));

      int option;
      option = displaySystem.getMenuAnswer("RegularUserMainMenu");



      // Option 1 is Account Info
      if (option == 1){
         this.regularUserAccountInfo(userName);
      }

      // Option 2 is Trading Info
      else if (option == 2){
         this.regularUserTradingInfo(userName);
      }

      // Option 3 is Meeting Info
      else if (option == 3){
         this.regularUserMeetingInfo(userName);
      }

      // Option 0 is log out
      else if (option == 0){
         this.logOut();
      }
   }

   /**
    * For regular user account info menu
    */
   private void regularUserAccountInfo(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("RegularUserAccountMenu");

      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 2){}
      else if (option == 3){}
      else if (option == 4){}
      else if (option == 5){}
      else if (option == 6){}
      else if (option == 7){}
      else if (option == 8){}
      else if (option == 0){
         this.regularUserMain(userName);
      }

   }

   /**
    * For regular user trading Info menu
    */

   private void regularUserTradingInfo(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("RegularUserTradingMenu");
      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 2){}
      else if (option == 3){}
      else if (option == 4){}
      else if (option == 5){}
      else if (option == 6){}
      else if (option == 7){}
      else if (option == 0){
         this.regularUserMain(userName);
      }
   }

   /**
    * For regular user meeting Info menu
    */

   private void regularUserMeetingInfo(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("RegularUserMeetingMenu");

      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 2){}
      else if (option == 3){}
      else if (option == 4){}
      else if (option == 5){}
      else if (option == 6){}
      else if (option == 7){}
      else if (option == 0){
         this.regularUserMain(userName);
      }

   }

   /**
    * For admin user main menu
    */

   private void adminUserMain(String userName) {
      displaySystem.printOut("######### Notification ########");
      displaySystem.printOut(this.sendNotification(userName));

      int option;
      option = displaySystem.getMenuAnswer("AdminUserMainMenu");

      // Option 1 is manage users
      if (option == 1){
         this.adminManageUsers(userName);
      }

      // Option 2 is Edit Thresholds
      else if (option == 2){
         this.adminEditThresholds(userName);
      }

      // Option 3 is other
      else if (option == 3){
         this.adminOther(userName);
      }

      // Option 0 is log out
      else if (option == 0){
         this.logOut();
      }

   }

   /**
    * For admin user manage users menu
    */

   private void adminManageUsers(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("AdminUserManageUsersSubMenu");

      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 2){}
      else if (option == 3){}
      else if (option == 0){
         this.adminUserMain(userName);
      }
   }

   /**
    * For admin user edit thresholds
    */
   private void adminEditThresholds(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("AdminUserEditThresholdsSubMenu");

      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 2){}
      else if (option == 3){}
      else if (option == 4){}
      else if (option == 0){
         this.adminUserMain(userName);
      }
   }

   /**
    * For admin user other options
    */
   private void adminOther(String userName) {
      int option;
      option = displaySystem.getMenuAnswer("AdminUserOtherSubMenu");

      // TODO: Implement relevant method

      if (option == 1){}
      else if (option == 0){
         this.adminUserMain(userName);
      }
   }



}
