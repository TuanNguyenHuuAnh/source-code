/*******************************************************************************
 * Class        ：JpmTaskSlaService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.Date;
import java.util.List;

import vn.com.unit.common.sla.dto.SlaDateResult;
import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.dto.JpmTaskSlaDto;
import vn.com.unit.workflow.entity.JpmTaskSla;

/**
 * <p>
 * JpmTaskSlaService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskSlaService extends DbRepositoryService<JpmTaskSla, Long>{

    /**
     * <p>
     * Creates the jpm task sla by sla date result.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param slaDateResult
     *            type {@link SlaDateResult}
     * @return {@link JpmTaskSla}
     */
    public JpmTaskSla createJpmTaskSlaBySlaDateResult(Long jpmTaskId, SlaDateResult slaDateResult);
    
    /**
     * <p>
     * Move jpm task sla to jpm hi task sla.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param completeDate
     *            type {@link Date}
     * @param elapsedMinutes
     *            type {@link Long}
     */
    public void moveJpmTaskSlaToJpmHiTaskSla(Long jpmTaskId, Date completeDate, Long elapsedMinutes);
    
    /**
     * <p>
     * Get jpm task sla dto by jpm task id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link void}
     */
    public JpmTaskSlaDto getJpmTaskSlaDtoByJpmTaskId(Long jpmTaskId);
    
    /**
     * <p>
     * Get jpm task sla dto list by doc id.
     * </p>
     *
     * @author TrieuVD
     * @param docId
     *            type {@link Long}
     * @return {@link List<JpmTaskSlaDto>}
     */
    public List<JpmTaskSlaDto> getJpmTaskSlaDtoListByDocId(Long docId);
}
