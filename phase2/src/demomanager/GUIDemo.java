package demomanager;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.adminusersubcontrollers.*;
import controllers.regularusersubcontrollers.*;
import gateway.FilesReaderWriter;
import gui.LongNotificationGUI;
import gui.NotificationGUI;
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

/**
 * This is the GUIDemo, this class control all gui classes
 *
 * @author  Jiaqi Gong
 * @version IntelliJ IDEA 2020.1.1
 */

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
    private String tempUsername;
    private SystemMessage systemMessage;
    private String partsOfUserAlert;
    private String partsOfAdminAlert;
    private ArrayList<Integer> thresholdValues;
    private boolean isGuest;

    // Gateway
    private FilesReaderWriter frw;

    /**
     * Constructor of GUIDemo class
     * @param userManager user manager, need DemoManager pass in
     * @param meetingManager meeting manager, need DemoManager pass in
     * @param tradeManager trade manager, need DemoManager pass in
     * @param itemManager item manager, need DemoManager pass in
     * @param feedbackManager feedback manager, need DemoManager pass in
     * @param messageManager message manager, need DemoManager pass in
     * @param accountCreator account manager, need DemoManager pass in
     * @param loginValidator login validator, need DemoManager pass in
     * @param actionManager action manager, need DemoManager pass in
     * @param partsOfUserAlert user alert, need DemoManager pass in
     * @param partsOfAdminAlert admin user alert, need DemoManager pass in
     * @param thresholdValues threshold values, need DemoManager pass in
     */
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
        this.systemMessage = new SystemMessage();
        this.adminUserOtherInfoChecker = new AdminUserOtherInfoChecker(this.actionManager);
        this.regularUserDateTimeChecker = new RegularUserDateTimeChecker();
        this.regularUserOtherInfoChecker = new RegularUserOtherInfoChecker();

        // Create new FilesReaderWriter
        this.frw = new FilesReaderWriter();

    }

    /**
     * This print the notification of invalid number
     */
    public void printInvalidNumber() {
        printNotification(this.systemMessage.invalidNumber());
    }

    /**
     * This method run trading system init menu gui, which is the init part of whole system
     */
    public void runTradingSystemInitMenuGUI(){
        TradingSystemInitMenuGUI tradingSystemInitMenuGUI = new TradingSystemInitMenuGUI(this);
        tradingSystemInitMenuGUI.run(this);
    }

    /**
     * This method run the login part gui of the system
     */
    public void runLogin(){
        LoginGUI login = new LoginGUI(this.loginValidator, this);
        login.run(this.loginValidator,this);
    }

    /**
     * This method run the create account gui for regular user of the system
     */
    public void runRegularUserCreateAccount() {
        RegularUserCreateAccountGUI regularUserCreateAccountGUI = new RegularUserCreateAccountGUI(this.accountCreator,
                this, this.systemMessage, this.regularUserOtherInfoChecker);
        regularUserCreateAccountGUI.run(this.accountCreator, this, this.systemMessage, this.regularUserOtherInfoChecker);
    }


    // Start of run admin menus

    /**
     * This method run admin user main menu gui and create all admin user controllers
     */
    public void runAdminUserMainMenu() {
        // Create all admin user controller
        this.adminUserManagerUsersController = new AdminUserManagerUsersController(this.userManager, this.itemManager,
                this.actionManager,this.getTempUsername());

        this.adminUserEditThresholdsController = new AdminUserEditThresholdsController(
                this.actionManager, this.userManager, this.systemMessage, this.getTempUsername(), this.thresholdValues);

        this.adminUserHistoricalActionController = new
                AdminUserHistoricalActionController(this.userManager, this.itemManager, this.tradeManager,
                this.meetingManager, this.actionManager, this.feedbackManager, this.getTempUsername());

        this.adminUserOtherActionsController = new AdminUserOtherActionsController(
                this.userManager, this.actionManager, this.getTempUsername(), this.meetingManager);

        AdminUserMainMenuGUI adminUserMainMenuGUI = new AdminUserMainMenuGUI(this, this.partsOfAdminAlert);
        adminUserMainMenuGUI.run(this, this.partsOfAdminAlert);
    }

    /**
     * This method run admin user manage users submenu gui
     */
    public void runAdminUserManageUsersSubMenu() {

        RegularUserIDChecker regularUserIDChecker = new RegularUserIDChecker(this.tradeManager,
                this.userManager, this.itemManager);

        AdminUserManageUsersSubMenuGUI adminUserManageUsersSubMenuGUI = new AdminUserManageUsersSubMenuGUI(
                this.adminUserManagerUsersController, this, this.systemMessage,
                regularUserIDChecker, this.adminUserOtherInfoChecker);

        adminUserManageUsersSubMenuGUI.run(this.adminUserManagerUsersController, this,
                this.systemMessage, regularUserIDChecker, this.adminUserOtherInfoChecker);
    }

    /**
     * This method run admin user edit thresholds submenu gui
     */
    public void runAdminUserEditThresholdsSubMenu() {

        AdminUserEditThresholdsSubMenuGUI adminUserEditThresholdsSubMenuGUI = new AdminUserEditThresholdsSubMenuGUI(this,
                this.adminUserEditThresholdsController);

        adminUserEditThresholdsSubMenuGUI.run(this,this.adminUserEditThresholdsController);

    }

    /**
     * This method run admin user historical actions submenu gui
     */
    public void runAdminUserHistoricalActionsSubMenu() {


        AdminUserHistoricalActionsSubMenu adminUserHistroicalActionsSubMenu = new AdminUserHistoricalActionsSubMenu(
                this, this.systemMessage, this.adminUserHistoricalActionController);
        adminUserHistroicalActionsSubMenu.run(this, this.systemMessage, this.adminUserHistoricalActionController);
    }

    /**
     * This method run admin user other actions submenu gui
     */
    public void runAdminUserOtherSubMenu() {
        AdminUserOtherSubMenuGUI adminUserOtherSubMenuGUI = new AdminUserOtherSubMenuGUI(this, this.adminUserOtherActionsController);
        adminUserOtherSubMenuGUI.run(this, this.adminUserOtherActionsController);
    }

    /**
     * This method run admin user create GUI
     */
    public void runAdminUserCreateAccount() {

        AdminUserCreateAccountGUI adminUserCreateAccountGUI = new AdminUserCreateAccountGUI(this.accountCreator, this,
                this.systemMessage, this.adminUserOtherActionsController);
        adminUserCreateAccountGUI.run(this.accountCreator, this,
                this.systemMessage, this.adminUserOtherActionsController);
    }


    //Regular User menu gui start

    /**
     * This method run regular user main menu gui and create all regular user controller
     * @param guest this is guest login or not
     */
    public void runRegularUserMainMenu(Boolean guest) {
        this.isGuest = guest;

        // create all regular user controller
        this.regularUserAccountMenuController = new RegularUserAccountMenuController(
                this.tradeManager, this.userManager, this.itemManager, this.actionManager, this.systemMessage, this.getTempUsername());

        this.regularUserCommunityMenuController = new RegularUserCommunityMenuController(this.userManager,
                this.actionManager, this.feedbackManager, this.messageManager, this.getTempUsername());

        this.regularUserIDChecker = new RegularUserIDChecker(this.tradeManager,
                this.userManager, this.itemManager);

        this.regularUserMeetingMenuController = new RegularUserMeetingMenuController(this.tradeManager,
                this.meetingManager, this.actionManager, this.userManager.usernameToID(this.getTempUsername()));

        this.regularUserOtherInfoChecker = new RegularUserOtherInfoChecker();

        this.regularUserSearchingMenuController = new RegularUserSearchingMenuController(this.tradeManager,
                this.meetingManager, this.actionManager, this.userManager, this.itemManager, this.feedbackManager, this.getTempUsername());

        this.regularUserThresholdController = new RegularUserThresholdController(
                this.meetingManager, this.userManager, this.getTempUsername());

        this.regularUserTradingMenuController = new RegularUserTradingMenuController(this.tradeManager,
                this.meetingManager, this.userManager, this.itemManager, this.actionManager, this.getTempUsername(),
                this.systemMessage, this.regularUserThresholdController);

        RegularUserMainMenuGUI regularUserMainMenuGUI = new RegularUserMainMenuGUI(this.isGuest, this.systemMessage, this,
                regularUserAccountMenuController, regularUserThresholdController, this.getTempUsername(), this.userManager,
                this.partsOfUserAlert, this.thresholdValues);

        // run regular user main menu gui
        regularUserMainMenuGUI.run(this.isGuest, this.systemMessage, this,
                regularUserAccountMenuController, regularUserThresholdController, this.getTempUsername(), this.userManager,
                this.partsOfUserAlert, this.thresholdValues);
    }

    /**
     * This method run regular user account follow menu gui
     */
    public void runRegularUserAccountFollowMenu(){
        RegularUserFollowMenuGUI regularUserFollowMenuGUI = new RegularUserFollowMenuGUI(this,
                this.regularUserAccountMenuController, this.regularUserIDChecker, this.systemMessage);
        regularUserFollowMenuGUI.run(this,
                this.regularUserAccountMenuController, this.regularUserIDChecker, this.systemMessage);
    }

    /**
     * This method run regular user account manage item menu gui
     */
    public void runRegularUserAccountManageItemsMenu(){
        RegularUserManageItemsMenuGUI regularUserManageItemsMenuGUI = new RegularUserManageItemsMenuGUI(this.isGuest,
                this.systemMessage,this, this.regularUserIDChecker,
                this.regularUserAccountMenuController, this.regularUserOtherInfoChecker);
        regularUserManageItemsMenuGUI.run(this.isGuest, this.systemMessage,this,
                this.regularUserIDChecker, this.regularUserAccountMenuController, this.regularUserOtherInfoChecker);
    }

    /**
     * This method run regular user account setting menu gui
     */
    public void runRegularUserAccountSettingsMenu(){
        RegularUserAccountSettingsMenuGUI regularUserAccountSettingsMenuGUI = new RegularUserAccountSettingsMenuGUI(
                this.regularUserAccountMenuController, this.systemMessage, this.regularUserIDChecker,
                this,this.adminUserOtherInfoChecker);
        regularUserAccountSettingsMenuGUI.run(this.regularUserAccountMenuController, this.systemMessage,
                this.regularUserIDChecker, this, this.adminUserOtherInfoChecker);

    }

    /**
     * This method run regular user account main Menu
     */
    public void runRegularUserAccountMainMenuGUI(){
        RegularUserAccountMainMenuGUI regularUserAccountMainMenuGUI = new RegularUserAccountMainMenuGUI(this.isGuest,
                this.systemMessage, this, this.regularUserAccountMenuController);
        regularUserAccountMainMenuGUI.run(this.isGuest, this.systemMessage, this, this.regularUserAccountMenuController);

    }

    /**
     * This method run regular user trading menu gui
     */
    public void runRegularUserTradingMenuGUI() {

        int maxNumTransactionAWeek = this.thresholdValues.get(0);
        int numLentBeforeBorrow = this.thresholdValues.get(2);

        RegularUserTradingMenuGUI regularUserTradingMenuGUI = new RegularUserTradingMenuGUI(this,
                this.regularUserTradingMenuController, this.systemMessage,maxNumTransactionAWeek, numLentBeforeBorrow,
                this.regularUserIDChecker, this.isGuest);
        regularUserTradingMenuGUI.run(this, this.regularUserTradingMenuController, this.systemMessage,
                maxNumTransactionAWeek, numLentBeforeBorrow, this.regularUserIDChecker, this.isGuest);
    }

    /**
     * This method run regular user community menu gui
     */
    public void runRegularUserCommunityMenuGUI(){
        RegularUserCommunityMenuGUI regularUserCommunityMenuGUI = new RegularUserCommunityMenuGUI(this.isGuest, this,
                this.regularUserCommunityMenuController, this.systemMessage, this.regularUserIDChecker);
        regularUserCommunityMenuGUI.run(this.isGuest, this, this.regularUserCommunityMenuController,
                this.systemMessage, this.regularUserIDChecker);
    }

    /**
     * This method run regular user meeting menu
     */
    public void runRegularUserMeetingMenu(){

        int maxNumTPEdits = thresholdValues.get(3);
        RegularUserMeetingMenuGUI regularUserMeetingMenuGUI = new RegularUserMeetingMenuGUI(this,
                this.regularUserMeetingMenuController, this.systemMessage,maxNumTPEdits, this.regularUserIDChecker,this.regularUserDateTimeChecker, this.isGuest);
        regularUserMeetingMenuGUI.run(this, this.regularUserMeetingMenuController, this.systemMessage,maxNumTPEdits,
                this.regularUserIDChecker,this.regularUserDateTimeChecker, this.isGuest);
    }

    /**
     * This method run regular user searching menu gui
     */
    public void runRegularUserSearchingMenuGUI() {
        RegularUserSearchingMenuGUI regularUserSearchingMenuGUI = new RegularUserSearchingMenuGUI(this,  this.isGuest,
                systemMessage);
        regularUserSearchingMenuGUI.run(this, this.isGuest, systemMessage);
    }

    /**
     * This method run regular user searching submenu gui
     */
    public void runRegularUserSearchingItemsSubMenu(){
        RegularUserSearchingItemsSubMenuGUI regularUserSearchingItemsSubMenuGUI = new RegularUserSearchingItemsSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.systemMessage, this.regularUserIDChecker);
        regularUserSearchingItemsSubMenuGUI.run(this.regularUserSearchingMenuController, this,  this.systemMessage, this.regularUserIDChecker);
    }

    /**
     * This method run regular user searching meeting submenu gui
     */
    public void runRegularUserSearchingMeetingsSubMenu(){
        RegularUserSearchingMeetingsSubMenuGUI regularUserSearchingMeetingsSubMenuGUI = new
                RegularUserSearchingMeetingsSubMenuGUI(this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingMeetingsSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }

    /**
     * This method run regular user searching trades submenu gui
     */
    public void runRegularUserSearchingTradesSubMenu(){
        RegularUserSearchingTradesSubMenuGUI regularUserSearchingTradesSubMenuGUI = new RegularUserSearchingTradesSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingTradesSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }

    /**
     * This method run regular user searching user submenu gui
     */
    public void runRegularUserSearchingUsersSubMenu(){
        RegularUserSearchingUsersSubMenuGUI regularUserSearchingUsersSubMenuGUI = new RegularUserSearchingUsersSubMenuGUI(
                this.regularUserSearchingMenuController, this, this.systemMessage);
        regularUserSearchingUsersSubMenuGUI.run(this.regularUserSearchingMenuController, this, this.systemMessage);
    }


    //Regular User menu gui end

    /**
     * This method set user name
     * @param username name of user login account
     */
    public void setTempUsername(String username){ this.tempUsername = username; }

    /**
     * This method return the current user name
     * @return current username
     */
    public String getTempUsername(){ return this.tempUsername; }

    /**
     * This method print the notification of given string
     * @param string need to print string
     */
    public void printNotification(String string) {
        if (string == null){
            string = "ERROR: NO INPUT VALUE!";
            NotificationGUI notificationGUI = new NotificationGUI(string);
            notificationGUI.run(string);
        }
        else if (string.contains("\n")){
            LongNotificationGUI longNotificationGUI = new LongNotificationGUI(string);
            longNotificationGUI.run(string);}
        else{
            NotificationGUI notificationGUI = new NotificationGUI(string);
            notificationGUI.run(string);
        }
    }

    /**
     * This method close the input panel
     * @param panel the name of panel want to close
     */
    public void closeWindow(JPanel panel){
        Window window = SwingUtilities.getWindowAncestor(panel);
        window.dispose();
    }

    /**
     * This method update each related Managers
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
            //Update the files saved username and pw
            this.loginValidator = new LoginValidator(
                    frw.readUserInfoFromCSVFile("./configs/secureinfofiles/RegularUserUsernameAndPassword.csv"),
                    frw.readUserInfoFromCSVFile("./configs/secureinfofiles/AdminUserUsernameAndPassword.csv"));
        } catch (IOException e) {
            this.printNotification("Sorry, because we cannot find the files to save your progress, so your progress is not saved.");
        }
    }
}



