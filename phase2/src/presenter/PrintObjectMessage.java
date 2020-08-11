package presenter;

import managers.actionmanager.Action;
import managers.itemmanager.Item;
import managers.usermanager.TradableUser;

import java.util.ArrayList;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan, Shi Tang, Chengle Yang, Jianhong Guo
 * @version IntelliJ IDEA 2020.1
 */
class PrintObjectMessage {

    /**
     * Constructs PrintObjectMessage
     */
    PrintObjectMessage(){

    }


    /**
     * Puts together the result of action with object type.
     * @param obj The list of objects need to be printed.
     * @return The message related to the result of the action.
     */
    protected String printResult(ArrayList<Object> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    /**
     * Puts together the list of items in properly formatted strings.
     * @param obj The list of items.
     * @return The list of items in properly formatted strings.
     */
    protected String printItemResult(ArrayList<Item> obj) {
        StringBuilder string = new StringBuilder();

        int count = 1;
        for (Object o : obj) {
            // if o is not a string[]
            if (!(o instanceof String[])) {
                string.append("#").append(count).append(". ").append(o.toString()).append("\n");
            }
            // if o is a string[]
            else {
                String[] strings = (String[]) o;
                string.append("#").append(count).append(". ").append("\n").append("Username: ").append(strings[0]);
                string.append("Message: ").append(strings[1]).append("\n");
            }
            count++;
        }

        return string.toString();
    }

    /**
     * Print out the list of username of the Regular Users.
     * @param listOfUser The list of Regular Users.
     * @return The list of username of the Regular Users.
     */
    protected String printListUser(ArrayList<TradableUser> listOfUser) {
        StringBuilder stringBuilder = new StringBuilder();
        for (TradableUser user : listOfUser) {
            stringBuilder.append("Tradable User ID #").append(user.getId()).append(" with username: ").append(user.getUsername()).append("\n");
        } return stringBuilder.toString();
    }


    /**
     * Puts together a string that contains all string representation
     * of the objects.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects.
     */
    protected String printListObject(ArrayList<Object> objects){
        StringBuilder out = new StringBuilder();
        if (objects.isEmpty()) {return "\n";}
        for (Object object: objects){
            if (object != null) {out.append(object.toString()).append("\n");}
        }
        return out.toString();
    }

    /**
     * Puts together a string that contains all string representation
     * of the objects, numbered.
     * @param objects The list of objects to be represented in string.
     * @return The string that contains all string representation
     * of the objects, numbered.
     */
    protected String printListNumberedObject(ArrayList<Object> objects){
        int i = 0;
        StringBuilder out = new StringBuilder();
        for (Object object: objects){
            out.append("#").append(i).append(". ").append(object.toString()).append("\n");
            i ++;
        }
        return out.toString();
    }

    /**
     * Puts together all the historical actions.
     * @param listOfAction The list of historical actions.
     * @return The string representation of all the historical actions in the list.
     */
    protected String printHistoricalAction(ArrayList<Action> listOfAction) {
        StringBuilder string = new StringBuilder();
        for (Action action : listOfAction) {
            String userType = action.getUserType();

            RegularUserActionMessage regularUserActionMessage = new RegularUserActionMessage();
            AdminUserActionMessage adminUserActionMessage = new AdminUserActionMessage();

            switch (userType) {
                case "regularUser":
                    string.append(regularUserActionMessage.regularUserAction(action));
                    break;
                case "adminUser":
                    string.append(adminUserActionMessage.adminUserAction(action));
                    break;
            }
        }
        return string.toString();
    }

    /**
     * Puts together a string representation of an object.
     * @param object The object.
     * @return The string representation of the object.
     */
    protected String printObject(Object object){
        return object.toString();
    }

}
