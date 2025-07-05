
/*******************************************************************************
 * Class        AuthorityListDto
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.dto;

import java.util.List;

import vn.com.unit.core.dto.JcaAuthorityDto;

/**
 * AuthorityListDto
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public class JcaAuthorityListDto {
    
    /** roleId */
    private Long roleId;
    
    /** data */
    private List<JcaAuthorityDto> data;

    /** processId*/
    private Long processId;
    
    private Long businessId;
    
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
     * Get data
     * @return List<AuthorityDto>
     * @author KhoaNA
     */
    public List<JcaAuthorityDto> getData() {
        return data;
    }

    /**
     * Set data
     * @param   data
     *          type List<AuthorityDto>
     * @return
     * @author  KhoaNA
     */
    public void setData(List<JcaAuthorityDto> data) {
        this.data = data;
    }

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getBusinessId() {
		return businessId;
	}

	public void setBusinessId(Long businessId) {
		this.businessId = businessId;
	}
    
    
}
