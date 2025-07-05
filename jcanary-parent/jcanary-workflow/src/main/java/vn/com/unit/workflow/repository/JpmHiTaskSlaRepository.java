/*******************************************************************************
 * Class        ：JpmHiTaskSlaRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.dto.JpmHiTaskSlaDto;
import vn.com.unit.workflow.entity.JpmHiTaskSla;

/**
 * <p>
 * JpmHiTaskSlaRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmHiTaskSlaRepository extends DbRepository<JpmHiTaskSla, Long>{

    /**
     * <p>
     * Get jpm hi task sla dto list by doc id.
     * </p>
     *
     * @author TrieuVD
     * @param docId
     *            type {@link Long}
     * @return {@link List<JpmHiTaskSlaDto>}
     */
    public List<JpmHiTaskSlaDto> getJpmHiTaskSlaDtoListByDocId(@Param("docId") Long docId);
    
}
