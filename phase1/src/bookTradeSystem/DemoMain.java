package bookTradeSystem;


import java.io.IOException;
import java.util.Map;

public class DemoMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InvalidIdException {
        DemoMainManager();
    }



    public static void DemoMainManager() throws IOException, ClassNotFoundException, InvalidIdException {
        // File path
        String meetingManagerFilePath = "";
        String tradeManagerFilePath = "";
        String userAccountInfoFilePath = "./src/bookTradeSystem/RegularUserUsernameAndPassword.csv";
        String adminAccountInfoFilePath = "./src/bookTradeSystem/AdminUserUsernameAndPassword.csv";

        // Create use class does not need to update all the time
        UserManager userManager = new UserManager();
        FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
        DisplaySystem displaySystem = new DisplaySystem();

        // Start trading system
        boolean condition = true;
        while (condition){

        // Create use classes need to update
        MeetingManager meetingManager = new MeetingManager(meetingManagerFilePath);
        TradeManager tradeManager = new TradeManager(tradeManagerFilePath);
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