import bookTradeSystem.*;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TradeManagerTest {
        @Test(timeout = 50)
        public void testRecentThreeItems() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId());
            Item item2 = new Item("Item2", "This is item 2", user2.getId());
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",true);
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
            Item item1 = new Item("Item1","This is item 1", user1.getId());
            Item item2 = new Item("Item2", "This is item 2", user2.getId());
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(), item2.getItemId(),"P",false);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            list.add(item2.getItemId());
            assertEquals(list,t.recentThreeItem(user1.getId()));
        }
        @Test(timeout = 50)
        public void testValidateTrade(){
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId());
            Item item2 = new Item("Item2", "This is item 2", user2.getId());
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",true);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            assertEquals(false,t.validateTrade(trade,user1));
        }
        @Test(timeout = 50)
        public void testValidateTrade1(){
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId());
            Item item2 = new Item("Item2", "This is item 2", user2.getId());
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",false);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(item1.getItemId());
            assertEquals(true,t.validateTrade(trade,user1));
        }
        @Test(timeout = 50)
        public void testTopThreePartners() throws InvalidIdException {
            User user1 = new User("User1","123","123@gmail.com", 1);
            User user2 = new User("User2","321","321@gmail.com", 2);
            Item item1 = new Item("Item1","This is item 1", user1.getId());
            Item item2 = new Item("Item2", "This is item 2", user2.getId());
            TradeManager t = new TradeManager();
            Trade trade = new Trade(user1.getId(),user2.getId(),item1.getItemId(),"P",false);
            t.addTrade(trade);
            trade.closedTrade();
            List<Integer> list = new ArrayList<>();
            list.add(user2.getId());
            assertEquals(list,t.topThreePartners(user1.getId()));
        }
        }

