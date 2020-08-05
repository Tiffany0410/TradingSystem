package gui.regularuser_account_menus_gui;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
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
                                         RegularUserIDChecker idChecker, RegularUserAccountMenuController amc){
        browseAllTradableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: this option is allowed for both guest + non-guest
                ArrayList<Item> tradableItems = amc.getTradables();
                if (tradableItems.isEmpty()){
                    printNote(sm.msgForNothing("here."));
                }
                else{
                    String str = sm.printListObject(new ArrayList<>(tradableItems));
                    printNote(str);
                }
            }
        });

        addToWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Add to own Wish List
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        removeFromWishListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Remove from Wish List
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        removeFromInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Remove from own Inventory
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        requestItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Request that an item be added to your inventory
                // TODO: print sm.msgForGuest(); if it's a guest
            }
        });

        mostRecentThreeItemsTradedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: See most recent three items traded
                // TODO: print sm.msgForGuest(); if it's a guest
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
                    ArrayList<Item> wishlist = amc.getWishLish();
                    ArrayList<Item> inventory = amc.getInventory();
                    String wish_str = "Here is your wishlist: ";
                    String inv_str = "Here is your inventory: ";
                    printNote(wish_str + sm.printListObject(new ArrayList<>(wishlist)) + "\n" + inv_str +
                            sm.printListObject(new ArrayList<>(inventory)) + "\n");
                }
            }
        });

        changeTradableStatusForItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
                // TODO: Back to RegularUserAccountMainMenu
            }
        });
    }

    public void run(boolean isGuest, SystemMessage sm, GUIDemo guiDemo, GUIUserInputInfo guiInput,
                    RegularUserIDChecker idChecker, RegularUserAccountMenuController acm) {
        JFrame frame = new JFrame("RegularUserManageItemsMenuGUI");
        frame.setContentPane(new RegularUserManageItemsMenuGUI(isGuest, sm, guiDemo, guiInput, idChecker, acm).rootPanel);
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
