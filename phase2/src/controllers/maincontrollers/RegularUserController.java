package controllers.maincontrollers;

import controllers.regularusersubcontrollers.*;
import exception.InvalidIdException;
import gateway.FilesReaderWriter;
import managers.actionmanager.ActionManager;
import managers.feedbackmanager.FeedbackManager;
import managers.itemmanager.Item;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.SystemMessage;
import presenter.DisplaySystem;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter.
 *
 *  @author Yu Xin Yan
 *  @version IntelliJ IDEA 2020.1
 */

//TODO: can delete this class now
public class RegularUserController {

    private RegularUserAccountMenuController amc;
    private RegularUserTradingMenuController tmc;
    private RegularUserMeetingMenuController mmc;
    private RegularUserCommunityMenuController cmc;
    private RegularUserThresholdController tc;
    private SystemMessage sm;
    private DisplaySystem ds;
    private FilesReaderWriter frw;
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private ActionManager am;
    private FeedbackManager fm;
    private String username;
    private int userId;
    private boolean asGuest;

    /**
     * Constructs a RegularUserController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, an UserManager, an ItemManager, an ActionManager,
     * a FeedbackManager, the regular user's username and userId,
     * as well as the asGuest boolean attribute.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param am       The current state of the ActionManager.
     * @param fm       The current state of the FeedbackManager.
     * @param username The username of the regular user.
     * @param asGuest  The determiner of limited access to menu options.
     */
    public RegularUserController(DisplaySystem ds, TradeManager tm, MeetingManager mm, UserManager um,
                                 ItemManager im, ActionManager am, FeedbackManager fm,
                                 String username, boolean asGuest) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.am = am;
        this.fm = fm;
        this.username = username;
        this.userId = um.usernameToID(username);
        this.asGuest = asGuest;
        this.frw = new FilesReaderWriter();
        // for other controllers / presenters
        this.amc = new RegularUserAccountMenuController(ds, tm, mm, um, im, am, fm, username, userId);
        this.tmc = new RegularUserTradingMenuController(ds, tm, mm, um, im, am, username, userId);
        this.mmc = new RegularUserMeetingMenuController(ds, tm, mm, um, im, am, username, userId);
        this.cmc = new RegularUserCommunityMenuController(ds, tm, mm, um, im, am, fm, username, userId);
        this.tc = new RegularUserThresholdController(ds, tm, mm, um, username, userId);
        this.sm = new SystemMessage();
    }


    /**
     * Calls appropriate methods based on user input
     * of the menu option (other than the logout or exit option)
     * and calls on the relevant presenter class method.
     *
     * @param mainMenuOption The main menu option chosen by the regular user.
     * @param subMenuOption  The sub menu option for a particular sub menu chosen by the regular user.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     *
     */
    public void actionResponse(int mainMenuOption, int subMenuOption, String thresholdValuesFilePath) {
        //TODO: leave this as a template for now --> the exact structure will be applied again
        // to the RegUserMainMenuGUI
        switch (mainMenuOption) {
            case 1:
                if (subMenuOption <= 7) {
                    userAccountMenuResponse1(subMenuOption);
                } else if (7 < subMenuOption && subMenuOption <= 14) {
                    userAccountMenuResponse2(subMenuOption);
                }
                break;
            case 2:
                // if user is frozen
                if (um.getFrozenStatus(userId)){
                    sm.lockMessageForFrozen(ds);}
                // if user's on vacation
                else if (um.getInfo(userId, "Vacation") == 1){
                    sm.lockMessageForVacation(ds);
                }
                else{
                    userTradingMenuResponse(subMenuOption, thresholdValuesFilePath);
                }
                break;
            case 3:
                // if user is frozen
                if (um.getFrozenStatus(userId)){
                    sm.lockMessageForFrozen(ds);}
                // if user's on vacation
                else if (um.getInfo(userId, "Vacation") == 1){
                    sm.lockMessageForVacation(ds);
                }
                else{
                    userMeetingMenuResponse(subMenuOption, thresholdValuesFilePath);
                }
                break;
            case 4:
                //TODO:SearchingInfo response
                break;
            case 5:
                userCommunityMenuResponse(subMenuOption);
                break;
        }


    }

}
