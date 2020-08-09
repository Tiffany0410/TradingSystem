package gui.regularuser_searching_menu_gui;

import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserSearchingWindow {
    private JPanel rootPanel;
    private javax.swing.JLabel JLabel;
    private JTextField textField;
    private JButton cancelButton;
    private JButton confirmButton;

    public RegularUserSearchingWindow(String inputName, int option, GUIDemo guiDemo, SystemMessage systemMessage,
                                      RegularUserSearchingMenuController regularUserSearchingMenuController) {
        JLabel.setText(inputName);

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (option == 2){
                    ArrayList<Integer> c = regularUserSearchingMenuController.searchItemByName(textField.getText());

                    if (c.size() == 0) {
                        guiDemo.printNotification(systemMessage.msgForNothing());
                    } else {
                        guiDemo.printNotification(systemMessage.printResult(new ArrayList<>(c)));
                    }

                }


                if (option == 3){
                    try{
                        int id = Integer.parseInt(textField.getText());
                        String description = regularUserSearchingMenuController.getItemById(id);
                        guiDemo.printNotification(description);
                        guiDemo.runSave();
                    } catch (NumberFormatException  ex){
                        guiDemo.printNotification("Please enter number!");
                    }

                }

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
                guiDemo.runRegularUserSearchingItemsSubMenu();

            }
        });
    }

    public void run(String inputName, int option, GUIDemo guiDemo, SystemMessage systemMessage,
                    RegularUserSearchingMenuController regularUserSearchingMenuController) {
        JFrame frame = new JFrame("RegularUserSearchingWindow");
        frame.setContentPane(new RegularUserSearchingWindow(inputName, option, guiDemo, systemMessage,regularUserSearchingMenuController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }


}
