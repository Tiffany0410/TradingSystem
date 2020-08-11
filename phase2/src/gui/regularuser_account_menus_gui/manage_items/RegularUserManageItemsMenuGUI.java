package gui.regularuser_account_menus_gui.manage_items;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import gui.regularuser_account_menus_gui.manage_items.manageItemsWindows.*;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Used to show regular user manage items menu
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
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

    /**
     * Run Regular User Manage Items Menu Gui
     * @param isGuest The boolean stores whether this user is guest account or not
     * @param sm SystemMessage
     * @param guiDemo GUIDemo
     * @param idChecker RegularUserIDChecker
     * @param amc RegularUserAccountMenuController
     * @param otherInfoChecker RegularUserOtherInfoChecker
     */
    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiDemo,
                    RegularUserIDChecker idChecker, RegularUserAccountMenuController amc,
                    RegularUserOtherInfoChecker otherInfoChecker) {
        JFrame frame = new JFrame("RegularUserManageItemsMenuGUI");
        frame.setContentPane(new RegularUserManageItemsMenuGUI(isGuest, sm, guiDemo, idChecker, amc,
                otherInfoChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Constructor for Regular User Manage Items Menu Gui
     * @param isGuest The boolean stores whether this user is guest account or not
     * @param sm SystemMessage
     * @param guiDemo GUIDemo
     * @param idChecker RegularUserIDChecker
     * @param amc RegularUserAccountMenuController
     * @param otherInfoChecker RegularUserOtherInfoChecker
     */
    public RegularUserManageItemsMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiDemo,
                                         RegularUserIDChecker idChecker, RegularUserAccountMenuController amc,
                                         RegularUserOtherInfoChecker otherInfoChecker){
        browseAllTradableButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, browse all tradable items
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> tradableItems = amc.getTradables();
                printObjects(tradableItems, sm, guiDemo);
            }
        });

        addToWishListButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, add item to wishlist
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                addToWishlist(isGuest, guiDemo, sm, amc, idChecker);
            }
        });

        removeFromWishListButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, remove item from wishlist
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromWishlist(isGuest, guiDemo, sm, amc, idChecker);
            }
        });

        removeFromInventoryButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, remove item from inventory
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                removeFromInventory(isGuest, guiDemo, sm, amc, idChecker);
            }
        });

        requestItemButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, request item to be added to inventory
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    RegularUserRequestItemWindow window = new RegularUserRequestItemWindow(guiDemo, amc, otherInfoChecker);
                    window.run(guiDemo, amc, otherInfoChecker);
                    guiDemo.runSave();
                }
            }
        });

        mostRecentThreeItemsTradedButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, see most recent threes item traded
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    ArrayList<Item> recentItems = amc.seeMostRecentThreeItems();
                    printObjects(recentItems, sm, guiDemo);
                }
            }
        });

        viewWishListInventoryButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, view wishlist and inventory
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewWishlistInventory(isGuest, guiDemo, sm, amc);
            }
        });

        changeTradableStatusForItemButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, change tradable status for an item
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                changeTradableStatus(isGuest, guiDemo, sm, amc, idChecker);
            }
        });

        getSuggestionForItemToLendButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, get suggestion for item to lend
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    RegularUserManageItemsSuggestionWindow window = new RegularUserManageItemsSuggestionWindow(guiDemo, sm, amc, idChecker);
                    window.run(guiDemo, sm, amc, idChecker);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, back to upper level menu
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runRegularUserAccountMainMenuGUI();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void changeTradableStatus(boolean isGuest, GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        if (isGuest){
            guiDemo.printNotification(sm.msgForGuest());
        }
        else {
            ArrayList<Item> tradable = amc.getTradableItems();
            ArrayList<Item> notTradable = amc.getNotTradableItems();
            if (tradable.isEmpty() && notTradable.isEmpty()){
                guiDemo.printNotification("There is no tradable items that can be changed.");
            }
            else {
                String string = getTradableId(sm, tradable, notTradable);
                ArrayList<Item> inventory = amc.getInventory();
                RegularUserManageItemsTradableStatusWindow window = new
                        RegularUserManageItemsTradableStatusWindow(inventory, string, guiDemo, sm, amc, idChecker);
                window.run(inventory, string, guiDemo, sm, amc, idChecker);
            }
            guiDemo.runSave();
        }
    }

    private void viewWishlistInventory(boolean isGuest, GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc) {
        if (isGuest){
            guiDemo.printNotification(sm.msgForGuest());
        }
        else {
            ArrayList<Item> wishlist = amc.getWishList();
            ArrayList<Item> inventory = amc.getInventory();
            String wish_str = "Here is your wishlist: \n";
            String inv_str = "Here is your inventory: \n";
            guiDemo.printNotification(wish_str + sm.printListObject(new ArrayList<>(wishlist)) + "\n" + inv_str
                    + sm.printListObject(new ArrayList<>(inventory)) + "\n");
        }
    }

    private void removeFromInventory(boolean isGuest, GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        if (isGuest){
            guiDemo.printNotification(sm.msgForGuest());
        }
        else {
            ArrayList<Item> items = amc.getInventory();
            if (items.isEmpty()){
                guiDemo.printNotification(sm.msgForNo("tradable items that can be removed from your inventory"));
            }
            else{
                String string = "Here is your inventory: \n" +
                        sm.printListObject(new ArrayList<>(items)) +
                        "\nPlease enter the item's id to remove from inventory:";
                RegularUserManageItemsRemoveInvtyWindow window = new RegularUserManageItemsRemoveInvtyWindow(items, string, guiDemo, sm, amc, idChecker);
                window.run(items, string, guiDemo, sm, amc, idChecker);
            }
        }
        guiDemo.runSave();
    }

    private void removeFromWishlist(boolean isGuest, GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        if (isGuest){
            guiDemo.printNotification(sm.msgForGuest());
        }
        else {
            ArrayList<Item> items = amc.getWishList();
            if (items.isEmpty()){
                guiDemo.printNotification(sm.msgForNo("tradable items that can be removed from your wishlist"));
            }
            else{
                String string = "Here is your wishlist: \n" +
                        sm.printListObject(new ArrayList<>(items)) +
                        "\nPlease enter the item's id to remove from wishlist:";
                RegularUserManageItemsRemoveWlstWindow window = new RegularUserManageItemsRemoveWlstWindow(items, string, guiDemo, sm, amc, idChecker);
                window.run(items, string, guiDemo, sm, amc, idChecker);
            }
        }
        guiDemo.runSave();
    }

    private void addToWishlist(boolean isGuest, GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker) {
        if (isGuest){
            guiDemo.printNotification(sm.msgForGuest());
        }
        else{
            ArrayList<Item> tradable = amc.getAllTradableFromOtherNotInWishlist();
            if (tradable.isEmpty()){
                guiDemo.printNotification(sm.msgForNo("tradable items that can be added to your wishlist"));
            }
            else{
                String string = "Here is a list of tradable items you can add to wishlist: \n" +
                        sm.printListObject(new ArrayList<>(tradable)) +
                        "\nPlease enter the item's id to add to wishlist: ";
                RegularUserManageItemsAddWlstWindow window = new RegularUserManageItemsAddWlstWindow(tradable, string, guiDemo, sm, amc, idChecker);
                window.run(tradable, string, guiDemo, sm, amc, idChecker);
            }
        }
        guiDemo.runSave();
    }

    /**
     * Helper method for printing object
     * @param items A list of Item
     * @param sm SystemMessage
     * @param guiDemo GuiDemo
     */
    private void printObjects(ArrayList<Item> items, SystemMessage sm, GUIDemo guiDemo){
        if (items.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here"));
        }
        else{
            String str = sm.printListObject(new ArrayList<>(items));
            guiDemo.printNotification(str);
        }
    }

    /**
     * Helper method for getting string representation for get tradable id
     * @param sm System Message
     * @param tradable A list of tradable items
     * @param nonTradable A list of non-tradable items
     * @return A string representation of tradable and nontradable
     */
    private String getTradableId(SystemMessage sm, ArrayList<Item> tradable, ArrayList<Item> nonTradable){
        String str = "Here's the list of items with tradable status: \n" + sm.printListObject(new ArrayList<>(tradable)) +
                    "Here's the list of items with non-tradable status: \n" + sm.printListObject(new ArrayList<>(nonTradable)) +
                    "Enter the item id of the item that you want to change the tradable status of.";
        return str;
    }
}
