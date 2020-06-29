package bookTradeSystem;
import java.util.*;
import java.io.Serializable;
public class Trade implements Serializable {
    private int tradeId;
    private int borrowerId;
    private int lenderId;
    private int itemId;
    protected Map<Integer, String> userStatus = new HashMap<>();
    /**
     * tradeType the type of the trade
     */
    protected String tradeType;
    /**
     * The type of the trade status (Open, Closed, Wait to be opened，Cancelled), default is Wait to be opened
     */
    protected String tradeStatus = "Wait to be opened";
    private static int idNumber = 1;

    /** Constructors of the Trade
     * @param borrowerId borrower id
     * @param lenderId lender id
     * @param itemId item id
     * @param tradeType trade type
     */
    public Trade(int borrowerId, int lenderId, int itemId, String tradeType){
        this.borrowerId = borrowerId;
        this.lenderId = lenderId;
        this.itemId = itemId;
        this.tradeType = tradeType;
        userStatus.put(borrowerId, "Agree");
        userStatus.put(lenderId, "Disagree");
        tradeId = idNumber;
        idNumber ++;
    }

    /** set borrower status
     * @param userId borrower id
     * @param status Agree or Disagree
     */
    public void setBorrowerStatus(int userId, String status){
        userStatus.replace(userId, status);
    }


    /** set lender status
     * @param userId lender id
     * @param status Agree or Disagree
     */
    public void setLenderStatus(int userId, String status){
        userStatus.replace(userId, status);
    }
    /**
     * @return a list of ids(tradeId, borrowerId, lenderId, itemId)
     */
    public List<Integer> getIds(){
        List<Integer> list = new ArrayList<>();
        Collections.addAll(list, tradeId, borrowerId, lenderId, itemId);
        return list;
    }

    /**
     * Change the trade status to Open
     */
    public void openTrade(){
        this.tradeStatus = "Open";
    }

    /**
     * Change the trade status to Closed
     */
    public void closedTrade(){
        this.tradeStatus = "Closed";

    }

    /**
     * change the trade status to Cancelled
     */
    public void cancelTrade(){
        this.tradeStatus = "Cancelled";
    }

    /** Print the trade description
     * @return trade id + borrow id + lender id + item id + trade type + trade status.
     */
    public String toString(){
        return "trade id:" + tradeId + "" + "borrower id:" + borrowerId + "" + "lender id:" + lenderId  + ""
                + "item id:" + itemId + "\n" + "trade type: " + tradeType + "" + "trade status" + tradeStatus + "\n"
                + "borrower status:" + userStatus.get(borrowerId) + "" + "lender status" + userStatus.get(lenderId);
    }
}
