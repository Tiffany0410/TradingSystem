package managers.actionmanager;
import java.util.ArrayList;
import java.util.List;

/**
 * Store all the actions which can be cancelled
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class ActionManager {
    private ArrayList<Action> listOfAllActions;
    private ArrayList<Action> listOfCurrentRevocableActions;
    private ArrayList<Action> listOfDeletedRevocableActions;


    // Constructor
    public ActionManager() {
        this.listOfAllActions = new ArrayList<>();
        this.listOfCurrentRevocableActions = new ArrayList<>();
    }

    // getter for all Actions
    public ArrayList<Action> getListOfAllActions() {return this.listOfAllActions;}

    //getter for all current Revocable Actions
    public ArrayList<Action> getListOfCurrentRevocableActions() {return this.listOfCurrentRevocableActions;}


    // setter
    public void addActionToAllActionsList(int actionOwnerID, String userType, String menuNumber,
                                          int adjustableInt, String adjustableStr) {
        int actionID;
        if (listOfAllActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_max_ID(this.getAllActionID(listOfAllActions)) + 1;}
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        listOfAllActions.add(new_action);
    }

    // setter
    public void addActionToCurrentRevocableList(int actionOwnerID, String userType, String menuNumber,
                                                int adjustableInt, String adjustableStr) {
        int actionID;
        if (listOfCurrentRevocableActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_max_ID(this.getAllActionID(listOfCurrentRevocableActions)) + 1;}
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        listOfCurrentRevocableActions.add(new_action);
    }


    // Setter: add action into the list of deleted Revocable Action
    public void addActionToDeletedRevocableList(Action action) {listOfDeletedRevocableActions.add(action);}

    // helper for max action ID in the list
    private int helper_max_ID(List<Integer> listOfAllID) {
        int max_ID = 1;
        for (int id: listOfAllID) {
            if (id > max_ID) {max_ID = id;}
        }
        return max_ID;
    }


    // remove the action from current Revocable Action list
    public void deleteAction(int actionID) {
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfCurrentRevocableActions.size()) {
            if (listOfCurrentRevocableActions.get(acc).getActionID() == actionID) { listOfCurrentRevocableActions.remove(acc); }
            acc ++;
        }
    }


    // return the list that contain all ActionID in the listOfActions
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

    // get the Action by actionID from list of current Revocable Actions
    public Action findActionByID(int actionID) {
        for (Action action: listOfCurrentRevocableActions) {
            if (action.getActionID() == actionID) {return action;}
        }
        return null;
    }


    // search all revocable action of specific user by provided ID
    public ArrayList<Action> searchRevocableActionByID(int userID) {
        ArrayList<Action> tempList = new ArrayList<>();
        for (Action action: listOfCurrentRevocableActions) { if (action.getActionOwnerID() == userID) {tempList.add(action);} }
        return tempList;
    }
}
