package managers.itemmanager;
import exception.InvalidIdException;
import managers.usermanager.User;

import java.io.Serializable;
import java.util.ArrayList;
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


    public ItemManager(){
        listItem = new ArrayList<>();
        categoryItem = new HashMap<>();
        listItemToAdd = new ArrayList<>();
    }

    public String getNamebyId(int itemId) throws InvalidIdException {
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                return item.getName();
            }
        }
        throw new InvalidIdException("Invalid Item ID");
    }

    public String getInfobyID(int itemId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId) {
                return item.toString();
            }
        }
        throw new InvalidIdException("Invalid Item ID");
    }

    public Item getItembyId(int itemId) throws InvalidIdException{
        for (Item item: listItem){
            if (item.getItemId() == itemId){
                return item;
            }
        }
        throw new InvalidIdException("Invalid Item Id");
    }

    public ArrayList<Item> getAllItem(){
        return listItem;
    }

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

    public ArrayList<Integer> getCategoryItem(String category){
        return categoryItem.get(category);
    }

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

    public void addItem(Item item){
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
}
