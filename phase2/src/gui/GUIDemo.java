package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.adminusersubcontrollers.*;
import controllers.regularusersubcontrollers.*;
import gui.adminuser_menus_gui.*;
import gui.regularuser_account_menus_gui.RegularUserAccountMainMenuGUI;
import gui.regularuser_account_menus_gui.RegularUserAccountSettingsMenuGUI;
import gui.regularuser_account_menus_gui.RegularUserFollowMenuGUI;
import gui.regularuser_account_menus_gui.RegularUserManageItemsMenuGUI;
import gui.regularuser_community_menu_gui.RegularUserCommunityMenuGUI;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import gui.regularuser_meeting_menu_gui.RegularUserMeetingMenuGUI;
import gui.regularuser_trading_menu_gui.RegularUserTradingMenuGUI;
import gui.trading_system_init_menu_gui.LoginGUI;
import gui.trading_system_init_menu_gui.RegularUserCreateAccountGUI;
import gui.trading_system_init_menu_gui.TradingSystemInitMenuGUI;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUIDemo {
    // Managers
    private UserManager userManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private ItemManager itemManager;
    private FeedbackManager feedbackManager;
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;
    private ActionManager actionManager;

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

    // Other variables
    private GUIUserInputInfo guiUserInputInfo;
    private String tempUsername;
    private SystemMessage systemMessage;
    private String partsOfUserAlert;
    private String partsOfAdminAlert;
    private List<Integer> thresholdValues;


    public GUIDemo(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
                   ItemManager itemManager, FeedbackManager feedbackManager, AccountCreator accountCreator,
                   LoginValidator loginValidator, ActionManager actionManager, String partsOfUserAlert,
                   String partsOfAdminAlert, List<Integer> thresholdValues){

        // get or create manager
        this.userManager = userManager;
        this.meetingManager = meetingManager;
        this.tradeManager = tradeManager;
        this.itemManager = itemManager;
        this.feedbackManager = feedbackManager;
        this.accountCreator = accountCreator;
        this.loginValidator = loginValidator;
        this.actionManager = actionManager;

        // other variables
        this.thresholdValues = thresholdValues;
        this.partsOfUserAlert = partsOfUserAlert;
        this.partsOfAdminAlert = partsOfAdminAlert;

        // create new object
        this.guiUserInputInfo = new GUIUserInputInfo();
        this.systemMessage = new SystemMessage();
        this.adminUserOtherInfoChecker = new AdminUserOtherInfoChecker(this.actionManager, this.userManager);
        this.regularUserDateTimeChecker = new RegularUserDateTimeChecker();



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

        this.adminUserManagerUsersController = new AdminUserManagerUsersController(this.userManager, this.itemManager,
                this.actionManager, this.systemMessage,this.adminUserOtherInfoChecker,this.getTempUsername());

        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this);
        adminUserMainMenuGUI.run(this);
    }

    public void runAdminUserManageUsersSubMenu() {
        AdminUserManagerUsersController adminUserManagerUsersController = new AdminUserManagerUsersController(
                this.userManager, this.itemManager, this.actionManager, this.systemMessage,
                this.adminUserOtherInfoChecker, this.getTempUsername());

        RegularUserIDChecker regularUserIDChecker = new RegularUserIDChecker(this.tradeManager, this.meetingManager,
                this.userManager, this.itemManager, this.getTempUsername());

        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI(
                adminUserManagerUsersController, this, this.guiUserInputInfo, this.systemMessage,
                regularUserIDChecker, this.adminUserOtherInfoChecker);
        adminUserManageUsersSubMenuGUI.run(adminUserManagerUsersController, this, this.guiUserInputInfo,
                this.systemMessage, regularUserIDChecker, this.adminUserOtherInfoChecker);
    }

    public void runAdminUserEditThresholdsSubMenu() {
        AdminUserEditThresholdsController adminUserEditThresholdsController = new AdminUserEditThresholdsController(
                this.actionManager, this.userManager, this.systemMessage, this.getTempUsername(), this.thresholdValues);

        AdminUserEditThresholdsSubMenuGUI adminUserEditThresholdsSubMenuGUI = new AdminUserEditThresholdsSubMenuGUI(this,
                this.guiUserInputInfo,adminUserEditThresholdsController, this.systemMessage);
        adminUserEditThresholdsSubMenuGUI.run(this, this.guiUserInputInfo,adminUserEditThresholdsController,
                this.systemMessage);

    }

    public void runAdminUserHistoricalActionsSubMenu() {
        AdminUserHistoricalActionController adminUserHistoricalActionController = new
                AdminUserHistoricalActionController(this.userManager, this.itemManager, this.tradeManager,
                this.meetingManager, this.actionManager, this.feedbackManager, this.getTempUsername());

        AdminUserHistoricalActionsSubMenu adminUserHistroicalActionsSubMenu = new AdminUserHistoricalActionsSubMenu(
                this, this.systemMessage, adminUserHistoricalActionController);
        adminUserHistroicalActionsSubMenu.run(this, this.systemMessage, adminUserHistoricalActionController);
    }

    public void runAdminUserOtherSubMenu() {
        AdminUserOtherSubMenuGUI adminUserOtherSubMenuGUI = new AdminUserOtherSubMenuGUI(this);
        adminUserOtherSubMenuGUI.run(this);
    }

    public void runAdminUserCreateAccount() {
        AdminUserOtherActionsController adminUserOtherActionsController = new AdminUserOtherActionsController(
                this.userManager, this.actionManager, this.getTempUsername());

        AdminUserCreateAccountGUI adminUserCreateAccountGUI = new AdminUserCreateAccountGUI(this.accountCreator, this,
                this.systemMessage, adminUserOtherActionsController);
        adminUserCreateAccountGUI.run(this.accountCreator, this,
                this.systemMessage, adminUserOtherActionsController);
    }

    //Regular User menu gui start
    public void runRegularUserMainMenu(Boolean guest) {
        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI(guest, this.systemMessage, this, );
        regularUserMainMenuGUI.run();
    }

    public void runRegularUserAccountFeedBackMenu(){
        RegularUserFollowMenuGUI regularUserFollowMenuGUI = new RegularUserFollowMenuGUI();
        regularUserFollowMenuGUI.run();
    }

    public void runRegularUserAccountManageItemsMenu(){
        RegularUserManageItemsMenuGUI regularUserManageItemsMenuGUI = new RegularUserManageItemsMenuGUI();
        regularUserManageItemsMenuGUI.run();
    }

    public void runRegularUserAccountSettingsMenu(){
        RegularUserAccountSettingsMenuGUI regularUserAccountSettingsMenuGUI = new RegularUserAccountSettingsMenuGUI();
        regularUserAccountSettingsMenuGUI.run();

    }

    public void runRegularUserAccountMainMenuGUI(){
        RegularUserAccountMainMenuGUI regularUserAccountMainMenuGUI = new RegularUserAccountMainMenuGUI();
        regularUserAccountMainMenuGUI.run();

    }

    public void runRegularUserTradingMenuGUI(){
        RegularUserTradingMenuGUI regularUserTradingMenuGUI = new RegularUserTradingMenuGUI();
        regularUserTradingMenuGUI.run();
    }

    public void runRegularUserCommunityMenuGUI(){
        RegularUserCommunityMenuGUI regularUserCommunityMenuGUI = new RegularUserCommunityMenuGUI();
        regularUserCommunityMenuGUI.run();
    }

    public void runRegularUserMeetingMenu(){
        RegularUserMeetingMenuGUI regularUserMeetingMenuGUI = new RegularUserMeetingMenuGUI();
        regularUserMeetingMenuGUI.run();
    }

    public void runRegularUserSearchingMenuGUI() {
        RegularUserSearchingMenuController regularUserSearchingMenuController = new RegularUserSearchingMenuController(
                this.tradeManager, this.meetingManager, this.userManager, this.itemManager, this.tempUsername);
    }

    //TODO: Regular User menu gui end


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

}

