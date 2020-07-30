package GUI;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.maincontrollers.TradingSystem;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import sun.rmi.runtime.Log;

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
        tradingSystemInitMenuGUI tradingSystemInitMenuGUI = new tradingSystemInitMenuGUI(this);
        tradingSystemInitMenuGUI.run(this);
    }

    public void runLogin(){
        loginGUI login = new loginGUI(loginValidator);
        login.run(loginValidator);
    }


    public void runRegularUserCreateAccount() {
        regularUserCreateAccountGUI regularUserCreateAccountGUI = new regularUserCreateAccountGUI(this.accountCreator, this);
        regularUserCreateAccountGUI.run(this.accountCreator, this);
    }

    public void runAdminUserMainMenu() {
        adminUserMainMenuGUI adminUserMainMenuGUI = new adminUserMainMenuGUI();
        adminUserMainMenuGUI.run();
    }
}
