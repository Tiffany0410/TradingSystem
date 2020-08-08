package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.adminusersubcontrollers.*;
import controllers.regularusersubcontrollers.*;
import gateway.FilesReaderWriter;
import gui.adminuser_menus_gui.*;
import gui.regularuser_account_menus_gui.RegularUserAccountMainMenuGUI;
import gui.regularuser_account_menus_gui.account_settings.RegularUserAccountSettingsMenuGUI;
import gui.regularuser_account_menus_gui.follow_menu.RegularUserFollowMenuGUI;
import gui.regularuser_account_menus_gui.manage_items.RegularUserManageItemsMenuGUI;
import gui.regularuser_community_menu_gui.RegularUserCommunityMenuGUI;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import gui.regularuser_meeting_menu_gui.RegularUserMeetingMenuGUI;
import gui.regularuser_searching_menu_gui.*;
import gui.regularuser_trading_menu_gui.RegularUserTradingMenuGUI;
import gui.trading_system_init_menu_gui.LoginGUI;
import gui.trading_system_init_menu_gui.RegularUserCreateAccountGUI;
import gui.trading_system_init_menu_gui.TradingSystemInitMenuGUI;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.messagemanger.MessageManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class GUIDemo {
    // Managers
    private UserManager userManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private ItemManager itemManager;
    private FeedbackManager feedbackManager;
    private ActionManager actionManager;
    private MessageManager messageManager;

    // Admin controllers
    private AdminUserEditThresholdsController adminUserEditThresholdsController;
    private AdminUserHistoricalActionController adminUserHistoricalActionController;
    private AdminUserManagerUsersController adminUserManagerUsersController;
    private AdminUserOtherActionsController adminUserOtherActionsController;
    private AdminUserOtherInfoChecker adminUserOtherInfoChecker;

    // Regular controllers
    private RegularUserAccountMenuController regularUserAccountMenuController;
    private RegularUserCommunityMenuController regularUserCommunityMenuController;
    private RegularUserDateTimeChecker regularUserDateTimeChecker;
    private RegularUserIDChecker regularUserIDChecker;
    private RegularUserMeetingMenuController regularUserMeetingMenuController;
    private RegularUserOtherInfoChecker regularUserOtherInfoChecker;
    private RegularUserSearchingMenuController regularUserSearchingMenuController;
    private RegularUserThresholdController regularUserThresholdController;
    private RegularUserTradingMenuController regularUserTradingMenuController;

    //other controllers
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;

    // Other variables
    private GUIUserInputInfo guiUserInputInfo;
    private String tempUsername;
    private SystemMessage systemMessage;
    private String partsOfUserAlert;
    private String partsOfAdminAlert;
    private ArrayList<Integer> thresholdValues;
    private boolean isGuest;

    // Gateway
    private FilesReaderWriter frw;

    public GUIDemo(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
                   ItemManager itemManager, FeedbackManager feedbackManager, MessageManager messageManager,
                   AccountCreator accountCreator, LoginValidator loginValidator, ActionManager actionManager,
                   String partsOfUserAlert, String partsOfAdminAlert, ArrayList<Integer> thresholdValues){

        // get or create manager
        this.userManager = userManager;
        this.meetingManager = meetingManager;
        this.tradeManager = tradeManager;
        this.itemManager = itemManager;
        this.feedbackManager = feedbackManager;
        this.accountCreator = accountCreator;
        this.loginValidator = loginValidator;
        this.actionManager = actionManager;
        this.messageManager = messageManager;

        // other variables
        this.thresholdValues = thresholdValues;
        this.partsOfUserAlert = partsOfUserAlert;
        this.partsOfAdminAlert = partsOfAdminAlert;
        this.isGuest = false;

        // create new object
        this.guiUserInputInfo = new GUIUserInputInfo();
        this.systemMessage = new SystemMessage();
        this.adminUserOtherInfoChecker = new AdminUserOtherInfoChecker(this.actionManager, this.userManager);
        this.regularUserDateTimeChecker = new RegularUserDateTimeChecker();

        // Create new FilesReaderWriter
        this.frw = new FilesReaderWriter();

    }

    public UserManager getUserManager() {
        return userManager;
    }
    public FeedbackManager getFeedbackManager(){
        return feedbackManager;
    }

    public void runTradingSystemInitMenuGUI(){
        TradingSystemInitMenuGUI tradingSystemInitMenuGUI = new TradingSystemInitMenuGUI(this);
        tradingSystemInitMenuGUI.run(this);
    }

    public void runLogin(){
        LoginGUI login = new LoginGUI(this.loginValidator, this);
        login.run(this.loginValidator,this);
    }


    public void runRegularUserCreateAccount() {
        RegularUserCreateAccountGUI regularUserCreateAccountGUI = new RegularUserCreateAccountGUI(this.accountCreator,
                this);
        regularUserCreateAccountGUI.run(this.accountCreator, this);
    }


    // Start of run admin menus
    public void runAdminUserMainMenu() {
        // Create all admin user controller
        this.adminUserManagerUsersController = new AdminUserManagerUsersController(this.userManager, this.itemManager,
                this.actionManager, this.systemMessage,this.getTempUsername());

        this.adminUserEditThresholdsController = new AdminUserEditThresholdsController(
                this.actionManager, this.userManager, this.systemMessage, this.getTempUsername(), this.thresholdValues);

        this.adminUserHistoricalActionController = new
                AdminUserHistoricalActionController(this.userManager, this.itemManager, this.tradeManager,
                this.meetingManager, this.actionManager, this.feedbackManager, this.getTempUsername());

        this.adminUserOtherActionsController = new AdminUserOtherActionsController(
                this.userManager, this.actionManager, this.getTempUsername());

        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this, this.partsOfAdminAlert);
        adminUserMainMenuGUI.run(this, this.partsOfAdminAlert);
    }

    public void runAdminUserManageUsersSubMenu() {

        RegularUserIDChecker regularUserIDChecker = new RegularUserIDChecker(this.tradeManager, this.meetingManager,
                this.userManager, this.itemManager);

        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI(
                this.adminUserManagerUsersController, this, this.guiUserInputInfo, this.systemMessage,
                regularUserIDChecker, this.adminUserOtherInfoChecker);

        adminUserManageUsersSubMenuGUI.run(this.adminUserManagerUsersController, this, this.guiUserInputInfo,
                this.systemMessage, regularUserIDChecker, this.adminUserOtherInfoChecker);
    }

    public void runAdminUserEditThresholdsSubMenu() {

        AdminUserEditThresholdsSubMenuGUI adminUserEditThresholdsSubMenuGUI = new AdminUserEditThresholdsSubMenuGUI(this,
                this.guiUserInputInfo, this.adminUserEditThresholdsController, this.systemMessage);

        adminUserEditThresholdsSubMenuGUI.run(this, this.guiUserInputInfo, this.adminUserEditThresholdsController,
                this.systemMessage);

    }

    public void runAdminUserHistoricalActionsSubMenu() {


        AdminUserHistoricalActionsSubMenu adminUserHistroicalActionsSubMenu = new AdminUserHistoricalActionsSubMenu(
                this, this.systemMessage, this.adminUserHistoricalActionController);
        adminUserHistroicalActionsSubMenu.run(this, this.systemMessage, this.adminUserHistoricalActionController);
    }

    public void runAdminUserOtherSubMenu() {
        AdminUserOtherSubMenuGUI adminUserOtherSubMenuGUI = new AdminUserOtherSubMenuGUI(this);
        adminUserOtherSubMenuGUI.run(this);
    }

    public void runAdminUserCreateAccount() {

        AdminUserCreateAccountGUI adminUserCreateAccountGUI = new AdminUserCreateAccountGUI(this.accountCreator, this,
                this.systemMessage, this.adminUserOtherActionsController);
        adminUserCreateAccountGUI.run(this.accountCreator, this,
                this.systemMessage, this.adminUserOtherActionsController);
    }


    //Regular User menu gui start
    public void runRegularUserMainMenu(Boolean guest) {
        this.isGuest = guest;

        // create all regular user controller
        this.regularUserAccountMenuController = new RegularUserAccountMenuController(
                this.tradeManager, this.userManager, this.itemManager, this.actionManager, this.systemMessage, this.getTempUsername());

        this.regularUserCommunityMenuController = new RegularUserCommunityMenuController(this.userManager,
                this.actionManager, this.feedbackManager, this.messageManager, this.getTempUsername());

        this.regularUserIDChecker = new RegularUserIDChecker(this.tradeManager, this.meetingManager,
                this.userManager, this.itemManager);

        this.regularUserMeetingMenuController = new RegularUserMeetingMenuController(this.tradeManager,
                this.meetingManager, this.actionManager, this.userManager.usernameToID(this.getTempUsername()));

        this.regularUserOtherInfoChecker = new RegularUserOtherInfoChecker(this.userManager);

        this.regularUserSearchingMenuController = new RegularUserSearchingMenuController(this.tradeManager,
                this.meetingManager, this.actionManager, this.userManager, this.itemManager, this.feedbackManager, this.getTempUsername());

        this.regularUserThresholdController = new RegularUserThresholdController(
                this.tradeManager, this.meetingManager, this.userManager, this.getTempUsername());

        this.regularUserTradingMenuController = new RegularUserTradingMenuController(this.tradeManager,
                this.meetingManager, this.userManager, this.itemManager, this.actionManager, this.getTempUsername(),
                this.systemMessage, this.regularUserThresholdController, this.regularUserOtherInfoChecker,
                this.regularUserIDChecker);



        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI(this.isGuest, this.systemMessage, this,
                regularUserAccountMenuController, regularUserThresholdController, this.getTempUsername(), this.userManager,
                this.partsOfUserAlert, this.thresholdValues);
        regularUserMainMenuGUI.run(this.isGuest, this.systemMessage, this,
                regularUserAccountMenuController, regularUserThresholdController, this.getTempUsername(), this.userManager,
                this.partsOfUserAlert, this.thresholdValues);
    }

    public void runRegularUserAccountFollowMenu(){
        RegularUserFollowMenuGUI regularUserFollowMenuGUI = new RegularUserFollowMenuGUI(this,
                this.regularUserAccountMenuController, this.regularUserIDChecker, this.systemMessage);
        regularUserFollowMenuGUI.run(this,
                this.regularUserAccountMenuController, this.regularUserIDChecker, this.systemMessage);
    }

    public void runRegularUserAccountManageItemsMenu(){
        RegularUserManageItemsMenuGUI regularUserManageItemsMenuGUI = new RegularUserManageItemsMenuGUI(this.isGuest,
                this.systemMessage,this, this.guiUserInputInfo, this.regularUserIDChecker,
                this.regularUserAccountMenuController, this.regularUserOtherInfoChecker);
        regularUserManageItemsMenuGUI.run(this.isGuest, this.systemMessage,this, this.guiUserInputInfo,
                this.regularUserIDChecker, this.regularUserAccountMenuController, this.regularUserOtherInfoChecker);
    }

    public void runRegularUserAccountSettingsMenu(){
        RegularUserAccountSettingsMenuGUI regularUserAccountSettingsMenuGUI = new RegularUserAccountSettingsMenuGUI(
                this.regularUserAccountMenuController, this.systemMessage, this.regularUserIDChecker,
                this,this.adminUserOtherInfoChecker);
        regularUserAccountSettingsMenuGUI.run(this.regularUserAccountMenuController, this.systemMessage,
                this.regularUserIDChecker, this, this.adminUserOtherInfoChecker);

    }

    public void runRegularUserAccountMainMenuGUI(){
        RegularUserAccountMainMenuGUI regularUserAccountMainMenuGUI = new RegularUserAccountMainMenuGUI(this.isGuest,
                this.systemMessage, this, this.regularUserAccountMenuController);
        regularUserAccountMainMenuGUI.run(this.isGuest, this.systemMessage, this, this.regularUserAccountMenuController);

    }

    public void runRegularUserTradingMenuGUI() {

        int maxNumTransactionAWeek = thresholdValues.get(0);
        int numLentBeforeBorrow =thresholdValues.get(2);

        RegularUserTradingMenuGUI regularUserTradingMenuGUI = new RegularUserTradingMenuGUI(this,
                this.regularUserTradingMenuController, this.systemMessage,maxNumTransactionAWeek, numLentBeforeBorrow,
                this.guiUserInputInfo, this.regularUserIDChecker, this.regularUserOtherInfoChecker);
        regularUserTradingMenuGUI.run(this, this.regularUserTradingMenuController, this.systemMessage,
                maxNumTransactionAWeek, numLentBeforeBorrow, this.guiUserInputInfo, this.regularUserIDChecker,
                this.regularUserOtherInfoChecker);
    }

    public void runRegularUserCommunityMenuGUI(){
        RegularUserCommunityMenuGUI regularUserCommunityMenuGUI = new RegularUserCommunityMenuGUI(this.isGuest, this,
                this.regularUserCommunityMenuController, this.systemMessage,this.guiUserInputInfo,
                this.regularUserIDChecker,this.regularUserOtherInfoChecker);
        regularUserCommunityMenuGUI.run(this.isGuest, this, this.regularUserCommunityMenuController,
                this.systemMessage,this.guiUserInputInfo, this.regularUserIDChecker,this.regularUserOtherInfoChecker);
    }

    public void runRegularUserMeetingMenu(){

        int maxNumTPEdits = thresholdValues.get(3);
        RegularUserMeetingMenuGUI regularUserMeetingMenuGUI = new RegularUserMeetingMenuGUI(this,
                this.regularUserMeetingMenuController, this.systemMessage,maxNumTPEdits,this.guiUserInputInfo,
                this.regularUserIDChecker,this.regularUserDateTimeChecker);
        regularUserMeetingMenuGUI.run(this, this.regularUserMeetingMenuController, this.systemMessage,maxNumTPEdits,
                this.guiUserInputInfo, this.regularUserIDChecker,this.regularUserDateTimeChecker);
    }

    public void runRegularUserSearchingMenuGUI() {
        RegularUserSearchingMenuGUI regularUserSearchingMenuGUI = new RegularUserSearchingMenuGUI(this);
        regularUserSearchingMenuGUI.run(this);
    }


    public void runRegularUserSearchingItemsSubMenu(){
        RegularUserSearchingItemsSubMenuGUI regularUserSearchingItemsSubMenuGUI = new RegularUserSearchingItemsSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.guiUserInputInfo, this.itemManager, this.systemMessage);
        regularUserSearchingItemsSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.guiUserInputInfo, this.itemManager, this.systemMessage);
    }

    public void runRegularUserSearchingMeetingsSubMenu(){
        RegularUserSearchingMeetingsSubMenuGUI regularUserSearchingMeetingsSubMenuGUI = new
                RegularUserSearchingMeetingsSubMenuGUI(this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingMeetingsSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }

    public void runRegularUserSearchingTradesSubMenu(){
        RegularUserSearchingTradesSubMenuGUI regularUserSearchingTradesSubMenuGUI = new RegularUserSearchingTradesSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingTradesSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }

    public void runRegularUserSearchingUsersSubMenu(){
        RegularUserSearchingUsersSubMenuGUI regularUserSearchingUsersSubMenuGUI = new RegularUserSearchingUsersSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingUsersSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }
    //Regular User menu gui end


    public void setTempUsername(String username){
        this.tempUsername = username;
    }

    public String getTempUsername(){
        return this.tempUsername;
    }

    public String getUserInput(){return this.guiUserInputInfo.getTempUserInput();}

    public void printNotification(String string) {
        NotificationGUI notificationGUI = new NotificationGUI(string);
        notificationGUI.run(string);
    }

    public String getInPut(String string) {
        UserInputGUI userInputGUI = new UserInputGUI(string, this.guiUserInputInfo);
        userInputGUI.run(string, this.guiUserInputInfo);
        return this.guiUserInputInfo.getTempUserInput();
    }

    //moved from account settings menu gui's helper method section
    public String getLongInput(String str, GUIUserInputInfo guiUserInputInfo){
        UserInputLongMsgGUI userInputLongMsgGUI = new UserInputLongMsgGUI(str, guiUserInputInfo);
        userInputLongMsgGUI.run(str, guiUserInputInfo);
        String userResponse = guiUserInputInfo.getTempUserInput();
        return userResponse;
    }

    public void closeWindow(JPanel panel){
        Window window = SwingUtilities.getWindowAncestor(panel);
        window.dispose();
    }

    /**
     * Update each related Managers
     *
     */
    public void runSave()  {
        try {
            //Save UserManager
            frw.saveManagerToFile(userManager, "./configs/serializedmanagersfiles/SerializedUserManager.ser");
            //Save ItemManager
            frw.saveManagerToFile(itemManager, "./configs/serializedmanagersfiles/SerializedItemManager.ser");
            //Save TradeManager
            frw.saveManagerToFile(tradeManager, "./configs/serializedmanagersfiles/SerializedTradeManager.ser");
            //Save MeetingManager
            frw.saveManagerToFile(meetingManager, "./configs/serializedmanagersfiles/SerializedMeetingManager.ser");
            //Save ActionManager
            frw.saveManagerToFile(actionManager, "./configs/serializedmanagersfiles/SerializedActionManager.ser");
            //Save FeedbackManager
            frw.saveManagerToFile(feedbackManager, "./configs/serializedmanagersfiles/SerializedFeedbackManager.ser");
            //Save MessageManager
            frw.saveManagerToFile(messageManager, "./configs/serializedmanagersfiles/SerializedMessageManager.ser");
            //Save thresholdValues
            frw.saveThresholdValuesToCSVFile(thresholdValues, "./configs/thresholdvaluesfile/ThresholdValues.csv");
        } catch (IOException e) {
            this.printNotification("File cannot be found...");
        }

    }
}



