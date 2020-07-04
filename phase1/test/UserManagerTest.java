import bookTradeSystem.Item;
import bookTradeSystem.User;
import bookTradeSystem.UserManager;
import bookTradeSystem.AdminUser;
import java.util.ArrayList;

import org.junit.*;

import static org.junit.Assert.*;

public class UserManagerTest {

    @Test(timeout = 50)
    public void testRequestItem() {
        User user1 = new User("User1","123","123@gmail.com");
        Item item1 = new Item("Item1","This is item 1", user1.getId());
        UserManager um = new UserManager();
        um.requestAddItem(item1.getName(), item1.getDescription(), user1.getId());
        assertEquals(um.getListItemToAdd().size(), 1);
    }

    @Test(timeout = 50)
    public void testEmptyConstructor(){ UserManager um = new UserManager();}

    @Test(timeout = 50)
    public void testListConstructor(){
        ArrayList<User> emp = new ArrayList<>();
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
    }

    @Test(timeout = 50)
    public void testSearchItem(){
        User person = new User("a", "a", "a@a");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        personinv.add(thing2);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);

        person.setInventory(personinv);

        assertTrue(um.searchItem("b").contains(thing1));
        assertFalse(um.searchItem("b").contains(thing1));
    }

    @Test(timeout = 50)
    public void testFreezeUser(){
        User person = new User("a", "a", "a@a");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);

        assertTrue(um.freezeUser("a"));
        assertTrue(person.getIfFrozen());
    }

    @Test(timeout = 50)
    public void testUnfreezeUser(){
        User person = new User("a", "a", "a@a");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);

        assertFalse(um.unfreezeUser("a"));
        um.freezeUser("a");
        assertTrue(um.unfreezeUser("a"));
    }

    @Test(timeout = 50)
    public void testCheckUser(){
        User person = new User("a", "a", "a@a");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);

        assertTrue(um.checkUser("a"));
        assertFalse(um.checkUser("b"));
    }

    @Test(timeout = 50)
    public void testAddAdmin() {
        User person = new User("a", "a", "a@a");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);

        um.addAdmin("b", "b", "b@b");

        assertEquals(1, um.getListAdmin().size());
        assertEquals(1, um.getListUser().size());
    }

    @Test(timeout = 50)
    public void testRemoveItemWishList() {
        User person = new User("a", "a", "a@a");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        ArrayList<Integer> personwish = new ArrayList<>();
        personinv.add(thing1);
        personwish.add(thing2.getItemId());
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);
        person.setWishList(personwish);

        um.removeItemWishlist(thing2.getItemId(), "a");

        assertTrue(person.getWishList().isEmpty());
        assertEquals(1, person.getInventory().size());
    }

    @Test(timeout = 50)
    public void testRemoveItemInventory() {
        User person = new User("a", "a", "a@a");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        ArrayList<Integer> personwish = new ArrayList<>();
        personinv.add(thing1);
        personwish.add(thing2.getItemId());
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);
        person.setWishList(personwish);

        assertTrue(um.removeItemInventory(thing1.getItemId(), "a"));
        assertFalse(um.removeItemInventory(thing1.getItemId(), "a"));
        assertTrue(person.getInventory().isEmpty());
        assertEquals(1, person.getWishList().size());
    }
}
