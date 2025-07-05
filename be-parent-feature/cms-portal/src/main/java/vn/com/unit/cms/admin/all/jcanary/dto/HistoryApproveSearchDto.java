/*******************************************************************************
 * Class        HistoryApproveDto
 * Created date 2017/03/24
 * Lasted date  2017/03/24
 * Author       TranLTH
 * Change log   2017/03/2401-00 TranLTH create a new
 ******************************************************************************/
package vn.com.unit.cms.admin.all.jcanary.dto;

/**
 * HistoryApproveDto
 * 
 * @version 01-00
 * @since 01-00
 * @author TranLTH
 */
public class HistoryApproveSearchDto {

    /** referenceId */
    private Long referenceId;

    /** referenceType */
    private String referenceType;
    
    /** processId */
    private Long processId;

    /**
     * Get referenceId
     * @return Long
     * @author hand
     */
    public Long getReferenceId() {
        return referenceId;
    }

    /**
     * Set referenceId
     * @param   referenceId
     *          type Long
     * @return
     * @author  hand
     */
    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    /**
     * Get referenceType
     * @return String
     * @author hand
     */
    public String getReferenceType() {
        return referenceType;
    }

    /**
     * Set referenceType
     * @param   referenceType
     *          type String
     * @return
     * @author  hand
     */
    public void setReferenceType(String referenceType) {
        this.referenceType = referenceType;
    }

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

}
