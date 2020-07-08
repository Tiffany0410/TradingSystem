1) Our Project Name:
    Trading System

2) Description Of Our Project:

    About:

    This trading system will allow users to create a new account, login in and look for items to trade.
    Users can make a one-way trade, where they lend someone item or borrow an item.
    Users can also make a two-way trade where both users borrow and lend an item.
    Each trade can be permanent or temporary. With a permanent trade, only one meeting will be created
    where the user gives away an item or borrows an item permanently. With a temporary trade, two meetings
    will be created where the second exchange will happen exactly one month after the first meeting in the
    same location. Users can edit or confirm the time and place to meet in real life to trade items.


    Key feature of trading system main menu:
       1. Login:
          By type in the user name and password, user can login to their account. Regular user will get into regular user
          main menu and admin user get into admin user main menu.

       2. Create new account:
          User can use this option to create new regular user account by give username, password and email address. user
          can not create an account which username already exist in the system. Can not create admin use account by this.

       0. Exit system:
          Exit this system and end the program.


    Key feature of regular user main menu:
        Regular user can see the state and the notification of this account at the top

        1. AccountInfo:
           User get into regular user account menu.

        2. TradingInfo:
           User get into regular user trading menu.

        3. MeetingInfo:
           User get into regular user meeting menu.

        0. Logout
          User can log out this account and back into the trading system main menu.


    Key feature of admin user main menu:
        1. ManageUsers:
           Admin user get into admin user manage users sub menu to manage regular users.

        2. EditThresholds:
           Admin user get into admin user edit thresholds sub menu to manage user's thresholds.

        3. Other:
           Admin user get into admin user other sub menu to do other things.

        0. Logout:
           Admin user can log out this account and get back into trading system main menu.


    Key feature of admin user manage user menu:
        1. Freeze user:
           admin user can freeze a user from trading if admin user think this user violate the rule of trading by typing the
           user name of the user
        2. Unfreeze user:
           admin user can see the list of user request unfreeze and decide to unfreeze which user by typing the username of
           the user
        3. Confirm and add item to user's inventory:
           admin user can see the request of user want to add item into their inventory and approve this request
        0. Exit menu:
           return to admin user main menu.
















3) Things you should know before running this program:

    - To run our program properly, make phase 1 as the root or change the build configurations (add the “\phase1” to
      the end of the working directory)

    - For first time running the program, please clear all content of the files in Manager folder except
      AdminUserUsernameAndPassword.csv

    - The default admin user account is: username: adminTest, password: adminTest

    - You can create a regular user by going to the main menu create account option.
      See “RegularUserUsernameAndPassword.csv” for the users that already exist in the system and
      for the username, password, and email information about the user you created

    - you can add an admin user to the system by logging in as an admin user (see “AdminUserUsernameAndPassword.csv”)
      for username and passwords and going to the Others submenu where you should see the option to add another admin
      user

    - In “RegularUserUsernameAndPassword.csv” or “AdminUserUsernameAndPassword.csv”, each line is in the following format
      and corresponds to one user: username, password, email

    - In some places of our program that asks for input(ex. associates with ids or other information), it will only stop
    until you enter the correct/valid input. We will improve on that for phase 2. But for now, please bear with it.



