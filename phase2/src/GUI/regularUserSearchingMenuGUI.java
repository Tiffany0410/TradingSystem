package GUI;

import javax.swing.*;

public class regularUserSearchingMenuGUI {
    private JPanel rootPanel;
    private JButton searchByXXXButton;

    public void run() {
        JFrame frame = new JFrame("regularUserSearchingMenuGUI");
        frame.setContentPane(new regularUserSearchingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton searchByXXXButton1;
    private JButton searchByXXXButton2;
    private JButton backButton;
}
