/*******************************************************************************
 * Class        ：QrtzMJobScheduleSearchDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QrtzMJobScheduleSearchDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */

@Getter
@Setter
public class QrtzMJobScheduleSearchDto extends AbstractCompanyDto {

	/** The job code. */
	private String jobCode;
	
	/** The sched code. */
	private String schedCode;
	
	/** The status. */
	private String status;
	
	private List<String> statusList;
	
	/** The start time. */
	private String startTime;
	
	/** The end time. */
	private String endTime;
	
	/** The start date. */
	private Date startDate;
	
	/** The end date. */
	private Date endDate;
	
	private Long jobId;
	
	private Long schedId;
}
