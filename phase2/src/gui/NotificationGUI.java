package gui;

import javax.swing.*;
import java.awt.event.*;

public class NotificationGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel Label;

    public NotificationGUI(String string) {
        Label.setText(string);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);


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
                //TODO: Closet this window
                dispose();
            }
        });
    }


    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public void run(String string) {
        NotificationGUI dialog = new NotificationGUI(string);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
