/*******************************************************************************
 * Class        ：JcaRoleService
 * Created date ：2020/12/02
 * Lasted date  ：2020/12/02
 * Author       ：ngannh
 * Change log   ：2020/12/02：01-00 ngannh create a new
 ******************************************************************************/
package vn.com.unit.core.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import vn.com.unit.core.dto.JcaRoleDto;
import vn.com.unit.core.dto.JcaRoleSearchDto;
import vn.com.unit.core.entity.JcaRole;

/**
 * JcaRoleService.
 *
 * @version 01-00
 * @since 01-00
 * @author ngannh
 */
public interface JcaRoleService {

    /** The Constant TABLE_ALIAS_JCA_ROLE. */
    static final String TABLE_ALIAS_JCA_ROLE = "role";

    /**
     * <p>
     * total role by condition
     * </p>
     * .
     *
     * @param jcaRoleSearchDto
     *            type {@link JcaRoleSearchDto}
     * @return int
     * @author NganNH
     */
    public int countRoleByCondition(JcaRoleSearchDto jcaRoleSearchDto);

    /**
     * <p>
     * get all role by condition
     * </p>
     * .
     *
     * @param roleSearchDto
     *            type {@link JcaRoleSearchDto}
     * @param pageable
     *            type {@link Pageable}
     * @return List<RoleDto>
     * @author NganNH
     */
    public List<JcaRoleDto> getRoleByCondition(JcaRoleSearchDto roleSearchDto, Pageable pageable);

    /**
     * getRoleById.
     *
     * @param roleId
     *            type {@link Long}
     * @return JcaRole
     * @author NganNH
     */
    JcaRole getRoleById(Long roleId);

    /**
     * saveJcaRole.
     *
     * @param jcaRole
     *            type {@link JcaRole}
     * @return {@link JcaRole}
     * @author taitt
     */
    JcaRole saveJcaRole(JcaRole jcaRole);

    /**
     * saveJpmProcessDto.
     *
     * @param jcaRoleDto
     *            type {@link JcaRoleDto}
     * @return {@link JcaRole}
     * @author taitt
     */
    JcaRole saveJcaRoleDto(JcaRoleDto jcaRoleDto);

    /**
     * getJcaRoleDtoById.
     *
     * @param id
     *            type {@link Long}
     * @return the jca role dto by id
     * @author taitt
     */
    JcaRoleDto getJcaRoleDtoById(Long id);

    /**
     * <p>
     * Deleted jca role by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @author sonnd
     */
    public void deletedJcaRoleById(Long id);

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
    public int countJcaRoleDtoByRoleCode(String roleCode);

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
    public int countJcaRoleDtoByRoleName(String roleName, Long roleId);

}
