package gui.regularUserTradingMenuGUI;

import javax.swing.*;

public class RegularUserTradingMenuGUI {
    private JPanel rootPanel;

    public void run() {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton requestATradeButton;
    private JButton respondeToTheTradeButton;
    private JButton viewOpenTradesButton;
    private JButton viewClosedTradesButton;
    private JButton confirmACompletedTradeButton;
    private JButton topThreeFrequentTradeButton;
    private JButton viewCancelledTransactionsButton;
    private JButton resonableTradeSuggestButton;
    private JButton backButton;
}
