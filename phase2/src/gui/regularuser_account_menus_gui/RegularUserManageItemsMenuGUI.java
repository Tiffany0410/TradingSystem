package gui.regularuser_account_menus_gui;

import com.sun.corba.se.spi.ior.ObjectKey;
import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserManageItemsMenuGUI {
    private JPanel rootPanel;
    private JButton browseAllTradableButton;
    private JButton addToWishListButton;
    private JButton removeFromWishListButton;
    private JButton removeFromInventoryButton;
    private JButton requestItemButton;
    private JButton mostRecentThreeItemsTradedButton;
    private JButton viewWishListInventoryButton;
    private JButton changeTradableStatusForItemButton;
    private JButton getSuggestionForItemToLendButton;
    private JButton backButton;

    public RegularUserManageItemsMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiDemo, GUIUserInputInfo guiInput,
                                         RegularUserIDChecker idChecker, RegularUserAccountMenuController amc,
                                         RegularUserOtherInfoChecker otherInfoChecker){
        browseAllTradableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: this option is allowed for both guest + non-guest
                ArrayList<Item> tradableItems = amc.getTradables();
                printObjects(new ArrayList<Object>(tradableItems), sm);
            }
        });

        addToWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add to own Wish List
                // TODO: print sm.msgForGuest(); if it's a guest
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else{
                    ArrayList<Item> tradable = amc.getAllTradableFromOther();
                    addToWishlist(tradable, sm, guiInput, idChecker, amc);
                }
            }
        });

        removeFromWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Remove from Wish List
                // TODO: print sm.msgForGuest(); if it's a guest
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> items = amc.getWishList();
                    removeFrom(amc, sm, guiInput, idChecker, items, "wishlist");
                }
            }
        });

        removeFromInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Remove from own Inventory
                // TODO: print sm.msgForGuest(); if it's a guest
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> items = amc.getInventory();
                    removeFrom(amc, sm, guiInput, idChecker, items, "inventory");
                }
            }
        });

        requestItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Request that an item be added to your inventory
                // TODO: print sm.msgForGuest(); if it's a guest
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    String itemName = getInPut("Please enter the item's name", guiInput);
                    String description = getInPut("Enter the description of the item", guiInput);
                    String category_input = getInPut("Please enter the type of the item, it must be in one of the " +
                            "categories below (all UPPERCASE)!\n" + sm.msgForCategory(), guiInput);
                    if (otherInfoChecker.checkItemType(category_input)){
                        Category category = Category.valueOf(category_input);
                        amc.requestAddItem(itemName, description, category);
                    }
                    else {
                        printNote("Please enter the type of the item correctly (all UPPERCASE).");
                    }
                }
            }
        });

        mostRecentThreeItemsTradedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: See most recent three items traded
                // TODO: print sm.msgForGuest(); if it's a guest
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> recentItems = amc.seeMostRecentThreeItems();
                    printObjects(new ArrayList<Object>(recentItems), sm);
                }
            }
        });

        viewWishListInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: print sm.msgForGuest(); if it's a guest

                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> wishlist = amc.getWishList();
                    ArrayList<Item> inventory = amc.getInventory();
                    String wish_str = "Here is your wishlist: \n";
                    String inv_str = "Here is your inventory: \n";
                    printNote(wish_str + sm.printListObject(new ArrayList<>(wishlist)) + "\n" + inv_str
                            + sm.printListObject(new ArrayList<>(inventory)) + "\n");
                }
            }
        });

        changeTradableStatusForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    printNote(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> inventory = amc.getInventory();
                    ArrayList<Item> tradable = amc.getTradableItems();
                    ArrayList<Item> notTradable = amc.getNotTradableItems();
                    if (tradable.isEmpty() && notTradable.isEmpty()){
                        printNote("There is no tradable items that can be changed.");
                    }
                    else {
                        String itemId_input = getTradableId(guiInput, sm, tradable, notTradable);
                        String setTradable_input = getStatus(sm, guiInput);
                        if (idChecker.checkInt(itemId_input) && idChecker.checkInt(setTradable_input)){
                            int itemId = Integer.parseInt(itemId_input);
                            int setTradable = Integer.parseInt(setTradable_input);
                            if (idChecker.checkItemID(inventory, itemId) && (setTradable == 1 | setTradable == 2)){
                                boolean result = amc.setTradableBasedOnResponse(itemId, setTradable);
                                printNote(sm.msgForSetTradable(result, setTradable));
                            }
                            else{
                                printNote("Please enter a valid input.");
                            }
                        }
                    }
                }
                // TODO: Change tradable status for an inventory item
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        getSuggestionForItemToLendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Get suggestions for item(s) that you can lend to a given user
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // guiDemo.RegularUserAccountMainMenuGUI();
            }
        });
    }

    private void removeFrom(RegularUserAccountMenuController amc, SystemMessage sm, GUIUserInputInfo guiInput, RegularUserIDChecker idChecker, ArrayList<Item> items, String type){
        if (items.isEmpty()){
            printNote(sm.msgForNothing("your " + type + " that can be removed"));
        }
        else{
            String str = "Here is your " + type + ": \n" +
                    sm.printListObject(new ArrayList<>(items)) +
                    "\nPlease enter the item's id to remove from " + type + ". ";
            String input = getInPut(str, guiInput);
            if (idChecker.checkInt(input)){
                int itemId = Integer.parseInt(input);
                if (idChecker.checkItemID(items, itemId)){
                    boolean result;
                    if (type.equals("wishlist")){
                        result = amc.removeFromWishlist(itemId); }
                    else {
                        result = amc.removeFromInventory(itemId); }
                    printNote(sm.msgForResult(result)); }
                else { printNote("Invalid item id was entered, please try again."); } }
            else { printNote("Please enter an integer."); }
        }
    }

    private void addToWishlist(ArrayList<Item> tradable, SystemMessage sm, GUIUserInputInfo guiInput, RegularUserIDChecker idChecker, RegularUserAccountMenuController amc){
        if (tradable.isEmpty()){
            printNote(sm.msgForNo("tradable items can be added to wishlist."));
        }
        else{
            String str = "Here is a list of tradable items you can add to wishlist: \n" +
                    sm.printListObject(new ArrayList<>(tradable)) +
                    "\nPlease enter the item's id to add to wishlist. ";
            String input = getInPut(str, guiInput);
            if (idChecker.checkInt(input)){
                int itemId = Integer.parseInt(input);
                if (idChecker.checkItemID(tradable, itemId)){
                    boolean result = amc.addToWishList(itemId);
                    printNote(sm.msgForResult(result));
                }
                else {
                    printNote("Invalid item id was entered, please try again.");
                }
            }
            else {
                printNote("Please enter an integer.");
            }
        }
    }

    private void printObjects(ArrayList<Object> objects, SystemMessage sm){
        if (objects.isEmpty()){
            printNote(sm.msgForNothing("here."));
        }
        else{
            String str = sm.printListObject(objects);
            printNote(str);
        }
    }

    private String getTradableId(GUIUserInputInfo guiInput, SystemMessage sm, ArrayList<Item> tradable, ArrayList<Item> nonTradable){
        String str = "Here's the list of items with tradable status: \n" + sm.printListObject(new ArrayList<>(tradable)) +
                    "Here's the list of items with non-tradable status: \n" + sm.printListObject(new ArrayList<>(nonTradable)) +
                    "Enter the item id of the item that you want to change the tradable status of.";
        return getInPut(str, guiInput);
    }

    private String getStatus(SystemMessage sm, GUIUserInputInfo guiInput){
        String str = sm.getNumKindOfResponse("set item to tradable", "set item to non-tradable");
        String result = getInPut(str, guiInput);
        return result;
    }

    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiDemo, GUIUserInputInfo guiInput,
                    RegularUserIDChecker idChecker, RegularUserAccountMenuController acm, RegularUserOtherInfoChecker otherInfoChecker) {
        JFrame frame = new JFrame("RegularUserManageItemsMenuGUI");
        frame.setContentPane(new RegularUserManageItemsMenuGUI(isGuest, sm, guiDemo, guiInput, idChecker, acm, otherInfoChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public String getInPut(String string, GUIUserInputInfo guiInput) {
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String userResponse = guiInput.getTempUserInput();
        // TODO: need to close first
        return userResponse;
    }

    public void printNote(String msg){
        NotificationGUI msgGUI = new NotificationGUI(msg);
        msgGUI.run(msg);
        // TODO: need to close first
    }

}
