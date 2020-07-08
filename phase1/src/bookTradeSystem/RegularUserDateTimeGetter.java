package bookTradeSystem;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * An instance of this class represents the datetime
 * getter for the RegularUserController class.
 */
public class RegularUserDateTimeGetter {

    /**
     * Constructs for RegularUserDateTimeGetter.
     */
    public RegularUserDateTimeGetter() {

    }


    /**
     * Asks the user for the valid datetime.
     * @param ds The presenter used to display information.
     * @return A list containing user's input of the valid datetime.
     */
    public List<Integer> getValidDate(DisplaySystem ds){

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm");
        List<Integer> list = new ArrayList<>();
        boolean ok = true;
        int year = 0, month = 0, day = 0, hour = 0, minute = 0;
        Scanner sc = new Scanner(System.in);

        do {
            ok = true;
            ds.printOut("Please enter the date between 2020 to 2030 (Format: yyyy-MM-dd HH-mm)");

            try {
                String typeIn = sc.nextLine();

                Date date = format.parse(typeIn);

                year = Integer.parseInt(typeIn.substring(0, 4));
                month = Integer.parseInt(typeIn.substring(5, 7));
                day = Integer.parseInt(typeIn.substring(8, 10));
                hour = Integer.parseInt(typeIn.substring(11, 13));
                minute = Integer.parseInt(typeIn.substring(14, 16));

            } catch (ParseException e) {
                ds.printOut("Wrong date format, please follow the correct format. (Format: yyyy-MM-dd HH-mm)(ex. 2025-09-01 08-30)");
                ok = false;

            } catch (NumberFormatException e) {
                ds.printOut("Please follow the correct date format! (Format: yyyy-MM-dd HH-mm)(ex. 2025-09-01 08-30)");
                ok = false;

            } catch (StringIndexOutOfBoundsException s) {
                ds.printOut("Follow the correct date format! (Format: yyyy-MM-dd HH-mm)(ex. 2025-09-01 08-30)");
                ok = false;
            }

            if (isValidDay(year, month, day) && isValidTime(hour, minute)) {
                ds.printOut("Time set success");
            } else {
                ds.printOut("Time set failed. Try again");
                ok = false;
            }
            list.add(year);
            list.add(month);
            list.add(day);
            list.add(hour);
            list.add(minute);
        } while(!ok);
        return list;

    }

    private boolean isValidTime(int hour, int min){return 1 <= hour && hour <= 24 && 0 <= min && min <= 59;}

    private boolean isValidDay(int year, int month, int day){
        if (year < 2020 || year > 2030){return false;}

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
