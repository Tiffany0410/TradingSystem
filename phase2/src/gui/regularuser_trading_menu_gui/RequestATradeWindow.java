package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestATradeWindow {
    private JButton cancelButton;
    private JButton OKButton;
    private JTextField itemIdOfFirstItem;
    private JTextField itemIdOfSecondItem;
    private JTextField lenderOrLender1Borrower2;
    private JTextField borrowerOrBorrower1Lender2;
    private JComboBox tradeKind;
    private JComboBox tradeType;
    private JPanel rootPanel;

    public RequestATradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                               SystemMessage sm, int numLentBeforeBorrow) {
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String tradeTypeS = (String)tradeType.getSelectedItem();
                //it'll either be 1 or 2
                char tradeKindS = ((String)tradeKind.getSelectedItem()).charAt(0);
                String itemIdOfFirstItemS = itemIdOfFirstItem.getText();
                String itemIdOfSecondItemS = itemIdOfSecondItem.getText();
                String userId1S = borrowerOrBorrower1Lender2.getText();
                String userId2S = lenderOrLender1Borrower2.getText();
                if (idC.checkInt(itemIdOfFirstItemS) && idC.checkInt(itemIdOfSecondItemS)
                        && idC.checkInt(userId1S) && idC.checkInt(userId2S)){
                    int userId1 = Integer.parseInt(userId1S);
                    int userId2 = Integer.parseInt(userId2S);
                    int itemid1 = Integer.parseInt(itemIdOfFirstItemS);
                    int itemid2 = Integer.parseInt(itemIdOfSecondItemS);
                    int tradeKindI = Integer.parseInt(String.valueOf(tradeKindS));
                    if (idC.checkUserID(userId1) && idC.checkUserID(userId2)
                            && idC.checkItemID(itemid1) && idC.checkItemID(itemid2)) {
                        guiD.printNotification(atc.requestTrade(tradeKindI, userId1, userId2, itemid1, itemid2, numLentBeforeBorrow, tradeTypeS));
                    }
                    else{
                        guiD.printNotification(sm.tryAgainMsgForWrongInput());
                    }
                }
                else{
                    guiD.printNotification(sm.tryAgainMsgForWrongFormatInput());
                }
                guiD.runSave();
                guiD.closeWindow(rootPanel);
            }
        });
    }
    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow){
        JFrame frame = new JFrame("RequestATradeWindow");
        frame.setContentPane(new RequestATradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }

}
