

# README

## Name 
Trading System



## Description

This trading system will allow tradableUsers to create a new account, login in and look for items to trade. Users can make a one-way trade, where they lend someone an item or borrow an item. Users can also make a two-way trade where both tradableUsers borrow and lend an item. Each trade can be permanent or temporary. With a permanent trade, only one meeting will be created where the user gives away an item or borrows an item permanently. With a temporary trade, two meetings will be created where the second exchange will happen exactly one month after the first meeting in the same location. Users can edit or confirm the time and place to meet in real life to trade items.

Users are allowed to give ratings and review to other users or report other users with a valid reason. Additionally, users can add other users as friends. By adding friends, users can send messages to each other. Also, users can follow other users to receive notifications when other users add items to their inventory and wishlist. Users can follow items as well, they can check for the recent status of the items they followed.

There exist two different menus for Admin User and Regular User. After login with correct username and password, AdminUsers have one main menu *AdminUserMainMenu* and four sub menus *AdminUserManageUsersSubMenu*, *AdminUserEditThresholdsSubMenu*, *AdminUserHistoricalActionSubMenu* and *AdminUserOtherSubMenu*. RegularUsers have one main menu *RegularUserMainMenu* and five sub menus *RegularUserAccountMenu*, *RegularUserTradingMenu*, *RegularUserMeetingMenu*, *RegularUserSearchingMenu* and *RegularUserCommunityMenu*.

  
  
## An overview of the menus and menu options


## *Admin Main menu*

>###  -- Manage Users --
>>  Freeze/unfreeze user or allow the item request

>### -- Edit Thresholds --

>> Change the thresholds value related to user, trade and meeting

>### --  Manage Historical Actions --

>> Review or Undo the Historical Actions

>### -- Other --

>> Create new admin or change system time

  

 

## *Regular User Main menu*

>### **-- Account Info --**

>>#### 1. Manage Items

>> Browse all tradable items, add items to wishlist, remove items from wishlist/inventory, view your wishlist and inventory, and request for an item to be added to inventory

>> See most recent three items traded, change tradable status for an inventory item, and get suggestions for item(s) that user can lend to a given user

>>#### 2.  Account Settings

>>  Request to unfreeze if you happen to be frozen, tell the system that you’re on or off vacation, change your home city, or undo an action

>>#### 3.  Follow Others/Items

>> Follow a user or item to able to see updates about the user’s inventory or wishlist as well as if the item’s tradable status is changed

>### **-- Trading Info --**

> Request an one-way or two-way trade, respond to trade requests, see the trades that are open, closed, and cancelled, check if a trade is complete, or get suggestions for the most reasonable trade.

  

>### **-- Meeting Info --**

>  Edit or confirm time and place, confirm meeting took place, see the list of meetings have been confirmed

>### **-- Searching Info --**

>> #### 1. Searching items

>> Filter by category, search item by keyword, by id and sort item by number of follows

>> #### 2. Searching users

>> Recent partners, most frequent partners and sort users by rating

>> #### 3. Searching meetings

>> Sort meeting by date, and search incomplete/complete meeting

>> #### 4. Searching trades

>> Search incomplete/complete trade

  

>### **-- Community Info --**

> Write a review and give a rating for a user, report a user, find the review for a given user or yourself.

> See users in home city, send friend requests to other users, respond to friend requests, view list of friends, unfriend a user, send messages to friends and view all messages


## Things you should know before running this program

-   To run our program properly, make phase 1 the root or change the build configurations **(add the “\phase1” to the end of the working directory)**
    
-   Add **JUnit4** as class path for the tests
    
- Program execution starts at **Main.java** class. This class will call the method in the DemoManager, which will run the init menu. The init menu will run different GUI classes depending on the user's choice.
    
## Important Tips

>### Important tips about Thresholds

>> **1. numLendBeforeBorrow (default = 1)**
    
 >> **What is it?**
 > >> The number of items the user must lend before he/she can borrow any item.
    
>> **How to test?**
>>> - go to the tradinginfo menu to request a trade -> if the user have not lend anything before requesting an one-way-trade as a borrower -> trade request = unsuccessful
>>> - two-way-trade can be successfully requested because the user is lending at the same time as borrowing
    

>> **2.  maxTimePlaceEdits (default = 3)**
    

>> **What is it?**
    
>>> Maximum number of time and place edits allowed per meeting per person.
    
>> **How to test?**
>>> 1. request a trade successfully
>>> 2. check out the meeting info menu to edit time and place for the first meeting
>>> 3. on the seventh edit (*<- if you're staying with the default value*), you will see a message saying you have exceeded the limit and the trade is cancelled

    
>> **3.  maxNumTransactionsPerWeek (default = 3)**
    
>>  **What is it?**
    
>>> Maximum number of transactions an user can request per week (*each successful trade requested means one less number of transactions allowed for the week*)
    
>> **How to test?**
>>> **Note:** this threshold value is assessed everytime you check the  notification option in the *regular user main menu*   and you can see the number of transactions you have left for the week there, too.
>>> **Note**: this threshold value will be set back to full **every Monday**. If you want to change the value of this threshold, you need to manipulate the system clock in the *admin user others submenu* to set the date to Monday and then go to the notification option in the *regular user main menu* to check that it's change.
>>> 1. change this threshold value to any value you want for testing
>>> 2. change the system clock to monday
>>> 3. check the notification option in the *regular user main menu* for the change
>>> 4. **set the system clock back to any other day of the week except Monday** (because every time you do step 3 on Monday, the value will be set back to full regardless if you've just used your chance or not)
>>> 5. let x = the new value you set for this threshold, try request x trades successfully
>>> 6. After you've reached the threshold limit, you should find that you will be unable to request another trade or respond to any trade request until it's Monday again
    

>> **4. NumIncompleteTransactionsBeforeFrozen (default = 3)**
    
>>**What is it?**
>>> The maximum number of transactions that can be incomplete before the user's account is frozen by the system
>
>>**How to test?**
>>> An incomplete transaction = both users agree to an one-way or two-way trade but one of them or both of them don't show up (i.e. don't confirm the meeting took place within 24 hours after the meeting is supposed to take place.
>
>>> Check the notification option in the *regular user main menu* to reassess and see your frozen status after you have reached the threshold.
    

  

> ### **Important tips about login and create account**

>>  You can create a regular user by going to the main menu’s **create account** option. See **“RegularUserUsernameAndPassword.csv”** for the users that already exist in the system and for the username, password, and email information about the user you created. **Note: please don’t test the program using the account of the regular user that already exist. Test with the account(s) you created.**
    

  >> you can add an admin user to the system by **logging in as an admin user** **(see “AdminUserUsernameAndPassword.csv”)**. There is an existing adminUser account, which is username: adminTest, password: adminTest. For username and passwords and going to the Others submenu where you should see the option to add another admin user
    

  >>In “RegularUserUsernameAndPassword.csv” or “AdminUserUsernameAndPassword.csv”, each line is in the following format and corresponds to one user: **username, password, email**
    

  >>**When you create the new tradable User**, **do not use the username adminTest**, which is the default AdminUser in our system and used for create new adminUser
    

  
  > ### **Important tips about setting system time for testing**

>>  Since some functions have time restrictions, **we added a setting system time option under the admin menu only for the purpose of testing easily**. For example, we only allow users to confirm the meeting took place if the meeting time is before the real time, **so you can change the system time instead of waiting for a long time to test**. Also, you can test the max transaction per week threshold function more easily by changing the system time (*for more details on how to test this threshold, see the important tips about thresholds*).
    

  
 > ### **Important tips about requesting a trade or responding to trade requests**

  

>>When selecting one way, you only need to input one item id, unlike for two ways, you need to input two item id.
    
>>Right now, you **can only request the two-way trade successfully if you’re a new user** because **according to the numLendBeforeBorrow threshold** (which is 1 by default), the user must lend at least an item before the user can borrow. (To override this, simply set the threshold mentioned to 0).
    
>> **After you’ve requested a trade successfully**, the trade request will be sent to the user who you would like to have this trade with. If the other user responds “Agree”, then this trade will be marked as “open” and you can check it in the “see open trades” option. Otherwise, this trade will be cancelled, and you can check it in the “see cancelled trades” option. If you request the trade unsuccessfully, there is also a chance of your account getting frozen, if the system finds that you currently borrow more than you lend.
    
>>**Once the trade is open**, the items in trade will be marked as non-tradable and be removed from the wishlist/inventory (*note: this means if the item is cancelled half way, the users need to do the return the item back thing privately and have the choice of adding it back into their inventory after they got the actual physical item back*) Also, the numBorrowed and numLend record of the users will also be changed. **The first meeting will be automatically setted up by the system** and you can go to the meeting menu to edit its time and place (**note: the time and place must be after the current time(not the system clock time) but you can set the system clock to test the confirm the meeting took place function**)
    
>>**While you’re editing the time and place, watch out for the maximum edits of time and place per person limit.** If you or the other user passed that limit, the trade will be automatically cancelled, and you will be able to see the trade in the “see trades that are cancelled” option.
    
>>Only one person needs to confirm the time and place. **The person who suggests the time and place confirms the time and place by default.** **Both people have to confirm the meeting took place within 24 hours after the time and date when the meeting is supposed to take place.**  If they don’t, this transaction counts as incomplete. Three incomplete transactions will count towards both users being frozen (note: it’s three because the incompleteTransactionsBeforeFrozen is three by default in our system. You can always change it to less for the sake of testing).
    
>>If it’s a **temporary trade**, the trade will be closed/complete if both meetings are confirmed to have taken place. If it’s a **permanent trade**, there is only one meeting so it is enough if both users confirm the first meeting has taken place.

 > ### **Important tips about checking the notification for regular user for tests**
 > > The notification contains information about the user.
 > They are:
 > - username, userid 
 > - user's frozen status
 > - user's on-vacation status
 > - user's home city
 > - the number of items the user have borrowed / lent
 > - the threshold values
 > - the number of transactions the user have left for the week
 > > The notification also evaluates/assesses user's frozen status the incomplete transaction threshold and the number of transactions the user have left for the week. So be sure to click on it if you want to get re-assessed on these. 


 > ### **Important tips about undo the historical actions**
> >The ids for all historical actions are different from the ids for all revocable actions.
