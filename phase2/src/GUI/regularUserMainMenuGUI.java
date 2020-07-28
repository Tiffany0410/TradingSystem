package GUI;

import javax.swing.*;
import java.awt.event.*;

public class regularUserMainMenuGUI extends JDialog {
    private JPanel contentPane;
    private JTextArea hiWelcomeToTheTextArea;
    private JButton accountInfoButton;
    private JButton tradingInfoButton;
    private JButton meetingInfoButton;
    private JButton searchingInfoButton;
    private JButton buttonBack;

    public regularUserMainMenuGUI() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonBack);


        buttonBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onBack();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onBack();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        accountInfoButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        tradingInfoButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        meetingInfoButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }


    private void onBack() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        regularUserMainMenuGUI dialog = new regularUserMainMenuGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
