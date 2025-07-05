/*******************************************************************************
 * Class        ：JpmTaskEmailAlertService
 * Created date ：2021/03/01
 * Lasted date  ：2021/03/01
 * Author       ：TrieuVD
 * Change log   ：2021/03/01：01-00 TrieuVD create a new
 ******************************************************************************/
package vn.com.unit.workflow.service;

import java.util.List;

import vn.com.unit.db.service.DbRepositoryService;
import vn.com.unit.workflow.entity.JpmTaskNotiAlert;

/**
 * <p>
 * JpmTaskEmailAlertService
 * </p>
 * .
 *
 * @author TrieuVD
 * @version 01-00
 * @since 01-00
 */
public interface JpmTaskNotiAlertService extends DbRepositoryService<JpmTaskNotiAlert, Long>{

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
    public List<JpmTaskNotiAlert> getJpmTaskNotiAlertListByJpmTaskId(Long jpmTaskId);
    
    /**
     * <p>
     * Creates the jpm task noti alert by list alert id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param alertIdList
     *            type {@link List<Long>}
     */
    void createJpmTaskNotiAlertByListAlertId(Long jpmTaskId, List<Long> alertIdList);
}
