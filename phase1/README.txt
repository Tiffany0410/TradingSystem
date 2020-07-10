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

    There exist two different menus for Admin User and Regular User. After login with correct username and password,
    AdminUsers have one main menu <AdminUserMainMenu> and three sub menus <AdminUserManageUsersSubMenu>,
    <AdminUserManageEditThresholdsSubMenu> and <AdminUserOtherSubMenu>.
    RegularUsers have one main menu <RegularUserMainMenu> and three sub menus <RegularUserAccountMenu>,
    <RegularUserTradingMenu> and <RegularUserMeetingMenu>.

    Key features of AdminUserMainMenu:
        1.ManageUsers
        Enter the sub menu <AdminUserManageUsersSubMenu>

        2.EditThresholds
        Enter the sub menu <AdminUserEditThresholdsSubMenu>

        3.Other
        Enter the sub menu <AdminUserOtherSubMenu>

        0.Logout


    >>Key features of AdminUserManageUsersSubMenu:
        1.Freeze user:
        Admin user can freeze a user from trading if admin user think this user violate the rule of trading
        by typing the user name of the user

        2.Unfreeze user:
        Admin user can see the list of user request unfreeze and decide to unfreeze which user by typing the username of
        the user

        3.Confirm and add item to user's inventory:
        Admin user can see the request of user want to add item into their inventory and approve this request

        0.Exit menu:
        Return to admin user main menu <AdminUserMainMenu.csv>.


    >>Key features of AdminUserEditThresholdsSubMenu:
        This menu has four options, which allows the admin user to edit the four threshold values in our system.

        1. the maximum number of transactions allowed a week

        2. the max number of transactions that can be incomplete before the account is frozen

        3. the number of books users must lend before users can borrow

        4. the max edits per user for meeting’s date + time


    >>Key features of AdminUserOtherSubMenu
        1.Add subsequent admin users:
        Create a new AdminUser by entering the username, password and email address.
        (the info of this new AdminUser will be saved into AdminUserUsernameAndPassword.csv
        and as part of UserManager be serialized into SerializedUserManager.ser)

        0.Exit menu:
        Return to admin user main menu <AdminUserMainMenu.csv>.


    Key features of RegularUserMainMenu:
        1.AccountInfo
        Enter the sub menu <RegularUserAccountMenu>

        2.TradingInfo
        Enter the sub menu <RegularUserTradingMenu>

        3.MeetingInfo
        Enter the sub menu <RegularUserMeetingMenu>

        0.Logout


    >>Key features of RegularUserAccountMenu
        1.Browse all the items in other users inventories:
        Prints a list of all the items in all the other users’ inventories.

        2.Add to own Wish List
        Enter the id of the item you want to add. The item will be added to your wishlist if it is not already there.

        3.Search item
        Enter part of the name of the item. Prints all the items with whose name contains the input.

        4.Remove from own Wish List
        Enter the id of the item you want to remove. The item will be removed from your wishlist if it is there.

        5.Remove from own Inventory
        Prints a list of all the items in your inventory. Enter the number in the list of the item
        that you want to remove. The item will be removed from your inventory.

        6.Request to unfreeze account
        Enter a message to tell an AdminUser and type OK to finish. A request with the message will be sent to an
        AdminUser to review.

        7.Request that an item be added to your inventory
        Enter the name and description of the item. A request will be sent to an AdminUser to review.

        8.View your wish list or inventory
        Prints all the items in your wishlist and all the items in your inventory.

        9.Exit
        Return to regular user main menu <RegularUserMainMenu.csv>.

    >>Key features of RegularUserTradingMenu:
        1.Request a trade:
        You can request a trade to another user, first step you need to choose what kind of trade you wanna do ( input 1 is one way trade and 2 is two way trade)
        Next enter the user id(input user1/(borrower) id first and user2 next)
        Next enter the id of the item you want to trade.

        2.Respond to trade requests:
        Respond a waited trade sent by others.

        3.View open trades:
        You can view all your trades with status open

        4.View closed trades:
        You can view all your trades with status closed

        5.Confirm that a trade has been completed:
        You can confirm that a trade has been completed or not.

        6.See top three most frequent trading partners:
        You can see the top three most frequent trading partners with you.

        7.View transactions that have been cancelled:
        You can view transactions you made that have been cancelled.

        8.Exit:
        Return to regular user main menu <RegularUserMainMenu.csv>.

    >>Key features of RegularUserMeetingMenu:

        The first meeting of the trade is created by the system automatically if both users agree to the trade.
        The second meeting is created by the system if the trade is “Temporary”, and the first meeting is complete.
        The second meeting is one month after the first meeting and in the same location. Users can not edit and
        confirm the time and place for the second meeting, but they have to confirm the completeness of the meeting.

        1. Suggest/edit time and place for meetings:
	    By entering the trade id and meeting number to choose the meeting you want to edit, then you can edit the
	    meeting time and place by inputting the valid time and place according to the instruction(We allow you to set
	    meeting time that is before current time for the reason to test easily, see the details in “The meeting time
	    thing” at the end of this text). The user only can edit successfully if it is the user’s turn and the times
	    of editing does not reach the threshold.

        2. Confirm time and place for meetings:
        You can confirm the meeting time and place if it is your turn and the times of editing does not reach the threshold.

        3. Confirm the meeting took place:
        You can confirm the meeting took place if the meeting time is before the current time.

        4. see the list of meetings that need to be confirmed that it too place:
        You can view the list of meetings that time and place has been confirmed, but the occurrence has not been confirmed.

        5. see the list of meetings that has been confirmed:
        You can view the list of meetings that is done.

        6. see the list of meetings with time and place that need to be confirmed:
        You can see the list of meetings that is not confirmed time and place.

        7. Exit menu:
        Return to regular user main menu <RegularUserMainMenu.csv>.



3) Things you should know before running this program:


 Section 1:

    - To run our program properly, make phase 1 as the root or change the build configurations (add the “\phase1” to
      the end of the working directory)

    - For first time running the program, please clear all content of the files in Manager folder except
      AdminUserUsernameAndPassword.csv

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

 Section 2:

 	- In order to easily test some methods, we allow the user to set the meeting time before the current time(any
 	  time from 2020 to 2030). Therefore, we can test  the “confirm the meeting took place” function immediately(since
 	  we can only confirm the meeting took place when the meeting time is before current time). Also, we can check
 	  the meetings that should have happened but have not confirmed the completeness after one day of the meeting
 	  time immediately.

    - You can see your user ID and username after you logged in. And in the browse other users inventory option,
      you can not only see the items and their IDs but also their owner IDs so you can use that when you
      request for a trade.

    - when you request for a one-way-trade, please keep in mind the numLendBeforeBorrow threshold and
      that the borrower needs to have numLend >= numBorrow

    - We serialize when we create new users/admin users and when user/admin user logs out. So, if you force
      the program to stop, your progress will not be saved. So, you might want to immediately log out after you
      have done something major (ex. request for a trade) and then log in to proceed with the next functionality
      to test.


 Section 3:

    - About the maxNumIncompleteTransactions threshold, if the user's frozen because of that for less than three times,
      the user can request the admin to get itself unfrozen and it would work (the threshold number of the user is going
      to be extended by itself). However, if the user's frozen because of that for three times, then this user
      will be permanently frozen. That is, the user can go to the account menu but not the other two menus
      anymore.

    - About the maxNumTransactionsAWeek threshold, we understood it as the max number of transactions a user can request a week,
      but if the limit is reached, the user can’t request nor accept any trade requests and can only make changes to ones already there.

      This is to prevent users from getting strategies to go behind this and use our program for other purposes (ex. a business
      can use this to arrange meetings between its customers).

      We keep track of the number of each user individually,
      and we deduct one from the number of transactions the user can request a week every time the user requests a
      trade successfully.

      When you first use this, you will get 3 transactions that you can request a week.
      If you decide to change this threshold value, it will be reflected next week
      when the threshold value is reassessed on Sunday.

      Note this threshold value will only be reassessed on Sunday (the first day of the week)
      but the user does need to log on on some other days of week once to enable the reassess function
      for the following Sunday.


