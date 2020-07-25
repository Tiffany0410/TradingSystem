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
    private int itemID;
}
