/*******************************************************************************
 * Class        ：QrtzMJobScheduleDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QrtzMJobScheduleDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */

@Setter
@Getter
public class QrtzMJobScheduleDto {

    /** The id. */
    private Long id;
    
    /** The job code. */
    private Long jobId;
    
    /** The job name. */
    private String jobName;
    
    /** The job group. */
    private String jobGroup;
    
    /** The job name ref. */
    private String jobNameRef;
    
    /** The sched id. */
    private Long schedId;
    
    /** The sched name. */
    private String schedName;
    
    /** The status. */
    private String status;
    
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
    
    /** The company name. */
    private String companyName;
    
    /** The company id. */
    private Long companyId;
    
    /** The start type. */
    private String startType;
    
    /** The start date. */
    private Date startDate;
    
    /** The error message. */
    private String errorMessage;
}
