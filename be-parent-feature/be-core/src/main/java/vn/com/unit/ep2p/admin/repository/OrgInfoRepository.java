/*******************************************************************************
 * Class        OrgInfoRepository
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.core.repository.JcaOrganizationRepository;
import vn.com.unit.ep2p.admin.dto.OrgNode;

/**
 * OrgInfoRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface OrgInfoRepository extends JcaOrganizationRepository {
    
    /**
     * Find list department by accountId
     * @param accountId 
     *         type Long
     * @return List<JcaOrganization>
     * @author KhoaNA
     */
    List<JcaOrganization> findDepartmentListByAccountId(@Param("accountId") Long accountId,@Param("CurrentDate") Date date);
    List<JcaOrganization> findDepartmentListByAccountIdOracle(@Param("accountId") Long accountId,@Param("CurrentDate") Date date);

	/**
	 * Find list JcaOrganization by OrgParentId And OrgType
	 * @param parentIdStr
	 *         type String
	 * @param orgType
	 *         type String
	 * @return List<JcaOrganization>
	 * @author KhoaNA
	 */
	List<JcaOrganization> findListByParentIdAndOrgType( @Param("parentIdStr") String parentIdStr,
	        @Param("orgType") String orgType);
	
	/**
     * Find list JcaOrganization by orgType
     * @param orgType
     *         type String
     * @return List<JcaOrganization>
     * @author KhoaNA
     */
    public List<JcaOrganization> findListByOrgType(@Param("orgType") String orgType);
    
    /**
     * Find all list Organization
     * @return List<JcaOrganization>
     * @author trieunh <trieunh@unit.com.vn>
     */
    List<JcaOrganization> findAllOrg();
    
    /**
     * Find max order by parent Id
     * @param orgId
     * @return orderBy
     * @author trieunh <trieunh@unit.com.vn>
     */
    int findMaxOrderByParrentId(@Param("orgId") long orgId);
    
    /**
     * Update organization tree
     * @param orgTreeId
     * @param orgId
     * @author trieunh <trieunh@unit.com.vn>
     */
    @Modifying
    void updateOrgTreeId(@Param("orgTreeId") String orgTreeId, @Param("orgId") long orgId);
    
    /**
     * Update organization
     * @param orgInfo
     * @author trieunh <trieunh@unit.com.vn>
     */
    @Modifying
    void updateOrg(@Param("orgInfo") JcaOrganization orgInfo); 
    /**
     * findByOrgCondition
     *
     * @param organizationDto
     * @author TranLTH
     */
    public JcaOrganizationDto findByOrgCondition(@Param("orgCode") String orgCode, @Param("companyId") Long companyId);
    /**
     * updateDeletedDate
     *
     * @param orgDto
     * @author TranLTH
     */
    @Modifying
    void updateDeletedDate(@Param("orgDto") JcaOrganizationDto orgDto);
    
    /**
     * updateDeletedDateOracle
     *
     * @param orgDto
     * @author phunghn
     */
    @Modifying
    void updateDeletedDateOracle(@Param("orgDto") JcaOrganizationDto orgDto);
    
    /**
     * findByOrgCode
     * @param orgCode
     * @param companyId
     * @return
     * @author trieuvd
     */
    JcaOrganization findByOrgCode(@Param("orgCode") String orgCode, @Param("companyId") Long companyId);
    
    /**
     * findByParentId
     * @param parentId
     * @return
     * @author trieuvd
     */
    JcaOrganization findByParentId(@Param("parentId") Long parentId);
    
    /**
     * getByListCompanyId
     * @param companyIds
     * @return
     * @author trieuvd
     */
    List<JcaOrganization> getByListCompanyId(@Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin);
    
    /**
     * countByParentId
     * @param treeId
     * @return
     * @author trieuvd
     */
    int countByParentId(@Param("parentId") Long parentId);
    
    /**
     * findByOrgTypeCompanyId
     * @param orgType
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<JcaOrganization> findByOrgTypeCompanyId(@Param("orgType") String orgType, @Param("companyId") Long companyId);
    
    /**
     * findByParentIdOrgTypeCompanyId
     * @param parentId
     * @param orgType
     * @param companyId
     * @return
     * @author trieuvd
     */
    List<JcaOrganization> findByParentIdOrgTypeCompanyId(@Param("parentId") Long parentId, @Param("orgType") String orgType, @Param("companyId") Long companyId);
    
    /**
     * deleteOrgByCompanyId
     * @param companyId
     * @param user
     * @param date
     * @author trieuvd
     */
    @Modifying
    void deleteOrgByCompanyId(@Param("companyId") Long companyId, @Param("user") String user, @Param("date") Date date);
    
    /**
     * findByCompanyIdList
     * @param companyIds
     * @return
     * @author trieuvd
     */
    List<JcaOrganization> findByCompanyIdList(@Param("companyIds") List<Long> companyIds);
    
    /**
     * findNodeByOrgParent
     * @param companyIds
     * @param isAdmin
     * @param orgId
     * @return
     * @author trieuvd
     */
    List<OrgNode> findNodeByOrgParent(@Param("companyIds") List<Long> companyIds, @Param("isAdmin") Boolean isAdmin, @Param("orgId")Long orgId);
    
    /**
     * @param codes
     * @param companyId
     * @return
     */
    List<String> findCodesByList(@Param("codes") List<String> codes, @Param("companyId") Long companyId);

    /**
     * @param code
     * @param companyId
     * @return
     */
    Long findIdByCode(@Param("code") String code, @Param("companyId") Long companyId);
    
    /**
     * findRootParent
     * @param code
     * @param companyId
     * @return
     * @author hangnkm
     */
    JcaOrganizationDto findRootParent(@Param("code") String code, @Param("companyId") Long companyId);
    
}
