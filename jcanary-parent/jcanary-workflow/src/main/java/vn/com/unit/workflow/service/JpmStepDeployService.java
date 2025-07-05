/*******************************************************************************
 * Class        ：JpmStepDeployService
 * Created date ：2020/12/15
 * Lasted date  ：2020/12/15
 * Author       ：KhuongTH
 * Change log   ：2020/12/15：01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmStepDeployDto;
import vn.com.unit.workflow.entity.JpmStepDeploy;

/**
 * JpmStepDeployService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStepDeployService {

    /**
     * get JpmStepDeployDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmStepDeployDto}
     * @author KhuongTH
     */
    JpmStepDeployDto getJpmStepDeployDtoById(Long id);

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
     * save JpmStepDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmStepDeploy
     *            type {@link JpmStepDeploy}
     * @return {@link JpmStepDeploy}
     * @author KhuongTH
     */
    JpmStepDeploy saveJpmStepDeploy(JpmStepDeploy jpmStepDeploy);

    /**
     * save JpmStepDeployDto
     * 
     * @param jpmStepDeployDto
     *            type {@link JpmStepDeployDto}
     * @return {@link JpmStepDeploy}
     * @author KhuongTH
     */
    JpmStepDeploy saveJpmStepDeployDto(JpmStepDeployDto jpmStepDeployDto);

    /**
     * Get first JpmStepDeploy by processDeployId
     * 
     * @param processDeployId
     *            type {@link Long}: id of process deploy
     * @return {@link JpmStepDeploy}
     * @author KhuongTH
     */
    JpmStepDeploy getJpmStepDeployFirstByProcessDeployId(Long processDeployId);

    /**
     * Get JpmStepDeployDto by processDeployId and stepCode
     * 
     * @param processDeployId
     *            type {@link Long}: id of JpmProcessDeploy
     * @param stepCode
     *            type {@link String}: stepCode of JpmStepDeploy
     * @return {@link JpmStepDeployDto}
     * @author KhoaNA
     */
    JpmStepDeployDto getJpmStepDeployDtoByProcessDeployIdAndStepCode(Long processDeployId, String stepCode);

    /**
     * <p>
     * save list JpmStepDeployDtos, language, and button for step by process deploy id
     * </p>
     * 
     * @param stepDeployDtos
     *            type {@link List<JpmStepDeployDto>}
     * @param processDeployId
     *            type {@link Long}: id of JpmProcessDeploy
     * @return {@link Map<Long, Long>}: key: stepId, value: stepDeployId
     * @author KhuongTH
     */
    Map<Long, Long> saveJpmStepDeployDtos(List<JpmStepDeployDto> stepDeployDtos, Long processDeployId);

    /**
     * <p>
     * getStepDeployDtosByProcessDeployId
     * </p>
     * .
     *
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmStepDeployDto>}
     * @author KhuongTH
     */
    List<JpmStepDeployDto> getStepDeployDtosByProcessDeployId(Long processDeployId);
}