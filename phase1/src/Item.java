import java.io.Serializable;

public class Item implements Serializable {
    private String name;
    private String description;
    private int itemId;
    private int ownerId;
    private int currHolderId;
    private static int idNumber = 1;

    public Item(String name, String description, int ownerId) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        currHolderId = ownerId;
        itemId = idNumber;
        idNumber ++;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public int getItemId(){
        return itemId;
    }

    public int getOwnerId(){
        return ownerId;
    }

    public int getCurrHolderId(){
        return currHolderId;
    }

    public void setCurrHolderId(int currHolderId){
        this.currHolderId = currHolderId;
    }
}
