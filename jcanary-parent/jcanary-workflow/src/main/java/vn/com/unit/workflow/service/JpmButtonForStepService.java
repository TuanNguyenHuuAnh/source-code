/*******************************************************************************
* Class        JpmButtonForStepService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.workflow.dto.JpmButtonForStepDto;
import vn.com.unit.workflow.entity.JpmButtonForStep;

/**
 * JpmButtonForStepService.
 *
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmButtonForStepService {

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
     * save JpmButtonForStep with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE).
     *
     * @param jpmButtonForStep
     *            type {@link JpmButtonForStep}
     * @return {@link JpmButtonForStep}
     * @author KhuongTH
     */
    JpmButtonForStep saveJpmButtonForStep(JpmButtonForStep jpmButtonForStep);

    /**
     * save JpmButtonForStepDto.
     *
     * @param jpmButtonForStepDto
     *            type {@link JpmButtonForStepDto}
     * @return {@link JpmButtonForStep}
     * @author KhuongTH
     */
    JpmButtonForStep saveJpmButtonForStepDto(JpmButtonForStepDto jpmButtonForStepDto);

    /**
     * <p>
     * get list ButtonForStepDtos by process id
     * </p>
     * .
     *
     * @param processId
     *            type {@link Long}
     * @return {@link List<JpmButtonForStepDto>}
     * @author KhuongTH
     */
    List<JpmButtonForStepDto> getButtonForStepDtosByProcessId(Long processId);

    /**
     * saveButtonForStepDtosByProcessId.
     *
     * @param buttonForStepDto
     *            type {@link List<JpmButtonForStepDto>}
     * @param stepId
     *            type {@link Long}
     * @author ngannh
     */
    void saveButtonForStepDtosByProcessId(List<JpmButtonForStepDto> buttonForStepDto, Long stepId);

    /**
     * getButtonForStepDtosByStepId.
     *
     * @param stepId
     *            type {@link Long}
     * @return the button for step dtos by step id
     * @author ngannh
     */
    List<JpmButtonForStepDto> getButtonForStepDtosByStepId(Long stepId);
    
    
    /**
     * <p>
     * Delete button for step by step id.
     * </p>
     *
     * @param stepId
     *            type {@link Long}
     * @author sonnd
     */
    void deleteButtonForStepByStepId(Long stepId);
}