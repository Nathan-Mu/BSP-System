package BSP;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
/**
 * Write a description of class Time here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Time
{
    public static final String JSON_DATE_FORMAT = "MMM dd, yyyy hh:mm:ss";

    public static Date toDate(String stringDate, String formatString)
    {
        SimpleDateFormat format= new SimpleDateFormat(formatString);
        Date date = null;
        try {
            date = format.parse(stringDate);
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        return date;
    }

    public static Date toTime(String stringTime)
    {
        SimpleDateFormat format= new SimpleDateFormat("HH:mm");
        Date time = null;
        try {
            time = format.parse(stringTime);
        }
        catch (Exception e) {
            //e.printStackTrace();
        }
        return time;
    }

    public static String getCurrentTextDate()
    {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DAY_OF_MONTH);
        return date + "/" + month + "/" + year;
    }

    public static Date getCurrentDate()
    {
        return toDate(getCurrentTextDate(), "dd/MM/yyyy");
    }

    public static String getCurrentTextTime()
    {
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return hour + ":" + minute;
    }

    public static Date getCurrentTime()
    {
        return toTime(getCurrentTextTime());
    }

    public static String toText(Date date, String formatString)
    {   SimpleDateFormat format = new SimpleDateFormat(formatString);
        String string = format.format(date);
        return string;
    }

    public static String getTextDate(Date date)
    {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String string = format.format(date);
        return string;
    }
    
    public static boolean isSameDay(Date date1, Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        if (cal1.get(Calendar.YEAR) != cal2.get(Calendar.YEAR))
            return false;
        else if (cal1.get(Calendar.MONTH) != cal2.get(Calendar.MONTH))
            return false;
        else if (cal1.get(Calendar.DAY_OF_MONTH) != cal2.get(Calendar.DAY_OF_MONTH))
            return false;
        else
            return true;
    }
}
