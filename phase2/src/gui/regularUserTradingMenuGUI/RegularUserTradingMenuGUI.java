package gui.regularUserTradingMenuGUI;

import javax.swing.*;

public class RegularUserTradingMenuGUI {
    private JPanel rootPanel;
    private JButton requestATradeButton;
    private JButton respondToTradeRequestsButton;
    private JButton viewOpenTradesButton;
    private JButton viewClosedTradesButton;
    private JButton confirmThatATradeButton;
    private JButton seeTopThreeMostButton;
    private JButton viewTransactionsThatHaveButton;
    private JButton suggestionForTheMostButton;
    private JButton backButton;

    public RegularUserTradingMenuGUI(){


    }

    public void run() {
        JFrame frame = new JFrame("regularUserTradingMenuGUI");
        frame.setContentPane(new RegularUserTradingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
