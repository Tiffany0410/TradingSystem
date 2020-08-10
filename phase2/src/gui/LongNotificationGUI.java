package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LongNotificationGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JScrollPane scrollPane;


    public LongNotificationGUI(String string) {
        textArea1.setText(string);
        textArea1.setEditable(false);
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        textArea1.setBackground(new Color(242,242,242));

        textArea1.setVisible(true);
        scrollPane.setVisible(true);

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
        LongNotificationGUI dialog = new LongNotificationGUI(string);
        dialog.setPreferredSize(new Dimension(500, 400));
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }

}
