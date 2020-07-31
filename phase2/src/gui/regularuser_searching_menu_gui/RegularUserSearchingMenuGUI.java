package gui.regularuser_searching_menu_gui;

import javax.swing.*;

public class RegularUserSearchingMenuGUI {
    private JPanel rootPanel;
    private JButton searchByXXXButton;

    public void run() {
        JFrame frame = new JFrame("regularUserSearchingMenuGUI");
        frame.setContentPane(new RegularUserSearchingMenuGUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton searchByXXXButton1;
    private JButton searchByXXXButton2;
    private JButton backButton;
}
