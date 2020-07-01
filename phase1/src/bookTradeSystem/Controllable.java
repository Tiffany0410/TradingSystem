package bookTradeSystem;

import java.io.FileNotFoundException;

public interface Controllable {

    /**
     * This method calls appropriate methods based on user input
     * of the option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption The main menu option chosen by the one who logged into the program.
     * @param subMenuOption The sub menu option for a particular sub menu chosen by the one
     *                      who logged into the program.
     * @throws FileNotFoundException In case the file can't be found.
     */
    void actionResponse(int mainMenuOption, int subMenuOption) throws FileNotFoundException;

    /**
     * This method gathers all the necessary notifications for the user.
     * @return Notifications as properly formatted strings.
     * @throws FileNotFoundException In case the file can't be found.
     */
    String alerts() throws FileNotFoundException;

}