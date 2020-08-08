package managers.actionmanager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Store and manage all the actions which can be cancelled
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class ActionManager implements Serializable {
    private ArrayList<Action> listOfAllActions;
    private ArrayList<Action> listOfCurrentRevocableActions;
    private ArrayList<Action> listOfDeletedRevocableActions;
    //Key is the actionID and related value is the userID who send the request
    private Map<Integer, Integer> mapOfUndoRequest;


    /**
     * Constructs a ActionManager with no Historical Actions
     */
    public ActionManager() {
        this.listOfAllActions = new ArrayList<>();
        this.listOfCurrentRevocableActions = new ArrayList<>();
        this.listOfDeletedRevocableActions = new ArrayList<>();
        this.mapOfUndoRequest = new HashMap<>();
    }


    /**
     * Getter for all Historical Actions done by AdminUser and RegularUser in system
     *
     * @return The ArrayList of all Historical Actions done by AdminUser and RegularUser in system
     */
    public ArrayList<Action> getListOfAllActions() {return this.listOfAllActions;}


    /**
     * Getter for all Current Revocable Actions done by RegularUser in system
     *
     * @return The ArrayList of all Current Revocable Actions done by RegularUser in system
     */
    public ArrayList<Action> getListOfCurrentRevocableActions() {return this.listOfCurrentRevocableActions;}


    /**
     * Getter for all undo request submitted by RegularUser in system
     *
     * @return The Map of all undo request submitted by RegularUser in system
     */
    public Map<Integer, Integer> getMapOfUndoRequest() {return this.mapOfUndoRequest;}


    /**
     * Add new Action into the list contains all Historical Actions done by AdminUser and RegularUser in system
     *
     * @param actionOwnerID The Id of AdminUser or RegularUser who did the action
     * @param userType The type of User: "regularUser" or "adminUser"
     * @param menuNumber The menu option number click by User
     * @param adjustableInt The adjustable integer used to store key info of the action
     * @param adjustableStr  The adjustable string used to store key info of the action
     */
    public void addActionToAllActionsList(int actionOwnerID, String userType, String menuNumber,
                                          int adjustableInt, String adjustableStr) {
        int actionID;
        // Provide an appropriate Action ID
        if (listOfAllActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_maxAllID() + 1;}
        // Create a new Action
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        // Add Action into the list
        listOfAllActions.add(new_action);
    }


    /**
     * Add new Action into the list contains all Current Revocable Actions done by RegularUser in system
     *
     * @param actionOwnerID The Id of RegularUser who did the action
     * @param userType The type of User: "regularUser" or "adminUser"
     * @param menuNumber The menu option number click by User
     * @param adjustableInt The adjustable integer used to store key info of the action
     * @param adjustableStr  The adjustable string used to store key info of the action
     */
    public void addActionToCurrentRevocableList(int actionOwnerID, String userType, String menuNumber,
                                                int adjustableInt, String adjustableStr) {
        int actionID;
        // Provide an appropriate Action ID
        if (listOfCurrentRevocableActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_maxRevocableID() + 1;}
        // Create a new Action
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        // Add Action into the list
        listOfCurrentRevocableActions.add(new_action);
    }

    /**
     * Setter: add action into the list of Deleted Revocable Actions
     *
     * @param action The action deleted by AdminUser
     */
    public void addActionToDeletedRevocableList(Action action) {listOfDeletedRevocableActions.add(action);}


    /**
     * Helper function used to get maximum action ID in All Action List
     *
     * @return the maximum action ID in All Action List
     */
    private int helper_maxAllID() {
        // Find the max ID in All Action List
        int max_ID = 1;
        for (Action action : listOfAllActions) {
            if (action.getActionID() > max_ID) { max_ID = action.getActionID();}
        }
        return max_ID;
    }


    /**
     * Helper function used to get maximum action ID in two Revocable Action List
     *
     * @return the maximum action ID in two Revocable Action List
     */
    private int helper_maxRevocableID() {
        // Find the max ID in Current Revocable Action List
        int max_current_ID = 1;
        for (Action action: listOfCurrentRevocableActions) {
            if (action.getActionID() > max_current_ID) {max_current_ID = action.getActionID();}
        }
        // Find the max ID in Deleted Revocable Action List
        int max_deleted_ID = 1;
        for (Action action: listOfCurrentRevocableActions) {
            if (action.getActionID() > max_deleted_ID) { max_deleted_ID = action.getActionID();}
        }
        // Return the maximum Action ID in two Revocable Action List
        return Math.max(max_current_ID, max_deleted_ID);
    }

    /**
     * Remove the action from current Revocable Action list by provided Action ID
     *
     * @param actionID The Action ID provided by AdminUser and used to remove
     *                 the action from current Revocable Action list
     */
    public void deleteAction(int actionID) {
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfCurrentRevocableActions.size()) {
            if (listOfCurrentRevocableActions.get(acc).getActionID() == actionID) { listOfCurrentRevocableActions.remove(acc); }
            acc ++;
        }
    }


    /**
     * Return the list that contain all ActionID in the provided list
     *
     * @param listOfAction The List contain Actions
     * @return The List of id from provided list
     */
    public List<Integer> getAllActionID(ArrayList<Action> listOfAction) {
        List<Integer> allID = new ArrayList<>();
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfAction.size()) {
            allID.add(listOfAction.get(acc).getActionID());
            acc ++;
        }
        return allID;
    }

    /**
     * Get the Action from list of current Revocable Actions by provided actionID
     *
     * @param actionID The Action ID provided by AdminUser
     * @return The Action from list of current Revocable Actions by provided actionID
     */
    public Action findActionByID(int actionID) {
        for (Action action: listOfCurrentRevocableActions) {
            if (action.getActionID() == actionID) {return action;}
        }
        return null;
    }


    /**
     * Return ArrayList of all revocable action of specific user by provided RegularUser ID
     *
     * @param userID The RegularUser ID provided by AdminUser
     * @return The ArrayList of all revocable action of specific user by provided RegularUser ID
     */
    public ArrayList<Action> searchRevocableActionByID(int userID) {
        ArrayList<Action> tempList = new ArrayList<>();
        for (Action action: listOfCurrentRevocableActions) { if (action.getActionOwnerID() == userID) {tempList.add(action);} }
        return tempList;
    }

    /**
     * Return true if listOfCurrentRevocableActions contains the provided Action
     *
     * @param targetAction The Action need to be checked
     * @return True if listOfCurrentRevocableActions contains the provided Action
     */
    public boolean checkRevocable(Action targetAction) {return listOfCurrentRevocableActions.contains(targetAction);}


    /**
     * Add the new Undo Request into the mapOfUndoRequest and return the result
     *
     * @param actionID The ID of revocable actions
     * @param userID The RegularUser ID who want to undo actions
     * @return True if successfully add request
     */
    public boolean addUndoRequest(int actionID, int userID) {
        if (!mapOfUndoRequest.containsKey(actionID)) {
            mapOfUndoRequest.put(actionID, userID);
            return true;
        }
    return false;
    }

    /**
     * Delete the new Undo Request into the mapOfUndoRequest and return the result
     *
     * @param actionID The ID of revocable actions
     * @return True if successfully delete request
     */
    public boolean deleteUndoRequest(int actionID){
        if (mapOfUndoRequest.containsKey(actionID)) {
            mapOfUndoRequest.remove(actionID);
            return true;
        }
        return false;
    }


    /**
     * Return true if mapOfUndoRequest contains the provided actionID
     *
     * @param actionID The ID of revocable actions
     * @return True if mapOfUndoRequest contains the provided actionID
     */
    public boolean checkUndoRequest(int actionID) {
        return mapOfUndoRequest.containsKey(actionID);
    }
}
