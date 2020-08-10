package controllers.regularusersubcontrollers;
import managers.itemmanager.Category;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An instance of this class represents the other information
 * checker for the regular user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */

public class RegularUserOtherInfoChecker {


    /**
     * Constructs a RegularUserOtherInfoGetter.
     */
    public RegularUserOtherInfoChecker() {
    }


    /**
     * Checks user's input of the item category
     * @param userInputCategory User's input of the item category.
     * @return If it's a valid category or not.
     */
    public boolean checkItemType(String userInputCategory) {
        ArrayList<String> categories = new ArrayList<>();
        for (Category category : Category.values()) {
            categories.add(category.name());
        }
        return categories.contains(userInputCategory);
    }

    /**
     * Checks if user's input of the string is a valid email format.
     * @param email The input from user
     * @return if the user's input is a valid email
     */
    public boolean checkEmail(String email){
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
