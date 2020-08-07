package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInputGUI {
    private JPanel rootPanel;
    private JTextField textField1;
    private JButton OKButton;
    private JButton cancelButton;
    private JTextArea textArea;

    public UserInputGUI(String string, GUIUserInputInfo guiUserInputInfo) {
        textArea.setText(string);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));

        OKButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiUserInputInfo.tempSaveUserInput(textField1.getText());
                //close this window
                Window window = SwingUtilities.getWindowAncestor(rootPanel);
                window.dispose();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                //close this window
                Window window = SwingUtilities.getWindowAncestor(rootPanel);
                window.dispose();
            }
        });
    }

    public void run(String string, GUIUserInputInfo guiUserInputInfo) {
        JFrame frame = new JFrame("userInputGUI");
        frame.setContentPane(new UserInputGUI(string, guiUserInputInfo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
