package gui.regularuser_account_menus_gui.follow_menu;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserFollowMenuGUI {
    private JButton followAnUserButton;
    private JButton followAnItemButton;
    private JButton seeRecentStatusOfUsersButton;
    private JButton seeRecentStatusOfItemsButton1;
    private JButton backButton;
    private JPanel rootPanel;

    public RegularUserFollowMenuGUI(GUIDemo guiD, RegularUserAccountMenuController amc,
                                    RegularUserIDChecker idChecker, SystemMessage sm) {
        followAnUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                FollowAnUserWindow followAnUserWindow = new FollowAnUserWindow(guiD, idChecker, sm, amc);
                followAnUserWindow.run(guiD, idChecker, sm, amc);
                guiD.runSave();
            }
        });
        followAnItemButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                FollowAnItemWindow followAnItemWindow = new FollowAnItemWindow(guiD, idChecker, sm, amc);
                followAnItemWindow.run(guiD, idChecker, sm, amc);
                guiD.runSave();
            }
        });
        seeRecentStatusOfUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUpdates(sm, amc.seeRecentStatusOfFollowedUser(), "users", guiD);
            }
        });
        seeRecentStatusOfItemsButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                viewUpdates(sm, amc.seeRecentStatusOfFollowedItem(), "items", guiD);
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //GO back to main menu
                guiD.runRegularUserAccountMainMenuGUI();
                guiD.closeWindow(rootPanel);
            }
        });
    }

    private void viewUpdates (SystemMessage sm, List<String> updates, String type, GUIDemo guiDemo) {
        if (updates.size() != 0) {
            String str = sm.printListObject(new ArrayList<>(updates));
            guiDemo.printNotification("Here's the list of updates for the " + type + " you're following \n" + str);
        } else {
            guiDemo.printNotification(sm.msgForNo("updates for now."));
        }
        guiDemo.runSave();
    }


    public void run(GUIDemo guiD, RegularUserAccountMenuController atc,
                    RegularUserIDChecker idChecker, SystemMessage sm) {
        JFrame frame = new JFrame("regularUserFollowMenuGUI");
        frame.setContentPane(new RegularUserFollowMenuGUI(guiD, atc, idChecker, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
