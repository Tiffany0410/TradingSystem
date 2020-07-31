package gui;

import javax.swing.*;

public class RegularUserCommunityMenuGUI {
    private JPanel rootPanel;
    private JButton writeAReviewForButton;
    private JButton reportAUserButton;
    private JButton findTheRatingForButton;
    private JButton seeUsersInYourButton;
    private JButton viewYourListOfButton;

    public void run() {
        JFrame frame = new JFrame("regularUserCommunityMenuGUI");
        frame.setContentPane(new RegularUserCommunityMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton sendAFriendRequestButton;
    private JButton respondToFriendsRequestButton;
    private JButton unfriendAUserButton;
    private JButton sendMessageToFriendsButton;
    private JButton viewAllMessageButton;
    private JButton backButton;
}
