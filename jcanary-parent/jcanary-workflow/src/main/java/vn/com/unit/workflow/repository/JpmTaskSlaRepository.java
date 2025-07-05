/*******************************************************************************
 * Class        ：JpmTaskSlaRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmTaskSlaDto;
import vn.com.unit.workflow.entity.JpmTaskSla;

/**
 * <p>
 * JpmTaskSlaRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskSlaRepository extends DbRepository<JpmTaskSla, Long>{

    /**
     * <p>
     * Get jpm task sla dto by jpm task id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link JpmTaskSlaDto}
     */
    public JpmTaskSlaDto getJpmTaskSlaDtoByJpmTaskId(@Param("jpmTaskId") Long jpmTaskId);
    
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
    public List<JpmTaskSlaDto> getJpmTaskSlaDtoListByDocId(@Param("docId") Long docId);
}
