package presenter;

/**
 * An instance of this class represents the
 * communication bridge from the system
 * to the user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
class TradeSystemMessage {


    /**
     * Constructs TradeSystemMessage.
     */
    TradeSystemMessage(){

    }

    /**
     * Puts together a message that indicates reasons why there is no trade suggestion.
     * @return A message that indicates reasons why there is no trade suggestion.
     */
    protected String msgForNoTradeSuggestion(){
        return "There is no recommended trade suggestion." +
                " It might be because of 1) your wishlist is empty or 2) the items" +
                " in your wishlist are not tradable and the items in the same category " +
                "as the items in your wishlist are also not tradable.";
    }

    /**
     * Puts together a message indicating whether the trade is complete or not.
     * @param result Whether or not the trade is completed.
     * @return A message indicating whether the trade is complete or not.
     */
    protected String msgFortradeCompletedOrNot(boolean result){
        if (result){
            return "This trade is completed.";
        }
        else{
            return "This trade is Incomplete.";
        }
    }

    /**
     * Puts together a message explaining why the trade request fails.
     * @return A message explaining why the trade request fails.
     */
    protected String msgTradeRequestFail(){
        return "Trade request failed, please check the following conditions:\n\nFor one-way-trade:\n" +
                "1. The item is tradable. \n2. You have added the item to your wishlist.\n3. You have completed a two-way-trade before.\n\n" +
                "For two-way-trade:\n" + "1. The items are tradable.\n2. Both users have added the items to " +
                "their wishlist.\n3. The number of borrow did not exceed the number of lend.";
    }

}
