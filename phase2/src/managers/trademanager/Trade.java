package managers.trademanager;

import java.io.Serializable;
import java.util.*;

/**
 * A trade
 * @author YuanzeBao
 * @version IntelliJ IDEA 2020.1
 */
public class Trade implements Serializable {
    private String OPEN = "Opened";
    private String WAIT = "Wait to be opened";
    private String CANCEL = "Cancelled";
    private String CLOSE = "Closed";
    private int tradeId;
    private int userId1;
    private int userId2;
    private int itemId;
    private int itemId1;
    private boolean isOneWayTrade;
    protected Map<Integer, String> userStatus = new HashMap<>();
    /**
     * tradeType the type of the trade
     */
    public String tradeType;
    /**
     * The type of the trade status (Open, Closed, Wait to be opened，Cancelled), default is Wait to be opened
     */
    public String tradeStatus = WAIT;


    /**
     * Constructors of the Trade
     *
     * @param userId1   user1 id if it is two way trade then this is also lenderid
     * @param userId2   user2 id if it is two way trade then this is also borrowerid
     * @param itemId    user borrow item id
     * @param tradeType trade type
     * @param isOneWayTrade True if it is one way trade
     */
    public Trade(int userId1, int userId2, int itemId, String tradeType, boolean isOneWayTrade, int tradeID) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.itemId = itemId;
        this.itemId1 = 0;
        this.tradeType = tradeType;
        this.isOneWayTrade = isOneWayTrade;
        this.tradeId = tradeID;
        userStatus.put(userId1, "Disagree");
        userStatus.put(userId2, "Disagree");
    }

    /**
     * Constructors of the Trade
     *
     * @param userId1   user1 id if it is two way trade then this is also lenderid
     * @param userId2   user2 id if it is two way trade then this is also borrowerid
     * @param itemId    user1 borrow item id
     * @param itemId1   user2 borrow item id
     * @param tradeType trade type
     * @param isOneWayTrade True if it is one way trade
     */
    public Trade(int userId1, int userId2, int itemId, int itemId1, String tradeType, boolean isOneWayTrade, int tradeID) {
        this.userId1 = userId1;
        this.userId2 = userId2;
        this.itemId = itemId;
        this.itemId1 = itemId1;
        this.tradeType = tradeType;
        this.isOneWayTrade = isOneWayTrade;
        this.tradeId = tradeID;
        userStatus.put(userId1, "Disagree");
        userStatus.put(userId2, "Disagree");
    }

    /** Get user status
     * @param userId user id
     * @return the status of user(Agree or Disagree)
     */
    public String getUserStatus(int userId){
        return userStatus.get(userId);
    }
    /**
     * set user status
     *
     * @param userId borrower id
     * @param status Agree or Disagree
     */
    protected void setUserStatus(int userId, String status) {
        userStatus.replace(userId, status);
    }

    /** Get is one way trade
     * @return true if it is one way trade
     */
    protected boolean getIsOneWayTrade(){
        return this.isOneWayTrade;
    }
    /**
     * Get user trade, user , item ids
     *
     * @return list of ids
     */
    protected List<Integer> getIds() {
        List<Integer> list = new ArrayList<>();
        if (itemId1 == 0) {
            Collections.addAll(list, tradeId, userId1, userId2, itemId);
        } else {
            Collections.addAll(list, tradeId, userId1, userId2, itemId, itemId1);
        }
        return list;
    }

    /**
     * Change the trade status to Open
     */
    public void openTrade() {
        this.tradeStatus = OPEN;
    }

    /**
     * Change the trade status to Closed
     */
    public void closedTrade() {
        this.tradeStatus = CLOSE;

    }

    /**
     * change the trade status to Cancelled
     */
    public void cancelTrade() {
        this.tradeStatus = CANCEL;
    }

    /**
     * Print the trade description
     *
     * @return trade id + borrow id + lender id + item id + trade type + trade status.
     */
    public String toString() {
        if (itemId1 == 0) {
            return "trade id:" + tradeId + ", " + "borrower id:" + userId1 + ", " + "lender id:" + userId2 + ", "
                    + "item id:" + itemId + "\n" +  "trade type: " + tradeType + ", " + "trade status:" + tradeStatus + "\n"
                    + "borrower status:" + userStatus.get(userId1) + ", " + "lender status:" + userStatus.get(userId2) +
                    ", " + "One way trade:" + isOneWayTrade;
        } else {
            return "trade id:" + tradeId + ", " + "user1 id1:" + userId1 + ", " + "user2 id:" + userId2 + ", "
                    + "item1 id:" + itemId + ", " + "item2 id: " + itemId1 + "\n" +
                    "trade type: " + tradeType + ", " + "trade status: " + tradeStatus + "\n"
                    + "user1 status:" + userStatus.get(userId1) + ", " + "user2 status:" + userStatus.get(userId2) + ", "+
                    "One way trade:" + isOneWayTrade;
        }
    }
}
