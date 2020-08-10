package controllers.regularusersubcontrollers;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

/**
 * An instance of this class represents the datetime
 * checker, which performs various datetime checks.
 *
 * @author Yu Xin Yan, Jiaqi Gong
 * @version IntelliJ IDEA 2020.1
 */
public class RegularUserDateTimeChecker {

    /**
     * Constructs for RegularUserDateTimeGetter.
     */
    public RegularUserDateTimeChecker() {

    }

    /**
     * Checks if the hour and minute are valid.
     * @param hour The hour input by the user.
     * @param min The minute input by the user.
     * @return Whether or not the hour and time are valid.
     */
    public boolean isValidTime(int hour, int min){return 1 <= hour && hour <= 24 && 0 <= min && min <= 59;}

    /**
     * Checks if the year, month, and day are valid for the system time.
     * @param year The year input received from the user as input.
     * @param month The month input by the user.
     * @param day The day input by the user.
     * @return Whether or not the year, month, and day are valid for the system time.
     */
    public boolean isValidDay(int year, int month, int day){
        if (year < 2020 || year > 2025){return false;}

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

    /**
     * Checks if the year, month, and day are valid for a meeting time.
     * @param year The year input received from the user as input.
     * @param month The month input by the user.
     * @param day The day input by the user.
     * @return Whether or not the year, month, and day are valid for a meeting time.
     */
    public boolean isValidDayForMeetingTime(int year, int month, int day){
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int yearNow  = localDate.getYear();
        int monthNow = localDate.getMonthValue();
        int dayNow   = localDate.getDayOfMonth();
        if (isValidDay(year, month, day)) {
            if(yearNow < year && year <= 2024){
                return true;
            }
            else if(yearNow == year && monthNow <month){
                return true;
            }else return yearNow == year && monthNow == month && dayNow < day;
    }return false;}
}
