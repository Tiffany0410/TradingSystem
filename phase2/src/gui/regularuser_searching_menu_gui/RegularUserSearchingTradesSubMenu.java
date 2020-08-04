package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import exception.InvalidIdException;
import gui.GUIDemo;
import managers.trademanager.Trade;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class RegularUserSearchingTradesSubMenu {
    private JPanel rootPanel;
    private JButton incompleteTradesButton;
    private JButton completeTradesButton;

    public RegularUserSearchingTradesSubMenu(RegularUserSearchingMenuController regularUserSearchingMenuController,
                                             GUIDemo guiDemo, SystemMessage systemMessage) {
        incompleteTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            try {
                List<Trade> filter1 = regularUserSearchingMenuController.filterIncompleteTrade();

                if (filter1.size() == 0) {
                    systemMessage.msgForNothing();
                }else {
                    systemMessage.printResult(new ArrayList<>(filter1));
                }
            } catch (InvalidIdException  ex) {
                systemMessage.printInvalidID();
            }
                // TODO: Need method to close this window

            }
        });
        completeTradesButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            try {
                List<managers.trademanager.Trade> filter = regularUserSearchingMenuController.filterCompleteTrade();
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
        JFrame frame = new JFrame("RegularUserSearchingTradesSubMenu");
        frame.setContentPane(new RegularUserSearchingTradesSubMenu(regularUserSearchingMenuController, guiDemo, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton backButton;
}
