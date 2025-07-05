/*******************************************************************************
* Class        JpmButtonForStepDeployService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.dto.JpmButtonWrapper;
import vn.com.unit.workflow.entity.JpmButtonForStepDeploy;

/**
 * JpmButtonForStepDeployService.
 *
 * @author KhuongTH
 * @version 01-00
 * @since 01-00
 */

public interface JpmButtonForStepDeployService {

    /**
     * save JpmButtonForStepDeploy with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @author KhuongTH
     * @param jpmButtonForStepDeploy
     *            the jpm button for step deploy type JpmButtonForStepDeploy
     * @return {@link JpmButtonForStepDeploy}
     */
    JpmButtonForStepDeploy saveJpmButtonForStepDeploy(JpmButtonForStepDeploy jpmButtonForStepDeploy);

    /**
     * save JpmButtonForStepDeployDto.
     *
     * @author KhuongTH
     * @param jpmButtonForStepDeployDto
     *            type {@link JpmButtonForStepDeployDto}
     * @return {@link JpmButtonForStepDeploy}
     */
    JpmButtonForStepDeploy saveJpmButtonForStepDeployDto(JpmButtonForStepDeployDto jpmButtonForStepDeployDto);

    /**
     * get list of ButtonForDocDto by ProcessDeployId and StepCode.
     *
     * @param processDeployId
     *            type {@link Long}: id of JpmProcessDeploy
     * @param stepCode
     *            type {@link String}: stepCode of JpmStepDeploy
     * @param lang
     *            type {@link String}: language code
     * @return {@link JpmButtonWrapper<JpmButtonForDocDto>}
     * @author KhuongTH
     */
    JpmButtonWrapper<JpmButtonForDocDto> getListButtonForDocDtoByProcessDeployIdAndStepCode(Long processDeployId, String stepCode,
            String lang);

    /**
     * <p>
     * save list JpmButtonForStepDeployDto by step deploy id
     * </p>
     * .
     *
     * @author KhuongTH
     * @param buttonForStepDeployDtos
     *            type {@link List<JpmButtonForStepDeployDto>}
     * @param stepDeployId
     *            type {@link Long}
     */
    void saveJpmButtonForStepDeployDtos(List<JpmButtonForStepDeployDto> buttonForStepDeployDtos, Long stepDeployId);

    /**
     * Gets the button for step deploy dtos by process deploy id.
     *
     * @author KhuongTH
     * @param processDeployId
     *            type {@link Long}
     * @return {@link List<JpmButtonForStepDeployDto>}
     */
    List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByProcessDeployId(Long processDeployId);

    /**
     * <p>
     * Gets the button for step deploy dtos by step deploy id.
     * </p>
     *
     * @author KhuongTH
     * @param stepDeployId
     *            type {@link Long}
     * @return the button for step deploy dtos by step deploy id
     */
    List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByStepDeployId(Long stepDeployId);

    /**
     * Gets the permission deploy id by step deploy id and button deploy id.
     *
     * @author tantm
     * @param stepId
     *            the step id type Long
     * @param actionId
     *            the action id type Long
     * @return the permission deploy id by step deploy id and button deploy id
     */
    Long getPermissionDeployIdByStepDeployIdAndButtonDeployId(Long stepId, Long actionId);

    /**
     * <p>
     * Get action dto by core task id and button id.
     * </p>
     *
     * @author taitt
     * @param processDeployId
     *            type {@link Long}
     * @param coreTaskId
     *            type {@link String}
     * @param buttonId
     *            type {@link Long}
     * @param docId
     *            type {@link Long}
     * @return {@link ActionDto}
     */
    ActionDto getActionDtoByCoreTaskIdAndButtonId(Long processDeployId, String coreTaskId, Long buttonId, String commonStatusCode);

    /**
     * getListButtonForStepDeployDtoByEfoOzDocIds.
     *
     * @author taitt
     * @param efoOzDocIds
     *            type {@link List<Long>}
     * @param accountId
     *            type {@link Long}
     * @param lang
     *            type {@link String}
     * @return {@link List<JpmButtonForStepDeployDto>}
     */
    List<JpmButtonForDocDto> getListButtonForStepDeployDtoByEfoOzDocIds(List<Long> efoOzDocIds, Long accountId, String lang);

    /**
     * getListButtonForDocDtoByDocId.
     *
     * @author taitt
     * @param buttonForDocDtos
     *            type {@link List<JpmButtonForDocDto>}
     * @param docId
     *            type {@link Long}
     * @return {@link JpmButtonWrapper<JpmButtonForDocDto>}
     */
    JpmButtonWrapper<JpmButtonForDocDto> getListButtonForDocDtoByDocId(List<JpmButtonForDocDto> buttonForDocDtos, Long docId);

    /**
     * <p>
     * Get permission deploy id by process deploy id and step deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param stepDeployId
     *            type {@link Long}
     * @return {@link Long}
     * @author tantm
     */
    Long getPermissionDeployIdByProcessDeployIdAndStepDeployId(Long processDeployId, Long stepDeployId);

}