/*******************************************************************************
 * Class        ：QrtzTriggerDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import lombok.Getter;
import lombok.Setter;


/**
 * QrtzTriggerDto
 * 
 * @version 01-00
 * @since 01-00
 * @author khadm
 */
@Getter
@Setter
public class QrtzTriggerDto {
	
	/** The trigger name. */
	private String triggerName;
	
	/** The trigger group. */
	private String triggerGroup;
	
	/** The job name. */
	private String jobName;
	
	/** The job group. */
	private String jobGroup;
}
