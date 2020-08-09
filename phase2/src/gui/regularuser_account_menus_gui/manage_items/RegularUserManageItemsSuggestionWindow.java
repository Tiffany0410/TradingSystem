package gui.regularuser_account_menus_gui.manage_items;

import controllers.regularusersubcontrollers.RegularUserAccountMenuController;
import controllers.regularusersubcontrollers.RegularUserIDChecker;
import demomanager.GUIDemo;
import managers.itemmanager.Item;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class RegularUserManageItemsSuggestionWindow {
    private JTextField userInput;
    private JButton cancelButton;
    private JButton getSuggestionButton;
    private JPanel rootPanel;

    public RegularUserManageItemsSuggestionWindow(GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){

        getSuggestionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText();
                if (idChecker.checkInt(input)){
                    int lendToUserId = Integer.parseInt(input);
                    if (lendToUserId == amc.getUserId()){
                        guiDemo.printNotification("Can't get suggestion to lend to yourself :)");
                    }
                    else if (idChecker.checkUserID(lendToUserId)){
                        ArrayList<Item> suggest = amc.getSuggestItemToLend(lendToUserId);
                        if (suggest.isEmpty()){
                            guiDemo.printNotification("No good suggestions available...\n" +
                                    "Here's a randomly generated one:\n" +
                                    sm.printListObject(new ArrayList<>(amc.getRandomSuggestion(lendToUserId))));
                        }
                        else{
                            guiDemo.printNotification("Below are suggestions of items you can lend to that user:\n"
                                    + sm.printListObject(new ArrayList<>(suggest)));
                        }
                    }
                    else{
                        guiDemo.printNotification("Please enter a valid user id.");
                    }
                }
                else{
                    guiDemo.printNotification("Please enter a valid input.");
                }
                guiDemo.closeWindow(rootPanel);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }
    public void run(GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Get Suggestion to Lend");
        frame.setContentPane(new RegularUserManageItemsSuggestionWindow(guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(300, 300));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
