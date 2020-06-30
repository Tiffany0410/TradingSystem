package bookTradeSystem;


import java.io.IOException;
import java.util.Map;

public class DemoMain {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DemoMainManager();
    }

    public static void DemoMainManager() throws IOException, ClassNotFoundException {
        UserManager userManager = new UserManager();
        MeetingManager meetingManager = new MeetingManager();
        FilesReaderWriter filesReaderWriter = new FilesReaderWriter();
        TradeManager tradeManager = new TradeManager();
        DisplaySystem displaySystem = new DisplaySystem();
        AccountCreator accountCreator = new AccountCreator(userManager, displaySystem, filesReaderWriter);

        Map<String, String> userLoginInfo = filesReaderWriter.readUserInfo("./src/bookTradeSystem/RegularUserAccounts.csv");
        Map<String, String> adminUserLoginInfo = filesReaderWriter.readUserInfo("./src/bookTradeSystem/AdminUserAccounts.csv");
        LoginValidator loginValidator = new LoginValidator(userLoginInfo, adminUserLoginInfo);

        TradingSystem tradingSystem = new TradingSystem(userManager, meetingManager, loginValidator, tradeManager,
                filesReaderWriter, displaySystem, accountCreator);
    }

}
