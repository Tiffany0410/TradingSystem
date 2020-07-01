package bookTradeSystem;
import java.util.*;
import java.io.Serializable;
public class TradeManager implements Serializable {
    private List<Trade> listTrade;
    private String filePath;

    public TradeManager(String filePath) {
        this.filePath = filePath;
        listTrade = FilesReaderWriter.readTradesFromFile(filePath);
    }

    /**
     * @return the list of trade
     */
    public List<Trade> getListTrade() {
        return listTrade;
    }

    /**
     * Add a trade in list trade
     *
     * @param t trade
     */
    public void addTrade(Trade t) {
        listTrade.add(t);
        FilesReaderWriter.saveTradesToFile(listTrade, filePath);
    }

    /**
     * Check a user history
     *
     * @param userId the user's id we want to check
     * @return a list of that user's trade history
     */
    public List<Trade> getTradeHistory(int userId) throws InvalidIdException {
        List<Trade> list = new ArrayList<>();
        for (Trade t : listTrade) {
            if (t.getIds().get(1) == userId || t.getIds().get(2) == userId) {
                list.add(t);
            }
            else{
                throw new InvalidIdException("Invalid Id");
            }
        }
        return list;
    }

    /**
     * Filter the user history by which his trade status is Closed
     *
     * @param userId user id
     * @return a list of trades which status are closed
     */
    public List<Trade> filterHistory(int userId) throws InvalidIdException {
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for (Trade t : list) {
            if (t.tradeStatus.equals("Closed")) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * returen list of recent three trade item ids
     *
     * @param userId user id
     * @return a list of recent three item ids
     */
    public List<Integer> recentThreeItem(int userId) throws InvalidIdException {
        List<Trade> list = this.filterHistory(userId);
        List<Integer> list1 = new ArrayList<>();
        list1.add(list.get(list.size() - 1).getIds().get(3));
        list1.add(list.get(list.size() - 2).getIds().get(3));
        list1.add(list.get(list.size() - 3).getIds().get(3));
        return list1;
    }

    /** return a list of top three partners id
     * @param userId user id
     * @return list of top three partners id
     * @throws InvalidIdException
     */
    public List<Integer> topThreePartners(int userId) throws InvalidIdException {
        Map<Integer, Integer> numTrade = new HashMap<>();
        List<Trade> list = this.getTradeHistory(userId);
        for (Trade t : list) {
            if (numTrade.containsKey(t.getIds().get(2))) {
                numTrade.put(t.getIds().get(2), numTrade.get(t.getIds().get(2)) + 1);
            } else {
                numTrade.put(t.getIds().get(2), 1);
            }
        }
        List<Integer> list1 = new ArrayList<>(numTrade.values());
        Collections.sort(list1);
        List<Integer> list2 = new ArrayList<>();
        list2.add(list1.get(list.size() - 1));
        list2.add(list1.get(list.size() - 2));
        list2.add(list1.get(list.size() - 3));
        return list2;
    }

    /**
     * Get a list of  user's Trades which its status is open
     *
     * @param userId the user's id we want to check
     * @return the list of  user's Trades which its status is open
     */
    public List<Trade> getOpenTrade(int userId) throws InvalidIdException {
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for (Trade t : list) {
            if (t.tradeStatus.equals("Open")) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * Get list of  user's Trades which its status is Closed
     *
     * @param userId the user's id we want to check
     * @return the list of  user's Trades which its status is closed
     */
    public List<Trade> getClosedTrade(int userId) throws InvalidIdException {
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for (Trade t : list) {
            if (t.tradeStatus.equals("Closed")) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * Get list of user's cancelled trade
     *
     * @param userId the user we want to look for
     * @return the list of cancel trade by user.
     */
    public List<Trade> getCancelledTrade(int userId) throws InvalidIdException {
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for (Trade t : list) {
            if (t.tradeStatus.equals("Cancelled")) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * Get list of trade which its status is wait to be opened
     *
     * @param userId the user's id we want to check
     * @return the list of trade which its status is wait to be opened
     */
    public List<Trade> getWaitTrade(int userId) throws InvalidIdException {
        List<Trade> list = this.getTradeHistory(userId);
        List<Trade> list1 = new ArrayList<>();
        for (Trade t : list) {
            if (t.tradeStatus.equals("Wait to be opened")) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * check if the trade with id in the TradeManager or not
     *
     * @param tradeId the ide of the trade
     * @return true if the trade with tradeId in the TradeManager
     */
    public Boolean checkInManager(int tradeId) {
        for (Trade trade : listTrade) {
            if (trade.getIds().get(0) == tradeId) {
                return true;
            }
        }
        return false;
    }

    /**
     * get the trade by the given trade id
     *
     * @param tradeId the id of the trade
     * @return a trade with the given id if the trade is in the TradeManager, otherwise, return a trade with borrowerId
     * and lenderId and itemId with 0, and tradeType with "".
     */
    public Trade getTradeById(int tradeId) {
        Trade trade1 = listTrade.get(0);
        for (Trade trade : listTrade) {
            if (trade.getIds().get(0) == tradeId) {
                return trade;
            }
        }
        return new Trade(0, 0, 0, "");
    }


    /**
     * Create a trade
     *
     * @param borrowerId borrower id
     * @param lenderId   lender id
     * @param itemId     item id
     * @param tradeType  type of the trade
     * @return a Trade
     */
    public Trade createTrade(int borrowerId, int lenderId, int itemId, String tradeType) {
        return new Trade(borrowerId, lenderId, itemId, tradeType);
    }

    /**
     * remove the trade from the list of trade
     *
     * @param tradeId trade id we want to remove
     * @return the trade we remove
     */
    public Trade removeTrade(int tradeId) throws InvalidIdException {
        for (Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                listTrade.remove(t);
                return t;
            }
        }
        throw new InvalidIdException("Invalid Id");
    }


    /**
     * Check if the trade is confirmed(status is closed or cancelled)
     *
     * @param tradeId the id of the trade
     * @return true if is, otherwise false
     */
    public boolean confirmComplete(int tradeId) throws InvalidIdException {
        for (Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                return t.tradeStatus.equals("Closed") || t.tradeStatus.equals("Cancelled");
            }else{
                throw new InvalidIdException("Invalid Id");
            }
        }
        return false;
    }
}
