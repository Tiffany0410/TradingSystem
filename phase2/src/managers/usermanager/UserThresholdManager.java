package managers.usermanager;
import java.io.Serializable;
import java.util.ArrayList;

public class UserThresholdManager implements Serializable {
    ArrayList<String[]> listUnfreezeRequest;

    public UserThresholdManager(){
        this.listUnfreezeRequest = new ArrayList<>();
    }
    /**
     * Freezes a User
     * @param person The User to freeze
     * @return true if the User was frozen, false otherwise
     */
    protected boolean freezeUser(TradableUser person){
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
     * @param person The User to unfreeze
     * @return true if the User was unfrozen, false otherwise
     */
    protected boolean unfreezeUser(String username, TradableUser person){
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
     * @param person The User that is being requested
     * @return true if the request was successful, false otherwise
     */
    protected boolean requestUnfreeze(String username, String message, TradableUser person){
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
     * @param person  The User to get the info of
     * @return true if the User is frozen, false if the User is not
     */
    protected boolean getFrozenStatus(TradableUser person) {
        if (person != null){
            return person.getIfFrozen();
        }
        return false;
    }

    /**
     * Changes thresholds according to what is requested
     * @param threshold The threshold to be changed
     * @param change The new threshold if there is any
     * @param person The User to change
     */
    protected void setThreshold (String threshold, int change, TradableUser person) {
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
     * @param threshold The name of the information that is wanted
     * @param person The User to get the info of
     * @return The information that is requested
     */
    protected int getInfo(String threshold, TradableUser person){
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
    protected ArrayList<String[]> getListUnfreezeRequest() {
        return listUnfreezeRequest;
    }

    /**
     * Lets a User change their status to go on vacation
     * @param userID The ID of the User
     * @param person The User to change
     * @param listTradableUser List of all Users
     * @param ucm A UserCommunityManager
     * @return true if the change was performed, false otherwise
     */
    protected boolean goOnVacation(int userID, TradableUser person, UserCommunityManager ucm,
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
     * @param listTradableUser List of all Users
     * @param person The User to change
     * @param ucm A UserCommunityManager
     * @return true if the change was performed, false otherwise
     */
    protected boolean comeFromVacation(int userID, TradableUser person, UserCommunityManager ucm,
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
