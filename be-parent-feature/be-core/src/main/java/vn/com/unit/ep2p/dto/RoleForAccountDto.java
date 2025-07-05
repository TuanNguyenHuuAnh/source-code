/*******************************************************************************
 * Class        RoleForAccountDto
 * Created date 2017/03/07
 * Lasted date  2017/03/07
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/03/0701-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.dto;

import java.util.Date;

/**
 * RoleForAccountDto
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public class RoleForAccountDto {
    private long id;
    public long accountId;
    public long roleId;
    private Date startDate;
    private Date endDate;
    public Boolean delFlg;
    private String roleName;
    
    private String functionCode;
    
    /**
     * Get functionCode
     * @return String
     * @author trieunh <trieunh@unit.com.vn>
     */
    public String getFunctionCode() {
        return functionCode;
    }

    /**
     * Set functionCode
     * @param   functionCode
     *          type String
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public RoleForAccountDto () {
        
    }

    /**
     * Get id
     * @return long
     * @author trieunh <trieunh@unit.com.vn>
     */
    public long getId() {
        return id;
    }

    /**
     * Set id
     * @param   id
     *          type long
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get accountId
     * @return long
     * @author trieunh <trieunh@unit.com.vn>
     */
    public long getAccountId() {
        return accountId;
    }

    /**
     * Set accountId
     * @param   accountId
     *          type long
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
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
     * Get delFlg
     * @return Boolean
     * @author trieunh <trieunh@unit.com.vn>
     */
    public Boolean getDelFlg() {
        return delFlg;
    }

    /**
     * Set delFlg
     * @param   delFlg
     *          type Boolean
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setDelFlg(Boolean delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * Get startDate
     * @return Date
     * @author trieunh <trieunh@unit.com.vn>
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set startDate
     * @param   startDate
     *          type Date
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get endDate
     * @return Date
     * @author trieunh <trieunh@unit.com.vn>
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set endDate
     * @param   endDate
     *          type Date
     * @return
     * @author  trieunh <trieunh@unit.com.vn>
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get roleName
     * @return String
     * @author thuydtn
     */
    public String getRoleName() {
        return roleName;
    }

    /**
     * Set roleName
     * @param   roleName
     *          type String
     * @return
     * @author  thuydtn
     */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    
}
