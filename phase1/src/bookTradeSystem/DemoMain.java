package bookTradeSystem;


import java.io.IOException;
import java.util.Map;

public class DemoMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidIdException {
        DemoMainManager();
    }



    public static void DemoMainManager() throws IOException, ClassNotFoundException, InvalidIdException {
        // File path
        String userAccountInfoFilePath = "./src/bookTradeSystem/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/bookTradeSystem/AdminUserUsernameAndPassword.csv";
        String serializedUsersFilePath = "./src/bookTradeSystem/Managers/SerializedUsers.ser";
        String serializedAdminUsersFilePath = "./src/bookTradeSystem/Managers/SerializedAdminUsers.ser";
        String serializedItemsFilePath = "./src/bookTradeSystem/Managers/SerializedItems.ser";
        String serializedMeetingsFilePath = "./src/bookTradeSystem/Managers/SerializedMeetings.ser";
        String serializedTradesFilePath = "./src/bookTradeSystem/Managers/SerializedTrades.ser";

        // Start trading system
        boolean condition = true;
        while (condition){

        // Create all use classes
        UserManager userManager = new UserManager(serializedUsersFilePath, serializedAdminUsersFilePath,
                serializedItemsFilePath);
        MeetingManager meetingManager = new MeetingManager(serializedMeetingsFilePath);
        FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
        TradeManager tradeManager = new TradeManager(serializedTradesFilePath);
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