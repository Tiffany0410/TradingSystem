package managers.actionmanager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Store all the actions which can be cancelled
 * @author Chengle Yang
 * @version IntelliJ IDEA 2020.1
 */
public class ActionManager {
    private ArrayList<Action> listOfAllActions;
    private ArrayList<Action> listOfRevocableActions;


    // Constructor
    public ActionManager() {
        this.listOfAllActions = new ArrayList<>();
        this.listOfRevocableActions = new ArrayList<>();
    }

    // getter for all Actions
    public ArrayList<Action> getListOfAllActions() {return this.listOfAllActions;}

    //getter for all Revocable Actions
    public ArrayList<Action> getListOfRevocableActions() {return this.listOfRevocableActions;}


    // setter
    public void addActionToListAllActions(int actionOwnerID, String userType, String menuNumber,
                                          int adjustableInt, String adjustableStr) {
        int actionID;
        if (listOfAllActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_max_ID(this.getAllActionID(listOfAllActions)) + 1;}
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        listOfAllActions.add(new_action);
    }

    // setter
    public void addActionToListRevocableActions(int actionOwnerID, String userType, String menuNumber,
                                                int adjustableInt, String adjustableStr) {
        int actionID;
        if (listOfRevocableActions.isEmpty()) {actionID = 1;}
        else {actionID = helper_max_ID(this.getAllActionID(listOfRevocableActions)) + 1;}
        Action new_action = new Action(actionID, userType, actionOwnerID, menuNumber, adjustableInt, adjustableStr);
        listOfRevocableActions.add(new_action);
    }

    // remove the action from list
    public void deleteAction(int actionID) {
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfAllActions.size()) {
            if (listOfAllActions.get(acc).getActionID() == actionID) { listOfAllActions.remove(acc); }
            acc ++;
        }
    }

    // helper for max action ID in the list
    private int helper_max_ID(List<Integer> listOfAllID) {
        int max_ID = 1;
        for (int id: listOfAllID) {
            if (id > max_ID) {max_ID = id;}
        }
        return max_ID;
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

    // get the Action by actionID from list of Revocable Actions
    public Action findActionByID(int actionID) {
        for (Action action: listOfAllActions) {
            if (action.getActionID() == actionID) {return action;}
        }
        return null;
    }

}
