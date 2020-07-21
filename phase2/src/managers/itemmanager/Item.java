package managers.itemmanager;

import java.io.Serializable;

/**
 * An instance of this class represents an item in the system.
 *
 *
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1.1
 */
public class Item implements Serializable {
    private String name;
    private String description;
    private int itemId;
    private int ownerId;
    private int currHolderId;

    /** Constructor of item.
     * Set this name with name, set this description with description, set this ownerId with ownerId, set this
     * currHolderId with ownerId by default, and set itemId with idNumber.
     * @param name The name of this item
     * @param description The description of this item
     * @param ownerId The owner's id of this item
     */
    public Item(String name, String description, int ownerId, int itemID) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        currHolderId = ownerId;
        this.itemId = itemID;
    }

    /** Get the name of this item
     * @return The name of this item
     */
    public String getName(){
        return name;
    }

    /** Set the name of this item
     * @param name The name of this item
     */
    public void setName(String name){
        this.name = name;
    }

    /** Get the description of this item
     * @return the description of this item
     */
    public String getDescription(){
        return description;
    }

    /** Set the description of this item
     * @param description The description of this item
     */
    public void setDescription(String description){
        this.description = description;
    }

    /** Get the ID of this item
     * @return The ID of this item
     */
    public int getItemId(){
        return itemId;
    }

    /** Get Owner's ID of this item
     * @return The owner's ID of this item
     */
    public int getOwnerId(){
        return ownerId;
    }

    /** Get current holder's ID of this item
     * @return The current holder's ID of this item
     */
    public int getCurrHolderId(){
        return currHolderId;
    }

    /** Set the current holder's ID of this item
     * @param currHolderId The current holder's ID of this item
     */
    public void setCurrHolderId(int currHolderId){
        this.currHolderId = currHolderId;
    }

    /** Override the to string to describe the item
     * @return A string description of this item
     */
    public String toString(){
        return "This " + name + " with ID " + itemId + " is: " + description + ".\n" +
                "Owner's ID is " + ownerId + " and current holder's ID is " + currHolderId + ".";
    }
}
