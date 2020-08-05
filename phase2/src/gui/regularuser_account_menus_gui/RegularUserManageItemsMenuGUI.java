package gui.regularuser_account_menus_gui;

import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public RegularUserManageItemsMenuGUI(boolean isGuest, SystemMessage sm){
        browseAllTradableButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Browse all tradable items in the system
                // TODO: this option is allowed for both guest + non-guest
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
                // TODO: View your wishlist and inventory
                // TODO: print sm.msgForGuest(); if it's a guest
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

    public void run(boolean isGuest, SystemMessage sm) {
        JFrame frame = new JFrame("RegularUserManageItemsMenuGUI");
        frame.setContentPane(new RegularUserManageItemsMenuGUI(isGuest, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
