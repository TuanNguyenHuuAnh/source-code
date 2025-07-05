/*******************************************************************************
 * Class        ：QrtzMScheduleDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * QrtzMScheduleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author khadm
 */
@Getter
@Setter
public class QrtzMScheduleDto {
	
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
