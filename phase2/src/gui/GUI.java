package gui;

import controllers.AccountCreator;
import controllers.LoginValidator;
import gui.adminUserMenusGUI.AdminUserMainMenuGUI;
import gui.tradingSystemInitMenuGUI.LoginGUI;
import gui.tradingSystemInitMenuGUI.RegularUserCreateAccountGUI;
import gui.tradingSystemInitMenuGUI.TradingSystemInitMenuGUI;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

public class GUI {
    private UserManager userManager;
    private MeetingManager meetingManager;
    private TradeManager tradeManager;
    private ItemManager itemManager;
    private FeedbackManager feedbackManager;
    private DisplaySystem displaySystem;
    private AccountCreator accountCreator;
    private LoginValidator loginValidator;


    public GUI(UserManager userManager, MeetingManager meetingManager, TradeManager tradeManager,
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
        LoginGUI login = new LoginGUI(loginValidator);
        login.run(loginValidator);
    }


    public void runRegularUserCreateAccount() {
        RegularUserCreateAccountGUI regularUserCreateAccountGUI = new RegularUserCreateAccountGUI(this.accountCreator, this);
        regularUserCreateAccountGUI.run(this.accountCreator, this);
    }

    public void runAdminUserMainMenu() {
        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI();
        adminUserMainMenuGUI.run();
    }
}
