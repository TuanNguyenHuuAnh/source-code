/*******************************************************************************
* Class        JpmButtonForStepDeployRepository
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.com.unit.common.dto.ActionDto;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmButtonForDocDto;
import vn.com.unit.workflow.dto.JpmButtonForStepDeployDto;
import vn.com.unit.workflow.entity.JpmButtonForStepDeploy;

/**
 * JpmButtonForStepDeployRepository.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmButtonForStepDeployRepository extends DbRepository<JpmButtonForStepDeploy, Long> {

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
    List<JpmButtonForDocDto> getListButtonForDocDtoByProcessDeployIdAndStepCode(
            @Param("processDeployId") Long processDeployId, @Param("userId") Long userId,
            @Param("stepCode") String stepCode, @Param("lang") String lang);

    /**
     * <p>
     * Gets the button for step deploy dtos by process deploy id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @return the button for step deploy dtos by process deploy id
     * @author KhuongTH
     */
    List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByProcessDeployId(@Param("processDeployId") Long processDeployId);

    /**
     * <p>
     * Gets the button for step deploy dtos by step deploy id.
     * </p>
     *
     * @param stepDeployId
     *            type {@link Long}
     * @return the button for step deploy dtos by step deploy id
     * @author KhuongTH
     */
    List<JpmButtonForStepDeployDto> getButtonForStepDeployDtosByStepDeployId(@Param("stepDeployId") Long stepDeployId);

    /**
     * Gets the permission deploy id by step deploy id and button deploy id.
     *
     * @param stepId
     *            the step id type Long
     * @param actionId
     *            the action id type Long
     * @return the permission deploy id by step deploy id and button deploy id
     * @author tantm
     */
    Long getPermissionDeployIdByStepDeployIdAndButtonDeployId(@Param("stepId") Long stepId, @Param("actionId") Long actionId);

    /**
     * <p>
     * Get action dto by core task id and button id.
     * </p>
     *
     * @param processDeployId
     *            type {@link Long}
     * @param coreTaskId
     *            type {@link String}
     * @param buttonId
     *            type {@link Long}
     * @param commonStatusCode
     *            type {@link String}
     * @return {@link ActionDto}
     * @author tantm
     */
    ActionDto getActionDtoByCoreTaskIdAndButtonId(@Param("processDeployId") Long processDeployId, @Param("coreTaskId") String coreTaskId,
            @Param("buttonId") Long buttonId, @Param("commonStatusCode") String commonStatusCode);

    /**
     * <p>
     * Get list button for doc dto by efo oz doc ids.
     * </p>
     *
     * @param efoOzDocIds
     *            type {@link List<Long>}
     * @param actTaskIds
     *            type {@link List<String>}
     * @param accountId
     *            type {@link Long}
     * @param lang
     *            type {@link String}
     * @param sysDate
     *            type {@link Date}
     * @return {@link List<JpmButtonDeployDto>}
     * @author taitt
     */
    List<JpmButtonForDocDto> getListButtonForStepDeployDtoByEfoOzDocIds(@Param("efoOzDocIds") List<Long> efoOzDocIds,
            @Param("accountId") Long accountId, @Param("lang") String lang, @Param("sysDate") Date sysDate);

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
    Long getPermissionDeployIdByProcessDeployIdAndStepDeployId(@Param("processDeployId") Long processDeployId,
            @Param("stepDeployId") Long stepDeployId);
}