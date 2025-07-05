/*******************************************************************************
 * Class        ：JpmTaskRepository
 * Created date ：2020/11/19
 * Lasted date  ：2020/11/19
 * Author       ：tantm
 * Change log   ：2020/11/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.Date;

import org.springframework.data.repository.query.Param;

import jp.xet.springframework.data.mirage.repository.query.Modifying;
import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmTaskDto;
import vn.com.unit.workflow.entity.JpmTask;

/**
 * JpmTaskRepository
 * 
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JpmTaskRepository extends DbRepository<JpmTask, Long> {

    /**
     * get JpmTask by CoreTaskId
     * @param coreTaskId
     *          type {@link String}
     * @return {@link JpmTask}
     * @author KhuongTH
     */
    JpmTask getJpmTaskByCoreTaskId(@Param("coreTaskId") String coreTaskId);

    /**
     * <p>
     * Update sla due date task.
     * </p>
     *
     * @param jpmTaskId
     *            type {@link Long}
     * @param dueDate
     *            type {@link Date}
     * @param slaMinutes
     *            type {@link int}
     * @author tantm
     * @param totalTime 
     * @param estimateUnitTime 
     * @param callandarType 
     * @param estimateTime 
     * @param dueDate 
     * @param startDate 
     * @param jpmTaskId 
     * @param estimateUnitTime 
     * @param callandarType 
     * @param slaConfigId 
     * @param dueDate2 
     * @param functionId 
     */
    @Modifying
    void updateSlaDueDateTask(@Param("jpmTaskId")Long jpmTaskId, @Param("startDate")Date startDate, @Param("dueDate")Date dueDate, @Param("estimateTime")int estimateTime, @Param("callandarType")int callandarType, @Param("estimateUnitTime")String estimateUnitTime, @Param("totalTime")Long totalTime, @Param("slaConfigId")Long slaConfigId);

    /**
     * <p>
     * Get current task by doc id.
     * </p>
     *
     * @param docId
     *            type {@link Long}
     * @return {@link JpmTaskDto}
     * @author tantm
     */
    JpmTaskDto getCurrentTaskByDocId(@Param("docId") Long docId);
}
