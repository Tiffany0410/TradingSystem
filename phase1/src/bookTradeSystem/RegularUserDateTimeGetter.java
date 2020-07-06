package bookTradeSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class RegularUserDateTimeGetter {

    private DisplaySystem ds; //instead of this maybe make the tradingSystem's one protected
    private FilesReaderWriter rw; //instead of this maybe make the tradingSystem's one protected
    private TradeManager tm;
    private MeetingManager mm;
    private UserManager um;
    private String username;
    private int userId;


    /**
     * Constructs a RegularUserDateTimeGetter with a DisplaySystem, a FilesReaderWriter,
     * a TradeManager, a MeetingManager, a UserManager, the regular user's username and userId.
     *
     * @param ds       The presenter class used to print to screen.
     * @param rw       The gateway class used to read or write to file.
     * @param tm       The current state of the TradeManager.
     * @param mm       The current state of the MeetingManager.
     * @param um       The current state of the UserManager.
     * @param username The username of the regular user.
     */
    public RegularUserDateTimeGetter(DisplaySystem ds, FilesReaderWriter rw,
                                 TradeManager tm, MeetingManager mm,
                                 UserManager um, String username) {
        this.ds = ds;
        this.rw = rw;
        this.tm = tm;
        this.mm = mm;
        this.um = um;
        this.username = username;
        this.userId = um.usernameToID(username);
    }


    public String getValidDate(){
        String typeIn = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");

        boolean condition = true;

        while (condition) {
            ds.printOut("Please enter the date between 2020 to 2030 (Format: yyyy-MM-dd HH-mm)");

            Scanner sc = new Scanner(System.in);
            typeIn = sc.nextLine();

            try {
                Date date = format.parse(typeIn);

            } catch (ParseException e) {
                ds.printOut("Wrong date format, please enter the date follow the format.");
            }

            int year = Integer.parseInt(typeIn.substring(0, 4));
            int month = Integer.parseInt(typeIn.substring(5, 7));
            int day = Integer.parseInt(typeIn.substring(8, 10));
            int hour = Integer.parseInt(typeIn.substring(11, 13));
            int minute = Integer.parseInt(typeIn.substring(14, 16));

            if (isValidDay(year, month, day) && isValidTime(hour, minute)) {
                ds.printOut("Time set success");
            } else {
                ds.printOut("Please enter the year between 2020 to 2030");
            }
            condition = false;
        }

        return typeIn;

    }

    private boolean isValidTime(int hour, int min){return 1 <= hour && hour <= 24 && 0 <= min && min <= 59;}

    private boolean isValidDay(int year, int month, int day){
        if (year <= 2020 || year >= 2030){return false;}

        if (month < 1 || month > 12){return false;}

        if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12){
            return 1 <= day && day <= 31;
        }
        else if(month == 4 || month == 6|| month == 9 || month == 11){
            return 1 <= day && day <= 30;
        }
        else{
            if (year % 4 == 0 && (year % 100 != 0 || year % 100 == 0 && year % 400 == 0)){
                return 1 <= day && day <= 29;
            }
            return 1 <= day && day <= 28;
        }
    }



}
