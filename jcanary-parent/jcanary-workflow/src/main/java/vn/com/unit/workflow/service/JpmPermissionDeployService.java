/*******************************************************************************
 * Class        ：JpmPermissionDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmPermissionDeployDto;
import vn.com.unit.workflow.entity.JpmPermissionDeploy;

/**
 * JpmPermissionDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmPermissionDeployService {

    /**
     * get JpmPermissionDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmPermissionDeployDto}
     * @author KhuongTH
     */
    JpmPermissionDeployDto getJpmPermissionDeployDtoById(Long id);

    /**
     * check flag DELETED_ID by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link boolean}
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmPermissionDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmPermissionDeploy
     *            type {@link JpmPermissionDeploy}
     * @return {@link JpmPermissionDeploy}
     * @author KhuongTH
     */
    JpmPermissionDeploy saveJpmPermissionDeploy(JpmPermissionDeploy jpmPermissionDeploy);

    /**
     * save JpmPermissionDeployDto
     * 
     * @param jpmPermissionDeployDto
     *            type {@link JpmPermissionDeployDto}
     * @return {@link JpmPermissionDeploy}
     * @author KhuongTH
     */
    JpmPermissionDeploy saveJpmPermissionDeployDto(JpmPermissionDeployDto jpmPermissionDeployDto);
    
    /**
     * <p>
     * Save jpm permission deploy dtos.
     * </p>
     *
     * @param permissionDeployDtos
     *            type {@link List<JpmPermissionDeployDto>}
     * @param processDeployId
     *            type {@link Long}
     * @return {@link Map<Long,Long>}: key: permissionId, value: permissionDeployId
     * @author KhuongTH
     */
    Map<Long, Long> saveJpmPermissionDeployDtos(List<JpmPermissionDeployDto> permissionDeployDtos, Long processDeployId);

    /**
     * <p>
     * getPermissionDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmPermissionDeployDto>}
     * @author KhuongTH
     */
    List<JpmPermissionDeployDto> getPermissionDeployDtosByProcessDeployId(Long processDeployId);

}