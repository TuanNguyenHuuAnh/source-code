/*******************************************************************************
 * Class        SchedulerJobLogSearchDto
 * Created date 2017/09/15
 * Lasted date  2017/09/15
 * Author       VinhLT
 * Change log   2017/09/15  ver 1.0 VinhLT create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.sla.dto;

import java.util.Date;

/**
 * @version 1.0
 * @since 1.0
 * @author VinhLT, phone: 0984 054 114
 *
 */

public class SchedulerJobLogSearchDto{

    private String jobName;

    private Date fromTime;

    private Date toTime;

    /**
     * Get jobName
     * 
     * @return String
     * @author VinhLT
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Set jobName
     * 
     * @param jobName
     *            type String
     * @return
     * @author VinhLT
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Get fromTime
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getFromTime() {
        return fromTime != null ? (Date) fromTime.clone() : null;
    }

    /**
     * Set fromTime
     * 
     * @param fromTime
     *            type Date
     * @return
     * @author VinhLT
     */
    public void setFromTime(Date fromTime) {
        this.fromTime = fromTime != null ? (Date) fromTime.clone() : null;
    }

    /**
     * Get toTime
     * 
     * @return Date
     * @author VinhLT
     */
    public Date getToTime() {
        return toTime != null ? (Date) toTime.clone() : null;
    }

    /**
     * Set toTime
     * 
     * @param toTime
     *            type Date
     * @return
     * @author VinhLT
     */
    public void setToTime(Date toTime) {
        this.toTime = toTime != null ? (Date) toTime.clone() : null;
    }

}
