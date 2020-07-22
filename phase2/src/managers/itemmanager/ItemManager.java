package managers.itemmanager;
import exception.InvalidIdException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * Stores all the items and manages the items
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1.1
 */
public class ItemManager implements Serializable {

    private ArrayList<Item> listItem;
    private ArrayList<Item> listItemToAdd;
    private HashMap<String, ArrayList<Integer>> categoryItem;
    // private Map<Integer, Integer> waitList;


    /**
     * Constructs A ItemManager
     */
    public ItemManager(){
        listItem = new ArrayList<>();
        categoryItem = new HashMap<>();
        listItemToAdd = new ArrayList<>();
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
     * Return all the items
     * @return A list of all the items
     */
    public ArrayList<Item> getAllItem(){
        return listItem;
    }

    /**
     * Set the item's current holder's ID to currHolderId
     * @param itemId The item's ID
     * @param currHolderId The current holder's ID
     * @throws InvalidIdException for invalid ID
     */
    public void setCurrHolderId(int itemId, int currHolderId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                item.setCurrHolderId(currHolderId);
            }
        }
        throw new InvalidIdException("Invalid Item ID");
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
     * Return all the items in the category
     * @param category The category of the item
     * @return A list of all items in the category
     */
    public ArrayList<Integer> getCategoryItem(String category){
        return categoryItem.get(category);
    }

    /**
     * Creates new item
     * @param name The item's name
     * @param description The item's description
     * @param ownerId The item's owner's id
     */
    public void requestAddItem(String name, String description, int ownerId){
        int temp_id;
        if (listItem.isEmpty()){
            if (listItemToAdd.isEmpty()){ temp_id = 1; }
            else {temp_id = listItemToAdd.size() + 1; } }
        else{
            if (listItemToAdd.isEmpty()){temp_id = listItem.size() + 1; }
            else{ temp_id = listItem.size() + listItemToAdd.size() + 1; }}
        Item item = new Item(name, description, ownerId, temp_id);
        listItemToAdd.add(item);
    }

    /**
     * Add item to listItem
     * @param item The item
     */
    public void addItemToAllItemsList(Item item){
        listItem.add(item);
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
    public ArrayList<Item> allTradableItems(int userID){
        ArrayList<Item> allItem = new ArrayList<>();
        for (Item item: listItem){
            if (item.getOwnerId() != userID){
                allItem.add(item);
            }
        }
        return allItem;
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
     * @param item The Item
     */
    public void removeFromListItemToAdd(Item item){
        listItemToAdd.remove(item);
    }

    /**
     * Return a list of IDs for a given item
     * @param item The item object
     * @return a list of IDs (Item's Id, Owner's Id, Current Holder's ID)
     */
    public ArrayList<Integer> getIDFromItem(Item item){
        ArrayList<Integer> listId = new ArrayList();
        for (Item item_: listItem){
            if (item_.getItemId() == item.getItemId()){
                Collections.addAll(listId, item.getItemId(), item.getOwnerId(), item.getCurrHolderId());
            }
        }
        return listId;
    }
}

