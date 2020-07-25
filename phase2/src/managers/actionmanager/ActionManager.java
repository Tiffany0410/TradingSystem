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
    private List<Action> listOfActions;


    // Constructor
    public ActionManager() {
        this.listOfActions = new ArrayList<>();
    }

}
