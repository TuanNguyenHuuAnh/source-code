/*******************************************************************************
* Class        JpmHiTaskService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.entity.JpmHiTask;

/**
 * JpmHiTaskService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiTaskService {

    /**
     * get JpmHiTaskDto by id
     * 
     * @param id
     *            type {@link Long}
     * @return {@link JpmHiTaskDto}
     * @author KhuongTH
     */
    JpmHiTaskDto getJpmHiTaskDtoById(Long id);

    /**
     * save JpmHiTask with (CREATED_ID and CREATED_DATE) or (UPDATED_ID and UPDATED_DATE)
     * 
     * @param JpmHiTask
     *            type {@link JpmHiTask}
     * @return {@link JpmHiTask}
     * @author KhuongTH
     */
    JpmHiTask saveJpmHiTask(JpmHiTask jpmHiTask);

    /**
     * save JpmHiTaskDto
     * 
     * @param jpmHiTaskDto
     *            type {@link JpmHiTaskDto}
     * @return {@link JpmHiTask}
     * @author KhuongTH
     */
    JpmHiTask saveJpmHiTaskDto(JpmHiTaskDto jpmHiTaskDto);

    /**
     * get JpmHiTask by taskId
     * 
     * @param id
     *            type {@link Long}: id of JpmTask
     * @return {@link JpmHiTask}
     * @author KhuongTH
     */
    JpmHiTask getJpmHiTaskById(@NotNull Long id);

    /**
     * <p>
     * Update sla due date hi task.
     * </p>
     *
     * @author tantm
     * @param jpmTaskId
     *            type {@link Long}
     * @param startDate
     *            type {@link Date}
     * @param dueDate
     *            type {@link Date}
     * @param estimateTime
     *            type {@link int}
     * @param callandarType
     *            type {@link int}
     * @param estimateUnitTime
     *            type {@link String}
     * @param totalTime
     *            type {@link Long}
     * @param slaConfigId 
     */
    void updateSlaDueDateHiTask(Long jpmTaskId, Date startDate, Date dueDate, int estimateTime, int callandarType, String estimateUnitTime, Long totalTime, Long slaConfigId);

    /**
     * <p>
     * Get list jpm hi task dto by doc code.
     * </p>
     *
     * @param docId
     *            type {@link String}
     * @return {@link List<JpmHiTaskDto>}
     * @author tantm
     */
    List<JpmHiTaskDto> getListJpmHiTaskDtoByDocId(Long docId);

}