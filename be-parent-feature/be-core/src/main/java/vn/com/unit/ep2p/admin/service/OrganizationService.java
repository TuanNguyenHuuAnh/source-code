/*******************************************************************************
 * Class        OrganizationService
 * Created date 2017/02/28
 * Lasted date  2017/02/28
 * Author       trieunh <trieunh@unit.com.vn>
 * Change log   2017/02/2801-00 trieunh <trieunh@unit.com.vn> create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaConstant;
import vn.com.unit.core.service.JcaOrganizationService;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.OrgNode;

/**
 * OrganizationService
 * 
 * @version 01-00
 * @since 01-00
 * @author trieunh <trieunh@unit.com.vn>
 */
public interface OrganizationService extends JcaOrganizationService{
	/**
	 * Build full tree organization
	 * @return OrganizationNodeModel
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public OrgNode buildOrg();
	
	/**
	 * Build organization tree model by list.
	 * @param listOrg
	 * @return OrganizationNodeModel
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public OrgNode buildOrgTreeModel(List<JcaOrganizationDto> listOrg);
	
	/**
	 * Find list user for organization
	 * @param orgId
	 * @return List<AccountDepartmentDto> : listAccountDepartmentDto
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	//public List<AccountDepartmentDto> findUserForOrg(long orgId);
	
	/**
	 * Build organization by orgId. This is detail for organization.
	 * @param orgId
	 * @param editable : true is edit, false is new 
	 * @return OrganizationModel
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public JcaOrganizationDto buildOrgModel(long orgId, boolean editable);
	
	/**
	 * Update organization. If orgId equals zero, it will create new organization. 
	 * Else it will update current organization.
	 * @param orgModel
	 * @return orgId
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public long updateOrg(JcaOrganizationDto orgModel);
	
	/**
	 * Delete organization
	 * @param orgId
	 * @author trieunh <trieunh@unit.com.vn>
	 */
	public void deleteOrg(Long orgId);
	
	//public void initModelAccountDepartment(ModelAndView mav, long orgId);
	
	//public void saveAccountDepartment(AccountDepartmentModel accDepartmentModel);
	
	//public void deleteAccountDepartMent (long accDeptId);
	/**
	 * findByCode
	 *
	 * @param organizationDto
	 * @return
	 * @author TranLTH
	 */
	public JcaOrganizationDto findByOrgCondition(String orgCode, Long companyId); 
	/**
	 * listDisplayOrgType
	 * Init screen after error
	 * @return List<ConstantDisplay>
	 * @author TranLTH
	 */
	public List<JcaConstant> listDisplayOrgType ();
	/**
	 * listDisplaySubOrgType
	 * Init screen after error
	 * @return List<ConstantDisplay>
	 * @author TranLTH
	 */
	public List<JcaConstant> listDisplaySubOrgType ();
	
	/**
	 * findOrgByListCompanyId
	 * @param companyIds
	 * @param isAdmin
	 * @return
	 * @author trieuvd
	 */
	public OrgNode findOrgByListCompanyId(List<Long> companyIds, Boolean isAdmin);
	
	/**
	 * findOrgByOrgParent
	 * @param orgId
	 * @return
	 * @author trieuvd
	 */
	public List<OrgNode> findOrgByOrgParent(Long orgId);
	
	/**
	 * buildOrgTreeByNodeSelect
	 * @param orgId
	 * @return
	 * @author DaiTrieu
	 */
	public List<OrgNode> buildOrgTreeByNodeSelect(Long orgId, Long companyId);
	
	/**
	 * @param codes
	 * @return
	 * @throws SQLException
	 */
	List<String> getCodesByList(List<String> codes, Long companyId) throws SQLException;
	
	/**
	 * @param list
	 * @param code
	 * @return
	 */
	boolean checkExistedOrg(List<String> list, String code);
	
	/**
	 * @param code
	 * @param companyId
	 * @return
	 * @throws SQLException
	 */
	Long getIdByCode(String code, Long companyId) throws SQLException;
	
	/**
	 * findRootParent
	 * @param code
	 * @param companyId
	 * @return
	 * @author hangnkm
	 */
	JcaOrganizationDto findRootParent(String code, Long companyId);

    /**
     * buildOrg
     * @param orgId
     * @return
     * @author UNIMAL01
     */
    OrgNode buildOrg(Long orgId);

    /**
     * getDetailDto
     * @param orgId
     * @return
     * @throws DetailException
     * @author UNIMAL01
     */
    JcaOrganizationDto getDetailDto(Long orgId) throws DetailException;
	
}
