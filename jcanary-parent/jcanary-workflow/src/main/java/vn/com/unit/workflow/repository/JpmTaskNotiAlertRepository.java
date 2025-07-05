/*******************************************************************************
 * Class        ：JpmTaskNotiAlertRepository
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

import vn.com.unit.db.repository.DbRepository;
import vn.com.unit.workflow.entity.JpmTaskNotiAlert;

/**
 * <p>
 * JpmTaskNotiAlertRepository
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskNotiAlertRepository extends DbRepository<JpmTaskNotiAlert, Long>{
    
    /**
     * <p>
     * Get jpm task noti alert list by jpm task id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link List<JpmTaskNotiAlert>}
     */
    public List<JpmTaskNotiAlert> getJpmTaskNotiAlertListByJpmTaskId(@Param("jpmTaskId") Long jpmTaskId);
}
