package gui.system_messages;

import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.*;

public class LockMessageForMaxTransactionsThreshold extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea thisOptionIsLockedTextArea;
    private SystemMessage sm;

    public LockMessageForMaxTransactionsThreshold(int maxNumTransactionAWeek) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        //Set the text as the lock message for the maximum number of transactions threshold
        thisOptionIsLockedTextArea.setText(sm.lockMessageForThreshold(maxNumTransactionAWeek));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void run(int maxNumTransactionAWeek) {
        LockMessageForMaxTransactionsThreshold dialog = new LockMessageForMaxTransactionsThreshold(maxNumTransactionAWeek);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(null);
    }
}
