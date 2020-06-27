package bookTradeSystem;

public interface Controllable {

    /**
     * This method calls appropriate methods based on user input
     * and calls on relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the user or admin user.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the user or admin user.
     */
    void actionResponse(int mainMenuOption, int subMenuOption);
    void alerts();

}