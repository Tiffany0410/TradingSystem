package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import demomanager.GUIDemo;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
/**
 * Used to show regular searching window
 * @author Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserSearchingTradesSubMenuGUI {
    private JPanel rootPanel;
    private JButton incompleteTradesButton;
    private JButton completeTradesButton;
    private JButton backButton;

    public RegularUserSearchingTradesSubMenuGUI(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                                GUIDemo guiDemo, SystemMessage systemMessage) {
        incompleteTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                List<Trade> filter1 = regularUserSearchingMenuController.filterIncompleteTrade();

                if (filter1.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                }else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(filter1)));
                }
            }
        });
        completeTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation
             * @param e click button
             */

            @Override
            public void actionPerformed(ActionEvent e) {

                List<Trade> filter = regularUserSearchingMenuController.filterCompleteTrade();
                if (filter.size() == 0) {
                    guiDemo.printNotification(systemMessage.msgForNothing());
                } else {
                    guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(filter)));
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
     * Run regular user searching trade submenu gui
     * @param regularUserSearchingMenuController regularUserSearchingMenuController
     * @param guiDemo GUIDemo
     * @param systemMessage systemMessage
     */
    public void run(RegularUserSearchingMenuController regularUserSearchingMenuController,
                    GUIDemo guiDemo, SystemMessage systemMessage) {
        JFrame frame = new JFrame("RegularUserSearchingTradesSubMenu");
        frame.setContentPane(new RegularUserSearchingTradesSubMenuGUI(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
