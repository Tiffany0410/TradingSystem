package gui.regularuser_trading_menu_gui;


import controllers.regularusersubcontrollers.RegularUserIDChecker;
import controllers.regularusersubcontrollers.RegularUserTradingMenuController;
import demomanager.GUIDemo;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RequestTradeWindow {
    private JRadioButton oneWayTradeRadioButton;
    private JRadioButton twoWayTradeRadioButton;
    private JRadioButton permanentRadioButton;
    private JRadioButton temporaryRadioButton;
    private JButton cancelButton;
    private JButton nextButton;
    private JPanel rootPanel;

    public RequestTradeWindow(RegularUserIDChecker idC, GUIDemo guiD, RegularUserTradingMenuController atc,
                              SystemMessage sm, int numLentBeforeBorrow){

        ButtonGroup tradeKind = new ButtonGroup();
        tradeKind.add(oneWayTradeRadioButton);
        tradeKind.add(twoWayTradeRadioButton);

        ButtonGroup tradeType = new ButtonGroup();
        tradeType.add(permanentRadioButton);
        tradeType.add(temporaryRadioButton);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guiD.closeWindow(rootPanel);
            }
        });
    }

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
