/*******************************************************************************
 * Class        ：QrtzMScheduleReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Get deleted by.
 * </p>
 *
 * @author khadm
 * @return {@link String}
 */
@Getter

/**
 * <p>
 * Sets the deleted by.
 * </p>
 *
 * @author khadm
 * @param deletedBy
 *            the new deleted by
 */
@Setter
public class QrtzMScheduleReq {

    /** The id. */
    private Long id;

    /** The sched code. */
    private String schedCode;

    /** The sched name. */
    private String schedName;

    /** The sched type. */
    private Long schedType;

    /** The freq type. */
    private Long freqType;

    /** The freq interval. */
    private Long freqInterval;

    /** The freq subday type. */
    private Long freqSubdayType;

    /** The freq subday interval. */
    private Long freqSubdayInterval;

    /** The freq recur interval. */
    private Long freqRecurInterval;

    /** The freq recur factor. */
    private Long freqRecurFactor;

    /** The start date. */
    private String startDate;

    /** The end date. */
    private String endDate;

    /** The start time. */
    private String startTime;

    /** The end time. */
    private String endTime;

    /** The cron expression. */
    private String cronExpression;

    /** The description. */
    private String description;

    /** The validflag. */
    private Long validflag;

    /** The company id. */
    private Long companyId;

    /** The company name. */
    private String companyName;

    /** The url. */
    private String url;

}
