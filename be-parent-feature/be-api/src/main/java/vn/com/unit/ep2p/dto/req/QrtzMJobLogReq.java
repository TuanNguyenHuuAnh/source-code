/*******************************************************************************
 * Class        ：QrtzMJobLogReq
 * Created date ：2021/01/25
 * Lasted date  ：2021/01/25
 * Author       ：khadm
 * Change log   ：2021/01/25：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.ep2p.dto.req;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * Get error message.
 * </p>
 *
 * @author khadm
 * @return {@link String}
 */
@Getter

/**
 * <p>
 * Sets the error message.
 * </p>
 *
 * @author khadm
 * @param errorMessage
 *            the new error message
 */
@Setter
public class QrtzMJobLogReq {
    /** The id. */
    private Long id;

    /** The job id. */
    private Long jobId;

    /** The job group. */
    private String jobGroup;

    /** The job name ref. */
    private String jobNameRef;

    /** The sched id. */
    private Long schedId;

    /** The status. */
    private Long status;

    /** The start time. */
    private Date startTime;

    /** The next fire time. */
    private Date nextFireTime;

    /** The end time. */
    private Date endTime;

    /** The description. */
    private String description;

    /** The validflag. */
    private Long validflag;
    
    /** The company id. */
    private Long companyId;
    
    /** The start date. */
    private Date startDate;
    
    /** The error message. */
    private String errorMessage;
}
