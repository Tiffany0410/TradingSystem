package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import managers.messagemanger.Message;
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

    public void run(GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm, GUIUserInputInfo guiInput) {
        JFrame frame = new JFrame("regularUserCommunityMenuGUI");
        frame.setContentPane(new RegularUserCommunityMenuGUI(guidemo, cmc, sm, guiInput).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


    public RegularUserCommunityMenuGUI(GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm, GUIUserInputInfo guiInput){
        writeAReviewForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code to code the windows
                rootPanel.setVisible(false); // MAYBE??

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
                int user_id = Integer.parseInt(sUserId);
                int point = Integer.parseInt(sPoint);
                if(sm.checkInt(sUserId) && sm.checkInt(sPoint) && guidemo.getUserManager().checkUser(user_id) && (0<=point && point<=10)){
                 boolean yesOrNo =  cmc.reviewUser(user_id, point, reason);
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
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String get_id = "Please enter the user's id that you want to report.";
                UserInputGUI userInputGUI = new UserInputGUI(get_id, guiInput);
                userInputGUI.run(get_id, guiInput);
                String sUserId = guiInput.getTempUserInput();
                String get_reason = "Please enter the reason why you report this user.";
                UserInputGUI userInputGUI2 = new UserInputGUI(get_id, guiInput);
                userInputGUI2.run(get_reason, guiInput);
                if (sm.checkInt(sUserId)){
                    int user_id = Integer.parseInt(sUserId);
                    String msg = sm.msgForResult(cmc.reportUser(user_id, get_reason));
                    NotificationGUI notificationGUI = new NotificationGUI(msg);
                    notificationGUI.run(msg);
                }
                else{
                    String s = "Please enter valid information.";
                    NotificationGUI notificationGUI = new NotificationGUI(s);
                    notificationGUI.run(s);
                }
            }
        });

        findTheRatingForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String get_id = "Please enter the user's id for his/her rating";
                UserInputGUI userInputGUI = new UserInputGUI(get_id, guiInput);
                userInputGUI.run(get_id, guiInput);
                String sUserId = guiInput.getTempUserInput();
                if (sm.checkInt(sUserId)){
                    int id = Integer.parseInt(get_id);
                    if (cmc.checkUserId(id)){
                        String msg = "The rating of this user is " + Math.round(cmc.findRatingForUser(id));
                        NotificationGUI notificationGUI = new NotificationGUI(msg);
                        notificationGUI.run(msg);
                    }
                }
                else {
                    String s = "Please enter a valid user id.";
                    NotificationGUI notificationGUI = new NotificationGUI(s);
                    notificationGUI.run(s);
                }
            }
        });

        seeUsersInYourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String string;
                ArrayList<TradableUser> users = cmc.seeUsersInSameHC();
                if (users.isEmpty()){
                    string = "There is no users in your home city, please check back later :)";
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
                else {
                    string = "Here is a list of users in the same city as you: \n" + sm.printListUser(users);
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
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
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

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
            }
        });

        sendAFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

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
                            userInputGUI1.run(msg, guiInput);
                            String msg_result = guiInput.getTempUserInput();
                            String out = sm.msgForFriendRequest(cmc.sendFriendRequest(userToID, msg_result), userToID);
                            NotificationGUI msgGUI = new NotificationGUI(out);
                            msgGUI.run(out);
                        }
                    }
                }
            }
        });

        respondToFriendsRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String string;
                ArrayList<TradableUser> requests = cmc.getFriendRequest();
                if (requests.isEmpty()){
                    string = "There is no friend requests.";
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
                else{
                    string = "Here is a list of friend requests: \n" + sm.printFriendRequest(requests);
                    UserInputGUI userInputGUI1 = new UserInputGUI(string, guiInput);
                    userInputGUI1.run(string, guiInput);
                    String id_input = guiInput.getTempUserInput();
                    if (sm.checkInt(id_input) && cmc.checkIdInRequest(Integer.parseInt(id_input))){
                        String result = sm.msgForResult(cmc.addFriend(Integer.parseInt(id_input)));
                        NotificationGUI notificationGUI = new NotificationGUI(result);
                        notificationGUI.run(result);
                    }
                    else {
                        String s = "Please enter a valid user id.";
                        NotificationGUI notificationGUI = new NotificationGUI(s);
                        notificationGUI.run(s);
                    }
                }
            }
        });

        unfriendAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

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
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                ArrayList<Message> messages = cmc.getAllMessages();
                String string;
                if (messages.isEmpty()){
                    string = "There is no messages.";
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
                else{
                    string = "Here is a list of messages: " + sm.printAllMessages(messages);
                    NotificationGUI msgGUI = new NotificationGUI(string);
                    msgGUI.run(string);
                }
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
