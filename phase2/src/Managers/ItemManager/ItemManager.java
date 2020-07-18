package Managers.ItemManager;
import Exception.InvalidIdException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

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

    // if don't store a map for item category in itemManager:
    //public ArrayList<Integer> getCategoryItem(String category){
    //    ArrayList<Integer> itemList = new ArrayList<>();
    //    for (Item item: listItem){
    //        if (item.category.equals(category)){
    //            itemList.add(item.getItemId());
    //        }
    //    }
    //    return itemList;
    //}

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

}
