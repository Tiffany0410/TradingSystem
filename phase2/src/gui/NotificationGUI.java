package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NotificationGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea textArea;


    public NotificationGUI(String string) {
        textArea.setText(string);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setBackground(new Color(242,242,242));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonCancel);


        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        buttonCancel.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void run(String string) {
        NotificationGUI dialog = new NotificationGUI(string);
        dialog.setPreferredSize(new Dimension(300, 300));
        dialog.pack();
        dialog.setVisible(true);
    }
}
