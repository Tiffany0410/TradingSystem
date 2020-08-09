package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TwoWayTradeWindow {
    private JTextField userid1;
    private JTextField userid2;
    private JTextField item1;
    private JTextField item2;
    private JButton cancelButton;
    private JButton requestButton;
    private JPanel panel;

    public TwoWayTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                             SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        requestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user1 = userid1.getText();
                String user2 = userid2.getText();
                String item1_input = item1.getText();
                String item2_input = item2.getText();

                if (idC.checkInt(user1) && idC.checkInt(user2) && idC.checkInt(item1_input) && idC.checkInt(item2_input)){
                    int userId1 = Integer.parseInt(user1);
                    int userId2 = Integer.parseInt(user2);
                    int itemId1 = Integer.parseInt(item1_input);
                    int itemId2 = Integer.parseInt(item2_input);
                    if (idC.checkUserID(userId1) && idC.checkUserID(userId2) && idC.checkItemID(itemId1) && idC.checkItemID(itemId2)) {
                        guiD.printNotification(atc.requestTrade(2, userId1, userId2, itemId1, itemId2, numLentBeforeBorrow, tradeType));
                        guiD.runSave();
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else{
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.closeWindow(panel);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(panel);
            }
        });
    }

    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow, String tradeType){
        JFrame frame = new JFrame("Two-way-trade request");
        frame.setContentPane(new TwoWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, tradeType).panel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
