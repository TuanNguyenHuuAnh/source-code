/*******************************************************************************
 * Class        AuthorityRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.repository.AuthorityRepository;

/**
 * AuthorityRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface AppAuthorityRepository extends AuthorityRepository {

    /**
     * Find list Authority by roleId
     * 
     * @param roleId
     *            Long
     * @param functionType
     *            String
     * @return List<AuthorityDto>
     * @author KhoaNA
     */
    List<JcaAuthorityDto> findAuthorityDtoListByRoleIdAndFunctionType(@Param("roleId") Long roleId, @Param("functionTypes") List<String> functionTypes, @Param("companyId") Long companyId);

    /**
     * Delete authority by roleId and functionType
     * 
     * @param roleId Long
     * @param functionType String
     * @return
     * @author KhoaNA
     */
    @Modifying
    void deleteAuthorityDtoByRoleIdAndFunctionType(@Param("roleId") Long roleId,
            @Param("functionType") String functionType);

    /**
     * Find all role for organization (function code, function name, etc.) for a account.
     * @param accountId : Long
     * @return listAuthority : List<AuthorityDto> 
     * @author trieunh <trieunh@unit.com.vn>
     */
    // AuthorityRepository_findAllRoleByAccountId
    List<JcaAuthorityDto> findAllRoleForOrgByAccountId(@Param("accountId") long accountId);
    List<JcaAuthorityDto> findAllRoleForOrgByAccountIdOracle(@Param("accountId") long accountId);
    
    /**
     * Find all role for account
     * @param accountId
     * @return listAuthority : List<AuthorityDto>
     * @author trieunh <trieunh@unit.com.vn>
     */
    // AuthorityRepository_findAllRoleForAccountByAccountId
    List<JcaAuthorityDto> findAllRoleForAccountByAccountId(@Param("accountId") long accountId);
    
    List<JcaAuthorityDto> findAllRoleForAccountByAccountIdOracle(@Param("accountId") long accountId, @Param("currentDate") Date currentDate);
    /**
     * 
     * @param roleId
     * @param functionType
     */
    @Modifying
    void deleteAuthorityDtoByRoleIdAndFunctionTypeOracle(@Param("roleId") Long roleId,
            @Param("functionType") String functionType);
    
    /**
     * findAuthorityListByRoleIdAndFunctionType
     * @param roleId
     * @param functionType
     * @return
     * @author HungHT
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndFunctionType(@Param("roleId") Long roleId,
            @Param("functionType") String functionType);
    
    @Modifying
    void deleteAuthorityDtoByRoleIdAndRoleType(@Param("roleId") Long roleId,
            @Param("functionType") String functionType);
    
    /**
     * 
     * findAuthorityListByRoleIdAndProcessIdForProcess
     * @param roleId
     * @param processId
     * @param functionType
     * @param subType
     * @return
     * @author KhuongTH
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndProcessIdForProcess(@Param("roleId") Long roleId
    		, @Param("processId") Long processId
    		, @Param("functionType") String functionType
    		, @Param("subType") String subType);
    /**
     * 
     * deleteAuthorityDtoByRoleIdAndFunctionTypeForProcess
     * @param roleId
     * @param functionType
     * @param processId
     * @author KhuongTH
     */
    @Modifying
    void deleteAuthorityDtoByRoleIdAndFunctionTypeForProcess(@Param("roleId") Long roleId
    		, @Param("functionType") String functionType
            , @Param("processId") Long processId
            , @Param("businessId") Long businessId);
    /**
     * 
     * findAuthorityListByRoleIdAndBusinessIdForService
     * @param roleId
     * @param businessId
     * @param functionType
     * @param subType
     * @return
     * @author KhuongTH
     */
    List<JcaAuthorityDto> findAuthorityListByRoleIdAndBusinessIdForService(@Param("roleId") Long roleId
    		, @Param("businessId") Long businessId
    		, @Param("functionType") String functionType
    		, @Param("subType") String subType);
    
    /**
     * findAllRoleForDelegatorByAccountId
     * @param accountId
     * @param expiredDate
     * @return
     */
    List<JcaAuthorityDto> findAllRoleForDelegatorByAccountId(@Param("accountId") long accountId, @Param("expiredDate") Date expiredDate);
    
}
