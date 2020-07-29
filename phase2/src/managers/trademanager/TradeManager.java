package managers.trademanager;

import managers.usermanager.TradableUser;
import exception.InvalidIdException;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A TradeManager
 * @author Yuanze Bao
 * @version IntelliJ IDEA 2020.1
 */
public class TradeManager implements Serializable {
    private String OPEN = "Opened";
    private String WAIT = "Wait to be opened";
    private String CANCEL = "Cancelled";
    private String CLOSE = "Closed";

    private List<managers.trademanager.Trade> listTrade;

    /** Constructor with no parameter
     * store a new list for listTrade
     */
    public TradeManager() {
        listTrade = new ArrayList<>();
    }

    /**
     * @return the list of trade
     */
    public List<managers.trademanager.Trade> getListTrade() {
        return listTrade;
    }

    /**
     * Add a trade in list trade
     *
     * @param t trade
     */
    public void addTrade(managers.trademanager.Trade t) {
        listTrade.add(t);
    }

    /**
     * Check a user history
     *
     * @param userId the user's id we want to check
     * @return a list of that user's trade history
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<managers.trademanager.Trade> getTradeHistory(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = new ArrayList<>();
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(1) == userId || t.getIds().get(2) == userId) {
                list.add(t);
            } else {
                throw new InvalidIdException("Invalid Id");
            }
        }
        return list;
    }

    /** Ccreate a trade
     * @param userId1 user1 id
     * @param userId2 user2 id
     * @param itemId item id
     * @param tradeType trade type
     * @param isOneWayTrade is one way trade
     * @param tradeID trade id
     * @return Trade
     */
    public managers.trademanager.Trade createTrade(int userId1, int userId2, int itemId, String tradeType, boolean isOneWayTrade, int tradeID){
        return new Trade(userId1, userId2, itemId, tradeType, isOneWayTrade, tradeID);
    }

    /** Ccreate a trade
     * @param userId1 user1 id
     * @param userId2 user2 id
     * @param itemId item1 id
     * @param itemId1 item 2 id
     * @param tradeType trade type
     * @param isOneWayTrade is one way trade
     * @param tradeID trade id
     * @return Trade
     */
    public managers.trademanager.Trade createTrade(int userId1, int userId2, int itemId, int itemId1, String tradeType,
                                                   boolean isOneWayTrade, int tradeID){
        return new Trade(userId1, userId2, itemId, itemId1, tradeType, isOneWayTrade, tradeID);
    }


    /**
     * Filter the user history by which his trade status is Closed
     *
     * @param userId user id
     * @return a list of trades which status are closed
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<managers.trademanager.Trade> filterHistory(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        List<managers.trademanager.Trade> list1 = new ArrayList<>();
        for (managers.trademanager.Trade t : list) {
            if (t.tradeStatus.equals(CLOSE)) {
                list1.add(t);
            }
        }
        return list1;
    }
    /**
     * returen list of recent three trade item ids
     *
     * @param userId user id
     * @return a list of recent three item ids (Latest at index 0)
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Integer> recentThreeItem(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.filterHistory(userId);
        List<Integer> list1 = new ArrayList<>();
        if (list.size() >= 3) {
            if (list.get(list.size() - 1).getIsOneWayTrade()) {
                list1.add(list.get(list.size() - 1).getIds().get(3));
            } else {
                list1.add(list.get(list.size() - 1).getIds().get(3));
                list1.add(list.get(list.size() - 1).getIds().get(4));
            }
            if (list.get(list.size() - 2).getIsOneWayTrade() && list1.size() == 1) {
                list1.add(list.get(list.size() - 2).getIds().get(3));
            } else if (!(list.get(list.size() - 2).getIsOneWayTrade()) && list1.size() == 1) {
                list1.add(list.get(list.size() - 2).getIds().get(3));
                list1.add(list.get(list.size() - 2).getIds().get(4));
            }
            if (list.size() != 3) {
                list1.add(list.get(list.size() - 3).getIds().get(3));
            }

        } else if (list.size() == 2) {
            if (list.get(list.size() - 1).getIsOneWayTrade()) {
                list1.add(list.get(list.size() - 1).getIds().get(3));
            } else {
                list1.add(list.get(list.size() - 1).getIds().get(3));
                list1.add(list.get(list.size() - 1).getIds().get(4));
            }
            if (list1.size() == 2 ||list.get(list.size() - 2).getIsOneWayTrade()){
                list1.add(list.get(list.size() - 2).getIds().get(3));
            } else if (list1.size()!=2 && !(list.get(list.size() - 2).getIsOneWayTrade())){
                list1.add(list.get(list.size() - 2).getIds().get(3));
                list1.add(list.get(list.size() - 2).getIds().get(4));
            }
        }
        if (list.size() == 1) {
            if (list.get(list.size() - 1).getIsOneWayTrade()) {
                list1.add(list.get(list.size() - 1).getIds().get(3));
            } else {
                list1.add(list.get(list.size() - 1).getIds().get(3));
                list1.add(list.get(list.size() - 1).getIds().get(4));
            }
        }
        return list1;
    }

    /**
     * return a list of top three partners id（finding the way to solve by comparing values
     * in the following website
     * @link https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
     * @param userId user id
     * @return list of top three partners id (Most is at index 0 and least at last index)
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Integer> topThreePartners(int userId) throws InvalidIdException {
        Map<Integer, Integer> numTrade = new HashMap<>();
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        for (managers.trademanager.Trade t : list) {
            if (numTrade.containsKey(t.getIds().get(2))) {
                numTrade.put(t.getIds().get(2), numTrade.get(t.getIds().get(2)) + 1);
            } else {
                numTrade.put(t.getIds().get(2), 1);
            }
        }
        Stream<Map.Entry<Integer,Integer>> sorted =
                numTrade.entrySet().stream()
                        .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
        Map<Integer,Integer> top3 =
                numTrade.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(3)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        return new ArrayList<Integer>(top3.keySet());
        // finding the way to solve by comparing values
        // in this website: https://stackoverflow.com/questions/109383/sort-a-mapkey-value-by-values
    }

    /**
     * Get a list of  user's Trades which its status is open
     *
     * @param userId the user's id we want to check
     * @return the list of  user's Trades which its status is open
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<managers.trademanager.Trade> getOpenTrade(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        List<managers.trademanager.Trade> list1 = new ArrayList<>();
        for (managers.trademanager.Trade t : list) {
            if (t.tradeStatus.equals(OPEN)) {
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
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<managers.trademanager.Trade> getClosedTrade(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        List<managers.trademanager.Trade> list1 = new ArrayList<>();
        for (managers.trademanager.Trade t : list) {
            if (t.tradeStatus.equals(CLOSE)) {
                list1.add(t);
            }
        }
        return list1;
    }

    /**
     * Get list of user's cancelled trade
     *
     * @param userId the user we want to look for
     * @return the list of cancel trade by user, throw InvalidIdException if invalid id
     */
    public List<managers.trademanager.Trade> getCancelledTrade(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        List<managers.trademanager.Trade> list1 = new ArrayList<>();
        for (managers.trademanager.Trade t : list) {
            if (t.tradeStatus.equals(CANCEL)) {
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
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<managers.trademanager.Trade> getWaitTrade(int userId) throws InvalidIdException {
        List<managers.trademanager.Trade> list = this.getTradeHistory(userId);
        List<managers.trademanager.Trade> list1 = new ArrayList<>();
        for (managers.trademanager.Trade t : list) {
            if (t.tradeStatus.equals(WAIT)) {
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
        for (managers.trademanager.Trade trade : listTrade) {
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
     * @return a trade with the given id if the trade is in the TradeManager, otherwise, throw InvalidIdException
     */
    public managers.trademanager.Trade getTradeById(int tradeId) throws InvalidIdException {
        managers.trademanager.Trade trade1 = listTrade.get(0);
        for (managers.trademanager.Trade trade : listTrade) {
            if (trade.getIds().get(0) == tradeId) {
                return trade;
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /**
     * remove the trade from the list of trade
     *
     * @param tradeId trade id we want to remove
     * @return the trade we remove
     */
    public managers.trademanager.Trade removeTrade(int tradeId) throws InvalidIdException {
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                listTrade.remove(t);
                return t;
            }
        }
        throw new InvalidIdException("Invalid Id");
    }


    /**
     * Check if the trade is confirmed(status is closed)
     *
     * @param tradeId the id of the trade
     * @return true if is, otherwise false
     */
    public boolean confirmComplete(int tradeId) throws InvalidIdException {
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                return t.tradeStatus.equals(CLOSE);
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** check if a trade is one way trade
     * @param tradeId trade id
     * @return true if it is one way trade, other wise false
     * @throws InvalidIdException
     */
    public boolean checkOneWayTrade(int tradeId) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                return t.getIsOneWayTrade();
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** Change a trade status to cancel
     * @param tradeId trade id
     * @throws InvalidIdException
     */
    public void cancelTrade(int tradeId) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                t.cancelTrade();
            }
        }
        throw new InvalidIdException("Invalid Id");
    }


    /** change a trade status to close
     * @param tradeId trade id
     * @throws InvalidIdException
     */
    public void closeTrade(int tradeId, managers.itemmanager.ItemManager im) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                ArrayList<Integer> lst = new ArrayList<Integer>(t.getIds().get(3));
                if (!t.getIsOneWayTrade()) {
                    lst.add(t.getIds().get(4));
                }
                im.setTradable(lst,true);
                t.closedTrade();
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** Change a trade status to open
     * @param tradeId trade id
     * @throws InvalidIdException
     */
    public void openTrade(int tradeId, managers.itemmanager.ItemManager im) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                ArrayList<Integer> lst = new ArrayList<Integer>(t.getIds().get(3));
                if (!t.getIsOneWayTrade()) {
                    lst.add(t.getIds().get(4));
                }
                im.setTradable(lst,false);
                t.openTrade();
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** return id we want to find
     * @param tradeId trade id
     * @param index index of id
     * @return id
     * @throws InvalidIdException
     */
    public int getId(int tradeId, int index) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                return t.getIds().get(index);
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** set user status
     * @param tradeId trade id
     * @param userId user id
     * @param status status
     * @throws InvalidIdException
     */
    public void setUserStatus(int tradeId, int userId, String status) throws InvalidIdException{
        for (managers.trademanager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                t.setUserStatus(userId, status);
            }
        }
        throw new InvalidIdException("Invalid Id");
    }

    /** Validate trade
     * @param trade trade we want to validate
     * @param borrower User borrower
     * @param numLendBeforeBorrow number of lend before borrow
     * @return true if borrower numlent = num lendBeforeBorrow and borrower numlent >= borrower numborrowed, false if it
     * is two way trade and otherwise false.
     */
    public boolean validateTrade(Trade trade, TradableUser borrower, int numLendBeforeBorrow) {
        if (!(trade.getIsOneWayTrade())) {
            //if two way trade
            return true;
        }
        //if one way trade
        if (borrower.getNumBorrowed() == 0 && borrower.getNumLent() == 0) {
            return false;
        } else {
            return borrower.getNumLent() >= numLendBeforeBorrow && borrower.getNumLent() >=
                    borrower.getNumBorrowed();
        }
    }
}

