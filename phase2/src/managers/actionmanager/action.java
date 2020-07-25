package managers.actionmanager;

import java.io.Serializable;

/**
 * Store the key info of the action which can be cancelled
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class Action implements Serializable {
    private int actionID;
    private int actionOwnerID;
    // MenuOption
    private String menuOption;
    private int adjustableInt;

    // Constructor
    public Action(int actionID, int actionOwnerID, String menuOption, int adjustableInt) {
        this.actionID = actionID;
        this.actionOwnerID = actionOwnerID;
        this.menuOption = menuOption;
        this.adjustableInt = adjustableInt;
    }

    // getter for Action ID
    public int getActionID() {return this.actionID;}

    // getter for Menu Option
    public String getMenuOption() {return this.menuOption;}

    // getter for Action Owner ID
    public int getActionOwnerID() {return this.actionOwnerID;}

    // getter for AdjustableInt
    public int getAdjustableInt() {return this.adjustableInt;}
}
