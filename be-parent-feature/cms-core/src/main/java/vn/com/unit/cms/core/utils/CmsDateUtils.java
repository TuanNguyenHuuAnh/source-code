/*******************************************************************************
 * Class        DateUtilities
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       TaiTM
 * Change log   2016/06/0101-00 TaiTM create a new
 ******************************************************************************/
package vn.com.unit.cms.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DateUtilities
 * 
 * @version 01-00
 * @since 01-00
 * @author TaiTM
 */
public class CmsDateUtils {

    public static final String DDMMYYYY_HYPHEN = "dd/MM/yyyy";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYYMMDD = "yyyyMMdd";

    public static final String YYYYMM = "yyyyMM";
	private static final Logger logger = LoggerFactory.getLogger(CmsDateUtils.class);

    /**
     * formatToString
     *
     * @param date
     * @param pattern
     * @return String
     * @author hand
     */
    public static String formatDateToString(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        if (date == null) {
            return null;
        }

        return simpleDateFormat.format(date);
    }

    /**
     * removeTime
     *
     * @param specified
     * @return
     * @author Phucdq
     */
    public static Date removeTime(Date specified) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(specified);
        // Clear time
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date addWorkingDate(Date offsetDate, int addDay, List<Date> holidays) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        for (int day = 1; day <= addDay; ++day) {
            c.setTime(addResult);
            c.add(Calendar.DATE, 1);
            addResult = c.getTime();
            if (isHoliday(addResult, holidays)) {
                addDay = addDay + 1;
            }
        }
        return addResult;
    }

    public static Date addDate(Date offsetDate, int addDay) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        c.setTime(addResult);
        c.add(Calendar.DATE, addDay);
        return c.getTime();
    }

    public static Date addMonth(Date offsetDate, int addMonth) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        c.setTime(addResult);
        c.add(Calendar.MONTH, addMonth);
        return c.getTime();
    }

    public static Date subtractDate(Date offsetDate, int subtractDay) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        c.setTime(addResult);
        c.add(Calendar.DATE, subtractDay * (-1));
        return c.getTime();
    }

    public static Date subtractMonth(Date offsetDate, int subtracMonth) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        c.setTime(addResult);
        c.add(Calendar.MONTH, subtracMonth * (-1));
        return c.getTime();
    }

    public static Date subtractYear(Date offsetDate, int subtracYear) {
        Date addResult = offsetDate;
        Calendar c = Calendar.getInstance();
        c.setTime(addResult);
        c.add(Calendar.YEAR, subtracYear * (-1));
        return c.getTime();
    }

    public static boolean isHoliday(Date date, List<Date> holidays) {
        for (Date holiday : holidays) {
            if (date.equals(holiday)) {
                return true;
            }
        }
        return false;
    }

    public static boolean dateAfterDate(Date expiredDate, Date effectedDate) {
        return !expiredDate.before(effectedDate);
    }

    /**
     * @param date
     * @return date in format dd.MM.yyyy
     * @throws ParseException
     */
    public static Date trim(Date date) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(format.format(date));
    }

    /**
     * convert date format string to date object
     * 
     * @param dateString
     * @param format
     * @return
     * @throws ParseException
     * @author thuydtn
     */
    public static Date formatStringToDate(String dateString, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    public static Date addCurrentTime(Date date) {
        Calendar currentCal = Calendar.getInstance();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, currentCal.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, currentCal.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, currentCal.get(Calendar.SECOND));

        return calendar.getTime();
    }

    public static boolean beforeDateRemoveTime(Date date, Date dateCompare) {
        return CmsDateUtils.removeTime(date).before(CmsDateUtils.removeTime(dateCompare));
    }
    
    public static long dateSubDate(Date date1, Date date2) {
        long diffInMillies = Math.abs(date1.getTime() - date2.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;
    }
    
    public static Date convertStringToDate(String dateString, String format) {
        Date date = null;
        try {
            date = new SimpleDateFormat(format).parse(dateString);
        } catch (ParseException e) {
            logger.error("Exception ", e);
        }

        return date;
    }
}
