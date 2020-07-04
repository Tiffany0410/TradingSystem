package bookTradeSystem;


import java.io.IOException;
import java.util.Map;

public class DemoMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidIdException {
        DemoMainManager();
    }



    public static void DemoMainManager() throws IOException, ClassNotFoundException, InvalidIdException {
        // File path
        String userAccountInfoFilePath = "./src/Managers/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/Managers/AdminUserUsernameAndPassword.csv";
        String serializedUserManagerFilePath = "./src/Managers/SerializedUserManager.ser";
        String serializedTradeManagerFilePath = "./src/Managers/SerializedTradeManager.ser";
        String serializedMeetingManagerFilePath = "./src/Managers/SerializedMeetingManager.ser";


        // Start trading system
        boolean condition = true;
        while (condition){

        // Create all use classes
        UserManager userManager = FilesReaderWriter.readUserManagerFromFile(serializedUserManagerFilePath);
        MeetingManager meetingManager = FilesReaderWriter.readMeetingManagerFromFile(serializedMeetingManagerFilePath);
        FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
        TradeManager tradeManager = FilesReaderWriter.readTradeManagerFromFile(serializedTradeManagerFilePath);
        DisplaySystem displaySystem = new DisplaySystem();
        AccountCreator accountCreator = new AccountCreator(userManager, displaySystem, filesReaderWriter);

        // Load accounts data from CSV file to initial login validator
        Map<String, String> userLoginInfo = filesReaderWriter.readUserInfoFromCSVFile(userAccountInfoFilePath);
        Map<String, String> adminUserLoginInfo = filesReaderWriter.readUserInfoFromCSVFile(adminAccountInfoFilePath);
        LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

        // Create trading system
        TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator, tradeManager,
                filesReaderWriter, displaySystem, accountCreator);

        // Run trading system
        condition = tradingSystem.tradingSystemInital();

        }

    }

}