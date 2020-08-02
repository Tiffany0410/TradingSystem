package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.maincontrollers.AdminUserController;
import controllers.regularusersubcontrollers.RegularUserSearchingMenuController;
import gui.adminuser_menus_gui.*;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
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

public class GUIDemo {
    private UserManager userManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private ItemManager itemManager;
    private FeedbackManager feedbackManager;
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;
    private ActionManager actionManager;
    private AdminUserManagerUsersController adminUserManagerUsersController;
    private GUIUserInputInfo guiUserInputInfo;
    private String tempUsername;
    private SystemMessage systemMessage;



    public GUIDemo(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
                   ItemManager itemManager, FeedbackManager feedbackManager,
                   AccountCreator accountCreator, LoginValidator loginValidator, ActionManager actionManager){

        this.userManager = userManager;
        this.meetingManager = meetingManager;
        this.tradeManager = tradeManager;
        this.itemManager = itemManager;
        this.feedbackManager = feedbackManager;
        this.accountCreator = accountCreator;
        this.loginValidator = loginValidator;
        this.actionManager = actionManager;
        this.guiUserInputInfo = new GUIUserInputInfo();
        this.systemMessage = new SystemMessage(this);

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
        RegularUserCreateAccountGUI regularUserCreateAccountGUI = new RegularUserCreateAccountGUI(this.accountCreator, this);
        regularUserCreateAccountGUI.run(this.accountCreator, this);
    }

    public void runAdminUserMainMenu() {

        AdminUserController adminUserController = new AdminUserController(this.accountCreator, this.userManager,
                this.itemManager, this.feedbackManager, this.tradeManager, this.actionManager, this.getTempUsername());
        this.adminUserManagerUsersController = new AdminUserManagerUsersController( this.accountCreator,
                this.userManager, this.itemManager, this.tradeManager,this.actionManager, this.getTempUsername());

        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this);
        adminUserMainMenuGUI.run(this);
    }

    public void runRegularUserMainMenu(Boolean guest) {
        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI();
        regularUserMainMenuGUI.run();
    }


    public void runAdminUserManageUsersSubMenu() {
        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI(
                this.adminUserManagerUsersController, this, this.guiUserInputInfo);
        adminUserManageUsersSubMenuGUI.run(this.adminUserManagerUsersController, this, this.guiUserInputInfo);
    }

    public void runAdminUserEditThresholdsSubMenu() {
        AdminUserEditThresholdsSubMenu adminUserEditThresholdsSubMenu = new AdminUserEditThresholdsSubMenu();
        adminUserEditThresholdsSubMenu.run();

    }

    public void runAdminUserHistoricalActionsSubMenu() {
        AdminUserHistoricalActionsSubMenu adminUserHistroicalActionsSubMenu = new AdminUserHistoricalActionsSubMenu(this);
        adminUserHistroicalActionsSubMenu.run(this);
    }

    public void runAdminUserOtherSubMenu() {
        AdminUserOtherSubMenuGUI adminUserOtherSubMenuGUI = new AdminUserOtherSubMenuGUI(this);
        adminUserOtherSubMenuGUI.run(this);
    }

    public void runAdminUserCreateAccount() {
        AdminUserCreateAccountGUI adminUserCreateAccountGUI = new AdminUserCreateAccountGUI(accountCreator, this);
        adminUserCreateAccountGUI.run(accountCreator, this);
    }


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

    public void runRegularUserSearchingMenuGUI() {
        RegularUserSearchingMenuController regularUserSearchingMenuController = new RegularUserSearchingMenuController(
                this.tradeManager, this.meetingManager, this.userManager, this.itemManager, this.actionManager,
                this.systemMessage, this.tempUsername);
    }
}
