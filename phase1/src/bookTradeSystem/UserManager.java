package bookTradeSystem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UserManager implements Serializable {

    private ArrayList<User> listUser;
    private ArrayList<AdminUser> listAdmin;
    private ArrayList<String> listUnfreezeRequest;

    public UserManager(){
        this.listUser = new ArrayList<>();
        this.listAdmin = new ArrayList<>();
        this.listUnfreezeRequest = new ArrayList<>();
    }
    public UserManager(ArrayList<User> users, ArrayList<AdminUser> admins){
        this.listUser = users;
        this.listAdmin = admins;
        this.listUnfreezeRequest = new ArrayList<>();
    }

    public ArrayList<AdminUser> getListAdmin() {
        return listAdmin;
    }

    public ArrayList<User> getListUser() {
        return listUser;
    }

    public void setListAdmin(ArrayList<AdminUser> listAdmin) {
        this.listAdmin = listAdmin;
    }

    public void setListUser(ArrayList<User> listUser) {
        this.listUser = listUser;
    }

    public ArrayList<Item> searchItem(String item){
        ArrayList<Item> out = new ArrayList<>();
        //TODO
        return out;
    }

    public boolean freezeUser(String username){
        boolean out = false;
        //TODO
        return out;
    }

    public boolean unfreezeUser(String username){
        boolean out = false;
        //TODO
        return out;
    }

    public boolean checkUSer(String username){
        boolean out = false;
        //TODO
        return false;
    }

    public void addAdmin(String username, String password, String email){
        //TODO
    }

    public ArrayList<String> underLending(){
        ArrayList<String> out = new ArrayList<>();
        //TODO
        return out;
    }

    public boolean removeItemWishlist(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    public boolean removeItemInventory(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    public boolean addItemWishlist(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    public boolean addItemInventory(Item item, String username){
        boolean out = false;
        //TODO
        return out;
    }

    public Map<String, String> userPasswords(){
        Map<String, String> out = new HashMap<>();
        //TODO
        return out;
    }

    public Map<String, String> adminPasswords(){
        Map<String, String> out = new HashMap<>();
        //TODO
        return out;
    }

    public User findUser(String username){
        User out = new User();
        //TODO
        return out;
    }

    public void changeThreshold(int change){
        //TODO
    }


}
