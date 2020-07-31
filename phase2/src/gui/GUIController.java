package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.adminusersubcontrollers.AdminUserManagerUsersController;
import controllers.maincontrollers.AdminUserController;
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
import presenter.DisplaySystem;

public class GUIController {
    private UserManager userManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private ItemManager itemManager;
    private FeedbackManager feedbackManager;
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;
    private AdminUserController adminUserController;
    private ActionManager actionManager;
    private String tempUserInput;
    private String tempUsername;
    private AdminUserManagerUsersController adminUserManagerUsersController;


    public GUIController(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
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
        this.tempUserInput = "";
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
        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this);
        adminUserMainMenuGUI.run(this);
        this.adminUserController = new AdminUserController(this.accountCreator,  this.userManager,
                this.itemManager, this.feedbackManager, this.actionManager, this.getTempUsername() );
        this.adminUserManagerUsersController = new AdminUserManagerUsersController( this.accountCreator,
                this.userManager, this.itemManager, this.actionManager, this.getTempUsername());
    }

    public void runRegularUserMainMenu(Boolean guest) {
        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI();
        regularUserMainMenuGUI.run();
    }


    public void runAdminUserManageUsersSubMenu() {
        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI(
                this.adminUserManagerUsersController, this);
        adminUserManageUsersSubMenuGUI.run(this.adminUserManagerUsersController, this);
    }

    public void runAdminUserEditThresholdsSubMenu() {
        AdminUserEditThresholdsSubMenu adminUserEditThresholdsSubMenu = new AdminUserEditThresholdsSubMenu();
        adminUserEditThresholdsSubMenu.run();

    }

    public void runAdminUserHistoricalActionsSubMenu() {
        AdminUserHistroicalActionsSubMenu adminUserHistroicalActionsSubMenu = new AdminUserHistroicalActionsSubMenu(this);
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

    public void tempSaveUserInput(String text) {
        this.tempUserInput = text;
    }

    public String getTempUserInput(){
        return this.tempUserInput;
    }

    public void setTempUsername(String username){
        this.tempUsername = username;
    }

    public String getTempUsername(){
        return this.tempUsername;
    }
}
