/*******************************************************************************
* Class        JpmStepService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;
import java.util.Map;

import vn.com.unit.workflow.dto.JpmStepDto;
import vn.com.unit.workflow.entity.JpmStep;

/**
 * JpmStepService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmStepService {

    /**
     * get JpmStepDto by id.
     *
     * @param id
     *            type {@link Long}
     * @return {@link JpmStepDto}
     * @author KhuongTH
     */
    JpmStepDto getJpmStepDtoById(Long id);

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
     * save JpmStep with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmStep
     *            type {@link JpmStep}
     * @return {@link JpmStep}
     * @author KhuongTH
     */
    JpmStep saveJpmStep(JpmStep jpmStep);

    /**
     * save JpmStepDto.
     *
     * @param jpmStepDto
     *            type {@link JpmStepDto}
     * @return {@link JpmStep}
     * @author KhuongTH
     */
    JpmStep saveJpmStepDto(JpmStepDto jpmStepDto);

    /**
     * <p>
     * get StepDtos and get language by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmStepDto>}
     * @author KhuongTH
     */
    List<JpmStepDto> getStepDtosByProcessId(Long processId);

    
    /**
     * <p>
     * Gets the step dto by step id.
     * </p>
     *
     * @param processStepId
     *            type {@link Long}
     * @return the step dto by step id
     * @author sonnd
     */
    JpmStepDto getStepDtoByStepId(Long processStepId);

    /**
     * saveButtonDtosByProcessId
     * @param stepDtos
     * @param processId
     * @author ngannh
     */
    Map<Long, Long> saveStepDtosByProcessId(List<JpmStepDto> stepDtos, Long processId);

}