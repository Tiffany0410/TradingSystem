package bookTradeSystem;

public interface Controllable {

    /**
     * This method calls appropriate methods based on user input
     * and calls on relevant presenter class method.
     * @param menuOption The menu option chosen by the user.
     */
    void actionResponse(int menuOption);
    void alerts();
    // ask Lindsey about this
    void serializeObjects();

}