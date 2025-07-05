/*******************************************************************************
 * Class        RoleService
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.servlet.ModelAndView;

import vn.com.unit.core.dto.JcaAuthorityDto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.entity.JcaAccount;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.dts.exception.DetailException;
import vn.com.unit.ep2p.admin.dto.JcaRoleAddDto;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.Select2ResultDto;
import vn.com.unit.ep2p.dto.CommonSearchDto;
import vn.com.unit.common.dto.PageWrapper;
import vn.com.unit.common.dto.SearchKeyDto;

/**
 * RoleService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface RoleService {
    
    /**
     * Create role
     *
     * @param roleAddDto
     *          type RoleAddDto
     * @return Role
     * @author KhoaNA
     */
    public JcaRole create(JcaRoleAddDto roleAddDto);
    
    /**
     * createWithRoleType
     * @param roleAddDto
     * @return
     * @author HungHT
     */
    public JcaRole createWithRoleType(JcaRoleAddDto roleAddDto);
    
    /**
     * Update role
     *
     * @param roleEditDto
     *          type RoleEditDto
     * @return
     * @author KhoaNA
     */
    public void update(RoleEditDto roleEditDto);
    
    /**
     * updateWithRoleType
     * @param roleEditDto
     * @author HungHT
     */
    public void updateWithRoleType(RoleEditDto roleEditDto);
    
    /**
     * Delete role
     *
     * @param id
     *          type Long
     * @return
     * @author KhoaNA
     */
    public void deleteRole(Long id);
    
    /**
     * Find role by name
     *
     * @param name
     *          type String
     * @return Role
     * @author KhoaNA
     */
    public JcaRole findByCodeAndCompanyId(String code, Long companyId);
    
    /**
     * Get RoleEditDto by id
     * 
     * @param id
     *         type Long
     * @return RoleEditDto
     * @author KhoaNA
     */
    public RoleEditDto findRoleEditDtoById(Long id);
    
    /**
     * Role to RoleEditDto
     * 
     * @param role
     *         type Role
     * @return RoleEditDto
     * @author KhoaNA
     */
    public RoleEditDto roleToRoleEditDto(JcaRole role);
    
    /**
     * Search role by condition roleSearchDto
     * 
     * @param roleSearchDto
     *         type JcaRoleSearchDto
     * @return PageWrapper<RoleListDto>
     * @author KhoaNA
     * @throws DetailException 
     */
    public PageWrapper<JcaRoleDto> search(CommonSearchDto searchDto, int page, int pageSize) throws DetailException;
    
    /**
     * searchWithRoleType
     * @param page
     * @param searchDto
     * @return
     * @author HungHT
     * @throws DetailException 
     */
    public PageWrapper<JcaRoleDto> searchWithRoleType(int page, CommonSearchDto searchDto) throws DetailException;
    
    /**
     * Init screen role List
     *
     * @param mav
     *          type ModelAndView
     * @author KhoaNA
     */
    public void initScreenRoleList(ModelAndView mav);
    
    /**
     * Find list role by roleType
     *
     * @param roleType
     *          type String
     * @return List<Role>
     * @author KhoaNA
     */
    public List<RoleEditDto> findRoleListByRoleType( String roleType, Long companyId, boolean companyAdmin);
    /**
     * 
     * @param locale
     * @return
     */
    public List<SearchKeyDto> genSearchKeyList(Locale locale);
    
    /**
     * 
     * @param id
     * @return
     */
    int checkRoleUsedByGroup(Long id);
    
    /**
     * Find list role by roleType
     *
     * @param roleType
     *          type String
     * @return List<Role>
     * @author KhuongTH
     */
    public List<RoleEditDto> findRoleListByRoleTypeForProcess( String roleType, Long companyId, boolean companyAdmin);
    
    /**
     * findRoleListByCompanyId
     * @param companyId
     * @return List RoleEditDto
     * @author trieuvd
     */
    public List<RoleEditDto> findRoleListByCompanyId(Long companyId);
    
    /**
     * getAllRoleForAccountByAccountId
     * @param accountId
     * @return
     */
    List<JcaAuthorityDto> getAllRoleForAccountByAccountId(long accountId);

    /**
     * addAuthorityDelegate
     * @param account
     * @return
     * @author taitt
     */
    List<GrantedAuthority> addAuthorityDelegate(JcaAccount account);
    
    /**
     * getListSelect2Dto
     * @param key
     * @param accountId
     * @param companyId
     * @param isPaging
     * @return
     * @throws SQLException
     * @author hiennt
     */
    Select2ResultDto getListSelect2Dto(String key, Long accountId, Long companyId, boolean isPaging) throws SQLException;
    
}
