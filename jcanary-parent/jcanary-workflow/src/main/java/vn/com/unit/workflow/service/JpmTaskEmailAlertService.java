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
import vn.com.unit.workflow.entity.JpmTaskEmailAlert;

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
public interface JpmTaskEmailAlertService extends DbRepositoryService<JpmTaskEmailAlert, Long>{

    /**
     * <p>
     * Get jpm task email alert list by jpm task id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @return {@link JpmTaskEmailAlert}
     */
    public List<JpmTaskEmailAlert> getJpmTaskEmailAlertListByJpmTaskId(Long jpmTaskId);
    
    /**
     * <p>
     * Creates the jpm task email alert by list alert id.
     * </p>
     *
     * @author TrieuVD
     * @param jpmTaskId
     *            type {@link Long}
     * @param alertIdList
     *            type {@link List<Long>}
     */
    public void createJpmTaskEmailAlertByListAlertId(Long jpmTaskId, List<Long> alertIdList);
}
