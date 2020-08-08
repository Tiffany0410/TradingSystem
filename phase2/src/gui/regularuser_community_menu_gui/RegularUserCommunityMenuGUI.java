package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import managers.messagemanger.Message;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class RegularUserCommunityMenuGUI {
    private JPanel rootPanel;
    private JButton writeAReviewForButton;
    private JButton reportAUserButton;
    private JButton findTheRatingForButton;
    private JButton seeUsersInYourButton;
    private JButton viewYourListOfButton;
    private JButton sendAFriendRequestButton;
    private JButton respondToFriendsRequestButton;
    private JButton unfriendAUserButton;
    private JButton sendMessageToFriendsButton;
    private JButton viewAllMessageButton;
    private JButton backButton;

    public void run(boolean isGuest, GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm,
                    GUIUserInputInfo guiInput,
                    RegularUserIDChecker idC,  RegularUserOtherInfoChecker otherChecker) {
        JFrame frame = new JFrame("regularUserCommunityMenuGUI");
        frame.setContentPane(new RegularUserCommunityMenuGUI(isGuest, guidemo, cmc, sm, guiInput, idC,
                otherChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public RegularUserCommunityMenuGUI(boolean isGuest, GUIDemo guidemo, RegularUserCommunityMenuController cmc,
                                       SystemMessage sm, GUIUserInputInfo guiInput,
                                       RegularUserIDChecker idC, RegularUserOtherInfoChecker otherChecker){
        writeAReviewForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    RegularUserCommunityWriteAReviewWindow window = new RegularUserCommunityWriteAReviewWindow(guidemo, idC, otherChecker, cmc, sm);
                    window.run(guidemo, idC, otherChecker, cmc, sm);
                }
            }
        });

        reportAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    RegularUserCommunityReportAUser report = new RegularUserCommunityReportAUser(sm, idC, cmc, guidemo);
                    report.run(sm, idC, cmc, guidemo);
                }
            }
        });

        findTheRatingForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    RegularUserCommunityRatingWindow window = new RegularUserCommunityRatingWindow(guidemo, cmc, idC);
                    window.run(guidemo, cmc, idC);
                    }
                }

        });

        seeUsersInYourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    String string;
                    ArrayList<TradableUser> users = cmc.seeUsersInSameHC();
                    if (users.isEmpty()) {
                        string = "There is no users in your home city, please check later :)";
                    }
                    else {
                        string = "Here is a list of users in the same city as you: \n" + sm.printListUser(users);
                    }
                    guidemo.printNotification(string);
                }
            }
        });

        viewYourListOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    String string;
                    if (cmc.getFriends().isEmpty()) {
                        string = sm.msgForNothing("your list of friends");
                    }
                    else {
                        string = sm.printListUser(cmc.getFriends());
                    }
                    guidemo.printNotification(string);
                }
            }
        });

        sendAFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    ArrayList<TradableUser> notFriends = cmc.getNotFriends();
                    if (!notFriends.isEmpty()) {
                        String string = "Here's a list of users you can send request to:\n" + sm.printListUser(notFriends)
                                + "\nPlease enter user's id to send friend request.\n";
                        RegularUserCommunitySendFriendRequestWindow window = new
                                RegularUserCommunitySendFriendRequestWindow(string, guidemo, sm, cmc, idC);
                        window.run(string, guidemo, sm, cmc, idC);
                    }
                    else{
                        guidemo.printNotification(sm.msgForNo("tradable users can be added"));
                    }
                }
            }
        });

        respondToFriendsRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    HashMap<TradableUser, String> requests = cmc.getFriendRequest();
                    if (!requests.isEmpty()){
                        String string = sm.printFriendRequest(requests) + "\nPlease enter user's id to accept friend request.\n" ;
                        RegularUserCommunityRespondRequestWindow window = new
                                RegularUserCommunityRespondRequestWindow(string, guidemo, sm, cmc, idC);
                        window.run(string, guidemo, sm, cmc, idC);
                    }
                    else{
                        guidemo.printNotification(sm.msgForNo("requests to be responded"));
                    }
                }
            }
        });

        unfriendAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    ArrayList<TradableUser> friends = cmc.getFriends();
                    if (!friends.isEmpty()){
                        String string = "Here is a list of friends:\n" + sm.printListUser(friends) +
                                "\nPlease enter user's id to unfriend.\n";
                        RegularUserCommunityUnfriendWindow window = new RegularUserCommunityUnfriendWindow(string, guidemo, sm, cmc, idC);
                        window.run(string, guidemo, sm, cmc, idC);

                    }
                    else{
                        guidemo.printNotification(sm.msgForNo("tradable users to be unfriended"));
                    }

                }
            }
        });

        sendMessageToFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest){
                    guidemo.printNotification(sm.msgForGuest());
                }
                else {
                    ArrayList<TradableUser> friends = cmc.getFriends();
                    sendMessage(friends, sm, guiInput, idC, cmc, guidemo);
                }
            }
        });

        viewAllMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isGuest) {
                    guidemo.printNotification(sm.msgForGuest());
                } else {
                    ArrayList<Message> messages = cmc.getAllMessages();
                    String string;
                    if (messages.isEmpty()) {
                        string = "There is no messages.";
                    }
                    else {
                        string = "Here is a list of messages: " + sm.printAllMessages(messages);
                    }
                    guidemo.printNotification(string);
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guidemo.runRegularUserMainMenu(isGuest);
                guidemo.closeWindow(rootPanel);
            }
        });

    }

    private void sendMessage(ArrayList<TradableUser> friends, SystemMessage sm, GUIUserInputInfo guiInput,
                             RegularUserIDChecker idC, RegularUserCommunityMenuController cmc, GUIDemo guiDemo){
        String string;
        if (friends.isEmpty()) {
            string = sm.msgForNo("friends. Please add friends first.");
        }
        else {
            String msg = "Please enter user's ID to send a message.\nHere is your list of friends:\n"
                    + sm.printListUser(friends);
            String result = guiDemo.getInPut(msg);
            if (idC.checkInt(result)) {
                int id = Integer.parseInt(result);
                if (!cmc.checkIsFriend(id)) {
                    string = "Please enter an id of your friend!";
                }
                else {
                    String message = guiDemo.getInPut("Please write a message: ");
                    string = sm.msgForResult(cmc.sendMessage(id, message));
                }
            }
            else {
                string = "Please enter a valid information.";
            }
        }
        guiDemo.printNotification(string);
    }
}