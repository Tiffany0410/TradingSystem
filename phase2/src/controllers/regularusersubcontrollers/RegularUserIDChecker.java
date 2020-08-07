package controllers.regularusersubcontrollers;

import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An instance of this class represents the id
 * checker for the SubController classes for regular user.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserIDChecker {

    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;

    /**
     * Constructs a RegularUserIDGetter with a DisplaySystem, a TradeManager,
     * a MeetingManager, a UserManager, and an ItemManager.
     *
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     */
    public RegularUserIDChecker(TradeManager tm, MeetingManager mm,
                                UserManager um, ItemManager im) {
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
    }


    /**
     * Checks if user's input of the item id is valid.
     * @param itemId User's input of the item id.
     * @return If user's input is valid.
     */
    public boolean checkItemID(int itemId) {
        // all possible ids the user can pick from
        ArrayList<Integer> potentialIds;
        // depends on the option the user chooses
        //check if the item is in the system
        potentialIds = im.getItemsIDs(im.getAllItem());
        return potentialIds.contains(itemId);

    }

    public boolean checkItemID(ArrayList<Item> items, int id){
        ArrayList<Integer> ids = im.getItemsIDs(items);
        return ids.contains(id);
    }

    /**
     * Checks if user's input of the string can be converted to an int.
     * @param str The input from user.
     * @return If user's input can be converted to an int.
     */
    public boolean checkInt(String str){
        /* Based on code by Stephen C from:
         https://stackoverflow.com/questions/47686381/input-validation-for-gui
         */
        Pattern p = Pattern.compile("-?[0-9]+");
        Matcher m = p.matcher(str);
        return m.matches();

    }


    /**
     * Checks if user's input of the user id is a
     * valid one.
     * @param userId User's input of the user id.
     * @return If user's input of the user id is valid.
     */
    public boolean checkUserID(int userId){
        return um.checkUser(um.idToUsername(userId));
    }


    /**
     * Checks if user's input of the trade id is a
     * valid one.
     * @param tradeId User's input of the trade id.
     * @return If user's input of the trade id is valid.
     */
    public boolean checkTradeID(int tradeId) {
        return tm.checkInManager(tradeId);

    }
}
