package bookTradeSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class AccountCreator {
    private UserManager um;
    private DisplaySystem ds;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds){
        this.um = um;
        this.ds = ds;
    }

    /**
     * Creates and adds a new User
     * @param username The username of the User
     * @param password The password of the User
     * @param email The email of the User
     * @return true if the User was successfully added, false otherwise
     */
    public boolean createAccount(String username, String password, String email){
        boolean out = false;
        HashMap<String, String> info = um.userPasswords();
        ArrayList<User> listPeople = um.getListUser();
        if (!info.containsKey(username)){
            User toAdd = new User(username, password, email);
            listPeople.add(toAdd);
            um.setListUser(listPeople);
            out = true;
        }
        return out;
    }
}
