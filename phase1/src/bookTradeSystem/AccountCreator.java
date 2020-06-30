package bookTradeSystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class AccountCreator {
    private UserManager um;
    private DisplaySystem ds;
    private FilesReaderWriter fr;

    /**
     * Constructs an AccountCreator with the given UserManager and DisplaySystem
     * @param um The initial UserManager
     * @param ds The initial Display system
     */
    public AccountCreator(UserManager um, DisplaySystem ds, FilesReaderWriter fr){
        this.um = um;
        this.ds = ds;
        this.fr = fr;
    }

    /**
     * Creates and adds a new User
     * @param type The type of account: normal user or admin
     * @return true if the User was successfully added, false otherwise
     */
    public boolean createAccount(String type){
        boolean out = false;
        HashMap<String, String> info = um.userPasswords();
        ArrayList<User> listPeople = um.getListUser();

        String username;
        String password;
        String email;


        username = ds.getUsername();
        password = ds.getPassword();
        email = ds.getEmail();

        if (!info.containsKey(username)) {
            User toAdd = new User(username, password, email);
            listPeople.add(toAdd);
            um.setListUser(listPeople);
            out = true;

            if (type.equals("Regular")) {
                fr.addNewUser(username, password, email, "./src/bookTradeSystem/RegularUserAccounts.csv");
            } else if (type.equals("Admin")) {
                fr.addNewUser(username, password, email, "./src/bookTradeSystem/AdminUserAccounts.csv");
            }
        }

        return out;
    }
}
