import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;

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
        String userAccountInfoFilePath = "./src/Managers/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/Managers/AdminUserUsernameAndPassword.csv";
        String serializedUserManagerFilePath = "./src/Managers/SerializedUserManager.ser";
        String serializedTradeManagerFilePath = "./src/Managers/SerializedTradeManager.ser";
        String serializedMeetingManagerFilePath = "./src/Managers/SerializedMeetingManager.ser";


        // Start trading system
        boolean condition = true;
        while (condition){


        try {
            // Create all use classes
            UserManager userManager = FilesReaderWriter.readUserManagerFromFile(serializedUserManagerFilePath);
            MeetingManager meetingManager = FilesReaderWriter.readMeetingManagerFromFile(serializedMeetingManagerFilePath);
            FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
            TradeManager tradeManager = FilesReaderWriter.readTradeManagerFromFile(serializedTradeManagerFilePath);
            DisplaySystem displaySystem = new DisplaySystem();
            AccountCreator accountCreator = new AccountCreator(userManager, displaySystem, filesReaderWriter);

            // Load accounts data from CSV file to initial login validator
            Map<String, String> userLoginInfo = FilesReaderWriter.readUserInfoFromCSVFile(userAccountInfoFilePath);
            Map<String, String> adminUserLoginInfo = FilesReaderWriter.readUserInfoFromCSVFile(adminAccountInfoFilePath);
            LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

            // Create trading system
            TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator, tradeManager,
                    filesReaderWriter, displaySystem, accountCreator);

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