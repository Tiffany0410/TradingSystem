package bookTradeSystem;

public interface Controllable {

    /**
     * This method calls appropriate methods based on user input
     * of the option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the one who logged into the program.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the one
     *                      who logged into the program.
     */
    void actionResponse(int mainMenuOption, int subMenuOption);

    /**
     * This method gathers all the necessary notifications for the user.
     * @return Notifications as properly formatted strings.
     */
    String alerts();

}