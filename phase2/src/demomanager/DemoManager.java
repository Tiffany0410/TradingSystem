package demomanager;

import controllers.AccountCreator;
import controllers.LoginValidator;
import controllers.maincontrollers.TradingSystem;
import gateway.FilesReaderWriter;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

public class DemoManager {

    public static void DemoManager() {
        // File path
        String userAccountInfoFilePath = "./src/managers/otherfiles/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/managers/otherfiles/AdminUserUsernameAndPassword.csv";
        String serializedUserManagerFilePath = "./src/managers/usermanager/SerializedUserManager.ser";
        String serializedTradeManagerFilePath = "./src/managers/trademanager/SerializedTradeManager.ser";
        String serializedMeetingManagerFilePath = "./src/managers/meetingmanager/SerializedMeetingManager.ser";
        String serializedItemManagerFilePath = "./src/managers/itemmanager/SerializedMeetingManager.ser";
        String serializedFeedbackManagerFilePath = "./src/managers/feedbackmanager/SerializedFeedbackManager.ser";


        // Start trading system
        boolean condition = true;
        while (condition){


            try {
                // Create all use classes
                FilesReaderWriter frw = new FilesReaderWriter();
                UserManager userManager = (UserManager)
                        frw.readManagerFromFile(serializedUserManagerFilePath, "userManager");
                MeetingManager meetingManager = (MeetingManager)
                        frw.readManagerFromFile(serializedMeetingManagerFilePath, "meetingManager");
                TradeManager tradeManager = (TradeManager)
                        frw.readManagerFromFile(serializedTradeManagerFilePath, "tradeManager");
                ItemManager itemManager = (ItemManager)
                        frw.readManagerFromFile(serializedItemManagerFilePath, "itemManager");
                FeedbackManager feedbackManager = (FeedbackManager)
                        frw.readManagerFromFile(serializedFeedbackManagerFilePath, "feedbackManager");
                DisplaySystem displaySystem = new DisplaySystem();
                AccountCreator accountCreator = new AccountCreator(userManager, displaySystem);

                // Load accounts data from CSV file to initial login validator
                Map<String, String> userLoginInfo = frw.readUserInfoFromCSVFile(userAccountInfoFilePath);
                Map<String, String> adminUserLoginInfo = frw.readUserInfoFromCSVFile(adminAccountInfoFilePath);
                LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

                // Create trading system
                TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator,
                        tradeManager, displaySystem, accountCreator, itemManager, feedbackManager);

                // Run trading system
                condition = tradingSystem.tradingSystemInital();
            } catch (FileNotFoundException ex) {
                System.out.println("Can not find file, Please check the root of the program and README file.");
                break;
            }catch (ClassNotFoundException ex){
                System.out.println("Can not find Manager in related file, Please check README file or rerun the program.");
                break;
            } catch (IOException ex){
                System.out.println("Please try to clean the content of the files in Managers folder");
                break;
            }
        }

    }
}
