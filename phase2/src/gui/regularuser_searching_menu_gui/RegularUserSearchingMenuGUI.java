package gui.regularuser_searching_menu_gui;

import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Used to show regular searching gui
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserSearchingMenuGUI {
    private JPanel rootPanel;
    private JButton searchingItemsButton;
    private JButton searchingUsersButton;
    private JButton searchingMeetingsButton;
    private JButton backButton;
    private JButton searchingTradesButton;


    public RegularUserSearchingMenuGUI(GUIDemo guiDemo, boolean guest, SystemMessage sm) {
        searchingItemsButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                    guiDemo.closeWindow(rootPanel);
                    guiDemo.runRegularUserSearchingItemsSubMenu();

            }
        });
        searchingUsersButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                if (guest) {
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    guiDemo.closeWindow(rootPanel);
                    guiDemo.runRegularUserSearchingUsersSubMenu();
                }

            }
        });
        searchingMeetingsButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                if (guest) {
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    guiDemo.closeWindow(rootPanel);
                    guiDemo.runRegularUserSearchingMeetingsSubMenu();
                }
                
            }
        });
        searchingTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {
                if (guest) {
                    guiDemo.printNotification(sm.msgForGuest());
                }
                else {
                    guiDemo.closeWindow(rootPanel);
                    guiDemo.runRegularUserSearchingTradesSubMenu();
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
                guiDemo.runRegularUserMainMenu(guest);
            }
        });
    }

    /**
     * Run regular uer searching menu gui
     * @param guiDemo GUIDemo
     * @param guest guest or not
     * @param sm systemMessage
     */
    public void run(GUIDemo guiDemo, boolean guest, SystemMessage sm) {
        JFrame frame = new JFrame("regularUserSearchingMenuGUI");
        frame.setContentPane(new RegularUserSearchingMenuGUI(guiDemo, guest, sm).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
