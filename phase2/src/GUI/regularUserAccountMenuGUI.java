package GUI;

import javax.swing.*;
import java.awt.event.*;

public class regularUserAccountMenuGUI extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea hiWelcomeToTheTextArea;
    private JButton browseAllTradableItemsButton;
    private JButton addToOwnWishButton;
    private JButton searchItemButton;
    private JButton removeFromOwnWishButton;
    private JButton removeFromOwnInventoryButton;
    private JButton requestToUnfreezeAccountButton;
    private JButton requestThatAnItemButton;
    private JButton seeMostRecentThreeButton;
    private JButton viewYourWishlistAndButton;
    private JButton setYourOnVacationButton;
    private JButton changeTradableStatusForButton;
    private JButton seeUsersInYourButton;
    private JButton changeYourHomeCityButton;
    private JButton getSuggestionsForItemButton;

    public regularUserAccountMenuGUI() {
        setContentPane(contentPane);
        //
        contentPane.setLayout(null);
        JButton button = new JButton("Testbutton!");
        contentPane.add(hiWelcomeToTheTextArea);
        hiWelcomeToTheTextArea.setBounds(10, 10 , 10, 10);
        contentPane.add(button);
        button.setBounds(10,10,40,40);
        //
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

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

    public static void main(String[] args) {
        regularUserAccountMenuGUI dialog = new regularUserAccountMenuGUI();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
