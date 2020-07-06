package bookTradeSystem;

public class RegularUserInstanceGetter {

    private RegularUserMeetingMenuController mmc;
    private RegularUserIDGetter idGetter;
    private RegularUserOtherInfoGetter otherInfoGetter;
    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserInstanceGetter with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserInstanceGetter(DisplaySystem ds, FilesReaderWriter rw,
                               TradeManager tm, MeetingManager mm,
                               UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
        this.mmc = new RegularUserMeetingMenuController(ds, rw, tm, mm, um, username);
        this.otherInfoGetter = new RegularUserOtherInfoGetter(ds, rw, tm, mm, um, username);
        this.idGetter = new RegularUserIDGetter(ds, rw, tm, mm, um, username);
    }

    // TODO: MOVE TO InstanceGetter class
    protected Meeting getMeeting() throws InvalidIdException {
        mmc.unconfirmedTandPMeetings();
//      ask the user to enter the trade id, meetingNum, time and place
        int tradeId = idGetter.getTradeID();
        int numMeeting = otherInfoGetter.getNumMeeting();
        return mm.getMeetingByIdNum(tradeId, numMeeting);
    }


}
