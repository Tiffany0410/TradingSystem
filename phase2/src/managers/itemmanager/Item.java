package managers.itemmanager;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

/**
 * An instance of this class represents an item in the system.
 *
 * @author Shi Tang
 * @version IntelliJ IDEA 2020.1.1
 */
public class Item implements Serializable{
    private String name;
    private String description;
    private int itemId;
    private int ownerId;
    private int currHolderId;
    private boolean tradable;
    private Category category;
    //PropertyChangeListener
    private PropertyChangeSupport support;

    /**
     * Constructor of item.
     * Set this name with name, set this description with description, set this ownerId with ownerId, set this
     * currHolderId with ownerId by default, and set itemId with idNumber.
     *
     * @param name        The name of this item
     * @param description The description of this item
     * @param ownerId     The owner's id of this item
     */
    public Item(String name, String description, int ownerId, int itemID, Category category) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        currHolderId = ownerId;
        this.itemId = itemID;
        this.category = category;
        this.tradable = true;
        //property support
        support = new PropertyChangeSupport(this);
    }

    /**
     * Set the flag indicates whether the item is tradable
     *
     * @param tradable the flag indicates whether the item is tradable
     */
    public void setTradable(boolean tradable) {
        support.firePropertyChange("Tradable Status of the item with item id " + itemId, this.tradable, tradable);
        this.tradable = tradable;
    }

    /**
     * Get the flag indicates whether the item is tradable
     *
     * @return tradable the flag indicates whether the item is tradable
     */
    protected boolean getTradable() {
        return this.tradable;
    }

    /**
     * Get the name of this item
     *
     * @return The name of this item
     */
    public String getName() {
        return name;
    }

    /**
     * Get the description of this item
     *
     * @return the description of this item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the category of this item
     *
     * @return the category of item
     */
    protected Category getCategory() {
        return this.category;
    }

    /**
     * Get the ID of this item
     *
     * @return The ID of this item
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * Get Owner's ID of this item
     *
     * @return The owner's ID of this item
     */
    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Override the to string to describe the item
     *
     * @return A string description of this item
     */
    @Override
    public String toString() {
        return "Item id " + itemId + " : " + name + " \nDescription: " + description + "\n" +
                "Owner's ID is " + ownerId + " and current holder's ID is " + currHolderId + "."
                + " Tradable = " + getTradable() + ".\n";
    }

    /**
     * Add PropertyChangeListener to support
     *
     * @param pcl PropertyChangeListener that needs to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    /**
     * Remove PropertyChangeListener to support
     *
     * @param pcl PropertyChangeListener that needs to be removed
     */
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }


}
