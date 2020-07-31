package gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userInputGUI {
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton OKButton;
    private JButton cancelButton;
    private javax.swing.JLabel JLabel;

    public userInputGUI(String string) {
        JLabel.setText(string);
        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public void run(String string) {
        JFrame frame = new JFrame("userInputGUI");
        frame.setContentPane(new userInputGUI(string).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
