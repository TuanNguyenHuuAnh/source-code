/*******************************************************************************
 * Class        ：JpmButtonDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.workflow.dto.JpmButtonDeployDto;
import vn.com.unit.workflow.entity.JpmButtonDeploy;

/**
 * JpmButtonDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonDeployService {

    /**
     * get JpmButtonDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmButtonDeployDto}
     * @author KhuongTH
     */
    JpmButtonDeployDto getJpmButtonDeployDtoById(Long id);

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
     * save JpmButtonDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmButtonDeploy
     *            type {@link JpmButtonDeploy}
     * @return {@link JpmButtonDeploy}
     * @author KhuongTH
     */
    JpmButtonDeploy saveJpmButtonDeploy(JpmButtonDeploy jpmButtonDeploy);

    /**
     * save JpmButtonDeployDto
     * 
     * @param jpmButtonDeployDto
     *            type {@link JpmButtonDeployDto}
     * @return {@link JpmButtonDeploy}
     * @author KhuongTH
     */
    JpmButtonDeploy saveJpmButtonDeployDto(JpmButtonDeployDto jpmButtonDeployDto);

    /**
     * <p>
     * Save jpm button deploy dtos.
     * </p>
     *
     * @param buttonDeployDtos
     *            type {@link List<JpmButtonDeployDto>}
     * @param processDeployId
     *            type {@link Long}
     * @return {@link Map<Long,Long>}: key: buttonId, value: buttonDeployId
     * @author KhuongTH
     */
    Map<Long, Long> saveJpmButtonDeployDtos(List<JpmButtonDeployDto> buttonDeployDtos, Long processDeployId);

    /**
     * <p>
     * getButtonDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmButtonDeployDto>}
     * @author KhuongTH
     */
    List<JpmButtonDeployDto> getButtonDeployDtosByProcessDeployId(Long processDeployId);

    /**
     * <p>
     * Get action dto by process id and button id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param buttonId
     *            type {@link Long}
     * @return {@link ActionDto}
     * @author tantm
     */
    ActionDto getActionDtoByProcessDeployIdAndButtonDeployId(Long processDeployId, Long buttonId);
    

    JpmButtonDeployDto getJpmButtonDeployDtoByButtonTextAndProcessDeployId(String buttonText, Long processDeployId);

}