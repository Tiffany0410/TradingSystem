package managers.usermanager;

import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class UserThresholdManager {
    ArrayList<String[]> listUnfreezeRequest;

    public UserThresholdManager(){
        this.listUnfreezeRequest = new ArrayList<>();
    }
    /**
     * Freezes a User
     * @param username The username of the the User that is being frozen
     * @return true if the User was frozen, false otherwise
     */
    public boolean freezeUser(String username, TradableUser person){
        boolean out = false;
        if (person != null){
            if (!person.getIfFrozen()){
                person.setIfFrozen(true);
                out = true;
            }
        }
        return out;
    }

    /**
     * Unfreezes a User
     * @param username The username of the User that is being unfrozen
     * @return true if the User was unfrozen, false otherwise
     */
    public boolean unfreezeUser(String username, TradableUser person){
        boolean out = false;
        if (person != null){
            if (person.getIfFrozen()){
                person.setIfFrozen(false);
                out = true;
            }
        }
        if (out) {
            for (String[] request : listUnfreezeRequest) {
                if (request[0].equals(username)) {
                    listUnfreezeRequest.remove(request);
                    return true;
                }
            }
        }
        return out;
    }

    /**
     * Sends a request to unfreeze a User
     * @param username The username of the User
     * @param message The message of the User to unfreeze
     * @return true if the request was successful, false otherwise
     */
    public boolean requestUnfreeze(String username, String message, TradableUser person){
        if (person == null){
            return false;
        }
        if (!person.getIfFrozen()){
            return false;
        }
        for (String[] request: listUnfreezeRequest) {
            if (request[0].equals(username)) {
                return false;
            }
        }
        String[] toAdd = {username, message};
        listUnfreezeRequest.add(toAdd);
        return true;
    }

    /**
     * Returns the frozen state of the User
     * @return true if the User is frozen, false if the User is not
     */
    public boolean getFrozenStatus(TradableUser person) {
        if (person != null){
            return person.getIfFrozen();
        }
        return false;
    }

    /**
     * Changes thresholds according to what is requested
     * @param userID The ID of the User
     * @param threshold The threshold to be changed
     * @param change The new threshold if there is any
     */
    public void setThreshold (int userID, String threshold, int change, TradableUser person) {
        if (person != null) {
            switch (threshold) {
                case "TransactionLeftForTheWeek":
                    person.setTransactionLeftForTheWeek(change);
                    break;
                case "NumFrozen":
                    person.addOneToNumFrozen();
                    break;
            }
        }
    }

    /**
     * Gives back the specified information of a User
     * @param userID The ID of the User
     * @param threshold The name of the information that is wanted
     * @return The information that is requested
     */
    public int getInfo(int userID, String threshold, TradableUser person){
        if (person != null){
            switch (threshold){
                case "TransactionLeftForTheWeek":
                    return person.getNumTransactionLeftForTheWeek();
                case "NumFrozen":
                    return person.getNumFrozen();
                case "NumLent":
                    return person.getNumLent();
                case "NumBorrowed":
                    return person.getNumBorrowed();
                case "Vacation":
                    boolean convert = person.getOnVacation();
                    if (convert){
                        return 1;
                    } else {
                        return 0;
                    }
            }
        }
        return -1;
    }

    /**
     * Get the list of usernames and messages of User that request to be unfrozen
     * @return The list of usernames and messages
     */
    public ArrayList<String[]> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }

    /**
     * Lets a User change their status to go on vacation
     * @param userID The ID of the User
     * @return true if the change was performed, false otherwise
     */
    public boolean goOnVacation(int userID, TradableUser person, UserCommunityManager ucm,
                                ArrayList<TradableUser> listTradableUser){
        if (person != null){
            if (person.getOnVacation()){
                return false;
            } else {
                person.setOnVacation(true);
                ucm.editFollowerLogs("User " + person.getUsername() + "is now on vacation.", userID, person,
                        listTradableUser);
                return true;
            }
        }
        return false;
    }

    /**
     * Lets a User change their status to come back from vacation
     * @param userID The ID of the User
     * @return true if the change was performed, false otherwise
     */
    public boolean comeFromVacation(int userID, TradableUser person, UserCommunityManager ucm,
                                    ArrayList<TradableUser> listTradableUser){
        if (person != null){
            if (!person.getOnVacation()){
                return false;
            } else {
                person.setOnVacation(false);
                ucm.editFollowerLogs("User " + person.getUsername() + "has come back from vacation.", userID,
                        person, listTradableUser);
                return true;
            }
        }
        return false;
    }
}
