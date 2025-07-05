/*******************************************************************************
 * Class        ：QrtzMJobDto
 * Created date ：2021/01/20
 * Lasted date  ：2021/01/20
 * Author       ：khadm
 * Change log   ：2021/01/20：01-00 khadm create a new
******************************************************************************/
package vn.com.unit.quartz.job.dto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * QrtzMJobDto
 * </p>
 * .
 *
 * @author khadm
 * @version 01-00
 * @since 01-00
 */
@Getter
@Setter
public class QrtzMJobDto {

	/** The id. */
	private Long id;

	/** The job code. */
	private String jobCode;

	/** The job name. */
	private String jobName;

	/** The job type. */
	private String jobType;

	/** The job group. */
	private String jobGroup;

	/** The job class name. */
	private String jobClassName;
	
	/** The store name. */
	private String storeName;

	/** The description. */
	private String description;

	/** The validflag. */
	private Long validflag;

	/** The send notification. */
	private Boolean sendNotification;
	
	/** The recipient address. */
	private String recipientAddress;
	
	/** The bcc email addresses. */
	private String bccEmailAddresses;
	
	/** The cc email addresses. */
	private String ccEmailAddresses;
	
	/** The email template. */
	private Long emailTemplate;
	
	/** The lst send status. */
	private List<Long> lstSendStatus;
	
	/** The company name. */
	private String companyName;
	
	
}
