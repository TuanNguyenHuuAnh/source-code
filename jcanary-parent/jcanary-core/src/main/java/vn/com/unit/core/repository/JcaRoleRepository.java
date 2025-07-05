/*******************************************************************************
 * Class        ：JcaRoleRepository
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：NganNH
 * Change log   ：2020/12/02：01-00 NganNH create a new
 ******************************************************************************/
package vn.com.unit.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;
import vn.com.unit.db.repository.DbRepository;

/**
 * JcaRoleRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author NganNH
 */
public interface JcaRoleRepository extends DbRepository<JcaRole, Long> {
    
    /**
     * Total role by condition.
     *
     * @param jcaRoleSearchDto
     *            the jca role search dto
     * @return int
     * @author NganNH
     */
    int countJcaRoleByCondition(@Param("jcaRoleSearchDto") JcaRoleSearchDto jcaRoleSearchDto);
    
    /**
     * Get all role by condition.
     *
     * @param jcaRoleSearchDto
     *            the jca role search dto
     * @param pageable
     *            the pageable
     * @return List<JcaRoleDto>
     * @author NganNH
     */
    public Page<JcaRoleDto> getJcaRoleByCondition(@Param("jcaRoleSearchDto") JcaRoleSearchDto jcaRoleSearchDto,Pageable pageable);
    
    /**
     * <p>
     * get JcaItem by id
     * </p>
     * .
     *
     * @param id
     *            the id
     * @return JcaItem
     * @author MinhNV
     */
    JcaRoleDto getJcaRoleDtoById(@Param("id") Long id);

    /**
     * <p>
     * Count jca role dto by role code.
     * </p>
     *
     * @param roleCode
     *            type {@link String}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaRoleDtoByRoleCode(@Param("roleCode")String roleCode);

    /**
     * <p>
     * Count jca role dto by role name.
     * </p>
     *
     * @param roleName
     *            type {@link String}
     * @param roleId
     *            type {@link Long}
     * @return {@link int}
     * @author sonnd
     */
    int countJcaRoleDtoByRoleName(@Param("roleName")String roleName, @Param("roleId")Long roleId);
    
}
