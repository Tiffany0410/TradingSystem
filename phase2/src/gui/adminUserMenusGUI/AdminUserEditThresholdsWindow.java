package gui.adminUserMenusGUI;

import javax.swing.*;

public class AdminUserEditThresholdsWindow {
    private JPanel rootPanel;
    private JButton editTheMaxNumberButton;
    private JButton editTheMaxNumberButton1;
    private JButton editTheNumberOfButton;
    private JButton editTheMaxEditsButton;
    private JButton backButton;

    public void run() {
        JFrame frame = new JFrame("adminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
