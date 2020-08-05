package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserInputLongMsgGUI {
    private JTextArea textArea;
    private JButton OKButton;
    private JButton cancelButton;
    private JPanel rootPanel;
    private JLabel label;

    public UserInputLongMsgGUI(String str, GUIUserInputInfo guiUserInputInfo){
        label.setText(str);
        textArea.setEditable(true);
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
                guiUserInputInfo.tempSaveUserInput(textArea.getText());
                // TODO: Need method to close this window

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


    public void run(String string, GUIUserInputInfo guiUserInputInfo) {
        JFrame frame = new JFrame("userInputLongMsgGUI");
        frame.setContentPane(new UserInputLongMsgGUI(string, guiUserInputInfo).rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
