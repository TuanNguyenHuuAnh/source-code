/*******************************************************************************
 * Class        ：JpmTaskSlaAsyncService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.Date;

import vn.com.unit.common.sla.dto.CalculateDueDateParam;
import vn.com.unit.common.sla.dto.CalculateElapsedMinutesParam;
import vn.com.unit.common.sla.dto.CreateSlaAlertParam;

/**
 * <p>
 * JpmTaskSlaAsyncService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskSlaAsyncService {

    /**
     * <p>
     * Creates the task sla.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param param
     *            type {@link CalculateDueDateParam}
     */
    public void createTaskSla(Long jpmTaskId, CalculateDueDateParam param);

    /**
     * <p>
     * Complete task sla.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param completeDate
     *            type {@link Date}
     */
    
    public void completeTaskSla(Long jpmTaskId, CalculateElapsedMinutesParam param);
    
    /**
     * <p>
     * Excute sla for task.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param param
     *            type {@link CreateSlaAlertParam}
     */
    public void excuteSlaForTask(Long jpmTaskId, CreateSlaAlertParam param);
    
    /**
     * <p>
     * Excute sla for finished.
     * </p>
     *
     * @author TrieuVD
     * @param processDeployId
     *            type {@link Long}
     * @param stepDeployId
     *            type {@link Long}
     * @param docId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     */
    public void excuteSlaForFinished(Long processDeployId, Long stepDeployId, Long docId, Date sysDate);
    
    /**
     * <p>
     * Excute sla for reject.
     * </p>
     *
     * @author TrieuVD
     * @param processDeployId
     *            type {@link Long}
     * @param stepDeployId
     *            type {@link Long}
     * @param docId
     *            type {@link Long}
     * @param sysDate
     *            type {@link Date}
     */
    public void excuteSlaForReject(Long processDeployId, Long stepDeployId, Long docId, Date sysDate);
}
