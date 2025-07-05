/*******************************************************************************
* Class        JpmHiStatusService
* Created date 2020/11/25
* Lasted date  2020/11/25
* Author       KhuongTH
* Change log   2020/11/25 01-00 KhuongTH create a new
******************************************************************************/

package vn.com.unit.workflow.service;

import vn.com.unit.workflow.entity.JpmHiStatus;
import vn.com.unit.workflow.entity.JpmStatus;

/**
 * JpmHiStatusService
 * 
 * @version 01-00
 * @since 01-00
 * @author KhuongTH
 */

public interface JpmHiStatusService {

    /**
     * <p>
     * Save jpm hi status.
     * </p>
     *
     * @param jpmStatus
     *            type {@link JpmStatus}
     * @return {@link JpmHiStatus}
     * @author KhuongTH
     */
    JpmHiStatus saveJpmHiStatus(JpmStatus jpmStatus);

}