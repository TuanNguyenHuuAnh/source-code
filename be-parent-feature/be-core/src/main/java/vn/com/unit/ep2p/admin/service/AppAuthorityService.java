/*******************************************************************************
 * Class        AuthorityService
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.util.List;

import vn.com.unit.common.exception.AppException;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.entity.JcaAuthority;
import vn.com.unit.core.service.AuthorityService;

/**
 * AuthorityService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppAuthorityService extends AuthorityService{

    /**
     * Get AuthorityDto List By RoleId
     * 
     * @param roleId
     *            type Long
     * @param functionTypes
     *            type List<String>
     * @return List<AuthorityDto>
     * @author KhoaNA
     */
    public List<JcaAuthorityDto> findAuthorityDtoListByRoleIdAndFunctionType(Long roleId, List<String> functionTypes, Long companyId);

    /**
     * List AuthorityDto To List Authority
     * 
     * @param authorityDtoLst
     *            type List<AuthorityDto>
     * @return List<Authority>
     * @author KhoaNA
     */
    public List<JcaAuthority> authorityDtoListToAuthorityList(List<JcaAuthorityDto> authorityDtoLst);

    /**
     * Save authority list
     * 
     * @param authorityList
     *            type List<Authority>
     * @param roleId
     *            type Long
     * @return
     * @author KhoaNA
     */
    public void saveAuthority(List<JcaAuthority> authorityList, Long roleId);

    /**
     * findAuthorityListByRoleIdAndFunctionType
     * 
     * @param roleId
     * @param functionType
     * @return
     * @author HungHT
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndFunctionType(Long roleId, String functionType);

    /**
     * saveAuthorityWithRoleType
     * 
     * @param authorityList
     * @param roleId
     * @author HungHT
     */
    void saveAuthorityWithRoleType(List<JcaAuthority> authorityList, Long roleId);

    /**
     * findAuthorityListByRoleIdAndFunctionType
     * 
     * @param roleId
     *            type Long
     * @param processId
     *            type Long
     * @return List<AuthorityDto>
     * @author KhuongTH
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndProcessIdForProcess(Long roleId, Long processId);

    /**
     * saveAuthorityProcess
     * 
     * @param authorityList
     * @param roleId
     * @param processId
     * @param businessId
     * @throws AppException
     *             void
     * @author KhuongTH
     */
    public void saveAuthorityProcess(List<JcaAuthority> authorityList, Long roleId, Long processId, Long businessId) throws AppException;

    /**
     * 
     * findAuthorityListByRoleIdAndBusinessIdForService
     * 
     * @param roleId
     * @param businessId
     * @return
     * @author KhuongTH
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndBusinessIdForService(Long roleId, Long businessId);

}
