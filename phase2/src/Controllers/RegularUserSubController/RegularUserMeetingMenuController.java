package Controllers.RegularUserSubController;

import Gateway.FilesReaderWriter;
import Managers.MeetingManager.Meeting;
import Managers.MeetingManager.MeetingManager;
import Managers.TradeManager.TradeManager;
import Managers.UserManager.UserManager;
import Presenter.DisplaySystem;
import Presenter.SystemMessage;
import Exception.InvalidIdException;

import java.io.FileNotFoundException;
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
    private Controllers.RegularUserSubController.RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private Managers.TradeManager.TradeManager tm;
    private Managers.MeetingManager.MeetingManager mm;
    private Managers.UserManager.UserManager um;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserMeetingMenuController with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     * @param userId   The userid of the regular user.
     */
    public RegularUserMeetingMenuController(DisplaySystem ds,
                                            TradeManager tm, MeetingManager mm,
                                            UserManager um, String username, int userId) {
        this.ds = ds;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = userId;
        this.idGetter = new RegularUserIDGetter(ds, tm, mm, um, username, userId);
        this.otherInfoGetter = new Controllers.RegularUserSubController.RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.dateTimeGetter = new RegularUserDateTimeGetter();
        this.sm = new SystemMessage();
    }

    /**
     * Let the presenter print the list of meetings if there are any,
     * otherwise, print to let user know that there aren't any.
     * @param meetings The list of meetings
     * @param type The type of the meeting.
     */
    public void seeMeetings(List<Managers.MeetingManager.Meeting> meetings, String type) {
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
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void confirmMeetingTookPlace() throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (mm.getUnConfirmMeeting(userId).size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            // "confirmed" means the meeting haven't take place but time and place are confirmed
            List<Managers.MeetingManager.Meeting> listOfUnconfirmedMeeting = mm.getUnConfirmMeeting(userId);
            // get the list of meetings whose completion are not confirmed
            ds.printOut("Here's a list of meeting(s) with unconfirmed complete:");
            ds.printResult(new ArrayList<>(listOfUnconfirmedMeeting));
            // get the information related to the meeting
            int tradeId = idGetter.getTradeID();
            int numMeeting = otherInfoGetter.getNumMeeting();
            // get the meeting
            Managers.MeetingManager.Meeting meeting3 = mm.getMeetingByIdNum(tradeId, numMeeting);
            // if the meeting exists
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
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case if the file cannot be found.
     */
    public void confirmMeetingTandP() throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        if (mm.getUnConfirmTimePlace(userId, tm).size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            //it will print the list of meetings with unconfirmed time and place
            // and then ask the user for the meeting info
            Managers.MeetingManager.Meeting meeting2 = getMeeting();
            // if the meeting exists in the system
            if (meeting2.getTradeId() != 0) {
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
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    public void EditMeetingTandP() throws InvalidIdException, FileNotFoundException {
        // get the threshold values read from the csv file
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        // get the threshold value needed for this method
        int maxMeetingTimePlaceEdits = thresholdValues.get(3);
        // if there're no meetings that need to be confirmed for time and place
        if (mm.getUnConfirmTimePlace(userId, tm).size() == 0) {
            sm.msgForNothing("here that requires action", ds);
        } else {
            // print the list of unconfirmed time and place meetings
            // get the meeting info from the user
            Managers.MeetingManager.Meeting meeting = getMeeting();
            // if the meeting exists and the threshold is not reached yet
            if (meeting.getTradeId() != 0 && mm.getEditOverThreshold(tm, meeting, thresholdValues.get(3)).equals("")) {
                //asks the user for the date and place
                List<Integer> list = this.dateTimeGetter.getValidDate(ds);
                String place = otherInfoGetter.getPlace();
                //call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
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
        List<Managers.MeetingManager.Meeting> listOfUnconfirmedTimePlace = mm.getUnConfirmTimePlace(userId, tm);
        // if there're meeting with unconfirmed time and place
        if (listOfUnconfirmedTimePlace.size() != 0) {
            ds.printOut("Here's a list of meeting(s) with unconfirmed time and place:");
            ds.printResult(new ArrayList<>(listOfUnconfirmedTimePlace));
        }
        // if there're no meeting with unconfirmed time and place
        else{
            sm.msgForNothing(ds);
        }
    }

    /**
     * Asks the user for the meeting information and finds that meeting.
     * @return The meeting instance that match user's input of the meeting information.
     * @throws InvalidIdException In case if the id is not valid.
     */
    private Meeting getMeeting() throws InvalidIdException {
        // print the meetings with unconfirmed time and place
        // the else part of the below method'll not be reached
        unconfirmedTandPMeetings();
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = idGetter.getTradeID();
        int numMeeting = otherInfoGetter.getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }
}
