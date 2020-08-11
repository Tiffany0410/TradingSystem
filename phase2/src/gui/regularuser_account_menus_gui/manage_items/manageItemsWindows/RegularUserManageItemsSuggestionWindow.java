package gui.regularuser_account_menus_gui.manage_items.manageItemsWindows;

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

/**
 * Used to show regular user get suggestion window
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserManageItemsSuggestionWindow {
    private JTextField userInput;
    private JButton cancelButton;
    private JButton getSuggestionButton;
    private JPanel rootPanel;

    /**
     * Constructor for Regular User Manage Items Suggestion Window
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public RegularUserManageItemsSuggestionWindow(GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){

        getSuggestionButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, get suggestion to lend
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                getSuggestion(idChecker, amc, guiDemo, sm);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, close this window
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.closeWindow(rootPanel);
            }
        });
    }

    private void getSuggestion(RegularUserIDChecker idChecker, RegularUserAccountMenuController amc, GUIDemo guiDemo, SystemMessage sm) {
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
            guiDemo.printNotification(sm.tryAgainMsgForWrongInput());
        }
        guiDemo.closeWindow(rootPanel);
    }

    /**
     * Run Regular User Manage Items Suggestion Window
     * @param guiDemo GUIDemo
     * @param sm SystemMessage
     * @param amc RegularUserAccountMenuController
     * @param idChecker RegularUserIDChecker
     */
    public void run(GUIDemo guiDemo, SystemMessage sm, RegularUserAccountMenuController amc, RegularUserIDChecker idChecker){
        JFrame frame = new JFrame("Get Suggestion to Lend");
        frame.setContentPane(new RegularUserManageItemsSuggestionWindow(guiDemo, sm, amc, idChecker).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
