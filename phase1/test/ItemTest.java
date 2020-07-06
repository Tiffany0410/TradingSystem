import bookTradeSystem.Item;
import bookTradeSystem.User;
import org.junit.*;
import static org.junit.Assert.*;

public class ItemTest {

    // constructor for item and user
    @Test(timeout = 50)
    public void testItem() {
        User user1 = new User("User1","123","123@gmail.com", 1);
        User user2 = new User("User2","321","321@gmail.com", 2);
        Item item1 = new Item("Item1","This is item 1", user1.getId(), 1);
        Item item2 = new Item("Item2", "This is item 2", user2.getId(), 2);
        }

    // test set and get name
    @Test(timeout = 50)
    public void testSetAndGetName(){
        User user1 = new User("User1","123","123@gmail.com", 1);
        Item item1 = new Item("Item1","This is item 1", user1.getId(), 1);
        item1.setName("Item2");
        assertEquals("Item2", item1.getName());
    }

    // test set and get description
    @Test(timeout = 50)
    public void testSetAndGetDescription(){
        User user1 = new User("User1","123","123@gmail.com", 1);
        Item item1 = new Item("Item1","This is item 1", user1.getId(), 1);
        item1.setDescription("Hehe");
        assertEquals("Hehe", item1.getDescription());
    }

    // test get item id
    @Test(timeout = 50)
    public void testGetItemId(){
        User user1 = new User("User1","123","123@gmail.com", 1);
        User user2 = new User("User2","321","321@gmail.com", 2);
        Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
        Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
        assertEquals(1,item1.getItemId());
        assertEquals(2,item2.getItemId());

    }

    // test get owner's id
    @Test(timeout = 50)
    public void testGetOwnerID(){
        User user1 = new User("User1","123","123@gmail.com", 1);
        Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
        assertEquals(1, item1.getOwnerId());
    }

    // test set and get current holder's id
    @Test(timeout = 50)
    public void testSetAndGetCurrHolderId(){
        User user1 = new User("User1","123","123@gmail.com", 1);
        User user2 = new User("User2","321","321@gmail.com", 2);
        Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
        assertEquals(1, item1.getCurrHolderId());
        item1.setCurrHolderId(2);
        assertEquals(2, item1.getCurrHolderId());
    }

}
