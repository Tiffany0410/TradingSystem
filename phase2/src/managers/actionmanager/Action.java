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

    /**
     * Construct an Action.
     *
     * @param actionID action's id.
     * @param userType action owner's type: RegularUser or AdminUser.
     * @param actionOwnerID action owner's id
     * @param menuOption the menu Option that User click
     * @param adjustableInt adjustable integer for different action
     * @param adjustableStr adjustable string for different action
     */
    public Action(int actionID, String userType, int actionOwnerID, String menuOption, int adjustableInt, String adjustableStr) {
        this.actionID = actionID;
        this.actionOwnerID = actionOwnerID;
        this.menuOption = menuOption;
        this.adjustableInt = adjustableInt;
        this.userType = userType;
        this.adjustableStr = adjustableStr;

    }


    /**
     * Get the action's id.
     *
     * @return action's id.
     */
    // getter for Action ID
    public int getActionID() {return this.actionID;}


    /**
     * Get the action's menuOption.
     *
     * @return action's menuOption.
     */
    // getter for Menu Option
    public String getMenuOption() {return this.menuOption;}


    /**
     * Get the action owner's id.
     *
     * @return action owner's id.
     */
    // getter for Action Owner ID
    public int getActionOwnerID() {return this.actionOwnerID;}


    /**
     * Get the adjustable integer for different action.
     *
     * @return adjustable integer for different action.
     */
    // getter for AdjustableInt
    public int getAdjustableInt() {return this.adjustableInt;}


    /**
     * Get the adjustable string for different action.
     *
     * @return adjustable string for different action.
     */
    // getter for AdjustableStr
    public String getAdjustableStr() {return this.adjustableStr;}


    /**
     * Get the action owner's type.
     *
     * @return action owner's type.
     */
    // getter for UserType
    public String getUserType() {return this.userType;}


    /**
     * Override the to string to describe the action
     *
     * @return A string description of this action
     */
    @Override
    public String toString() {
        return "Action #" + this.actionID + ": " + this.userType + " #" + this.actionOwnerID
                + " process menuOption #" + menuOption;
    }
}
