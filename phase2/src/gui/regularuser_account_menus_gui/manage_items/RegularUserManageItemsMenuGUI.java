package gui.regularuser_account_menus_gui.manage_items;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
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

    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiDemo, GUIUserInputInfo guiInput,
                    RegularUserIDChecker idChecker, RegularUserAccountMenuController acm,
                    RegularUserOtherInfoChecker otherInfoChecker) {
        JFrame frame = new JFrame("RegularUserManageItemsMenuGUI");
        frame.setContentPane(new RegularUserManageItemsMenuGUI(isGuest, sm, guiDemo, guiInput, idChecker, acm,
                otherInfoChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


    public RegularUserManageItemsMenuGUI(boolean isGuest, SystemMessage sm, GUIDemo guiDemo, GUIUserInputInfo guiInput,
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
                    ArrayList<Item> tradable = amc.getAllTradableFromOther();
                    addToWishlist(tradable, sm, guiInput, idChecker, amc, guiDemo);
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
                    removeFrom(amc, sm, guiInput, idChecker, items, "wishlist", guiDemo);
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
                    removeFrom(amc, sm, guiInput, idChecker, items, "inventory", guiDemo);
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
                }
                guiDemo.runSave();
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
                        String itemId_input = getTradableId(guiInput, sm, tradable, notTradable, guiDemo);
                        String setTradable_input = getStatus(sm, guiInput, guiDemo);
                        setTradable(itemId_input, setTradable_input, idChecker, sm, amc, guiDemo);
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
                    String result = guiDemo.getInPut("Please enter the user's id you want to lend item(s) to");
                    getSuggestion(result, amc, sm, idChecker, guiDemo);
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

    private void removeFrom(RegularUserAccountMenuController amc, SystemMessage sm, GUIUserInputInfo guiInput, RegularUserIDChecker idChecker, ArrayList<Item> items, String type,
                            GUIDemo guiDemo){
        if (items.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("your " + type + " that can be removed"));
        }
        else{
            String str = "Here is your " + type + ": \n" +
                    sm.printListObject(new ArrayList<>(items)) +
                    "\nPlease enter the item's id to remove from " + type + ". ";
            String input = guiDemo.getInPut(str);
            if (idChecker.checkInt(input)){
                int itemId = Integer.parseInt(input);
                if (idChecker.checkItemID(items, itemId)){
                    boolean result;
                    if (type.equals("wishlist")){
                        result = amc.removeFromWishlist(itemId); }
                    else {
                        result = amc.removeFromInventory(itemId); }
                    guiDemo.printNotification(sm.msgForResult(result)); }
                else { guiDemo.printNotification("Invalid item id was entered, please try again."); } }
            else { guiDemo.printNotification("Please enter an integer."); }
        }
    }

    private void addToWishlist(ArrayList<Item> tradable, SystemMessage sm, GUIUserInputInfo guiInput, RegularUserIDChecker idChecker, RegularUserAccountMenuController amc,
                               GUIDemo guiDemo){
        if (tradable.isEmpty()){
            guiDemo.printNotification(sm.msgForNo("tradable items can be added to wishlist."));
        }
        else{
            String str = "Here is a list of tradable items you can add to wishlist: \n" +
                    sm.printListObject(new ArrayList<>(tradable)) +
                    "\nPlease enter the item's id to add to wishlist. ";
            String input = guiDemo.getInPut(str);
            if (idChecker.checkInt(input)){
                int itemId = Integer.parseInt(input);
                if (idChecker.checkItemID(tradable, itemId)){
                    boolean result = amc.addToWishList(itemId);
                    guiDemo.printNotification(sm.msgForResult(result));
                }
                else {
                    guiDemo.printNotification("Invalid item id was entered, please try again.");
                }
            }
            else {
                guiDemo.printNotification("Please enter an integer.");
            }
        }
    }

    private void printObjects(ArrayList<Item> items, SystemMessage sm, GUIDemo guiDemo){
        if (items.isEmpty()){
            guiDemo.printNotification(sm.msgForNothing("here."));
        }
        else{
            String str = sm.printListObject(new ArrayList<>(items));
            guiDemo.printNotification(str);
        }
    }

    private String getTradableId(GUIUserInputInfo guiInput, SystemMessage sm, ArrayList<Item> tradable, ArrayList<Item> nonTradable, GUIDemo guiDemo){
        String str = "Here's the list of items with tradable status: \n" + sm.printListObject(new ArrayList<>(tradable)) +
                    "Here's the list of items with non-tradable status: \n" + sm.printListObject(new ArrayList<>(nonTradable)) +
                    "Enter the item id of the item that you want to change the tradable status of.";
        return guiDemo.getInPut(str);
    }

    private String getStatus(SystemMessage sm, GUIUserInputInfo guiInput, GUIDemo guiDemo){
        String str = sm.getNumKindOfResponse("set item to tradable", "set item to non-tradable");
        return guiDemo.getInPut(str);
    }

    private void setTradable(String itemId_input, String setTradable_input, RegularUserIDChecker idChecker, SystemMessage sm, RegularUserAccountMenuController amc, GUIDemo guiDemo){
        ArrayList<Item> inventory = amc.getInventory();
        if (idChecker.checkInt(itemId_input) && idChecker.checkInt(setTradable_input)){
            int itemId = Integer.parseInt(itemId_input);
            int setTradable = Integer.parseInt(setTradable_input);
            if (idChecker.checkItemID(inventory, itemId) && (setTradable == 1 | setTradable == 2)){
                boolean result = amc.setTradableBasedOnResponse(itemId, setTradable);
                guiDemo.printNotification(sm.msgForSetTradable(result, setTradable));
            }
            else{
                guiDemo.printNotification("Please enter a valid input.");
            }
        }
        else{
            guiDemo.printNotification("Please enter a valid input.");
        }
    }

    private void getSuggestion(String result, RegularUserAccountMenuController amc, SystemMessage sm, RegularUserIDChecker idChecker, GUIDemo guiDemo){
        if (idChecker.checkInt(result)){
            int lendToUserId = Integer.parseInt(result);
            if (idChecker.checkUserID(lendToUserId)){
                ArrayList<Item> suggest = amc.getSuggestItemToLend(lendToUserId);
                if (suggest.isEmpty()){
                    guiDemo.printNotification("No good suggestions available...\nHere's a randomly generated one:\n" +
                            sm.printListObject(new ArrayList<>(amc.getRandomSuggestion(lendToUserId))));
                }
                else{
                    guiDemo.printNotification("Below are suggestions of items you can lend to that user: \\n" + sm.printListObject(new ArrayList<>(suggest)));
                }
            }
            else{
                guiDemo.printNotification("Please enter a valid input.");
            }
        }
        else{
            guiDemo.printNotification("Please enter a valid input.");
        }
    }


}
