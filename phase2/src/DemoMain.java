import Controllers.AccountCreator;
import Controllers.LoginValidator;
import Controllers.MainControllers.TradingSystem;
import Gateway.FilesReaderWriter;
import Managers.ItemManager.Item;
import Managers.ItemManager.ItemManager;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

/**
 * This is the main class of whole system, this class start and initial whole program
 *
 * @author Jiaqi Gong, Chengle Yang
 * @version IntelliJ IDEA 2020.1.1
 */

public class DemoMain {
    public static void main(String[] args) {
        DemoMainManager();
    }

    public static void DemoMainManager() {
        // File path
        String userAccountInfoFilePath = "./src/managers/otherFiles/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/managers/otherFiles/AdminUserUsernameAndPassword.csv";
        String serializedUserManagerFilePath = "./src/managers/userManager/SerializedUserManager.ser";
        String serializedTradeManagerFilePath = "./src/managers/tradeManager/SerializedTradeManager.ser";
        String serializedMeetingManagerFilePath = "./src/managers/meetingManager/SerializedMeetingManager.ser";
        String serializedItemManagerFilePath = "./src/managers/itemManager/SerializedMeetingManager.ser";


        // Start trading system
        boolean condition = true;
        while (condition){


        try {
            // Create all use classes
            FilesReaderWriter frw = new FilesReaderWriter();
            UserManager userManager = (UserManager) frw.readManagerFromFile(serializedUserManagerFilePath,"userManager");
            MeetingManager meetingManager = (MeetingManager) frw.readManagerFromFile(serializedMeetingManagerFilePath, "meetingManager");
            TradeManager tradeManager = (TradeManager) frw.readManagerFromFile(serializedTradeManagerFilePath, "tradeManager");
            ItemManager itemManager = (ItemManager) frw.readManagerFromFile(serializedItemManagerFilePath, "itemManager");
            DisplaySystem displaySystem = new DisplaySystem();
            AccountCreator accountCreator = new AccountCreator(userManager, displaySystem, frw);

            // Load accounts data from CSV file to initial login validator
            Map<String, String> userLoginInfo = FilesReaderWriter.readUserInfoFromCSVFile(userAccountInfoFilePath);
            Map<String, String> adminUserLoginInfo = FilesReaderWriter.readUserInfoFromCSVFile(adminAccountInfoFilePath);
            LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

            // Create trading system
            TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator, tradeManager,
                    frw, displaySystem, accountCreator, itemManager);

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