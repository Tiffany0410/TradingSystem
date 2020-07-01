package bookTradeSystem;


import java.io.IOException;
import java.util.Map;

public class DemoMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DemoMainManager();
    }



    public static void DemoMainManager() throws IOException, ClassNotFoundException {
        // Create all use classes
        UserManager userManager = new UserManager();
        MeetingManager meetingManager = new MeetingManager();
        FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
        TradeManager tradeManager = new TradeManager();
        DisplaySystem displaySystem = new DisplaySystem();
        AccountCreator accountCreator = new AccountCreator(userManager, displaySystem, filesReaderWriter);

        // Load accounts data from CSV file to initial login validator
        Map<String, String> userLoginInfo = filesReaderWriter.readUserInfoFromCSVFile("./src/bookTradeSystem/RegularUserUsernameAndPassword.csv");
        Map<String, String> adminUserLoginInfo = filesReaderWriter.readUserInfoFromCSVFile("./src/bookTradeSystem/AdminUserUsernameAndPassword.csv");
        LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

        // create trading system
        TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator, tradeManager,
                filesReaderWriter, displaySystem, accountCreator);

        // Start trading system
        boolean condition = true;
        while (condition){
            condition = tradingSystem.tradingSystemInital();
        }

    }

}