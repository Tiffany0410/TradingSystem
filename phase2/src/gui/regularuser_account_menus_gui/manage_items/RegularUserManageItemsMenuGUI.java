package gui.regularuser_account_menus_gui.manage_items;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
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


    public RegularUserManageItemsMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiDemo,
                                         RegularUserIDChecker idChecker, RegularUserAccountMenuController amc,
                                         RegularUserOtherInfoChecker otherInfoChecker){
        browseAllTradableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> tradableItems = amc.getTradables();
                printObjects(tradableItems, sm, guiDemo);
            }
        });

        addToWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        removeFromWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        removeFromInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        requestItemButton.addActionListener(new ActionListener() {
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
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        changeTradableStatusForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        getSuggestionForItemToLendButton.addActionListener(new ActionListener() {
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
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runRegularUserAccountMainMenuGUI();
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void printObjects(ArrayList<Item> items, SystemMessage sm, GUIDemo guiDemo){
        if (items.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here"));
        }
        else{
            String str = sm.printListObject(new ArrayList<>(items));
            guiDemo.printNotification(str);
        }
    }

    private String getTradableId(SystemMessage sm, ArrayList<Item> tradable, ArrayList<Item> nonTradable){
        String str = "Here's the list of items with tradable status: \n" + sm.printListObject(new ArrayList<>(tradable)) +
                    "Here's the list of items with non-tradable status: \n" + sm.printListObject(new ArrayList<>(nonTradable)) +
                    "Enter the item id of the item that you want to change the tradable status of.";
        return str;
    }
}
