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

    public UserInputGUI(String string, GUIDemo guiController) {
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
                //TODO：Need a method to return the value user put in the text area.
                guiController.tempSaveUserInput(textField1.getText());

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
                // TODO: Need method to close this window

            }
        });
    }

    public void run(String string, GUIDemo guiController) {
        JFrame frame = new JFrame("userInputGUI");
        frame.setContentPane(new UserInputGUI(string, guiController).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
