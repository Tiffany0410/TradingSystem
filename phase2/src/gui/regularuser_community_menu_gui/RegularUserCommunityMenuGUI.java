package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserOtherInfoChecker;
import demomanager.GUIDemo;
import gui.regularuser_community_menu_gui.communityWindows.*;
import managers.feedbackmanager.Review;
import managers.messagemanger.Message;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Used to show regular user community menu
 * @author Shi Tang, Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */

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
    private JButton viewYourRatingAndButton;

    /**
     * Run Regular User Community Menu GUI
     * @param isGuest The boolean stores whether this user is guest account or not
     * @param guidemo GUIDemo
     * @param cmc RegularUserCommunityMenuController
     * @param sm SystemMessage
     * @param idC RegularUserIDChecker
     */
    public void run(boolean isGuest, GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm,
                    RegularUserIDChecker idC) {
        JFrame frame = new JFrame("regularUserCommunityMenuGUI");
        frame.setContentPane(new RegularUserCommunityMenuGUI(isGuest, guidemo, cmc, sm, idC).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

    /**
     * Constructor for Regular User Community Menu GUI
     * @param isGuest The boolean stores whether this user is guest account or not
     * @param guidemo GUIDemo
     * @param cmc RegularUserCommunityMenuController
     * @param sm SystemMessage
     * @param idC RegularUserIDChecker
     */
    public RegularUserCommunityMenuGUI(boolean isGuest, GUIDemo guidemo, RegularUserCommunityMenuController cmc,
                                       SystemMessage sm, RegularUserIDChecker idC){
        writeAReviewForButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, write a review for user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                writeAReview(isGuest, guidemo, sm, idC, cmc);
            }
        });

        reportAUserButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, report an user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                reportAUser(isGuest, guidemo, sm, idC, cmc);
            }
        });

        findTheRatingForButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, find the rating for a particular user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                findTheRatingForUser(isGuest, guidemo, sm, cmc, idC);
            }
        });

        seeUsersInYourButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, see users in the home same city
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                seeUsersInHomeCity(isGuest, guidemo, sm, cmc);
            }
        });

        viewYourListOfButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, view your list of friends
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewFriends(isGuest, guidemo, sm, cmc);
            }
        });

        sendAFriendRequestButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, send friend request to other user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                sendFriendRequest(isGuest, guidemo, sm, cmc, idC);
            }
        });

        respondToFriendsRequestButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, respond to friend requests
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                respondToFriendRequests(isGuest, guidemo, sm, cmc, idC);
            }
        });

        unfriendAUserButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, unfriend an user
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                unfriendAUser(isGuest, guidemo, sm, cmc, idC);
            }
        });

        sendMessageToFriendsButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, send message to friends
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAMessageToFriend(isGuest, guidemo, sm, cmc, idC);
            }
        });

        viewAllMessageButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, view all messages
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewMessages(isGuest, guidemo, sm, cmc);
            }
        });

        viewYourRatingAndButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, view user's rating and review
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewReviews(isGuest, guidemo, sm, cmc);
            }
        });

        backButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, back to upper level menu
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guidemo.runRegularUserMainMenu(isGuest);
                guidemo.closeWindow(rootPanel);
            }
        });
    }

    private void viewReviews(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc) {
        if (isGuest){
            guidemo.printNotification(sm.msgForGuest());
        }
        else{
            ArrayList<Review> reviews = cmc.getAllReviews(cmc.getUserId());
            double rating = cmc.findRatingForUser(cmc.getUserId());
            if (reviews.isEmpty()){
                guidemo.printNotification(sm.msgForNothing());
            }
            else{
                guidemo.printNotification(sm.msgForRatingReview(rating, reviews));
            }
        }
    }

    private void viewMessages(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc) {
        if (isGuest) {
            guidemo.printNotification(sm.msgForGuest());
        } else {
            ArrayList<Message> messages = cmc.getAllMessages();
            String string;
            if (messages.isEmpty()) {
                string = "There is no messages.";
            }
            else {
                string = "Here is a list of messages: \n" + sm.printAllMessages(messages);
            }
            guidemo.printNotification(string);
        }
    }

    private void sendAMessageToFriend(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC) {
        if (isGuest){
            guidemo.printNotification(sm.msgForGuest());
        }
        else {
            ArrayList<TradableUser> friends = cmc.getFriends();
            if (!friends.isEmpty()){
                String string = "Here is your list of friends:\n" + sm.printListUser(friends) +
                        "\nPlease enter user's id to send a message.\n";
                RegularUserCommunitySendMessageWindow window =
                        new RegularUserCommunitySendMessageWindow(string, guidemo, sm, cmc, idC);
                window.run(string, guidemo, sm, cmc, idC);
            }
            else{
                guidemo.printNotification(sm.msgForNo("friends, please add friends first"));
            }
            guidemo.runSave();
        }
    }

    private void unfriendAUser(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC) {
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
            guidemo.runSave();
        }
    }

    private void respondToFriendRequests(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC) {
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
        guidemo.runSave();
    }

    private void sendFriendRequest(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC) {
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
        guidemo.runSave();
    }

    private void viewFriends(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc) {
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

    private void seeUsersInHomeCity(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc) {
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

    private void findTheRatingForUser(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserCommunityMenuController cmc, RegularUserIDChecker idC) {
        if (isGuest) {
            guidemo.printNotification(sm.msgForGuest());
        }
        else {
            RegularUserCommunityRatingWindow window = new RegularUserCommunityRatingWindow(guidemo, cmc, idC, sm);
            window.run(guidemo, cmc, idC, sm);
            }
    }

    private void reportAUser(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc) {
        if (isGuest) {
            guidemo.printNotification(sm.msgForGuest());
        }
        else {
            RegularUserCommunityReportAUser report = new RegularUserCommunityReportAUser(sm, idC, cmc, guidemo);
            report.run(sm, idC, cmc, guidemo);
        }
        guidemo.runSave();
    }

    private void writeAReview(boolean isGuest, GUIDemo guidemo, SystemMessage sm, RegularUserIDChecker idC, RegularUserCommunityMenuController cmc) {
        if (isGuest) {
            guidemo.printNotification(sm.msgForGuest());
        }
        else {
            RegularUserCommunityWriteAReviewWindow window = new RegularUserCommunityWriteAReviewWindow(guidemo, idC, cmc, sm);
            window.run(guidemo, idC, cmc, sm);
        }
        guidemo.runSave();
    }
}