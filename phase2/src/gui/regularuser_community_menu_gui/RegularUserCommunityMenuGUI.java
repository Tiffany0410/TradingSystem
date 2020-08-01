package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import javax.xml.bind.Marshaller;
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

    public void run() {
        JFrame frame = new JFrame("regularUserCommunityMenuGUI");
        frame.setContentPane(new RegularUserCommunityMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public RegularUserCommunityMenuGUI(GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm, GUIUserInputInfo guiInput){
        writeAReviewForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code to code the windows

                String string = "Please input an user id for the user you want to review: ";
                UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
                userInputGUI.run(string, guiInput);
                String sUserId = guiInput.getTempUserInput();
                String string1 = "Please enter the point for the user(0-10): ";
                UserInputGUI userInputGUI1 = new UserInputGUI(string1, guiInput);
                userInputGUI1.run(string1,guiInput);
                String sPoint = guiInput.getTempUserInput();
                String string2 = "Please enter the reason why you get the point: ";
                UserInputGUI userInputGUI2 = new UserInputGUI(string2, guiInput);
                userInputGUI2.run(string2,guiInput);
                String reason = guiInput.getTempUserInput();
                if(sm.checkInt(sUserId) &&  sm.checkInt(sPoint) && guidemo.getUserManager().checkUser(Integer.
                        parseInt(sUserId)) && (0<=Integer.parseInt(sPoint) && Integer.parseInt(sPoint)<=10)){
                 boolean yesOrNo =  guidemo.getFeedbackManager().setReview(cmc.getUserId(), Integer.parseInt(sUserId),
                            Integer.parseInt(sPoint), reason);
                    NotificationGUI notificationGUI = new NotificationGUI(sm.msgForResult(yesOrNo));
                    notificationGUI.run(sm.msgForResult(yesOrNo));
                }else{
                    NotificationGUI notificationGUI = new NotificationGUI("Please enter the valid information!");
                    notificationGUI.run("Please enter the valid information!");
                }
            }
        });

        reportAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Report a user

            }
        });

        findTheRatingForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Find the rating for a user
            }
        });

        seeUsersInYourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: See users in the same home city
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
                NotificationGUI msgGUI;
                String string;
                if (cmc.getFriends().isEmpty()){
                    string = sm.msgForNothing("in your list of friends.");
                }
                else {
                    string = sm.printListUser(cmc.getFriends());
                }
                msgGUI = new NotificationGUI(string);
                rootPanel.setVisible(false); // Not sure...
                msgGUI.run(string);
                // TODO: close this window;
            }
        });

        sendAFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TradableUser> notFriends = cmc.getNotFriends();
                String string;
                if (notFriends.isEmpty()){  // IF NO AVAILABLE USERS TO ADD
                    string = sm.msgForNo("tradable users to be added. Please check your friend requests");
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
                else{
                    string = "Here is a list of users you can add:\n" + sm.printListUser(notFriends) +
                            "\nPlease enter user's Id to send friend request, or enter 0 to go back.";
                    UserInputGUI userInputGui = new UserInputGUI(string, guiInput);
                    userInputGui.run(string, guiInput);
                    String result = guiInput.getTempUserInput();
                    if (sm.checkInt(result)){
                        int userToID = Integer.parseInt(result);
                        if (userToID == 0){
                            guidemo.runRegularUserCommunityMenuController(false); //back to the menu
                        }
                        else {
                            String msg = "Please leave a message for this user: ";
                            UserInputGUI userInputGUI1 = new UserInputGUI(msg, guiInput);
                            String msg_result = guiInput.getTempUserInput();
                            String out = sm.msgForFriendRequest(cmc.sendFriendRequest(userToID, msg_result), userToID);
                            NotificationGUI msgGUI = new NotificationGUI(out);
                            msgGUI.run(out);
                        }
                    }
                }
            }
            // TODO: close this window;
        });

        respondToFriendsRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Respond to friends request
            }
        });

        unfriendAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<TradableUser> friends = cmc.getFriends();
                String string;
                if (friends.isEmpty()){
                    string = sm.msgForNo("tradable users to be unfriended");
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
                else {
                    string = "Here is your list of friends: \n" + sm.printListUser(friends) +
                            "\nPlease enter user's Id to unfriend, or enter 0 to go back.";
                    UserInputGUI userInputGui = new UserInputGUI(string, guiInput);
                    userInputGui.run(string, guiInput);
                    String result = guiInput.getTempUserInput();
                    if (sm.checkInt(result)){
                        int id = Integer.parseInt(result);
                        if (id == 0){
                            guidemo.runRegularUserCommunityMenuController(false); //back to the menu
                        }
                        else{
                            String out = sm.msgForResult(cmc.unfriendUser(id));
                            NotificationGUI msgGUI = new NotificationGUI(out);
                            msgGUI.run(out);
                        }
                    }
                }
            }
            // TODO: close this window;
        });

        sendMessageToFriendsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Send message to friends
            }
        });

        viewAllMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: view all message
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Return to RegularUser Main Menu
                guidemo.runRegularUserMainMenu(false);
            }
        });
    }
}
