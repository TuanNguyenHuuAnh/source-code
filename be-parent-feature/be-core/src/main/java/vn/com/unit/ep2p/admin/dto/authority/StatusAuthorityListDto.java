/*******************************************************************************
 * Class        StatusAuthorityListDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto.authority;

import java.util.List;

/**
 * StatusAuthorityListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class StatusAuthorityListDto {
    
    /** roleId */
    private Long roleId;
    
    /** itemId */
    private Long itemId;
    
    /** processId */
    private Long processId;
    
    /** data */
    private List<StatusAuthorityDto> data;
    
    /**
     * Get roleId
     * @return Long
     * @author KhoaNA
     */
    public Long getRoleId() {
        return roleId;
    }

    /**
     * Set roleId
     * @param   roleId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    /**
     * Get itemId
     * @return Long
     * @author KhoaNA
     */
    public Long getItemId() {
        return itemId;
    }

    /**
     * Set itemId
     * @param   itemId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    /**
     * Get processId
     * @return Long
     * @author KhoaNA
     */
    public Long getProcessId() {
        return processId;
    }

    /**
     * Set processId
     * @param   processId
     *          type Long
     * @return
     * @author  KhoaNA
     */
    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    /**
     * Get data
     * @return List<StatusAuthorityDto>
     * @author KhoaNA
     */
    public List<StatusAuthorityDto> getData() {
        return data;
    }

    /**
     * Set data
     * @param   data
     *          type List<StatusAuthorityDto>
     * @return
     * @author  KhoaNA
     */
    public void setData(List<StatusAuthorityDto> data) {
        this.data = data;
    }
}
