package bookTradeSystem;

import java.io.Serializable;

public class RegularUserController implements Serializable, Controllable {
    TradeManager tm;
    MeetingManager mm;
    UserManager um;
    FileWriter fw;
    DisplaySystem ds;
    int userId;

    public RegularUserController(TradeManager tm, MeetingManager mm, UserManager um,
                                 FileWriter fw, DisplaySystem ds, int userId) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.fw = fw;
        this.ds = ds;
        this.userId = userId;
    }

    @Override
    public void alerts() {
       /*
        1. get the list of things to be printed
        2. call the presenter class to let it format it and then print
        */
    }

    @Override
    public void actionResponse(int menuOption) {
       /*
        1. decide the menu options (discuss with Jiaqi)
        2. decide what use case method to call for each menu option (discuss with Gabriel)
        3. decide what presenter method to call to print the results for each menu option (discuss with Jiaqi)

        */
    }

    @Override
    public void serializeObjects() {

    }
}