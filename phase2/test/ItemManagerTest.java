import managers.itemmanager.Category;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import static org.junit.Assert.*;
import org.junit.Test;


import java.util.ArrayList;
import java.util.Collections;

public class ItemManagerTest {

    @Test
    public void testRequestAddItemAndGetNameById() {
        ItemManager im = new ItemManager();
        im.requestAddItem("book1", "this is book1", 1, Category.BOOKS);
        Item book1 = im.getListItemToAdd().get(0);
        im.addItemToAllItemsList(book1);
        assertEquals(1, im.getListItemToAdd().size());

        im.removeFromListItemToAdd(book1.getItemId());
        assertEquals(0, im.getListItemToAdd().size());

        String name = im.getNamebyId(book1.getItemId());
        assertEquals(name, book1.getName());
    }

    @Test
    public void testSearchItem() {
        ItemManager im = new ItemManager();
        im.requestAddItem("book 1", "this is book1", 1, Category.BOOKS);
        im.requestAddItem("book 2", "this is book2", 2, Category.BOOKS);
        Item book1 = im.getListItemToAdd().get(0);
        Item book2 = im.getListItemToAdd().get(1);
        im.addItemToAllItemsList(book1);
        im.addItemToAllItemsList(book2);
        ArrayList<Integer> ids = im.searchItem("book");
        assertEquals(2, im.searchItem("book").size());
        assertEquals(0, im.searchItem("book4").size());

        ArrayList<Integer> id = new ArrayList<>();
        id.add(book1.getItemId());
        im.setTradable(id, false);
        assertEquals(1, im.getAllTradableItems().size());
    }

    @Test
    public void testCategory(){
        ItemManager im = new ItemManager();
        Item book1 = new Item("book1", "this is book1", 1,1, Category.BOOKS);
        Item book2 = new Item("book2", "this is book2", 1,2, Category.BOOKS);
        Item book3 = new Item("book3", "this is book3", 1,3, Category.BOOKS);
        Item furniture1 = new Item("furniture1", "this is furniture1", 1, 4, Category.FURNITURE);
        Item notebook1 = new Item("notebook1", "this is notebook1", 1, 5, Category.SUPPLIES);
        im.addItemToAllItemsList(book1);
        im.addItemToAllItemsList(book2);
        im.addItemToAllItemsList(book3);
        im.addItemToAllItemsList(furniture1);
        im.addItemToAllItemsList(notebook1);
        assertEquals(3, im.getCategoryItem(Category.BOOKS).size());
        assertEquals(Category.BOOKS, im.getSortedCategory(im.getAllCategoryItem()).get(0));
    }

    @Test
    public void testGetMatchItem() {
        ItemManager im = new ItemManager();
        Item book1 = new Item("book1", "this is book1", 1,1, Category.BOOKS);
        Item book2 = new Item("book2", "this is book2", 1,2, Category.BOOKS);
        Item book3 = new Item("book3", "this is book3", 1,3, Category.BOOKS);
        Item furniture1 = new Item("furniture1", "this is furniture1", 1, 4, Category.FURNITURE);
        Item notebook1 = new Item("notebook1", "this is notebook1", 1, 5, Category.SUPPLIES);
        im.addItemToAllItemsList(book1);
        im.addItemToAllItemsList(book2);
        im.addItemToAllItemsList(book3);
        im.addItemToAllItemsList(furniture1);
        im.addItemToAllItemsList(notebook1);
        ArrayList<Integer> ids = im.getMatchItem(im.getAllItem());
        int item_id = ids.get(0);
        assertEquals (1, item_id);
    }

    @Test
    public void testGetMostMatchItem() {
        ItemManager im = new ItemManager();
        Item book1 = new Item("book1", "this is book1", 1,1, Category.BOOKS);
        Item book2 = new Item("book2", "this is book2", 1,2, Category.BOOKS);
        Item book3 = new Item("book3", "this is book3", 1,3, Category.BOOKS);
        Item furniture1 = new Item("furniture1", "this is furniture1", 1, 4, Category.FURNITURE);
        Item notebook1 = new Item("notebook1", "this is notebook1", 1, 5, Category.SUPPLIES);
        im.addItemToAllItemsList(book1);
        im.addItemToAllItemsList(book2);
        im.addItemToAllItemsList(book3);
        im.addItemToAllItemsList(furniture1);
        im.addItemToAllItemsList(notebook1);
        ArrayList<Integer> id = new ArrayList<>();
        Collections.addAll(id, book1.getItemId(), book2.getItemId());

        ArrayList<Item> wishlists = new ArrayList<>();
        Collections.addAll(wishlists, book1, book2, book3, furniture1, notebook1);

        im.setTradable(id,false);
        ArrayList<Integer> suggestions = im.getMatchItem(wishlists);
        int itemid = suggestions.get(0);
        assertEquals (3, itemid);
    }
}
