package gui.adminUserMenusGUI;

import javax.swing.*;

public class AdminUserEditThresholdsWindow {
    private JPanel rootPanel;

    public void run() {
        JFrame frame = new JFrame("adminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
