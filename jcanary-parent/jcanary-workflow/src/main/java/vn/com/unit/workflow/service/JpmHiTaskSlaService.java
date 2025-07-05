/*******************************************************************************
 * Class        ：JpmHiTaskSlaService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.dto.JpmHiTaskSlaDto;
import vn.com.unit.workflow.entity.JpmHiTaskSla;

/**
 * <p>
 * JpmHiTaskSlaService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmHiTaskSlaService extends DbRepositoryService<JpmHiTaskSla, Long> {

    /**
     * <p>
     * Get jpm hi task sla dto list by doc id.
     * </p>
     *
     * @author TrieuVD
     * @param docId
     *            type {@link Long}
     * @return {@link JpmHiTaskSlaDto}
     */
    public List<JpmHiTaskSlaDto> getJpmHiTaskSlaDtoListByDocId(Long docId);
}
