/*******************************************************************************
 * Class        RoleRepository
 * Created date 2016/06/01
 * Lasted date  2016/06/01
 * Author       KhoaNA
 * Change log   2016/06/0101-00 KhoaNA create a new
 ******************************************************************************/
package vn.com.unit.ep2p.admin.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.common.dto.Select2Dto;
import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.core.repository.JcaRoleRepository;
import vn.com.unit.ep2p.admin.dto.RoleEditDto;
import vn.com.unit.ep2p.admin.dto.RoleListDto;

/**
 * RoleRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhoaNA
 */
public interface RoleRepository extends JcaRoleRepository {

    /**
     * Find role from view vw_get_user_authority by username and list orgId
     * 
     * @param username
     *          type String
     * @param orgIdList 
     *          type Iterable<Long>
     * @return List<RoleDto>
     * @author KhoaNA
     */
    List<JcaRoleDto> findRoleByUserNameAndOrgIds( @Param("username")String username,
    		@Param("orgIdList")Iterable<Long> orgIdList);
    
    /**
     * Find role by name
     * 
     * @param name
     *          type String
     * @return Role
     * @author KhoaNA
     */
    JcaRole findByCode(@Param("code")String code, @Param("companyId") Long companyId);
    
    /**
     * Count role by JcaRoleSearchDto
     * 
     * @param jcaRoleSearchDto
     *          type JcaRoleSearchDto
     * @return int
     * @author KhoaNA
     */
    int countByRoleSearchDto(@Param("roleSearchDto")JcaRoleSearchDto jcaRoleSearchDto);
    
    /**
     * Find all role by condition roleSearchDto
     * 
     * @param offset
     *         type int
     * @param sizeOfPage
     *         type int
     * @param jcaRoleSearchDto
     *         type JcaRoleSearchDto
     * @return List<RoleListDto>
     * @author KhoaNA
     */
    List<RoleListDto> findListByRoleSearchDtoMYSQL(
    		@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage,
            @Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);
    
    /**
     * 
     * @param offset
     * @param sizeOfPage
     * @param jcaRoleSearchDto
     * @return
     */
    List<RoleListDto> findListByRoleSearchDtoSQLServer(
    		@Param("offset") int offset,
            @Param("sizeOfPage") int sizeOfPage,
            @Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);
    
    /**
     * findListByRoleSearchDto
     * 
     * @param offset
     * @param sizeOfPage
     * @param jcaRoleSearchDto
     * @return
     * @author HungHT
     */
    List<RoleListDto> findListByRoleSearchDto(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);
    
    /**
     * Find list role by roleType
     *
     * @param roleType
     *          type String
     * @return List<Role>
     * @author KhoaNA
     */
    public List<RoleEditDto> findRoleListByRoleType(@Param("roleType") String roleType, @Param("companyId") Long companyId, @Param("companyAdmin") boolean companyAdmin);
    
    /**
     * findRoleListByCompanyId
     * @param companyId
     * @param companyAdmin
     * @return
     * @author trieuvd
     */
    public List<RoleEditDto> findRoleListByCompanyId(@Param("companyId") Long companyId);
    
    //thaonv: update
    int checkRoleUsedByGroup(@Param("id") Long id);
    
	/*-----------------------BEGIN_ORACLE----------------------*/
    
    //RoleRepository_findRoleByUserNameAndOrgIdsOracle
	List<JcaRoleDto> findRoleByUserNameAndOrgIdsOracle(@Param("username") String username,
			@Param("orgIdList") Iterable<Long> orgIdList);

	//RoleRepository_findByCodeOracle
	JcaRole findByCodeOracle(@Param("code") String code);

	//RoleRepository_countByRoleSearchDtoOracle
	int countByRoleSearchDtoOracle(@Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);

	//RoleRepository_findListByRoleSearchDtoOracle
	List<RoleListDto> findListByRoleSearchDtoOracle(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
			@Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);

	//RoleRepository_findRoleListByRoleTypeOracle
	public List<JcaRole> findRoleListByRoleTypeOracle(@Param("roleType") String roleType);
	
    int countByRoleListDtoOracle(@Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);

    List<RoleListDto> findListByRoleListDtoOracle(@Param("offset") int offset, @Param("sizeOfPage") int sizeOfPage,
            @Param("roleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);

	/*-----------------------END_ORACLE----------------------*/
    
    /**
     * Find list role by roleType for authority_process
     *
     * @param roleType
     *          type String
     * @return List<Role>
     * @author KhuongTH
     */
    public List<RoleEditDto> findRoleListByRoleTypeForProcess(@Param("roleType") String roleType, @Param("companyId") Long companyId, @Param("companyAdmin") boolean companyAdmin);
    
    /**
     * findListSelect2Dto
     * @param key
     * @param accountId
     * @param companyId
     * @param isPaging
     * @return
     * @author hiennt
     */
    List<Select2Dto> findListSelect2Dto(@Param("key") String key, @Param("accountId") Long accountId, @Param("companyId") Long companyId, @Param("isPaging") boolean isPaging);
    
}
