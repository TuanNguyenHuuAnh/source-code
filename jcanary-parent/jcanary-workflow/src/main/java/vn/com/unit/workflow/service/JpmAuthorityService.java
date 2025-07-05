/*******************************************************************************
 * Class        ：JpmAuthorityService
 * Created date ：2021/03/07
 * Lasted date  ：2021/03/07
 * Author       ：Tan Tai
 * Change log   ：2021/03/07：01-00 Tan Tai create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmAuthorityDto;

/**
 * JpmAuthorityService.
 *
 * @author Tan Tai
 * @version 01-00
 * @since 01-00
 */
public interface JpmAuthorityService {

    /**
     * getListJpmAuthorityDtoByPermissionIds.
     *
     * @author Tan Tai
     * @param permissionIds
     *            type {@link List<Long>}
     * @return {@link List<JpmAuthorityDto>}
     */
    List<JpmAuthorityDto> getListJpmAuthorityDtoByPermissionIds(List<Long> permissionIds);

    /**
     * <p>
     * Gets the jpm authority dtos by process deploy id and role id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return the jpm authority dtos by process deploy id and role id
     * @author KhuongTH
     */
    List<JpmAuthorityDto> getJpmAuthorityDtosByProcessDeployIdAndRoleId(Long processDeployId, Long roleId);

    /**
     * <p>
     * Save jpm authority dtos by process deploy id and role id.
     * </p>
     *
     * @param authorityDtos
     *            type {@link List<JpmAuthorityDto>}
     * @param processDeployId
     *            type {@link Long}
     * @param roleId
     *            type {@link Long}
     * @return true, if successful
     * @author KhuongTH
     */
    boolean saveJpmAuthorityDtosByProcessDeployIdAndRoleId(List<JpmAuthorityDto> authorityDtos, Long processDeployId, Long roleId);

    /**
     * <p>
     * Clone role for process.
     * </p>
     *
     * @param oldProcessDeployId
     *            type {@link Long}
     * @param newProcessDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void cloneRoleForProcess(Long oldProcessDeployId, Long newProcessDeployId);
}
