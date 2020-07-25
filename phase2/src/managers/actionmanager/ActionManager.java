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
    private ArrayList<Action> listOfActions;


    // Constructor
    public ActionManager() {
        this.listOfActions = new ArrayList<>();
    }

    // getter for all actions
    public ArrayList<Action> getListOfActions() {return this.listOfActions;}


    // setter
    public void addAction(int actionOwnerID, String menuNumber, int adjustableInt) {
        int actionID;
        if (listOfActions.isEmpty()) {actionID = 1;}
        else {actionID = this.helper_max_ID(this.getAllActionID()) + 1;}
        Action new_action = new Action(actionID, actionOwnerID, menuNumber, adjustableInt);
        listOfActions.add(new_action);
    }

    // remove the action from list
    public void deleteAction(int actionID) {
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfActions.size()) {
            if (listOfActions.get(acc).getActionID() == actionID) { listOfActions.remove(acc); }
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
    public List<Integer> getAllActionID() {
        List<Integer> allID = new ArrayList<>();
        // Used to track Action index in listOfActions
        int acc = 0;
        // find the index of action in the listOfActions
        while (acc < listOfActions.size()) {
            allID.add(listOfActions.get(acc).getActionID());
            acc ++;
        }
        return allID;
    }

    // get the Action by actionID
    public Action findActionByID(int actionID) {
        for (Action action: listOfActions) {
            if (action.getActionID() == actionID) {return action;}
        }
        return null;
    }

}
