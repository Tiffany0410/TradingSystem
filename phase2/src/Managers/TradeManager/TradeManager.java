package Managers.TradeManager;

import Managers.UserManager.User;
import Exception.InvalidIdException;

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

    private List<Managers.TradeManager.Trade> listTrade;

    /** Constructor with no parameter
     * store a new list for listTrade
     */
    public TradeManager() {
        listTrade = new ArrayList<>();
    }

    /**
     * @return the list of trade
     */
    public List<Managers.TradeManager.Trade> getListTrade() {
        return listTrade;
    }

    /**
     * Add a trade in list trade
     *
     * @param t trade
     */
    public void addTrade(Managers.TradeManager.Trade t) {
        listTrade.add(t);
    }

    /**
     * Check a user history
     *
     * @param userId the user's id we want to check
     * @return a list of that user's trade history
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Managers.TradeManager.Trade> getTradeHistory(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = new ArrayList<>();
        for (Managers.TradeManager.Trade t : listTrade) {
            if (t.getIds().get(1) == userId || t.getIds().get(2) == userId) {
                list.add(t);
            } else {
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
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Managers.TradeManager.Trade> filterHistory(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        List<Managers.TradeManager.Trade> list1 = new ArrayList<>();
        for (Managers.TradeManager.Trade t : list) {
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
     * @return a list of recent three item ids (Latest at index 0)
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Integer> recentThreeItem(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.filterHistory(userId);
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
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        for (Managers.TradeManager.Trade t : list) {
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
    public List<Managers.TradeManager.Trade> getOpenTrade(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        List<Managers.TradeManager.Trade> list1 = new ArrayList<>();
        for (Managers.TradeManager.Trade t : list) {
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
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Managers.TradeManager.Trade> getClosedTrade(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        List<Managers.TradeManager.Trade> list1 = new ArrayList<>();
        for (Managers.TradeManager.Trade t : list) {
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
     * @return the list of cancel trade by user, throw InvalidIdException if invalid id
     */
    public List<Managers.TradeManager.Trade> getCancelledTrade(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        List<Managers.TradeManager.Trade> list1 = new ArrayList<>();
        for (Managers.TradeManager.Trade t : list) {
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
     * @throws InvalidIdException In case the id is invalid.
     */
    public List<Managers.TradeManager.Trade> getWaitTrade(int userId) throws InvalidIdException {
        List<Managers.TradeManager.Trade> list = this.getTradeHistory(userId);
        List<Managers.TradeManager.Trade> list1 = new ArrayList<>();
        for (Managers.TradeManager.Trade t : list) {
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
        for (Managers.TradeManager.Trade trade : listTrade) {
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
    public Managers.TradeManager.Trade getTradeById(int tradeId) throws InvalidIdException {
        Managers.TradeManager.Trade trade1 = listTrade.get(0);
        for (Managers.TradeManager.Trade trade : listTrade) {
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
    public Managers.TradeManager.Trade removeTrade(int tradeId) throws InvalidIdException {
        for (Managers.TradeManager.Trade t : listTrade) {
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
        for (Managers.TradeManager.Trade t : listTrade) {
            if (t.getIds().get(0) == tradeId) {
                return t.tradeStatus.equals("Closed");
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
    public boolean validateTrade(Trade trade, User borrower, int numLendBeforeBorrow) {
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

