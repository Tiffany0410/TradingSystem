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
    /**
     * tradeType the type of the user(Regular or Admin)
     */
    public String userType;
    // MenuOption
    private String menuOption;
    private int adjustableInt;
    private String adjustableStr;

    // Constructor
    public Action(int actionID, String userType, int actionOwnerID, String menuOption, int adjustableInt, String adjustableStr) {
        this.actionID = actionID;
        this.actionOwnerID = actionOwnerID;
        this.menuOption = menuOption;
        this.adjustableInt = adjustableInt;
        this.userType = userType;
        this.adjustableStr = adjustableStr;

    }

    // getter for Action ID
    public int getActionID() {return this.actionID;}

    // getter for Menu Option
    public String getMenuOption() {return this.menuOption;}

    // getter for Action Owner ID
    public int getActionOwnerID() {return this.actionOwnerID;}

    // getter for AdjustableInt
    public int getAdjustableInt() {return this.adjustableInt;}

    // getter for AdjustableStr
    public String getAdjustableStr() {return this.adjustableStr;}

    // getter for UserType
    public String getUserType() {return this.userType;}
}
