package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import exception.InvalidIdException;
import gui.GUIDemo;
import managers.usermanager.TradableUser;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingUsersSubMenu {
    private JPanel rootPanel;
    private JButton recentTradeUserButton;
    private JButton frequentTradeUserButton;
    private JButton sortUserByRatingButton;
    private JButton backButton;

    public RegularUserSearchingUsersSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                            GUIDemo guiDemo, SystemMessage systemMessage) {
        recentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    List<Integer> filter = regularUserSearchingMenuController.recentThreePartner();
                if (filter.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(filter)); }
                } catch ( InvalidIdException ex) {
                    systemMessage.printInvalidID(); }
                    // TODO: Need method to close this window

                }
        });
        frequentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    List<Integer> filter = regularUserSearchingMenuController.sortAllTradedPartner();
                    if (filter.size() == 0) {
                        systemMessage.msgForNothing();
                    } else {
                        systemMessage.printResult(new ArrayList<>(filter));
                    }
                } catch (InvalidIdException ex) {
                    systemMessage.printInvalidID();
                }

                // TODO: Need method to close this window

            }
        });
        sortUserByRatingButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {


                List<TradableUser> l = regularUserSearchingMenuController.sortRating();
                if (l.size() == 0) {
                    systemMessage.msgForNothing();
                } else {
                    systemMessage.printResult(new ArrayList<>(l));
                }
                // TODO: Need method to close this window

            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runRegularUserSearchingMenuGUI();
                // TODO: Need method to close this window

            }
        });
    }

    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingUsersSubMenu");
        frame.setContentPane(new RegularUserSearchingUsersSubMenu(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}
