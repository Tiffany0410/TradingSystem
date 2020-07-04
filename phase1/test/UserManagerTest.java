import bookTradeSystem.Item;
import bookTradeSystem.User;
import bookTradeSystem.UserManager;
import org.junit.*;

import java.util.ArrayList;

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
}
