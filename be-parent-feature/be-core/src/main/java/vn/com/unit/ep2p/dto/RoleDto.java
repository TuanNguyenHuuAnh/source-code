/*******************************************************************************
 * Class        RoleDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * RoleDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class RoleDto {
    
    /** roleId */
    private long roleId;

    /** roleName */
    private String roleName;
    
    /**
     * roleCode
     */
    private String roleCode;
    
    /** itemName */
    private String itemName;
    
    /** functionType */
    private String functionType;
    
    /** accessFlg */
    private String accessFlg;
    
    /** status */
    private String status;
    
    /** startDate */
    private Date startDate;
    
    /** endDate */
    private Date endDate;
    
    /** teamName */
    private String teamName;
    
    /** processId */
    private String processId;
    
    public RoleDto() {
        
    }

    public RoleDto(long roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    /**
     * Get roleId
     * @return long
     * @author trieunh <trieunh@unit.com.vn>
     */
    public long getRoleId() {
        return roleId;
    }

    /**
     * Set roleId
     * @param   roleId
     *          type long
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    /**
     * Get roleName
     * @return String
     * @author KhoaNA
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set roleName
     * @param   roleName
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * Get itemName
     * @return String
     * @author KhoaNA
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Set itemName
     * @param   itemName
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    /**
     * Get accessFlg
     * @return String
     * @author KhoaNA
     */
    public String getAccessFlg() {
        return accessFlg;
    }

    /**
     * Set accessFlg
     * @param   accessFlg
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setAccessFlg(String accessFlg) {
        this.accessFlg = accessFlg;
    }

    /**
     * Get status
     * @return String
     * @author KhoaNA
     */
    public String getStatus() {
        return status;
    }

    /**
     * Set status
     * @param   status
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * Get startDate
     * @return Date
     * @author KhoaNA
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set startDate
     * @param   startDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get endDate
     * @return Date
     * @author KhoaNA
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set endDate
     * @param   endDate
     *          type Date
     * @return
     * @author  KhoaNA
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    /**
     * Get teamName
     * @return String
     * @author KhoaNA
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Set teamName
     * @param   teamName
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    
    /**
     * Get processId
     * @return String
     * @author KhoaNA
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }
    
    /**
     * Get functionType
     * @return String
     * @author KhoaNA
     */
    public String getFunctionType() {
        return functionType;
    }

    /**
     * Set functionType
     * @param   functionType
     *          type String
     * @return
     * @author  KhoaNA
     */
    public void setFunctionType(String functionType) {
        this.functionType = functionType;
    }

    /**
     * Get roleCode
     * @return String
     * @author thuydtn
     */
    public String getRoleCode() {
        return roleCode;
    }

    /**
     * Set roleCode
     * @param   roleCode
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

}
