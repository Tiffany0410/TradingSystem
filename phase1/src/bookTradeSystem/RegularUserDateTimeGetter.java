package bookTradeSystem;

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


    protected int getYear(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int year = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the year (2020-2030) " + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                year = sc.nextInt();
                // if the input is valid
                if (isValidYear(year)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid year!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return year;
    }

    // move to DatetimeGetter class
    protected int getMonth(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int month = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the month (1-12)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                month = sc.nextInt();
                // if the input is valid
                if (isValidMonth(month)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid month!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return month;
    }

    // move to DatetimeGetter class
    protected int getDay(int year, int month){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int day = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the day" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                day = sc.nextInt();
                // if the input is valid
                if (isValidDay(year, month, day)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid day!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return day;

    }

    // move to DatetimeGetter class
    protected int getHour(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int hour = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the hour (1-24)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                hour = sc.nextInt();
                // if the input is valid
                if (isValidHour(hour)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid hour!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return hour;

    }

    // move to DatetimeGetter class
    protected int getMin(){
        /*
         * Based on code by Yassine.b from
         * https://stackoverflow.com/questions/32592922/java-try-catch-with-scanner
         */
        Scanner sc = new Scanner(System.in);
        int min = 0;

        boolean okInput = false;
        do {
            ds.printOut("Please enter the minute (0-59)" + ": ");
            // if the input is int
            if (sc.hasNextInt()) {
                min = sc.nextInt();
                // if the input is valid
                if (isValidMin(min)) {
                    okInput = true;
                } else {
                    ds.printOut("Please enter a valid minute!");
                }
            } else {
                sc.nextLine();
                ds.printOut("Enter a valid Integer value please");
            }
        } while (!okInput);
        return min;

    }

    // move to DatetimeGetter class
    protected boolean isValidYear(int year){
        return 2020 <= year && year <= 2030;
    }

    // move to DatetimeGetter class
    protected boolean isValidMonth(int month){
        return 1 <= month && month <= 12;
    }

    // move to DatetimeGetter class
    protected boolean isValidDay(int year, int month, int day){
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
    // move to DatetimeGetter class
    protected boolean isValidHour(int hour){
        return 1 <= hour && hour <= 24;
    }
    // move to DatetimeGetter class
    protected boolean isValidMin(int min){
        return 0 <= min && min <= 59;
    }
}
