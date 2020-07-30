package managers.itemmanager;
import exception.InvalidIdException;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.io.Serializable;
import java.lang.reflect.Array;
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

    /**
     * Return item name by given item ID
     * @param itemId The item's ID
     * @return the item's name
     * @throws InvalidIdException for invalid ID
     */
    public String getNamebyId(int itemId) throws InvalidIdException {
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                return item.getName();
            }
        }
        throw new InvalidIdException("Invalid Item ID");
    }

    /**
     * Return item's information by given item ID
     * @param itemId The item's ID
     * @return the information of the item
     * @throws InvalidIdException for invalid ID
     */
    public String getInfobyID(int itemId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                return item.toString();
            }
        }
        throw new InvalidIdException("Invalid Item ID");
    }

    /**
     * Return the item by given item ID
     * @param itemId The item's ID
     * @return The corresponding Item
     * @throws InvalidIdException for invalid ID
     */
    public Item getItembyId(int itemId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId){
                return item;
            }
        }
        throw new InvalidIdException("Invalid Item Id");
    }


    /**
     * Return the item ID by given item name
     * @param itemName The item's name
     * @return The corresponding Item
     * @throws InvalidIdException for invalid ID
     */
    public Integer getRequestItemIDByName(String itemName) throws InvalidIdException{
        for (Item item: listItemToAdd){
            if (item.getName().equals(itemName)){ return item.getItemId(); }
        }
        throw new InvalidIdException("Invalid Item Id");
    }


    /**
     * Return the item by given item ID
     * @param itemId The item's ID
     * @return The corresponding Item
     * @throws InvalidIdException for invalid ID
     */
    public Item getDeletedItemById(int itemId) throws InvalidIdException{
        for (Item item: listDeletedItem){
            if (item.getItemId() == itemId){
                return item;
            }
        }
        throw new InvalidIdException("Invalid Item Id");
    }


    /**
     * Set the item's current holder's ID to currHolderId
     * @param itemId The item's ID
     * @param currHolderId The current holder's ID
     */
    public void setCurrHolderId(int itemId, int currHolderId){
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                item.setCurrHolderId(currHolderId);
            }
        }
    }
    /*
     if don't store a map for item category in itemManager:

     public ArrayList<Integer> getCategoryItem(String category){
        ArrayList<Integer> itemList = new ArrayList<>();
        for (Item item: listItem){
            if (item.category.equals(category)){
                itemList.add(item.getItemId());
            }
        }
        return itemList;
    }
    */

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

    /**
     * Return all items
     * @return A list of items
     */
    public ArrayList<Item> getListItemToAdd(){
        return listItemToAdd;
    }

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
     * Return all the items
     * @return A list of all the items
     */
    public ArrayList<Item> getAllItem(){
        return listItem;
    }

    /**
     * Add item to listItem
     * @param item The item
     */
    public void addItemToAllItemsList(Item item){
        listItem.add(item);
    }

    /**
     * Remove item from listItem
     * @param itemId The Item's ID
     */
    public void removeFromListItem(int itemId) {
        listItem.removeIf(item -> item.getItemId() == itemId);
    }

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
     * Return a list of IDs for a given item
     * @param item The item object
     * @return a list of IDs (Item's Id, Owner's Id, Current Holder's ID)
     */
    public ArrayList<Integer> getIDFromItem(Item item){
        ArrayList<Integer> listId = new ArrayList<>();
        for (Item item_: listItem){
            if (item_.getItemId() == item.getItemId()){
                Collections.addAll(listId, item.getItemId(), item.getOwnerId(), item.getCurrHolderId());
            }
        }
        return listId;
    }

    /**
     * Return a list of items in listItem with corresponding Ids
     * @param listIds A list of items' Ids
     * @return A list of items with corresponding Ids
     */
    public ArrayList<Item> getItemsByIds(ArrayList<Integer> listIds) throws InvalidIdException {
        ArrayList<Item> items = new ArrayList<>();
        Set<Integer> setIds = new HashSet<>(listIds);
        for (Integer id : setIds) {
            items.add(getItembyId(id));
        }
        return items;
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

    public boolean getTradable(int itemId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId){
                return item.getTradable();
            }
        }
        throw new InvalidIdException("Invalid Item Id");
    }

    public void setTradable(ArrayList<Integer> listIds, boolean tradable) throws InvalidIdException {
        Set<Integer> setIds = new HashSet<>(listIds);
        for (Integer id: setIds){
            this.getItembyId(id).setTradable(tradable);
        }
    }

    public ArrayList<Item> getAllTradableItems(){
        ArrayList<Item> items = new ArrayList<>();
        for (Item item: listItem){
            if (item.getTradable()){
                items.add(item);
            }
        }
        return items;
    }

    public void addItemToListDeletedItem(Item item) {listDeletedItem.add(item);}

    public void removeItemFromListDeletedItem(int itemID) {
        listDeletedItem.removeIf(item -> item.getItemId() == itemID);
    }

    public HashMap<Category, ArrayList<Integer>> getAllCategoryItem(){
        HashMap<Category, ArrayList<Integer>> category = new HashMap<>();
        for (Category cat: Category.values()){
            category.put(cat, getCategoryItem(cat));
        }
        return category;
    }

    public HashMap<Category, ArrayList<Integer>> getAllCategoryItem(ArrayList<Item> items){
        HashMap<Category, ArrayList<Integer>> category = new HashMap<>();
        for (Category cat: Category.values()){
            if (!getCategoryItem(cat, items).isEmpty()){
                category.put(cat, getCategoryItem(cat, items));
            }
        }
        return category;
    }

    /**
     * Return all the items in the category
     * @param category The category of the item
     * @return A list of all item IDS in the category
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

    public ArrayList<Integer> getCategoryItem(Category category, ArrayList<Item> items){
        ArrayList<Integer> lst = new ArrayList<>();
        for (Item item: items){
            if (item.getCategory().equals(category)){
                lst.add(item.getItemId());
            }
        }
        return lst;
    }

    public ArrayList<Category> getSortedCategory(HashMap<Category, ArrayList<Integer>> category){
        ArrayList<Category> out = new ArrayList<>();
        while (!category.isEmpty()){
            Category most = null;
            for (Category c: category.keySet()){
                if (most == null || category.get(c).size() > category.get(most).size()){
                    most = c;
                }
            }
            out.add(most);
            category.remove(most);
        }
        return out;
    }

    public ArrayList<Integer> getMatchItem(ArrayList<Item> wishlist) throws InvalidIdException {
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
        return getMostMatchItem(wishlist);      // If all of the items in wishlist are not tradable
    }

    public ArrayList<Integer> getMostMatchItem(ArrayList<Item> wishlist) throws InvalidIdException {
        ArrayList<Integer> ids = new ArrayList<>();
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



}

