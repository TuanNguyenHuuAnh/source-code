/*******************************************************************************
* Class        JpmPermissionService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmPermissionDto;
import vn.com.unit.workflow.entity.JpmPermission;

/**
 * JpmPermissionService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmPermissionService {

    /**
     * get JpmPermissionDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmPermissionDto}
     * @author KhuongTH
     */
    JpmPermissionDto getJpmPermissionDtoById(Long id);

    /**
     * check flag DELETED_ID by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmPermission with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmPermission
     *            type {@link JpmPermission}
     * @return {@link JpmPermission}
     * @author KhuongTH
     */
    JpmPermission saveJpmPermission(JpmPermission jpmPermission);

    /**
     * save JpmPermissionDto.
     *
     * @param jpmPermissionDto
     *            type {@link JpmPermissionDto}
     * @return {@link JpmPermission}
     * @author KhuongTH
     */
    JpmPermission saveJpmPermissionDto(JpmPermissionDto jpmPermissionDto);

    /**
     * <p>
     * get list PermissionDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmPermissionDto>}
     * @author KhuongTH
     */
    List<JpmPermissionDto> getPermissionDtosByProcessId(Long processId);

    
    /**
     * <p>
     * Gets the jpm permission dto by permission id.
     * </p>
     *
     * @param processPermissionId
     *            type {@link Long}
     * @return the jpm permission dto by permission id
     * @author SonND
     */
    JpmPermissionDto getJpmPermissionDtoByPermissionId(Long processPermissionId);

    /**
     * savePermissionDtosByProcessId
     * @param permissionDto
     * @param processId
     * @author ngannh
     */
    Map<Long, Long> savePermissionDtosByProcessId(List<JpmPermissionDto> permissionDto, Long processId);
}