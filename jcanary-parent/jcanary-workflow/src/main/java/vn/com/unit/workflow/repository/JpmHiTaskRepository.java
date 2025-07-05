/*******************************************************************************
* Class        JpmHiTaskRepository
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

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmHiTaskDto;
import vn.com.unit.workflow.entity.JpmHiTask;

/**
 * JpmHiTaskRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

@Repository
public interface JpmHiTaskRepository extends DbRepository<JpmHiTask, Long> {

    /**
     * <p>
     * Update sla due date hi task.
     * </p>
     *
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
     * @author tantm
     * @param slaConfigId 
     */
    @Modifying
    void updateSlaDueDateHiTask(@Param("jpmTaskId")Long jpmTaskId, @Param("startDate")Date startDate, @Param("dueDate")Date dueDate, @Param("estimateTime")int estimateTime, @Param("callandarType")int callandarType, @Param("estimateUnitTime")String estimateUnitTime, @Param("totalTime")Long totalTime, @Param("slaConfigId")Long slaConfigId);

    /**
     * <p>
     * Get list jpm hi task dto by doc code.
     * </p>
     *
     * @param docId
     *            type {@link String}
     * @param langCode
     *            type {@link String}
     * @return {@link List<JpmHiTaskDto>}
     * @author tantm
     */
    List<JpmHiTaskDto> getListJpmHiTaskDtoByDocId(@Param("docId")Long docId, @Param("lang")String langCode);
    
}