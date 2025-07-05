/*******************************************************************************
* Class        JpmStatusDeployService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmStatusDeployDto;
import vn.com.unit.workflow.entity.JpmStatusDeploy;

/**
 * JpmStatusDeployService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStatusDeployService {

    /**
     * get JpmStatusDeployDto by id.
     *
     * @param id
     *            type {@link Long}: id of JpmStatusDeploy
     * @return {@link JpmStatusDeployDto}
     * @author KhuongTH
     */
    JpmStatusDeployDto getJpmStatusDeployDtoById(Long id);

    /**
     * <p>
     * Delete by id.
     * </p>
     *
     * @param id
     *            type {@link Long}
     * @return true, if successful
     * @author KhuongTH
     */
    boolean deleteById(Long id);

    /**
     * save JpmStatusDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStatusDeploy
     *            type {@link JpmStatusDeploy}
     * @return {@link JpmStatusDeploy}
     * @author KhuongTH
     */
    JpmStatusDeploy saveJpmStatusDeploy(JpmStatusDeploy jpmStatusDeploy);

    /**
     * save JpmStatusDeployDto.
     *
     * @param jpmStatusDeployDto
     *            type {@link JpmStatusDeployDto}
     * @return {@link JpmStatusDeploy}
     * @author KhuongTH
     */
    JpmStatusDeploy saveJpmStatusDeployDto(JpmStatusDeployDto jpmStatusDeployDto);

    /**
     * <p>
     * Save status deploy dtos.
     * </p>
     *
     * @param statusDeployDtos
     *            type {@link List<JpmStatusDeployDto>}
     * @param processDeployId
     *            type {@link Long}
     * @return {@link Map<Long,Long>}
     * @author KhuongTH
     */
    Map<Long, Long> saveJpmStatusDeployDtos(List<JpmStatusDeployDto> statusDeployDtos, Long processDeployId);

    /**
     * <p>
     * getStatusDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmStatusDeployDto>}
     * @author KhuongTH
     */
    List<JpmStatusDeployDto> getStatusDeployDtosByProcessDeployId(Long processDeployId);

    /**
     * <p>
     * Count status deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link int}
     * @author tantm
     */
    int countStatusDeployDtosByProcessDeployId(Long processDeployId);
    
    public JpmStatusDeployDto getStatusDeploy(Long docId, String languageCode);
    
    public JpmStatusDeployDto getStatusDeployByStatusCode(Long docId, String statusCode, String languageCode);

}