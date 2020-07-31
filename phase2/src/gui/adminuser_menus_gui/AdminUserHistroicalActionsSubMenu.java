package gui.adminuser_menus_gui;

import gui.GUIController;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserHistroicalActionsSubMenu {
    private JPanel rootPanel;
    private JButton listAllTheHistoricalButton;
    private JButton cancelTheRevocableHistoricalButton;
    private JButton findAllTheRevocableButton;
    private JButton backButton;

    public AdminUserHistroicalActionsSubMenu(GUIController guiController) {
        listAllTheHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        cancelTheRevocableHistoricalButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        findAllTheRevocableButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

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

            }
        });
    }

    public void run(GUIController guiController) {
        JFrame frame = new JFrame("AdminUserHistroicalActionsSubMenu");
        frame.setContentPane(new AdminUserHistroicalActionsSubMenu(guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }


}