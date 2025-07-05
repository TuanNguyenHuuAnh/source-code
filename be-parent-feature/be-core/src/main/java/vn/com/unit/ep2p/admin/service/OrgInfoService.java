/*******************************************************************************
 * Class        OrgInfoService
 * Created date 2016/07/21
 * Lasted date  2016/07/21
 * Author       KhoaNA
 * Change log   2016/07/1901-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.core.dto.JcaOrganizationDto;
import vn.com.unit.core.entity.JcaOrganization;
import vn.com.unit.ep2p.admin.dto.OrgNode;
import vn.com.unit.ep2p.admin.enumdef.OrgType;

/**
 * OrgInfoService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface OrgInfoService {
    
    /**
     * Find departments by accountId
     * @param accountId 
     *         type Long
     * @return List<OrgInfo>
     * @author KhoaNA
     */
    public List<JcaOrganization> findDepartmentListByAccountId(Long accountId);
    
    /**
     * Find list OrgInfo by orgParentId and orgType
     * @param parentId
     *          type Long
     * @param orgType
     *          type OrgType
     * @return List<OrgInfo>
     * @author KhoaNA
     */
    public List<JcaOrganization> findListByParentIdAndOrgType(Long parentId, OrgType orgType);
    
    /**
     * Find list tree OrgNode by orgType
     * @param orgType
     *          type OrgType
     * @return List<OrgNode>
     * @author KhoaNA
     */
    public List<OrgNode> findOrgNodeTreeByOrgType(OrgType orgType);
    
    /**
     * Find list tree OrgNode by parentId and orgType
     *
     * @param parentId
     *          type Long
     * @param orgType
     *          type OrgType
     * @return List<OrgNode>
     * @author KhoaNA
     */
    public List<OrgNode> findOrgNodeTreeByParentIdAndOrgType(Long parentId, OrgType orgType);
    
    /**
     * Find list OrgInfo by orgType
     *
     * @param orgType
     *          type OrgType
     * @return List<OrgInfo>
     * @author KhoaNA
     */
    public List<JcaOrganization> findListByOrgType(OrgType orgType);
    
    /**
     * findByOrgTypeCompanyId
     * @param orgType
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<OrgNode> getNodeByOrgTypeCompanyId(OrgType orgType, Long companyId);
    
    /**
     * findByParentIdOrgTypeCompanyId
     * @param parentId
     * @param orgType
     * @param companyId
     * @return
     * @author trieuvd
     */
    public List<OrgNode> getNodeByParentIdOrgTypeCompanyId(Long parentId, OrgType orgType, Long companyId);
    
    public List<OrgNode> getNodeByCompanyIdList(List<Long> companyIds);
    
    /**
     * Find orgInfo by id
     * @param id 
     *         type Long
     * @return OrgInfo
     * @author KhoaNA
     */
    public JcaOrganization findOrgInfoById(Long id);
    
    /**
     * getNodeByCompanyId
     * @param companyId
     * @return
     * @author DaiTrieu
     */
    public List<OrgNode> getNodeByCompanyId(Long companyId);
    
    /**
     * 
     * getByCodeAndCompanyId
     * @param companyId
     * @return
     * @author taitt
     */
    public JcaOrganizationDto getByCodeAndCompanyId(String code,Long companyId);
}
