import bookTradeSystem.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TradeManagerTest {
        @Test(timeout = 50)
        public void testRecentThreeItems() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",true,1);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            assertEquals(list,t.recentThreeItem(user1.getId()));
        }
        @Test(timeout = 50)
        public void testRecentThreeItems1() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(), item2.getItemId(),"P",false,1);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            list.add(item2.getItemId());
            assertEquals(list,t.recentThreeItem(user1.getId()));
        }
        @Test(timeout = 50)
        public void testRecentThreeItems3() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(), item2.getItemId(),"P",false,1);
            Trade trade1 = new Trade(user1.getId(),user2.getId(),item2.getItemId(), item1.getItemId(),"P",false,2);
            t.addTrade(trade);
            t.addTrade(trade1);
            trade.closedTrade();
            trade1.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item2.getItemId());
            list.add(item1.getItemId());
            list.add(item1.getItemId());
            assertEquals(list,t.recentThreeItem(user1.getId()));
        }
        @Test(timeout = 50)
        public void testValidateTrade(){
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",true,1);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            // third arg = numLendBeforeBorrow (default = 1)
            assertFalse(t.validateTrade(trade, user1, 1));
        }
        @Test(timeout = 50)
        public void testValidateTrade1(){
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",false,1);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            // third arg = numLendBeforeBorrow (default = 1)
            assertTrue(t.validateTrade(trade, user1, 1));
        }
        @Test(timeout = 50)
        public void testTopThreePartners() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId(),1);
            Item item2 = new Item("Item2", "This is item 2", user2.getId(),2);
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",false,1);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(user2.getId());
            assertEquals(list,t.topThreePartners(user1.getId()));
        }
        }

