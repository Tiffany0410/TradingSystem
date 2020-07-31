package controllers.maincontrollers;

import exception.InvalidIdException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * Classes that implement this interface
 * appropriately react to user input.
 *
 * @author Yu Xin, Yan
 * @version IntelliJ IDEA 2020.1
 */

public interface Controllable {

    /**
     * Calls appropriate methods based on user input
     * of the option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     * @param mainMenuOption  The main menu option chosen by the one who logged into the program.
     * @param subMenuOption   The sub menu option for a particular sub menu chosen by the one
     *                        who logged into the program.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @throws FileNotFoundException In case the file can't be found.
     * @throws InvalidIdException In case the id is invalid.
     */
    void actionResponse(int mainMenuOption, int subMenuOption, String thresholdValuesFilePath)
            throws IOException, InvalidIdException, ParseException;


}
