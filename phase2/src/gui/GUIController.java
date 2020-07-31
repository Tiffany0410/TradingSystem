package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import gui.adminuser_menus_gui.*;
import gui.regularuser_main_menu_gui.RegularUserMainMenuGUI;
import gui.trading_system_init_menu_gui.LoginGUI;
import gui.trading_system_init_menu_gui.RegularUserCreateAccountGUI;
import gui.trading_system_init_menu_gui.TradingSystemInitMenuGUI;
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
    private DisplaySystem displaySystem;
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;


    public GUIController(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
               ItemManager itemManager, FeedbackManager feedbackManager, DisplaySystem displaySystem,
               AccountCreator accountCreator, LoginValidator loginValidator){

        this.userManager = userManager;
        this.meetingManager = meetingManager;
        this.tradeManager = tradeManager;
        this.itemManager = itemManager;
        this.feedbackManager = feedbackManager;
        this.displaySystem = displaySystem;
        this.accountCreator = accountCreator;
        this.loginValidator = loginValidator;
    }

    public void runTradingSystemInitMenuGUI(){
        TradingSystemInitMenuGUI tradingSystemInitMenuGUI = new TradingSystemInitMenuGUI(this);
        tradingSystemInitMenuGUI.run(this);
    }

    public void runLogin(){
        LoginGUI login = new LoginGUI(loginValidator, this);
        login.run(loginValidator,this);
    }


    public void runRegularUserCreateAccount() {
        RegularUserCreateAccountGUI regularUserCreateAccountGUI = new RegularUserCreateAccountGUI(this.accountCreator, this);
        regularUserCreateAccountGUI.run(this.accountCreator, this);
    }

    public void runAdminUserMainMenu() {
        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this);
        adminUserMainMenuGUI.run(this);
    }

    public void runRegularUserMainMenu(Boolean guest) {
        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI();
        regularUserMainMenuGUI.run();
    }


    public void runAdminUserManageUsersSubMenu() {
        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI();
        adminUserManageUsersSubMenuGUI.run();
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
        AdminUserOtherSubMenuGUI adminUserOtherSubMenuGUI = new AdminUserOtherSubMenuGUI();
        adminUserOtherSubMenuGUI.run();
    }
}
