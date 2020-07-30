package GUI;

import javax.swing.*;

public class adminUserEditThresholdsWindow {
    private JPanel rootPanel;

    public void run() {
        JFrame frame = new JFrame("adminUserEditThresholdsSubMenu");
        frame.setContentPane(new adminUserEditThresholdsWindow().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
