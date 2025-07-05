/*******************************************************************************
 * Class        ：CommonDateUtil
 * Created date ：2020/11/10
 * Lasted date  ：2020/11/10
 * Author       ：taitt
 * Change log   ：2020/11/10：01-00 taitt create a new
 ******************************************************************************/
package vn.com.unit.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.com.unit.dts.utils.DtsDateUtil;

/**
 * CommonDateUtil.
 *
 * @version 01-00
 * @since 01-00
 * @author taitt
 */
public class CommonDateUtil extends DtsDateUtil{
    
    /** The Constant DATE_TIME_HYPHEN. */
    public static final String DATE_TIME_HYPHEN = "dd/MM/yyyy HH:mm:ss";
    
    /**
     * setMaxTime.
     *
     * @param date
     *            type Date
     * @return Date
     * @author taitt
     */
    public static Date setMaxTime(Date date) {
        date = DtsDateUtil.truncate(date, Calendar.DATE);
        return DtsDateUtil.addMilliseconds(DtsDateUtil.addDays(date, 1), -1);
    }
    
    /**
     * removeTime.
     *
     * @param date
     *            type Date
     * @return Date
     * @author taitt
     */
    public static Date removeTime(Date date) {
        return DtsDateUtil.truncate(date, Calendar.DATE);
    }
    
    /**
     * getSystemDateTime.
     *
     * @return Date
     * @author taitt
     */
    public static Date getSystemDateTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }
    
    /**
     * getSystemDate.
     *
     * @return Date
     * @author taitt
     */
    public static Date getSystemDate() {
        Date sysDate = getSystemDateTime();
        return removeTime(sysDate);
    }
    
    /**
     * parseDate.
     *
     * @param dateString
     *            type String
     * @param format
     *            type String
     * @return Date
     * @author taitt
     */
    public static Date parseDate(String dateString, String format) {
        try {
            return DtsDateUtil.parseDate(dateString, format);
        } catch (ParseException e) {
            return null;
        }
    }
    
    /**
     * addDate.
     *
     * @param offsetDate
     *            type int
     * @param addDay
     *            type Date
     * @return Date
     * @author taitt
     */
    public static Date addDate(Date offsetDate, int addDay) {
        return DtsDateUtil.addDays(offsetDate, addDay);
    }
    
    /**
     * formatDateToString.
     *
     * @param date
     *            type Date
     * @param pattern
     *            type String
     * @return String
     * @author taitt
     */
    public static String formatDateToString(Date date, String pattern) {
        String result = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        if (date != null) {
            result = simpleDateFormat.format(date);
        }

        return result;
    }
    
    /**
     * <p>
     * Format string to date.
     * </p>
     *
     * @param dateString
     *            type {@link String}
     * @param format
     *            type {@link String}
     * @return {@link Date}
     * @throws ParseException
     *             the parse exception
     * @author taitt
     */
    public static Date formatStringToDate(String dateString, String format) throws ParseException{
        DateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }
}
