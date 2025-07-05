/*******************************************************************************
 * Class        ：DebugLogger
 * Created date ：2020/05/06
 * Lasted date  ：2020/05/06
 * Author       ：HungHT
 * Change log   ：2020/05/06：01-00 HungHT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.utils;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import vn.com.unit.common.utils.CommonDateUtil
;

/**
 * DebugLogger
 * 
 * @version 01-00
 * @since 01-00
 * @author HungHT
 */
public class AccessLogger {

    private Date startTime;
    private Date endTime;
    private long elapsedTime;

    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(AccessLogger.class);

    /**
     * @param startTime
     * @author HungHT
     */
    public AccessLogger() {
        super();
        this.startTime = new Date();
    }

    /**
     * Get startTime
     * 
     * @return Date
     * @author HungHT
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * Get endTime
     * 
     * @return Date
     * @author HungHT
     */
    public Date getEndTime() {
        return endTime;
    }

    /**
     * Get setEndTime
     * 
     * @author HungHT
     */
    public void setEndTime() {
        this.endTime = new Date();
        long start = this.startTime.getTime();
        long end = this.endTime.getTime();
        this.elapsedTime = end - start;
    }

    /**
     * Get elapsedTime
     * 
     * @return long
     * @author HungHT
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

    /**
     * getStart
     * 
     * @return
     * @author HungHT
     */
    public String getStart() {
        return CommonDateUtil.formatDateToString(this.getStartTime(), CommonDateUtil.YYYYMMDDHHMMSSFFF);
    }

    /**
     * getEnd
     * 
     * @return
     * @author HungHT
     */
    public String getEnd() {
        return CommonDateUtil.formatDateToString(this.getEndTime(), CommonDateUtil.YYYYMMDDHHMMSSFFF);
    }

    /**
     * debug
     * 
     * @param format
     * @param args
     * @author HungHT
     */
    public static void debug(String format, Object... args) {
        logger.debug(String.format(format, args));
    }
    
    /**
     * isDebugEnabled
     * 
     * @return
     * @author HungHT
     */
    public static boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }
    
}
