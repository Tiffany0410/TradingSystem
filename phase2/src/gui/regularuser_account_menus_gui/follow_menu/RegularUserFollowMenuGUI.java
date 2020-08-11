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

/**
 * A class that is responsible for the view and getting input for user
 * when user browses the follow menu.
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserFollowMenuGUI {
    private JButton followAnUserButton;
    private JButton followAnItemButton;
    private JButton seeRecentStatusOfUsersButton;
    private JButton seeRecentStatusOfItemsButton1;
    private JButton backButton;
    private JPanel rootPanel;

    /**
     * Construct a RegularUserFollowMenuGUI.
     * @param guiD The GUI helper.
     * @param amc The RegularUserAccountMenuController
     * @param idChecker The id checker.
     * @param sm The presenter.
     */
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
                followAnUser(guiD, idChecker, sm, amc);
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
                followAnItem(guiD, idChecker, sm, amc);
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

    private void followAnItem(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm, RegularUserAccountMenuController amc) {
        FollowAnItemWindow followAnItemWindow = new FollowAnItemWindow(guiD, idChecker, sm, amc);
        followAnItemWindow.run(guiD, idChecker, sm, amc);
        guiD.runSave();
    }

    private void followAnUser(GUIDemo guiD, RegularUserIDChecker idChecker, SystemMessage sm, RegularUserAccountMenuController amc) {
        FollowAnUserWindow followAnUserWindow = new FollowAnUserWindow(guiD, idChecker, sm, amc);
        followAnUserWindow.run(guiD, idChecker, sm, amc);
        guiD.runSave();
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


    /**
     * Responsible for running this window.
     * @param guiD The GUI helper.
     * @param atc The RegularUserAccountMenuController
     * @param idChecker The id checker.
     * @param sm The presenter.
     */
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
