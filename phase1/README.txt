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

    Key features of RegularUserAccountMenu
    1. Browse all the items in other users inventories:
    Prints a list of all the items in all the other users’ inventories.
    2. Add to own Wish List
    Enter the id of the item you want to add. The item will be added to your wishlist if it is not already there.
    3. Search item
    Enter part of the name of the item. Prints all the items with whose name contains the input.
    4. Remove from own Wish List
    Enter the id of the item you want to remove. The item will be removed from your wishlist if it is there.
    5. Remove from own Inventory
    Prints a list of all the items in your inventory. Enter the number in the list of the item that you want to remove.
    The item will be removed from your inventory.
    6. Request to unfreeze account
    Enter a message to tell an AdminUser and type OK to finish. A request with the message will be sent to an AdminUser
    to review.
    7. Request that an item be added to your inventory
    Enter the name and description of the item. A request will be sent to an AdminUser to review.
    8. View your wish list or inventory
    Prints all the items in your wishlist and all the items in your inventory.
    9. Exit
    Go to the previous menu.







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

    Key feature of tradingInfo menu:
        1.Request a trade: You can request a trade to another user, first step you need to choose what kind of trade you wanna do ( input 1 is one way trade and 2 is two way trade)
        Next enter the user id(input user1/(borrower) id first and user2 next)
        Next enter the id of the item you want to trade.

        2. Respond to trade requests: Respond a waited trade sent by others.

        3. View open trades: You can view all your trades with status open

        4. View closed trades: You can view all your trades with status closed

        5. Confirm that a trade has been completed: You can confirm that a trade has been completed or not.

        6.  See top three most frequent trading partners: You can see the top three most frequent trading partners with you.

        7. View transactions that have been cancelled: You can view transactions you made that have been cancelled.

        8. Exit: To the previous page.
















3) Things you should know before running this program:

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



