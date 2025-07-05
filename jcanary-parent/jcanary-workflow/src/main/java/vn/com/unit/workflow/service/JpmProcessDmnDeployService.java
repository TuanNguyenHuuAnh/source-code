/*******************************************************************************
* Class        JpmProcessDmnDeployService
* Created date 2021/03/15
* Lasted date  2021/03/15
* Author       KhuongTH
* Change log   2021/03/15 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmProcessDmnDeployDto;
import vn.com.unit.workflow.entity.JpmProcessDmnDeploy;

/**
 * JpmProcessDmnDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmProcessDmnDeployService {

    /**
     * get JpmProcessDmnDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmProcessDmnDeployDto}
     * @author KhuongTH
     */
    JpmProcessDmnDeployDto getJpmProcessDmnDeployDtoById(Long id);

    /**
     * save JpmProcessDmnDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmProcessDmnDeploy
     *            type {@link JpmProcessDmnDeploy}
     * @return {@link JpmProcessDmnDeploy}
     * @author KhuongTH
     */
    JpmProcessDmnDeploy saveJpmProcessDmnDeploy(JpmProcessDmnDeploy jpmProcessDmnDeploy);

    /**
     * save JpmProcessDmnDeployDto
     * 
     * @param jpmProcessDmnDeployDto
     *            type {@link JpmProcessDmnDeployDto}
     * @return {@link JpmProcessDmnDeploy}
     * @author KhuongTH
     */
    JpmProcessDmnDeploy saveJpmProcessDmnDeployDto(JpmProcessDmnDeployDto jpmProcessDmnDeployDto);

    /**
     * <p>
     * Gets the jpm process dmn deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the jpm process dmn deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmProcessDmnDeployDto> getJpmProcessDmnDeployDtosByProcessDeployId(Long processDeployId);

}