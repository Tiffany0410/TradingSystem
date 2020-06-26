package bookTradeSystem;

import java.io.Serializable;

public class AdminUserController implements Serializable, Controllable {

    UserManager um;
    DisplaySystem ds;
    FileWriter fw;
    int adminUserId;

    public AdminUserController(UserManager um, FileWriter fw, DisplaySystem ds, int adminUserId) {
        this.um = um;
        this.fw = fw;
        this.ds = ds;
        this.adminUserId = adminUserId;
    }

    @Override
    public void alerts() {

    }

    @Override
    public void actionResponse(int menuOption) {

    }

    @Override
    public void serializeObjects() {

    }

}