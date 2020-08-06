import managers.usermanager.TradableUser;
import managers.usermanager.User;
import managers.usermanager.UserManager;
import managers.itemmanager.ItemManager;
import managers.itemmanager.Category;
import managers.itemmanager.Item;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class UserManagerTest {
    UserManager um = new UserManager();
    TradableUser u1 = new TradableUser("a", "a", "a@a", 1);
    TradableUser u2 = new TradableUser("b", "b", "b@b", 2);
    Item i1 = new Item("c", "c", 1, 1, Category.CLOTHING);
    Item i2 = new Item("d", "d", 1, 2, Category.APPLIANCES);
    Item i3 = new Item("e", "e", 2, 3, Category.BOOKS);
    Item i4 = new Item("f", "f", 2, 4, Category.ELECTRONICS);

    @Test(timeout = 50)
    public void testGetUserInventory() {
        ArrayList<Integer> inv1 = new ArrayList<>();
        inv1.add(1);
        inv1.add(2);
        u1.setInventory(inv1);

        assertEquals(um.getUserInventory(1), inv1);
        assertEquals(um.getUserInventory(2), new ArrayList<>());
    }

    @Test(timeout = 50)
    public void testGetUserWishlist() {
        ArrayList<Integer> wish2 = new ArrayList<>();
        wish2.add(1);
        u2.setWishList(wish2);

        assertEquals(um.getUserWishlist(1), new ArrayList<>());
        assertEquals(um.getUserWishlist(2), wish2);
    }

    @Test(timeout = 50)
    public void testRequestFriend() {
        assertTrue(um.requestFriend("add", "a", "b"));
        assertFalse(um.requestFriend("no", "b", "a"));
        assertFalse(um.requestFriend("yes", "a", "b"));
        assertFalse(um.requestFriend("yes", "c", "b"));
    }

    @Test(timeout = 50)
    public void testAddFriend(){
        assertFalse(um.addFriend("c", "d"));
        assertTrue(um.addFriend(1, 2));
        assertFalse(um.addFriend(1, 2));
        assertFalse(um.addFriend("b", "a"));
    }

    @Test(timeout = 50)
    public void testRemoveFriend(){
        assertFalse(um.removeFriend("c", "d"));
        assertTrue(um.removeFriend(1, 2));
        assertFalse(um.removeFriend(1, 2));
        assertFalse(um.removeFriend("b", "a"));
    }

    @Test(timeout = 50)
    public void testGoOnVacation(){
        assertFalse(um.goOnVacation(3));
        assertTrue(um.goOnVacation(1));
        assertFalse(um.goOnVacation(1));
    }

    @Test(timeout = 50)
    public void testComeFromVacation(){
        assertFalse(um.comeFromVacation(3));
        assertTrue(um.comeFromVacation(1));
        assertFalse(um.comeFromVacation(1));
    }

    @Test(timeout = 50)
    public void testSameCity(){
        u1.setHome("Toronto");
        u2.setHome("Toronto");

        assertTrue(um.sameCity(1).contains(u2));
        assertTrue(um.sameCity(2).contains(u1));
        assertFalse(um.sameCity(1).contains(u1));
        assertFalse(um.sameCity(2).contains(u2));
    }

    @Test(timeout = 50)
    public void testWantedItems(){
        assertTrue(um.wantedItems(2, 1).contains(1));
        assertFalse(um.wantedItems(2, 1).contains(2));
        assertFalse(um.wantedItems(2, 1).contains(3));
        assertFalse(um.wantedItems(1, 2).contains(1));
    }

}
