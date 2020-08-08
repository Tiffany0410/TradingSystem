package gui.adminuser_menus_gui;

import controllers.adminusersubcontrollers.AdminUserEditThresholdsController;
import gui.GUIDemo;
import gui.GUIUserInputInfo;
import gui.UserInputGUI;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUserEditThresholdsSubMenuGUI {
    private JPanel rootPanel;
    private JButton editTheMaxNumberButton;
    private JButton editTheMaxNumberButton1;
    private JButton editTheNumberOfButton;
    private JButton editTheMaxEditsButton;
    private JButton backButton;

    public AdminUserEditThresholdsSubMenuGUI(GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo,
                                             AdminUserEditThresholdsController adminUserEditThresholdsController,
                                             SystemMessage systemMessage) {
        editTheMaxNumberButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberTransactions() + "\n" + "Please enter new value:";
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                try {
                    int futureValue = Integer.parseInt(guiUserInputInfo.getTempUserInput());
                    guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberTransactions(futureValue));
                }catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
                }
                guiDemo.runSave();
                //close this window
//              guiDemo.closeWindow(rootPanel);
            }
        });
        editTheMaxNumberButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMaxNumberIncompleteTransactions() + "\n" + "Please enter new value:";
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                try {
                    int futureValue = Integer.parseInt(guiUserInputInfo.getTempUserInput());
                    guiDemo.printNotification(adminUserEditThresholdsController.editMaxNumberIncompleteTransactions(futureValue));
                }catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
                }
                guiDemo.runSave();
                //close this window
                guiDemo.closeWindow(rootPanel);

            }
        });
        editTheNumberOfButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getMustLendNumber() + "\n" + "Please enter new value:";
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                try {
                    int futureValue = Integer.parseInt(guiUserInputInfo.getTempUserInput());
                    guiDemo.printNotification(adminUserEditThresholdsController.editMustLendNumber(futureValue));
                }catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
                }
                guiDemo.runSave();
                //close this window
                guiDemo.closeWindow(rootPanel);

            }
        });
        editTheMaxEditsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String string = adminUserEditThresholdsController.getEditMaxEdits() + "\n" + "Please enter new value:";
                UserInputGUI userInputGUI = new UserInputGUI(string, guiUserInputInfo);
                userInputGUI.run(string, guiUserInputInfo);

                try {
                    int futureValue = Integer.parseInt(guiUserInputInfo.getTempUserInput());
                    guiDemo.printNotification(adminUserEditThresholdsController.editMaxEdits(futureValue));
                }catch (NumberFormatException ex){
                    systemMessage.invalidNumber();
                }
                guiDemo.runSave();
                //close this window
                guiDemo.closeWindow(rootPanel);

            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                guiDemo.runAdminUserMainMenu();
                //close this window
                guiDemo.closeWindow(rootPanel);

            }
        });
    }

    public void run(GUIDemo guiDemo, GUIUserInputInfo guiUserInputInfo,
                    AdminUserEditThresholdsController adminUserEditThresholdsController,
                    SystemMessage systemMessage) {
        JFrame frame = new JFrame("AdminUserEditThresholdsSubMenu");
        frame.setContentPane(new AdminUserEditThresholdsSubMenuGUI(guiDemo, guiUserInputInfo, adminUserEditThresholdsController, systemMessage).rootPanel);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }


}
