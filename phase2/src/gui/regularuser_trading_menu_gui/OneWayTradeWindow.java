package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import gui.adminuser_menus_gui.adminuser_menuswindow.AdminUserManageUsersConfirmInventoryWindow;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OneWayTradeWindow {
    private JTextField thisUser;
    private JTextField otherUser;
    private JTextField item;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel rootPanel;

    public OneWayTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                             SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String borrower = thisUser.getText();
                String lender = otherUser.getText();
                String item_input = item.getText();

                if (idC.checkInt(borrower) && idC.checkInt(lender) && idC.checkInt(item_input)){
                    int userId1 = Integer.parseInt(borrower);
                    int userId2 = Integer.parseInt(lender);
                    int itemid1 = Integer.parseInt(item_input);
                    if (idC.checkUserID(userId1) && idC.checkUserID(userId2) && idC.checkItemID(itemid1)) {
                        guiD.printNotification(atc.requestTrade(1, userId1, userId2, itemid1,-1, numLentBeforeBorrow, tradeType));
                        guiD.runSave();
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else{
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.closeWindow(rootPanel);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
    }

    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        JFrame frame = new JFrame("One-way-trade request");
        frame.setContentPane(new OneWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, tradeType).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
