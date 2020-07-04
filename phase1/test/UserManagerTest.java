import bookTradeSystem.Item;
import bookTradeSystem.User;
import bookTradeSystem.UserManager;
import bookTradeSystem.AdminUser;
import java.util.ArrayList;
import java.util.HashMap;

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
        assertFalse(um.searchItem("b").contains(thing2));
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

        assertTrue(um.removeItemWishlist(thing2.getItemId(), "a"));
        assertFalse(um.removeItemWishlist(thing2.getItemId(), "a"));
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

    @Test(timeout = 50)
    public void testAddItemWishList() {
        User person = new User("a", "a", "a@a");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        ArrayList<Integer> personwish = new ArrayList<>();
        personwish.add(thing2.getItemId());
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setWishList(personwish);

        assertTrue(um.addItemWishlist(thing1.getItemId(), "a"));
        assertFalse(um.addItemWishlist(thing1.getItemId(), "a"));
        assertFalse(um.addItemWishlist(thing2.getItemId(), "a"));
        assertEquals(2, person.getWishList().size());
        assertTrue(person.getInventory().isEmpty());
    }

    @Test(timeout = 50)
    public void testAddItemInventory() {
        User person = new User("a", "a", "a@a");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        assertTrue(um.addItemInventory(thing2, "a"));
        assertFalse(um.addItemInventory(thing2, "a"));
        assertFalse(um.addItemInventory(thing1, "a"));
        assertEquals(2, person.getInventory().size());
        assertTrue(person.getWishList().isEmpty());
    }

    @Test(timeout = 50)
    public void testUserPasswords() {
        User person = new User("a", "a", "a@a");
        AdminUser admin = new AdminUser("d","d","d@d");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        emp2.add(admin);
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        HashMap<String, String> pass = um.userPasswords();
        assertTrue(pass.containsKey("a"));
        assertTrue(pass.containsValue("a"));
        assertEquals("a", pass.get("a"));
        assertFalse(pass.containsKey("d"));
    }

    @Test(timeout = 50)
    public void testAdminPasswords() {
        User person = new User("a", "a", "a@a");
        AdminUser admin = new AdminUser("d", "d", "d@d");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        emp2.add(admin);
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        HashMap<String, String> pass = um.adminPasswords();
        assertTrue(pass.containsKey("d"));
        assertTrue(pass.containsValue("d"));
        assertEquals("d", pass.get("d"));
        assertFalse(pass.containsKey("a"));
    }

    @Test(timeout = 50)
    public void testFindUser() {
        User person = new User("a", "a", "a@a");
        User admin = new User("d", "d", "d@d");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        emp.add(admin);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        assertEquals(person, um.findUser(person.getId()));
        assertEquals(person, um.findUser("a"));
        assertEquals(admin, um.findUser(admin.getId()));
        assertEquals(admin, um.findUser("d"));
        assertNotEquals(admin, um.findUser(1));
        assertNotEquals(admin, um.findUser("a"));
    }

    @Test(timeout = 50)
    public void testIDtoUsername() {
        User person = new User("a", "a", "a@a");
        AdminUser admin = new AdminUser("d", "d", "d@d");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        emp2.add(admin);
        UserManager um = new UserManager(emp, emp2);

        assertEquals("a", um.idToUsername(person.getId()));
        assertNotEquals("a", um.idToUsername(2));
        assertEquals("d", um.idToUsername(admin.getId()));
        assertNotEquals("d", um.idToUsername(1));
    }

    @Test(timeout = 50)
    public void testUsernametoID() {
        User person = new User("a", "a", "a@a");
        AdminUser admin = new AdminUser("d", "d", "d@d");
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        emp2.add(admin);
        UserManager um = new UserManager(emp, emp2);

        assertEquals(person.getId(), um.usernameToID("a"));
        assertNotEquals(2, um.usernameToID("a"));
        assertEquals(admin.getId(), um.usernameToID("d"));
        assertNotEquals(person.getId(), um.usernameToID("d"));
    }

    @Test(timeout = 50)
    public void testAllItems() {
        User person = new User("a", "a", "a@a");
        User admin = new User("d", "d", "d@d");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        personinv.add(thing2);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        emp.add(admin);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        assertTrue(um.allItems(1).isEmpty());
        assertTrue(um.allItems(2).contains(thing1));
        assertTrue(um.allItems("d").contains(thing2));
    }

    @Test(timeout = 50)
    public void testRequestUnfreeze() {
        User person = new User("a", "a", "a@a");
        User admin = new User("d", "d", "d@d");
        Item thing1 = new Item("b", "b", 1);
        Item thing2 = new Item("c", "c", 1);
        ArrayList<Item> personinv = new ArrayList<>();
        personinv.add(thing1);
        personinv.add(thing2);
        ArrayList<User> emp = new ArrayList<>();
        emp.add(person);
        emp.add(admin);
        ArrayList<AdminUser> emp2 = new ArrayList<>();
        UserManager um = new UserManager(emp, emp2);
        person.setInventory(personinv);

        um.freezeUser("a");
        assertFalse(um.requestUnfreeze("d", "Not Frozen"));
        assertTrue(um.requestUnfreeze("a", "Please"));
        assertFalse(um.requestUnfreeze("a","Again"));
        assertFalse(um.requestUnfreeze("c", "Nonexistent"));
    }
}
