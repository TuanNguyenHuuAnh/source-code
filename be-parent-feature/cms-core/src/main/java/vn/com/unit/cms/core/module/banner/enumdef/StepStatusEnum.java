/*******************************************************************************
 * Class        StepStatusEnum
 * Created date 2018/10/18
 * Lasted date  2018/10/18
 * Author       hand
 * Change log   2018/10/1801-00 hand create a new
 ******************************************************************************/
package vn.com.unit.cms.core.module.banner.enumdef;

/**
 * StepStatusEnum
 * 
 * @version 01-00
 * @since 01-00
 * @author hand
 */
public enum StepStatusEnum {

	// Save draft
	DRAFT(1, "process.status.save_draft"),

	// Rejected
	REJECTED(98, "process.status.rejected"),

	// Approved
	APPROVED(3, "process.status.approved"),

	// Cancel
	CANCELED(100, "process.status.canceled"),
	
	// Waiting for approve
	WAITING_FOR_APPROVE(2, "process.status.waiting.for.approve"),
    
	// Published
    PUBLISHED(99, "process.status.published");

	private Integer stepNo;

	private String statusName;

	private StepStatusEnum(Integer stepNo, String statusName) {
		this.stepNo = stepNo;
		this.statusName = statusName;
	}

	public Integer getStepNo() {
		return stepNo;
	}

	public String getStatusName() {
		return statusName;
	}

}
