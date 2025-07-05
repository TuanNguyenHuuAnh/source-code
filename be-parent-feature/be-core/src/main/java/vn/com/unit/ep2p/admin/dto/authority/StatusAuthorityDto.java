/*******************************************************************************
 * Class        StatusAuthorityDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto.authority;

/**
 * StatusAuthorityDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class StatusAuthorityDto {
    
    /** processStatusId */
    private Long processStatusId;
    
    /** statusCode */
    private String statusCode;
    
    /** statusName */
    private String statusName;
    
    /** canAccessFlg */
    private boolean canAccessFlg;
    
    /**
     * Get processStatusId
     * @return Long
     * @author KhoaNA
     */
    public Long getProcessStatusId() {
        return processStatusId;
    }

    /**
     * Set processStatusId
     * @param   processStatusId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setProcessStatusId(Long processStatusId) {
        this.processStatusId = processStatusId;
    }

    /**
     * Get statusCode
     * @return String
     * @author KhoaNA
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Set statusCode
     * @param   statusCode
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Get statusName
     * @return String
     * @author KhoaNA
     */
    public String getStatusName() {
        return statusName;
    }

    /**
     * Set statusName
     * @param   statusName
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Get canAccessFlg
     * @return boolean
     * @author KhoaNA
     */
    public boolean isCanAccessFlg() {
        return canAccessFlg;
    }

    /**
     * Set canAccessFlg
     * @param   canAccessFlg
     *          type boolean
     * @return
     * @author  KhoaNA
     */
    public void setCanAccessFlg(boolean canAccessFlg) {
        this.canAccessFlg = canAccessFlg;
    }
}
