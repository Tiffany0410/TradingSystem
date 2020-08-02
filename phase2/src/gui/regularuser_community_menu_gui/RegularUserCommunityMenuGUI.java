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
                String sUserId = getInPut(string, guiInput);
                String string1 = "Please enter the point for the user(0-10): ";
                String sPoint = getInPut(string1, guiInput);
                String string2 = "Please enter the reason why you get the point: ";
                String reason = getInPut(string2, guiInput);
                if(sm.checkInt(sUserId) && sm.checkInt(sPoint)){
                    int user_id = Integer.parseInt(sUserId);
                    int point = Integer.parseInt(sPoint);
                    if(guidemo.getUserManager().checkUser(user_id) && (0<=point && point<=10)) {
                        boolean yesOrNo =  cmc.reviewUser(user_id, point, reason);
                        printNote(sm.msgForResult(yesOrNo));
                    }
                }else{
                    printNote("Please enter a valid id and point.");
                }
            }
        });

        reportAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String get_id = "Please enter the user's id that you want to report.";
                String sUserId = getInPut(get_id, guiInput);
                String get_reason = "Please enter the reason why you report this user.";
                String reason = getInPut(get_reason, guiInput);
                if (sm.checkInt(sUserId)){
                    int user_id = Integer.parseInt(sUserId);
                    String msg = sm.msgForResult(cmc.reportUser(user_id, reason));
                    printNote(msg);
                }
                else{
                    String s = "Please enter valid information.";
                    printNote(s);
                }
            }
        });

        findTheRatingForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code for closing the menu first;
                rootPanel.setVisible(false); // MAYBE??

                String get_id = "Please enter the user's id for his/her rating";
                String sUserId = getInPut(get_id,guiInput);
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
                    printNote(string);
                }
                else {
                    string = "Here is a list of users in the same city as you: \n" + sm.printListUser(users);
                    printNote(string);
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

                String string;
                if (cmc.getFriends().isEmpty()){
                    string = sm.msgForNothing("in your list of friends.");
                }
                else {
                    string = sm.printListUser(cmc.getFriends());

                }
                rootPanel.setVisible(false); // Not sure...
                printNote(string);
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
                    printNote(string);
                }
                else{
                    string = "Here is a list of users you can add:\n" + sm.printListUser(notFriends) +
                            "\nPlease enter user's Id to send friend request, or enter 0 to go back.";
                    String result = getInPut(string,guiInput);
                    if (sm.checkInt(result)){
                        int userToID = Integer.parseInt(result);
                        if (userToID == 0){
                            guidemo.runRegularUserCommunityMenuController(false); //back to the menu
                        }
                        else {
                            String msg = "Please leave a message for this user: ";
                            String msg_result = getInPut(msg,guiInput);
                            String out = sm.msgForFriendRequest(cmc.sendFriendRequest(userToID, msg_result), userToID);
                            printNote(out);
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
                    printNote(string);
                }
                else{
                    string = "Here is a list of friend requests: \n" + sm.printFriendRequest(requests);
                    String id_input = getInPut(string,guiInput);
                    if (sm.checkInt(id_input) && cmc.checkIdInRequest(Integer.parseInt(id_input))){
                        String result = sm.msgForResult(cmc.addFriend(Integer.parseInt(id_input)));
                        printNote(result);
                    }
                    else {
                        String s = "Please enter a valid user id.";
                        printNote(s);
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
                    printNote(string);
                }
                else {
                    string = "Here is your list of friends: \n" + sm.printListUser(friends) +
                            "\nPlease enter user's Id to unfriend, or enter 0 to go back.";
                    String result = getInPut(string,guiInput);
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
                // TODO: close the windows
                rootPanel.setVisible(false); // MAYBE??

                ArrayList<TradableUser> friends = cmc.getFriends();
                String string;
                if (friends.isEmpty()){
                    string = sm.msgForNo("friends. Please add friends first.");
                    printNote(string);
                }else{  string = "Here is your list of friends: \n" + sm.printListUser(friends) +
                        "\nPlease enter user's ID to send a message.";
                    String result = getInPut(string, guiInput);
                    if (sm.checkInt(result)){
                        int id = Integer.parseInt(result);
                        if (!cmc.checkIsFriend(id)){
                            NotificationGUI notificationGUI = new NotificationGUI("Please enter an id of your friend!");
                            notificationGUI.run("Please enter an id of your friend!");
                        }
                        else{
                            string = "Please write a message: ";
                            String message = getInPut(string,guiInput);
                            String string1 = sm.msgForResult(cmc.sendMessage(id, message));
                            printNote(string1);
                        }}}

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
    }public String getInPut(String string, GUIUserInputInfo guiInput){
        UserInputGUI userInputGUI = new UserInputGUI(string, guiInput);
        userInputGUI.run(string, guiInput);
        String sUserId = guiInput.getTempUserInput();
        // TODO: need to close first
        return string;
    }
    public void printNote(String string){
        NotificationGUI msgGUI = new NotificationGUI(string);
        msgGUI.run(string);
        // TODO: need to close first
    }
}
