package gui.regularuser_community_menu_gui;

import controllers.regularusersubcontrollers.RegularUserCommunityMenuController;
import gui.GUIDemo;
import gui.NotificationGUI;
import gui.UserInputGUI;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import presenter.SystemMessage;

import javax.swing.*;
import javax.xml.bind.Marshaller;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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


    public RegularUserCommunityMenuGUI(GUIDemo guidemo, RegularUserCommunityMenuController cmc, SystemMessage sm){
        writeAReviewForButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: need code to code the windows

                String string = "Please input an user id for the user you want to review: ";
                UserInputGUI userInputGUI = new UserInputGUI(string, guidemo);
                userInputGUI.run(string, guidemo);
                String sUserId = guidemo.getTempUserInput();
                String string1 = "Please enter the point for the user(0-10): ";
                UserInputGUI userInputGUI1 = new UserInputGUI(string1, guidemo);
                userInputGUI1.run(string1,guidemo);
                String sPoint = guidemo.getTempUserInput();
                String string2 = "Please enter the reason why you get the point: ";
                UserInputGUI userInputGUI2 = new UserInputGUI(string2, guidemo);
                userInputGUI2.run(string2,guidemo);
                String reason = guidemo.getTempUserInput();
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
                msgGUI.run(string);
            }
        });

        sendAFriendRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Send A Friend Request
            }
        });

        respondToFriendsRequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Respond to friends reqeust
            }
        });

        unfriendAUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: unfriend a user
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
