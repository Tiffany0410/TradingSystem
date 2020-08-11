package gui.regularuser_trading_menu_gui;

import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Used to show regular user trade request
 * @author Yu Xin Yan, Shi Tang
 * @version IntelliJ IDEA 2020.1
 */

public class RequestTradeWindow {
    private JRadioButton oneWayTradeRadioButton;
    private JRadioButton twoWayTradeRadioButton;
    private JRadioButton permanentRadioButton;
    private JRadioButton temporaryRadioButton;
    private JButton cancelButton;
    private JButton nextButton;
    private JPanel rootPanel;

    /**
     * Constructor of regular user request for trade window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     */
    public RequestTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                              SystemMessage sm, int numLentBeforeBorrow){

        ButtonGroup tradeKind = new ButtonGroup();
        tradeKind.add(oneWayTradeRadioButton);
        tradeKind.add(twoWayTradeRadioButton);

        ButtonGroup tradeType = new ButtonGroup();
        tradeType.add(permanentRadioButton);
        tradeType.add(temporaryRadioButton);

        nextButton.addActionListener(new ActionListener() {
            /**
             * Invoke when click button and do related operation, request for a trade
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                requestATrade(idC, guiD, atc, sm, numLentBeforeBorrow);
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            /** Invoke when click button and return to menu
             * @param e click button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
    }

    private void requestATrade(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc, SystemMessage sm, int numLentBeforeBorrow) {
        if (oneWayTradeRadioButton.isSelected() && permanentRadioButton.isSelected()){
            OneWayTradeWindow window = new OneWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, "Permanent");
            window.run(idC, guiD, atc, sm, numLentBeforeBorrow, "Permanent");
        }
        else if(oneWayTradeRadioButton.isSelected() && temporaryRadioButton.isSelected()){
            OneWayTradeWindow window = new OneWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, "Temporary");
            window.run(idC, guiD, atc, sm, numLentBeforeBorrow, "Temporary");
        }
        else if (twoWayTradeRadioButton.isSelected() && permanentRadioButton.isSelected()){
            TwoWayTradeWindow window = new TwoWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, "Permanent");
            window.run(idC, guiD, atc, sm, numLentBeforeBorrow, "Permanent");
        }
        else if (twoWayTradeRadioButton.isSelected() && temporaryRadioButton.isSelected()){
            TwoWayTradeWindow window = new TwoWayTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow, "Temporary");
            window.run(idC, guiD, atc, sm, numLentBeforeBorrow, "Temporary");
        }
        else{
            guiD.printNotification("Please select an option.");
        }
        guiD.runSave();
        guiD.closeWindow(rootPanel);
    }

    /**
     * Run trade request window
     * @param idC RegularUserIDChecker
     * @param guiD GUIDemo
     * @param atc RegularUserTradingMenuController
     * @param sm SystemMessage
     * @param numLentBeforeBorrow Number lent before borrow
     */
    public void run(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                    SystemMessage sm, int numLentBeforeBorrow){
        JFrame frame = new JFrame("Trade request");
        frame.setContentPane(new RequestTradeWindow(idC, guiD, atc, sm, numLentBeforeBorrow).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 400));
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
}
