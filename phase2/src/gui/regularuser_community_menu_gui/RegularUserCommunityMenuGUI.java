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
                    String sUserId = guidemo.getInPut("Please enter the user's id for his/her rating");
                    if (idC.checkInt(sUserId)) {
                        int id = Integer.parseInt(sUserId);
                        if (cmc.checkUserId(id)) {
                            String msg = "The rating of this user is " + Math.round(cmc.findRatingForUser(id));
                            guidemo.printNotification(msg);
                        }
                    }
                    else {
                        String s = "Please enter a valid user id.";
                        guidemo.printNotification(s);
                    }
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
                        string = sm.msgForNothing("your list of friends.");
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
                    sendOrRespondOrUnfriend(notFriends, sm, guiInput, idC, cmc, "send friend request",
                            "tradable users to be added. Please check your friend requests",
                            "users you can add", guidemo);
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
                    ArrayList<TradableUser> requests = cmc.getFriendRequest();
                    sendOrRespondOrUnfriend(requests, sm, guiInput, idC, cmc, "accept friend request",
                            "friend requests", "friend requests", guidemo);
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
                    sendOrRespondOrUnfriend(friends, sm, guiInput, idC, cmc, "unfriend",
                            "tradable users to be unfriended", "friends", guidemo);
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

    private void sendOrRespondOrUnfriend(ArrayList<TradableUser> users, SystemMessage sm, GUIUserInputInfo guiInput,
                                         RegularUserIDChecker idC, RegularUserCommunityMenuController cmc,
                                         String actionType, String msg1, String msg2, GUIDemo guiDemo){
        if (users.isEmpty()){
            guiDemo.printNotification(sm.msgForNo(msg1));
        }
        else{
            String string = "Please enter user's Id to " + actionType + ".\n" + "Here is a list of " + msg2 + ":\n";
            if (actionType.equals("send friend request")) {
                string = string + sm.printFriendRequest(users);
            }
            else{
                string = string + sm.printListUser(users);
            }
            String result = guiDemo.getInPut(string);
            if (idC.checkInt(result)){
                int id = Integer.parseInt(result);
                printRequest(sm, guiInput, cmc, actionType, id, guiDemo);
            }
            else{
                guiDemo.printNotification("Please enter a valid information.");
            }
        }
    }

    private void printRequest(SystemMessage sm, GUIUserInputInfo guiInput, RegularUserCommunityMenuController cmc,
                              String actionType, int id, GUIDemo guidemo){
        String out;
        if (actionType.equals("send friend request")){
            String msg_result = guidemo.getInPut("Please leave a message for this user: ");
            out = sm.msgForFriendRequest(cmc.sendFriendRequest(id, msg_result), id);
        }
        else if (actionType.equals("accept friend request")){
            if (cmc.checkIdInRequest(id)){
                out = sm.msgForResult(cmc.addFriend(id));
            }
            else{
                out = "Please enter a valid information.";
            }
        }
        else {
            out = sm.msgForResult(cmc.unfriendUser(id));
        }
        guidemo.printNotification(out);
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