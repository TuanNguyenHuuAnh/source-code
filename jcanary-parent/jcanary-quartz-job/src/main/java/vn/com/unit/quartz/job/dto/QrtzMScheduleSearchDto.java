/*******************************************************************************
 * Class        ：QrtzMScheduleSearchDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * QrtzMScheduleSearchDto
 * 
 * @version 01-00
 * @since 01-00
 * @author khadm
 */
@Getter
@Setter
public class QrtzMScheduleSearchDto extends AbstractCompanyDto {

	/** The sched code. */
	private String schedCode;

	/** The sched name. */
	private String schedName;

	/** The cron expression. */
	private String cronExpression;

	/** The description. */
	private String description;
	
}
