package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import demomanager.GUIDemo;
import managers.usermanager.TradableUser;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * Used to show regular searching menu
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserSearchingUsersSubMenuGUI {
    private JPanel rootPanel;
    private JButton recentTradeUserButton;
    private JButton frequentTradeUserButton;
    private JButton sortUserByRatingButton;
    private JButton backButton;

    public RegularUserSearchingUsersSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                               GUIDemo guiDemo, SystemMessage systemMessage) {
        recentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                List<Integer> filter = regularUserSearchingMenuController.recentThreePartner();
                if (filter.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(filter)));
                }
            }
        });
        frequentTradeUserButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                List<Integer> filter = regularUserSearchingMenuController.sortAllTradedPartner();
                if (filter.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(filter)));
                }
            }
        });
        sortUserByRatingButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {


                List<TradableUser> l = regularUserSearchingMenuController.sortRating();
                if (l.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(l)));
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runRegularUserSearchingMenuGUI();
            }
        });
    }

    /**
     * Run regular user searching users submenu gui
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     */
    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingUsersSubMenu");
        frame.setContentPane(new RegularUserSearchingUsersSubMenuGUI(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
