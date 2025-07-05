/*******************************************************************************
 * Class        RoleForDisplayEmail
 * Created date 2019/12/03
 * Author       trieuvd
 ******************************************************************************/
package vn.com.unit.ep2p.admin.entity;

import org.springframework.data.annotation.Id;

import jp.sf.amateras.mirage.annotation.Column;
import jp.sf.amateras.mirage.annotation.PrimaryKey;
import jp.sf.amateras.mirage.annotation.PrimaryKey.GenerationType;
import jp.sf.amateras.mirage.annotation.Table;
import vn.com.unit.db.entity.AbstractTracking;

/**
 * RoleForCompany
 * 
 * @version 01-00
 * @since 01-00
 * @author DaiTrieu
 */
@Table(name = "JCA_ROLE_FOR_DISPLAY_EMAIL")
public class RoleForDisplayEmail extends AbstractTracking{
    @Id
    @Column(name = "id")
    @PrimaryKey(generationType = GenerationType.SEQUENCE, generator = "SEQ_JCA_ROLE_FOR_DISPLAY_EMAIL")
    private Long id;
    
    @Column(name = "company_id")
    private Long companyId;
    
    @Column(name = "role_id")
    private Long roleId;
    
    @Column(name = "del_flg")
    private Boolean delFlg;
    
    @Column(name = "org_id")
    private Long orgId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Boolean getDelFlg() {
		return delFlg;
	}

	public void setDelFlg(Boolean delFlg) {
		this.delFlg = delFlg;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
}
