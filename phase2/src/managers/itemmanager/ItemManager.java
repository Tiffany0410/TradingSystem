package managers.itemmanager;
import managers.usermanager.UserManager;

import java.io.Serializable;
import java.util.*;

/**
 * Stores all the items and manages the items
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1.1
 */
public class ItemManager implements Serializable {

    private ArrayList<Item> listItem;
    private ArrayList<Item> listItemToAdd;
    private ArrayList<Item> listDeletedItem;

    /**
     * Constructs A ItemManager
     */
    public ItemManager(){
        listItem = new ArrayList<>();
        listItemToAdd = new ArrayList<>();
        listDeletedItem = new ArrayList<>();
    }

    // Getters

    /**
     * Return all items
     * @return A list of items
     */
    public ArrayList<Item> getListItemToAdd(){
        return listItemToAdd;
    }

    /**
     * Return all the items
     * @return A list of all the items
     */
    public ArrayList<Item> getAllItem(){
        return listItem;
    }

    /**
     * Return a list of tradable items
     * @return a list of tradable items
     */
    public ArrayList<Item> getAllTradableItems(){
        ArrayList<Item> items = new ArrayList<>();
        for (Item item: listItem){
            if (item.getTradable()){
                items.add(item);
            }
        }
        return items;
    }

    /**
     * Return a map that maps categories to the corresponding list of items' ids for all items
     * @return A map that maps categories to the corresponding list of items' ids
     */
    public HashMap<Category, ArrayList<Integer>> getAllCategoryItem(){
        HashMap<Category, ArrayList<Integer>> category = new HashMap<>();
        for (Category cat: Category.values()){
            category.put(cat, getCategoryItem(cat));
        }
        return category;
    }

    // Getters by ID

    /**
     * Return item name by given item ID
     * @param itemId The item's ID
     * @return the item's name
     */
    public String getNamebyId(int itemId) {
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                return item.getName();
            }
        }
        return "Item not found.";
    }

    /**
     * Return the item by given item ID
     * @param itemId The item's ID
     * @return The corresponding Item
     */
    public Item getItembyId(int itemId){
        for (Item item: listItem){
            if (item.getItemId() == itemId){
                return item;
            }
        }
        return null;
    }

    /**
     * Return all Items except the ones that the owner's ID matches userID
     * @param userID The id of the user
     * @return A list of all the Items except the ones that the owner's ID matches userID
     */
    public ArrayList<Item> allTradableItemsFromOtherUser(int userID){
        ArrayList<Item> allItem = new ArrayList<>();
        for (Item item: listItem){
            if (item.getOwnerId() != userID && item.getTradable()){
                allItem.add(item);
            }
        }
        return allItem;
    }

    /**
     * Return a list of items in listItem with corresponding Ids
     * @param listIds A list of items' Ids
     * @return A list of items with corresponding Ids
     */
    public ArrayList<Item> getItemsByIds(ArrayList<Integer> listIds){
        ArrayList<Item> items = new ArrayList<>();
        for (Integer id : listIds) {
            items.add(getItembyId(id));
        }
        return items;
    }

    // Getters by Item Name / Item

    /**
     * Return the item ID by given item name
     * @param itemName The item's name
     * @return The corresponding Item
     */
    public Integer getRequestItemIDByName(String itemName){
        for (Item item: listItemToAdd){
            if (item.getName().equals(itemName)){ return item.getItemId(); }
        }
        return -1;
    }

    /**
     * Return a list of IDs for a given item which waiting for add
     * @param item The item object
     * @return a list of IDs (Item's Id, Owner's Id, Current Holder's ID)
     */
    public ArrayList<Integer> getIDFromWaitingItem(Item item){
        ArrayList<Integer> listId = new ArrayList<>();
        for (Item temp: listItemToAdd){
            if (temp.getItemId() == item.getItemId()){
                listId.add(item.getItemId());
                listId.add(item.getOwnerId());
            }
        }
        return listId;
    }


    /**
     * Return a list of corresponding Ids for items
     * @param items A list of items
     * @return a list of ids for items
     */
    public ArrayList<Integer> getItemsIDs(ArrayList<Item> items){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Item item: items){
            ids.add(item.getItemId());
        }
        return ids;
    }

    /**
     * Return a list of tradable items that are in items
     * @param items A list of items
     * @return a list of tradable items that are in items
     */
    public ArrayList<Item> getTradableItems(ArrayList<Item> items){
        ArrayList<Item> out = new ArrayList<>();
        for (Item item: items){
            if (item.getTradable()){
                out.add(item);
            }
        }
        return out;
    }

    /**
     * Return a list of non-tradable items that are in items
     * @param items A list of items
     * @return a list of non-tradable items that are in items
     */
    public ArrayList<Item> getNotTradableItems(ArrayList<Item> items){
        ArrayList<Item> out = new ArrayList<>();
        for (Item item: items){
            if (!item.getTradable()){
                out.add(item);
            }
        }
        return out;
    }

    /**
     * Return a map that maps categories to the corresponding list of items' ids for the list of items
     * @param items A list of items
     * @return A map that maps categories to the corresponding list of items' ids for the list of items
     */
    public HashMap<Category, ArrayList<Integer>> getAllCategoryItem(ArrayList<Item> items){
        HashMap<Category, ArrayList<Integer>> category = new HashMap<>();
        for (Category cat: Category.values()){
            if (!getCategoryItem(cat, items).isEmpty()){
                category.put(cat, getCategoryItem(cat, items));
            }
        }
        return category;
    }

    // Setters & Add

    /**
     * Set the list of items' tradable statuses to tradable
     * @param listIds The list of items' ids
     * @param tradable The tradable status
     */
    public void setTradable(ArrayList<Integer> listIds, boolean tradable){
        Set<Integer> setIds = new HashSet<>(listIds);
        for (Integer id: setIds){
            this.getItembyId(id).setTradable(tradable);
        }
    }

    /**
     * Set the item's tradable status to tradable
     * @param item The item
     * @param tradable The tradable status
     */
    public boolean setTradable(Item item, boolean tradable){
        if (tradable){
            if (item.getTradable()){
                return false;
            }
        }
        if (!tradable){
            if (! item.getTradable()){
                return false;
            }
        }
        item.setTradable(tradable);
        return true;
    }

    /**
     * Add item to listItem
     * @param item The item
     */
    public void addItemToAllItemsList(Item item){
        listItem.add(item);
    }

    /**
     * Add item to listDeletedItem
     * @param item The item
     */
    public void addItemToListDeletedItem(Item item) {listDeletedItem.add(item);}

    /**
     * Creates new item
     * @param name The item's name
     * @param description The item's description
     * @param ownerId The item's owner's id
     */
    public void requestAddItem(String name, String description, int ownerId, Category category){
        int temp_id;
        ArrayList<Item> a = listItem;
        ArrayList<Item> b = listItemToAdd;
        if (a.isEmpty()){
            if (b.isEmpty()){ temp_id = 1; }
            else {temp_id = b.get(b.size()-1).getItemId() + 1; } }
        else{
            if (b.isEmpty()){temp_id = a.get(a.size()-1).getItemId() + 1; }
            else{ temp_id = Math.max(a.get(a.size()-1).getItemId(), b.get(b.size()-1).getItemId()) + 1;} }
        Item item = new Item(name, description, ownerId, temp_id, category);
        listItemToAdd.add(item);
    }

    // Remove

    /**
     * Remove item from listItemToAdd
     * @param itemId The Item's ID
     */
    public boolean removeFromListItemToAdd(int itemId){
        if (getItemsIDs(listItemToAdd).contains(itemId)){
            listItemToAdd.removeIf(item -> item.getItemId() == itemId);
            return true;
        }
        return false;
    }

    /**
     * @param targetItem The item that being deleted
     */
    public void deleteItemFromListItemToAdd(Item targetItem) {
        listItemToAdd.remove(targetItem); }

    // Getters by Category

    /**
     * Return all the items in this category for all items
     * @param category The category of the item
     * @return A list of all item ids in this category for all items
     */
    public ArrayList<Integer> getCategoryItem(Category category){
        ArrayList<Integer> lst = new ArrayList<>();
        for (Item item: listItem){
            if (item.getCategory().equals(category)){
                lst.add(item.getItemId());
            }
        }
        return lst;
    }

    /**
     * Return a list of all item ids in this category for items
     * @param category The item's category
     * @param items A list of items
     * @return A list of all item ids in this category for items
     */
    public ArrayList<Integer> getCategoryItem(Category category, ArrayList<Item> items){
        ArrayList<Integer> lst = new ArrayList<>();
        for (Item item: items){
            if (item.getCategory().equals(category)){
                lst.add(item.getItemId());
            }
        }
        return lst;
    }

    /**
     * Return a list of sorted category by most to least number of items in the category
     * @param category The item's category
     * @return A list of sorted category
     */
    public ArrayList<Category> getSortedCategory(HashMap<Category, ArrayList<Integer>> category){
        ArrayList<Category> out = new ArrayList<>();
        HashMap<Category, ArrayList<Integer>> temp_cat = new HashMap<>(category);
        while (!temp_cat.isEmpty()){
            Category most = null;
            for (Category c: temp_cat.keySet()){
                if (most == null || temp_cat.get(c).size() > temp_cat.get(most).size()){
                    most = c;
                }
            }
            out.add(most);
            temp_cat.remove(most);
        }
        return out;
    }

    // Search & Get Match Item

    /**
     * Searches for item that contains itemName
     * @param itemName The prefix of the name of the Item searched for
     * @return A list of all items with the prefix in their name same as itemName
     */
    public ArrayList<Integer> searchItem(String itemName){
        ArrayList<Integer> allItem = new ArrayList<>();
        for (Item item: listItem){
            if (item.getName().contains(itemName)){
                allItem.add(item.getItemId());
            }
        }
        return allItem;
    }

    /**
     * Return the suggested item
     * @param wishlist The user's wishlist
     * @return A list of ids for the suggested item (item's id, item owner's id)
     */
    public ArrayList<Integer> getMatchItem(ArrayList<Item> wishlist){
        ArrayList<Integer> ids = new ArrayList<>();
        HashMap<Category, ArrayList<Integer>> category = getAllCategoryItem(wishlist);
        for (Category c: getSortedCategory(category)){
            for (int id: category.get(c)){
                if (getItembyId(id).getTradable()){
                    Collections.addAll(ids, id, getItembyId(id).getOwnerId());
                    return ids;
                }
            }
        }
        return getMostMatchItem(wishlist);
    }

    /**
     * Helper method for getMatchItem
     * @param wishlist The user's wishlist
     * @return A list of ids for the suggested item (item's id, item owner's id)
     */
    private ArrayList<Integer> getMostMatchItem(ArrayList<Item> wishlist) {
        ArrayList<Integer> ids = new ArrayList<>();
        if (wishlist.isEmpty()){
            return ids;
        }
        int ownerId = wishlist.get(0).getOwnerId();
        HashMap<Category, ArrayList<Integer>> category = getAllCategoryItem(wishlist);
        for (Category c : getSortedCategory(category)) {
            for (int id : getCategoryItem(c)){
                Item item = getItembyId(id);
                if (item.getTradable() && item.getOwnerId()!= ownerId){
                    Collections.addAll(ids, item.getItemId(), item.getOwnerId());
                    return ids;
                }
            }
        }
        return ids;  // No suggestion
    }

    // Get Sorted Items by followers

    /**
     * Return the a list of items that sorted by number of follows
     * @param um Using dependency injection for UserManager
     * @return a list of items that sorted by number of follows
     */
    public ArrayList<Item> getSortedItemByFollows(UserManager um){
        ArrayList<Item> sortedItems = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>(getUsersByItemsFollowed(um));
        while (!map.isEmpty()) {
            int most = 0;
            for (int itemid: map.keySet()) {
                if (most == 0 || map.get(itemid).size() > map.get(most).size()) {
                    most = itemid;
                }
            }
            sortedItems.add(getItembyId(most));
            map.remove(most);
        }
        return sortedItems;
    }

    /**
     * Helper method for getSortedItemByFollows
     * @param um Using dependency injection for UserManager
     * @return A Map that maps item's id to a list of followers (user' id)
     */
    private HashMap<Integer, ArrayList<Integer>> getUsersByItemsFollowed(UserManager um){
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        for (int itemid: getItemsIDs(listItem)){
            ArrayList<Integer> lstUsers = getUsersByItem(um, itemid);
            map.put(itemid, lstUsers);
        }
        return map;

    }

    /**
     * Helper for getUsersByItemsFollowed
     * @param um Using dependency injection for UserManager
     * @param itemId The item's id
     * @return a list of users' ids that followed this item
     */
    private ArrayList<Integer> getUsersByItem(UserManager um, int itemId){
        ArrayList<Integer> listUserids = new ArrayList<>();
        HashMap<Integer, ArrayList<Integer>> itemsFollowed = um.itemsFollowed();
        for (int userId: itemsFollowed.keySet()){
            if (itemsFollowed.get(userId).contains(itemId)){
                listUserids.add(userId);
                }
            }
        return listUserids;
        }

    }