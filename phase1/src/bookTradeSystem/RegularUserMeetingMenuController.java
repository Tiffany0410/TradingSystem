package bookTradeSystem;

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
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
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
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, tm, mm, um, username, userId);
        this.dateTimeGetter = new RegularUserDateTimeGetter();
        this.sm = new SystemMessage();
    }

    /**
     * Let the presenter print the list of unconfirmed meetings if there are any,
     * otherwise, print to let user know that there aren't any.
     * @param unConfirmMeeting The list of unconfirmed meetings.
     * @param s The specific part of the message that relates to context.
     */
    protected void seeMeetingsToBeConfirmed(List<Meeting> unConfirmMeeting, String s) {
        if (unConfirmMeeting.size() == 0) {
            sm.msgForNothing(s, ds);
        } else {
            ds.printResult(new ArrayList<>(unConfirmMeeting));
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
    protected void confirmMeetingTookPlace() throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        List<Meeting> unconfirmedMeeting = mm.getUnConfirmMeeting(userId);
        if (unconfirmedMeeting.size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            // print the meetings
            ds.printOut("Here's a list of meeting(s) that haven't been confirmed that it took place: ");
            ds.printResult(new ArrayList<>(unconfirmedMeeting));
            Meeting meeting3 = getMeeting();
            if (meeting3.getTradeId() != 0) {
                ds.printResult(mm.setMeetingConfirm(tm, meeting3, userId, thresholdValues.get(3)));
            } else {
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
    protected void confirmMeetingTandP() throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        List<Meeting> meetingUnconfirmedTP = mm.getUnConfirmTimePlace(userId, tm);
        if (meetingUnconfirmedTP.size() == 0) {
            sm.msgForNothing("that needs to be confirmed", ds);
        } else {
            printUnconfirmedTPMeetings(meetingUnconfirmedTP);
            Meeting meeting2 = getMeeting();
            if (meeting2.getTradeId() != 0) {
                ds.printResult(meeting2.setTimePlaceConfirm(userId, thresholdValues.get(3)));
            } else {
                sm.msgForMeetingDNE(ds);
            }
        }
    }

    private void printUnconfirmedTPMeetings(List<Meeting> meetingUnconfirmedTP) {
        // print the meetings
        ds.printOut("Here's a list of meeting(s) with unconfirmed time and place:");
        ds.printResult(new ArrayList<>(meetingUnconfirmedTP));
    }

    /**
     * If there're any meetings that need to be edited for time
     * and place, let the user input the input of the meeting information
     * and let the user edit the time and place. Else, print to
     * let the user know that there aren't any.
     * @throws InvalidIdException In case if the id is not valid.
     * @throws FileNotFoundException In case the file cannot be found.
     */
    protected void EditMeetingTandP() throws InvalidIdException, FileNotFoundException {
        List<Integer> thresholdValues = FilesReaderWriter.readThresholdValuesFromCSVFile("./src/Others/ThresholdValues.csv");
        int maxMeetingTimePlaceEdits = thresholdValues.get(3);
        List<Meeting> meetingUnconfirmedTP = mm.getUnConfirmTimePlace(userId, tm);
        if (meetingUnconfirmedTP.size() == 0) {
            sm.msgForNothing("here that requires action", ds);
        } else {
            // print the meetings
            printUnconfirmedTPMeetings(meetingUnconfirmedTP);
            Meeting meeting = getMeeting();
            // if the meeting exists and the threshold is not reached yet
            if (meeting.getTradeId() != 0 && mm.getEditOverThreshold(tm, meeting, thresholdValues.get(3)).equals("")) {

                List<Integer> list = this.dateTimeGetter.getValidDate(ds);
                String place = otherInfoGetter.getPlace();

                //int year, int month, int day, int hour, int min, int sec
                //call the setTimePlaceEdit method to pass in param + edit (*pass time by year, month, day, hour, min, sec)
                boolean editSuccess= meeting.setTimePlaceEdit(userId, list.get(0), list.get(1), list.get(2),
                        list.get(3), list.get(4), 0, place, maxMeetingTimePlaceEdits);
                ds.printResult(editSuccess);
                if (!editSuccess){
                    ds.printOut("It's not your turn.");
                }
                // record that it's confirmed by this user
                meeting.setTimePlaceConfirm(userId, maxMeetingTimePlaceEdits);
                // for the edit threshold
                ds.printOut(mm.getEditOverThreshold(tm, meeting, maxMeetingTimePlaceEdits));
            } else {
                if (meeting.getTradeId() == 0) {
                    sm.msgForMeetingDNE(ds);
                }
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
    protected void unconfirmedTandPMeetings() throws InvalidIdException {
        List<Meeting> meetingUnconfirmedTP = mm.getUnConfirmTimePlace(userId, tm);
        if (meetingUnconfirmedTP.size() != 0) {
            printUnconfirmedTPMeetings(meetingUnconfirmedTP);
        }
        else{
            sm.msgForNothing(ds);
        }
    }

    /**
     * Asks the user for the meeting information and finds that meeting.
     * @return The meeting instance that match user's input of the meeting information.
     */
    private Meeting getMeeting(){
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = idGetter.getTradeID();
        int numMeeting = otherInfoGetter.getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }
}
