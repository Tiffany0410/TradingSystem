package controllers.regularusersubcontrollers;

import gateway.FilesReaderWriter;
import managers.itemmanager.ItemManager;
import managers.meetingmanager.Meeting;
import managers.meetingmanager.MeetingManager;
import managers.trademanager.TradeManager;
import managers.usermanager.UserManager;
import presenter.DisplaySystem;
import presenter.SystemMessage;
import exception.InvalidIdException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An instance of this class represents the communication system between the regular user,
 * the use cases, and the presenter, for the meeting menu part.
 *
 * @author Yu Xin Yan
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserMeetingMenuController {

    private SystemMessage sm;
    private RegularUserIDGetter idGetter;
    private RegularUserDateTimeGetter dateTimeGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private ItemManager im;
    private String username;
    private int userId;
    private FilesReaderWriter frw;


    /**
     * Constructs a RegularUserMeetingMenuController with a DisplaySystem,
     * a TradeManager, a MeetingManager, a UserManager, an ItemManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param im       The current state of the ItemManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserMeetingMenuController(DisplaySystem ds, TradeManager tm, MeetingManager mm, UserManager um,
                                            ItemManager im, String username, int userId) throws IOException, ClassNotFoundException {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.im = im;
        this.username = username;
        this.userId = userId;
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, im, username, userId);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.dateTimeGetter = new RegularUserDateTimeGetter();
        this.sm = new SystemMessage();
        this.frw = new FilesReaderWriter();
    }

    /**
     * Let the presenter print the list of meetings if there are any,
     * otherwise, print to let user know that there aren't any.
     * @param meetings The list of meetings
     * @param type The type of the meeting.
     */
    public void seeMeetings(List<Meeting> meetings, String type) {
        if (meetings.size() == 0) {
            sm.msgForNothing(type, ds);
        } else {
            ds.printResult(new ArrayList<>(meetings));
        }
    }

    /**
     * If there're meetings that need to be confirmed,
     * get user's input of the meeting information and let the user
     * confirm the meeting. Else, print to let user know that there
     * aren't any.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void confirmMeetingTookPlace(String thresholdValuesFilePath) throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        if (mm.getUnConfirmMeeting(userId).size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            // "confirmed" means the meeting haven't take place but time and place are confirmed
            List<Meeting> listOfUnconfirmedMeeting = mm.getUnConfirmMeeting(userId);
            // get the list of meetings whose completion are not confirmed
            ds.printOut("Here's a list of meeting(s) that haven't confirmed as complete:");
            ds.printResult(new ArrayList<>(listOfUnconfirmedMeeting));
            // get the meeting
            Meeting meeting3 = getMeeting();
            // if the meeting exists
//          TODO: need a mm method to check if a meeting's trade id is 0 or not
//          TODO: replace getTradeId()
            if (meeting3.getTradeId() != 0) {
                ds.printResult(mm.setMeetingConfirm(tm, meeting3, userId, thresholdValues.get(3)));
            } else {
                // if the meeting DNE
                sm.msgForMeetingDNE(ds);
            }
            }
    }


    /**
     * If there're meetings that need to be confirmed for time and place
     * get user's input of the meeting information and let the user
     * confirm the meeting. Else, print to let user know that there
     * aren't any.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case if the file cannot be found.
     */
    public void confirmMeetingTandP(String thresholdValuesFilePath) throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        if (mm.getUnConfirmTimePlace(userId, tm).size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            // print the meetings with unconfirmed time and place
            unconfirmedTandPMeetings();
            // and then ask the user for the meeting info
            Meeting meeting2 = getMeeting();
            // if the meeting exists in the system
            if (meeting2.getTradeId() != 0) {
//          TODO: need a mm method to set time place confirm for a meeting
//          TODO: replace setTimePlaceConfirm()
                Boolean confirmSuccess = meeting2.setTimePlaceConfirm(userId, thresholdValues.get(3));
                ds.printResult(confirmSuccess);
                if(!confirmSuccess){
                    ds.printOut("It's not your turn, or you haven't suggested the time and place." + "\n");
                }
                // if the meeting DNE in the system
            } else {
                sm.msgForMeetingDNE(ds);
            }
        }
    }

    /**
     * If there're any meetings that need to be edited for time
     * and place, let the user input the input of the meeting information
     * and let the user edit the time and place. Else, print to
     * let the user know that there aren't any.
     * @param thresholdValuesFilePath The filepath of the file that stores all the threshold values in the system.
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void EditMeetingTandP(String thresholdValuesFilePath) throws InvalidIdException, FileNotFoundException {
        // get the threshold values read from the csv file
        List<Integer> thresholdValues = frw.readThresholdValuesFromCSVFile(thresholdValuesFilePath);
        // get the threshold value needed for this method
        int maxMeetingTimePlaceEdits = thresholdValues.get(3);
        // if there're no meetings that need to be confirmed for time and place
        if (mm.getUnConfirmTimePlace(userId, tm).size() == 0) {
            sm.msgForNothing("here that requires action", ds);
        } else {
            // print the meetings with unconfirmed time and place
            unconfirmedTandPMeetings();
            // get the meeting info from the user
            Meeting meeting = getMeeting();
            // if the meeting exists and the threshold is not reached yet
            if (meeting.getTradeId() != 0 && mm.getEditOverThreshold(tm, meeting, thresholdValues.get(3)).equals("")) {
                //asks the user for the date and place
                List<Integer> list = this.dateTimeGetter.getValidDate(ds);
                String place = otherInfoGetter.getPlace();
                //call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
//          TODO: need a mm method to set time place edit for a meeting
//          TODO: replace setTimePlaceEdit()
                boolean editSuccess= meeting.setTimePlaceEdit(userId, list.get(0), list.get(1), list.get(2),
                        list.get(3), list.get(4), 0, place, maxMeetingTimePlaceEdits);
                ds.printResult(editSuccess);
                // if the user did not edit it successfully
                if (!editSuccess){
                    ds.printOut("It's not your turn.");
                }
                // record that it's confirmed by this user
                meeting.setTimePlaceConfirm(userId, maxMeetingTimePlaceEdits);
                // for the edit threshold
                ds.printOut(mm.getEditOverThreshold(tm, meeting, maxMeetingTimePlaceEdits));
            } else {
                // print a helpful messages to let user know why failed
                // if the meeting DNE
                if (meeting.getTradeId() == 0) {
                    sm.msgForMeetingDNE(ds);
                }
                // if the threshold is reached
                else {
                    ds.printOut("You reached the threshold to edit." + "\n");
                    ds.printOut("And/or, the trade that goes with this meeting is cancelled" + "\n");
                }

            }
        }
    }

    /**
     * Let the presenter print the list of unconfirmed meetings
     * with unconfirmed time and place, if there are any.
     * Else, print to let the user know that there aren't any.
     * @throws InvalidIdException In case if the id is not valid.
     */
    public void unconfirmedTandPMeetings() throws InvalidIdException {
        //get the list of meetings with unconfirmed time and place from the meeting manager
        List<Meeting> listOfUnconfirmedTimePlace = mm.getUnConfirmTimePlace(userId, tm);
        // if there're meeting with unconfirmed time and place
        ds.printOut("Here's a list of meeting(s) with unconfirmed time and place:");
        ds.printResult(new ArrayList<>(listOfUnconfirmedTimePlace));
    }

    /**
     * Asks the user for the meeting information and finds that meeting.
     * @return The meeting instance that match user's input of the meeting information.
     * @throws InvalidIdException In case if the id is not valid.
     */
    private Meeting getMeeting() throws InvalidIdException {
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = idGetter.getTradeID();
        int numMeeting = otherInfoGetter.getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }
}
