/*******************************************************************************
 * Class        ：JpmTaskService
 * Created date ：2020/11/19
 * Lasted date  ：2020/11/19
 * Author       ：tantm
 * Change log   ：2020/11/19：01-00 tantm create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.Date;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.dto.JpmTaskDto;
import vn.com.unit.workflow.entity.JpmHiTask;
import vn.com.unit.workflow.entity.JpmTask;

/**
 * <p>
 * JpmTaskService
 * </p>
 * .
 *
 * @version 01-00
 * @since 01-00
 * @author tantm
 */
public interface JpmTaskService extends DbRepositoryService<JpmTask, Long>{

    /**
     * <p>
     * Get jpm task by core task id.
     * </p>
     *
     * @param coreTaskId
     *            type {@link String}
     * @return {@link JpmTask}
     * @author tantm
     */
    public JpmTask getJpmTaskByCoreTaskId(String coreTaskId);

    /**
     * <p>
     * Creates the task.
     * </p>
     *
     * @param jpmTask
     *            type {@link JpmTask}
     * @author tantm
     */
    void createTask(JpmTask jpmTask);

    /**
     * <p>
     * Save jpm task.
     * </p>
     *
     * @param jpmTask
     *            type {@link JpmTask}
     * @return {@link JpmTask}
     * @author tantm
     */
    public JpmTask saveJpmTask(JpmTask jpmTask);

    /**
     * <p>
     * Complete jpm task.
     * </p>
     *
     * @param hiTask
     *            type {@link JpmHiTask}
     * @author tantm
     */
    void completeJpmTask(JpmHiTask hiTask);

    /**
     * <p>
     * Update plan sla jpm task.
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
    public void updatePlanSlaJpmTask(Long jpmTaskId, Date startDate, Date dueDate, int estimateTime, int callandarType, String estimateUnitTime, Long totalTime, Long slaConfigId);

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
    public JpmTaskDto getCurrentTaskByDocId(Long docId);
    
}
