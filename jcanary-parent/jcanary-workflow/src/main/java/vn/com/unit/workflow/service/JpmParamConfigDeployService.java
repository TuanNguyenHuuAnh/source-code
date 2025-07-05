/*******************************************************************************
* Class        JpmParamConfigDeployService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmParamConfigDeployDto;
import vn.com.unit.workflow.entity.JpmParamConfigDeploy;

/**
 * JpmParamConfigDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmParamConfigDeployService {

    /**
     * get JpmParamConfigDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmParamConfigDeployDto}
     * @author KhuongTH
     */
    JpmParamConfigDeployDto getJpmParamConfigDeployDtoById(Long id);

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
     * save JpmParamConfigDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmParamConfigDeploy
     *            type {@link JpmParamConfigDeploy}
     * @return {@link JpmParamConfigDeploy}
     * @author KhuongTH
     */
    JpmParamConfigDeploy saveJpmParamConfigDeploy(JpmParamConfigDeploy jpmParamConfigDeploy);

    /**
     * save JpmParamConfigDeployDto
     * 
     * @param jpmParamConfigDeployDto
     *            type {@link JpmParamConfigDeployDto}
     * @return {@link JpmParamConfigDeploy}
     * @author KhuongTH
     */
    JpmParamConfigDeploy saveJpmParamConfigDeployDto(JpmParamConfigDeployDto jpmParamConfigDeployDto);

    /**
     * <p>
     * save list JpmParamConfigDeployDtos by param id 
     * </p>
     * 
     * @param paramConfigDeployDtos
     *            type {@link List<JpmParamConfigDeployDto>}
     * @param paramDeployId
     *            type {@link Long}
     * @param processDeployId
     *            type {@link Long}
     * @author KhuongTH
     */
    void saveJpmParamConfigDeployDtos(List<JpmParamConfigDeployDto> paramConfigDeployDtos, Long paramDeployId, Long processDeployId);

    /**
     * <p>
     * getParamConfigDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmParamConfigDeployDto>}
     * @author KhuongTH
     */
    List<JpmParamConfigDeployDto> getParamConfigDeployDtosByProcessDeployId(Long processDeployId);

    /**
     * <p>
     * Gets the param config deploy dtos by param deploy id.
     * </p>
     *
     * @param paramDeployId
     *            type {@link Long}
     * @return the param config deploy dtos by param deploy id
     * @author KhuongTH
     */
    List<JpmParamConfigDeployDto> getParamConfigDeployDtosByParamDeployId(Long paramDeployId);

}