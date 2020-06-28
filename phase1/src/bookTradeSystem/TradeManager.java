package bookTradeSystem;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;
public class TradeManager implements Serializable {
    private List<Trade> listTrade;

    /** Constructor with a list of trade
     * @param listTrade list of Trade
     */
    public TradeManager(List<Trade> listTrade){
        this.listTrade = listTrade;
    }

    /**
     * @return the list of trade
     */
    public List<Trade> getListTrade(){
        return listTrade;
    }

    /** Set the old listTrade to new listTrade
     * @param listTrade a list of trade
     */
    public void setListTrade(List<Trade> listTrade){
        this.listTrade = listTrade;
    }

    /** Check a user history
     * @param userId the user's id we want to check
     * @return a list of that user's trade history
     */
    public List<Trade> getTradeHistory(int userId){
        List<Trade> list = new ArrayList<>();
        for (Trade t: listTrade){
            if(t.getIds().get(1) == userId || t.getIds().get(2) == userId){
                list.add(t);
            }
        }
        return list;
    }

    /** Get a list of  user's Trades which its status is open
     * @param userId the user's id we want to check
     * @return the list of  user's Trades which its status is open
     */
    public List<Trade> getOpenTrade(int userId){
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for(Trade t: list){
            if (t.tradeStatus.equals("Open")){
                list1.add(t);
            }
        }
        return list1;
    }

    /** Get list of  user's Trades which its status is Closed
     * @param userId the user's id we want to check
     * @return the list of  user's Trades which its status is closed
     */
    public List<Trade> getClosedTrade(int userId){
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for(Trade t: list){
            if (t.tradeStatus.equals("Closed")){
                list1.add(t);
            }
        }
        return list1;
    }

    /** Get list of user's cancelled trade
     * @param userId the user we want to look for
     * @return the list of cancel trade by user.
     */
    public List<Trade> getCancelledTrade(int userId){
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for(Trade t: list){
            if (t.tradeStatus.equals("Cancelled")){
                list1.add(t);
            }
        }
        return list1;
    }

    /** Get list of trade which its status is wait to be opened
     * @param userId the user's id we want to check
     * @return the list of trade which its status is wait to be opened
     */
    public List<Trade> getWaitTrade(int userId){
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for(Trade t: list){
            if (t.tradeStatus.equals("Wait to be opened")){
                list1.add(t);
            }
        }
        return list1;
    }

    /** check if the trade with id in the TradeManager or not
     * @param tradeId the ide of the trade
     * @return true if the trade with tradeId in the TradeManager
     */
    public Boolean checkInManager(int tradeId){
        for(Trade trade:listTrade){
            if(trade.getIds().get(0) == tradeId){
                return true;
    }}return false;}

    /** get the trade by the given trade id
     * @param tradeId the id of the trade
     * @return a trade with the given id if the trade is in the TradeManager, otherwise, return a trade with borrowerId
     * and lenderId and itemId with 0, and tradeType with "".
     */
    public Trade getTradeById(int tradeId){
        Trade trade1 = listTrade.get(0);
        for(Trade trade:listTrade){
            if(trade.getIds().get(0) == tradeId){
                return trade;
    }}return new Trade(0,0,0,"");}


    /** Create a trade
     * @param borrowerId borrower id
     * @param lenderId lender id
     * @param itemId item id
     * @param tradeType type of the trade
     * @return a Trade
     */
    public Trade createTrade(int borrowerId, int lenderId, int itemId, String tradeType){
        return new Trade(borrowerId, lenderId, itemId, tradeType);
    }

    /** remove the trade from the list of trade
     * @param tradeId trade id we want to remove
     * @return the trade we remove
     */
    public Trade removeTrade(int tradeId) {
        for (Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                listTrade.remove(t);
                return t;
            }
        }
        return null;
    }


    /** Check if the trade is confirmed
     * @param tradeId the id of the trade
     * @return true if is, otherwise false
     */
    public boolean confirmComplete(int tradeId){
        for (Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId){
                if(t.tradeStatus.equals("Closed")){
                    return true;
                }
            }
        }
        return false;
    }
}
